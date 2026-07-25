package com.waseel.pbm.pbmadminservice.sfda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.waseel.pbm.pbmadminservice.model.SFDARequestModel;
import com.waseel.pbm.pbmadminservice.model.SFDAResponseModel;
import com.waseel.pbm.pbmadminservice.persist.mdss.DrugService;
import com.waseel.pbm.pbmadminservice.persist.mdss.DrugServiceMetaData;
import com.waseel.pbm.pbmadminservice.repository.mdss.DrugServiceMetaDataRepository;
import com.waseel.pbm.pbmadminservice.repository.mdss.DrugServiceRepository;
import com.waseel.pbm.pbmadminservice.service.SFDAService;
import com.waseel.pbm.pbmadminservice.util.Constants;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class SFDAManagementTests {

	private final Logger log = LoggerFactory.getLogger(SFDAManagementTests.class);

	@Autowired
	private SFDAService sfdaService;

	@MockBean
	private DrugServiceRepository drugServiceRepository;

	@MockBean
	private DrugServiceMetaDataRepository drugServiceMetaDataRepository;

	private SFDARequestModel sfdaRequestModel = null;
	private String sfdaCode = "129-334-10";
	Optional<DrugService> drugServiceListOp = Optional.empty();
	private DrugService drugService = null;
	private static final String fileName = "SFDA";
	private static final String sfdaVersion = "V1";
	private Timestamp timestamp = (new Timestamp(Calendar.getInstance().getTimeInMillis()));
	private Date date = new Date();

	@BeforeAll
	void setupData() {
		sfdaRequestModel = generateSFDARequestModel();
		drugService = generateDrugService();
	}

	@BeforeEach
	public void mockCommonData() {
		Mockito.when(drugServiceMetaDataRepository
				.findFirstByEffectiveDateLessThanEqualOrderByEffectiveDateDescUploadDateTimeDesc(Mockito.any()))
				.thenReturn(Optional.of(generateDrugServiceMetaData()));
	}

	private Optional<DrugService> generateDrugServiceOptional() {
		return Optional.of(drugService);
	}

	private DrugService generateDrugService() {
		return new DrugService("06285101001192", "PHARMACEUTICAL", null, "PREDO 5MG TABLETS", "TABLETS", 1L, "30",
				"PREDNISOLONE", "Jazeera Pharmaceutical Industries (JPI),SAUDI ARABIA", "SFDA", "129-334-10", "30'S",
				"BLISTER PACK", sfdaCode, null, sfdaCode, null, sfdaCode, sfdaCode, sfdaCode, sfdaCode, sfdaCode, null);
	}

	private SFDARequestModel generateSFDARequestModel() {
		return new SFDARequestModel(sfdaCode, "PREDO 5MG TABLETS", "13", "30", "PREDO 5MG TABLETS", "PREDO 5MG TABLETS",
				"06285101001192");
	}

	@Test
	@DisplayName("SFDA Drug not found to update.")
	void SFDADrugNotFoundToUpdateTest() {
		Mockito.when(drugServiceRepository.findByOtherCodesValueAndDrugListId(Mockito.any(), Mockito.anyLong()))
				.thenReturn(drugServiceListOp);
		SFDAResponseModel sfdaResponseModel = sfdaService.editSFDADrug(sfdaRequestModel);
		assertNotNull(sfdaResponseModel.getErrorCode());
		assertNotNull(sfdaResponseModel.getErrorDescription());
		assertEquals(Constants.INVALID, sfdaResponseModel.getErrorCode());
		assertEquals("[" + sfdaRequestModel.getSfdaCode() + "]" + Constants.DRUG_NOT_FOUND,
				sfdaResponseModel.getErrorDescription());
	}

	@Test
	@DisplayName("Update SFDA Drug.")
	void updateSFDADrugTest() {
		Mockito.when(drugServiceRepository.findByOtherCodesValueAndDrugListId(Mockito.any(), Mockito.anyLong()))
				.thenReturn(generateDrugServiceOptional());
		Mockito.when(drugServiceRepository.save(Mockito.any())).thenReturn(drugService);
		SFDAResponseModel sfdaResponseModel = sfdaService.editSFDADrug(sfdaRequestModel);
		assertNotNull(sfdaResponseModel.getSfdaCode());
		assertEquals(sfdaCode, sfdaResponseModel.getSfdaCode());
	}

	@Test
	@DisplayName("SFDA Drug not found to delete.")
	void SFDADrugNotFoundToDeleteTest() {
		Mockito.when(drugServiceRepository.findByOtherCodesValueAndDrugListId(Mockito.any(), Mockito.anyLong()))
				.thenReturn(drugServiceListOp);
		SFDAResponseModel sfdaResponseModel = sfdaService.deleteSFDADrug(sfdaCode);
		assertNotNull(sfdaResponseModel.getErrorCode());
		assertNotNull(sfdaResponseModel.getErrorDescription());
		assertEquals(Constants.INVALID, sfdaResponseModel.getErrorCode());
		assertEquals("[" + sfdaRequestModel.getSfdaCode() + "]" + Constants.DRUG_NOT_FOUND,
				sfdaResponseModel.getErrorDescription());
	}

	@Test
	@DisplayName("Delete SFDA Drug.")
	void deleteSFDADrugTest() {
		Mockito.when(drugServiceRepository.findByOtherCodesValueAndDrugListId(Mockito.any(), Mockito.anyLong()))
				.thenReturn(generateDrugServiceOptional());
		SFDAResponseModel sfdaResponseModel = sfdaService.deleteSFDADrug(sfdaCode);
		assertEquals(sfdaCode, sfdaResponseModel.getSfdaCode());
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
