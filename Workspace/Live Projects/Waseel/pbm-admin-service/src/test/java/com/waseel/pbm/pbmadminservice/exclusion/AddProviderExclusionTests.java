package com.waseel.pbm.pbmadminservice.exclusion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
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
import com.waseel.pbm.pbmadminservice.model.drugexclusion.provider.ProviderExclusionRequestModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.provider.ProviderExclusionResponseModel;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugExclusionMetadata;
import com.waseel.pbm.pbmadminservice.persist.businessrules.NetworkExclusionAssc;
import com.waseel.pbm.pbmadminservice.persist.businessrules.ProviderExclusionAssc;
import com.waseel.pbm.pbmadminservice.persist.businessrules.ProviderNetwork;
import com.waseel.pbm.pbmadminservice.persist.hira.AccountToAccountAssociation;
import com.waseel.pbm.pbmadminservice.persist.hira.AccountToAccountAssociationId;
import com.waseel.pbm.pbmadminservice.persist.hira.SwitchAccount;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugExclusionMetadataRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.ExclusionAsscTypeListRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.ProviderExclusionAsscRepository;
import com.waseel.pbm.pbmadminservice.repository.hira.AccountToAccountAssociationRepository;
import com.waseel.pbm.pbmadminservice.repository.hira.SwitchAccountRepository;
import com.waseel.pbm.pbmadminservice.service.AuditLogService;
import com.waseel.pbm.pbmadminservice.service.ProviderExclusionService;

