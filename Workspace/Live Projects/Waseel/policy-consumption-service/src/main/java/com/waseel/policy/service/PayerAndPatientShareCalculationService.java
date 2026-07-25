package com.waseel.policy.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.policy.enums.BenefitCase;
import com.waseel.policy.enums.BusinessRulesPrivilage;
import com.waseel.policy.enums.ExceptionLogs;
import com.waseel.policy.enums.PolicyConsumptionDenialCodes;
import com.waseel.policy.enums.PolicyResponseStatus;
import com.waseel.policy.enums.PolicyResponseStatusDescription;
import com.waseel.policy.enums.TransactionStatusType;
import com.waseel.policy.enums.TransactionType;
import com.waseel.policy.exception.PolicyException;
import com.waseel.policy.model.BrandAndGenericModel;
import com.waseel.policy.model.DispensibleDrugsRequestModel;
import com.waseel.policy.model.DrugListModel;
import com.waseel.policy.model.MemberPolicyDetailsModel;
import com.waseel.policy.model.PolicyResponseModel;
import com.waseel.policy.persist.businessrules.CommonDenial;
import com.waseel.policy.persist.businessrules.TransactionLog;
import com.waseel.policy.persist.hira.AccountToAccountAssociation;
import com.waseel.policy.repository.businessrules.CommonDenialsRepository;
import com.waseel.policy.repository.businessrules.GenericIrreplicableBrandRepository;
import com.waseel.policy.repository.businessrules.ReplicableBrandRepository;
import com.waseel.policy.repository.businessrules.TransactionLogRepository;
import com.waseel.policy.repository.hira.AccountToAccountAssociationRepository;
import com.waseel.policy.service.management.SessionService;

@Service
public class PayerAndPatientShareCalculationService {

	@Autowired
	private AccountToAccountAssociationRepository accountToAccountAssociationRepository;

	@Autowired
	private TransactionLogRepository transactionLogRepository;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private CommonDenialsRepository commonDenialsRepository;

	@Autowired
	private MemberDetailsService memberDetailsService;

	@Autowired
	private GenericIrreplicableBrandRepository genericIrreplicableBrandRepository;

	@Autowired
	private ReplicableBrandRepository replicableBrandRepository;

	private final Logger logger = LoggerFactory.getLogger(PayerAndPatientShareCalculationService.class);

	public PolicyResponseModel fetchPatientShareForDispensableDrugs(String idNumber,
			DispensibleDrugsRequestModel dispensableDrugsRequestModel,
			ContentCachingRequestWrapper contentCachingRequestWrapper) throws PolicyException {
		validateDispensableDrugsRequestModel(dispensableDrugsRequestModel, idNumber);
		String payerId = dispensableDrugsRequestModel.getPayerId();
		String providerId = dispensableDrugsRequestModel.getProviderId();
		String requestId = dispensableDrugsRequestModel.getRequestId();
		logger.info("Fetch patient share, and benetift case for suggested drugs for prescription RequestID: [{}]",
				requestId);
		handleTransactionLog(contentCachingRequestWrapper, payerId, providerId, requestId,
				new BigDecimal(BusinessRulesPrivilage.DISPENSE_PRIVILAGE.value()));
		String providerPayerCode = getProviderPayerCode(new BigDecimal(providerId), new BigDecimal(payerId));
		MemberPolicyDetailsModel memberPolicyDetailsModel = memberDetailsService.fetchMemberPolicyDetails(idNumber,
				dispensableDrugsRequestModel.getBenefitCode(), dispensableDrugsRequestModel.getBenefitCase(), payerId,
				providerId, providerPayerCode);
		boolean hasTotalRemainingLimitAmount = null != memberPolicyDetailsModel.getTotalRemainingLimitAmount()
				&& memberPolicyDetailsModel.getTotalRemainingLimitAmount().compareTo(BigDecimal.ZERO) > 0;
		if (hasTotalRemainingLimitAmount) {
			BigDecimal benefitRemainingLimitValue = memberPolicyDetailsModel.getRemainingLimitValue();
			if (null == benefitRemainingLimitValue || benefitRemainingLimitValue.compareTo(BigDecimal.ZERO) == 0) {
				throw new PolicyException(populateInvalidPolicyResponse(PolicyResponseStatus.REJECTED.value(),
						PolicyResponseStatusDescription.DISPENSE_NO_REMAINING_LIMIT.value(),
						String.valueOf(HttpStatus.OK.value()),
						PolicyResponseStatusDescription.DISPENSE_NO_REMAINING_LIMIT.value(),
						PolicyConsumptionDenialCodes.BR_PC_NO_REMAINING_LIMIT.value(), idNumber));
			} else {
				return populatePolicyResponse(memberPolicyDetailsModel, requestId,
						dispensableDrugsRequestModel.getDispensibleDrugs(), idNumber);
			}
		} else {
			throw new PolicyException(populateInvalidPolicyResponse(PolicyResponseStatus.REJECTED.value(),
					PolicyResponseStatusDescription.NO_REMAINING_LIMIT.value(), String.valueOf(HttpStatus.OK.value()),
					PolicyResponseStatusDescription.NO_REMAINING_LIMIT.value(),
					PolicyConsumptionDenialCodes.BR_PC_NO_REMAINING_LIMIT.value(), idNumber));
		}
	}

