package com.waseel.pbm.pbmadminservice.customization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.waseel.pbm.pbmadminservice.model.customization.DrugToDiagnosisApprovalCategoryModel;
import com.waseel.pbm.pbmadminservice.model.customization.DrugToDiagnosisModel;
import com.waseel.pbm.pbmadminservice.model.customization.IcdDiagnosisModel;
import com.waseel.pbm.pbmadminservice.model.customization.PayerConfigModel;
import com.waseel.pbm.pbmadminservice.model.customization.ServiceCodeModel;
import com.waseel.pbm.pbmadminservice.repository.hira.ICDDiagnosisRepository;
import com.waseel.pbm.pbmadminservice.repository.mdss.DrugServiceMetaDataRepository;
import com.waseel.pbm.pbmadminservice.repository.mdss.DrugServiceRepository;
import com.waseel.pbm.pbmadminservice.repository.mdss.DrugToDiagnosisApprovalCategoryRepository;
import com.waseel.pbm.pbmadminservice.repository.mdss.PayerConfigRepository;
import com.waseel.pbm.pbmadminservice.service.PCDrugToDiagnosisService;

@SpringBootTest
@ActiveProfiles("test")
class DrugToDiagnosisUploadValidationTests {

	private static final String INVALID_EXTENSION_MESSAGE = "Invalid file format, only .xls or .xlsx are allowed.";
	private static final String INVALID_FILE_EMPTY_MESSAGE = "Please select a file to upload.";
	private static final String INVALID_FILE_SIZE_MESSAGE = "File size exceeds the maximum allowed size (5MB).";
	private static final String INVALID_HEADERS_MESSAGE = "Invalid format. Please reorder/rename the headers or check the values or refer the format of Sample File.";
	private static final String NO_RECORDS_MESSAGE = "File must have at least one record.";
	private static final String NO_HEADERS_MESSAGE = "Headers not found.";

	@Autowired
	private PCDrugToDiagnosisService pcDrugToDiagnosisService;

	@MockBean
	private PayerConfigRepository payerConfigRepository;

	@MockBean
	private DrugToDiagnosisApprovalCategoryRepository drugToDiagnosisApprovalCategoryRepository;

	@MockBean
	private ICDDiagnosisRepository icdDiagnosisRepository;

	@MockBean
	private DrugServiceRepository drugServiceRepository;
	
	@MockBean
	private DrugServiceMetaDataRepository drugServiceMetaDataRepository;

