package com.waseel.pbm.payercustomizationservice.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.pbm.payercustomizationservice.common.CommonMethods;
import com.waseel.pbm.payercustomizationservice.enums.DrugType;
import com.waseel.pbm.payercustomizationservice.enums.ModuleName;
import com.waseel.pbm.payercustomizationservice.enums.PCModuleCode;
import com.waseel.pbm.payercustomizationservice.enums.PayerCode;
import com.waseel.pbm.payercustomizationservice.enums.RejectionCode;
import com.waseel.pbm.payercustomizationservice.enums.ServiceStatus;
import com.waseel.pbm.payercustomizationservice.model.DssRequest;
import com.waseel.pbm.payercustomizationservice.model.DssResponse;
import com.waseel.pbm.payercustomizationservice.model.Error;
import com.waseel.pbm.payercustomizationservice.model.PCRequest;
import com.waseel.pbm.payercustomizationservice.model.Result;
import com.waseel.pbm.payercustomizationservice.persist.CustomizationAuditTrail;
import com.waseel.pbm.payercustomizationservice.persist.PCQuantityLimitCheck;
import com.waseel.pbm.payercustomizationservice.repository.CommonRejectionReasonRepository;
import com.waseel.pbm.payercustomizationservice.repository.PCQuantityLimitCheckRepository;

@Service
public class QuantityLimitCheckService {

	@Autowired
	private PCQuantityLimitCheckRepository pcQuantityLimitCheckRepository;

	@Autowired
	private PatientAgeConverterService patientAgeConverterService;

	@Autowired
	private CommonRejectionReasonRepository commonRejectionReasonRepository;

	@Autowired
	private CommonMethods commonMethods;

	private static final String ALL_ICD_CODE = "ALL";

	public DssResponse manageCustomizationQuantityLimitCheck(PCRequest pcRequest) {
		DssRequest dssRequest = pcRequest.getDssRequest();
		DssResponse dssResponse = pcRequest.getDssResponse();
		List<String> icdCodes = new ArrayList<>();
		icdCodes.addAll(dssRequest.getIcdCodes());

		icdCodes.add(ALL_ICD_CODE);
		List<CustomizationAuditTrail> auditTrailList = new ArrayList<>();
		List<String> ruleIdList = new ArrayList<>();
		dssResponse.getResults().forEach(result -> {
			if (result.getStatus().equalsIgnoreCase(ServiceStatus.REJECTED.value())) {

				manageRejectedService(result, icdCodes, dssRequest.getPayerId(), dssRequest.getDateOfBirth(),
						dssRequest.getRequestId(), auditTrailList, ruleIdList);
			} else {
				manageApprovedService(result, dssRequest.getPayerId(), dssRequest.getDateOfBirth(), icdCodes,
						dssRequest.getIcdCodes(), dssRequest.getRequestId(), auditTrailList, ruleIdList);
			}
		});


		commonMethods.saveDataToAuditTrail(auditTrailList);
		return dssResponse;
	}

