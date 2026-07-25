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
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
import com.waseel.drugexclusionvalidationservice.persist.businessrules.DrugExclusionDetails;
import com.waseel.drugexclusionvalidationservice.persist.businessrules.ProviderExclusionAssc;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.CommonDenialsRepository;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.DrugExclusionDetailsRepository;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.ProviderExclusionAsscRepository;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.TransactionLogRepository;
import com.waseel.drugexclusionvalidationservice.service.exclusions.ProviderExclusionService;

@SpringBootTest
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
class ProviderExclusionTests {

	@Autowired
	private ProviderExclusionService providerExclusionService;

	@MockBean
	private TransactionLogRepository transactionLogRepository;

	@MockBean
	private DrugExclusionDetailsRepository drugExclusionDetailsRepository;

	@MockBean
	private CommonDenialsRepository commonDenialsRepository;

	@MockBean
	private ProviderExclusionAsscRepository providerExclusionAsscRepository;

	private List<DrugExclusionDetails> drugExclusionDetailsList;
	private CommonDenials commonDenials;
	private DrugExclusionRequestModel drugExclusionRequestModel;
	private ProviderExclusionAssc providerExclusionAssc;
	private String requestId = "f90abad5-8c8d-4f61-afe9-36af91e30637";
	private String payerId = "102";
	private String providerId = "601";
	private String physicianLicenseNumber = "12345";
	private String specialityName = "Anesthesia Cardiology";
	private Date date = new Date();
	private List<String> drugList = new ArrayList<>();
	private List<ProviderExclusionAssc> providerExclusionAsscList = new ArrayList<>();


	@BeforeEach
	void setupData() {
		generateCommonData();
		Mockito.when(providerExclusionAsscRepository.findByProviderIdAndPayerIdAndIsEnabledAndIsDeleted(
				Mockito.anyLong(), Mockito.anyLong(), Mockito.any(), Mockito.any()))
				.thenReturn(providerExclusionAsscList);
		Mockito.when(
				drugExclusionDetailsRepository.findByExclusionIdInAndIsDeleted(Mockito.anyList(), Mockito.anyBoolean()))
				.thenReturn(drugExclusionDetailsList);
		Mockito.when(commonDenialsRepository.findByDenialCode(Mockito.any())).thenReturn(Optional.of(commonDenials));
		assertNotNull(Optional.of(providerExclusionAssc));
		assertNotNull(drugExclusionDetailsList);
		assertNotNull(Optional.of(commonDenials));
	}

	@Test
	@DisplayName("Provider approved drugs only.")
	@Order(1)
	void checkSpecialityExclusionForApproved() {
		drugList.add("31-277-98");
		drugList.add("23-883-19");
		List<DrugExclusionModel> response = providerExclusionService
				.providerExclusionCheckForDrugs(drugExclusionRequestModel);
		assertNotNull(response);
		assertThat(response).hasSize(2);

		DrugExclusionModel drugExclusionModel1 = response.get(0);
		assertThat(drugExclusionModel1.getStatusCode()).isEqualTo(ServiceStatus.APPROVED.value());
		assertThat(drugExclusionModel1.getDrugCode()).isEqualTo("31-277-98");
		assertNull(drugExclusionModel1.getDenialCode());
		assertNull(drugExclusionModel1.getStatusDescription());

		DrugExclusionModel drugExclusionModel2 = response.get(1);
		assertThat(drugExclusionModel2.getStatusCode()).isEqualTo(ServiceStatus.APPROVED.value());
		assertThat(drugExclusionModel2.getDrugCode()).isEqualTo("23-883-19");
		assertNull(drugExclusionModel2.getDenialCode());
		assertNull(drugExclusionModel2.getStatusDescription());
	}

