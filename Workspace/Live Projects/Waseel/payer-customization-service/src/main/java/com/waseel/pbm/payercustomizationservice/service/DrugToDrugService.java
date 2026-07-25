package com.waseel.pbm.payercustomizationservice.service;

import com.waseel.pbm.payercustomizationservice.common.CommonMethods;
import com.waseel.pbm.payercustomizationservice.enums.*;
import com.waseel.pbm.payercustomizationservice.model.Error;
import com.waseel.pbm.payercustomizationservice.model.*;
import com.waseel.pbm.payercustomizationservice.persist.CustomizationAuditTrail;
import com.waseel.pbm.payercustomizationservice.persist.PcDrugToDrug;
import com.waseel.pbm.payercustomizationservice.repository.CommonRejectionReasonRepository;
import com.waseel.pbm.payercustomizationservice.repository.PCDrugToDrugRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class DrugToDrugService {

    @Autowired
    private PCDrugToDrugRepository drugToDrugRepository;

    @Autowired
    private CommonRejectionReasonRepository commonRejectionReasonRepository;

    @Autowired
    CommonMethods commonMethods;

    public DssResponse manageCustomizationDrugToDrug(PCRequest pcRequest) {
        DssRequest dssRequest = pcRequest.getDssRequest();
        DssResponse dssResponse = pcRequest.getDssResponse();
        List<CustomizationAuditTrail> auditTrailList = new ArrayList<>();
        List<String> ruleIdList = new ArrayList<>();
        dssResponse.getResults().forEach(result -> {
            if (result.getStatus().equalsIgnoreCase(ServiceStatus.REJECTED.value())) {
                manageRejectedService(result, dssRequest.getPayerId(), dssResponse.getResults(),
                        dssRequest.getRequestId(), auditTrailList, ruleIdList);
            } else {
                manageApprovedService(result, dssRequest.getPayerId(), result.getNdcDrugCode(),
                        dssResponse.getResults(), dssRequest.getRequestId(), auditTrailList, ruleIdList);
            }
        });
        commonMethods.saveDataToAuditTrail(auditTrailList);
        return dssResponse;
    }

    private void manageApprovedService(Result result, String payerId, String serviceCode,
                                       List<Result> results, String requestId,
                                       List<CustomizationAuditTrail> auditTrailList, List<String> ruleIdList) {
        List<PcDrugToDrug> payerConfig = payerConfigDetailsWithoutModuleName(payerId, serviceCode, results);
        List<Error> newErrorList = new ArrayList<>();
        if (!payerConfig.isEmpty()) {
            List<PcDrugToDrug> specificPayers = filterPayerList(payerConfig);
            if (!specificPayers.isEmpty()) {
                payerConfig.removeAll(specificPayers);
                setApprovedServiceResultForPrimaryServiceCode(specificPayers, newErrorList, requestId,
                        auditTrailList, ruleIdList);
            }
            setApprovedServiceResultForPrimaryServiceCode(payerConfig, newErrorList, requestId, auditTrailList, ruleIdList);
            result.setStatus(ServiceStatus.REJECTED.value());
            result.setErrors(newErrorList);
        }
    }

    private void setApprovedServiceResultForPrimaryServiceCode(List<PcDrugToDrug> payerDetails,
                                                               List<Error> newErrorList, String requestId,
                                                               List<CustomizationAuditTrail> auditTrailList,
                                                               List<String> ruleIdList) {
        payerDetails.forEach(config -> {
            Error error = new Error();
            error.setCode(PCModuleCode.PC_DRUG_TO_DRUG.code());
            if (!StringUtils.isBlank(config.getAdditionalRejectionReason())) {
                error.setDescription(config.getAdditionalRejectionReason());
            } else {
                error.setDescription(getCommonRejectionReasonMsg(config.getId().getServiceCode(),
                        config.getId().getInteractedServiceCode()));
            }
            commonMethods.saveDataToAuditTrailList(requestId, config.getRuleId(), auditTrailList, ruleIdList);
            newErrorList.add(error);
        });
    }

    private List<String> getInteractedServiceCodes(List<Result> results, String primaryServiceCode) {
        List<String> interactedServiceCodes = new ArrayList<>();
        List<Result> interactedResultList = results.stream()
                .filter(dssResult -> !dssResult.getNdcDrugCode().equals(primaryServiceCode))
                .collect(Collectors.toList());
        interactedResultList.forEach(result -> interactedServiceCodes.add(result.getNdcDrugCode()));
        return interactedServiceCodes;
    }

    private void manageRejectedService(Result result, String payerId, List<Result> results, String requestId,
                                       List<CustomizationAuditTrail> auditTrailList, List<String> ruleIdList) {
        String serviceCode = result.getNdcDrugCode();
        List<Error> approvedErrorList = new ArrayList<>();
        List<Error> newRejectedErrorList = new ArrayList<>();
        List<String> interactedServiceCodeFromErrors = new ArrayList<>();
        result.getErrors().forEach(error -> {
            if (error.getCode().equals(RejectionCode.FDB_DRUG_TO_DRUG.code())) {
                validateModuleBaseError(result, approvedErrorList, error, newRejectedErrorList,
                        payerConfigDetailsBasedonModuleName(payerId, serviceCode, ModuleName.FDB.value(), results),
                        requestId, auditTrailList, ruleIdList);
            } else if (error.getCode().equals(RejectionCode.IDF_DRUG_TO_DRUG.code())) {
                validateModuleBaseError(result, approvedErrorList, error, newRejectedErrorList,
                        payerConfigDetailsBasedonModuleName(payerId, serviceCode, ModuleName.IDF.value(), results),
                        requestId, auditTrailList, ruleIdList);
            }
            addInteractedServiceCodeAvailableInErrors(results, serviceCode, error, interactedServiceCodeFromErrors);
        });
        validateInteractedDrugNotInError(payerId, serviceCode, results, interactedServiceCodeFromErrors,
                newRejectedErrorList);
        if (!approvedErrorList.isEmpty()) {
            result.getErrors().removeAll(approvedErrorList);
        }
        if (!newRejectedErrorList.isEmpty()) {
            result.getErrors().addAll(newRejectedErrorList);
        }
        if (result.getErrors().isEmpty()) {
            result.setStatus(ServiceStatus.APPROVED.value());
            result.setErrors(null);
        }
    }

    private void addInteractedServiceCodeAvailableInErrors(List<Result> results, String serviceCode, Error error,
                                                           List<String> interactedServiceCodeFromErrors) {
        List<String> allInteractedServiceCode = getInteractedServiceCodes(results, serviceCode);
        allInteractedServiceCode.forEach(code -> {
            Matcher matcher = Pattern.compile("with " + code + " ", Pattern.CASE_INSENSITIVE)
                    .matcher(error.getDescription());
            if (matcher.find()) {
                matcher.reset();
                interactedServiceCodeFromErrors.add(code);
            }
        });
    }

    private void validateInteractedDrugNotInError(String payerId, String serviceCode, List<Result> results,
                                                  List<String> interactedServiceCodeFromErrors, List<Error> newRejectedErrorList) {
        /*
         * Need this because if in the request drug REJECTED with errors but in table we
         * have another drug as REJECTED that is not available in error
         */
        List<PcDrugToDrug> payerConfig = payerConfigDetailsWithoutModuleName(payerId, serviceCode, results);
        if (payerConfig != null && !payerConfig.isEmpty()) {
            List<String> codes = interactedServiceCodeFromErrors.stream().distinct().collect(Collectors.toList());
            payerConfig.stream().filter(config -> !codes.contains(config.getId().getInteractedServiceCode()))
                    .forEach(config -> {
                        if (config.getServiceStatus().equals(ServiceStatus.REJECTED.value())) {
                            newRejectedErrorList.add(createNewError(config));// need to add error
                        }
                    });
        }
    }

    private void validateModuleBaseError(Result result, List<Error> approvedErrorList, Error error,
                                         List<Error> newRejectedErrorList, List<PcDrugToDrug> payerConfig,
                                         String requestId, List<CustomizationAuditTrail> auditTrailList,
                                         List<String> ruleIdList) {
        List<PcDrugToDrug> specificPayers = filterPayerList(payerConfig);
        if (!specificPayers.isEmpty()) {
            payerConfig.removeAll(specificPayers);
            populateConfigBasedOnModuleName(result, approvedErrorList, error, newRejectedErrorList,
                    specificPayers, requestId, auditTrailList, ruleIdList);
        }
        populateConfigBasedOnModuleName(result, approvedErrorList, error, newRejectedErrorList,
                payerConfig, requestId, auditTrailList, ruleIdList);
    }

    private void populateConfigBasedOnModuleName(Result result, List<Error> approvedErrorList, Error error,
                                                 List<Error> primaryDrugErrorList, List<PcDrugToDrug> payerConfig,
                                                 String requestId, List<CustomizationAuditTrail> auditTrailList,
                                                 List<String> ruleIdList) {
        // Need add checkedServiceList to not override module response - FDB/IDF and ALL
        List<String> checkedServiceList = new ArrayList<>();
        if (!payerConfig.isEmpty()) {
            payerConfig.forEach(config -> {
                // Need to set all error in primary drug FDB and PC
                if (!checkedServiceList.contains(config.getId().getServiceCode() + config.getId().getInteractedServiceCode())) {
                    checkedServiceList.add(config.getId().getServiceCode() + config.getId().getInteractedServiceCode());
                    populateErrors(result, config, error, approvedErrorList, primaryDrugErrorList);
                    commonMethods.saveDataToAuditTrailList(requestId, config.getRuleId(), auditTrailList, ruleIdList);
                }
            });
        }
    }

    private void populateErrors(Result result, PcDrugToDrug config, Error error,
                                List<Error> approvedErrorList, List<Error> primaryDrugErrorList) {
        if (result.getNdcDrugCode().equals(config.getId().getServiceCode())) {
            Matcher matcher = matchPattern(config.getId().getInteractedServiceCode(), error.getDescription());
            if (matcher.find()) {
                matcher.reset();//need it to reset otherwise it store old value if we print matcher.find() anywhere
                if (config.getServiceStatus().equals(ServiceStatus.APPROVED.value())) {
                    approvedErrorList.add(error);// need to remove error
                } else {
                    primaryDrugErrorList.add(error);// need to add error
                }
            }
        } else {
            if (config.getServiceStatus().equals(ServiceStatus.REJECTED.value())) {
                primaryDrugErrorList.add(createNewError(config));// need to add error
            }
        }
    }

    private Matcher matchPattern(String interactedServiceCode, String errorDescription) {
        return Pattern.compile("with " + interactedServiceCode + " ", Pattern.CASE_INSENSITIVE)
                .matcher(errorDescription);
    }

    private Error createNewError(PcDrugToDrug config) {
        Error pcError = new Error();
        pcError.setCode(PCModuleCode.PC_DRUG_TO_DRUG.code());
        if (!StringUtils.isBlank(config.getAdditionalRejectionReason())) {
            pcError.setDescription(config.getAdditionalRejectionReason());
        } else {
            pcError.setDescription(getCommonRejectionReasonMsg(config.getId().getServiceCode(),
                    config.getId().getInteractedServiceCode()));

        }
        return pcError;
    }

    private List<PcDrugToDrug> payerConfigDetailsBasedonModuleName(String payerId, String serviceCode,
                                                                   String moduleName, List<Result> results) {
        return drugToDrugRepository.findByPayerIdAndServiceCodeAndModuleName(payerId, moduleName, serviceCode,
                getInteractedServiceCodes(results, serviceCode));
    }

    private List<PcDrugToDrug> payerConfigDetailsWithoutModuleName(String payerId, String serviceCode,
                                                                   List<Result> results) {
        return drugToDrugRepository.findByPayerIdAndServiceCode(payerId, serviceCode,
                getInteractedServiceCodes(results, serviceCode), ServiceStatus.REJECTED.value());
    }

    private List<PcDrugToDrug> filterPayerList(List<PcDrugToDrug> payerConfig) {
        if (!payerConfig.isEmpty()) {
            return payerConfig.stream()
                    .filter(config -> !config.getId().getPayerId().equals(PayerCode.ALL_PAYER.value()))
                    .collect(Collectors.toList());
        }
        return payerConfig;
    }

    private String getCommonRejectionReasonMsg(String serviceCode, String interactedServiceCode) {
        return commonRejectionReasonRepository.findByRejectionCode(PCModuleCode.PC_DRUG_TO_DRUG.code())
                .replace("<DrugName> (<DrugCode>)", serviceCode)
                .replace("<InteractedDrugName> (<InteractedDrugCode>)", interactedServiceCode);
    }
}
