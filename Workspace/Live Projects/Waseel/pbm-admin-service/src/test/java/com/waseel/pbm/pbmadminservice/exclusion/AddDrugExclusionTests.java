package com.waseel.pbm.pbmadminservice.exclusion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import com.waseel.pbm.pbmadminservice.enums.drugexclusion.ExclusionMessages;
import com.waseel.pbm.pbmadminservice.enums.drugexclusion.ExclusionType;
import com.waseel.pbm.pbmadminservice.exceptions.AdminException;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.DrugExclusionRequestModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.DrugExclusionResponseModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.ExclusionListDrugDetailsRequestModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.ExclusionTypeRequestModel;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugExclusionMetadata;
import com.waseel.pbm.pbmadminservice.persist.businessrules.ExclusionAsscTypeList;
import com.waseel.pbm.pbmadminservice.persist.businessrules.NetworkExclusionAssc;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugExclusionDetailsRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugExclusionMetadataRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.ExclusionAsscTypeListRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.HighCostExclusionAsscRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.NetworkExclusionAsscRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.ProviderNetworkRepository;
import com.waseel.pbm.pbmadminservice.service.AuditLogService;
import com.waseel.pbm.pbmadminservice.service.DrugExclusionService;

@ActiveProfiles("test")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddDrugExclusionTests {

	@Autowired
	private DrugExclusionService drugExclusionService;
	@Autowired
	private Validator validator;
	@Autowired
	private MessageSource messageSource;
	@MockBean
	private NetworkExclusionAsscRepository networkExclusionAsscRepository;
	@MockBean
	private DrugExclusionMetadataRepository drugExclusionMetadataRepository;
	@MockBean
	private HighCostExclusionAsscRepository highCostExclusionAsscRepository;
	@MockBean
	private DrugExclusionDetailsRepository drugExclusionDetailsRepository;
	@MockBean
	private ExclusionAsscTypeListRepository exclusionAsscTypeListRepository;
	@MockBean
	private AuditLogService auditLogService;
	@MockBean
	private ProviderNetworkRepository providerNetworkRepository;
	private DrugExclusionRequestModel drugExclusionRequestModel;
	private DrugExclusionMetadata drugExclusionMetadata;
	private NetworkExclusionAssc networkExclusionAssc;
	private ExclusionAsscTypeList exclusionAsscTypeList;
	private Long payerId = 102L;
	private Long exclusionId = 1L;
	private Long networkId = 1L;
	private Date date = new Date();
	private String drugCode = "129-277-02";
	private String exclusionName = "Drug Exclusion";

	@BeforeAll
	public void setUpCommonData() {
		generateMockUserInfo();
		drugExclusionRequestModel = generateDrugExclusionRequestModel();
		drugExclusionMetadata = generateDrugExclusionMetadata();
		networkExclusionAssc = generateNetworkExclusionAssc();
		exclusionAsscTypeList = generateExclusionAsscTypeList();
	}

	@BeforeEach
	public void setupData() {
		Mockito.when(drugExclusionMetadataRepository.save(Mockito.any())).thenReturn(drugExclusionMetadata);
		Mockito.when(drugExclusionMetadataRepository.findByExclusionNameIgnoreCase(Mockito.any()))
				.thenReturn(Optional.of(drugExclusionMetadata));
		Mockito.when(networkExclusionAsscRepository.save(Mockito.any())).thenReturn(networkExclusionAssc);
		Mockito.when(exclusionAsscTypeListRepository.save(Mockito.any())).thenReturn(exclusionAsscTypeList);
		Mockito.when(drugExclusionMetadataRepository.findByExclusionNameIgnoreCaseAndIsDeletedAndPayerId(Mockito.any(),
				Mockito.anyBoolean(), Mockito.anyLong())).thenReturn(Optional.of(drugExclusionMetadata));
	}

	@Test
	@DisplayName("Exclusion Name already exists")
	void validateExclusionName() {
		try {

			drugExclusionService.addNewDrugExclusionList(drugExclusionRequestModel);
		} catch (AdminException e) {
			assertEquals(ExclusionMessages.DUPLICATE_EXCLUSION_NAME.value(), e.getMessage());
		}
	}

	@Test
	@DisplayName("Duplicate exclusion Network found")
	void duplicateExclusionNetworkFound() {
		try {
			DrugExclusionRequestModel model = generateDrugExclusionRequestModel();
			model.setExclusionListName("test");
			List<ExclusionTypeRequestModel> exclusionTypeDetails = model.getExclusionTypeDetails();
			exclusionTypeDetails
					.add(new ExclusionTypeRequestModel(ExclusionType.NETWORK_EXCLUSION.value(), "1", null, null));
			Mockito.when(drugExclusionMetadataRepository.findByExclusionNameIgnoreCase(model.getExclusionListName()))
					.thenReturn(Optional.empty());
			drugExclusionService.addNewDrugExclusionList(model);
		} catch (AdminException e) {
			//assertEquals(ExclusionMessages.DUPLICATE_EXCLUSION_NETWORK.value() + "[1]", e.getMessage());
		}
	}

	@Test
	@DisplayName("Add New Drug Exclusion")
	void addNewDrugExclusion() {
		try {
			Mockito.when(drugExclusionMetadataRepository
					.findByExclusionNameIgnoreCase(drugExclusionRequestModel.getExclusionListName()))
					.thenReturn(Optional.empty());
			DrugExclusionResponseModel response = drugExclusionService
					.addNewDrugExclusionList(drugExclusionRequestModel);
			assertNotNull(response);
			if (response.getErrors() != null && response.getErrors().isEmpty())
				assertEquals(1, response.getExclusionId());
		} catch (AdminException ex) {

		}
	}

	@Test
	@DisplayName("Request model Not Empty validations")
	void notEmptyValidationTest() {
		DrugExclusionRequestModel drugExclusionRequestModel = new DrugExclusionRequestModel(null, null, null);
		List<ConstraintViolation<DrugExclusionRequestModel>> sortedViolations = getSortedViolations(
				drugExclusionRequestModel);
		Assertions.assertFalse(sortedViolations.isEmpty());
		Assertions.assertEquals(3, sortedViolations.size());
		List<String> messages = new ArrayList<>();
		for (ConstraintViolation<DrugExclusionRequestModel> violation : sortedViolations) {
			String propertyPath = violation.getPropertyPath().toString() + " ";
			String expectedMessage = propertyPath
					+ messageSource.getMessage("notNullOrEmpty", null, Locale.getDefault());
			messages.add(expectedMessage);
		}
		Assertions.assertEquals(
				"exclusionDrugDetails should not be null or empty," + " exclusionListName should not be null or empty,"
						+ " exclusionTypeDetails should not be null or empty",
				StringUtils.strip(messages.toString(), "[]"));
	}

	private NetworkExclusionAssc generateNetworkExclusionAssc() {
		return new NetworkExclusionAssc(1L, networkId, exclusionId, date);
	}

	private ExclusionAsscTypeList generateExclusionAsscTypeList() {
		ExclusionAsscTypeList highCost = new ExclusionAsscTypeList();
		highCost.setExclusionId(exclusionId);
		highCost.setExclusionType(ExclusionType.HIGH_COST_EXCLUSION.value());
		highCost.setExclusionAsscId(123L);
		highCost.setPayerId(payerId);
		return highCost;
	}

	private DrugExclusionMetadata generateDrugExclusionMetadata() {
		return new DrugExclusionMetadata(exclusionId, payerId, exclusionName, date, "PayerAdmin", date, false, null);
	}

	private DrugExclusionRequestModel generateDrugExclusionRequestModel() {
		List<ExclusionTypeRequestModel> exclusionTypeDetails = new ArrayList<>();
		exclusionTypeDetails
				.add(new ExclusionTypeRequestModel(ExclusionType.NETWORK_EXCLUSION.value(), "1", null, null));
		exclusionTypeDetails
				.add(new ExclusionTypeRequestModel(ExclusionType.HIGH_COST_EXCLUSION.value(), null, null, null));
		List<ExclusionListDrugDetailsRequestModel> exclusionDrugDetails = new ArrayList<>();
		exclusionDrugDetails.add(new ExclusionListDrugDetailsRequestModel(drugCode, "TEST", "TEST", "TEST",
				date.toString(), new BigDecimal(120), "10001"));
		exclusionDrugDetails.add(new ExclusionListDrugDetailsRequestModel("146-23-98", "TEST", "TEST", "TEST",
				date.toString(), new BigDecimal(120), "10002"));
		return new DrugExclusionRequestModel(exclusionName, exclusionTypeDetails, exclusionDrugDetails);
	}

	private List<ConstraintViolation<DrugExclusionRequestModel>> getSortedViolations(
			DrugExclusionRequestModel drugExclusionRequestModel2) {
		Set<ConstraintViolation<DrugExclusionRequestModel>> violations = validator.validate(drugExclusionRequestModel2);
		List<ConstraintViolation<DrugExclusionRequestModel>> sortedViolations = new ArrayList<>(violations);
		Collections.sort(sortedViolations, Comparator.comparing(violation -> violation.getPropertyPath().toString()));
		return sortedViolations;
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
