package com.waseel.policy.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.waseel.policy.enums.ModuleConfiguration;
import com.waseel.policy.enums.PbmPayerApiStatus;
import com.waseel.policy.enums.PolicyConsumptionDenialCodes;
import com.waseel.policy.enums.PolicyResponseStatus;
import com.waseel.policy.enums.PolicyResponseStatusDescription;
import com.waseel.policy.exception.PolicyException;
import com.waseel.policy.model.BrandAndGenericModel;
import com.waseel.policy.model.MemberPolicyDetailsModel;
import com.waseel.policy.model.PolicyResponseModel;
import com.waseel.policy.model.client.ClassBenefitCasesModel;
import com.waseel.policy.model.client.InvalidResponseModel;
import com.waseel.policy.model.client.MemberDetailsResponseModel;
import com.waseel.policy.model.client.PbmPayerApiResponseModel;
import com.waseel.policy.model.client.PolicyClassBenefitsModel;
import com.waseel.policy.model.client.PolicyDetailsModel;
import com.waseel.policy.persist.businessrules.CommonDenial;
import com.waseel.policy.repository.businessrules.CommonDenialsRepository;
import com.waseel.policy.repository.businessrules.ModuleConfigurationRepository;
import com.waseel.policy.service.clienthandler.PbmPayerApisRestHandler;

@Service
public class MemberDetailsService {

	private final Logger logger = LoggerFactory.getLogger(MemberDetailsService.class);
	private static final String IRREPLACEABLE_BRAND = "Generics and Irreplacable Brands";
	private static final String REPLACEABLE_BRAND = "Replaceable Brand";

	@Autowired
	private PbmPayerApisRestHandler pbmPayerApisRestHandler;

	@Autowired
	private CommonDenialsRepository commonDenialsRepository;

	@Autowired
	private ModuleConfigurationRepository moduleConfigurationRepository;

