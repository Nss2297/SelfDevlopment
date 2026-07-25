package com.waseel.pbm.pbmadminservice.exclusion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import com.waseel.pbm.pbmadminservice.enums.drugexclusion.ExclusionMessages;
import com.waseel.pbm.pbmadminservice.exceptions.AdminException;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.network.NetworkExclusionModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.network.NetworkExclusionRequestModel;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugExclusionMetadata;
import com.waseel.pbm.pbmadminservice.persist.businessrules.NetworkExclusionAssc;
import com.waseel.pbm.pbmadminservice.persist.businessrules.ProviderNetwork;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugExclusionMetadataRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.ExclusionAsscTypeListRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.NetworkExclusionAsscRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.ProviderNetworkRepository;
import com.waseel.pbm.pbmadminservice.service.AuditLogService;
import com.waseel.pbm.pbmadminservice.service.NetworkExclusionService;

@ActiveProfiles("test")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AddNetworkExclusionTests {

	@Autowired
	private NetworkExclusionService networkExclusionService;
	@MockBean
	private ProviderNetworkRepository providerNetworkRepository;
	@MockBean
	private NetworkExclusionAsscRepository networkExclusionAsscRepository;
	@MockBean
	private DrugExclusionMetadataRepository drugExclusionMetadataRepository;
	@MockBean
	private AuditLogService auditLogService;
	@MockBean
	private ExclusionAsscTypeListRepository exclusionAsscTypeListRepository;
	
	private NetworkExclusionRequestModel networkExclusionRequestModel;
	private DrugExclusionMetadata drugExclusionMetadata;
	private ProviderNetwork providerNetwork;
	private NetworkExclusionAssc networkExclusionAssc;
	private Long payerId = 102L;
	private Long exclusionId = 1L;
	private Long networkId = 1L;
	private Date date = new Date();

	@BeforeAll
	public void setUpCommonData() {
		generateMockUserInfo();
		networkExclusionRequestModel = generateNetworkExclusionRequestModel();
		drugExclusionMetadata = generateDrugExclusionMetadata();
		providerNetwork = generateProviderNetwork();
		networkExclusionAssc = generateNetworkExclusionAssc();
	}

	@BeforeEach
	public void setupData() {
		Mockito.when(drugExclusionMetadataRepository.save(Mockito.any())).thenReturn(drugExclusionMetadata);
		Mockito.when(providerNetworkRepository.save(Mockito.any())).thenReturn(providerNetwork);
		Mockito.when(networkExclusionAsscRepository.save(Mockito.any())).thenReturn(networkExclusionAssc);
		Mockito.when(
				drugExclusionMetadataRepository.findByExclusionIdAndPayerIdAndIsDeleted(exclusionId, payerId, false))
				.thenReturn(Optional.of(drugExclusionMetadata));
		Mockito.when(providerNetworkRepository.findByNetworkIdAndPayerIdAndIsDeleted(networkId, payerId, false))
				.thenReturn(Optional.of(providerNetwork));
		Mockito.when(networkExclusionAsscRepository.findByExclusionIdAndNetworkId(exclusionId, networkId))
				.thenReturn(Optional.of(networkExclusionAssc));
	}

	@Test
	@DisplayName("Validation of ExclusionId")
	void validateExclusionId() {
		try {
			networkExclusionService.addNetworkExclusion(networkExclusionRequestModel, 2L);
		} catch (AdminException e) {
			assertEquals(ExclusionMessages.EXCLUSIONID_NOT_FOUND.value(), e.getMessage());
		}
	}

	@Test
	@DisplayName("Validation of NetworkId")
	void validateNetworkId() {
		try {
			networkExclusionRequestModel.setNetworkId("2");
			networkExclusionService.addNetworkExclusion(networkExclusionRequestModel, exclusionId);
		} catch (AdminException e) {
			assertEquals(ExclusionMessages.NETWORK_ID_NOT_FOUND.value(), e.getMessage());
		}
	}

	@Test
	@DisplayName("Validation of Already exists details")
	void validateForAlreadyExistsDetails() {
		try {
			networkExclusionRequestModel.setNetworkId(networkId.toString());
			networkExclusionService.addNetworkExclusion(networkExclusionRequestModel, exclusionId);
		} catch (AdminException e) {
			assertEquals(ExclusionMessages.NETWORK_EXCLUSION_ALREADY_EXISTS.value(), e.getMessage());
		}
	}

	@Test
	@DisplayName("Add Network Exclusion")
	void addNetworkExclusionSuccessResponse() throws AdminException {
		providerNetwork = new ProviderNetwork(2L, "PayerAdmin", null, false, date, "Network B", payerId);
		Mockito.when(providerNetworkRepository.save(Mockito.any())).thenReturn(providerNetwork);
		Mockito.when(providerNetworkRepository.findByNetworkIdAndPayerIdAndIsDeleted(providerNetwork.getNetworkId(),
				payerId, false)).thenReturn(Optional.of(providerNetwork));
		networkExclusionRequestModel.setNetworkId("2");
		Mockito.when(networkExclusionAsscRepository.save(Mockito.any(NetworkExclusionAssc.class)))
				.thenAnswer(network -> {
					NetworkExclusionAssc savedNetworkExclusionAsscDetail = network.getArgument(0);
					savedNetworkExclusionAsscDetail.setExclusionId(exclusionId);
					savedNetworkExclusionAsscDetail.setNetworkId(2L);
					savedNetworkExclusionAsscDetail.setNetworkExclusionAsscId(2L);
					savedNetworkExclusionAsscDetail.setLastUpdateDate(date);
					return savedNetworkExclusionAsscDetail;
				});
		NetworkExclusionModel response = networkExclusionService.addNetworkExclusion(networkExclusionRequestModel,
				exclusionId);
		assertNotNull(response);
		assertEquals(2L, response.getNetworkExclusionAsscId());
	}

	private NetworkExclusionAssc generateNetworkExclusionAssc() {
		return new NetworkExclusionAssc(1L, networkId, exclusionId, date);
	}

	private ProviderNetwork generateProviderNetwork() {
		return new ProviderNetwork(networkId, "PayerAdmin", null, false, date, "Network A", payerId);
	}

	private DrugExclusionMetadata generateDrugExclusionMetadata() {
		return new DrugExclusionMetadata(exclusionId, payerId, "Network Exclusion", date, "PayerAdmin", date, false,
				null);
	}

	private NetworkExclusionRequestModel generateNetworkExclusionRequestModel() {
		return new NetworkExclusionRequestModel("1");
	}

	public void generateMockUserInfo() {
		Map<String, Object> details = new HashMap<String, Object>();
		details.put("accId", payerId.toString());
		details.put("accName", "TEST");
		details.put("accCode", "accCode");
		details.put("username", "userName");
		details.put("email", "email");
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(authentication.getPrincipal()).thenReturn(details);
	}
}
