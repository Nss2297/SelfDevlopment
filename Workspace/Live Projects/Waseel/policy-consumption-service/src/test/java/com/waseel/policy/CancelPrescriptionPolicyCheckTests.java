package com.waseel.policy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import com.waseel.policy.enums.BenefitCode;
import com.waseel.policy.enums.BusinessRulesPrivilage;
import com.waseel.policy.enums.EntitiesName;
import com.waseel.policy.enums.PolicyConsumptionDenialCodes;
import com.waseel.policy.enums.PolicyResponseStatus;
import com.waseel.policy.enums.PolicyResponseStatusDescription;
import com.waseel.policy.enums.RequestType;
import com.waseel.policy.exception.PolicyException;
import com.waseel.policy.model.CancellAndDispensePolicyRequestModel;
import com.waseel.policy.model.PolicyResponseModel;
import com.waseel.policy.model.client.ClassBenefitCasesModel;
import com.waseel.policy.model.client.MemberDetailsResponseModel;
import com.waseel.policy.model.client.PbmPayerApiResponseModel;
import com.waseel.policy.model.client.PolicyClassBenefitsModel;
import com.waseel.policy.model.client.PolicyDetailsModel;
import com.waseel.policy.persist.businessrules.AuditLog;
import com.waseel.policy.persist.businessrules.CommonDenial;
import com.waseel.policy.persist.businessrules.PrescriptionMetadata;
import com.waseel.policy.persist.businessrules.TransactionLog;
import com.waseel.policy.persist.hira.AccountToAccountAssociation;
import com.waseel.policy.persist.hira.AccountToAccountAssociationId;
import com.waseel.policy.repository.businessrules.AuditLogRepository;
import com.waseel.policy.repository.businessrules.CommonDenialsRepository;
import com.waseel.policy.repository.businessrules.ModuleConfigurationRepository;
import com.waseel.policy.repository.businessrules.PrescriptionMetadataRepository;
import com.waseel.policy.repository.businessrules.TransactionLogRepository;
import com.waseel.policy.repository.hira.AccountToAccountAssociationRepository;
import com.waseel.policy.service.PolicyConsumptionService;
import com.waseel.policy.service.clienthandler.PbmPayerApisRestHandler;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles({ "test" })
class CancelPrescriptionPolicyCheckTests {

	@Autowired
	private PolicyConsumptionService policyConsumptionService;

	@Autowired
	ObjectMapper mapper;

	@MockBean
	private PrescriptionMetadataRepository prescriptionMetadataRepository;

	@MockBean
	private TransactionLogRepository transactionLogRepository;

	@MockBean
	private CommonDenialsRepository commonDenialsRepository;

	@MockBean
	private ModuleConfigurationRepository moduleConfigurationRepository;

	@MockBean
	private AccountToAccountAssociationRepository accountToAccountAssociationRepository;

	@MockBean
	private AuditLogRepository auditLogRepository;

	@MockBean
	private PbmPayerApisRestHandler pbmPayerApisRestHandler;

	private String payerId = "102";
	private String requestId = "0fb78ee7-9e62-4d9e-aee7-18f099e126f1";
	private String idNumber = "2392019564";
	private String currency = "SAR";
	private Long id = 1L;

	private AccountToAccountAssociation accountToAccountAssociation = null;
	private Date date = new Date();
	private Timestamp timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
	private String policyNumber = "24735173";
	private String providerId = "801";
	private final MockHttpServletRequest request = new MockHttpServletRequest();
	protected static final int CONTENT_CACHE_LIMIT = 13;
	private ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(this.request,
			CONTENT_CACHE_LIMIT);
	private CancellAndDispensePolicyRequestModel policyCancellationRequestModel = null;
	private PolicyResponseModel policyResponseModel = null;
	private TransactionLog transactionLog = null;
	private PbmPayerApiResponseModel payerApiResponseModel = null;
	private PrescriptionMetadata prescriptionMetadata = null;
	private AuditLog auditLog = null;

	@BeforeAll
	private void commonData() {
		policyCancellationRequestModel = generatePolicyCancellationRequestModel();
		requestWrapper = getContentCachingRequestWrapper();
		policyResponseModel = populatePolicyResponseModel();
		transactionLog = generateTransactionLog();
		payerApiResponseModel = populatePbmPayerApiResponseModel();
		prescriptionMetadata = populatePrescriptionMetadata();
		auditLog = populateAuditLog();
		accountToAccountAssociation = populateAccountToAccountAssociation();
	}

