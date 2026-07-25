package com.waseel.prescription.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.waseel.prescription.expections.PrescriptionException;
import com.waseel.prescription.model.enums.BenefitCaseType;
import com.waseel.prescription.model.enums.CommonWords;
import com.waseel.prescription.model.enums.Currency;
import com.waseel.prescription.model.enums.FrequencyType;
import com.waseel.prescription.model.enums.RequestStatusType;
import com.waseel.prescription.model.enums.ServiceStatus;
import com.waseel.prescription.model.enums.UnitType;
import com.waseel.prescription.model.modifydecision.ModifyDecisionDrugList;
import com.waseel.prescription.model.modifydecision.ModifyDecisionRequestModel;
import com.waseel.prescription.model.prescription.ServiceDetailsModel;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.persist.prescriptionservice.ServiceInfo;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRequestRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceInfoRepository;
import com.waseel.prescription.repository.prescriptionservice.ServiceResponseInfoRepository;
import com.waseel.prescription.service.validation.PrescriptionUpdationValidationService;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
class ModifyDecisionValidationTest {

	@Autowired
	private PrescriptionUpdationValidationService prescriptionUpdationValidationService;
	@MockBean
	private PrescriptionRequestRepository prescriptionRequestRepository;
	@MockBean
	private ServiceInfoRepository serviceInfoRepository;

	@MockBean
	private ServiceResponseInfoRepository serviceResponseInfoRepository;

	private ModifyDecisionRequestModel modifyDecisionRequestModel;
	private PrescriptionRequest prescriptionRequest;
	private List<ServiceInfo> serviceInfoList;

	private Date date = new Date();
	private String ePrescriptionRefNum = "2023-01";
	private String payerId = "102";
	private String requestId = "8890e048-a1ba-44c7-b0c7-86393cc5773b";
	private static final String currency = Currency.SAR.value();
	private static final String scientificCode1 = "7000000687-6000000-200000016494";
	private static final String scientificCode2 = "7000000687-6000000-200000016495";
	private static final String scientificCode3 = "7000000687-6000000-200000016496";
	private static final Long id = 1L;
	private static final String unitType = UnitType.PACKAGE.value();
	private static final Double unitPrice = 45D;
	private static final BigDecimal quantity = BigDecimal.TEN;
	private static final BigDecimal requestedAmount = quantity.multiply(new BigDecimal(unitPrice));
	private static final String orderingClinician = "33";
	private static final Long duration = 1L;
	private static final String frequency = FrequencyType.EVERY_12_HOURS.value();
	private static final String frequencyOthersDescription = "";
	private static final Boolean isDeleted = Boolean.FALSE;
	private static final Double useUnitValue = 4D;
	private static final String drugsInRequestResponseMessage = "Please provide the decision for all the drugs["
			+ scientificCode1 + ", " + scientificCode2 + ", " + scientificCode3 + "].";
	private static final String drugCodeWithMissingDescription = "Decision description is mandatory for drugCode [46-172-05, 123-277-02]";
	private static final String scientificCodeWithMissingDescription = "Decision description is mandatory for drugCode ["
			+ scientificCode1 + ", " + scientificCode2 + ", " + scientificCode3 + "]";

	@BeforeEach
	public void setUpCommonData() {
		modifyDecisionRequestModel = generateModifyDecisionRequestModel();
		prescriptionRequest = generatePrescriptionRequest();
		serviceInfoList = generateListOfServiceInfo();
		Mockito.when(prescriptionRequestRepository.save(Mockito.any())).thenReturn(prescriptionRequest);
		Mockito.when(prescriptionRequestRepository.findByePrescriptionReferenceNumber(ePrescriptionRefNum))
				.thenReturn(Optional.of(prescriptionRequest));
		Mockito.when(serviceInfoRepository.saveAll(Mockito.any())).thenReturn(serviceInfoList);
		Mockito.when(serviceInfoRepository.findByIsDeletedAndRequestId(false, requestId)).thenReturn(serviceInfoList);
	}

	@Order(1)
	@Test
	@DisplayName("Validation for EPrescriptionReferenceNumber")
	void ePrescriptionReferenceNumberNotFoundValidation() {
		try {
			prescriptionUpdationValidationService
					.checkRequiredValidationToUpdatePrescription(modifyDecisionRequestModel.getDrugList(), "2023");
		} catch (PrescriptionException e) {
			assertThat(e.getMessage()).isEqualTo("EPrescriptionReferenceNumber is not found or exists.");
		}
	}

	@Order(2)
	@Test
	@DisplayName("Validation for PENDING Prescription Status")
	void prescriptionStatusValidation() {
		try {
			prescriptionRequest.setStatusCode(RequestStatusType.APPROVED.value());
			Mockito.when(prescriptionRequestRepository.save(Mockito.any())).thenReturn(prescriptionRequest);
			prescriptionUpdationValidationService.checkRequiredValidationToUpdatePrescription(
					modifyDecisionRequestModel.getDrugList(), ePrescriptionRefNum);
		} catch (PrescriptionException e) {
			assertThat(e.getMessage()).isEqualTo("Modification only allow for Pending Prescription");
		}
	}

