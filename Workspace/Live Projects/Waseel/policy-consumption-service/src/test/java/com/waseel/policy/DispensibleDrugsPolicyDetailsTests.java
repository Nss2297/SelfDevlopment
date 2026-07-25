package com.waseel.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waseel.policy.enums.BenefitCase;
import com.waseel.policy.enums.BenefitCode;
import com.waseel.policy.enums.BusinessRulesPrivilage;
import com.waseel.policy.enums.ModuleConfiguration;
import com.waseel.policy.enums.PolicyConsumptionDenialCodes;
import com.waseel.policy.enums.PolicyResponseStatus;
import com.waseel.policy.enums.PolicyResponseStatusDescription;
import com.waseel.policy.enums.TransactionStatusType;
import com.waseel.policy.enums.TransactionType;
import com.waseel.policy.exception.PolicyException;
import com.waseel.policy.model.DispensibleDrugsRequestModel;
import com.waseel.policy.model.DrugListModel;
import com.waseel.policy.model.PolicyResponseModel;
import com.waseel.policy.model.client.ClassBenefitCasesModel;
import com.waseel.policy.model.client.MemberDetailsResponseModel;
import com.waseel.policy.model.client.PbmPayerApiResponseModel;
import com.waseel.policy.model.client.PolicyClassBenefitsModel;
import com.waseel.policy.model.client.PolicyDetailsModel;
import com.waseel.policy.persist.businessrules.CommonDenial;
import com.waseel.policy.persist.businessrules.TransactionLog;
import com.waseel.policy.persist.hira.AccountToAccountAssociation;
import com.waseel.policy.persist.hira.AccountToAccountAssociationId;
import com.waseel.policy.repository.businessrules.CommonDenialsRepository;
import com.waseel.policy.repository.businessrules.GenericIrreplicableBrandRepository;
import com.waseel.policy.repository.businessrules.ModuleConfigurationRepository;
import com.waseel.policy.repository.businessrules.ReplicableBrandRepository;
import com.waseel.policy.repository.businessrules.TransactionLogRepository;
import com.waseel.policy.repository.hira.AccountToAccountAssociationRepository;
import com.waseel.policy.service.PayerAndPatientShareCalculationService;
import com.waseel.policy.service.clienthandler.PbmPayerApisRestHandler;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles({ "test" })
@TestMethodOrder(OrderAnnotation.class)
class DispensibleDrugsPolicyDetailsTests {

	@Autowired
	private PayerAndPatientShareCalculationService payerAndPatientShareCalculationService;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	private TransactionLogRepository transactionLogRepository;

	@MockBean
	private CommonDenialsRepository commonDenialsRepository;

	@MockBean
	private ModuleConfigurationRepository moduleConfigurationRepository;

	@MockBean
	private PbmPayerApisRestHandler pbmPayerApisRestHandler;

	@MockBean
	private AccountToAccountAssociationRepository accountToAccountAssociationRepository;

	@MockBean
	private GenericIrreplicableBrandRepository genericIrreplicableBrandRepository;

	@MockBean
	private ReplicableBrandRepository replicableBrandRepository;

	private static final String idNumber = "2392019564";
	private static final String benefitCode = BenefitCode.DENTAL.value();
	private static final String benefitCase = BenefitCase.INPATIENT.value();
	private static final String payerId = "102";
	private static final String requestId = "0fb78ee7-9e62-4d9e-aee7-18f099e126f1";
	private static final String providerId = "801";
	private static final List<String> dispensibleDrugs = populateDrugList();
	private static final String invalidDenialDescription = idNumber + " has an invalid request.";
	private static final String invalidStatusDescription = "Prescription not found,Benefit cases are unavailable,Benefit details are not present,PayerId is not present,ProviderId is not present,No drugs to dispense";
	private static final Timestamp timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
	private static final Long id = 1L;
	private static final String transactionReferenceNumber = "47984272";
	private static final String payerProviderCode = "8587";
	private static final String noRemainingLimitValueDenialDescription = idNumber + "  has no remaining limit left.";
	private static final BigDecimal remainingLimitValue = BigDecimal.valueOf(15000);
	private static final String currency = "SAR";
	private static final String statusDescription = PolicyResponseStatusDescription.MEMBER_POLICY_DETAILS_FOR_DISPENSE
			.value();
	private static final String drug1 = "1501233101";
	private static final String drug2 = "1808210952";
	private static final String drug3 = "425-277-20";
	private static final String drug4 = "1-5614-21";
	private static final Date date = new Date();
	private final MockHttpServletRequest request = new MockHttpServletRequest();
	protected static final int CONTENT_CACHE_LIMIT = 13;
	private ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(this.request,
			CONTENT_CACHE_LIMIT);
	private PolicyResponseModel policyResponseModel = null;
	private TransactionLog transactionLog = null;
	private PbmPayerApiResponseModel payerApiResponseModel = null;
	private AccountToAccountAssociation accountToAccountAssociation = null;
	private DispensibleDrugsRequestModel dispensibleDrugsRequestModel = null;;

