package com.waseel.pbm.pbmadminservice.drugformulary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
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

import com.waseel.pbm.pbmadminservice.exceptions.AdminException;
import com.waseel.pbm.pbmadminservice.model.drugformulary.DrugFormularyDrugDetailsRequestModel;
import com.waseel.pbm.pbmadminservice.model.drugformulary.DrugFormularyDrugDetailsResponseModel;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugFormularyDetails;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugFormularyMetadata;
import com.waseel.pbm.pbmadminservice.persist.mdss.DrugService;
import com.waseel.pbm.pbmadminservice.persist.mdss.DrugServiceMetaData;
import com.waseel.pbm.pbmadminservice.repository.businessrules.AuditLogRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugFormularyDetailsRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugFormularyMetadataRepository;
import com.waseel.pbm.pbmadminservice.repository.mdss.DrugServiceMetaDataRepository;
import com.waseel.pbm.pbmadminservice.repository.mdss.DrugServiceRepository;
import com.waseel.pbm.pbmadminservice.service.DrugFormularyService;

@SpringBootTest
@ActiveProfiles({ "test" })
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AddDrugFormularyDrugDetailTests {

	@Autowired
	private DrugFormularyService drugFormularyService;

	@MockBean
	private AuditLogRepository auditLogRepository;

	@MockBean
	private DrugFormularyMetadataRepository drugFormularyMetadataRepository;

	@MockBean
	private DrugServiceRepository drugServiceRepository;

	@MockBean
	private DrugFormularyDetailsRepository drugFormularyDetailsRepository;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private Validator validator;

	@MockBean
	private DrugServiceMetaDataRepository drugServiceMetaDataRepository;

	private Long formularyId = 1L;
	private String payerId = "102";
	private String drugCode = "256-212-09";
	private Date date = new Date();
	private DrugFormularyDrugDetailsRequestModel drugFormularyDrugDetailsRequestModel;
	private DrugFormularyMetadata drugFormularyMetadata;
	private DrugService drugService;
	private DrugFormularyDetails drugFormularyDetails;
	private Long drugListId = 1L;
	private static final String fileName = "SFDA";
	private static final String sfdaVersion = "V1";
	private Timestamp timestamp = (new Timestamp(Calendar.getInstance().getTimeInMillis()));

	@BeforeAll
	public void setUpCommonData() {
		generateMockUserInfo();
		drugFormularyMetadata = generateDrugFormularyMetadata();
		drugService = generateDrugService();
		drugFormularyDetails = generateDrugFormularyDetails();
	}

	@BeforeEach
	public void setupData() {
		drugFormularyDrugDetailsRequestModel = generateDrugDetailsRequestModel();
		Mockito.when(drugFormularyMetadataRepository.save(drugFormularyMetadata)).thenReturn(drugFormularyMetadata);
		Mockito.when(
				drugFormularyMetadataRepository.findByFormularyIdAndPayerIdAndIsDeleted(formularyId, payerId, false))
				.thenReturn(Optional.of(drugFormularyMetadata));
		Mockito.when(drugServiceRepository.save(drugService)).thenReturn(drugService);
		Mockito.when(drugFormularyDetailsRepository.save(drugFormularyDetails)).thenReturn(drugFormularyDetails);
		Mockito.when(drugServiceMetaDataRepository
				.findFirstByEffectiveDateLessThanEqualOrderByEffectiveDateDescUploadDateTimeDesc(Mockito.any()))
				.thenReturn(Optional.of(generateDrugServiceMetaData()));
	}

	@Test
	@DisplayName("FormularyId Not Exists")
	void formularyIdNotExists() {
		try {
			drugFormularyService.addDrugFormularyDrugDetails(3L, drugFormularyDrugDetailsRequestModel);
		} catch (AdminException e) {
			assertEquals("FormularyId is not found or exists.", e.getMessage());
		}
	}

	@Test
	@DisplayName("DrugCode Not Found")
	void drugCodeNotExists() {
		try {
			Mockito.when(drugServiceRepository.findByOtherCodesValueAndDrugListId("PQR", 1L))
					.thenReturn(Optional.of(drugService));
			drugFormularyService.addDrugFormularyDrugDetails(formularyId, drugFormularyDrugDetailsRequestModel);
		} catch (AdminException e) {
			assertEquals("DrugCode is not found.", e.getMessage());
		}
	}

	@Test
	@DisplayName("Drug details already exists")
	void drugDetailsAlreadyExists() {
		try {
			Mockito.when(drugServiceRepository.findByOtherCodesValueAndDrugListId(drugCode, drugListId))
					.thenReturn(Optional.of(drugService));
			Mockito.when(drugFormularyDetailsRepository
					.findByFormularyIdAndWaseelDrugIdAndRegistrationNumberAndIsDeleted(formularyId,
							drugService.getWaseelDrugId(), drugFormularyDrugDetailsRequestModel.getDrugCode(), false))
					.thenReturn(Optional.of(drugFormularyDetails));
			drugFormularyService.addDrugFormularyDrugDetails(formularyId, drugFormularyDrugDetailsRequestModel);
		} catch (AdminException e) {
			assertEquals("Drug details already exists.", e.getMessage());
		}
	}

	@Test
	@DisplayName("Success Response")
	void successResponse() throws AdminException {
		Mockito.when(drugServiceRepository.findByOtherCodesValueAndDrugListId(drugCode, drugListId))
				.thenReturn(Optional.of(drugService));
		Mockito.when(drugFormularyDetailsRepository.findByFormularyIdAndWaseelDrugIdAndRegistrationNumberAndIsDeleted(
				2L, drugService.getWaseelDrugId(), drugFormularyDrugDetailsRequestModel.getDrugCode(), false))
				.thenReturn(Optional.of(drugFormularyDetails));
		Mockito.when(drugFormularyDetailsRepository.save(Mockito.any(DrugFormularyDetails.class))).thenAnswer(drug -> {
			DrugFormularyDetails savedDrugDetails = drug.getArgument(0);
			savedDrugDetails.setDrugFormularyDetailsId(2L);
			savedDrugDetails.setWaseelDrugId(10002L);
			return savedDrugDetails;
		});
		DrugFormularyDrugDetailsResponseModel response = drugFormularyService.addDrugFormularyDrugDetails(formularyId,
				drugFormularyDrugDetailsRequestModel);
		assertNotNull(response);
		assertEquals(2L, response.getDrugFormularyDetailsId());
	}

	@Test
	@DisplayName("Request model Not Empty validations")
	void notEmptyValidationTest() {
		drugFormularyDrugDetailsRequestModel.setDrugCode(null);
		drugFormularyDrugDetailsRequestModel.setDrugName(null);
		drugFormularyDrugDetailsRequestModel.setGenericName(null);
		drugFormularyDrugDetailsRequestModel.setPrice(null);
		List<ConstraintViolation<DrugFormularyDrugDetailsRequestModel>> sortedViolations = getSortedViolations(
				drugFormularyDrugDetailsRequestModel);
		Assertions.assertFalse(sortedViolations.isEmpty());
		Assertions.assertEquals(4, sortedViolations.size());
		List<String> messages = new ArrayList<>();
		for (ConstraintViolation<DrugFormularyDrugDetailsRequestModel> violation : sortedViolations) {
			String propertyPath = violation.getPropertyPath().toString() + " ";
			String expectedMessage = propertyPath
					+ messageSource.getMessage("notNullOrEmpty", null, Locale.getDefault());
			messages.add(expectedMessage);
		}
		Assertions.assertEquals(
				"drugCode should not be null or empty, drugName should not be null or empty, "
						+ "genericName should not be null or empty, price should not be null or empty",
				StringUtils.strip(messages.toString(), "[]"));
	}

	@Test
	@DisplayName("Request model length validations")
	void lengthValidationTest() {
		drugFormularyDrugDetailsRequestModel.setDrugCode(getDrugCodeLengthMoreThan50());
		List<ConstraintViolation<DrugFormularyDrugDetailsRequestModel>> sortedViolations = getSortedViolations(
				drugFormularyDrugDetailsRequestModel);
		Assertions.assertFalse(sortedViolations.isEmpty());
		Assertions.assertEquals(1, sortedViolations.size());
		List<String> messages = new ArrayList<>();
		for (ConstraintViolation<DrugFormularyDrugDetailsRequestModel> violation : sortedViolations) {
			String propertyPath = violation.getPropertyPath().toString() + " ";
			String expectedMessage = propertyPath
					+ messageSource.getMessage("noMoreThan50LengthValidation", null, Locale.getDefault());
			messages.add(expectedMessage);
		}
		Assertions.assertEquals("drugCode shouldn't be more than 50", StringUtils.strip(messages.toString(), "[]"));
	}

	private String getDrugCodeLengthMoreThan50() {
		return "TESTtestTESTtestTESTtestTESTtestTESTtestTESTtestTESTtestTESTtestTESTtestTESTtestT";
	}

	private List<ConstraintViolation<DrugFormularyDrugDetailsRequestModel>> getSortedViolations(
			DrugFormularyDrugDetailsRequestModel requestModel) {
		Set<ConstraintViolation<DrugFormularyDrugDetailsRequestModel>> violations = validator.validate(requestModel);
		List<ConstraintViolation<DrugFormularyDrugDetailsRequestModel>> sortedViolations = new ArrayList<>(violations);
		Collections.sort(sortedViolations, Comparator.comparing(violation -> violation.getPropertyPath().toString()));
		return sortedViolations;
	}

	private DrugFormularyDetails generateDrugFormularyDetails() {
		return new DrugFormularyDetails(1L, formularyId, 10001L, drugCode, "FEVADOL FAST 500MG TABLETS",
				"FEVADOL FAST 500MG TABLETS", "10101010", new BigDecimal(12), new Date());
	}

	private DrugFormularyMetadata generateDrugFormularyMetadata() {
		return new DrugFormularyMetadata(formularyId, payerId, "Test", date, "Test", date, false, "NA");
	}

	private DrugFormularyDrugDetailsRequestModel generateDrugDetailsRequestModel() {
		return new DrugFormularyDrugDetailsRequestModel(drugCode, "FEVADOL FAST 500MG TABLETS",
				"FEVADOL FAST 500MG TABLETS", new BigDecimal(12));
	}

	private DrugService generateDrugService() {
		return new DrugService("06281086002568", "PHARMACEUTICAL", "10%", "FEVADOL FAST 500MG TABLETS", "TABLETS", 1L,
				"MG", "TEST", "SPIMACO,SAUDI ARABIA", "SFDA", drugCode, "20S", "BLISTER PACK", "5.55", new Date(),
				"SPIMACO", Timestamp.from(Instant.now()), "ORAL", "500 MG|630 MG", "TABLET", null, null, new Date());
	}

	public void generateMockUserInfo() {
		Map<String, Object> details = new HashMap<String, Object>();
		details.put("accId", payerId);
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

	private DrugServiceMetaData generateDrugServiceMetaData() {
		DrugServiceMetaData drugServiceMetaData = new DrugServiceMetaData();
		drugServiceMetaData.setDrugListId(1L);
		drugServiceMetaData.setEffectiveDate(date);
		drugServiceMetaData.setFileName(fileName);
		drugServiceMetaData.setOwnerName(fileName);
		drugServiceMetaData.setSfdaUpdateDate(date);
		drugServiceMetaData.setSfdaVersion(sfdaVersion);
		drugServiceMetaData.setUploadDateTime(timestamp);
		return drugServiceMetaData;
	}
}
