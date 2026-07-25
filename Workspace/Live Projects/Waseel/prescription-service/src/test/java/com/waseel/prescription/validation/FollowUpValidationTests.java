package com.waseel.prescription.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waseel.prescription.expections.PrescriptionException;
import com.waseel.prescription.model.enums.BenefitCaseType;
import com.waseel.prescription.model.enums.Currency;
import com.waseel.prescription.model.enums.GenderType;
import com.waseel.prescription.model.enums.PhysicianCategory;
import com.waseel.prescription.model.enums.RequestStatusType;
import com.waseel.prescription.model.enums.RequestType;
import com.waseel.prescription.model.enums.UnitType;
import com.waseel.prescription.model.policyconsumption.DrugListModel;
import com.waseel.prescription.model.prescription.DiagnosisCodes;
import com.waseel.prescription.model.prescription.DrugList;
import com.waseel.prescription.model.prescription.PrescriptionRequestModel;
import com.waseel.prescription.persist.prescriptionservice.InvalidPrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.MemberInfo;
import com.waseel.prescription.persist.prescriptionservice.Physician;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.TransactionLog;
import com.waseel.prescription.repository.prescriptionservice.InvalidPrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.MemberInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.PhysicianRepository;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.TransactionLogRepository;
import com.waseel.prescription.service.validation.TechnicalValidationService;
import com.waseel.prescription.util.UserInfoUtil;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles({ "test" })
class FollowUpValidationTests {

	@Autowired
	private TechnicalValidationService technicalValidationService;

	@Autowired
	ObjectMapper mapper;

	@MockBean
	private TransactionLogRepository transactionLogRepository;

	@MockBean
	private PrescriptionRequestRepository prescriptionRequestRepository;

	@MockBean
	private PhysicianRepository physicianRepository;

	@MockBean
	private MemberInfoRepository memberInfoRepository;

	@MockBean
	private InvalidPrescriptionRequestRepository invalidPrescriptionRequestRepository;

	private PrescriptionRequestModel prescriptionRequestModel;
	private PrescriptionRequest prescriptionRequest;
	private Optional<MemberInfo> memberInfo;
	private Optional<Physician> physician;

	private TransactionLog transactionLog;
	private String requestId;
	private String validEPrescriptionNumber = "2023-1";
	private final MockHttpServletRequest request = new MockHttpServletRequest();
	private ContentCachingRequestWrapper cachingRequestWrapper = new ContentCachingRequestWrapper(request);
	private Timestamp timestamp = (new Timestamp(Calendar.getInstance().getTimeInMillis()));
	private String providerId = "801";
	private static final String memberName = "Salim";
	private static final String currency = Currency.SAR.value();
	private static final String drugListId = "1";
	private final String memberNationality = "Saudi Arabia";

	@BeforeAll
	public void setUpCommonData() {
		generateMockUserInfo();
		providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
	}

	@BeforeEach
	void setUpData() {
		requestId = "8890e044-a1ba-44c7-b0c7-86393cc5773b";
		prescriptionRequestModel = getPrescriptionRequestModel();
		prescriptionRequest = generatePrescriptionRequest();
		memberInfo = Optional.of(generateMemberInfo());
		physician = Optional.of(generatePhysician());
		transactionLog = generateTransactionLogWithvalidStatus();
		Mockito.when(transactionLogRepository.save(Mockito.any())).thenReturn(transactionLog);
		Mockito.when(prescriptionRequestRepository.save(Mockito.any())).thenReturn(prescriptionRequest);
		Mockito.when(memberInfoRepository.save(Mockito.any())).thenReturn(memberInfo);
		Mockito.when(physicianRepository.save(Mockito.any())).thenReturn(physician);
		assertNotNull(prescriptionRequest);
		assertNotNull(transactionLog);
		assertNotNull(memberInfo);
		assertNotNull(physician);
		when(prescriptionRequestRepository.findByRequestId(requestId)).thenReturn(Optional.of(prescriptionRequest));
		when(physicianRepository.findByRequestId(requestId)).thenReturn(physician);
		when(memberInfoRepository.findByRequestId(requestId)).thenReturn(memberInfo);
		when(transactionLogRepository.findByePrescriptionReferenceNumberWithValidStatus(validEPrescriptionNumber))
				.thenReturn(Optional.of(transactionLog));
		cachingRequestWrapper = getContentCachingRequestWrapper();
		Mockito.when(invalidPrescriptionRequestRepository.save(Mockito.any()))
				.thenReturn(generateInvalidPrescriptionRequest());
	}

	@Test
	void identifyFollowUpRequest() {
		boolean isFollowUp1 = technicalValidationService.identifyNewFollowUpRequest(validEPrescriptionNumber);
		boolean isFollowUp2 = technicalValidationService.identifyNewFollowUpRequest(null);
		assertThat(isFollowUp1).isTrue();
		assertThat(isFollowUp2).isFalse();
	}

	@Test
	void validateFollowUpRequestWithEmptyEpreRefNum() {
		prescriptionRequestModel.setePrescriptionReferenceNumber("");
		try {
			technicalValidationService.validateFollowUpRequest(prescriptionRequestModel, cachingRequestWrapper,
					providerId);
		} catch (PrescriptionException e) {
			String errorMsg = e.getInvalidResponse().getStatusDescription();
			assertThat(errorMsg).isEqualTo("EPrescriptionReferenceNumber can't be empty.");
		}
	}

	@Test
	void validateFollowUpRequestWithNotFoundEpreRefNum() {
		prescriptionRequestModel.setePrescriptionReferenceNumber("22222");
		try {
			technicalValidationService.validateFollowUpRequest(prescriptionRequestModel, cachingRequestWrapper,
					providerId);
		} catch (PrescriptionException e) {
			String errorMsg = e.getInvalidResponse().getStatusDescription();
			assertThat(errorMsg).isEqualTo("EPrescriptionReferenceNumber is not found or exists.");
		}
	}

