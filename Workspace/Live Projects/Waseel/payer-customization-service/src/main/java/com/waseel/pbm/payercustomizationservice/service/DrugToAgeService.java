package com.waseel.pbm.payercustomizationservice.service;

import com.waseel.pbm.payercustomizationservice.common.CommonMethods;
import com.waseel.pbm.payercustomizationservice.enums.*;
import com.waseel.pbm.payercustomizationservice.model.Error;
import com.waseel.pbm.payercustomizationservice.model.*;
import com.waseel.pbm.payercustomizationservice.persist.CustomizationAuditTrail;
import com.waseel.pbm.payercustomizationservice.persist.PCAge;
import com.waseel.pbm.payercustomizationservice.repository.CommonRejectionReasonRepository;
import com.waseel.pbm.payercustomizationservice.repository.PCAgeRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DrugToAgeService {

    @Autowired
    private PCAgeRepository pcAgeRepository;

    @Autowired
    private PatientAgeConverterService patientAgeConverterService;

    @Autowired
    private CommonRejectionReasonRepository commonRejectionReasonRepository;

    @Autowired
    private CommonMethods commonMethods;

    public DssResponse manageCustomizationAge(PCRequest pcRequest) {
        DssRequest dssRequest = pcRequest.getDssRequest();
        DssResponse dssResponse = pcRequest.getDssResponse();
        List<CustomizationAuditTrail> auditTrailList = new ArrayList<>();
        List<String> ruleIdList = new ArrayList<>();
        dssResponse.getResults().forEach(result -> {
            if (result.getStatus().equalsIgnoreCase(ServiceStatus.REJECTED.value())) {
                manageRejectedService(result, dssRequest.getPayerId(), dssRequest.getDateOfBirth(),
                        dssRequest.getRequestId(), auditTrailList, ruleIdList);
            } else {
                manageApprovedService(result, dssRequest.getPayerId(), dssRequest.getDateOfBirth(),
                        dssRequest.getRequestId(), auditTrailList, ruleIdList);
            }
        });
        commonMethods.saveDataToAuditTrail(auditTrailList);
        return dssResponse;
    }

    private void manageRejectedService(Result result, String payerId, String dateOfBirth, String requestId,
                                       List<CustomizationAuditTrail> auditTrailList, List<String> ruleIdList) {
        List<Error> approvedErrorList = new ArrayList<>();
        result.getErrors().forEach(error -> {
            if (error.getCode().equals(RejectionCode.FDB_AGE.code())) {
                populateConfigBasedOnModuleName(result, payerId, dateOfBirth, error, approvedErrorList,
                        ModuleName.FDB.value(), requestId, auditTrailList, ruleIdList);
            } else if (error.getCode().equals(RejectionCode.IDF_AGE.code())) {
                populateConfigBasedOnModuleName(result, payerId, dateOfBirth, error, approvedErrorList,
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

    private void populateConfigBasedOnModuleName(Result result, String payerId, String dateOfBirth, Error error,
                                                 List<Error> approvedErrorList, String moduleName, String requestId,
                                                 List<CustomizationAuditTrail> auditTrailList, List<String> ruleIdList) {
        List<PCAge> payerConfig = payerConfigDetailsBasedonModuleName(payerId, result.getNdcDrugCode(), dateOfBirth,
                moduleName,result.getScientificCode());
        if (!payerConfig.isEmpty()) {
            commonMethods.saveDataToAuditTrailList(requestId, payerConfig.get(0).getRuleId(), auditTrailList, ruleIdList);
            approvedErrorList.add(error);
        }
    }

    private void manageApprovedService(Result result, String payerId, String dateOfBirth, String requestId,
                                       List<CustomizationAuditTrail> auditTrailList, List<String> ruleIdList) {
        List<PCAge> payerConfig = payerConfigDetailsWithoutModuleName(payerId, result.getNdcDrugCode(), dateOfBirth,
        		result.getScientificCode());
        if (!payerConfig.isEmpty()) {
            PCAge config = payerConfig.stream().filter(m -> !m.getPayerId().equals(PayerCode.ALL_PAYER.value()))
                    .findAny().orElse(payerConfig.get(0));
            List<Error> updatedErrorList = new ArrayList<>();
            result.setStatus(ServiceStatus.REJECTED.value());
            Error error = new Error();
            error.setCode(PCModuleCode.PC_AGE.code());
            if (!StringUtils.isBlank(config.getAdditionalRejectionReason())) {
                error.setDescription(config.getAdditionalRejectionReason());
            } else {
                error.setDescription(getCommonRejectionReasonMsg(result.getNdcDrugCode(),result.getScientificCode()));
            }
            updatedErrorList.add(error);
            result.setErrors(updatedErrorList);
            commonMethods.saveDataToAuditTrailList(requestId, config.getRuleId(), auditTrailList, ruleIdList);
        }
    }

    private String getCommonRejectionReasonMsg(String serviceCode,String scientificCode) {
    	String code = StringUtils.isNotBlank(serviceCode) ? serviceCode : scientificCode;
        return commonRejectionReasonRepository.findByRejectionCode(PCModuleCode.PC_AGE.code())
                .replace("<DrugName> (<DrugCode>)", code);
    }

	private List<PCAge> payerConfigDetailsBasedonModuleName(String payerId, String serviceCode, String birthdate,
			String moduleName, String scientificCode) {
		/*
		 * This is for REJECTED service in requestbody so we need to match only APPROVED
		 * service status in config table
		 */
		return pcAgeRepository.findByPayerIdAndServiceCodeOrScientificCodeAndModuleNameAndDOB(payerId, serviceCode,
				moduleName, convertBirthdateInDays(birthdate), ServiceStatus.APPROVED.value(), scientificCode);
	}

	private List<PCAge> payerConfigDetailsWithoutModuleName(String payerId, String serviceCode, String birthdate,
			String scientificCode) {
		/*
		 * This is for APPROVED service in requestbody so we need to match only REJECTED
		 * service status in config table
		 */
		return pcAgeRepository.findByPayerIdAndServiceCodeOrScientificCodeAndDOB(payerId, serviceCode,
				convertBirthdateInDays(birthdate), ServiceStatus.REJECTED.value(), scientificCode);
	}

    private Long convertBirthdateInDays(String birthDate) {
        return patientAgeConverterService.patientAgeConverter(birthDate);
    }
}