	@BeforeEach
	private void setUpData() {
		Mockito.when(accountToAccountAssociationRepository.findByIdSourceAndIdDestinationsAndIsEnabled(Mockito.any(),
				Mockito.any(), Mockito.anyBoolean())).thenReturn(accountToAccountAssociation);
		Mockito.when(pbmPayerApisRestHandler.getMemberDetails(Mockito.anyLong(), Mockito.anyString()))
				.thenReturn(payerApiResponseModel);
		Mockito.when(transactionLogRepository.save(Mockito.any())).thenReturn(transactionLog);
		Mockito.when(moduleConfigurationRepository.findByProviderIdAndPayerIdAndIsEnabled(Mockito.anyLong(),
				Mockito.anyLong())).thenReturn(generateEmptyListOfModuleConfiguration());
		Mockito.when(prescriptionMetadataRepository.findByRequestId(Mockito.any()))
				.thenReturn(Optional.of(prescriptionMetadata));
		Mockito.when(prescriptionMetadataRepository.save(Mockito.any())).thenReturn(prescriptionMetadata);
		Mockito.when(auditLogRepository.save(Mockito.any())).thenReturn(auditLog);
	}

	@Order(1)
	@Test
	@DisplayName("Cancelled checked successfully")
	void policyConsumptionCancellationCheck() throws PolicyException {
		PolicyResponseModel responseModel = policyConsumptionService.policyCheckForCancellation(idNumber,
				policyCancellationRequestModel.getRequestId(), policyCancellationRequestModel.getPayerId(),
				policyCancellationRequestModel.getBenefitCase(), policyCancellationRequestModel.getBenefitCode(),
				policyCancellationRequestModel.getProviderId(), requestWrapper);
		commonAssertionResponseForApprovedResult(responseModel);
		assertEquals(responseModel.getHttpStatusDescription(),
				PolicyResponseStatusDescription.PASSED_POLICY_CHECK.value());
		assertEquals("0", prescriptionMetadata.getActivePrescription());
		prescriptionMetadata.setActivePrescription("1");
	}

	@Order(2)
	@Test
	@DisplayName("Invalid Prescription")
	void invalidPrescription() {
		Mockito.when(prescriptionMetadataRepository.findByRequestId(Mockito.any())).thenReturn(Optional.empty());
		Mockito.when(commonDenialsRepository.findByDenialCode(Mockito.any())).thenReturn(
				Optional.of(generateCommonDenial(PolicyConsumptionDenialCodes.BR_PC_INVALID_PRESCRIPTION.value(),
						idNumber + " has invalid prescription.")));
		PolicyResponseModel invalidResponse = null;
		try {
			invalidResponse = policyConsumptionService.policyCheckForCancellation(idNumber,
					policyCancellationRequestModel.getRequestId(), policyCancellationRequestModel.getPayerId(),
					policyCancellationRequestModel.getBenefitCase(), policyCancellationRequestModel.getBenefitCode(),
					policyCancellationRequestModel.getProviderId(), requestWrapper);
		} catch (PolicyException exception) {
			invalidResponse = exception.getInvalidResponse();
			commonAssertionResponseForFailedOrInvalidOrRejected(invalidResponse);
			assertEquals(invalidResponse.getStatus(), PolicyResponseStatus.INVALID.value());
			assertEquals(invalidResponse.getStatusDescription(),
					PolicyResponseStatusDescription.INVALID_PRESCRIPTION.value());
			assertEquals(invalidResponse.getHttpStatusCode(), String.valueOf(HttpStatus.BAD_REQUEST.value()));
			assertEquals(invalidResponse.getHttpStatusDescription(),
					PolicyResponseStatusDescription.INVALID_PRESCRIPTION.value());
			assertEquals(invalidResponse.getDenialCode(),
					PolicyConsumptionDenialCodes.BR_PC_INVALID_PRESCRIPTION.value());
			assertEquals(invalidResponse.getDenialDescription(), idNumber + " has invalid prescription.");
		}
	}