	@Order(3)
	@Test
	@DisplayName("Validation for Drug match")
	void drugsValidation() {
		try {
			prescriptionUpdationValidationService.checkRequiredValidationToUpdatePrescription(
					modifyDecisionRequestModel.getDrugList(), ePrescriptionRefNum);
		} catch (PrescriptionException e) {
			assertThat(e.getMessage())
					.isEqualTo("Please provide the decision for all the drugs[46-172-05, 123-277-02].");
		}
	}

	@Order(4)
	@Test
	@DisplayName("Validation for Drug match with scientific code.")
	void drugsValidationForDrugsWithScientificCode() {
		try {
			serviceInfoList = generateServiceInfoDetailsForDrugsWithScientificCodes();
			Mockito.when(serviceInfoRepository.findByIsDeletedAndRequestId(Mockito.anyBoolean(), Mockito.any()))
					.thenReturn(serviceInfoList);
			modifyDecisionRequestModel.setDrugList(generatePayerDrugListWithScientificCode());
			modifyDecisionRequestModel.getDrugList().stream()
					.forEach(drug -> drug.setScientificCode(drug.getScientificCode() + "1"));
			prescriptionUpdationValidationService.checkRequiredValidationToUpdatePrescription(
					modifyDecisionRequestModel.getDrugList(), ePrescriptionRefNum);
		} catch (PrescriptionException e) {
			assertNotNull(e);
			String responseMessage = e.getMessage();
			assertNotNull(responseMessage);
			assertEquals(drugsInRequestResponseMessage, responseMessage);
			modifyDecisionRequestModel = generateModifyDecisionRequestModel();
		}
	}

	@Order(5)
	@Test
	@DisplayName("Mandatory eprescriptionStatus for request with drug codes.")
	void missingEPrescriptionForRequestWitDrugCode() {
		try {
			modifyDecisionRequestModel.getDrugList().stream().forEach(drug -> drug.setDecisionDescription(""));
			Mockito.when(serviceResponseInfoRepository.getIsNotDeletedDrugAndRequestId(Mockito.any(), Mockito.any()))
					.thenReturn(generateApprovedOrRejectedServiceDetails(Boolean.TRUE));
			prescriptionUpdationValidationService.checkRequiredValidationToUpdatePrescription(
					modifyDecisionRequestModel.getDrugList(), ePrescriptionRefNum);
		} catch (PrescriptionException e) {
			assertNotNull(e);
			String responseMessage = e.getMessage();
			assertNotNull(responseMessage);
			assertEquals(drugCodeWithMissingDescription, responseMessage);
			modifyDecisionRequestModel = generateModifyDecisionRequestModel();
		}
	}

	@Order(6)
	@Test
	@DisplayName("Mandatory eprescriptionStatus for request with scientific codes.")
	void missingEPrescriptionForRequestWitScientificCode() {
		try {
			modifyDecisionRequestModel.setDrugList(generatePayerDrugListWithScientificCode());
			modifyDecisionRequestModel.getDrugList().stream().forEach(drug -> drug.setDecisionDescription(""));
			Mockito.when(serviceResponseInfoRepository.getIsNotDeletedDrugAndRequestId(Mockito.any(), Mockito.any()))
					.thenReturn(generateApprovedOrRejectedServiceDetails(Boolean.FALSE));
			serviceInfoList = generateServiceInfoDetailsForDrugsWithScientificCodes();
			Mockito.when(serviceInfoRepository.findByIsDeletedAndRequestId(Mockito.anyBoolean(), Mockito.any()))
					.thenReturn(serviceInfoList);
			prescriptionUpdationValidationService.checkRequiredValidationToUpdatePrescription(
					modifyDecisionRequestModel.getDrugList(), ePrescriptionRefNum);
		} catch (PrescriptionException e) {
			assertNotNull(e);
			String responseMessage = e.getMessage();
			assertNotNull(responseMessage);
			assertEquals(scientificCodeWithMissingDescription, responseMessage);
			modifyDecisionRequestModel = generateModifyDecisionRequestModel();
		}
	}

	private PrescriptionRequest generatePrescriptionRequest() {
		String statusDesc = "46-172-05 is an expensive drug - this request requires a manual review from the payer.";
		PrescriptionRequest prescriptionRequest = new PrescriptionRequest(requestId, payerId, "55",
				new Timestamp(Calendar.getInstance().getTimeInMillis()),
				new Timestamp(Calendar.getInstance().getTimeInMillis()), RequestStatusType.PENDING.value(), statusDesc,
				ePrescriptionRefNum, new BigDecimal(0), new BigDecimal(0), BenefitCaseType.INPATIENT.value(), currency,
				currency);
		prescriptionRequest.setCanCancel(true);
		prescriptionRequest.setCanFollowUp(true);
		return prescriptionRequest;
	}

	private ModifyDecisionRequestModel generateModifyDecisionRequestModel() {
		ModifyDecisionRequestModel requestModel = new ModifyDecisionRequestModel();
		requestModel.setDrugList(generateModifyDecisionDrugList());
		return requestModel;
	}

