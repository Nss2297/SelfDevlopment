package com.waseel.drugexclusionvalidationservice.exclusions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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
import com.waseel.drugexclusionvalidationservice.persist.businessrules.DeptSpecPhyscAssc;
import com.waseel.drugexclusionvalidationservice.persist.businessrules.DrugExclusionDetails;
import com.waseel.drugexclusionvalidationservice.persist.businessrules.DrugExclusionMetadata;
import com.waseel.drugexclusionvalidationservice.persist.businessrules.PhysicianCategory;
import com.waseel.drugexclusionvalidationservice.persist.businessrules.PhysicianInfo;
import com.waseel.drugexclusionvalidationservice.persist.businessrules.Speciality;
import com.waseel.drugexclusionvalidationservice.persist.businessrules.SpecialityExclusionAssc;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.CommonDenialsRepository;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.DeptSpecPhyscAsscRepository;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.DrugExclusionDetailsRepository;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.DrugExclusionMetadataRepository;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.PhysicianInfoRepository;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.SpecialityExclusionAsscRepository;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.SpecialityRepository;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.TransactionLogRepository;
import com.waseel.drugexclusionvalidationservice.service.exclusions.SpecialityExclusionService;

@SpringBootTest
@ActiveProfiles("test")
class SpecialityExclusionTests {

	@Autowired
	private SpecialityExclusionService specialityExclusionService;

	@MockBean
	private PhysicianInfoRepository physicianInfoRepository;

	@MockBean
	private SpecialityRepository specialityRepository;

	@MockBean
	private TransactionLogRepository transactionLogRepository;

	@MockBean
	private DeptSpecPhyscAsscRepository deptSpecPhyscAsscRepository;

	@MockBean
	private SpecialityExclusionAsscRepository specialityExclusionAsscRepository;

	@MockBean
	private DrugExclusionDetailsRepository drugExclusionDetailsRepository;

	@MockBean
	private CommonDenialsRepository commonDenialsRepository;

	@MockBean
	private DrugExclusionMetadataRepository drugExclusionMetadataRepository;

	private PhysicianInfo physicianInfo;
	private Speciality speciality;
	private DeptSpecPhyscAssc deptSpecPhyscAssc;
	private SpecialityExclusionAssc specialityExclusionAssc;
	private List<SpecialityExclusionAssc> specialityExclusionList = new ArrayList<>();;
	private List<DrugExclusionDetails> drugExclusionDetailsList;
	private CommonDenials commonDenials;
	private DrugExclusionRequestModel drugExclusionRequestModel;
	private DrugExclusionMetadata drugExclusionMetadata;

	private String requestId = "f90abad5-8c8d-4f61-afe9-36af91e30637";
	private String payerId = "102";
	private String providerId = "99999";
	private String physicianLicenseNumber = "12345";
	private String specialityName = "Emergency Medicine Specialty";

	List<String> drugList = new ArrayList<>();

	@BeforeEach
	void setupData() {
		setRelatedData();
		Mockito.when(physicianInfoRepository.save(Mockito.any())).thenReturn(physicianInfo);
		Mockito.when(specialityRepository.save(Mockito.any())).thenReturn(speciality);
		Mockito.when(deptSpecPhyscAsscRepository.save(Mockito.any())).thenReturn(deptSpecPhyscAssc);
		Mockito.when(specialityExclusionAsscRepository.save(Mockito.any())).thenReturn(specialityExclusionAssc);
		Mockito.when(commonDenialsRepository.save(Mockito.any())).thenReturn(commonDenials);
		Mockito.when(drugExclusionMetadataRepository.save(Mockito.any())).thenReturn(drugExclusionMetadata);

		assertNotNull(physicianInfo);
		assertNotNull(speciality);
		assertNotNull(deptSpecPhyscAssc);
		assertNotNull(specialityExclusionAssc);
		mockNeededRepositories();
	}