	@Order(3)
	@Test
	@DisplayName("Inactive prescription.")
	void inactivcePrescription() {
		prescriptionMetadata.setActivePrescription("0");
		PolicyResponseModel invalidResponse = null;
		Mockito.when(commonDenialsRepository.findByDenialCode(Mockito.any()))
				.thenReturn(Optional.of(generateCommonDenial(PolicyConsumptionDenialCodes.BR_PC_INACTIVE.value(),
						idNumber + "  has an inactive request.")));
		try {
			invalidResponse = policyConsumptionService.policyCheckForCancellation(idNumber,
					policyCancellationRequestModel.getRequestId(), policyCancellationRequestModel.getPayerId(),
					policyCancellationRequestModel.getBenefitCase(), policyCancellationRequestModel.getBenefitCode(),
					policyCancellationRequestModel.getProviderId(), requestWrapper);
		} catch (PolicyException exception) {
			invalidResponse = exception.getInvalidResponse();
			commonAssertionResponseForFailedOrInvalidOrRejected(invalidResponse);
			assertEquals(invalidResponse.getStatus(), PolicyResponseStatus.REJECTED.value());
			assertEquals(invalidResponse.getStatusDescription(),
					PolicyResponseStatusDescription.REQUEST_IS_INACTIVE.value());
			assertEquals(invalidResponse.getHttpStatusCode(), String.valueOf(HttpStatus.OK.value()));
			assertEquals(invalidResponse.getHttpStatusDescription(),
					PolicyResponseStatusDescription.REQUEST_IS_INACTIVE.value());
			assertEquals(invalidResponse.getDenialCode(), PolicyConsumptionDenialCodes.BR_PC_INACTIVE.value());
			assertEquals(invalidResponse.getDenialDescription(), idNumber + "  has an inactive request.");
			prescriptionMetadata.setActivePrescription("1");
		}
	}

	@Order(4)
	@Test
	@DisplayName("No remaining limit in policy.")
	void noRemainingLimitForPolicy() {
		PolicyDetailsModel policyDetailsModel = payerApiResponseModel.getMemberDetailsResponseModel()
				.getPolicyInformation().get(0);
		policyDetailsModel.setRemainingLimitValue(BigDecimal.ZERO);
		PolicyResponseModel invalidResponse = null;
		Mockito.when(commonDenialsRepository.findByDenialCode(Mockito.any())).thenReturn(
				Optional.of(generateCommonDenial(PolicyConsumptionDenialCodes.BR_PC_NO_REMAINING_LIMIT.value(),
						idNumber + "  has no remaining limit left.")));
		try {
			invalidResponse = policyConsumptionService.policyCheckForCancellation(idNumber,
					policyCancellationRequestModel.getRequestId(), policyCancellationRequestModel.getPayerId(),
					policyCancellationRequestModel.getBenefitCase(), policyCancellationRequestModel.getBenefitCode(),
					policyCancellationRequestModel.getProviderId(), requestWrapper);
		} catch (PolicyException exception) {
			invalidResponse = exception.getInvalidResponse();
			commonAssertionResponseForFailedOrInvalidOrRejected(invalidResponse);
			assertEquals(invalidResponse.getStatus(), PolicyResponseStatus.REJECTED.value());
			assertEquals(invalidResponse.getStatusDescription(),
					PolicyResponseStatusDescription.NO_REMAINING_LIMIT.value());
			assertEquals(invalidResponse.getHttpStatusCode(), String.valueOf(HttpStatus.OK.value()));
			assertEquals(invalidResponse.getHttpStatusDescription(),
					PolicyResponseStatusDescription.NO_REMAINING_LIMIT.value());
			assertEquals(invalidResponse.getDenialCode(),
					PolicyConsumptionDenialCodes.BR_PC_NO_REMAINING_LIMIT.value());
			assertEquals(invalidResponse.getDenialDescription(), idNumber + "  has no remaining limit left.");
			payerApiResponseModel.getMemberDetailsResponseModel().getPolicyInformation().get(0)
					.setRemainingLimitValue(BigDecimal.valueOf(15000));
		}
	}

	@Order(5)
	@Test
	@DisplayName("Expired prescription.")
	void expiredPrescription() {
		prescriptionMetadata.setPolicyNumber(BigDecimal.TEN);
		PolicyResponseModel invalidResponse = null;
		Mockito.when(commonDenialsRepository.findByDenialCode(Mockito.any()))
				.thenReturn(Optional.of(generateCommonDenial(PolicyConsumptionDenialCodes.BR_PC_EXPIRED.value(),
						idNumber + "  request has expired.")));
		try {
			invalidResponse = policyConsumptionService.policyCheckForCancellation(idNumber,
					policyCancellationRequestModel.getRequestId(), policyCancellationRequestModel.getPayerId(),
					policyCancellationRequestModel.getBenefitCase(), policyCancellationRequestModel.getBenefitCode(),
					policyCancellationRequestModel.getProviderId(), requestWrapper);
		} catch (PolicyException exception) {
			invalidResponse = exception.getInvalidResponse();
			commonAssertionResponseForFailedOrInvalidOrRejected(invalidResponse);
			assertEquals(invalidResponse.getStatus(), PolicyResponseStatus.REJECTED.value());
			assertEquals(invalidResponse.getStatusDescription(),
					PolicyResponseStatusDescription.EXPIRED_PRESCRIPTION.value());
			assertEquals(invalidResponse.getHttpStatusCode(), String.valueOf(HttpStatus.OK.value()));
			assertEquals(invalidResponse.getHttpStatusDescription(),
					PolicyResponseStatusDescription.EXPIRED_PRESCRIPTION.value());
			assertEquals(invalidResponse.getDenialCode(), PolicyConsumptionDenialCodes.BR_PC_EXPIRED.value());
			assertEquals(invalidResponse.getDenialDescription(), idNumber + "  request has expired.");
			prescriptionMetadata.setPolicyNumber(new BigDecimal(policyNumber));
		}
	}