	private List<ModifyDecisionDrugList> generateModifyDecisionDrugList() {
		List<ModifyDecisionDrugList> modifyDecisionDrugList = new ArrayList<>();
		modifyDecisionDrugList.add(new ModifyDecisionDrugList("46-172-05", UnitType.PACKAGE.value(), new BigDecimal(10),
				10D, 10D, FrequencyType.AS_NEEDED.value(), null, 5L, new BigDecimal(10), new BigDecimal(10),
				ServiceStatus.APPROVED.name(), "Payer has approved this drug 46-172-05"));
		modifyDecisionDrugList.add(new ModifyDecisionDrugList("123-277-02", UnitType.PACKAGE.value(),
				new BigDecimal(10), 10D, 10D, FrequencyType.AS_NEEDED.value(), null, 5L, new BigDecimal(10),
				new BigDecimal(10), ServiceStatus.APPROVED.name(), "Payer has approved this drug 123-277-02"));
		return modifyDecisionDrugList;
	}

	private List<ModifyDecisionDrugList> generatePayerDrugListWithScientificCode() {
		List<ModifyDecisionDrugList> modifyDecisionDrugList = new ArrayList<>();
		ModifyDecisionDrugList modifiedDrug1 = new ModifyDecisionDrugList("", UnitType.PACKAGE.value(),
				new BigDecimal(10), 10D, 10D, FrequencyType.AS_NEEDED.value(), null, 5L, new BigDecimal(10),
				new BigDecimal(10), ServiceStatus.APPROVED.name(), "Payer has approved this drug " + scientificCode1);
		modifiedDrug1.setScientificCode(scientificCode1);
		modifyDecisionDrugList.add(modifiedDrug1);
		ModifyDecisionDrugList modifiedDrug2 = new ModifyDecisionDrugList(null, UnitType.PACKAGE.value(),
				new BigDecimal(10), 10D, 10D, FrequencyType.AS_NEEDED.value(), null, 5L, new BigDecimal(10),
				new BigDecimal(10), ServiceStatus.APPROVED.name(), "Payer has approved this drug " + scientificCode2);
		modifiedDrug2.setScientificCode(scientificCode2);
		modifyDecisionDrugList.add(modifiedDrug2);
		ModifyDecisionDrugList modifiedDrug3 = new ModifyDecisionDrugList(CommonWords.UNDEFINED.value(),
				UnitType.PACKAGE.value(), new BigDecimal(10), 10D, 10D, FrequencyType.AS_NEEDED.value(), null, 5L,
				new BigDecimal(10), new BigDecimal(10), ServiceStatus.APPROVED.name(),
				"Payer has approved this drug " + scientificCode3);
		modifiedDrug3.setScientificCode(scientificCode3);
		modifyDecisionDrugList.add(modifiedDrug3);
		return modifyDecisionDrugList;
	}

	private List<ServiceInfo> generateListOfServiceInfo() {
		List<ServiceInfo> serviceInfoList = new ArrayList<>();
		serviceInfoList.add(new ServiceInfo(1L, "46-172-05", UnitType.PACKAGE.value(), 318.00, new BigDecimal(3),
				new BigDecimal(31.0), "test", date, date, 8L, "test", "test", requestId));
		serviceInfoList.add(new ServiceInfo(1L, "123-277-02", UnitType.UNIT.value(), 318.00, new BigDecimal(3),
				new BigDecimal(31.0), "test", date, date, 8L, "test", "test", requestId));
		return serviceInfoList;
	}

	private List<ServiceDetailsModel> generateApprovedOrRejectedServiceDetails(Boolean isDrugCode) {
		List<ServiceInfo> serviceInfoDetails = isDrugCode ? serviceInfoList
				: generateServiceInfoDetailsForDrugsWithScientificCodes();
		return serviceInfoDetails.stream()
				.map(serviceInfo -> new ServiceDetailsModel(serviceInfo.getDrugCode(),
						RequestStatusType.REJECTED.value(), serviceInfo.getScientificCode()))
				.collect(Collectors.toList());
	}

	private List<ServiceInfo> generateServiceInfoDetailsForDrugsWithScientificCodes() {
		List<ServiceInfo> serviceInfoList = new ArrayList<>();
		serviceInfoList.add(new ServiceInfo(id, "", unitType, unitPrice, quantity, requestedAmount, orderingClinician,
				date, date, duration, frequency, frequencyOthersDescription, requestId, isDeleted, unitType,
				useUnitValue, scientificCode1));
		serviceInfoList.add(new ServiceInfo(id + 1, null, unitType, unitPrice, quantity, requestedAmount,
				orderingClinician, date, date, duration, frequency, frequencyOthersDescription, requestId, isDeleted,
				unitType, useUnitValue, scientificCode2));
		serviceInfoList.add(new ServiceInfo(id + 2, CommonWords.UNDEFINED.value(), unitType, unitPrice, quantity,
				requestedAmount, orderingClinician, date, date, duration, frequency, frequencyOthersDescription,
				requestId, isDeleted, unitType, useUnitValue, scientificCode3));
		return serviceInfoList;
	}
}
