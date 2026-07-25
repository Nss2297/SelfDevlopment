package com.waseel.drugexclusionvalidationservice.exclusions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.waseel.drugexclusionvalidationservice.model.DrugExclusionModel;
import com.waseel.drugexclusionvalidationservice.model.DrugExclusionRequestModel;
import com.waseel.drugexclusionvalidationservice.model.enums.DenialCode;
import com.waseel.drugexclusionvalidationservice.model.enums.ServiceStatus;
import com.waseel.drugexclusionvalidationservice.persist.businessrules.CommonDenials;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.CommonDenialsRepository;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.DrugExclusionDetailsRepository;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.DrugExclusionMetadataRepository;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.HighCostExclusionAsscRepository;
import com.waseel.drugexclusionvalidationservice.service.exclusions.HighCostDrugsExclusionService;

@SpringBootTest
@ActiveProfiles("test")
class HighCostDrugsExclusionTest {

	@Autowired
	private HighCostDrugsExclusionService highCostDrugsExclusionService;

	@MockBean
	private CommonDenialsRepository commonDenialsRepository;

	@MockBean
	private HighCostExclusionAsscRepository highCostExclusionAsscRepository;

	@MockBean
	private DrugExclusionMetadataRepository drugExclusionMetadataRepository;

	@MockBean
	private DrugExclusionDetailsRepository drugExclusionDetailsRepository;

	private DrugExclusionRequestModel drugExclusionRequestModel;
	private CommonDenials commonDenials;

	private String requestId = "f90abad5-8c8d-4f61-afe9-36af91e30637";
	private String payerId = "102";
	private String providerId = "99999";
	private String physicianLicenseNumber = "12345";
	private String specialityName = "Emergency Medicine Specialty";
	private String commonStatusDesc = " is part of high cost exclusion list so it should undergo manual review by payer.";

	List<String> drugList = new ArrayList<>();
	List<String> rejectableDrugs = new ArrayList<>();

	@BeforeEach
	void setupData() {
		drugExclusionRequestModel = createSpecialityExclusionRequestModel();
		commonDenials = createCommonDenials();
		Mockito.when(commonDenialsRepository.save(Mockito.any())).thenReturn(commonDenials);
		Mockito.when(commonDenialsRepository.findByDenialCode(DenialCode.HIGH_COST_DRUGS_EXCLUSION.value()))
				.thenReturn(Optional.of(commonDenials));
	}

	@Test
	@DisplayName("Approved Drugs response")
	void checkHighCostDrugsExclusionForApproved() {
		drugList.add("23-883-19");
		drugList.add("31-277-98");
		Mockito.when(highCostExclusionAsscRepository
				.checkHighCostDrugsExclusionByPayerIdAndDrugList(Long.valueOf(payerId), new HashSet<>(drugList)))
				.thenReturn(rejectableDrugs);

		List<DrugExclusionModel> response = highCostDrugsExclusionService
				.checkHighCostDrugsExclusion(drugExclusionRequestModel);
		assertNotNull(response);
		assertThat(response).hasSize(2);
		DrugExclusionModel drug1Res = response.get(0);
		assertThat(drug1Res.getStatusCode()).isEqualTo(ServiceStatus.APPROVED.value());
		assertThat(drug1Res.getDrugCode()).isEqualTo("23-883-19");
		assertNull(drug1Res.getDenialCode());
		assertNull(drug1Res.getStatusDescription());

		DrugExclusionModel drug2Res = response.get(1);
		assertThat(drug2Res.getStatusCode()).isEqualTo(ServiceStatus.APPROVED.value());
		assertThat(drug2Res.getDrugCode()).isEqualTo("31-277-98");
		assertNull(drug2Res.getDenialCode());
		assertNull(drug2Res.getStatusDescription());
	}

