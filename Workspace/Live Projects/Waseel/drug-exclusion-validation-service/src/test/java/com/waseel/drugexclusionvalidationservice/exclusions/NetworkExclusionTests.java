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
import com.waseel.drugexclusionvalidationservice.persist.businessrules.DrugExclusionDetails;
import com.waseel.drugexclusionvalidationservice.persist.businessrules.DrugExclusionMetadata;
import com.waseel.drugexclusionvalidationservice.persist.businessrules.NetworkExclusionAssc;
import com.waseel.drugexclusionvalidationservice.persist.businessrules.ProviderNetwork;
import com.waseel.drugexclusionvalidationservice.persist.businessrules.ProviderNetworkAssc;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.CommonDenialsRepository;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.DrugExclusionDetailsRepository;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.DrugExclusionMetadataRepository;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.NetworkExclusionAsscRepository;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.ProviderNetworkAsscRepository;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.ProviderNetworkRepository;
import com.waseel.drugexclusionvalidationservice.service.exclusions.NetworkExclusionService;

@SpringBootTest
@ActiveProfiles("test")
class NetworkExclusionTests {

	@Autowired
	NetworkExclusionService networkExclusionService;
	@MockBean
	ProviderNetworkRepository providerNetworkRepository;
	@MockBean
	ProviderNetworkAsscRepository providerNetworkAsscRepository;
	@MockBean
	NetworkExclusionAsscRepository networkExclusionAsscRepository;
	@MockBean
	DrugExclusionMetadataRepository drugExclusionMetadataRepository;
	@MockBean
	DrugExclusionDetailsRepository drugExclusionDetailsRepository;
	@MockBean
	CommonDenialsRepository commonDenialsRepository;

	private ProviderNetwork providerNetwork;
	private Optional<ProviderNetworkAssc> providerNetworkAssc;
	private Optional<NetworkExclusionAssc> networkExclusionAssc;
	private List<DrugExclusionDetails> drugExclusionDetailsList;
	private CommonDenials commonDenials;
	private DrugExclusionRequestModel drugExclusionRequestModel;
	private DrugExclusionMetadata drugExclusionMetadata;
	private List<NetworkExclusionAssc> networkExclusionAsscList = new ArrayList<>();
	private List<DrugExclusionMetadata> drugExclusionMetadataList = new ArrayList<>();
	private List<ProviderNetworkAssc> providerNetworkAsscList = new ArrayList<>();

	private String requestId = "f90abad5-8c8d-4f61-afe9-36af91e30637";
	private String payerId = "102";
	private String providerId = "99999";
	private String physicianLicenseNumber = "12345";
	private String specialityName = "Emergency Medicine Specialty";

	List<String> drugList = new ArrayList<>();

	@BeforeEach
	void setupData() {
		setRelatedData();
		Mockito.when(providerNetworkRepository.save(Mockito.any())).thenReturn(providerNetwork);
		Mockito.when(providerNetworkAsscRepository.save(Mockito.any())).thenReturn(providerNetworkAssc);
		Mockito.when(networkExclusionAsscRepository.save(Mockito.any())).thenReturn(networkExclusionAssc);
		Mockito.when(commonDenialsRepository.save(Mockito.any())).thenReturn(commonDenials);
		Mockito.when(drugExclusionMetadataRepository.save(Mockito.any())).thenReturn(drugExclusionMetadata);

		assertNotNull(providerNetwork);
		assertNotNull(drugExclusionMetadata);
		assertNotNull(drugExclusionDetailsList);
		assertNotNull(commonDenials);
		assertNotNull(networkExclusionAssc);
		assertNotNull(providerNetworkAssc);
		mockNeededRepositories();
	}

	@Test
	@DisplayName("Approved or Rejected drugs response ")
	void checkSpecialityExclusionForApproved() {
		drugList.add("9-539-08");
		List<DrugExclusionModel> response = networkExclusionService.checkNetworkExclusion(drugExclusionRequestModel);
		assertNotNull(response);
		assertThat(response).hasSize(1);
		DrugExclusionModel drug = response.get(0);
		if (drug.getStatusCode().equalsIgnoreCase(ServiceStatus.REJECTED.value())) {
			assertNotNull(drug.getDenialCode());
			assertNotNull(drug.getStatusDescription());
		} else if (drug.getStatusCode().equalsIgnoreCase(ServiceStatus.APPROVED.value())) {
			assertNull(drug.getDenialCode());
			assertNull(drug.getStatusDescription());
		}
	}

