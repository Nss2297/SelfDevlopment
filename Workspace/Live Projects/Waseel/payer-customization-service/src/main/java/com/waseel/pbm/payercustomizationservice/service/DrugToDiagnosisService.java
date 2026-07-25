package com.waseel.pbm.payercustomizationservice.service;

import com.waseel.pbm.payercustomizationservice.common.CommonMethods;
import com.waseel.pbm.payercustomizationservice.enums.*;
import com.waseel.pbm.payercustomizationservice.model.Error;
import com.waseel.pbm.payercustomizationservice.model.*;
import com.waseel.pbm.payercustomizationservice.persist.CustomizationAuditTrail;
import com.waseel.pbm.payercustomizationservice.persist.PCDrugToDiagnosis;
import com.waseel.pbm.payercustomizationservice.repository.CommonRejectionReasonRepository;
import com.waseel.pbm.payercustomizationservice.repository.PCDrugToDiagnosisRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DrugToDiagnosisService {

    @Autowired
    private PCDrugToDiagnosisRepository configurationRepository;

    @Autowired
    private CommonRejectionReasonRepository commonRejectionReasonRepository;

    @Autowired
    private CommonMethods commonMethods;

    public DssResponse manageCustomizationDrugToDiagnosis(PCRequest pcRequest) {
        DssResponse dssResponse = pcRequest.getDssResponse();
        DssRequest dssRequest = pcRequest.getDssRequest();
        List<Result> dssResult = dssResponse.getResults();
        List<Result> finalResult = new ArrayList<>();
        List<CustomizationAuditTrail> auditTrailList = new ArrayList<>();
        List<String> ruleIdList = new ArrayList<>();
        var wrapper = new Object() {
            List<PCDrugToDiagnosis> allPayerConfig = null;
        };
        dssResponse.getResults().forEach(result -> {
            List<PCDrugToDiagnosis> payerConfig = getPayerConfigDetails(dssRequest.getPayerId(),
                    result.getNdcDrugCode(), dssRequest.getIcdCodes());
            if (payerConfig == null || payerConfig.isEmpty()) {
                wrapper.allPayerConfig = getPayerConfigDetails(PayerCode.ALL_PAYER.value(), result.getNdcDrugCode(),
                        dssRequest.getIcdCodes());
            }
            if (result.getErrors() != null && !result.getErrors().isEmpty()) {
                manageRejectedService(result, payerConfig, wrapper.allPayerConfig, finalResult,
                        dssRequest.getRequestId(), auditTrailList, ruleIdList);
            } else {
                manageApprovedService(result, payerConfig, wrapper.allPayerConfig, finalResult,
                        dssRequest.getRequestId(), auditTrailList, ruleIdList, dssRequest.getIcdCodes());
            }
        });
        if (dssResult.size() > finalResult.size()) {
            dssResult.removeAll(finalResult);
            finalResult.addAll(dssResult);
        }
        commonMethods.saveDataToAuditTrail(auditTrailList);
        DssResponse response = new DssResponse();
        response.setResults(finalResult);
        response.setStatus(setRequestStatus(finalResult));
        return response;
    }

    private void manageRejectedService(Result result, List<PCDrugToDiagnosis> payerConfig,
                                       List<PCDrugToDiagnosis> allPayerConfig, List<Result> finalResult, String requestId,
                                       List<CustomizationAuditTrail> auditTrailList, List<String> ruleIdList) {
        List<Error> updatedErrorList = new ArrayList<>();
        result.getErrors().forEach(error -> {
            if (error.getCode().equals(RejectionCode.IDF_INDICATION.code())
                    || error.getCode().equals(RejectionCode.IDF_CONTRAINDICATION.code())
                    || error.getCode().equals(RejectionCode.FDB_INDICATION.code())
                    || error.getCode().equals(RejectionCode.FDB_CONTRAINDICATION.code())) {
                if (payerConfig != null && !payerConfig.isEmpty()) {
                    updatedErrorList.addAll(validateConfigurationForRejectedService(payerConfig, result, error,
                            requestId, auditTrailList, ruleIdList));
                } else if (allPayerConfig != null && !allPayerConfig.isEmpty()) {
                    updatedErrorList.addAll(validateConfigurationForRejectedService(allPayerConfig, result, error,
                            requestId, auditTrailList, ruleIdList));
                }
            }
        });
        if (!updatedErrorList.isEmpty()) {
            result.getErrors().removeAll(updatedErrorList);
            if (result.getErrors().isEmpty()) {
                result.setStatus(ServiceStatus.APPROVED.value());
                result.setErrors(null);
            }
        }
        finalResult.add(result);
    }

    private void manageApprovedService(Result result, List<PCDrugToDiagnosis> payerConfig,
                                       List<PCDrugToDiagnosis> allPayerConfig, List<Result> finalResult, String requestId,
                                       List<CustomizationAuditTrail> auditTrailList, List<String> ruleIdList, List<String> dssIcdCodes) {
        // APPROVED
        if ((payerConfig != null && !payerConfig.isEmpty())) {
            finalResult.add(validateConfigurationForApprovedService(payerConfig, result, requestId, auditTrailList,
                    ruleIdList, dssIcdCodes));
        } else if (allPayerConfig != null && !allPayerConfig.isEmpty()) {
            finalResult.add(validateConfigurationForApprovedService(allPayerConfig, result, requestId, auditTrailList,
                    ruleIdList, dssIcdCodes));
        }
    }

    private String setRequestStatus(List<Result> drugValidationResultList) {
        List<String> servicesStatusList = drugValidationResultList.stream().map(Result::getStatus)
                .collect(Collectors.toList());
        if (servicesStatusList.stream().distinct().count() <= 1)
            return servicesStatusList.get(0);
        return RequestStatus.PARTIAL_APPROVED.value();
    }

    private List<Error> validateConfigurationForRejectedService(List<PCDrugToDiagnosis> payerConfig, Result result,
                                                                Error error, String requestId, List<CustomizationAuditTrail> auditTrailList, List<String> ruleIdList) {
        List<Error> errorList = new ArrayList<>();
        List<PCDrugToDiagnosis> idfList = new ArrayList<>();
        List<PCDrugToDiagnosis> fdbList = new ArrayList<>();
        List<PCDrugToDiagnosis> allList = new ArrayList<>();
        addConfigAccordingToModuleName(payerConfig, idfList, fdbList, allList);
        payerConfig.forEach(config -> {
            if (result.getNdcDrugCode().equals(config.getServiceCode())
                    && !StringUtils.isBlank(config.getRejectionCategory())
                    && !config.getServiceStatus().equalsIgnoreCase(result.getStatus())
                    && config.getServiceStatus().equalsIgnoreCase(ServiceStatus.APPROVED.value())
                    && (checkDiagnosisIndication(config, error, idfList, fdbList, allList)
                    || checkDiagnosisContraIndication(config, error, idfList, fdbList, allList)
                    || checkALLDiagnosisCases(config, error, idfList, fdbList, allList))) {
                commonMethods.saveDataToAuditTrailList(requestId, config.getRuleId(), auditTrailList, ruleIdList);
                errorList.add(error);
            }
        });
        return errorList;
    }

    private void addConfigAccordingToModuleName(List<PCDrugToDiagnosis> payerConfig, List<PCDrugToDiagnosis> idfList,
                                                List<PCDrugToDiagnosis> fdbList, List<PCDrugToDiagnosis> allList) {
        // In PayerConfig we get all data so need to specify by moduleName

        payerConfig.forEach(config -> {
            if (config.getModuleName().equals(ModuleName.IDF.value())) {
                idfList.add(config);
            } else if (config.getModuleName().equals(ModuleName.FDB.value())) {
                fdbList.add(config);
            } else if (config.getModuleName().equals(ModuleName.ALL.value())) {
                allList.add(config);
            }
        });
    }

    private boolean checkDiagnosisIndication(PCDrugToDiagnosis config, Error error, List<PCDrugToDiagnosis> idfList,
                                             List<PCDrugToDiagnosis> fdbList, List<PCDrugToDiagnosis> allList) {
        return (config.getRejectionCategory().equalsIgnoreCase(RejectionReason.DIAGNOSIS_INDICATION.value())
                && ((idfList != null && !idfList.isEmpty()
                && config.getModuleName().equalsIgnoreCase(ModuleName.IDF.value())
                && error.getCode().equals(RejectionCode.IDF_INDICATION.code()))
                || (fdbList != null && !fdbList.isEmpty()
                && config.getModuleName().equalsIgnoreCase(ModuleName.FDB.value())
                && error.getCode().equals(RejectionCode.FDB_INDICATION.code()))
                || (allList != null && !allList.isEmpty()
                && ((config.getModuleName().equalsIgnoreCase(ModuleName.ALL.value())
                && error.getCode().equals(RejectionCode.IDF_INDICATION.code()))
                || (config.getModuleName().equalsIgnoreCase(ModuleName.ALL.value())
                && error.getCode().equals(RejectionCode.FDB_INDICATION.code())))))

        );
    }

    private boolean checkDiagnosisContraIndication(PCDrugToDiagnosis config, Error error,
                                                   List<PCDrugToDiagnosis> idfList, List<PCDrugToDiagnosis> fdbList, List<PCDrugToDiagnosis> allList) {
        return (config.getRejectionCategory().equalsIgnoreCase(RejectionReason.DIAGNOSIS_CONTRAINDICATION.value())
                && error.getDescription().contains(" " + config.getIcdCode())
                && ((idfList != null && !idfList.isEmpty()
                && config.getModuleName().equalsIgnoreCase(ModuleName.IDF.value())
                && error.getCode().equals(RejectionCode.IDF_CONTRAINDICATION.code()))
                || (fdbList != null && !fdbList.isEmpty()
                && config.getModuleName().equalsIgnoreCase(ModuleName.FDB.value())
                && error.getCode().equals(RejectionCode.FDB_CONTRAINDICATION.code()))
                || (allList != null && !allList.isEmpty()
                && ((config.getModuleName().equalsIgnoreCase(ModuleName.ALL.value())
                && error.getCode().equals(RejectionCode.IDF_CONTRAINDICATION.code()))
                || (config.getModuleName().equalsIgnoreCase(ModuleName.ALL.value())
                && error.getCode()
                .equals(RejectionCode.FDB_CONTRAINDICATION.code()))))));
    }

    private boolean checkALLDiagnosisCases(PCDrugToDiagnosis config, Error error, List<PCDrugToDiagnosis> idfList,
                                           List<PCDrugToDiagnosis> fdbList, List<PCDrugToDiagnosis> allList) {

        return (config.getRejectionCategory().equalsIgnoreCase(RejectionReason.ALL.value())
                && ((config.getModuleName().equalsIgnoreCase(ModuleName.IDF.value()) && idfList != null
                && !idfList.isEmpty()
                && ((error.getCode().equals(RejectionCode.IDF_CONTRAINDICATION.code())
                && error.getDescription().contains(" " + config.getIcdCode()))
                || error.getCode().equals(RejectionCode.IDF_INDICATION.code())))

                || (config.getModuleName().equalsIgnoreCase(ModuleName.FDB.value()) && fdbList != null
                && !fdbList.isEmpty()
                && ((error.getCode().equals(RejectionCode.FDB_CONTRAINDICATION.code())
                && error.getDescription().contains(" " + config.getIcdCode()))
                || error.getCode().equals(RejectionCode.FDB_INDICATION.code())))

                || (config.getModuleName().equalsIgnoreCase(ModuleName.ALL.value()) && allList != null
                && !allList.isEmpty()
                && (((error.getCode().equals(RejectionCode.FDB_CONTRAINDICATION.code())
                || error.getCode().equals(RejectionCode.IDF_CONTRAINDICATION.code()))
                && error.getDescription().contains(" " + config.getIcdCode()))
                || (error.getCode().equals(RejectionCode.FDB_INDICATION.code())
                || error.getCode().equals(RejectionCode.IDF_INDICATION.code()))))));
    }

    private Result validateConfigurationForApprovedService(List<PCDrugToDiagnosis> payerConfig, Result result,
                                                           String requestId, List<CustomizationAuditTrail> auditTrailList, List<String> ruleIdList,
                                                           List<String> dssIcdCodes) {
        List<Error> errorList = new ArrayList<>();
        List<Error> errorListForIndication = new ArrayList<>();
        payerConfig.forEach(config -> {
            if (result.getNdcDrugCode().equals(config.getServiceCode())) {
                Error error = new Error();
                if ((!result.getStatus().equalsIgnoreCase(config.getServiceStatus()))
                        && !StringUtils.isBlank(config.getRejectionCategory())
                        && (config.getServiceStatus().equalsIgnoreCase(ServiceStatus.REJECTED.value()))) {
                    if (config.getRejectionCategory().equalsIgnoreCase(RejectionReason.DIAGNOSIS_INDICATION.value())) {
                        error.setCode(PCModuleCode.PC_DRUGTODIAGNOSIS_INDICATION.code());
                        error.setDescription(getErrorDesc(config, PCModuleCode.PC_DRUGTODIAGNOSIS_INDICATION.code()));
                        errorListForIndication.add(error);
                    } else if (config.getRejectionCategory()
                            .equalsIgnoreCase(RejectionReason.DIAGNOSIS_CONTRAINDICATION.value())) {
                        error.setCode(PCModuleCode.PC_DRUGTODIAGNOSIS_CONTRAINDICATION.code());
                        error.setDescription(
                                getErrorDesc(config, PCModuleCode.PC_DRUGTODIAGNOSIS_CONTRAINDICATION.code()));
                        errorList.add(error);
                    } else {
                        // ALL
                        Error errorContraIndication = new Error();
                        error.setCode(PCModuleCode.PC_DRUGTODIAGNOSIS_INDICATION.code());
                        errorContraIndication.setCode(PCModuleCode.PC_DRUGTODIAGNOSIS_CONTRAINDICATION.code());
                        error.setDescription(getErrorDesc(config, PCModuleCode.PC_DRUGTODIAGNOSIS_INDICATION.code()));
                        errorContraIndication.setDescription(
                                getErrorDesc(config, PCModuleCode.PC_DRUGTODIAGNOSIS_CONTRAINDICATION.code()));
                        errorList.add(errorContraIndication);
                        errorListForIndication.add(error);
                    }
                    commonMethods.saveDataToAuditTrailList(requestId, config.getRuleId(), auditTrailList, ruleIdList);
                }
            }
        });

        if (!errorListForIndication.isEmpty() && dssIcdCodes.size() == errorListForIndication.size()) {
            //If drug is Rejected for all dss icdCode then only it should rejected.
            errorList.addAll(errorListForIndication);
        }
        if (!errorList.isEmpty()) {
            result.setErrors(errorList);
            result.setStatus(ServiceStatus.REJECTED.value());
        }
        return result;
    }

    private String getErrorDesc(PCDrugToDiagnosis config, String moduleCode) {
        if (!StringUtils.isBlank(config.getAdditionalRejectionReason())) {
            return config.getAdditionalRejectionReason();
        } else {
            return getCommonRejectionReasonMsg(config.getServiceCode(), config.getIcdCode(),
                    moduleCode);
        }
    }

    private List<PCDrugToDiagnosis> getPayerConfigDetails(String payerId, String serviceCode, List<String> icdCodes) {
        return configurationRepository.findByPayerIdAndServiceCodeAndIcdCodes(payerId, serviceCode, icdCodes);
    }

    private String getCommonRejectionReasonMsg(String serviceCode, String icdCode, String moduleCode) {
        return commonRejectionReasonRepository.findByRejectionCode(moduleCode)
                .replace("<DrugName> (<DrugCode>)", serviceCode).replace("<ICD>", icdCode);
    }

}