	@BeforeAll
	private void setUpCommonData() {
		dispensibleDrugsRequestModel = populateDispensibleDrugsRequestModel();
		requestWrapper = getContentCachingRequestWrapper();
		policyResponseModel = populatePolicyResponseModel();
		transactionLog = generateTransactionLog();
		accountToAccountAssociation = populateAccountToAccountAssociation();
		payerApiResponseModel = populatePbmPayerApiResponseModel();
	}

	@BeforeEach
	private void setUpData() {
		Mockito.when(transactionLogRepository.save(Mockito.any())).thenReturn(transactionLog);
		Mockito.when(accountToAccountAssociationRepository.findByIdSourceAndIdDestinationsAndIsEnabled(Mockito.any(),
				Mockito.any(), Mockito.anyBoolean())).thenReturn(accountToAccountAssociation);
		Mockito.when(moduleConfigurationRepository.findByProviderIdAndPayerIdAndIsEnabled(Mockito.anyLong(),
				Mockito.anyLong())).thenReturn(generateListOfModuleConfiguration());
		Mockito.when(pbmPayerApisRestHandler.getMemberDetails(Mockito.anyLong(), Mockito.anyString()))
				.thenReturn(payerApiResponseModel);
		Mockito.when(
				genericIrreplicableBrandRepository.findAllNonDeletedGenericIrreplaceableDrugsByDrugCodes(Mockito.any()))
				.thenReturn(Optional.of(getGenericDrugs()));
		Mockito.when(replicableBrandRepository.findAllNonDeletedReplicableDrugsByDrugCodes(Mockito.any()))
				.thenReturn(Optional.of(getBrandedDrugs()));
	}

	@Order(1)
	@Test
	@DisplayName("Invalid Request Body.")
	void invalidRequestBody() throws PolicyException {
		PolicyResponseModel policyResponseModel = null;
		try {
			dispensibleDrugsRequestModel = null;
			Mockito.when(commonDenialsRepository.findByDenialCode(Mockito.any()))
					.thenReturn(Optional.of(generateCommonDenial(PolicyConsumptionDenialCodes.BR_PC_INVALID.value(),
							invalidDenialDescription)));
			payerAndPatientShareCalculationService.fetchPatientShareForDispensableDrugs(idNumber,
					dispensibleDrugsRequestModel, requestWrapper);
		} catch (PolicyException policyException) {
			policyResponseModel = policyException.getInvalidResponse();
			commonPolicyResponseForInvalidOrRejectedPrescription(policyResponseModel);
			assertEquals(policyResponseModel.getStatus(), PolicyResponseStatus.INVALID.value());
			assertEquals(PolicyResponseStatusDescription.INVALID_REQUEST.value(),
					policyResponseModel.getStatusDescription());
			assertEquals(policyResponseModel.getHttpStatusDescription(),
					PolicyResponseStatusDescription.INVALID_REQUEST.value());
			assertEquals(policyResponseModel.getDenialCode(), PolicyConsumptionDenialCodes.BR_PC_INVALID.value());
			assertEquals(invalidDenialDescription, policyResponseModel.getDenialDescription());
			dispensibleDrugsRequestModel = populateDispensibleDrugsRequestModel();
		}
	}

