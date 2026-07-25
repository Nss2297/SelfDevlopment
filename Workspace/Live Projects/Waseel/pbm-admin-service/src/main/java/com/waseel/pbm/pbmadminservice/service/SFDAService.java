package com.waseel.pbm.pbmadminservice.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.waseel.pbm.pbmadminservice.model.SFDADrugReponseModel;
import com.waseel.pbm.pbmadminservice.model.SFDADrugRequestModel;
import com.waseel.pbm.pbmadminservice.model.SFDARequestModel;
import com.waseel.pbm.pbmadminservice.model.SFDAResponseModel;
import com.waseel.pbm.pbmadminservice.persist.mdss.DrugService;
import com.waseel.pbm.pbmadminservice.persist.mdss.DrugServiceMetaData;
import com.waseel.pbm.pbmadminservice.repository.mdss.DrugServiceMetaDataRepository;
import com.waseel.pbm.pbmadminservice.repository.mdss.DrugServiceRepository;
import com.waseel.pbm.pbmadminservice.specification.SFDADrugSpecification;
import com.waseel.pbm.pbmadminservice.util.Constants;

@Service
public class SFDAService {

	private final Logger log = LoggerFactory.getLogger(SFDAService.class);

	@Autowired
	DrugServiceRepository drugServiceRepository;

	@Autowired
	DrugServiceMetaDataRepository drugServiceMetaDataRepository;

	@Autowired
	private SFDADrugSpecification sfdaDrugSpecification;

	private static final String INVALID_EXTENSION_MESSAGE = "Invalid file format, only .xls and .xlsx are allowed.";
	private static final String INVALID = "Invalid";
	private static final String INVALID_HEADERS_MESSAGE = "Invalid format. Please reorder/rename the headers or check the values or refer the format of Sample File.";
	private static final String NO_RECORDS_MESSAGE = "File must have at least one record.";

	public SFDAResponseModel addSFDACodeDetails(SFDARequestModel sfdaRequestModel) {
		DrugService drugService = mapRequestModelToDrugService(sfdaRequestModel);
		drugServiceRepository.save(drugService);
		SFDAResponseModel sfdaResponseModel = new SFDAResponseModel();
		sfdaResponseModel.setSfdaCode(sfdaRequestModel.getSfdaCode());
		return sfdaResponseModel;
	}

	private DrugService mapRequestModelToDrugService(SFDARequestModel sfdaRequestModel) {
		String sfdaCode = sfdaRequestModel.getSfdaCode();
		Long drugListId = getDrugListIdByLastEffectiveDate();
		Optional<DrugService> optionalDrugService = drugServiceRepository.findByOtherCodesValueAndDrugListId(sfdaCode,
				drugListId);
		DrugService drugService = new DrugService();
		drugService.setOtherCodesValue(sfdaRequestModel.getSfdaCode());
		drugService.setDrugListId(getDrugListId());
		if (optionalDrugService.isPresent())
			drugService = optionalDrugService.get();
		drugService.setCode(sfdaRequestModel.getGtinCode());
		drugService.setDisplay(sfdaRequestModel.getTradeName());
		drugService.setGranularUnit(sfdaRequestModel.getGranularUnit());
		drugService.setPrice(sfdaRequestModel.getPrice());
		drugService.setScientificCode(sfdaRequestModel.getScientificCode());
		drugService.setIngredients(sfdaRequestModel.getScientificName());
		drugService.setLastUpdatedDate(Timestamp.from(Instant.now()));
		return drugService;
	}