	@Test
	@DisplayName("Approved And Rejected Drugs response")
	void checkHighCostDrugsExclusionForApprovedAndRejected() {
		drugList.add("23-883-19");
		drugList.add("12-23-45");
		rejectableDrugs.add("12-23-45");
		Mockito.when(highCostExclusionAsscRepository
				.checkHighCostDrugsExclusionByPayerIdAndDrugList(Long.valueOf(payerId),new HashSet<>(drugList)))
				.thenReturn(rejectableDrugs);
		List<DrugExclusionModel> response = highCostDrugsExclusionService
				.checkHighCostDrugsExclusion(drugExclusionRequestModel);
		assertNotNull(response);
		assertThat(response).hasSize(2);

		DrugExclusionModel drug1Res = response.get(0);
		assertThat(drug1Res.getStatusCode()).isEqualTo(ServiceStatus.APPROVED.value());
		assertThat(drug1Res.getDrugCode()).isEqualTo("23-883-19");
		assertNull(drug1Res.getDenialCode());
		assertNull(drug1Res.getStatusDescription());

		DrugExclusionModel drug3Res = response.get(1);
		assertThat(drug3Res.getStatusCode()).isEqualTo(ServiceStatus.REJECTED.value());
		assertThat(drug3Res.getDrugCode()).isEqualTo("12-23-45");
		assertNotNull(drug3Res.getDenialCode());
		assertNotNull(drug3Res.getStatusDescription());
		assertThat(drug3Res.getDenialCode()).isEqualTo(DenialCode.HIGH_COST_DRUGS_EXCLUSION.value());
		assertThat(drug3Res.getStatusDescription()).isEqualTo("12-23-45" + commonStatusDesc);
	}

	@Test
	@DisplayName("Rejected Drugs response")
	void checkHighCostDrugsExclusionForRejected() {
		drugList.add("22-33-55");
		drugList.add("12-23-45");
		rejectableDrugs.add("22-33-55");
		rejectableDrugs.add("12-23-45");
		Mockito.when(highCostExclusionAsscRepository
				.checkHighCostDrugsExclusionByPayerIdAndDrugList(Long.valueOf(payerId),new HashSet<>(drugList)))
				.thenReturn(rejectableDrugs);
		List<DrugExclusionModel> response = highCostDrugsExclusionService
				.checkHighCostDrugsExclusion(drugExclusionRequestModel);
		assertNotNull(response);
		assertThat(response).hasSize(2);

		DrugExclusionModel drug1Res = response.get(0);
		assertThat(drug1Res.getStatusCode()).isEqualTo(ServiceStatus.REJECTED.value());
		assertNotNull(drug1Res.getDenialCode());
		assertNotNull(drug1Res.getStatusDescription());
		assertThat(drug1Res.getDenialCode()).isEqualTo(DenialCode.HIGH_COST_DRUGS_EXCLUSION.value());
		assertThat(drug1Res.getStatusDescription()).isEqualTo("22-33-55" + commonStatusDesc);

		DrugExclusionModel drug2Res = response.get(1);
		assertThat(drug2Res.getStatusCode()).isEqualTo(ServiceStatus.REJECTED.value());
		assertNotNull(drug2Res.getDenialCode());
		assertNotNull(drug2Res.getStatusDescription());
		assertThat(drug2Res.getDenialCode()).isEqualTo(DenialCode.HIGH_COST_DRUGS_EXCLUSION.value());
		assertThat(drug2Res.getStatusDescription()).isEqualTo("12-23-45" + commonStatusDesc);
	}

	private DrugExclusionRequestModel createSpecialityExclusionRequestModel() {
		return new DrugExclusionRequestModel(requestId, physicianLicenseNumber, drugList, specialityName, payerId,
				providerId);
	}

	private CommonDenials createCommonDenials() {
		return new CommonDenials(1L, DenialCode.HIGH_COST_DRUGS_EXCLUSION.value(),
				"<drugcode> <DrugName>" + commonStatusDesc);
	}
}
