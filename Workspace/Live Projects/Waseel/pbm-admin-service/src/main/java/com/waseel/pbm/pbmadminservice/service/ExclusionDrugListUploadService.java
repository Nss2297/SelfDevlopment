package com.waseel.pbm.pbmadminservice.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.waseel.pbm.pbmadminservice.exceptions.AdminException;
import com.waseel.pbm.pbmadminservice.model.customization.ServiceCodeModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.ExclusionDrugListModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.ExclusionDrugListUploadResponseModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.ExclusionListDrugDetails;
import com.waseel.pbm.pbmadminservice.persist.mdss.DrugService;
import com.waseel.pbm.pbmadminservice.persist.mdss.DrugServiceMetaData;
import com.waseel.pbm.pbmadminservice.repository.mdss.DrugServiceMetaDataRepository;
import com.waseel.pbm.pbmadminservice.repository.mdss.DrugServiceRepository;

@Service
public class ExclusionDrugListUploadService {

	private static final String INVALID_FILE_EMPTY_MESSAGE = "Please select a file to upload.";
	private static final String INVALID_FILE_SIZE_MESSAGE = "File size exceeds the maximum allowed size (5MB).";
	private static final String INVALID_HEADERS_MESSAGE = "Invalid format. Please reorder/rename the headers or check "
			+ "the values or refer the format of Sample File.";
	private static final String NO_RECORDS_MESSAGE = "File must have at least one record.";
	private static final String NO_HEADERS_MESSAGE = "Headers not found.";
	private static final String INVALID_EXTENSION_MESSAGE = "Invalid file format, only .xls or .xlsx are allowed.";
	private static final String STR_DRUG_CODE = "drug ";
	private static final String STR_NOT_FOUND = " was not found.";

	@Autowired
	private DrugServiceRepository drugServiceRepository;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private DrugServiceMetaDataRepository drugServiceMetaDataRepository;

	public ExclusionDrugListUploadResponseModel uploadDrugListFile(MultipartFile file)
			throws AdminException, IOException {
		if (file.isEmpty()) {
			throw new AdminException(INVALID_FILE_EMPTY_MESSAGE);
		}
		ExclusionDrugListUploadResponseModel fileUploadResponse = new ExclusionDrugListUploadResponseModel();
		List<String> errors = new ArrayList<>();
		validateFileExtension(file);
		validateFileSize(file);
		Sheet sheet = getSheet(file);
		validateFileHeaders(sheet);
		validateEmptyRecordsExceptHeader(sheet);
		List<String> duplicateRecordMessages = new ArrayList<>();
		List<ExclusionDrugListModel> exclusionDrugListModel = new ArrayList<>();
		validateExcelRecords(sheet, duplicateRecordMessages, fileUploadResponse, exclusionDrugListModel, errors);
		List<ExclusionListDrugDetails> drugDetails = new ArrayList<>();
		if (errors.isEmpty() && duplicateRecordMessages.isEmpty()) {
			prepareDrugDetails(drugDetails, exclusionDrugListModel);
		}
		return populateFileResponseModel(fileUploadResponse, duplicateRecordMessages, errors,
				drugDetails);
	}

	private void validateFileSize(MultipartFile file) throws AdminException {
		if (file.getSize() > 5 * 1024 * 1024) {
			throw new AdminException(INVALID_FILE_SIZE_MESSAGE);
		}
	}

	private void validateFileExtension(MultipartFile file) throws AdminException {
		String fileName = file.getOriginalFilename();
		if (fileName == null || !isExcelFile(file)) {
			throw new AdminException(INVALID_EXTENSION_MESSAGE);
		}
	}

	private boolean isExcelFile(MultipartFile file) {
		String contentType = file.getContentType();
		String xlsFileType = "application/vnd.ms-excel";
		String xlsxFileType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		return contentType != null && (contentType.equals(xlsFileType) || contentType.equals(xlsxFileType));
	}

	private Sheet getSheet(MultipartFile file) throws IOException, AdminException {
		InputStream inputStream = file.getInputStream();
		String filename = file.getOriginalFilename();
		try (Workbook workbook = getWorkbook(inputStream, filename)) {
			return workbook.getSheetAt(0);
		}
	}

