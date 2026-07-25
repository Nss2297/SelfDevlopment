package com.waseel.pbm.pbmadminservice.exclusion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
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

import com.waseel.pbm.pbmadminservice.exceptions.AdminException;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugExclusionDetails;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugExclusionMetadata;
import com.waseel.pbm.pbmadminservice.persist.businessrules.HighCostExclusionAssc;
import com.waseel.pbm.pbmadminservice.persist.businessrules.NetworkExclusionAssc;
import com.waseel.pbm.pbmadminservice.persist.businessrules.ProviderExclusionAssc;
import com.waseel.pbm.pbmadminservice.persist.businessrules.SpecialityExclusionAssc;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugExclusionDetailsRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugExclusionMetadataRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.ExclusionAsscTypeListRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.HighCostExclusionAsscRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.NetworkExclusionAsscRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.ProviderExclusionAsscRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.SpecialityExclusionAsscRepository;
import com.waseel.pbm.pbmadminservice.service.DrugExclusionService;

@ActiveProfiles("test")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DeleteDrugExclusionTests {

	private DrugExclusionMetadata drugExclusionMetadata;
	private HighCostExclusionAssc highCostExclusionAssc;
	private List<NetworkExclusionAssc> networkExclusionAssc;
	private List<ProviderExclusionAssc> providerExclusionAssc;
	private List<SpecialityExclusionAssc> specialityExclusionAssc;
	private List<DrugExclusionDetails> drugExclusionDetailsList;
	private Long payerId = 102L;
	private Long exclusionId = 1L;
	private Date date = new Date();

	@MockBean
	private DrugExclusionDetailsRepository drugExclusionDetailsRepository;
	@MockBean
	private ExclusionAsscTypeListRepository exclusionAsscTypeListRepository;
	@MockBean
	private DrugExclusionMetadataRepository drugExclusionMetadataRepository;
	@MockBean
	private HighCostExclusionAsscRepository highCostExclusionAsscRepository;
	@MockBean
	private NetworkExclusionAsscRepository networkExclusionAsscRepository;
	@MockBean
	private ProviderExclusionAsscRepository providerExclusionAsscRepository;
	@MockBean
	private SpecialityExclusionAsscRepository specialityExclusionAsscRepository;
	@Autowired
	private DrugExclusionService drugExclusionService;

	@BeforeAll
	public void setUpCommonData() {
		generateMockUserInfo();
		drugExclusionMetadata = generateDrugExclusionMetadata();
		specialityExclusionAssc = generateSpecialityAssc();
		providerExclusionAssc = generateProviderExclusionAssc();
		networkExclusionAssc = generateNetworkExclusionAssc();
		highCostExclusionAssc = generateHighCostExclusionAssc();
		drugExclusionDetailsList = generateDrugExclusionDetailsList();
	}

	@Test
	@DisplayName("Not exists ExclusionId")
	void exclusionIdNotExist() {
		try {
			Mockito.when(drugExclusionMetadataRepository.findById(exclusionId)).thenReturn(Optional.empty());
			drugExclusionService.deleteDrugExclusionMetadata(1L);
		} catch (AdminException e) {
			assertEquals("ExclusionId is not found or exists.", e.getMessage());
		}
	}

	@Test
	@DisplayName("Delete success")
	void deleteSuccess() throws AdminException {
		Mockito.when(drugExclusionMetadataRepository.findById(exclusionId))
				.thenReturn(Optional.of(drugExclusionMetadata));
		Mockito.when(drugExclusionMetadataRepository.save(drugExclusionMetadata)).thenReturn(drugExclusionMetadata);
		Mockito.when(highCostExclusionAsscRepository.findByExclusionId(exclusionId))
				.thenReturn(Optional.of(highCostExclusionAssc));
		Mockito.when(networkExclusionAsscRepository.findByExclusionId(exclusionId))
				.thenReturn(Optional.of(networkExclusionAssc));
		Mockito.when(providerExclusionAsscRepository.findByExclusionId(exclusionId))
				.thenReturn(Optional.of(providerExclusionAssc));
		Mockito.when(specialityExclusionAsscRepository.findByExclusionId(exclusionId))
				.thenReturn(Optional.of(specialityExclusionAssc));
		Mockito.when(highCostExclusionAsscRepository.save(Mockito.any())).thenReturn(highCostExclusionAssc);
		Mockito.when(specialityExclusionAsscRepository.saveAll(Mockito.any())).thenReturn(specialityExclusionAssc);
		Mockito.when(networkExclusionAsscRepository.saveAll(Mockito.any())).thenReturn(networkExclusionAssc);
		Mockito.when(providerExclusionAsscRepository.saveAll(Mockito.any())).thenReturn(providerExclusionAssc);
		Mockito.doNothing().when(exclusionAsscTypeListRepository).deleteAllByExclusionId(exclusionId);
		Mockito.when(drugExclusionDetailsRepository.saveAll(Mockito.any())).thenReturn(drugExclusionDetailsList);
		Mockito.when(drugExclusionDetailsRepository.findByExclusionId(exclusionId))
				.thenReturn(Optional.of(drugExclusionDetailsList));
		drugExclusionService.deleteDrugExclusionMetadata(1L);
	}

	private DrugExclusionMetadata generateDrugExclusionMetadata() {
		return new DrugExclusionMetadata(exclusionId, payerId, "Network Exclusion", date, "PayerAdmin", date, false,
				null);
	}

	private HighCostExclusionAssc generateHighCostExclusionAssc() {
		HighCostExclusionAssc highCostExclusionAssc = new HighCostExclusionAssc();
		highCostExclusionAssc.setExclusionId(exclusionId);
		highCostExclusionAssc.setHighCostExclusionAsscId(Long.valueOf("321"));
		highCostExclusionAssc.setPayerId(payerId);
		highCostExclusionAssc.setIsEnabled(true);
		highCostExclusionAssc.setLastUpdateDate(new Date());
		return highCostExclusionAssc;
	}

	private List<NetworkExclusionAssc> generateNetworkExclusionAssc() {
		List<NetworkExclusionAssc> networkExclusionAsscList = new ArrayList<NetworkExclusionAssc>();
		NetworkExclusionAssc networkExclusionAssc = new NetworkExclusionAssc();
		networkExclusionAssc.setExclusionId(exclusionId);
		networkExclusionAssc.setIsEnabled(true);
		networkExclusionAssc.setLastUpdateDate(new Date());
		networkExclusionAsscList.add(networkExclusionAssc);
		return networkExclusionAsscList;
	}

	private List<ProviderExclusionAssc> generateProviderExclusionAssc() {
		List<ProviderExclusionAssc> providerExclusionAsscList = new ArrayList<ProviderExclusionAssc>();
		ProviderExclusionAssc providerExclusionAssc = new ProviderExclusionAssc();
		providerExclusionAssc.setExclusionId(exclusionId);
		providerExclusionAssc.setIsEnabled(true);
		providerExclusionAssc.setLastUpdateDate(new Date());
		providerExclusionAsscList.add(providerExclusionAssc);
		return providerExclusionAsscList;
	}

	private List<SpecialityExclusionAssc> generateSpecialityAssc() {
		List<SpecialityExclusionAssc> specialityExclusionAsscList = new ArrayList<SpecialityExclusionAssc>();
		SpecialityExclusionAssc specialityExclusionAssc = new SpecialityExclusionAssc();
		specialityExclusionAssc.setExclusionId(exclusionId);
		specialityExclusionAssc.setIsEnabled(true);
		specialityExclusionAssc.setLastUpdateDate(new Date());
		specialityExclusionAsscList.add(specialityExclusionAssc);
		return specialityExclusionAsscList;
	}

	private List<DrugExclusionDetails> generateDrugExclusionDetailsList() {
		List<DrugExclusionDetails> drugExclusionDetails = new ArrayList<DrugExclusionDetails>();
		drugExclusionDetails
				.add(new DrugExclusionDetails(1l, 1l, "55-55-22", "Test", "Test", "Test", new BigDecimal(2), date));
		return drugExclusionDetails;
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
