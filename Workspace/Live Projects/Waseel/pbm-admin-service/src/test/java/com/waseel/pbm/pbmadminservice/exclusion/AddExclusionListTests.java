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
import com.waseel.pbm.pbmadminservice.model.drugexclusion.DrugExclusionRequestModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.DrugExclusionResponseModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.ExclusionListDrugDetailsRequestModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.ExclusionTypeRequestModel;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugExclusionMetadata;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugExclusionMetadataRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.HighCostExclusionAsscRepository;
import com.waseel.pbm.pbmadminservice.service.DrugExclusionService;

@ActiveProfiles("test")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AddExclusionListTests {

	@Autowired
	private DrugExclusionService drugExclusionService;
	@MockBean
	private DrugExclusionMetadataRepository drugExclusionMetadataRepository;
	@MockBean
	private HighCostExclusionAsscRepository highCostExclusionAsscRepository;

	private DrugExclusionRequestModel drugExclusionRequestModel;
	private DrugExclusionMetadata drugExclusionMetadata;
	private Long payerId = 102L;
	private String listName = "2023-V5";

	@BeforeAll
	public void setUpCommonData() {
		generateMockUserInfo();
		drugExclusionRequestModel = generateExclusionListRequestModel();
		drugExclusionMetadata = generateDrugExclusionMetadata();
	}

	@BeforeEach
	public void setupData() {
		Mockito.when(drugExclusionMetadataRepository.findByExclusionNameIgnoreCaseAndIsDeletedAndPayerId(Mockito.any(),
				Mockito.anyBoolean(), Mockito.anyLong())).thenReturn(Optional.of(drugExclusionMetadata));
		Mockito.when(drugExclusionMetadataRepository.save(Mockito.any()))
				.thenReturn(Optional.of(drugExclusionMetadata));
	}

	@Test
	@DisplayName("Validation of ExclusionList")
	void validateExclusionId() {
		try {
			DrugExclusionResponseModel drugExclusionResponseModel = drugExclusionService
					.addNewDrugExclusionList(drugExclusionRequestModel);
			assertEquals(drugExclusionResponseModel.getExclusionId(), Mockito.anyLong());
		} catch (AdminException e) {
			assertEquals(ExclusionMessages.DUPLICATE_EXCLUSION_NAME.value(), e.getMessage());
		}
	}

	private DrugExclusionMetadata generateDrugExclusionMetadata() {
		return new DrugExclusionMetadata(Long.valueOf("1"), payerId, listName, new Date(), "pbmPayerTest", new Date(),
				false, null);
	}

	private DrugExclusionRequestModel generateExclusionListRequestModel() {
		DrugExclusionRequestModel drugExclusionRequestModel = new DrugExclusionRequestModel();
		List<ExclusionTypeRequestModel> exclusionTypeDetails = new ArrayList<ExclusionTypeRequestModel>();
		exclusionTypeDetails.add(new ExclusionTypeRequestModel("High Cost Medicine", null, null, null));
		List<ExclusionListDrugDetailsRequestModel> drugList = new ArrayList<ExclusionListDrugDetailsRequestModel>();
		drugList.add(new ExclusionListDrugDetailsRequestModel("7-288-08", "PANADOL COLD AND FLU DAY  F.C. CABLETS",
				"PHENYLEPHRINE|CAFFEINE|PARACETAMOL", "7000001049", "05-JAN-23", new BigDecimal("10.7"), "1000979"));
		drugExclusionRequestModel.setExclusionDrugDetails(drugList);
		drugExclusionRequestModel.setExclusionListName("V4-Speciality");
		drugExclusionRequestModel.setExclusionTypeDetails(exclusionTypeDetails);
		return drugExclusionRequestModel;
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