	@Order(2)
	@Test
	@DisplayName("Invalid or null fields in Request Body.")
	void invalidOrNullFieldsInRequestBody() {
		PolicyResponseModel policyResponseModel = null;
		try {
			dispensibleDrugsRequestModel = new DispensibleDrugsRequestModel();
			Mockito.when(commonDenialsRepository.findByDenialCode(Mockito.any()))
					.thenReturn(Optional.of(generateCommonDenial(PolicyConsumptionDenialCodes.BR_PC_INVALID.value(),
							invalidDenialDescription)));
			policyResponseModel = payerAndPatientShareCalculationService.fetchPatientShareForDispensableDrugs(idNumber,
					dispensibleDrugsRequestModel, requestWrapper);
		} catch (PolicyException policyException) {
			policyResponseModel = policyException.getInvalidResponse();
			commonPolicyResponseForInvalidOrRejectedPrescription(policyResponseModel);
			assertEquals(policyResponseModel.getStatus(), PolicyResponseStatus.INVALID.value());
			assertEquals(invalidStatusDescription, policyResponseModel.getStatusDescription());
			assertEquals(policyResponseModel.getHttpStatusDescription(),
					PolicyResponseStatusDescription.INVALID_REQUEST.value());
			assertEquals(policyResponseModel.getDenialCode(), PolicyConsumptionDenialCodes.BR_PC_INVALID.value());
			assertEquals(invalidDenialDescription, policyResponseModel.getDenialDescription());
			dispensibleDrugsRequestModel = populateDispensibleDrugsRequestModel();
		}
	}

	@Order(3)
	@Test
	@DisplayName("Exceeded total remaining limit.")
	void exceededTotalRemainingLimit() {
		PolicyResponseModel policyResponseModel = null;
		try {
			payerApiResponseModel.getMemberDetailsResponseModel().getPolicyInformation().get(0)
					.setRemainingLimitValue(BigDecimal.ZERO);
			Mockito.when(commonDenialsRepository.findByDenialCode(Mockito.any()))
					.thenReturn(Optional.of(generateCommonDenial(PolicyConsumptionDenialCodes.BR_PC_INVALID.value(),
							noRemainingLimitValueDenialDescription)));
			payerAndPatientShareCalculationService.fetchPatientShareForDispensableDrugs(idNumber,
					dispensibleDrugsRequestModel, requestWrapper);
		} catch (PolicyException policyException) {
			policyResponseModel = policyException.getInvalidResponse();
			commonPolicyResponseForInvalidOrRejectedPrescription(policyResponseModel);
			assertEquals(policyResponseModel.getStatus(), PolicyResponseStatus.REJECTED.value());
			assertEquals(PolicyResponseStatusDescription.NO_REMAINING_LIMIT.value(),
					policyResponseModel.getStatusDescription());
			assertEquals(policyResponseModel.getHttpStatusDescription(),
					PolicyResponseStatusDescription.NO_REMAINING_LIMIT.value());
			assertEquals(policyResponseModel.getDenialCode(),
					PolicyConsumptionDenialCodes.BR_PC_NO_REMAINING_LIMIT.value());
			assertEquals(noRemainingLimitValueDenialDescription, policyResponseModel.getDenialDescription());
			payerApiResponseModel.getMemberDetailsResponseModel().getPolicyInformation().get(0)
					.setRemainingLimitValue(remainingLimitValue);
		}

	}

	@Order(4)
	@Test
	@DisplayName("No remaining limit left.")
	void noRemainingLimitLeft() {
		PolicyResponseModel policyResponseModel = null;
		try {
			payerApiResponseModel.getMemberDetailsResponseModel().getPolicyInformation().get(0).getClassBenefits()
					.get(0).setRemainingLimitValue(BigDecimal.ZERO);
			Mockito.when(commonDenialsRepository.findByDenialCode(Mockito.any()))
					.thenReturn(Optional.of(generateCommonDenial(PolicyConsumptionDenialCodes.BR_PC_INVALID.value(),
							noRemainingLimitValueDenialDescription)));
			payerAndPatientShareCalculationService.fetchPatientShareForDispensableDrugs(idNumber,
					dispensibleDrugsRequestModel, requestWrapper);
		} catch (PolicyException policyException) {
			policyResponseModel = policyException.getInvalidResponse();
			commonPolicyResponseForInvalidOrRejectedPrescription(policyResponseModel);
			assertEquals(policyResponseModel.getStatus(), PolicyResponseStatus.REJECTED.value());
			assertEquals(PolicyResponseStatusDescription.DISPENSE_NO_REMAINING_LIMIT.value(),
					policyResponseModel.getStatusDescription());
			assertEquals(policyResponseModel.getHttpStatusDescription(),
					PolicyResponseStatusDescription.DISPENSE_NO_REMAINING_LIMIT.value());
			assertEquals(policyResponseModel.getDenialCode(),
					PolicyConsumptionDenialCodes.BR_PC_NO_REMAINING_LIMIT.value());
			assertEquals(noRemainingLimitValueDenialDescription, policyResponseModel.getDenialDescription());
			payerApiResponseModel.getMemberDetailsResponseModel().getPolicyInformation().get(0).getClassBenefits()
					.get(0).setRemainingLimitValue(remainingLimitValue);
		}
	}

