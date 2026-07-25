package com.waseel.dssadminservice.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Stream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import com.waseel.dssadminservice.exceptions.AdminException;

@Service
public class ExcelValidationUtils {

	private static final String INVALID_EXTENSION_MESSAGE = "Invalid file format, only .xls or .xlsx are allowed.";
	private static final String INVALID_FILE_SIZE_MESSAGE = "File size exceeds the maximum allowed size (5MB).";
	private static final String INVALID_HEADERS_MESSAGE = "Invalid format. Please reorder/rename the headers or check "
			+ "the values or refer the format of Sample File.";
	private static final String NO_RECORDS_MESSAGE = "File must have at least one record.";
	private static final int BATCH_SIZE = 999;

	public void validateFileExtension(MultipartFile file) throws AdminException {
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

	public void validateFileSize(MultipartFile file, long size) throws AdminException {
		DataSize maxFileSize = DataSize.ofMegabytes(size);
		if (file.getSize() > maxFileSize.toBytes()) {
			throw new AdminException(INVALID_FILE_SIZE_MESSAGE);
		}
	}

	public Sheet getSheet(MultipartFile file) throws IOException, AdminException {
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

	public void checkHeaderCellHasNumericOrBlankCellType(Row headerRow) throws AdminException {
		boolean hasInvalidCellType = Stream.iterate(0, i -> i + 1).limit(headerRow.getPhysicalNumberOfCells())
				.map(headerRow::getCell).anyMatch(cell -> cell == null || cell.getCellType() == CellType.BLANK
						|| cell.getCellType() == CellType.NUMERIC);
		if (hasInvalidCellType) {
			throw new AdminException(INVALID_HEADERS_MESSAGE);
		}
	}

	public boolean isRowEmpty(Row currentRow, Row initialRow) {
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

	public void validateEmptyRecordsExceptHeader(Sheet sheet) throws AdminException {
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

	public <T> List<T> matchDataFromDbToExcel(Sheet sheet, int cellNumber,
			BiFunction<List<String>, List<T>, List<T>> dbQueryFunction, List<T> finalList) {
		Set<String> uniqueValuesFromExcel = getUniqueValueFromExcel(sheet, cellNumber);
		List<String> currentBatchOfValues = new ArrayList<>();

		uniqueValuesFromExcel.forEach(value -> {
			currentBatchOfValues.add(value);
			if (currentBatchOfValues.size() == BATCH_SIZE) {
				finalList.addAll(dbQueryFunction.apply(new ArrayList<>(currentBatchOfValues), finalList));
				currentBatchOfValues.clear();
			}
		});
		if (!currentBatchOfValues.isEmpty()) {
			finalList.addAll(dbQueryFunction.apply(new ArrayList<>(currentBatchOfValues), finalList));
		}
		return finalList;
	}

	public <T> List<T> matchDataFromDbToExcelForMultiCells(Sheet sheet, List<Integer> cellNumbers,
			BiFunction<List<String>, List<T>, List<T>> dbQueryFunction, List<T> finalList) {
		Set<String> uniqueValuesFromExcel = new HashSet<>();

		for (int cellNumber : cellNumbers) {
			uniqueValuesFromExcel.addAll(getUniqueValueFromExcel(sheet, cellNumber));
		}

		List<String> currentBatchOfValues = new ArrayList<>();

		uniqueValuesFromExcel.forEach(value -> {
			currentBatchOfValues.add(value);
			if (currentBatchOfValues.size() == BATCH_SIZE) {
				finalList.addAll(dbQueryFunction.apply(new ArrayList<>(currentBatchOfValues), finalList));
				currentBatchOfValues.clear();
			}
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

	public String getCellValue(Row row, int cellNumber) {
		try {
			DecimalFormat decimalFormat = new DecimalFormat("#");
			Cell cell = row.getCell(cellNumber);
			if (cell != null) {
				if (cell.getCellType().equals(CellType.NUMERIC)) {
					return decimalFormat.format(cell.getNumericCellValue());
				}
				return cell.getStringCellValue();
			}
		} catch (Exception e) {
		}
		return null;
	}

	public String getCellValueForDecimals(Row row, int cellNumber) {
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		Cell cell = row.getCell(cellNumber);
		if (cell != null) {
			if (cell.getCellType().equals(CellType.NUMERIC)) {
				return decimalFormat.format(cell.getNumericCellValue());
			}
			return cell.getStringCellValue();
		}
		return null;
	}
}