	@Test
	@DisplayName("Empty file validation")
	void emptyFileValidation() {
		MockMultipartFile file = new MockMultipartFile("pc_drugToDiagnosis.xlsx", "pc_drugToDiagnosis.xlsx",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", new byte[0]);
		try {
			pcDrugToDiagnosisService.addPcDrugToDiagnosisDetailsFromFile(file, false);
		} catch (AdminException e) {
			assertEquals(INVALID_FILE_EMPTY_MESSAGE, e.getMessage());
		} catch (IOException e) {
		}
	}

	@Test
	@DisplayName("File Extension validation")
	void fileExtensionValidation() {
		try {
			MockMultipartFile file = new MockMultipartFile("pc_drugToDiagnosis.txt", "pc_drugToDiagnosis.txt",
					"text/plain",
					getClass().getClassLoader().getResourceAsStream("pcDrugToDiagnosisExcel/pc_drugToDiagnosis.xlsx"));
			pcDrugToDiagnosisService.addPcDrugToDiagnosisDetailsFromFile(file, false);
		} catch (AdminException e) {
			assertEquals(INVALID_EXTENSION_MESSAGE, e.getMessage());
		} catch (IOException e) {
		}
	}

	@Test
	@DisplayName("File Size validation")
	void fileSizeValidation() {
		try {
			MockMultipartFile file = new MockMultipartFile("pc_drugToDiagnosis.xlsx", "pc_drugToDiagnosis.xlsx",
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
					new byte[(int) DataSize.ofMegabytes(6).toBytes()]);
			pcDrugToDiagnosisService.addPcDrugToDiagnosisDetailsFromFile(file, false);
		} catch (AdminException e) {
			assertEquals(INVALID_FILE_SIZE_MESSAGE, e.getMessage());
		} catch (IOException e) {
		}
	}

	@Test
	@DisplayName("No Header column validation")
	void withoutHeaderColumnValidation() {
		try {
			MockMultipartFile file = new MockMultipartFile("noHeaderPcDrugToDiagnosis.xlsx",
					"noHeaderPcDrugToDiagnosis.xlsx",
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", getClass().getClassLoader()
							.getResourceAsStream("pcDrugToDiagnosisExcel/noHeaderPcDrugToDiagnosis.xlsx"));
			pcDrugToDiagnosisService.addPcDrugToDiagnosisDetailsFromFile(file, false);
		} catch (AdminException e) {
			assertEquals(NO_HEADERS_MESSAGE, e.getMessage());
		} catch (IOException e) {
		}
	}

	@Test
	@DisplayName("Header column validation")
	void invalidHeaderValidation() {
		try {
			MockMultipartFile file = new MockMultipartFile("invalidHeaderPcDrugtodiagnosis.xlsx",
					"invalidHeaderPcDrugtodiagnosis.xlsx",
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", getClass().getClassLoader()
							.getResourceAsStream("pcDrugToDiagnosisExcel/invalidHeaderPcDrugtodiagnosis.xlsx"));
			pcDrugToDiagnosisService.addPcDrugToDiagnosisDetailsFromFile(file, false);
		} catch (AdminException e) {
			assertEquals(INVALID_HEADERS_MESSAGE, e.getMessage());
		} catch (IOException e) {
		}
	}

	@Test
	@DisplayName("No Record Found validation")
	void noRecordFoundValidation() {
		try {
			MockMultipartFile file = new MockMultipartFile("onlyHeaderDataPcDrugToDiagnosis.xlsx",
					"onlyHeaderDataPcDrugToDiagnosis.xlsx",
					"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", getClass().getClassLoader()
							.getResourceAsStream("pcDrugToDiagnosisExcel/onlyHeaderDataPcDrugToDiagnosis.xlsx"));
			pcDrugToDiagnosisService.addPcDrugToDiagnosisDetailsFromFile(file, false);
		} catch (AdminException e) {
			assertEquals(NO_RECORDS_MESSAGE, e.getMessage());
		} catch (IOException e) {
		}
	}

	@Test
	@DisplayName("Record level validation")
	void recordLevelValidation() throws IOException, AdminException {
		mockRepositories();
		MockMultipartFile file = new MockMultipartFile("pc_drugToDiagnosis.xlsx", "pc_drugToDiagnosis.xlsx",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
				getClass().getClassLoader().getResourceAsStream("pcDrugToDiagnosisExcel/pc_drugToDiagnosis.xlsx"));
		Map<Object, Object> response = pcDrugToDiagnosisService.addPcDrugToDiagnosisDetailsFromFile(file, false);
		assertStatements(response);
	}

	private void assertStatements(Map<Object, Object> response) {
		assertNotNull(response);
		assertTrue(response.containsKey("duplicateRecordCount"));
		assertTrue(response.containsKey("errorList"));
		assertTrue(response.containsKey("duplicateRecords"));
		assertEquals(2, response.get("duplicateRecordCount"));
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> errorList = (List<Map<String, Object>>) response.get("errorList");
		assertEquals(9, errorList.size());
		DrugToDiagnosisModel firstError = (DrugToDiagnosisModel) errorList.get(0);
		List<String> errorDescription = firstError.getErrorDescriptions();
		Long rowNumber = firstError.getRowNumber();
		assertTrue(errorDescription.contains(
				"RejectionCategory should be one of these values : Diagnosis-Indication,Diagnosis-ContraIndication or ALL"));
		assertTrue(errorDescription.contains("ServiceStatus should be one of these values : APPROVED or REJECTED"));
		assertTrue(errorDescription.contains("CategoryOfApproval values should be like AsPerPBMExperts"));
		assertEquals(2, rowNumber);
		DrugToDiagnosisModel forthError = (DrugToDiagnosisModel) errorList.get(3);
		Long forthRowNumber = forthError.getRowNumber();
		assertEquals(5, forthRowNumber);
	}

	private void mockRepositories() {
		when(drugToDiagnosisApprovalCategoryRepository.findByApprovalCategories(Mockito.any()))
			.thenReturn(generateApprovalCategory());
		when(payerConfigRepository.findByPayerIds(Mockito.any())).thenReturn(generatePayerModel());
		when(drugServiceRepository.findByServiceCodes(Mockito.any(),Mockito.any())).thenReturn(generateServiceCodeModel());
		when(icdDiagnosisRepository.findByIcdCodes(Mockito.any())).thenReturn(generateIcdDiagnosisModel());		
	}

	private List<DrugToDiagnosisApprovalCategoryModel> generateApprovalCategory() {
		List<DrugToDiagnosisApprovalCategoryModel> appCatList = new ArrayList<>();
		appCatList.add(createMockApprovalCategory("Tawuniya", 1));
		appCatList.add(createMockApprovalCategory("jjj", 0));
		appCatList.add(createMockApprovalCategory("abc", 0));
		return appCatList;
	}

	private List<PayerConfigModel> generatePayerModel() {
		List<PayerConfigModel> payerList = new ArrayList<>();
		payerList.add(createMockPayerId("102", 1));
		payerList.add(createMockPayerId("204", 1));
		payerList.add(createMockPayerId("10200", 0));
		return payerList;
	}

	private List<ServiceCodeModel> generateServiceCodeModel() {
		List<ServiceCodeModel> serviceList = new ArrayList<>();
		serviceList.add(createMockServiceCodeModel("129-334-10", 1));
		serviceList.add(createMockServiceCodeModel("45646456", 0));
		serviceList.add(createMockServiceCodeModel("123-277-02", 1));
		return serviceList;
	}

	private List<IcdDiagnosisModel> generateIcdDiagnosisModel() {
		List<IcdDiagnosisModel> icdCodeList = new ArrayList<>();
		icdCodeList.add(createIcdDiagnosisModel("R25.2", 1));
		icdCodeList.add(createIcdDiagnosisModel("F31.1", 1));
		icdCodeList.add(createIcdDiagnosisModel("K10", 1));
		icdCodeList.add(createIcdDiagnosisModel("R25.2rrr", 0));
		icdCodeList.add(createIcdDiagnosisModel("R25.222244", 0));
		icdCodeList.add(createIcdDiagnosisModel("R25.2777", 0));
		return icdCodeList;
	}

	private IcdDiagnosisModel createIcdDiagnosisModel(String diagnosisCode, int isValid) {
		return new IcdDiagnosisModel() {
			@Override
			public Integer getIsValid() {
				return isValid;
			}

			@Override
			public String getDiagnosisCode() {
				return diagnosisCode;
			}
		};
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

	private PayerConfigModel createMockPayerId(String payerId, int isValid) {
		return new PayerConfigModel() {
			@Override
			public String getPayerId() {
				return payerId;
			}

			@Override
			public Integer getIsValid() {
				return isValid;
			}
		};
	}

	private DrugToDiagnosisApprovalCategoryModel createMockApprovalCategory(String name, int isValid) {
		return new DrugToDiagnosisApprovalCategoryModel() {
			@Override
			public String getName() {
				return name;
			}

			@Override
			public Integer getIsValid() {
				return isValid;
			}
		};
	}
}