	@Test
	void validateNotAllowToUpdateFields() {
		try {
			prescriptionRequestModel.setePrescriptionReferenceNumber(validEPrescriptionNumber);
			when(prescriptionRequestRepository.findByRequestId(requestId)).thenReturn(Optional.of(prescriptionRequest));
			when(physicianRepository.findByRequestId(requestId)).thenReturn(physician);
			when(memberInfoRepository.findByRequestId(requestId)).thenReturn(memberInfo);
			when(prescriptionRequestRepository.findByePrescriptionReferenceNumber(Mockito.any()))
					.thenReturn(Optional.of(prescriptionRequest));
			technicalValidationService.validateFollowUpRequest(prescriptionRequestModel, cachingRequestWrapper,
					providerId);
		} catch (PrescriptionException e) {
			String errorMsg = e.getInvalidResponse().getStatusDescription();
			String msg = "Don't allow to update ";
			assertThat(errorMsg).isEqualTo(msg + "PayerId, " + msg + "ProviderId, " + msg + "MemberId, " + msg
					+ "IdNumber, " + msg + "PolicyNumber, " + msg + "DateOfBirth, " + msg + "MemberWeight, " + msg
					+ "MemberHeight, " + msg + "MemberGender, " + msg + "PhysicianName, " + msg + "PhysicianCategory, "
					+ msg + "PhysicianLicenseNumber");
		}
	}

	private PrescriptionRequestModel getPrescriptionRequestModel() {
		List<DiagnosisCodes> diagnosisCodesList = new ArrayList<>();
		DiagnosisCodes code1 = new DiagnosisCodes("F31.6", "PRIMARY");
		DiagnosisCodes code2 = new DiagnosisCodes("R25.2", "SECONDARY");
		diagnosisCodesList.add(code1);
		diagnosisCodesList.add(code2);

		List<DrugList> drugLists = new ArrayList<>();
		DrugList drugList1 = new DrugList("31-277-98", UnitType.UNIT.value(), new BigDecimal(5), 10D, "HealthCare", "5",
				"once-daily", "others", "23/02/2023", "25/02/2023", drugListId);
		DrugList drugList2 = new DrugList("51-277-98", UnitType.PACKAGE.value(), new BigDecimal(4), 40D,
				"HealthCare Online", "15", "twice-daily", "others", "24/03/2023", "25/02/2023", drugListId);
		drugLists.add(drugList1);
		drugLists.add(drugList2);

		List<DrugListModel> policyConsumptionDrugList = new ArrayList<>();
		DrugListModel drugListModel = new DrugListModel("51-277-98", new BigDecimal(50), null, null, null, null);
		policyConsumptionDrugList.add(drugListModel);

		PrescriptionRequestModel request = new PrescriptionRequestModel("101", "1234", "001", "05/01/2002", "p001",
				"FEMALE", BigDecimal.valueOf(50), BigDecimal.valueOf(145), "33", "Dr.Abc", "Orthopedic",
				diagnosisCodesList, drugLists, memberName, "outpatient", policyConsumptionDrugList);
		return request;
	}

	private TransactionLog generateTransactionLogWithvalidStatus() {
		return new TransactionLog(2L, requestId, 51.11, RequestType.NEW.name(), "102", "12", "Received",
				validEPrescriptionNumber, null, null, new Timestamp(Calendar.getInstance().getTimeInMillis()),
				new Timestamp(Calendar.getInstance().getTimeInMillis()), null, "200", "APPROVED");
	}

	private PrescriptionRequest generatePrescriptionRequest() {
		return new PrescriptionRequest(requestId, "102", "12", new Timestamp(Calendar.getInstance().getTimeInMillis()),
				new Timestamp(Calendar.getInstance().getTimeInMillis()), RequestStatusType.APPROVED.value(), "",
				validEPrescriptionNumber, new BigDecimal(0), new BigDecimal(0), BenefitCaseType.INPATIENT.value(),
				currency, currency);
	}

	private MemberInfo generateMemberInfo() {
		return new MemberInfo("123", 123457890L, "123456", convertStringToDate("22/02/2000"), 51D, 146D,
				GenderType.MALE.value(), requestId, memberName, memberNationality);
	}

	private Physician generatePhysician() {
		return new Physician("32", requestId, "Dr.Test", PhysicianCategory.CONSULTANT.value(), "Test");
	}

	private Timestamp convertStringToDate(String dateStr) {
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		Date date = null;
		try {
			date = format.parse(dateStr);
			return new Timestamp(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	private ContentCachingRequestWrapper getContentCachingRequestWrapper() {
		ContentCachingRequestWrapper cachingRequestWrapper = null;
		try {
			MockHttpServletRequest hRequest = new MockHttpServletRequest();
			String req = mapper.writeValueAsString(prescriptionRequestModel);
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

	private InvalidPrescriptionRequest generateInvalidPrescriptionRequest() {
		return new InvalidPrescriptionRequest(1L, requestId, prescriptionRequest.getePrescriptionReferenceNumber(),
				timestamp, timestamp, null, null, null, 0, null, prescriptionRequest.getPayerId(),
				prescriptionRequest.getProviderId());
	}

	public void generateMockUserInfo() {
		Map<String, Object> details = new HashMap<String, Object>();
		details.put("accId", "accId");
		details.put("accName", "accName");
		details.put("accCode", "accCode");
		details.put("username", "username");
		details.put("email", "email");
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(authentication.getPrincipal()).thenReturn(details);
	}
}