	public MemberPolicyDetailsModel fetchMemberPolicyDetails(String idNumber, String benefitCode, String benefitCase,
			String payerId, String providerId, String providerPayerCode) throws PolicyException {
		logger.info("Fetch member-details from payer:[{}] for IdNumber:[{}]", payerId, idNumber);
		PbmPayerApiResponseModel pbmPayerApiResponseModel = pbmPayerApisRestHandler
				.getMemberDetails(Long.valueOf(idNumber), providerPayerCode);
		MemberDetailsResponseModel memberDetailsResponseModel = pbmPayerApiResponseModel
				.getMemberDetailsResponseModel();
		if (null != memberDetailsResponseModel) {
			return fetchPolicyDetails(memberDetailsResponseModel, idNumber, benefitCode, benefitCase, payerId,
					providerId);
		} else {
			InvalidResponseModel invalidResponseModel = pbmPayerApiResponseModel.getInvalidResponseModel();
			String statusDescription = String.join(",", invalidResponseModel.getErrors());
			if (invalidResponseModel.getStatus().equals(PbmPayerApiStatus.INVALID.value())) {
				throw new PolicyException(populateInvalidPolicyResponse(PolicyResponseStatus.INVALID.value(),
						statusDescription, String.valueOf(HttpStatus.BAD_REQUEST.value()), statusDescription,
						PolicyConsumptionDenialCodes.BR_PC_INVALID_ID_NUMBER.value(), idNumber));
			} else {
				String httpStatus = StringUtils.isBlank(statusDescription)
						|| !statusDescription.equals(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()))
								? String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value())
								: statusDescription;
				throw new PolicyException(populateInvalidPolicyResponse(PolicyResponseStatus.FAILED.value(),
						statusDescription, httpStatus, statusDescription,
						PolicyConsumptionDenialCodes.BR_PC_FAILED.value(), idNumber));
			}
		}
	}

	private MemberPolicyDetailsModel fetchPolicyDetails(MemberDetailsResponseModel memberDetailsResponseModel,
			String idNumber, String benefitCode, String benefitCase, String payerId, String providerId)
			throws PolicyException {
		List<PolicyDetailsModel> policyDetailsModels = memberDetailsResponseModel.getPolicyInformation().stream()
				.collect(Collectors.toList());
		PolicyDetailsModel policyDetailsModel = policyDetailsModels.get(0);
		List<PolicyClassBenefitsModel> classBenefits = policyDetailsModel.getClassBenefits();
		Optional<PolicyClassBenefitsModel> classBenefitOpt = classBenefits.stream()
				.filter(benefit -> benefit.getBenefitCode().equals(benefitCode)).findAny();
		if (classBenefitOpt.isPresent()) {
			PolicyClassBenefitsModel classBenefit = classBenefitOpt.get();
			List<ClassBenefitCasesModel> benefitCases = classBenefit.getBenefitCases();
			Optional<ClassBenefitCasesModel> classBenefitCasesModelOpt = benefitCases.stream()
					.filter(policybenefitCase -> policybenefitCase.getCaseCode().equals("OUTPATIENT")).findAny();
			ClassBenefitCasesModel classBenefitDetails = classBenefitCasesModelOpt.isPresent()
					? classBenefitCasesModelOpt.get()
					: null;
			Optional<ClassBenefitCasesModel> replaceableCasesModelOpt = Optional.empty();
			Optional<ClassBenefitCasesModel> irreplaceableCasesModelOpt = Optional.empty();
			List<Long> moduleConfigurations = moduleConfigurationRepository
					.findByProviderIdAndPayerIdAndIsEnabled(Long.valueOf(payerId), Long.valueOf(providerId));
			if (policyDetailsModel.getIsChiPolicy() != null && policyDetailsModel.getIsChiPolicy()
					&& moduleConfigurations.contains(ModuleConfiguration.BRAND_REPLICABILITY.value())) {
				replaceableCasesModelOpt = benefitCases.stream()
						.filter(policybenefitCase -> policybenefitCase.getCaseCode().equals(REPLACEABLE_BRAND))
						.findAny();
				irreplaceableCasesModelOpt = benefitCases.stream()
						.filter(policybenefitCase -> policybenefitCase.getCaseCode().equals(IRREPLACEABLE_BRAND))
						.findAny();
			}
			if (null != classBenefitDetails) {
				return populateMemberPolicyDetails(classBenefitDetails, classBenefit, policyDetailsModel,
						replaceableCasesModelOpt.isPresent() ? replaceableCasesModelOpt.get() : null,
						irreplaceableCasesModelOpt.isPresent() ? irreplaceableCasesModelOpt.get() : null);
			} else {
				throw new PolicyException(populateInvalidPolicyResponse(PolicyResponseStatus.INVALID.value(),
						PolicyResponseStatusDescription.NO_BENEFIT_DETAILS.value(),
						String.valueOf(HttpStatus.BAD_REQUEST.value()),
						PolicyResponseStatusDescription.NO_BENEFIT_DETAILS.value(),
						PolicyConsumptionDenialCodes.BR_PC_NO_BENEFITS.value(), idNumber));
			}
		} else {
			throw new PolicyException(populateInvalidPolicyResponse(PolicyResponseStatus.INVALID.value(),
					PolicyResponseStatusDescription.NO_BENEFIT_CASE.value(),
					String.valueOf(HttpStatus.BAD_REQUEST.value()),
					PolicyResponseStatusDescription.NO_BENEFIT_CASE.value(),
					PolicyConsumptionDenialCodes.BR_PC_NO_BENEFIT_CASE.value(), idNumber));
		}

	}

	private PolicyResponseModel populateInvalidPolicyResponse(String status, String statusDescription,
			String httpStatus, String httpDescription, String denialCode, String idNumber) {
		PolicyResponseModel policyResponseModel = new PolicyResponseModel();
		Optional<CommonDenial> commonDenialsOp = commonDenialsRepository.findByDenialCode(denialCode);
		if (commonDenialsOp.isPresent()) {
			policyResponseModel
					.setDenialDescription(commonDenialsOp.get().getDenialDescription().replace("<IdNumber>", idNumber));
		}
		policyResponseModel.setStatus(status);
		policyResponseModel.setStatusDescription(statusDescription);
		policyResponseModel.setHttpStatusCode(httpStatus);
		policyResponseModel.setHttpStatusDescription(httpDescription);
		policyResponseModel.setDenialCode(denialCode);
		return policyResponseModel;
	}

	private MemberPolicyDetailsModel populateMemberPolicyDetails(ClassBenefitCasesModel casesModel,
			PolicyClassBenefitsModel classBenefit, PolicyDetailsModel policyDetailsModel,
			ClassBenefitCasesModel replaceableCasesModel, ClassBenefitCasesModel irreplaceableCasesModel) {
		BigDecimal benefitLimitValue = classBenefit.getBenefitLimitValue();
		BigDecimal remainingLimitValue = classBenefit.getRemainingLimitValue();
		BrandAndGenericModel brandAndGenericModel = null;
		BigDecimal patientShareValue = null;
		String patientShareCurrency = null;
		BigDecimal maxPatientShareValue = null;
		if (casesModel != null) {
			patientShareValue = new BigDecimal(casesModel.getPatientShareValue());
			patientShareCurrency = casesModel.getPatientShareCurrency();
			maxPatientShareValue = casesModel.getMaxPatientShareValue();
		}
		if (replaceableCasesModel != null && irreplaceableCasesModel != null) {
			brandAndGenericModel = new BrandAndGenericModel(
					new BigDecimal(irreplaceableCasesModel.getPatientShareValue()),
					irreplaceableCasesModel.getPatientShareCurrency(),
					irreplaceableCasesModel.getMaxPatientShareValue(),
					new BigDecimal(replaceableCasesModel.getPatientShareValue()),
					replaceableCasesModel.getPatientShareCurrency(), replaceableCasesModel.getMaxPatientShareValue(),
					irreplaceableCasesModel.getMaxPatientShareCurrency(),
					replaceableCasesModel.getMaxPatientShareCurrency());
		}
		String policyNumber = policyDetailsModel.getPolicyNumber();
		String policyClass = policyDetailsModel.getPolicyClass();
		String memberId = policyDetailsModel.getMemberId();
		String benefitLimitCurrency = classBenefit.getBenefitLimitCurrency();
		BigDecimal totalRemainingLimitAmount = policyDetailsModel.getRemainingLimitValue();
		String totalRemainingLimitCurrency = policyDetailsModel.getRemainingLimitCurrency();
		return new MemberPolicyDetailsModel(benefitLimitValue, remainingLimitValue, patientShareValue,
				patientShareCurrency, maxPatientShareValue, policyNumber, policyClass, memberId, benefitLimitCurrency,
				totalRemainingLimitAmount, brandAndGenericModel, totalRemainingLimitCurrency);
	}
}