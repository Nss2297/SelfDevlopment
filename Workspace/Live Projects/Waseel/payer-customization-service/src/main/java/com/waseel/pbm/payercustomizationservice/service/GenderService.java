package com.waseel.pbm.payercustomizationservice.service;

import com.waseel.pbm.payercustomizationservice.common.CommonMethods;
import com.waseel.pbm.payercustomizationservice.enums.*;
import com.waseel.pbm.payercustomizationservice.model.Error;
import com.waseel.pbm.payercustomizationservice.model.*;
import com.waseel.pbm.payercustomizationservice.persist.CustomizationAuditTrail;
import com.waseel.pbm.payercustomizationservice.persist.PCGender;
import com.waseel.pbm.payercustomizationservice.repository.CommonRejectionReasonRepository;
import com.waseel.pbm.payercustomizationservice.repository.PCGenderRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenderService {

    @Autowired
    private PCGenderRepository pcGenderRepository;

    @Autowired
    private CommonRejectionReasonRepository commonRejectionReasonRepository;

    @Autowired
    private CommonMethods commonMethods;

    public DssResponse manageCustomizationGender(PCRequest pcRequest) {
        DssRequest dssRequest = pcRequest.getDssRequest();
        DssResponse dssResponse = pcRequest.getDssResponse();
        List<CustomizationAuditTrail> auditTrailList = new ArrayList<>();
        List<String> ruleIdList = new ArrayList<>();
        dssResponse.getResults().forEach(result -> {
            if (result.getStatus().equalsIgnoreCase(ServiceStatus.REJECTED.value())) {
                manageRejectedService(result, dssRequest.getPayerId(), dssRequest.getMemberGender(),
                        dssRequest.getRequestId(), auditTrailList, ruleIdList);
            } else {
                manageApprovedService(result, dssRequest.getPayerId(), dssRequest.getMemberGender(),
                        dssRequest.getRequestId(), auditTrailList, ruleIdList);
            }
        });
        commonMethods.saveDataToAuditTrail(auditTrailList);
        return dssResponse;
    }

    private void manageRejectedService(Result result, String payerId, String gender, String requestId,
                                       List<CustomizationAuditTrail> auditTrailList, List<String> ruleIdList) {
        List<Error> approvedErrorList = new ArrayList<>();
        result.getErrors().forEach(error -> {
            if (error.getCode().equals(RejectionCode.FDB_GENDER.code())) {
                populateConfigBasedOnModuleName(result, payerId, gender, error, approvedErrorList,
                        ModuleName.FDB.value(), requestId, auditTrailList, ruleIdList);
            } else if (error.getCode().equals(RejectionCode.IDF_GENDER.code())) {
                populateConfigBasedOnModuleName(result, payerId, gender, error, approvedErrorList,
                        ModuleName.IDF.value(), requestId, auditTrailList, ruleIdList);
            }
        });
        if (!approvedErrorList.isEmpty()) {
            result.getErrors().removeAll(approvedErrorList);
            if (result.getErrors().isEmpty()) {
                result.setStatus(ServiceStatus.APPROVED.value());
                result.setErrors(null);
            }
        }
    }

    private void populateConfigBasedOnModuleName(Result result, String payerId, String gender,
                                                 Error error, List<Error> approvedErrorList,
                                                 String moduleName, String requestId,
                                                 List<CustomizationAuditTrail> auditTrailList, List<String> ruleIdList) {
		List<PCGender> payerConfig = rejectedPayerConfigDetails(payerId, result.getNdcDrugCode(), gender, moduleName,
				result.getScientificCode());
        if (!payerConfig.isEmpty()) {
            commonMethods.saveDataToAuditTrailList(requestId, payerConfig.get(0).getRuleId(), auditTrailList, ruleIdList);
            approvedErrorList.add(error);
        }
    }

    private void manageApprovedService(Result result, String payerId, String gender, String requestId,
                                       List<CustomizationAuditTrail> auditTrailList, List<String> ruleIdList) {
        List<PCGender> payerConfig = approvedPayerConfigDetails(payerId, result.getNdcDrugCode(), gender,
        		result.getScientificCode());
        if (!payerConfig.isEmpty()) {
            PCGender config = payerConfig.stream().filter(m -> !m.getPayerId()
                    .equals(PayerCode.ALL_PAYER.value())).findAny().orElse(payerConfig.get(0));
            List<Error> updatedErrorList = new ArrayList<>();
            result.setStatus(ServiceStatus.REJECTED.value());
            Error error = new Error();
            error.setCode(PCModuleCode.PC_GENDER.code());
            if (!StringUtils.isBlank(config.getAdditionalRejectionReason())) {
                error.setDescription(config.getAdditionalRejectionReason());
            } else {
                error.setDescription(getCommonRejectionReasonMsg(result.getNdcDrugCode(),result.getScientificCode(), gender));
            }
            updatedErrorList.add(error);
            result.setErrors(updatedErrorList);
            commonMethods.saveDataToAuditTrailList(requestId, config.getRuleId(), auditTrailList, ruleIdList);
        }
    }

	private List<PCGender> rejectedPayerConfigDetails(String payerId, String serviceCode, String gender,
			String moduleName, String scientificCode) {
		if (StringUtils.isNotBlank(serviceCode)) {
			return pcGenderRepository.findByPayerIdAndServiceCodeAndModuleNameAndGender(payerId, serviceCode,
					moduleName, ServiceStatus.APPROVED.value(), gender);
		}
		return pcGenderRepository.findByPayerIdAndScientificCodeAndModuleNameAndGender(payerId, scientificCode,
				moduleName, ServiceStatus.APPROVED.value(), gender);
	}

	private List<PCGender> approvedPayerConfigDetails(String payerId, String serviceCode, String gender,
			String scientificCode) {
		if (StringUtils.isNotBlank(serviceCode)) {
			return pcGenderRepository.findByPayerIdAndServiceCodeAndGender(payerId, serviceCode,
					ServiceStatus.REJECTED.value(), gender);
		}
		return pcGenderRepository.findByPayerIdAndScientificCodeAndGender(payerId, scientificCode,
				ServiceStatus.REJECTED.value(), gender);
	}

    private String getCommonRejectionReasonMsg(String serviceCode, String scientificCode,String gender) {
    	String code = StringUtils.isNotBlank(serviceCode) ? serviceCode : scientificCode;
        gender = gender.equalsIgnoreCase("MALE") ? "FEMALE" : "MALE";
        return commonRejectionReasonRepository.findByRejectionCode(PCModuleCode.PC_GENDER.code())
                .replace("<Condition>", gender + "_EXCLUSIVE")
                .replace("<DrugName> (<DrugCode>)", code);
    }

}