	@Test
	@DisplayName("Approved Drugs response")
	void checkSpecialityExclusionForApproved() {
		drugList.add("123-325-04");
		drugList.add("23-883-19");
		List<DrugExclusionModel> response = specialityExclusionService
				.checkSpecialityExclusion(drugExclusionRequestModel);
		assertNotNull(response);
		assertThat(response).hasSize(2);

		DrugExclusionModel drug1Res = response.get(0);
		assertThat(drug1Res.getStatusCode()).isEqualTo(ServiceStatus.APPROVED.value());
		assertThat(drug1Res.getDrugCode()).isEqualTo("123-325-04");
		assertNull(drug1Res.getDenialCode());
		assertNull(drug1Res.getStatusDescription());

		DrugExclusionModel drug2Res = response.get(1);
		assertThat(drug2Res.getStatusCode()).isEqualTo(ServiceStatus.APPROVED.value());
		assertNull(drug2Res.getDenialCode());
		assertNull(drug2Res.getStatusDescription());
	}

	@Test
	@DisplayName("Approved And Rejected drugs response ")
	void checkSpecialityExclusionForApprovedAndRejected() {
		drugList.add("123-325-04");
		drugList.add("23-883-19");
		drugList.add("31-277-98");
		List<DrugExclusionModel> response = specialityExclusionService
				.checkSpecialityExclusion(drugExclusionRequestModel);

		assertNotNull(response);
		assertThat(response).hasSize(3);

		DrugExclusionModel drug1Res = response.get(0);
		assertThat(drug1Res.getStatusCode()).isEqualTo(ServiceStatus.APPROVED.value());
		assertThat(drug1Res.getDrugCode()).isEqualTo("123-325-04");
		assertNull(drug1Res.getDenialCode());
		assertNull(drug1Res.getStatusDescription());

		DrugExclusionModel drug2Res = response.get(1);
		assertThat(drug2Res.getStatusCode()).isEqualTo(ServiceStatus.APPROVED.value());
		assertNull(drug2Res.getDenialCode());
		assertNull(drug2Res.getStatusDescription());

		DrugExclusionModel drug3Res = response.get(2);
		assertThat(drug3Res.getStatusCode()).isEqualTo(ServiceStatus.REJECTED.value());
		assertNotNull(drug3Res.getDenialCode());
		assertNotNull(drug3Res.getStatusDescription());
		assertThat(drug3Res.getDenialCode()).isEqualTo(DenialCode.SPECIALITY_EXCLUSION.value());
		assertThat(drug3Res.getStatusDescription()).isEqualTo("31-277-98 is part of the drug exclusion list");
	}

	@Test
	@DisplayName("Rejected drugs response")
	void checkSpecialityExclusionForRejected() {
		drugList.add("31-277-98");
		drugList.add("57-15-97");
		List<DrugExclusionModel> response = specialityExclusionService
				.checkSpecialityExclusion(drugExclusionRequestModel);

		assertNotNull(response);
		assertThat(response).hasSize(2);

		DrugExclusionModel drug1Res = response.get(0);
		assertThat(drug1Res.getStatusCode()).isEqualTo(ServiceStatus.REJECTED.value());
		assertNotNull(drug1Res.getDenialCode());
		assertNotNull(drug1Res.getStatusDescription());
		assertThat(drug1Res.getDenialCode()).isEqualTo(DenialCode.SPECIALITY_EXCLUSION.value());
		assertThat(drug1Res.getStatusDescription()).isEqualTo("31-277-98 is part of the drug exclusion list");

		DrugExclusionModel drug2Res = response.get(1);
		assertThat(drug2Res.getStatusCode()).isEqualTo(ServiceStatus.REJECTED.value());
		assertNotNull(drug2Res.getDenialCode());
		assertNotNull(drug2Res.getStatusDescription());
		assertThat(drug2Res.getDenialCode()).isEqualTo(DenialCode.SPECIALITY_EXCLUSION.value());
		assertThat(drug2Res.getStatusDescription()).isEqualTo("57-15-97 is part of the drug exclusion list");
	}