	private CancellAndDispensePolicyRequestModel generatePolicyCancellationRequestModel() {
		return new CancellAndDispensePolicyRequestModel(BenefitCode.DENTAL.value(), "INPATIENT", payerId, requestId,
				null, providerId, RequestType.CANCELLATION.value());
	}

	private ContentCachingRequestWrapper getContentCachingRequestWrapper() {
		ContentCachingRequestWrapper cachingRequestWrapper = null;
		try {
			MockHttpServletRequest hRequest = new MockHttpServletRequest();
			String req = this.mapper.writeValueAsString(policyCancellationRequestModel);
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

	private AccountToAccountAssociation populateAccountToAccountAssociation() {
		AccountToAccountAssociationId id = new AccountToAccountAssociationId(new BigDecimal("102"),
				new BigDecimal("602"));
		return new AccountToAccountAssociation(id, true, true, "8587");
	}

	private PolicyResponseModel populatePolicyResponseModel() {
		PolicyResponseModel responseModel = new PolicyResponseModel();
		responseModel.setBenefitLimitCurrency(currency);
		responseModel.setBenefitLimitValue(new BigDecimal(2000));
		responseModel.setBenefitLimitCurrency(currency);
		responseModel.setDenialCode("");
		responseModel.setDenialDescription("");
		responseModel.setHttpStatusCode(String.valueOf(HttpStatus.OK.value()));
		responseModel.setHttpStatusDescription("");
		responseModel.setPolicyBenefit("policyBenefit");
		responseModel.setPolicyClass("a");
		responseModel.setPolicyNumber(policyNumber);
		responseModel.setRemainingLimit("2000");
		responseModel.setRequestId(requestId);
		responseModel.setStatus(PolicyResponseStatus.APPROVED.value());
		responseModel.setStatusDescription(PolicyResponseStatusDescription.PASSED_POLICY_CHECK.value());
		return responseModel;
	}

	private TransactionLog generateTransactionLog() {
		TransactionLog transactionLog = new TransactionLog();
		transactionLog.setHttpStatus("200");
		transactionLog.setHttpStatusDescription(policyResponseModel.getHttpStatusDescription());
		transactionLog.setPayerId(payerId);
		transactionLog.setProviderId(providerId);
		transactionLog.setReceivingRequestDateTime(timestamp);
		transactionLog.setRequestId(requestId);
		transactionLog.setSendingResponseDateTime(timestamp);
		transactionLog.setStatus("APPROVED");
		transactionLog.setStatusDescription(policyResponseModel.getStatusDescription());
		transactionLog
				.setTransactionId(new BigDecimal(BusinessRulesPrivilage.PRESCRIPTION_CANCELLATION_PRIVILAGE.value()));
		transactionLog.setTransactionLogId(id);
		transactionLog.setTransactionReferenceNumber("47984272");
		transactionLog.setTransactionStatus("Sent");
		transactionLog.setTransactionType("POLICY_CONSUMPTION");
		transactionLog.setReceivingRequestDateTime(timestamp);
		transactionLog.setSendingResponseDateTime(timestamp);
		return transactionLog;
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
		PolicyDetailsModel policyDetailsModel = new PolicyDetailsModel(policyNumber, "Marissa Wagner",
				"001014523658001", "PRINCIPAL", "GL/BS", "Gold B", date, date, new BigDecimal(15000), currency,
				classBenefits);
		return policyDetailsModel;
	}

	private PolicyClassBenefitsModel populatePolicyClassBenefitsModel() {
		ClassBenefitCasesModel classBenefitCasesModel = populateClassBenefitCasesModel();
		List<ClassBenefitCasesModel> benefitCases = new ArrayList<ClassBenefitCasesModel>();
		benefitCases.add(classBenefitCasesModel);
		PolicyClassBenefitsModel classBenefitsModel = new PolicyClassBenefitsModel("DENTAL", "Dental Benefit",
				new BigDecimal(5000), currency, "10", "%", new BigDecimal(500), currency, new BigDecimal(0), currency,
				new BigDecimal(1000), currency, new BigDecimal(15000), currency, benefitCases);
		return classBenefitsModel;
	}

	private ClassBenefitCasesModel populateClassBenefitCasesModel() {
		ClassBenefitCasesModel classBenefitCasesModel = new ClassBenefitCasesModel("OUTPATIENT", "10", "%",
				new BigDecimal(200), currency, BigDecimal.ZERO, currency, BigDecimal.ONE, currency);
		return classBenefitCasesModel;
	}

	private List<Long> generateEmptyListOfModuleConfiguration() {
		return new ArrayList<>();
	}

	private PrescriptionMetadata populatePrescriptionMetadata() {
		PrescriptionMetadata prescriptionMetadata = new PrescriptionMetadata();
		prescriptionMetadata.setId(id);
		prescriptionMetadata.setRequestId(requestId);
		prescriptionMetadata.setPatientShare(new BigDecimal(7.5));
		prescriptionMetadata.setPayerShare(new BigDecimal(42.5));
		prescriptionMetadata.setRemainingLimit(new BigDecimal(180));
		prescriptionMetadata.setBenefitLimitValue(new BigDecimal(2000));
		prescriptionMetadata.setBenefitLimitCurr(currency);
		prescriptionMetadata.setPolicyNumber(new BigDecimal(policyNumber));
		prescriptionMetadata.setActivePrescription("1");
		prescriptionMetadata.setUpdateDate(timestamp);
		prescriptionMetadata.setPrescriptionValue(new BigDecimal(50));
		return prescriptionMetadata;
	}

	private AuditLog populateAuditLog() {
		return new AuditLog(id, "policy-consumption-service", date, id, EntitiesName.PRESCRIPTION_METADATA.value(),
				"UPDATE", "");
	}

	private CommonDenial generateCommonDenial(String code, String description) {
		CommonDenial commonDenial = new CommonDenial();
		commonDenial.setCommonDenialsId(0);
		commonDenial.setDenialCode(code);
		commonDenial.setDenialDescription(description);
		return commonDenial;
	}

	private void commonAssertionResponseForApprovedResult(PolicyResponseModel responseModel) {
		assertNotNull(responseModel);
		assertNotNull(responseModel.getRequestId());
		assertNotNull(responseModel.getStatus());
		assertEquals(responseModel.getStatus(), PolicyResponseStatus.APPROVED.value());
		assertNotNull(responseModel.getStatusDescription());
		assertEquals(responseModel.getStatusDescription(), PolicyResponseStatusDescription.PASSED_POLICY_CHECK.value());
		assertNotNull(responseModel.getHttpStatusCode());
		assertEquals(responseModel.getHttpStatusCode(), String.valueOf(HttpStatus.OK.value()));
		assertNotNull(responseModel.getHttpStatusDescription());
		assertNotNull(responseModel.getRemainingLimit());
		assertNull(responseModel.getDenialCode());
		assertNull(responseModel.getDenialDescription());
		assertNotNull(responseModel.getPolicyNumber());
		assertNotNull(responseModel.getPolicyClass());
		assertTrue(StringUtils.isBlank(responseModel.getPolicyBenefit()));
		assertNotNull(responseModel.getBenefitLimitValue());
		assertNotNull(responseModel.getBenefitLimitCurrency());
		assertNotNull(responseModel.getMemberId());
		assertNull(responseModel.getDrugList());
	}

	private void commonAssertionResponseForFailedOrInvalidOrRejected(PolicyResponseModel invalidResponse) {
		assertNotNull(invalidResponse);
		assertTrue(StringUtils.isBlank(invalidResponse.getRequestId()));
		assertNotNull(invalidResponse.getStatus());
		assertNotNull(invalidResponse.getStatusDescription());
		assertNotNull(invalidResponse.getHttpStatusCode());
		assertNotNull(invalidResponse.getHttpStatusDescription());
		assertTrue(StringUtils.isBlank(invalidResponse.getRemainingLimit()));
		assertNotNull(invalidResponse.getDenialCode());
		assertNotNull(invalidResponse.getDenialDescription());
		assertTrue(StringUtils.isBlank(invalidResponse.getPolicyNumber()));
		assertTrue(StringUtils.isBlank(invalidResponse.getPolicyClass()));
		assertTrue(StringUtils.isBlank(invalidResponse.getPolicyBenefit()));
		assertNull(invalidResponse.getBenefitLimitValue());
		assertTrue(StringUtils.isBlank(invalidResponse.getBenefitLimitCurrency()));
		assertTrue(StringUtils.isBlank(invalidResponse.getMemberId()));
		assertNull(invalidResponse.getDrugList());
	}
}