	private Workbook getWorkbook(InputStream inputStream, String filename) throws IOException, AdminException {
		if (filename != null) {
			if (filename.endsWith(".xlsx")) {
				return new XSSFWorkbook(inputStream);
			} else if (filename.endsWith(".xls")) {
				return new HSSFWorkbook(inputStream);
			}
		}
		throw new AdminException(INVALID_EXTENSION_MESSAGE);
	}

	private void validateFileHeaders(Sheet sheet) throws AdminException {
		Row headerRow = sheet.getRow(0);
		if (headerRow == null) {
			throw new AdminException(NO_HEADERS_MESSAGE);
		}
		checkHeaderCellHasNumericOrBlankCellType(headerRow);
		int cellCount = headerRow.getPhysicalNumberOfCells();
		if (!(cellCount == 1 && headerRow.getCell(0).getStringCellValue().trim().equalsIgnoreCase("DrugCode"))) {
			throw new AdminException(INVALID_HEADERS_MESSAGE);
		}
	}

	private void checkHeaderCellHasNumericOrBlankCellType(Row headerRow) throws AdminException {
		boolean hasInvalidCellType = Stream.iterate(0, i -> i + 1).limit(headerRow.getPhysicalNumberOfCells())
				.map(headerRow::getCell).anyMatch(cell -> cell == null || cell.getCellType() == CellType.BLANK
						|| cell.getCellType() == CellType.NUMERIC);
		if (hasInvalidCellType) {
			throw new AdminException(INVALID_HEADERS_MESSAGE);
		}
	}