	private DrugExclusionRequestModel generateSpecialityExclusionRequestModel() {
		return new DrugExclusionRequestModel(requestId, physicianLicenseNumber, drugList, specialityName, payerId,
				providerId);
	}

	private void setRelatedData() {
		drugExclusionRequestModel = generateSpecialityExclusionRequestModel();
		physicianInfo = generatePhysicianInfo();
		speciality = generateSpeciality();
		deptSpecPhyscAssc = generateDeptSpecPhyscAssc();
		specialityExclusionAssc = generatespecialityExclusionAssc();
		specialityExclusionList.add(specialityExclusionAssc);
		drugExclusionDetailsList = generateDrugExclusionDetailsList();
		commonDenials = generateCommonDenials();
		drugExclusionMetadata = generateDrugExclusionMetadata();
	}

	private DrugExclusionMetadata generateDrugExclusionMetadata() {
		return new DrugExclusionMetadata(1L, 102L, "TEST", new Date(), "TEST", new Date(), false, "NA");
	}

	private CommonDenials generateCommonDenials() {
		return new CommonDenials(1L, DenialCode.SPECIALITY_EXCLUSION.value(),
				"<drugcode> <DrugName> is part of the drug exclusion list");
	}

	private void mockNeededRepositories() {
		Mockito.when(physicianInfoRepository.findByRegistrationNumber(physicianLicenseNumber))
				.thenReturn(physicianInfo);
		Mockito.when(specialityRepository.findBySpecialityNameAndIsDeleted(specialityName, false))
				.thenReturn(speciality);
		Mockito.when(deptSpecPhyscAsscRepository.findByPhysicianInfoIdAndSpecialityIdAndIsEnabled(
				physicianInfo.getPhysicianInfoId(), speciality.getSpecialityId(), true)).thenReturn(deptSpecPhyscAssc);
		Mockito.when(
				specialityExclusionAsscRepository.findBySpecialityIdAndIsEnabled(speciality.getSpecialityId(), true))
				.thenReturn(specialityExclusionList);
		Mockito.when(drugExclusionDetailsRepository
				.findByExclusionIdAndIsDeleted(specialityExclusionAssc.getExclusionId(), false))
				.thenReturn(drugExclusionDetailsList);
		Mockito.when(commonDenialsRepository.findByDenialCode(DenialCode.SPECIALITY_EXCLUSION.value()))
				.thenReturn(Optional.of(commonDenials));
		Mockito.when(drugExclusionMetadataRepository.findByExclusionIdAndPayerIdAndIsDeleted(
				specialityExclusionAssc.getExclusionId(), Long.parseLong(drugExclusionRequestModel.getPayerId()),
				false)).thenReturn(drugExclusionMetadata);

	}

	private List<DrugExclusionDetails> generateDrugExclusionDetailsList() {
		List<DrugExclusionDetails> list = new ArrayList<>();
		list.add(new DrugExclusionDetails(1L, 1L, 10001L, "31-277-98", "test", "test", "test", new BigDecimal(10),
				new Date(), false, "test"));
		list.add(new DrugExclusionDetails(2L, 1L, 10002L, "57-15-97", "test", "test", "test", new BigDecimal(10),
				new Date(), false, "test"));
		return list;
	}

	private SpecialityExclusionAssc generatespecialityExclusionAssc() {
		return new SpecialityExclusionAssc(1L, new BigDecimal(1.0), 1L, new Date(), true);
	}

	private PhysicianInfo generatePhysicianInfo() {
		PhysicianCategory physicianCategory = new PhysicianCategory();
		physicianCategory.setPhysicianCategoryName("1");
		physicianCategory.setCategoryDescription("General Physician");
		return new PhysicianInfo(1L, 99999L, physicianLicenseNumber, "Test", physicianCategory);
	}

	private Speciality generateSpeciality() {
		return new Speciality(new BigDecimal(1.0), specialityName, new Date(), false);
	}

	private DeptSpecPhyscAssc generateDeptSpecPhyscAssc() {
		return new DeptSpecPhyscAssc(1L, new BigDecimal(1.0), 1L, 1L, true);
	}
}