	@Order(5)
	@Test
	@DisplayName("Success Response.")
	void successResponse() throws PolicyException {
		PolicyResponseModel policyResponseModel = payerAndPatientShareCalculationService
				.fetchPatientShareForDispensableDrugs(idNumber, dispensibleDrugsRequestModel, requestWrapper);
		commonAssertionForApprovedPolicyResponse(policyResponseModel);
	}

	private static List<String> populateDrugList() {
		List<String> drugs = new ArrayList<>();
		drugs.add(drug1);
		drugs.add(drug2);
		drugs.add(drug3);
		drugs.add(drug4);
		return drugs;
	}

	private DispensibleDrugsRequestModel populateDispensibleDrugsRequestModel() {
		return new DispensibleDrugsRequestModel(benefitCode, benefitCase, payerId, requestId, providerId,
				dispensibleDrugs);
	}

	private TransactionLog generateTransactionLog() {
		TransactionLog transactionLog = new TransactionLog();
		transactionLog.setHttpStatus(String.valueOf(HttpStatus.OK.value()));
		transactionLog.setHttpStatusDescription(policyResponseModel.getHttpStatusDescription());
		transactionLog.setPayerId(payerId);
		transactionLog.setProviderId(providerId);
		transactionLog.setReceivingRequestDateTime(timestamp);
		transactionLog.setRequestId(requestId);
		transactionLog.setSendingResponseDateTime(timestamp);
		transactionLog.setStatus(PolicyResponseStatus.APPROVED.value());
		transactionLog.setStatusDescription(policyResponseModel.getStatusDescription());
		transactionLog.setTransactionId(new BigDecimal(BusinessRulesPrivilage.DISPENSE_PRIVILAGE.value()));
		transactionLog.setTransactionLogId(id);
		transactionLog.setTransactionReferenceNumber(transactionReferenceNumber);
		transactionLog.setTransactionStatus(TransactionStatusType.SENT.value());
		transactionLog.setTransactionType(TransactionType.POLICY_CONSUMPTION.value());
		transactionLog.setReceivingRequestDateTime(timestamp);
		transactionLog.setSendingResponseDateTime(timestamp);
		return transactionLog;
	}

	private AccountToAccountAssociation populateAccountToAccountAssociation() {
		AccountToAccountAssociationId accountToAccountAssociationId = new AccountToAccountAssociationId(
				new BigDecimal(payerId), new BigDecimal(providerId));
		return new AccountToAccountAssociation(accountToAccountAssociationId, true, true, payerProviderCode);
	}

	private CommonDenial generateCommonDenial(String code, String description) {
		CommonDenial commonDenial = new CommonDenial();
		commonDenial.setCommonDenialsId(0);
		commonDenial.setDenialCode(code);
		commonDenial.setDenialDescription(description);
		return commonDenial;
	}

	private List<String> getGenericDrugs() {
		List<String> genericDrugs = new ArrayList<>();
		genericDrugs.add(drug1);
		genericDrugs.add(drug2);
		return genericDrugs;
	}

	private List<String> getBrandedDrugs() {
		List<String> genericDrugs = new ArrayList<>();
		genericDrugs.add(drug3);
		genericDrugs.add(drug4);
		return genericDrugs;
	}

