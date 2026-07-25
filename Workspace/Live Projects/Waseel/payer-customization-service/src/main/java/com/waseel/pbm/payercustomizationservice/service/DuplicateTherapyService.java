package com.waseel.pbm.payercustomizationservice.service;

import com.waseel.pbm.payercustomizationservice.common.CommonMethods;
import com.waseel.pbm.payercustomizationservice.enums.*;
import com.waseel.pbm.payercustomizationservice.model.Error;
import com.waseel.pbm.payercustomizationservice.model.*;
import com.waseel.pbm.payercustomizationservice.persist.CustomizationAuditTrail;
import com.waseel.pbm.payercustomizationservice.persist.PCDuplicateTherapy;
import com.waseel.pbm.payercustomizationservice.repository.CommonRejectionReasonRepository;
import com.waseel.pbm.payercustomizationservice.repository.PCDuplicateTherapyRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DuplicateTherapyService {

    @Autowired
    private PCDuplicateTherapyRepository duplicateTherapyRepository;

    @Autowired
    private CommonRejectionReasonRepository commonRejectionReasonRepository;

    @Autowired
    private CommonMethods commonMethods;

    public DssResponse manageCustomizationDuplicateTherapy(PCRequest pcRequest) {
        DssRequest dssRequest = pcRequest.getDssRequest();
        DssResponse dssResponse = pcRequest.getDssResponse();
        List<Result> resultList = dssResponse.getResults();
        List<CustomizationAuditTrail> auditTrailList = new ArrayList<>();
        List<String> ruleIdList = new ArrayList<>();
        resultList.forEach(result -> {
            List<Error> newErrorList = new ArrayList<>();
            if (result.getStatus().equalsIgnoreCase(ServiceStatus.REJECTED.value())) {
                newErrorList.addAll(result.getErrors());
                manageRejectedService(result, dssRequest.getPayerId(), resultList, newErrorList,
                        dssRequest.getRequestId(), auditTrailList, ruleIdList);
            } else {
                manageApprovedService(result, dssRequest.getPayerId(), resultList, newErrorList,
                        dssRequest.getRequestId(), auditTrailList, ruleIdList);
            }
            result.setErrors(newErrorList);
        });
        resultList.forEach(result -> {
            if (result.getErrors().isEmpty()) {
                result.setStatus(ServiceStatus.APPROVED.value());
                result.setErrors(null);
            }
        });
        commonMethods.saveDataToAuditTrail(auditTrailList);
        return dssResponse;
    }

    private void manageRejectedService(Result result, String payerId, List<Result> resultList,
                                       List<Error> newErrorList, String requestId,
                                       List<CustomizationAuditTrail> auditTrailList, List<String> ruleIdList) {
        result.getErrors().forEach(error -> {
            if (error.getCode().equals(RejectionCode.FDB_DUPLICATE_THERAPY.code())) {
                populateConfigBasedOnModuleName(result, payerId, error, ModuleName.FDB.value(), resultList,
                        newErrorList, requestId, auditTrailList, ruleIdList);
            } else if (error.getCode().equals(RejectionCode.IDF_DUPLICATE_THERAPY.code())) {
                populateConfigBasedOnModuleName(result, payerId, error, ModuleName.IDF.value(), resultList,
                        newErrorList, requestId, auditTrailList, ruleIdList);
            }
        });
    }

    private void populateConfigBasedOnModuleName(Result result, String payerId,
                                                 Error error, String moduleName,
                                                 List<Result> resultList, List<Error> newErrorList, String requestId,
                                                 List<CustomizationAuditTrail> auditTrailList, List<String> ruleIdList) {
        String serviceCode = result.getNdcDrugCode();
        List<PCDuplicateTherapy> payerConfig = rejectedPayerConfigDetails(payerId,
                serviceCode, moduleName);
        if (!payerConfig.isEmpty()) {
            filterPayerList(newErrorList, resultList, error, payerConfig, serviceCode, ServiceStatus.REJECTED.value()
                    , requestId, auditTrailList, ruleIdList);
        }
    }

    private void manageApprovedService(Result result, String payerId, List<Result> resultList,
                                       List<Error> newErrorList, String requestId,
                                       List<CustomizationAuditTrail> auditTrailList, List<String> ruleIdList) {
        String serviceCode = result.getNdcDrugCode();
        List<PCDuplicateTherapy> payerConfig = approvedPayerConfigDetails(payerId, serviceCode);
        if (!payerConfig.isEmpty()) {
            filterPayerList(newErrorList, resultList, null, payerConfig, serviceCode,
                    ServiceStatus.APPROVED.value(), requestId, auditTrailList, ruleIdList);
        }
    }

    private void filterPayerList(List<Error> newErrorList, List<Result> resultList, Error error,
                                 List<PCDuplicateTherapy> payerConfig, String serviceCode,
                                 String serviceStatus, String requestId,
                                 List<CustomizationAuditTrail> auditTrailList, List<String> ruleIdList) {
        List<PCDuplicateTherapy> specificConfig = payerConfig.stream()
                .filter(c -> !c.getId().getPayerId().equals(PayerCode.ALL_PAYER.value()))
                .collect(Collectors.toList());
        if (!specificConfig.isEmpty()) {
            manageCommonError(newErrorList, resultList, error, specificConfig, serviceCode, serviceStatus,
                    requestId, auditTrailList, ruleIdList);
            payerConfig.removeAll(specificConfig);
        }
        if (!payerConfig.isEmpty())
            manageCommonError(newErrorList, resultList, error, payerConfig, serviceCode, serviceStatus,
                    requestId, auditTrailList, ruleIdList);
    }

    private void manageCommonError(List<Error> newErrorList, List<Result> resultList, Error error,
                                   List<PCDuplicateTherapy> payerConfig, String serviceCode,
                                   String serviceStatus, String requestId,
                                   List<CustomizationAuditTrail> auditTrailList, List<String> ruleIdList) {
        List<String> dataList = new ArrayList<>();
        payerConfig.forEach(config -> {
            String interactedServiceCode = config.getId().getInteractedServiceCode();
            if (!dataList.contains(serviceCode + interactedServiceCode)) {
                dataList.add(serviceCode + interactedServiceCode);
                Result r = resultList.stream()
                        .filter(r1 -> interactedServiceCode.equals(r1.getNdcDrugCode()))
                        .findAny()
                        .orElse(null);
                if (r != null) {
                    if (config.getServiceStatus().equals(ServiceStatus.REJECTED.value())
                            && (error == null || !error.getDescription().endsWith(interactedServiceCode))) {
                        Error error1 = new Error();
                        error1.setCode(PCModuleCode.PC_DUPLICATE_THERAPY.code());
                        if (!StringUtils.isBlank(config.getAdditionalRejectionReason())) {
                            error1.setDescription(config.getAdditionalRejectionReason());
                        } else {
                            error1.setDescription(getCommonRejectionReasonMsg(serviceCode, interactedServiceCode));
                        }
                        newErrorList.add(error1);
                        commonMethods.saveDataToAuditTrailList(requestId, config.getRuleId(), auditTrailList, ruleIdList);
                    } else if (serviceStatus.equals(ServiceStatus.REJECTED.value())
                            && config.getServiceStatus().equals(ServiceStatus.APPROVED.value())
                            && error.getDescription().endsWith(interactedServiceCode)) {
                        newErrorList.remove(error);
                        commonMethods.saveDataToAuditTrailList(requestId, config.getRuleId(), auditTrailList, ruleIdList);
                    }
                }
            }
        });
    }

    private List<PCDuplicateTherapy> rejectedPayerConfigDetails(String payerId, String serviceCode,
                                                                String moduleName) {
        return duplicateTherapyRepository.findByPayerIdAndServiceCodeAndModuleName(payerId, serviceCode, moduleName);
    }

    private List<PCDuplicateTherapy> approvedPayerConfigDetails(String payerId, String serviceCode) {
        return duplicateTherapyRepository.findByPayerIdAndServiceCode(payerId, serviceCode);
    }

    private String getCommonRejectionReasonMsg(String serviceCode, String interactedServiceCode) {
        return commonRejectionReasonRepository.findByRejectionCode(PCModuleCode.PC_DUPLICATE_THERAPY.code())
                .replace("<DrugName> (<DrugCode>)", serviceCode)
                .replace("<DrugName> (<ConcurrentDrugCode>)", interactedServiceCode);
    }
}
