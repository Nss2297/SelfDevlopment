package com.waseel.pbm.pbmadminservice.exclusion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.unit.DataSize;

import com.waseel.pbm.pbmadminservice.exceptions.AdminException;
import com.waseel.pbm.pbmadminservice.model.customization.ServiceCodeModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.ExclusionDrugListUploadResponseModel;
import com.waseel.pbm.pbmadminservice.persist.mdss.DrugService;
import com.waseel.pbm.pbmadminservice.repository.mdss.DrugServiceMetaDataRepository;
import com.waseel.pbm.pbmadminservice.repository.mdss.DrugServiceRepository;
import com.waseel.pbm.pbmadminservice.service.ExclusionDrugListUploadService;

@SpringBootTest
@ActiveProfiles("test")
class DrugListUploadValidationTests {

	private static final String INVALID_EXTENSION_MESSAGE = "Invalid file format, only .xls or .xlsx are allowed.";
	private static final String INVALID_FILE_EMPTY_MESSAGE = "Please select a file to upload.";
	private static final String INVALID_FILE_SIZE_MESSAGE = "File size exceeds the maximum allowed size (5MB).";
	private static final String INVALID_HEADERS_MESSAGE = "Invalid format. Please reorder/rename the headers or check the values or refer the format of Sample File.";

	@Autowired
	private ExclusionDrugListUploadService exclusionDrugListUploadService;

	@MockBean
	private DrugServiceRepository drugServiceRepository;

	@MockBean
	private DrugServiceMetaDataRepository drugServiceMetaDataRepository;

	@Test
	@DisplayName("Empty file validation")
	void emptyFileValidation() {
		MockMultipartFile file = new MockMultipartFile("drugList.xlsx", "drugList.xlsx",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", new byte[0]);
		try {
			exclusionDrugListUploadService.uploadDrugListFile(file);
		} catch (AdminException e) {
			assertEquals(INVALID_FILE_EMPTY_MESSAGE, e.getMessage());
		} catch (IOException e) {
		}
	}

	@Test
	@DisplayName("File Extension validation")
	void fileExtensionValidation() {
		try {
			MockMultipartFile file = new MockMultipartFile("drugList.txt", "drugList.txt", "text/plain",
					getClass().getClassLoader().getResourceAsStream("drugListExcel/drugList.xlsx"));
			exclusionDrugListUploadService.uploadDrugListFile(file);
		} catch (AdminException e) {
			assertEquals(INVALID_EXTENSION_MESSAGE, e.getMessage());
		} catch (IOException e) {
		}
	}

	@Test
	@DisplayName("File Size validation")
	void fileSizeValidation() {
		try {
			MockMultipartFile file = new MockMultipartFile("drugList.xlsx", "drugList.xlsx",
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
					new byte[(int) DataSize.ofMegabytes(6).toBytes()]);
			exclusionDrugListUploadService.uploadDrugListFile(file);
		} catch (AdminException e) {
			assertEquals(INVALID_FILE_SIZE_MESSAGE, e.getMessage());
		} catch (IOException e) {
		}
	}

	@Test
	@DisplayName("Header column validation")
	void invalidHeaderValidation() {
		try {
			MockMultipartFile file = new MockMultipartFile("invalidHeaderDrugList.xlsx.xlsx",
					"invalidHeaderDrugList.xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
					getClass().getClassLoader().getResourceAsStream("drugListExcel/invalidHeaderDrugList.xlsx"));
			exclusionDrugListUploadService.uploadDrugListFile(file);
		} catch (AdminException e) {
			assertEquals(INVALID_HEADERS_MESSAGE, e.getMessage());
		} catch (IOException e) {
		}
	}

	@Test
	@DisplayName("Record level validation")
	void recordLevelValidation() throws IOException, AdminException {
		mockRepositories();
		MockMultipartFile file = new MockMultipartFile("drugList.xlsx", "drugList.xlsx",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
				getClass().getClassLoader().getResourceAsStream("drugListExcel/drugList.xlsx"));
		ExclusionDrugListUploadResponseModel response = exclusionDrugListUploadService.uploadDrugListFile(file);
		assertStatements(response);
	}

	private void assertStatements(ExclusionDrugListUploadResponseModel response) {
		assertNotNull(response);
		if (response.getExclusionListDrugDetailsRequestModel() != null)
			assertTrue(response.getExclusionListDrugDetailsRequestModel().get(0).getScientificCode()
					.equalsIgnoreCase("7000001251-2-100000073664"));
	}

	private void mockRepositories() {
		when(drugServiceRepository.findByServiceCodes(Mockito.any(), Mockito.any()))
				.thenReturn(generateServiceCodeModel());
		when(drugServiceRepository.findByOtherCodesValueInAndDrugListId(Mockito.any(), Mockito.any()))
				.thenReturn(generateDrugServiceModel());
	}

	private List<DrugService> generateDrugServiceModel() {
		List<DrugService> drugs = new ArrayList<DrugService>();
		DrugService drug = new DrugService();
		drug.setScientificCode("7000001251-2-100000073664");
		drug.setPrice("16.4");
		drug.setOtherCodesValue("3001233193");
		drug.setIngredients("TIZANIDINE");
		drugs.add(drug);
		return drugs;
	}

	private List<ServiceCodeModel> generateServiceCodeModel() {
		List<ServiceCodeModel> serviceList = new ArrayList<>();
		serviceList.add(createMockServiceCodeModel("3001233193", 1));
		return serviceList;
	}

	private ServiceCodeModel createMockServiceCodeModel(String serviceCode, int isValid) {
		return new ServiceCodeModel() {
			@Override
			public String getserviceCode() {
				return serviceCode;
			}

			@Override
			public Integer getIsValid() {
				return isValid;
			}
		};
	}
}