	private ContentCachingRequestWrapper getContentCachingRequestWrapper() {
		ContentCachingRequestWrapper cachingRequestWrapper = null;
		try {
			MockHttpServletRequest hRequest = new MockHttpServletRequest();
			String req = this.objectMapper.writeValueAsString(dispensibleDrugsRequestModel);
			hRequest.setCharacterEncoding(StandardCharsets.UTF_8.name());
			hRequest.setContent(req.getBytes(StandardCharsets.UTF_8.name()));
			cachingRequestWrapper = new ContentCachingRequestWrapper(hRequest);
			cachingRequestWrapper.setRequest(hRequest);
			FileCopyUtils.copyToByteArray(cachingRequestWrapper.getInputStream());
			return cachingRequestWrapper;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cachingRequestWrapper;
	}

	private PolicyResponseModel populatePolicyResponseModel() {
		PolicyResponseModel policyResponseModel = new PolicyResponseModel();
		policyResponseModel.setHttpStatusCode(String.valueOf(HttpStatus.OK.value()));
		policyResponseModel.setHttpStatusDescription(statusDescription);
		policyResponseModel.setRequestId(requestId);
		policyResponseModel.setStatus(PolicyResponseStatus.APPROVED.value());
		policyResponseModel.setStatusDescription(statusDescription);
		policyResponseModel.setDrugList(populateDrugsList());
		return policyResponseModel;
	}

	private List<DrugListModel> populateDrugsList() {
		List<DrugListModel> drugList = new ArrayList<DrugListModel>();
		DrugListModel firstDrug = new DrugListModel(drug1, BenefitCase.IRREPLACEABLE_BRAND.value());
		DrugListModel secondDrug = new DrugListModel(drug2, BenefitCase.IRREPLACEABLE_BRAND.value());
		DrugListModel thirdDrug = new DrugListModel(drug3, BenefitCase.REPLACEABLE_BRAND.value());
		DrugListModel fourthDrug = new DrugListModel(drug4, BenefitCase.REPLACEABLE_BRAND.value());
		drugList.add(firstDrug);
		drugList.add(secondDrug);
		drugList.add(thirdDrug);
		drugList.add(fourthDrug);
		return drugList;
	}

	private PbmPayerApiResponseModel populatePbmPayerApiResponseModel() {
		MemberDetailsResponseModel memberDetailsResponseModel = populateMemberDetailsResponseModel();
		PbmPayerApiResponseModel payerApiResponseModel = new PbmPayerApiResponseModel(memberDetailsResponseModel, null);
		;
		return payerApiResponseModel;
	}

	private MemberDetailsResponseModel populateMemberDetailsResponseModel() {
		PolicyDetailsModel policyDetailsModel = populatePolicyDetailsModel();
		List<PolicyDetailsModel> policyInformation = new ArrayList<PolicyDetailsModel>();
		policyInformation.add(policyDetailsModel);
		MemberDetailsResponseModel memberDetailsResponseModel = new MemberDetailsResponseModel("Hashim Ebrahim",
				Long.valueOf(idNumber), "male", date, "", "saudi", "00966504875128", "test@hotmail.com",
				policyInformation);
		return memberDetailsResponseModel;
	}

	private PolicyDetailsModel populatePolicyDetailsModel() {
		PolicyClassBenefitsModel policyClassBenefitsModel = populatePolicyClassBenefitsModel();
		List<PolicyClassBenefitsModel> classBenefits = new ArrayList<PolicyClassBenefitsModel>();
		classBenefits.add(policyClassBenefitsModel);
		PolicyDetailsModel policyDetailsModel = new PolicyDetailsModel("24735173", "Marissa Wagner", "001014523658001",
				"PRINCIPAL", "GL/BS", "Gold B", date, date, remainingLimitValue, currency, classBenefits);
		return policyDetailsModel;
	}

	private PolicyClassBenefitsModel populatePolicyClassBenefitsModel() {
		List<ClassBenefitCasesModel> benefitCases = populateClassBenefitCasesModel();
		PolicyClassBenefitsModel classBenefitsModel = new PolicyClassBenefitsModel("DENTAL", "Dental Benefit",
				new BigDecimal(5000), currency, "10", "%", new BigDecimal(500), currency, new BigDecimal(0), currency,
				new BigDecimal(1000), currency, remainingLimitValue, currency, benefitCases);
		return classBenefitsModel;
	}

	private List<ClassBenefitCasesModel> populateClassBenefitCasesModel() {
		List<ClassBenefitCasesModel> benefitCases = new ArrayList<>();
		ClassBenefitCasesModel inpatientBenefits = new ClassBenefitCasesModel(BenefitCase.INPATIENT.value(), "10", "%",
				new BigDecimal(200), currency, BigDecimal.ZERO, currency, BigDecimal.ONE, currency);
		ClassBenefitCasesModel outpatientBenefits = new ClassBenefitCasesModel(BenefitCase.OUTPATIENT.value(), "20",
				"%", new BigDecimal(200), currency, BigDecimal.ZERO, currency, BigDecimal.ONE, currency);
		ClassBenefitCasesModel genericsAndIrreplacableBrandsBenefits = new ClassBenefitCasesModel(
				"Generics and Irreplacable Brands", "30", "%", new BigDecimal(300), currency, BigDecimal.ZERO, currency,
				BigDecimal.ONE, currency);
		ClassBenefitCasesModel replaceableBrandBenefits = new ClassBenefitCasesModel("Replaceable Brand", "40", "%",
				new BigDecimal(9999), currency, BigDecimal.ZERO, currency, BigDecimal.ONE, currency);
		benefitCases.add(inpatientBenefits);
		benefitCases.add(outpatientBenefits);
		benefitCases.add(genericsAndIrreplacableBrandsBenefits);
		benefitCases.add(replaceableBrandBenefits);
		return benefitCases;
	}

	private List<Long> generateListOfModuleConfiguration() {
		List<Long> moduleConfigurations = Arrays.stream(ModuleConfiguration.values()).map(ModuleConfiguration::value)
				.collect(Collectors.toList());
		return moduleConfigurations;
	}

	private void commonAssertionForApprovedPolicyResponse(PolicyResponseModel responseModel) {
		assertNotNull(responseModel);
		assertNotNull(responseModel.getRequestId());
		assertEquals(requestId, responseModel.getRequestId());
		assertNotNull(responseModel.getStatus());
		assertEquals(responseModel.getStatus(), PolicyResponseStatus.APPROVED.value());
		assertNotNull(responseModel.getStatusDescription());
		assertEquals(statusDescription, responseModel.getStatusDescription());
		assertNotNull(responseModel.getHttpStatusCode());
		assertEquals(responseModel.getHttpStatusCode(), String.valueOf(HttpStatus.OK.value()));
		assertNotNull(responseModel.getHttpStatusDescription());
		assertEquals(statusDescription, responseModel.getHttpStatusDescription());
		assertNull(responseModel.getRemainingLimit());
		assertNull(responseModel.getDenialCode());
		assertNull(responseModel.getDenialDescription());
		assertNull(responseModel.getPolicyNumber());
		assertNull(responseModel.getPolicyClass());
		assertTrue(StringUtils.isBlank(responseModel.getPolicyBenefit()));
		assertNull(responseModel.getBenefitLimitValue());
		assertNull(responseModel.getBenefitLimitCurrency());
		assertNull(responseModel.getMemberId());
		assertFalse(responseModel.getDrugList().isEmpty());
	}

	private void commonPolicyResponseForInvalidOrRejectedPrescription(PolicyResponseModel responseModel) {
		assertNotNull(responseModel);
		assertNull(responseModel.getRequestId());
		assertNotNull(responseModel.getStatus());
		assertNotNull(responseModel.getStatusDescription());
		assertNotNull(responseModel.getHttpStatusCode());
		assertEquals(responseModel.getHttpStatusCode(), String.valueOf(HttpStatus.OK.value()));
		assertNotNull(responseModel.getHttpStatusDescription());
		assertNull(responseModel.getRemainingLimit());
		assertNotNull(responseModel.getDenialCode());
		assertNotNull(responseModel.getDenialDescription());
		assertNull(responseModel.getPolicyNumber());
		assertNull(responseModel.getPolicyClass());
		assertTrue(StringUtils.isBlank(responseModel.getPolicyBenefit()));
		assertNull(responseModel.getBenefitLimitValue());
		assertNull(responseModel.getBenefitLimitCurrency());
		assertNull(responseModel.getMemberId());
		assertNull(responseModel.getDrugList());
	}
}
