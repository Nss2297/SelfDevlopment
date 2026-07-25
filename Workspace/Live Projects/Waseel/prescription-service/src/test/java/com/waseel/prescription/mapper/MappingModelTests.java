package com.waseel.prescription.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import com.waseel.prescription.model.dss.DssDrugList;
import com.waseel.prescription.model.dss.DssRequest;
import com.waseel.prescription.model.dss.DssResponse;
import com.waseel.prescription.model.dss.Error;
import com.waseel.prescription.model.dss.Result;
import com.waseel.prescription.model.enums.Currency;
import com.waseel.prescription.model.enums.RequestStatusType;
import com.waseel.prescription.model.enums.ServiceStatus;
import com.waseel.prescription.model.enums.UnitType;
import com.waseel.prescription.model.policyconsumption.DrugListModel;
import com.waseel.prescription.model.policyconsumption.PolicyResponseModel;
import com.waseel.prescription.model.prescription.DiagnosisCodes;
import com.waseel.prescription.model.prescription.DrugList;
import com.waseel.prescription.model.prescription.MedicalValidations;
import com.waseel.prescription.model.prescription.PrescriptionRequestModel;
import com.waseel.prescription.model.prescription.PrescriptionResponseModel;
import com.waseel.prescription.model.prescription.ServiceResponse;
import com.waseel.prescription.repository.hira.DrugListServiceRepository;
import com.waseel.prescription.service.mapper.MapperService;
import com.waseel.prescription.util.UserInfoUtil;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles({ "test" })
class MappingModelTests {

	@Autowired
	private MapperService mapperService;

	@MockBean
	private DrugListServiceRepository drugListServiceRepository;

	private String providerId = "801";
	private static final String memberName = "Salim";
	private static final String currency = Currency.SAR.value();
	private static final String drugListId = "1";

	@BeforeAll
	public void setUpCommonData() {
		generateMockUserInfo();
		providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
	}

	@Test
	void mappingPrescriptionRequest() {
		PrescriptionRequestModel prescriptionRequestModel = getPrescriptionRequest();
		DssRequest dssRequest = mapperService.createDssRequest(prescriptionRequestModel, UUID.randomUUID().toString(),
				providerId);

		assertNotNull(dssRequest.getDrugList());
		assertNotNull(dssRequest.getIcdCodes());

		assertThat(dssRequest.getDrugList()).hasSize(2);
		assertThat(dssRequest.getIcdCodes()).hasSize(2);

		assertThat(dssRequest.getPayerId()).isEqualTo(prescriptionRequestModel.getPayerId());
		assertThat(dssRequest.getPrescriberId()).isEqualTo(providerId);
		assertThat(dssRequest.getMemberId()).isEqualTo(prescriptionRequestModel.getMemberId());
		assertThat(dssRequest.getMemberGender()).isEqualTo(prescriptionRequestModel.getMemberGender());
		assertThat(dssRequest.getMemberWeight()).isEqualTo(prescriptionRequestModel.getMemberWeight());
		assertThat(dssRequest.getPharmacyId()).isEqualTo(prescriptionRequestModel.getPhysicianLicenseNumber());
		assertThat(dssRequest.getDateOfBirth()).isEqualTo(prescriptionRequestModel.getDateOfBirth());

		DssDrugList dssDrugList = dssRequest.getDrugList().get(0);
		DrugList serviceResponse = prescriptionRequestModel.getDrugList().get(0);

		assertThat(dssRequest.getDateOfService()).isEqualTo("24/03/2023");
		assertThat(dssDrugList.getAmount()).isEqualTo(new BigDecimal("50.0"));
		assertThat(dssRequest.getIcdCodes().get(0))
				.isEqualTo(prescriptionRequestModel.getDiagnosisCodes().get(0).getDiagnosisCode());
		assertThat(dssDrugList.getDaysOfSupply()).isEqualTo(serviceResponse.getDuration());
		assertThat(dssDrugList.getDispensedQuantity()).isEqualTo(serviceResponse.getQuantity());
	}