	private DrugExclusionRequestModel generateSpecialityExclusionRequestModel() {
		return new DrugExclusionRequestModel(requestId, physicianLicenseNumber, drugList, specialityName, payerId,
				providerId);
	}

	private void setRelatedData() {
		drugExclusionRequestModel = generateSpecialityExclusionRequestModel();
		providerNetwork = generateProviderNetwork();
		drugExclusionDetailsList = generateDrugExclusionDetailsList();
		commonDenials = generateCommonDenials();
		drugExclusionMetadata = generateDrugExclusionMetadata();
		networkExclusionAssc = generateNetworkExclusionAssc();
		providerNetworkAssc = generateProviderNetworkAssc();
		networkExclusionAsscList.add(networkExclusionAssc.get());
		drugExclusionMetadataList.add(drugExclusionMetadata);
		providerNetworkAsscList.add(providerNetworkAssc.get());
	}

	private Optional<ProviderNetworkAssc> generateProviderNetworkAssc() {
		ProviderNetworkAssc providerNetworkAssc = new ProviderNetworkAssc();
		providerNetworkAssc.setIsEnabled(true);
		providerNetworkAssc.setLastUpdateDate(new Date());
		providerNetworkAssc.setProviderId(new BigDecimal(providerId));
		providerNetworkAssc.setProviderNetwork(providerNetwork);
		return Optional.ofNullable(providerNetworkAssc);
	}

	private Optional<NetworkExclusionAssc> generateNetworkExclusionAssc() {
		NetworkExclusionAssc networkExclusionAssc = new NetworkExclusionAssc();
		networkExclusionAssc.setExclusionId(new BigDecimal("1"));
		networkExclusionAssc.setIsEnabled(true);
		networkExclusionAssc.setLastUpdateDate(new Date());
		networkExclusionAssc.setNetworkExclusionAsscId(Long.valueOf("66"));
		networkExclusionAssc.setProviderNetwork(providerNetwork);
		return Optional.ofNullable(networkExclusionAssc);
	}

	private DrugExclusionMetadata generateDrugExclusionMetadata() {
		return new DrugExclusionMetadata(1L, 102L, "TEST", new Date(), "TEST", new Date(), false, "NA");
	}

	private CommonDenials generateCommonDenials() {
		return new CommonDenials(1L, DenialCode.SPECIALITY_EXCLUSION.value(),
				"<drugcode> <DrugName> is part of the drug exclusion list");
	}

	private void mockNeededRepositories() {
		Mockito.when(providerNetworkAsscRepository
				.findByProviderIdAndIsEnabled(new BigDecimal(drugExclusionRequestModel.getProviderId()), true))
				.thenReturn(providerNetworkAsscList);
		Mockito.when(networkExclusionAsscRepository.findByProviderNetwork_NetworkIdInAndIsEnabled(Mockito.anyList(),
				Mockito.anyBoolean())).thenReturn(networkExclusionAsscList);
		Mockito.when(
				drugExclusionDetailsRepository.findByExclusionIdInAndIsDeleted(Mockito.anyList(), Mockito.anyBoolean()))
				.thenReturn(drugExclusionDetailsList);
		Mockito.when(commonDenialsRepository.findByDenialCode(DenialCode.NETWORK_EXCLUSION.value()))
				.thenReturn(Optional.of(commonDenials));
		List<Long> exclusionIds = networkExclusionService.getAllExclusionIdsFromList(networkExclusionAsscList);
		Mockito.when(drugExclusionMetadataRepository.findByExclusionIdInAndPayerIdAndIsDeleted(exclusionIds,
				Long.parseLong(drugExclusionRequestModel.getPayerId()), false)).thenReturn(drugExclusionMetadataList);
	}

	private List<DrugExclusionDetails> generateDrugExclusionDetailsList() {
		List<DrugExclusionDetails> list = new ArrayList<>();
		list.add(new DrugExclusionDetails(2L, 1L, 10002L, "9-539-08", "test", "test", "test", new BigDecimal(10),
				new Date(), false, "test"));
		return list;
	}

	private ProviderNetwork generateProviderNetwork() {
		ProviderNetwork providerNetwork = new ProviderNetwork();
		providerNetwork.setIsDeleted(false);
		providerNetwork.setLastUpdateDate(new Date());
		providerNetwork.setNetworkId(Long.valueOf("1"));
		providerNetwork.setNetworkName("A+");
		providerNetwork.setPayerId(new BigDecimal("102"));
		return providerNetwork;
	}
}