	private void validateDispensableDrugsRequestModel(DispensibleDrugsRequestModel dispensableDrugsRequestModel,
			String idNumber) throws PolicyException {
		List<String> errors = new ArrayList<>();
		if (null != dispensableDrugsRequestModel) {
			validateDispensableDrugsRequestModelFields(errors, dispensableDrugsRequestModel);
		} else {
			errors.add(PolicyResponseStatusDescription.INVALID_REQUEST.value());
		}
		if (!errors.isEmpty()) {
			throw new PolicyException(populateInvalidPolicyResponse(PolicyResponseStatus.INVALID.value(),
					String.join(",", errors), String.valueOf(HttpStatus.OK.value()),
					PolicyResponseStatusDescription.INVALID_REQUEST.value(),
					PolicyConsumptionDenialCodes.BR_PC_INVALID.value(), idNumber));
		}
	}

	private void validateDispensableDrugsRequestModelFields(List<String> errors,
			DispensibleDrugsRequestModel dispensableDrugsRequestModel) {
		if (StringUtils.isBlank(dispensableDrugsRequestModel.getRequestId())) {
			errors.add(PolicyResponseStatusDescription.INVALID_PRESCRIPTION.value().replace(".", ""));
		}
		if (StringUtils.isBlank(dispensableDrugsRequestModel.getBenefitCase())) {
			errors.add(PolicyResponseStatusDescription.NO_BENEFIT_CASE.value().replace(".", ""));
		}
		if (StringUtils.isBlank(dispensableDrugsRequestModel.getBenefitCode())) {
			errors.add(PolicyResponseStatusDescription.NO_BENEFIT_DETAILS.value().replace(".", ""));
		}
		if (StringUtils.isBlank(dispensableDrugsRequestModel.getPayerId())) {
			errors.add(PolicyResponseStatusDescription.NO_PAYER_ID.value().replace(".", ""));
		}
		if (StringUtils.isBlank(dispensableDrugsRequestModel.getProviderId())) {
			errors.add(PolicyResponseStatusDescription.NO_PROVIDER_ID.value().replace(".", ""));
		}
		if (null == dispensableDrugsRequestModel.getDispensibleDrugs()
				|| dispensableDrugsRequestModel.getDispensibleDrugs().isEmpty()) {
			errors.add(PolicyResponseStatusDescription.NO_DRUGS_TO_DISPENSE.value().replace(".", ""));
		}
	}

	private String getProviderPayerCode(BigDecimal providerId, BigDecimal payerId) {
		AccountToAccountAssociation accountToAccountAssociation = accountToAccountAssociationRepository
				.findByIdSourceAndIdDestinationsAndIsEnabled(providerId, payerId, true);
		return null != accountToAccountAssociation ? accountToAccountAssociation.getCode() : providerId.toString();
	}

	private PolicyResponseModel populatePolicyResponse(MemberPolicyDetailsModel memberPolicyDetailsModel,
			String requestId, List<String> drugs, String idNumber) {
		logger.info(
				"Calculate payer and patient share respectively for IdNumber: [{}] with prescription RequestID: [{}]",
				idNumber, requestId);
		List<DrugListModel> dispensibleDrugs = categorizeDrugBenefitCase(drugs, memberPolicyDetailsModel);
		String statusDescription = PolicyResponseStatusDescription.MEMBER_POLICY_DETAILS_FOR_DISPENSE.value();
		PolicyResponseModel policyResponseModel = new PolicyResponseModel();
		policyResponseModel.setDrugList(dispensibleDrugs);
		policyResponseModel.setRequestId(requestId);
		policyResponseModel.setStatus(PolicyResponseStatus.APPROVED.value());
		policyResponseModel.setStatusDescription(statusDescription);
		policyResponseModel.setHttpStatusCode(String.valueOf(HttpStatus.OK.value()));
		policyResponseModel.setHttpStatusDescription(statusDescription);
		return policyResponseModel;
	}