	private void manageRejectedService(Result result, List<String> icdCodes, String payerId, String dateOfBirth,
			String requestId, List<CustomizationAuditTrail> auditTrailList, List<String> ruleIdList) {

		List<Error> approvedErrorList = new ArrayList<>();
		result.getErrors().forEach(error -> {
			if (error.getCode().equals(RejectionCode.FDB_QUANTITY_LIMIT_CHECK.code())) {
				populateConfigBasedOnModuleName(result, payerId, dateOfBirth, icdCodes, error, approvedErrorList,
						ModuleName.FDB.value(), requestId, auditTrailList, ruleIdList);
			} else if (error.getCode().equals(RejectionCode.IDF_QUANTITY_LIMIT_CHECK.code())) {
				populateConfigBasedOnModuleName(result, payerId, dateOfBirth, icdCodes, error, approvedErrorList,
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

	private void populateConfigBasedOnModuleName(Result result, String payerId, String dateOfBirth,

			List<String> icdCodes, Error error, List<Error> approvedErrorList, String moduleName, String requestId,
			List<CustomizationAuditTrail> auditTrailList, List<String> ruleIdList) {
		List<PCQuantityLimitCheck> payerConfig = rejectedPayerConfigDetails(payerId, result.getNdcDrugCode(), icdCodes,
				dateOfBirth, moduleName);
		if (!payerConfig.isEmpty()) {
			PCQuantityLimitCheck config = payerConfig.stream().filter(m -> m.getId().getModuleName().equals(moduleName))
					.findAny().orElse(payerConfig.get(0));

			if (result.getDaysOfSupply() != null && !result.getDaysOfSupply().isEmpty()

					&& (config.getId().getIcdCode().equals(ALL_ICD_CODE)
							|| icdCodes.contains(config.getId().getIcdCode()))) {
				if (validateQLC(config, result.getDispensedQuantity(), result.getDaysOfSupply())) {
					approvedErrorList.add(error);
				} else {
					if (!StringUtils.isBlank(config.getAdditionalRejectionReason())) {
						error.setDescription(config.getAdditionalRejectionReason());
					}
				}


				commonMethods.saveDataToAuditTrailList(requestId, config.getRuleId(), auditTrailList, ruleIdList);
			}
		}
	}

	private void manageApprovedService(Result result, String payerId, String dateOfBirth, List<String> icdCodes,

			List<String> dssIcdCodes, String requestId, List<CustomizationAuditTrail> auditTrailList,
			List<String> ruleIdList) {

		List<PCQuantityLimitCheck> payerConfig = approvedPayerConfigDetails(payerId, result.getNdcDrugCode(), icdCodes,
				dateOfBirth);
		if (!payerConfig.isEmpty()) {
			PCQuantityLimitCheck config = payerConfig.stream()
					.filter(codeConfig -> dssIcdCodes.contains(codeConfig.getId().getIcdCode())).findAny()
					.orElse(payerConfig.get(0));

			if (result.getDaysOfSupply() != null && !result.getDaysOfSupply().isEmpty()
					&& !validateQLC(config, result.getDispensedQuantity(), result.getDaysOfSupply())) {
				List<Error> updatedErrorList = new ArrayList<>();
				result.setStatus(ServiceStatus.REJECTED.value());
				Error error = new Error();
				error.setCode(PCModuleCode.PC_QLC.code());
				if (!StringUtils.isBlank(config.getAdditionalRejectionReason())) {
					error.setDescription(config.getAdditionalRejectionReason());
				} else {
					error.setDescription(getCommonRejectionReasonMsg(result.getNdcDrugCode()));
				}
				updatedErrorList.add(error);
				result.setErrors(updatedErrorList);


				commonMethods.saveDataToAuditTrailList(requestId, config.getRuleId(), auditTrailList, ruleIdList);
			}

		}
	}

	private List<PCQuantityLimitCheck> rejectedPayerConfigDetails(String payerId, String serviceCode,
			List<String> icdCodes, String birthdate, String moduleName) {
		List<PCQuantityLimitCheck> payerConfig = pcQuantityLimitCheckRepository
				.findByPayerIdAndServiceCodeAndIcdCodesAndModuleNameAndDOB(payerId, serviceCode, icdCodes, moduleName,
						convertBirthdateInDays(birthdate));
		if (payerConfig.isEmpty()) {
			payerConfig = pcQuantityLimitCheckRepository.findByPayerIdAndServiceCodeAndIcdCodesAndModuleNameAndDOB(
					PayerCode.ALL_PAYER.value(), serviceCode, icdCodes, moduleName, convertBirthdateInDays(birthdate));
		}
		return payerConfig;
	}

	private List<PCQuantityLimitCheck> approvedPayerConfigDetails(String payerId, String serviceCode,
			List<String> icdCodes, String birthdate) {
		List<PCQuantityLimitCheck> payerConfig = pcQuantityLimitCheckRepository
				.findByPayerIdAndServiceCodeAndIcdCodesAndDOB(payerId, serviceCode, icdCodes,
						convertBirthdateInDays(birthdate));
		if (payerConfig.isEmpty()) {
			payerConfig = pcQuantityLimitCheckRepository.findByPayerIdAndServiceCodeAndIcdCodesAndDOB(
					PayerCode.ALL_PAYER.value(), serviceCode, icdCodes, convertBirthdateInDays(birthdate));
		}
		return payerConfig;
	}

	private Long convertBirthdateInDays(String birthDate) {
		return patientAgeConverterService.patientAgeConverter(birthDate);
	}


	private boolean validateQLC(PCQuantityLimitCheck config, BigDecimal dispensedQuantity, String daysOfSupply) {
		BigDecimal totalProductPackageSize = dispensedQuantity;
		if (!config.getDrugType().equalsIgnoreCase(DrugType.SOLID.value())) {
			totalProductPackageSize = dispensedQuantity.multiply(new BigDecimal(config.getProductPackageSize()));
		}
		// 1.Max in the system * days of supply - We want to consider the less than a
		// box
		double tabPerDOS = config.getMaxValuePerDay() * Double.parseDouble(daysOfSupply);
		// 2.We have to calculate the percentage of the allow amount comparing to the
		// box size
		long boxSize = (long) Math.ceil(tabPerDOS / config.getProductPackageSize());
		// 3. convert box to tablets
		long perBox = boxSize * config.getProductPackageSize();
		// 4. compare dispensedQuantity with per box -- True -> Approved, False ->
		// Rejected

		int qlcCompareResult = totalProductPackageSize.compareTo(new BigDecimal(perBox));
		if (qlcCompareResult == -1 || qlcCompareResult == 0)
			return true;
		else
			return false;

	}

	private String getCommonRejectionReasonMsg(String serviceCode) {
		return commonRejectionReasonRepository.findByRejectionCode(PCModuleCode.PC_QLC.code())
				.replace("<DrugName> (<DrugCode>)", serviceCode).replace("<UnitType>", "day");
	}
}