@ActiveProfiles("test")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddProviderExclusionTests {

	@Autowired
	private ProviderExclusionService providerExclusionService;

	@MockBean
	private ProviderExclusionAsscRepository providerExclusionAsscRepository;
	@MockBean
	private AuditLogService auditLogService;
	@MockBean
	private DrugExclusionMetadataRepository drugExclusionMetadataRepository;
	@MockBean
	private AccountToAccountAssociationRepository accountToAccountAssociationRepository;
	@MockBean
	private SwitchAccountRepository switchAccountRepository;
	@MockBean
	private ExclusionAsscTypeListRepository exclusionAsscTypeListRepository;

	private ProviderExclusionRequestModel providerExclusionRequestModel;
	private DrugExclusionMetadata drugExclusionMetadata;
	private ProviderExclusionAssc providerExclusionAssc;
	private AccountToAccountAssociation accountToAccountAssociation;
	private SwitchAccount switchAccount;
	private Long payerId = 102L;
	private Long exclusionId = 1L;
	private Long providerId = 601L;
	private String providerName = "Dallah Hospital";
	private Date date = new Date();

	@BeforeAll
	public void setUpCommonData() {
		generateMockUserInfo();
		providerExclusionRequestModel = generateProviderExclusionRequestModel();
		drugExclusionMetadata = generateDrugExclusionMetadata();
		providerExclusionAssc = generateProviderExclusionAssc();
		accountToAccountAssociation = generateAccountToAccountAssociation();
		switchAccount = generateswitchAccount();
	}

	@BeforeEach
	public void setupData() {
		Mockito.when(drugExclusionMetadataRepository.save(Mockito.any())).thenReturn(drugExclusionMetadata);
		Mockito.when(providerExclusionAsscRepository.save(Mockito.any())).thenReturn(providerExclusionAssc);
		Mockito.when(
				drugExclusionMetadataRepository.findByExclusionIdAndPayerIdAndIsDeleted(exclusionId, payerId, false))
				.thenReturn(Optional.of(drugExclusionMetadata));
		Mockito.when(providerExclusionAsscRepository.findByProviderIdAndPayerIdAndExclusionId(providerId, payerId,
				exclusionId)).thenReturn(Optional.of(providerExclusionAssc));
		Mockito.when(accountToAccountAssociationRepository
				.findByIdSourceAndIdDestinationAndIsEnabled(new BigDecimal(providerId), new BigDecimal(payerId), true))
				.thenReturn(Optional.of(accountToAccountAssociation));
		Mockito.when(switchAccountRepository
				.findBySwitchAccountIdAndIsEnabledAndCategoryIgnoreCase(new BigDecimal(providerId), "1", "PROVIDER"))
				.thenReturn(Optional.of(switchAccount));
	}

	@Test
	@DisplayName("Validation of ExclusionId")
	void validateExclusionId() {
		try {
			providerExclusionService.addProviderExclusion(providerExclusionRequestModel, 2L);
		} catch (AdminException e) {
			assertEquals(ExclusionMessages.EXCLUSIONID_NOT_FOUND.value(), e.getMessage());
		}
	}

	@Test
	@DisplayName("Validation of ProviderId")
	void validateProviderId() {
		try {
			providerExclusionRequestModel.setProviderId("701");
			providerExclusionService.addProviderExclusion(providerExclusionRequestModel, exclusionId);
		} catch (AdminException e) {
			assertEquals(ExclusionMessages.PROVIDER_ID_NOT_FOUND.value(), e.getMessage());
		}
	}

	@Test
	@DisplayName("Validation of ProviderName")
	void validateProviderName() {
		try {
			providerExclusionRequestModel.setProviderId(providerId.toString());
			providerExclusionRequestModel.setProviderName("test");
			providerExclusionService.addProviderExclusion(providerExclusionRequestModel, exclusionId);
		} catch (AdminException e) {
			assertEquals(ExclusionMessages.PROVIDER_NAME_NOT_FOUND.value(), e.getMessage());
		}
	}

	@Test
	@DisplayName("Validation of Already exists details")
	void validateForAlreadyExistsDetails() {
		try {
			providerExclusionRequestModel.setProviderId(providerId.toString());
			providerExclusionRequestModel.setProviderName(providerName);
			providerExclusionService.addProviderExclusion(providerExclusionRequestModel, exclusionId);
		} catch (AdminException e) {
			assertEquals(ExclusionMessages.PROVIDER_EXCLUSION_ALREADY_EXISTS.value(), e.getMessage());
		}
	}

	@Test
	@DisplayName("Add Provider Exclusion")
	void addProviderExclusionSuccessResponse() throws AdminException {
		Long exclusionId = 2L;
		drugExclusionMetadata = new DrugExclusionMetadata(exclusionId, payerId, "Network Exclusion", date, "PayerAdmin",
				date, false, null);
		Mockito.when(drugExclusionMetadataRepository.save(Mockito.any())).thenReturn(drugExclusionMetadata);
		Mockito.when(
				drugExclusionMetadataRepository.findByExclusionIdAndPayerIdAndIsDeleted(exclusionId, payerId, false))
				.thenReturn(Optional.of(drugExclusionMetadata));
		Mockito.when(providerExclusionAsscRepository.save(Mockito.any(ProviderExclusionAssc.class)))
				.thenAnswer(assc -> {
					ProviderExclusionAssc savedProviderExclusionAsscDetail = assc.getArgument(0);
					savedProviderExclusionAsscDetail.setExclusionId(exclusionId);
					savedProviderExclusionAsscDetail.setProviderId(providerId);
					savedProviderExclusionAsscDetail.setProviderExclusionAsscId(2L);
					savedProviderExclusionAsscDetail.setLastUpdateDate(date);
					return savedProviderExclusionAsscDetail;
				});
		ProviderExclusionResponseModel response = providerExclusionService
				.addProviderExclusion(providerExclusionRequestModel, 2L);
		assertNotNull(response);
		assertEquals(2L, response.getProviderExclusionAsscId());
	}

	private AccountToAccountAssociation generateAccountToAccountAssociation() {
		AccountToAccountAssociationId id = new AccountToAccountAssociationId(new BigDecimal(providerId),
				new BigDecimal(payerId));
		return new AccountToAccountAssociation(id, true, true, "100001");
	}

	private DrugExclusionMetadata generateDrugExclusionMetadata() {
		return new DrugExclusionMetadata(exclusionId, payerId, "Network Exclusion", date, "PayerAdmin", date, false,
				null);
	}

	private ProviderExclusionRequestModel generateProviderExclusionRequestModel() {
		return new ProviderExclusionRequestModel(providerId.toString(), providerName);
	}

	private ProviderExclusionAssc generateProviderExclusionAssc() {
		return new ProviderExclusionAssc(providerId, exclusionId, providerName, payerId, date);
	}

	private SwitchAccount generateswitchAccount() {
		return new SwitchAccount(new BigDecimal(providerId), providerName, "مستشفى دله", "PROVIDER", "10001", 2D, "1");
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