	@Test
	@DisplayName("Provider approved and rejected drugs.")
	@Order(2)
	void checkSpecialityExclusionForApprovedAndRejected() {
		drugList.add("45-895-98");
		drugList.add("23-883-19");
		drugList.add("31-277-98");
		List<DrugExclusionModel> response = providerExclusionService
				.providerExclusionCheckForDrugs(drugExclusionRequestModel);
		assertNotNull(response);
		assertThat(response).hasSize(3);

		DrugExclusionModel drugExclusionModel1 = response.get(0);
		assertThat(drugExclusionModel1.getStatusCode()).isEqualTo(ServiceStatus.REJECTED.value());
		assertNotNull(drugExclusionModel1.getDenialCode());
		assertNotNull(drugExclusionModel1.getStatusDescription());
		assertThat(drugExclusionModel1.getDenialCode()).isEqualTo(DenialCode.PROVIDER_EXCLUSION.value());
		assertThat(drugExclusionModel1.getStatusDescription())
				.isEqualTo("45-895-98 is excluded by provider so it should undergo manual review by payer.");

		DrugExclusionModel drugExclusionModel2 = response.get(1);
		assertThat(drugExclusionModel2.getStatusCode()).isEqualTo(ServiceStatus.APPROVED.value());
		assertThat(drugExclusionModel2.getDrugCode()).isEqualTo("23-883-19");
		assertNull(drugExclusionModel2.getDenialCode());
		assertNull(drugExclusionModel2.getStatusDescription());

		DrugExclusionModel drugExclusionModel3 = response.get(2);
		assertThat(drugExclusionModel3.getStatusCode()).isEqualTo(ServiceStatus.APPROVED.value());
		assertThat(drugExclusionModel3.getDrugCode()).isEqualTo("31-277-98");
		assertNull(drugExclusionModel3.getDenialCode());
		assertNull(drugExclusionModel3.getStatusDescription());
	}

	@Test
	@DisplayName("Provider rejected drugs only.")
	@Order(3)
	void checkSpecialityExclusionForRejected() {
		drugList.add("45-895-98");
		List<DrugExclusionModel> response = providerExclusionService
				.providerExclusionCheckForDrugs(drugExclusionRequestModel);
		assertNotNull(response);
		assertThat(response).hasSize(1);

		DrugExclusionModel drugExclusionModel = response.get(0);
		assertThat(drugExclusionModel.getStatusCode()).isEqualTo(ServiceStatus.REJECTED.value());
		assertNotNull(drugExclusionModel.getDenialCode());
		assertNotNull(drugExclusionModel.getStatusDescription());
		assertThat(drugExclusionModel.getDenialCode()).isEqualTo(DenialCode.PROVIDER_EXCLUSION.value());
		assertThat(drugExclusionModel.getStatusDescription())
				.isEqualTo("45-895-98 is excluded by provider so it should undergo manual review by payer.");
	}

	private void generateCommonData() {
		drugExclusionRequestModel = generateExclusionRequestModel();
		drugExclusionDetailsList = generateDrugExclusionDetailsList();
		commonDenials = generateCommonDenials();
		providerExclusionAssc = generateProviderExclusionAssoc();
		providerExclusionAsscList.add(providerExclusionAssc);
	}

	private DrugExclusionRequestModel generateExclusionRequestModel() {
		return new DrugExclusionRequestModel(requestId, physicianLicenseNumber, drugList, specialityName, payerId,
				providerId);
	}

	private List<DrugExclusionDetails> generateDrugExclusionDetailsList() {
		List<DrugExclusionDetails> list = new ArrayList<>();
		list.add(new DrugExclusionDetails(21L, 41L, 10003L, "45-895-98", "test", "test", "test", new BigDecimal(10),
				date, false, "test"));
		list.add(new DrugExclusionDetails(41L, 62L, 10004L, "22-33-55", "test", "test", "test", new BigDecimal(10),
				date, false, "test"));
		list.add(new DrugExclusionDetails(42L, 64L, 10005L, "12-23-45", "test", "test", "test", new BigDecimal(10),
				date, false, "test"));
		return list;
	}

	private CommonDenials generateCommonDenials() {
		return new CommonDenials(1L, DenialCode.PROVIDER_EXCLUSION.value(),
				"<drugcode> <DrugName> is excluded by provider so it should undergo manual review by payer.");
	}

	private ProviderExclusionAssc generateProviderExclusionAssoc() {
		return new ProviderExclusionAssc(1L, 601L, 41L, "Dallah Hospital", true, 120L);
	}
}
