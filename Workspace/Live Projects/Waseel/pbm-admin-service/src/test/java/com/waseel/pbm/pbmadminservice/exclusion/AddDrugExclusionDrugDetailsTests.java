package com.waseel.pbm.pbmadminservice.exclusion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
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

import com.waseel.pbm.pbmadminservice.exceptions.AdminException;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.DrugExclusionDrugDetailsRequestModel;
import com.waseel.pbm.pbmadminservice.persist.businessrules.AuditLog;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugExclusionDetails;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugExclusionMetadata;
import com.waseel.pbm.pbmadminservice.persist.mdss.DrugService;
import com.waseel.pbm.pbmadminservice.persist.mdss.DrugServiceMetaData;
import com.waseel.pbm.pbmadminservice.repository.businessrules.AuditLogRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugExclusionDetailsRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugExclusionMetadataRepository;
import com.waseel.pbm.pbmadminservice.repository.mdss.DrugServiceMetaDataRepository;
import com.waseel.pbm.pbmadminservice.repository.mdss.DrugServiceRepository;
import com.waseel.pbm.pbmadminservice.service.DrugExclusionService;

@ActiveProfiles("test")
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AddDrugExclusionDrugDetailsTests {

	private Long payerId = 102L;
	private Long exclusionId = 1l;
	private DrugExclusionDrugDetailsRequestModel drugExclusionDrugDetailsRequestModel;
	private DrugExclusionDetails drugExclusionDetails;
	private Date date = new Date();
	private static final String fileName = "SFDA";
	private static final String sfdaVersion = "V1";
	private Timestamp timestamp = (new Timestamp(Calendar.getInstance().getTimeInMillis()));

	@MockBean
	private DrugExclusionMetadataRepository drugExclusionMetadataRepository;
	@MockBean
	private DrugServiceRepository drugServiceRepository;
	@MockBean
	private DrugExclusionDetailsRepository drugExclusionDetailsRepository;
	@MockBean
	private AuditLogRepository auditLogRepository;

	@Autowired
	private DrugExclusionService drugExclusionService;

	@MockBean
	private DrugServiceMetaDataRepository drugServiceMetaDataRepository;

	@BeforeAll
	public void setUpCommonData() {
		generateMockUserInfo();
		drugExclusionDetails = generateDrugExclusionDetails();
		drugExclusionDrugDetailsRequestModel = generateDrugExclusionDrugDetailsRequestModel();
	}

	@BeforeEach
	public void mockCommonData() {
		Mockito.when(drugServiceMetaDataRepository
				.findFirstByEffectiveDateLessThanEqualOrderByEffectiveDateDescUploadDateTimeDesc(Mockito.any()))
				.thenReturn(Optional.of(generateDrugServiceMetaData()));
	}

	@Test
	@DisplayName("Not exists ExclusionId")
	void exclusionIdNotExists() {
		try {
			Mockito.when(drugExclusionMetadataRepository.findByExclusionIdAndIsDeleted(Mockito.anyLong(),
					Mockito.anyBoolean())).thenReturn(Optional.empty());
			drugExclusionService.addDrugExclusionDrugDetails(drugExclusionDrugDetailsRequestModel, exclusionId);
		} catch (AdminException e) {
			assertEquals("ExclusionId is not found or exists.", e.getMessage());
		}
	}

	@Test
	@DisplayName("Not exists DrugCode")
	void drugCodeNotExists() {
		try {
			Mockito.when(drugExclusionMetadataRepository.findByExclusionIdAndIsDeleted(Mockito.anyLong(),
					Mockito.anyBoolean())).thenReturn(Optional.of(new DrugExclusionMetadata()));
			Mockito.when(drugServiceRepository.findByOtherCodesValueAndDrugListId(Mockito.any(), Mockito.anyLong()))
					.thenReturn(Optional.empty());
			drugExclusionService.addDrugExclusionDrugDetails(drugExclusionDrugDetailsRequestModel, exclusionId);
		} catch (AdminException e) {
			assertEquals("DrugCode is not found.", e.getMessage());
		}
	}

	@Test
	@DisplayName("Add successfully")
	void successSaveDrugDetails() {
		try {
			Mockito.when(drugExclusionMetadataRepository.findByExclusionIdAndIsDeleted(Mockito.anyLong(),
					Mockito.anyBoolean())).thenReturn(Optional.of(new DrugExclusionMetadata()));
			Mockito.when(drugServiceRepository.findByOtherCodesValueAndDrugListId(Mockito.any(), Mockito.anyLong()))
					.thenReturn(Optional.of(new DrugService()));
			Mockito.when(
					drugExclusionDetailsRepository.findByExclusionIdAndWaseelDrugIdAndRegistrationNumberAndIsDeleted(
							Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString(), Mockito.anyBoolean()))
					.thenReturn(Optional.empty());
			Mockito.when(drugExclusionDetailsRepository.save(Mockito.any())).thenReturn(drugExclusionDetails);
			Mockito.when(drugExclusionMetadataRepository.save(Mockito.any())).thenReturn(new DrugExclusionMetadata());
			Mockito.when(auditLogRepository.save(Mockito.any())).thenReturn(new AuditLog());
			drugExclusionService.addDrugExclusionDrugDetails(drugExclusionDrugDetailsRequestModel, exclusionId);
		} catch (AdminException e) {
			assertEquals("Drug details already exists.", e.getMessage());
		}
	}

	private DrugExclusionDetails generateDrugExclusionDetails() {
		return new DrugExclusionDetails(exclusionId, 1l, "55-55-22", "Test", "Test", "Test", new BigDecimal(2), date);
	}

	private DrugExclusionDrugDetailsRequestModel generateDrugExclusionDrugDetailsRequestModel() {
		DrugExclusionDrugDetailsRequestModel requestModel = new DrugExclusionDrugDetailsRequestModel();
		requestModel.setDrugCode("123-52-66");
		requestModel.setDrugName("Test");
		requestModel.setGenericName("Test");
		requestModel.setPrice(new BigDecimal("150"));
		return requestModel;
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
