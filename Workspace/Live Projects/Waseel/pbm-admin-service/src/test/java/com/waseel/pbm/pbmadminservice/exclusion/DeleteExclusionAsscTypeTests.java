package com.waseel.pbm.pbmadminservice.exclusion;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

import com.waseel.pbm.pbmadminservice.enums.drugexclusion.ExclusionType;
import com.waseel.pbm.pbmadminservice.exceptions.AdminException;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugExclusionMetadata;
import com.waseel.pbm.pbmadminservice.persist.businessrules.NetworkExclusionAssc;
import com.waseel.pbm.pbmadminservice.persist.businessrules.SpecialityExclusionAssc;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugExclusionMetadataRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.ExclusionAsscTypeListRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.NetworkExclusionAsscRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.SpecialityExclusionAsscRepository;
import com.waseel.pbm.pbmadminservice.service.AuditLogService;
import com.waseel.pbm.pbmadminservice.service.DrugExclusionService;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DeleteExclusionAsscTypeTests {

	@Autowired
	private DrugExclusionService drugExclusionService;

	@MockBean
	private DrugExclusionMetadataRepository drugExclusionMetadataRepository;
	@MockBean
	private NetworkExclusionAsscRepository networkExclusionAsscRepository;
	@MockBean
	private AuditLogService auditLogService;
	@MockBean
	private ExclusionAsscTypeListRepository exclusionAsscTypeListRepository;
	@MockBean
	private SpecialityExclusionAsscRepository specialityExclusionAsscRepository;

	private DrugExclusionMetadata drugExclusionMetadata;
	private NetworkExclusionAssc networkExclusionAssc;
	private SpecialityExclusionAssc specialityExclusionAssc;
	private SpecialityExclusionAssc disabledSpecialityExclusionAssc;
	private Long payerId = 102L;
	private Long exclusionId = 1L;
	private Long networkId = 1L;
	private Long networkExclusionAsscId = 1L;
	private Date date = new Date();
	private Long specialityExclusionAsscId = 4L;
	private BigDecimal specialityId = new BigDecimal("1.02");

	@BeforeAll
	public void setUpCommonData() {
		generateMockUserInfo();
		drugExclusionMetadata = generateDrugExclusionMetadata();
		networkExclusionAssc = generateNetworkExclusionAssc();
		specialityExclusionAssc = populateSpecialityExclusionAssc();
	}

	@BeforeEach
	public void setupData() {
		Mockito.when(drugExclusionMetadataRepository.save(Mockito.any())).thenReturn(drugExclusionMetadata);
		Mockito.when(networkExclusionAsscRepository.save(Mockito.any())).thenReturn(networkExclusionAssc);
		Mockito.when(
				drugExclusionMetadataRepository.findByExclusionIdAndPayerIdAndIsDeleted(exclusionId, payerId, false))
				.thenReturn(Optional.of(drugExclusionMetadata));
		Mockito.when(
				networkExclusionAsscRepository.findByNetworkExclusionAsscIdAndIsEnabled(networkExclusionAsscId, true))
				.thenReturn(Optional.of(networkExclusionAssc));
	}

	@Test
	@DisplayName("Validation of ExclusionType")
	void validateExclusionType() {
		try {
			drugExclusionService.deleteExclusionType("test", networkExclusionAsscId.toString());
		} catch (AdminException e) {
			assertEquals("exclusionType is not found or exists.", e.getMessage());
		}
	}

	@Test
	@DisplayName("Validation of exclusionAsscId")
	void validateNetworkExclusionAsscId() {
		try {
			Mockito.when(networkExclusionAsscRepository.findByNetworkExclusionAsscIdAndIsEnabled(networkExclusionAsscId,
					true)).thenReturn(Optional.empty());
			drugExclusionService.deleteExclusionType(ExclusionType.NETWORK_EXCLUSION.value(),
					networkExclusionAsscId.toString());
		} catch (AdminException e) {
			assertEquals("exclusionAsscId is not found or exists.", e.getMessage());
		}
	}

	@Test
	@DisplayName("Delete Network Exclusion Association")
	void deleteNetworkExclusionAssociation() throws AdminException {
		drugExclusionService.deleteExclusionType(ExclusionType.NETWORK_EXCLUSION.value(),
				networkExclusionAsscId.toString());
		assertThat(networkExclusionAssc.getIsEnabled()).isFalse();
		assertNotEquals(drugExclusionMetadata.getLastUpdateDate(), date);
	}

	@Test
	@DisplayName("Delete Speciality Exclusion Association")
	void deleteSpecialityExclusion() throws AdminException {
		disabledSpecialityExclusionAssc = populateDisabledSpecialityExclusionAssc();
		Mockito.when(specialityExclusionAsscRepository.findBySpecialityExclusionAsscIdAndIsEnabled(Mockito.anyLong(),
				Mockito.any())).thenReturn(Optional.of(specialityExclusionAssc));
		Mockito.when(specialityExclusionAsscRepository.save(Mockito.any())).thenReturn(disabledSpecialityExclusionAssc);
		drugExclusionService.deleteExclusionType(ExclusionType.SPECIALITY_EXCLUSION.value(),
				specialityExclusionAsscId.toString());
		assertNotNull(disabledSpecialityExclusionAssc);
		assertEquals(false, disabledSpecialityExclusionAssc.getIsEnabled());
		assertNotEquals(drugExclusionMetadata.getLastUpdateDate(), date);
		verify(exclusionAsscTypeListRepository, times(1))
				.deleteByExclusionIdAndExclusionAsscIdAndExclusionTypeAndPayerId(Mockito.anyLong(), Mockito.anyLong(),
						Mockito.any(), Mockito.anyLong());
	}

	private NetworkExclusionAssc generateNetworkExclusionAssc() {
		return new NetworkExclusionAssc(1L, networkId, exclusionId, date);
	}

	private DrugExclusionMetadata generateDrugExclusionMetadata() {
		return new DrugExclusionMetadata(exclusionId, payerId, "Network Exclusion", date, "PayerAdmin", date, false,
				null);
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

	private SpecialityExclusionAssc populateSpecialityExclusionAssc() {
		return new SpecialityExclusionAssc(specialityExclusionAsscId, specialityId, exclusionId, true, date);
	}

	private SpecialityExclusionAssc populateDisabledSpecialityExclusionAssc() {
		return new SpecialityExclusionAssc(specialityExclusionAsscId, specialityId, exclusionId, false, date);
	}
}