	private PrescriptionRequestModel getPrescriptionRequest() {
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

		PrescriptionRequestModel request = new PrescriptionRequestModel("102", "123", "001", "05/01/2002", "p001",
				"FEMALE", BigDecimal.valueOf(50), BigDecimal.valueOf(145), "33", "Dr.Abc", "Orthopedic",
				diagnosisCodesList, drugLists, memberName, "inpatient", policyConsumptionDrugList);

		return request;
	}

	@Test
	void mappingPrescriptionResponse() {
		DssResponse dssResponse = getDSSResponseModel();
		PolicyResponseModel policyResponseModel = getPolicyResponseModel();
		PrescriptionResponseModel prescriptionResponse = mapperService.createPrescriptionResponse(dssResponse,
				getPrescriptionRequest(), "2023-1", policyResponseModel);
		assertNotNull(prescriptionResponse.getResults());
		assertNotNull(prescriptionResponse.getStatusDescription());

		assertThat(prescriptionResponse.getResults()).hasSize(2);

		assertThat(prescriptionResponse.getStatus()).isEqualTo(dssResponse.getStatus());
		assertThat(prescriptionResponse.getRequestId()).isEqualTo(dssResponse.getRequestId());

		ServiceResponse serviceResponse = prescriptionResponse.getResults().get(0);
		assertNull(serviceResponse.getErrors());

		ServiceResponse serviceResponse2 = prescriptionResponse.getResults().get(1);
		assertNotNull(serviceResponse2.getErrors());

		Result result = dssResponse.getResults().get(1);
		Error dssError = result.getErrors().get(0);

		assertThat(result.getErrors()).hasSize(2);
		MedicalValidations medicalValidations = serviceResponse2.getErrors().get(0);

		assertThat(serviceResponse2.getStatus()).isEqualTo(result.getStatus());
		assertThat(medicalValidations.getRejectionReason()).isEqualTo(dssError.getDescription());
		assertThat(medicalValidations.getDenialCode()).isEqualTo(dssError.getCode());
		assertThat(medicalValidations.getDrugCode()).isEqualTo(result.getNdcDrugCode());

	}

	private DssResponse getDSSResponseModel() {
		List<Error> errors = new ArrayList<>();
		Error error1 = new Error("Drug 51-277-98 is inconsistent with the patient's age", "FDB_CPAGE902");
		Error error2 = new Error("Medication 51-277-98 is not indicated with diagnosis code R25.2", "IDF_CPINDI001");
		errors.add(error1);
		errors.add(error2);

		List<Result> results = new ArrayList<>();
		Result result1 = new Result("31-277-98", new BigDecimal(5), new BigDecimal(50D), "5",
				ServiceStatus.APPROVED.name(), null);
		Result result2 = new Result("51-277-98", new BigDecimal(4), new BigDecimal(160D), "15",
				ServiceStatus.REJECTED.name(), errors);
		results.add(result1);
		results.add(result2);

		List<String> errorsList = new ArrayList<>();
		errorsList.add(error1.getDescription());
		errorsList.add(error2.getDescription());

		DssResponse response = new DssResponse(UUID.randomUUID().toString(), RequestStatusType.PARTIAL_APPROVED.value(),
				errorsList, results, HttpStatus.OK.value(), HttpStatus.OK.name());

		return response;
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

	private PolicyResponseModel getPolicyResponseModel() {
		List<DrugListModel> policyConsumptiondrugList = new ArrayList<>();
		DrugListModel drug = new DrugListModel("45-56-78", new BigDecimal("100"), null, null, currency, currency);
		policyConsumptiondrugList.add(drug);
		return new PolicyResponseModel("APPROVED", "APPROVED", "20", "100", "", "", String.valueOf(HttpStatus.OK.value()),
				"147954268", "789465", "", new BigDecimal("15000"), "SAR", "SAR", "APPROVED", "100", "123456789",
				policyConsumptiondrugList, currency, currency);
	}
}