	private void validateEmptyRecordsExceptHeader(Sheet sheet) throws AdminException {
		if (sheet.getPhysicalNumberOfRows() < 2) {
			throw new AdminException(NO_RECORDS_MESSAGE);
		}
		for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
			Row currentRow = sheet.getRow(rowNum);
			if (!isRowEmpty(currentRow, sheet.getRow(0))) {
				return;
			}
		}
		throw new AdminException(NO_RECORDS_MESSAGE);
	}

	private static boolean isRowEmpty(Row currentRow, Row initialRow) {
		if (currentRow == null || currentRow.getFirstCellNum() == -1 || currentRow.getLastCellNum() <= 0) {
			return true;
		}
		for (int c = currentRow.getFirstCellNum(); c < initialRow.getLastCellNum(); c++) {
			Cell cell = currentRow.getCell(c);
			if ((cell != null && cell.getCellType() != CellType.BLANK) || (cell != null
					&& cell.getCellType() == CellType.STRING && !cell.getStringCellValue().strip().isEmpty())) {
				return false;
			}
		}
		return true;
	}

	private void validateExcelRecords(Sheet sheet, List<String> duplicateRecordMessages,
			ExclusionDrugListUploadResponseModel fileUploadResponse,
			List<ExclusionDrugListModel> exclusionDrugListModelList, List<String> errorList) {
		List<ServiceCodeModel> drugServiceList = matchDataFromDbToExcel(sheet, 0, (batchOfValues, resultList) -> {
			findServiceCodesFromDb(batchOfValues, resultList);
			return resultList;
		}, new ArrayList<>());
		final Row initialRow = sheet.getRow(0);
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row != null) {
				int rowNumber = i + 1;
				ExclusionDrugListModel exclusionDrugListModel = mapRequestModelFromRow(row, initialRow);
				exclusionDrugListModelList.add(exclusionDrugListModel);
				validateExclusionDrugListModel(rowNumber, errorList, drugServiceList, exclusionDrugListModel);
			}
		}
		validateDuplicates(sheet, duplicateRecordMessages, fileUploadResponse);
	}

	private void validateExclusionDrugListModel(int rowNumber, List<String> errorList,
			List<ServiceCodeModel> drugServiceList, ExclusionDrugListModel exclusionDrugListModel) {
		List<String> errorMessages = new ArrayList<>();
		if (exclusionDrugListModel != null && !exclusionDrugListModel.getDrugCode().isEmpty()) {
			validateServiceCode(drugServiceList, exclusionDrugListModel.getDrugCode(), errorMessages);
			if (!errorMessages.isEmpty()) {
				errorList.add(("In row number " + rowNumber + ", " + errorMessages.toString()).replace("[", "")
						.replace("]", ""));
			}
		}
	}

	private void validateServiceCode(List<ServiceCodeModel> drugServiceList, String serviceCode,
			List<String> errorMessages) {
		if (hasNotNullValidationMessage(STR_DRUG_CODE, errorMessages)) {
			return;
		}
		ServiceCodeModel matchedServiceCode = drugServiceList.stream()
				.filter(serviceInfo -> serviceInfo.getserviceCode().equalsIgnoreCase(serviceCode)
						&& serviceInfo.getIsValid() == 1)
				.findAny().orElse(null);
		if (matchedServiceCode == null || serviceCode.getBytes().length > 250) {
			errorMessages.add(STR_DRUG_CODE + serviceCode + STR_NOT_FOUND);
		}
	}

	private boolean hasNotNullValidationMessage(String modelFieldName, List<String> errorMessages) {
		String notNullValidationMsg = messageSource.getMessage("notNullOrEmpty", null, Locale.getDefault());
		return errorMessages.contains(modelFieldName + " " + notNullValidationMsg);
	}

	private void validateDuplicates(Sheet sheet, List<String> duplicateRecordMessages,
			ExclusionDrugListUploadResponseModel fileUploadResponse) {
		int drugCodeColumnIndex = 0;
		Map<String, Set<Integer>> drugCodesMap = new HashMap<>();
		for (Row row : sheet) {
			Cell drugCodeCell = row.getCell(drugCodeColumnIndex);
			if (drugCodeCell != null) {
				String drugCode = getCellValueAsString(drugCodeCell);
				if (drugCode != null) {
					Set<Integer> rowNumbers = drugCodesMap.getOrDefault(drugCode, new HashSet<>());
					rowNumbers.add(row.getRowNum() + 1);
					drugCodesMap.put(drugCode, rowNumbers);
				}
			}
		}
		int count = 0;
		for (Map.Entry<String, Set<Integer>> entry : drugCodesMap.entrySet()) {
			String drugCode = entry.getKey();
			Set<Integer> rowNumbers = entry.getValue();
			if (rowNumbers.size() > 1) {
				String message = "Duplicate entry found for drug with code: " + drugCode + " in rows " + rowNumbers;
				duplicateRecordMessages.add(message);
				++count;
			}
		}
		if (count > 0) {
			fileUploadResponse.setDuplicateRecordCount(count);
		}
	}

	private static String getCellValueAsString(Cell cell) {
		CellType cellType = cell.getCellType();
		if (cellType == CellType.STRING) {
			return cell.getStringCellValue();
		} else if (cellType == CellType.NUMERIC) {
			return String.valueOf((long) cell.getNumericCellValue());
		} else if (cellType == CellType.FORMULA) {
			return getCellValueAsString(cell.getCachedFormulaResultType() == CellType.STRING ? cell
					: cell.getSheet().getRow(cell.getRowIndex()).getCell(cell.getColumnIndex()));
		}
		return null;
	}

	private void findServiceCodesFromDb(List<String> currentBatchOfServiceCodes,
			List<ServiceCodeModel> finalServiceCodesList) {
		Long drugListId = getDrugListId();
		List<ServiceCodeModel> drugServiceList = drugServiceRepository.findByServiceCodes(currentBatchOfServiceCodes,
				drugListId);
		finalServiceCodesList.addAll(drugServiceList);
	}

	private <T> List<T> matchDataFromDbToExcel(Sheet sheet, int cellNumber,
			BiFunction<List<String>, List<T>, List<T>> dbQueryFunction, List<T> finalList) {
		Set<String> uniqueValuesFromExcel = getUniqueValueFromExcel(sheet, cellNumber);
		List<String> currentBatchOfValues = new ArrayList<>();

		uniqueValuesFromExcel.forEach(value -> {
			currentBatchOfValues.add(value);
		});
		if (!currentBatchOfValues.isEmpty()) {
			finalList.addAll(dbQueryFunction.apply(new ArrayList<>(currentBatchOfValues), finalList));
		}
		return finalList;
	}

	private Set<String> getUniqueValueFromExcel(Sheet sheet, int cellNumber) {
		Set<String> uniqueValueFromExcel = new HashSet<>();
		for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
			Row row = sheet.getRow(rowIndex);
			if (row != null) {
				Cell cell = row.getCell(cellNumber);
				if (cell != null) {
					if (cell.getCellType() == CellType.STRING) {
						uniqueValueFromExcel.add(cell.getStringCellValue());
					} else if (cell.getCellType().equals(CellType.NUMERIC)) {
						DecimalFormat decimalFormat = new DecimalFormat("#");
						uniqueValueFromExcel.add(decimalFormat.format(cell.getNumericCellValue()));
					}
				}
			}
		}
		return uniqueValueFromExcel;
	}

	private ExclusionDrugListModel mapRequestModelFromRow(Row row, Row initialRow) {
		if (isRowEmpty(row, initialRow)) {
			return null;
		}
		ExclusionDrugListModel model = new ExclusionDrugListModel();
		model.setDrugCode(getCellValue(row, 0));
		return model;
	}

	private String getCellValue(Row row, int cellNumber) {
		DecimalFormat decimalFormat = new DecimalFormat("#");
		Cell cell = row.getCell(cellNumber);
		if (cell != null) {
			if (cell.getCellType().equals(CellType.NUMERIC)) {
				return decimalFormat.format(cell.getNumericCellValue());
			}
			return cell.getStringCellValue();
		}
		return null;
	}

	private void prepareDrugDetails(List<ExclusionListDrugDetails> drugDetails,
			List<ExclusionDrugListModel> exclusionListDrugs) {
		Set<String> drugCodes = new HashSet<>();
		    // Check if exclusionListDrugs is null
			if (exclusionListDrugs != null) {
				drugCodes.addAll(
					exclusionListDrugs.stream()
						.filter(Objects::nonNull)  // Exclude null elements from the list
						.map(ExclusionDrugListModel::getDrugCode)
						.filter(Objects::nonNull)  // Exclude null drug codes
						.collect(Collectors.toSet())
				);
			}
		Long drugListId = getDrugListId();
		List<DrugService> drugServices = drugServiceRepository.findByOtherCodesValueInAndDrugListId(drugCodes,
				drugListId);
		if (null != drugServices && !drugServices.isEmpty()) {
			drugServices.stream().forEach(drugService -> {
				ExclusionListDrugDetails drug = new ExclusionListDrugDetails();
				drug.setDrugCode(drugService.getOtherCodesValue());
				drug.setDrugName(drugService.getDisplay());
				drug.setLastUpdateDate(drugService.getLastUpdatedDate());
				drug.setPrice(drugService.getPrice());
				drug.setScientificCode(drugService.getScientificCode());
				drug.setScientificName(drugService.getIngredients());
				drug.setWaseelDrugId(String.valueOf(drugService.getWaseelDrugId()));
				drugDetails.add(drug);
			});
		}
	}

	private Long getDrugListId() {
		return drugServiceMetaDataRepository
				.findFirstByEffectiveDateLessThanEqualOrderByEffectiveDateDescUploadDateTimeDesc(new Date())
				.map(DrugServiceMetaData::getDrugListId).orElse(0L);
	}

	private ExclusionDrugListUploadResponseModel populateFileResponseModel(
			ExclusionDrugListUploadResponseModel fileUploadResponse,
			List<String> duplicateRecordMessages, List<String> errors, List<ExclusionListDrugDetails> drugDetails) {
		if (!duplicateRecordMessages.isEmpty()) {
			fileUploadResponse.setDuplicateRecords(duplicateRecordMessages);
		}
		if (!errors.isEmpty()) {
			fileUploadResponse.setErrors(errors);
		}
		if (!drugDetails.isEmpty()) {
			fileUploadResponse.setExclusionListDrugDetailsRequestModel(drugDetails);
		}
		return fileUploadResponse;
	}
}