	private List<DrugListModel> categorizeDrugBenefitCase(List<String> drugs,
			MemberPolicyDetailsModel memberPolicyDetailsModel) {
		List<DrugListModel> categorizedDispensibleDrugList = new ArrayList<>();
		Set<String> drugCodes = drugs.stream().collect(Collectors.toSet());
		Optional<List<String>> genericAndIrreplaceableDrugs = genericIrreplicableBrandRepository
				.findAllNonDeletedGenericIrreplaceableDrugsByDrugCodes(drugCodes);
		// if the chi flag is true and the BrandAndGenericModel ! null/empty
		BrandAndGenericModel brandAndGenericModel = memberPolicyDetailsModel.getBrandAndGenericModel();
		if (brandAndGenericModel != null) {
			if (genericAndIrreplaceableDrugs.isPresent() && !genericAndIrreplaceableDrugs.get().isEmpty()) {
				categorizedDispensibleDrugList = drugs.stream()
						.filter(drug -> genericAndIrreplaceableDrugs.get().contains(drug)).map(drug -> {
							// set the shares directly from the payer api object
							DrugListModel drugListModel = new DrugListModel(drug,
									brandAndGenericModel.getGenericDrugPatientShareValue(),
									brandAndGenericModel.getGenericDrugPatientShareCurrency(),
									brandAndGenericModel.getGenericDrugMaxPatientShare(),
									brandAndGenericModel.getGenericDrugMaxPatientShareCurrency(),
									BenefitCase.IRREPLACEABLE_BRAND.value());
							drugCodes.remove(drug);
							return drugListModel;
						}).collect(Collectors.toList());
			}
			if (categorizedDispensibleDrugList.size() < drugs.size() && !drugCodes.isEmpty()) {
				Optional<List<String>> replicableBrandDrugs = replicableBrandRepository
						.findAllNonDeletedReplicableDrugsByDrugCodes(drugCodes);
				if (replicableBrandDrugs.isPresent() && !replicableBrandDrugs.get().isEmpty()) {
					List<DrugListModel> categorizedDrugList = categorizedDispensibleDrugList;
					if (categorizedDrugList.isEmpty()) {
						categorizedDispensibleDrugList
								.addAll(drugs.stream().filter(drug -> replicableBrandDrugs.get().contains(drug))
										.map(drug -> new DrugListModel(drug,
												brandAndGenericModel.getBrandDrugPatientShareValue(),
												brandAndGenericModel.getBrandDrugPatientShareCurrency(),
												brandAndGenericModel.getBrandDrugMaxPatientShare(),
												brandAndGenericModel.getBrandedDrugMaxPatientShareCurrency(),
												BenefitCase.REPLACEABLE_BRAND.value()))
										.collect(Collectors.toList()));
					}
				}
			}
		} else {
			categorizedDispensibleDrugList.addAll(drugs.stream()
					.map(drug -> new DrugListModel(drug, memberPolicyDetailsModel.getPatientShareValue(),
							memberPolicyDetailsModel.getPatientShareCurrency(),
							memberPolicyDetailsModel.getMaxPatientShareValue(),
							memberPolicyDetailsModel.getMaxPatientShareCurrency(), BenefitCase.OUTPATIENT.value()))
					.collect(Collectors.toList()));
		}
		return categorizedDispensibleDrugList;
	}

	private void handleTransactionLog(ContentCachingRequestWrapper contentCachingRequestWrapper, String payerId,
			String providerId, String requestId, BigDecimal transactionId) {
		try {
			Timestamp timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
			TransactionLog transactionLog = new TransactionLog(payerId, providerId, timestamp, requestId,
					TransactionType.POLICY_CONSUMPTION.value(), TransactionStatusType.RECEIVED.value(), transactionId);
			transactionLog = transactionLogRepository.save(transactionLog);
			sessionService.setTransactionLogIdInSession(contentCachingRequestWrapper,
					transactionLog.getTransactionLogId());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("{} {} .", ExceptionLogs.FAILED_TRANSACTION.value(), requestId);
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
}