	public Map<Object, Object> addSFDACodeDetailsFromFile(MultipartFile file) throws IOException {
		Map<Object, Object> responseMap = new HashMap<>();
		List<SFDAResponseModel> errorList;
		errorList = validateFileExtension(file);
		if (errorList.isEmpty()) {
			XSSFSheet sheet = getXSSFSheet(file);
			errorList = validateFileHeaders(sheet);
			if (errorList.isEmpty()) {
				List<SFDARequestModel> sfdaRequestModelList = new ArrayList<>();
				List<String> sfdaCodeList = new ArrayList<>();
				int duplicateCount = 0;
				for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
					Row row = sheet.getRow(i);
					SFDARequestModel sfdaRequestModel = mapRequestModelFromRow(row);
					String sfdaCode = sfdaRequestModel.getSfdaCode();
					boolean isDuplicate = sfdaCodeList.contains(sfdaCode);
					validateSFDARequestModel(sfdaRequestModel, i, errorList, sfdaRequestModelList, isDuplicate);
					if (isDuplicate) {
						duplicateCount++;
						continue;
					}
					sfdaCodeList.add(sfdaCode);
				}
				if (errorList.isEmpty()) {
					List<DrugService> drugServiceList = new ArrayList<>();
					sfdaRequestModelList.forEach(
							sfdaRequestModel -> drugServiceList.add(mapRequestModelToDrugService(sfdaRequestModel)));
					drugServiceRepository.saveAll(drugServiceList);
				}
				responseMap.put("DuplicateRecords", duplicateCount);
				responseMap.put("ValidRecords", sfdaRequestModelList.size());
				responseMap.put("InvalidRecords", errorList.size() - duplicateCount);
				responseMap.put("errorList", errorList);
			}
		}
		return responseMap;
	}

	private SFDARequestModel mapRequestModelFromRow(Row row) {
		SFDARequestModel sfdaRequestModel = new SFDARequestModel();
		sfdaRequestModel.setSfdaCode(getCellValue(row, 0));
		sfdaRequestModel.setTradeName(getCellValue(row, 1));
		sfdaRequestModel.setPrice(getCellValue(row, 2));
		sfdaRequestModel.setGranularUnit(getCellValue(row, 3));
		sfdaRequestModel.setScientificCode(getCellValue(row, 4));
		sfdaRequestModel.setScientificName(getCellValue(row, 5));
		sfdaRequestModel.setGtinCode(getCellValue(row, 6));
		return sfdaRequestModel;
	}

	private String getCellValue(Row row, int cellNumber) {
		Cell cell = row.getCell(cellNumber);
		if (cell != null) {
			if (cell.getCellType().equals(CellType.NUMERIC)) {
				return cellNumber == 3 ? (int) cell.getNumericCellValue() + "" : cell.getNumericCellValue() + "";
			}
			return cell.getStringCellValue();
		}
		return null;
	}

	private List<SFDAResponseModel> validateFileHeaders(XSSFSheet sheet) {
		List<SFDAResponseModel> errorList = new ArrayList<>();
		if (sheet.getPhysicalNumberOfRows() < 2) {
			SFDAResponseModel sfdaResponseModel = new SFDAResponseModel();
			sfdaResponseModel.setErrorCode(INVALID);
			sfdaResponseModel.setErrorDescription(NO_RECORDS_MESSAGE);
			errorList.add(sfdaResponseModel);
			return errorList;
		}
		Row row = sheet.getRow(0);
		int cellCount = row.getPhysicalNumberOfCells();
		if (cellCount > 6 && row.getCell(0).getStringCellValue().equalsIgnoreCase("SFDA code")
				&& row.getCell(1).getStringCellValue().equalsIgnoreCase("Trade Name")
				&& row.getCell(2).getStringCellValue().equalsIgnoreCase("Price")
				&& row.getCell(3).getStringCellValue().equalsIgnoreCase("Granular Unit")
				&& row.getCell(4).getStringCellValue().equalsIgnoreCase("Scientific Code")
				&& row.getCell(5).getStringCellValue().equalsIgnoreCase("Scientific Name")
				&& row.getCell(6).getStringCellValue().equalsIgnoreCase("GTIN code")) {
			return errorList;
		}
		SFDAResponseModel sfdaResponseModel = new SFDAResponseModel();
		sfdaResponseModel.setErrorCode(INVALID);
		sfdaResponseModel.setErrorDescription(INVALID_HEADERS_MESSAGE);
		errorList.add(sfdaResponseModel);
		return errorList;
	}

	private long getDrugListId() {
		Long id = drugServiceMetaDataRepository.getDataListIdOrderByEffectiveDateTime();
		if (id != null && id > 0) {
			return id;
		}
		return 1;
	}

	private XSSFSheet getXSSFSheet(MultipartFile file) throws IOException {
		InputStream inputStream = file.getInputStream();
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		return workbook.getSheetAt(0);
	}

	private List<SFDAResponseModel> validateFileExtension(MultipartFile file) {
		List<SFDAResponseModel> errorList = new ArrayList<>();
		String fileName = file.getOriginalFilename();
		if (fileName == null || !fileName.contains(".xls")) {
			SFDAResponseModel sfdaResponseModel = new SFDAResponseModel();
			sfdaResponseModel.setErrorCode(INVALID);
			sfdaResponseModel.setErrorDescription(INVALID_EXTENSION_MESSAGE);
			errorList.add(sfdaResponseModel);
		}
		return errorList;
	}

	private void validateSFDARequestModel(SFDARequestModel sfdaRequestModel, int rowNumber,
			List<SFDAResponseModel> errorList, List<SFDARequestModel> sfdaRequestModelList, boolean isDuplicate) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<SFDARequestModel>> violations = validator.validate(sfdaRequestModel);
		StringBuilder message = new StringBuilder();
		for (ConstraintViolation<SFDARequestModel> c : violations) {
			if (message.length() == 0) {
				message.append(c.getMessage());
				continue;
			}
			message.append(", ").append(c.getMessage());
		}
		if (message.length() > 0 || isDuplicate) {
			SFDAResponseModel sfdaResponseModel = new SFDAResponseModel();
			sfdaResponseModel.setErrorCode(INVALID);
			sfdaResponseModel.setErrorDescription(
					message.length() > 0 ? message.toString() : "Duplicate data found with same SFDA Code");
			sfdaResponseModel.setRowNumber(Long.valueOf(rowNumber));
			errorList.add(sfdaResponseModel);
			return;
		}
		sfdaRequestModelList.add(sfdaRequestModel);
	}

	public SFDAResponseModel editSFDADrug(SFDARequestModel sfdaRequestModel) {
		Long drugListId = getDrugListIdByLastEffectiveDate();
		Optional<DrugService> drugServiceOptional = drugServiceRepository
				.findByOtherCodesValueAndDrugListId(sfdaRequestModel.getSfdaCode(), drugListId);
		if (drugServiceOptional.isPresent()) {
			DrugService drugService = drugServiceOptional.get();
			drugService.setCode(sfdaRequestModel.getGtinCode());
			drugService.setDisplay(sfdaRequestModel.getTradeName());
			drugService.setGranularUnit(sfdaRequestModel.getGranularUnit());
			drugService.setPrice(sfdaRequestModel.getPrice());
			drugService.setScientificCode(sfdaRequestModel.getScientificCode());
			drugService.setIngredients(sfdaRequestModel.getScientificName());
			drugService.setLastUpdatedDate(new Date());
			drugService = drugServiceRepository.save(drugService);
			log.info("Data updated successfully for SFDA Drug : [{}] ", drugService.getOtherCodesValue());
			return new SFDAResponseModel(null, null, drugService.getOtherCodesValue(), null);
		} else {
			log.error("[{}]" + Constants.DRUG_NOT_FOUND, sfdaRequestModel.getSfdaCode());
			return new SFDAResponseModel(Constants.INVALID,
					"[" + sfdaRequestModel.getSfdaCode() + "]" + Constants.DRUG_NOT_FOUND, null, null);
		}
	}

	public Page<SFDADrugReponseModel> getAllSFDADrug(SFDADrugRequestModel sfdaDrugModel) {
		log.info("Page Number :- {}, Record Size :- {}, SFDA Drug Model :- {} ", sfdaDrugModel.getPageNumber(),
				sfdaDrugModel.getRecordSize(), sfdaDrugModel);
		return sfdaDrugSpecification.findSFDADrugsWithPagination(sfdaDrugModel);
	}

	public SFDAResponseModel deleteSFDADrug(String sfdaCode) {
		Long drugListId = getDrugListIdByLastEffectiveDate();
		Optional<DrugService> drugServiceOptional = drugServiceRepository.findByOtherCodesValueAndDrugListId(sfdaCode,
				drugListId);
		if (drugServiceOptional.isPresent()) {
			drugServiceRepository.deleteByOtherCodesValue(sfdaCode);
			log.info("Data updated successfully for SFDA Drug : [{}] ", sfdaCode);
			return new SFDAResponseModel(null, null, sfdaCode, null);
		} else {
			log.error("[{}]" + Constants.DRUG_NOT_FOUND, sfdaCode);
			return new SFDAResponseModel(Constants.INVALID, "[" + sfdaCode + "]" + Constants.DRUG_NOT_FOUND, null,
					null);
		}
	}

	private Long getDrugListIdByLastEffectiveDate() {
		return drugServiceMetaDataRepository
				.findFirstByEffectiveDateLessThanEqualOrderByEffectiveDateDescUploadDateTimeDesc(new Date())
				.map(DrugServiceMetaData::getDrugListId).orElse(0L);
	}
}
