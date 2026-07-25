package com.waseel.dssadminservice.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.multipart.MultipartFile;

import com.waseel.dssadminservice.enums.AuditLogAction;
import com.waseel.dssadminservice.enums.EntitiesName;
import com.waseel.dssadminservice.exceptions.AdminException;
import com.waseel.dssadminservice.mapper.DrugServiceMapper;
import com.waseel.dssadminservice.model.sfdamanagement.Drug;
import com.waseel.dssadminservice.model.sfdamanagement.DrugResponseModel;
import com.waseel.dssadminservice.model.sfdamanagement.SFDADrugListResponseModel;
import com.waseel.dssadminservice.model.sfdamanagement.SFDADrugRequestModel;
import com.waseel.dssadminservice.model.sfdamanagement.SFDADrugResponseModel;
import com.waseel.dssadminservice.model.sfdamanagement.SFDAExcelFields;
import com.waseel.dssadminservice.model.sfdamanagement.SFDAExcelRequestModel;
import com.waseel.dssadminservice.model.sfdamanagement.SFDAManagementResponseModel;
import com.waseel.dssadminservice.model.sfdamanagement.SFDAMetaDataResponseModel;
import com.waseel.dssadminservice.model.sfdamanagement.SFDAMetaDataSearchModel;
import com.waseel.dssadminservice.model.sfdamanagement.SfdaDrugSearchModel;
import com.waseel.dssadminservice.persist.mdss.DrugService;
import com.waseel.dssadminservice.persist.mdss.DrugServiceMetaData;
import com.waseel.dssadminservice.repository.mdss.DrugServiceMetaDataRepository;
import com.waseel.dssadminservice.repository.mdss.DrugServiceRepository;
import com.waseel.dssadminservice.specification.SFDADrugListSpecification;
import com.waseel.dssadminservice.specification.SFDAManagementSpecification;
import com.waseel.dssadminservice.util.ExcelValidationUtils;
import com.waseel.dssadminservice.util.UserInfoUtil;

@Service
public class SFDAManagementService {

	private final Logger logger = LoggerFactory.getLogger(SFDAManagementService.class);

	private static final String STR_WASEEL_DRUGID_NOT_FOUND = "WaseelDrugId is not found or exists.";
	private static final String STR_DRUGLISTID_NOT_FOUND = "DrugListId is not found or exists.";

	@Autowired
	private SFDAManagementSpecification sfdaManagementSpecification;
	@Autowired
	private SFDADrugListSpecification sfdaDrugListSpecification;
	@Autowired
	private DrugServiceRepository drugServiceRepository;
	@Autowired
	private DrugServiceMetaDataRepository drugServiceMetaDataRepository;
	@Autowired
	private AuditLogService auditLogService;
	@Autowired
	private ExcelValidationUtils excelValidationUtils;

	private static final String NO_HEADERS_MESSAGE = "Headers not found.";
	private static final String INVALID_HEADERS_MESSAGE = "Invalid format. Please reorder/rename the headers or check the values or refer the format of Sample File.";

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public SFDAManagementResponseModel uploadDrugsFromFile(String effectiveDateStr, MultipartFile sfdaFile)
			throws AdminException, IOException, ParseException {
		logger.info("Checking File name exist or not for File: {}", sfdaFile.getOriginalFilename());
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date effectiveDate = formatter.parse(effectiveDateStr);
		Optional<DrugServiceMetaData> drugMetaDataOp = drugServiceMetaDataRepository
				.findByFileNameAndEffectiveDate(sfdaFile.getOriginalFilename(), effectiveDate);
		if (drugMetaDataOp.isPresent()) {
			throw new AdminException("SFDA Filename already exists. Please change Filename and upload again.");
		}

		logger.info("Start Validating SFDA file: {}", sfdaFile.getOriginalFilename());
		excelValidationUtils.validateFileExtension(sfdaFile);
		excelValidationUtils.validateFileSize(sfdaFile, 5L);

		Sheet sheet = excelValidationUtils.getSheet(sfdaFile);
		validateFileHeaders(sheet);
		excelValidationUtils.validateEmptyRecordsExceptHeader(sheet);

		SFDAManagementResponseModel responseModel = new SFDAManagementResponseModel();

		List<SFDAManagementResponseModel> errorList = new ArrayList<>();
		List<String> duplicateRecordMessages = new ArrayList<>();
		Map<Integer, List<Integer>> duplicateRecordRowNumberMap = new HashMap<>();
		Map<String, Integer> duplicateRecordMsgMap = new HashMap<>();
		List<SFDAExcelRequestModel> excelRequestModels = new ArrayList<>();
		validateExcelRecords(sheet, duplicateRecordMsgMap, duplicateRecordRowNumberMap, excelRequestModels, errorList);
		int duplicateRecordCount = addDuplicateRecordMessageInList(duplicateRecordRowNumberMap,
				duplicateRecordMessages);

		logger.info("SFDA file: {} validated", sfdaFile.getOriginalFilename());
		if (errorList.isEmpty() && duplicateRecordMessages.isEmpty()) {
			logger.info("Started saving SFDA File: {} ", sfdaFile.getOriginalFilename());
			saveDrugServiceData(excelRequestModels, effectiveDate, sfdaFile.getOriginalFilename());
			logger.info("Saving completed for SFDA File: {} ", sfdaFile.getOriginalFilename());
		}
		if (duplicateRecordCount > 0)
			responseModel.setDuplicateRecordCount(duplicateRecordCount);
		List<String> allErrors = errorList.stream().map(SFDAManagementResponseModel::getErrors) // Stream<List<String>>
				.filter(Objects::nonNull).flatMap(List::stream) // Flatten the Stream<List<String>> to Stream<String>
				.collect(Collectors.toList());
		allErrors.addAll(duplicateRecordMessages);
		responseModel.setErrors(allErrors);
		return responseModel;
	}

	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	private void saveDrugServiceData(List<SFDAExcelRequestModel> excelRequestModels, Date effectiveDate,
			String fileName) {
		DrugServiceMetaData serviceMetaData = new DrugServiceMetaData();
		serviceMetaData.setEffectiveDate(effectiveDate);
		serviceMetaData.setFileName(fileName);
		String ownerName = UserInfoUtil.getAccName(SecurityContextHolder.getContext().getAuthentication());
		serviceMetaData.setOwnerName(ownerName);
		serviceMetaData.setSfdaUpdateDate(new Date());
		serviceMetaData.setUploadDateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		serviceMetaData.setSfdaVersion(fetchSfdaVersion());

		serviceMetaData = drugServiceMetaDataRepository.save(serviceMetaData);
		final Long drugListId = serviceMetaData.getDrugListId();
		excelRequestModels.stream().forEach(request -> {
			DrugService drugService = new DrugService();
			drugService.setCategory("PHARMACEUTICAL");
			drugService.setCode(request.getGtinCode());
			drugService.setDiscontinueDate(null);
			drugService.setDisplay(request.getTradeName());
			drugService.setDosageForm(request.getDosageForm());
			drugService.setDrugListId(drugListId);
			drugService.setGranularUnit(request.getGranularUnit());
			drugService.setIngredients(request.getScientificName());
			drugService.setLastUpdatedDate(new Date());
			drugService.setOtherCodesType("SFDA");
			drugService.setOtherCodesValue(request.getSfdaCode());
			drugService.setPackageSize(request.getPackageSize());
			drugService.setPrice(request.getPrice());
			drugService.setScientificCode(request.getScientificCode());
			drugService.setStrength(request.getStrength());
			drugService.setStrengthUnit(request.getStrengthUnit());
			drugService.setRoaSuggested(request.getAdministrationRoute());
			drugService.setWaseelDrugId(getWaseelDrugId());
			drugService = drugServiceRepository.save(drugService);
		});
	}

	private String fetchSfdaVersion() {
		Optional<DrugServiceMetaData> oldDrugServiceOp = drugServiceMetaDataRepository
				.findFirstByOrderByDrugListIdDesc();
		String version = "";
		if (oldDrugServiceOp.isPresent()) {
			String oldVersion = oldDrugServiceOp.get().getSfdaVersion();
			int versionNumber = Integer.valueOf(oldVersion.substring(1)) + 1;
			version = "V" + versionNumber;
		} else {
			version = "V1";
		}
		return version;
	}

	private int addDuplicateRecordMessageInList(Map<Integer, List<Integer>> duplicateRecordRowNumberMap,
			List<String> duplicateRecordMessages) {
		AtomicInteger duplicateRecordCount = new AtomicInteger();
		duplicateRecordCount.set(0);
		duplicateRecordRowNumberMap.entrySet().forEach(entry -> {
			duplicateRecordMessages.add("Duplicate SFDA code record found for row number " + entry.getKey()
					+ " at row number(s) " + StringUtils.strip(entry.getValue().toString(), "[]"));
			duplicateRecordCount.set(duplicateRecordCount.get() + entry.getValue().size());
		});
		return duplicateRecordCount.get();
	}

	public Page<SFDAMetaDataResponseModel> getAllSFDAList(SFDAMetaDataSearchModel sfdaMetaDataSearchModel) {
		logger.info("Fetch SFDA MetaData List for page number: [{}], and recode size: [{}]",
				sfdaMetaDataSearchModel.getPageNumber(), sfdaMetaDataSearchModel.getRecordSize());
		return sfdaManagementSpecification.findSFDAListWithPagination(sfdaMetaDataSearchModel);
	}

	private void validateExcelRecords(Sheet sheet, Map<String, Integer> duplicateRecordMsgMap,
			Map<Integer, List<Integer>> duplicateRecordRowNumberMap, List<SFDAExcelRequestModel> excelRequestModels,
			List<SFDAManagementResponseModel> errorList) {
		final Row initialRow = sheet.getRow(0);
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row != null) {
				int rowNumber = i + 1;
				SFDAExcelRequestModel requestModel = mapRequestModelFromRow(row, rowNumber, initialRow);
				if (requestModel != null) {
					findDuplicateRecordsWithRowNumbers(requestModel, duplicateRecordMsgMap, rowNumber,
							duplicateRecordRowNumberMap);
					validatePCDrugToDiagnosisRequestModel(requestModel, rowNumber, errorList, excelRequestModels);
				}
			}
		}

	}

	private void validatePCDrugToDiagnosisRequestModel(SFDAExcelRequestModel requestModel, int rowNumber,
			List<SFDAManagementResponseModel> errorList, List<SFDAExcelRequestModel> excelRequestModel) {
		List<String> errorMessages = new ArrayList<>();
		validateBeanValidationOfRequestModel(requestModel, errorMessages);
		if (!errorMessages.isEmpty()) {
			SFDAManagementResponseModel model = new SFDAManagementResponseModel();
			model.setErrors(errorMessages);
			model.setRowNumber(rowNumber);
			errorList.add(model);
			return;
		}
		excelRequestModel.add(requestModel);
	}

	private void validateBeanValidationOfRequestModel(SFDAExcelRequestModel requestModel, List<String> errorMessages) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<SFDAExcelRequestModel>> violations = validator.validate(requestModel);
		for (ConstraintViolation<SFDAExcelRequestModel> c : violations) {
			errorMessages.add(c.getMessage() + " at line number: [" + requestModel.getRowNumber() + "]");
		}
	}

	private void findDuplicateRecordsWithRowNumbers(SFDAExcelRequestModel requestModel,
			Map<String, Integer> duplicateRecordMsgMap, int i,
			Map<Integer, List<Integer>> duplicateRecordRowNumberMap) {
		String recordStrLowerCase = requestModel.getSfdaCode().toLowerCase();
		if (!duplicateRecordMsgMap.containsKey(recordStrLowerCase)) {
			duplicateRecordMsgMap.put(recordStrLowerCase, i);
		} else {
			Integer originalRowNumber = duplicateRecordMsgMap.get(recordStrLowerCase);
			duplicateRecordRowNumberMap.computeIfAbsent(originalRowNumber, k -> new ArrayList<>()).add(i);
		}
	}

	private SFDAExcelRequestModel mapRequestModelFromRow(Row row, int rowNumber, Row initialRow) {
		if (excelValidationUtils.isRowEmpty(row, initialRow)) {
			return null;
		}
		SFDAExcelRequestModel model = new SFDAExcelRequestModel();
		model.setGtinCode(excelValidationUtils.getCellValue(row, 0));
		model.setTradeName(excelValidationUtils.getCellValue(row, 1));
		model.setPrice(excelValidationUtils.getCellValue(row, 2));
		model.setGranularUnit(excelValidationUtils.getCellValue(row, 3));
		model.setDosageForm(excelValidationUtils.getCellValue(row, 4));
		model.setAdministrationRoute(excelValidationUtils.getCellValue(row, 5));
		model.setPackageType(excelValidationUtils.getCellValue(row, 6));
		model.setPackageSize(excelValidationUtils.getCellValue(row, 7));
		model.setScientificName(excelValidationUtils.getCellValue(row, 8));
		model.setStrength(excelValidationUtils.getCellValue(row, 9));
		model.setSfdaCode(excelValidationUtils.getCellValue(row, 10));
		model.setScientificCode(excelValidationUtils.getCellValue(row, 11));
		model.setStrengthUnit(excelValidationUtils.getCellValue(row, 12));
		model.setRowNumber(rowNumber);
		return model;
	}

	private void validateFileHeaders(Sheet sheet) throws AdminException {
		Row headerRow = sheet.getRow(0);
		// CHECK FOR EMPTY HEADER
		if (excelValidationUtils.isRowEmpty(headerRow, sheet.getRow(0))) {
			throw new AdminException(NO_HEADERS_MESSAGE);
		}
		excelValidationUtils.checkHeaderCellHasNumericOrBlankCellType(headerRow);
		int cellCount = headerRow.getPhysicalNumberOfCells();
		if (!(cellCount > 12
				&& headerRow.getCell(0).getStringCellValue().trim()
						.equalsIgnoreCase(SFDAExcelFields.GTIN_CODE.getFieldName())
				&& headerRow.getCell(1).getStringCellValue().trim()
						.equalsIgnoreCase(SFDAExcelFields.TRADE_NAME.getFieldName())
				&& headerRow.getCell(2).getStringCellValue().trim()
						.equalsIgnoreCase(SFDAExcelFields.PRICE.getFieldName())
				&& headerRow.getCell(3).getStringCellValue().trim()
						.equalsIgnoreCase(SFDAExcelFields.GRANULAR_UNIT.getFieldName())
				&& headerRow.getCell(4).getStringCellValue().trim()
						.equalsIgnoreCase(SFDAExcelFields.DOSAGE_FORM.getFieldName())
				&& headerRow.getCell(5).getStringCellValue().trim()
						.equalsIgnoreCase(SFDAExcelFields.ADMINISTRATION_ROUTE.getFieldName())
				&& headerRow.getCell(6).getStringCellValue().trim()
						.equalsIgnoreCase(SFDAExcelFields.PACKAGE_TYPE.getFieldName())
				&& headerRow.getCell(7).getStringCellValue().trim()
						.equalsIgnoreCase(SFDAExcelFields.PACKAGE_SIZE.getFieldName())
				&& headerRow.getCell(8).getStringCellValue().trim()
						.equalsIgnoreCase(SFDAExcelFields.SCIENTIFIC_NAME.getFieldName())
				&& headerRow.getCell(9).getStringCellValue().trim()
						.equalsIgnoreCase(SFDAExcelFields.STRENGTH.getFieldName())
				&& headerRow.getCell(10).getStringCellValue().trim()
						.equalsIgnoreCase(SFDAExcelFields.SFDA_CODE.getFieldName())
				&& headerRow.getCell(11).getStringCellValue().trim()
						.equalsIgnoreCase(SFDAExcelFields.SCIENTIFIC_CODE.getFieldName())
				&& headerRow.getCell(12).getStringCellValue().trim()
						.equalsIgnoreCase(SFDAExcelFields.STRENGTH_UNIT.getFieldName()))) {
			throw new AdminException(INVALID_HEADERS_MESSAGE);
		}
	}

	public SFDAManagementResponseModel populateUnAuthorizedResponse(AccessDeniedException ex) {
		List<String> errors = new ArrayList<>();
		errors.add(ex.getMessage());
		return new SFDAManagementResponseModel(errors);
	}

	public SFDAManagementResponseModel populateFailedResponse(Exception exception) {
		List<String> errors = new ArrayList<>();
		errors.add(exception.getMessage());
		return new SFDAManagementResponseModel(errors);
	}

	public SFDAManagementResponseModel populateInvalidResponse(AdminException adminException) {
		List<String> errors = new ArrayList<>();
		errors.add(adminException.getMessage());
		return new SFDAManagementResponseModel(errors);
	}

	public SFDAManagementResponseModel populateInvalidResponseForConstraints(Exception exception) {
		List<String> errors = new ArrayList<>();
		if (exception instanceof ConstraintViolationException) {
			ConstraintViolationException constraintViolationException = (ConstraintViolationException) exception;
			errors.addAll(constraintViolationException.getConstraintViolations().stream()
					.map(ConstraintViolation::getMessage).collect(Collectors.toList()));
		}
		if (exception instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) exception;
			errors.addAll(methodArgumentNotValidException.getBindingResult().getAllErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList()));
		}
		return new SFDAManagementResponseModel(errors);
	}

	public SFDADrugResponseModel getSpecificDrugDetails(Long waseelDrugId, Long drugListId) throws AdminException {
		Optional<DrugServiceMetaData> drugServiceMetaDataOpt = drugServiceMetaDataRepository
				.findByDrugListId(drugListId);
		if (drugServiceMetaDataOpt.isPresent()) {
			Optional<DrugService> drugService = drugServiceRepository.findByWaseelDrugId(waseelDrugId);
			if (drugService.isPresent()) {
				return DrugServiceMapper.INSTANCE.drugServiceToSFDADrugResponseModel(drugService.get());
			}
			throw new AdminException(STR_WASEEL_DRUGID_NOT_FOUND);
		}
		throw new AdminException(STR_DRUGLISTID_NOT_FOUND);
	}

	@Transactional
	public void updateDrugDetails(Long waseelDrugId, Long drugListId, SFDADrugRequestModel sfdaDrugRequestModel)
			throws AdminException {
		Optional<DrugServiceMetaData> drugServiceMetaDataOpt = drugServiceMetaDataRepository
				.findByDrugListId(drugListId);
		if (drugServiceMetaDataOpt.isPresent()) {
			Optional<DrugService> drugServiceOpt = drugServiceRepository.findByWaseelDrugId(waseelDrugId);
			if (drugServiceOpt.isPresent()) {
				DrugService drugService = drugServiceOpt.get();
				DrugServiceMapper.INSTANCE.updateDrugServiceFromSFDADrugRequestModel(sfdaDrugRequestModel, drugService);
				drugService.setLastUpdatedDate(new Date());
				drugServiceRepository.save(drugService);
				auditLogService.addDataInAuditLog(drugService.getWaseelDrugId(), EntitiesName.DRUG_SERVICE,
						AuditLogAction.UPDATE, drugService);
				logger.info("Data updated successfully for SFDA Drug: [{}] , WaseelDrugId: [{}]",
						drugService.getOtherCodesValue(), waseelDrugId);
				return;
			}
			throw new AdminException(STR_WASEEL_DRUGID_NOT_FOUND);
		}
		throw new AdminException(STR_DRUGLISTID_NOT_FOUND);
	}

	public SFDADrugListResponseModel getDrugListDetails(Long drugListId, SfdaDrugSearchModel sfdaDrugSearchModel)
			throws AdminException {
		Optional<DrugServiceMetaData> drugServiceMetaDataOpt = drugServiceMetaDataRepository
				.findByDrugListId(drugListId);
		if (drugServiceMetaDataOpt.isPresent()) {
			Optional<Page<DrugService>> drugListDetailsOpt = sfdaDrugListSpecification
					.getSfdaDrugsPaginated(sfdaDrugSearchModel, drugListId);
			if (drugListDetailsOpt.isPresent() && !drugListDetailsOpt.get().isEmpty()) {
				Page<DrugService> drugListDetailsPage = drugListDetailsOpt.get();
				return createSFDADrugListResponseModel(drugListId, drugServiceMetaDataOpt.get(), drugListDetailsPage);
			}
			throw new AdminException("No corresponding drugs were found.");
		}
		throw new AdminException("DrugListId is not found.");
	}

	private SFDADrugListResponseModel createSFDADrugListResponseModel(Long drugListId,
			DrugServiceMetaData drugServiceMetaData, Page<DrugService> drugListDetails) {
		SFDADrugListResponseModel sfdaDrugListResponseModel = new SFDADrugListResponseModel();
		sfdaDrugListResponseModel.setEffectiveDate(drugServiceMetaData.getEffectiveDate().toString());
		sfdaDrugListResponseModel.setUploadDate(drugServiceMetaData.getUploadDateTime().toString());
		sfdaDrugListResponseModel.setId(drugListId.toString());
		List<Drug> drugs = drugListDetails.getContent().stream()
				.map(drugService -> new Drug(drugService.getOtherCodesValue(), drugService.getCode(),
						drugService.getDisplay(), drugService.getIngredients(), drugService.getPrice(),
						drugService.getWaseelDrugId().toString(), drugService.getScientificCode()))
				.collect(Collectors.toList());

		sfdaDrugListResponseModel
				.setDrugs(new PageImpl<>(drugs, drugListDetails.getPageable(), drugListDetails.getTotalElements()));
		return sfdaDrugListResponseModel;
	}

	@Transactional
	public DrugResponseModel addDrug(long drugListId, SFDADrugRequestModel sfdaRequestModel) throws AdminException {
		Optional<DrugServiceMetaData> drugServiceMetaDataOpt = drugServiceMetaDataRepository
				.findByDrugListId(drugListId);
		if (drugServiceMetaDataOpt.isPresent()) {
			Optional<List<DrugService>> drugListDetailsOpt = drugServiceRepository
					.findByDrugListIdAndOtherCodesValue(drugListId, sfdaRequestModel.getSfdaCode());
			if (!drugListDetailsOpt.isPresent() || drugListDetailsOpt.get().isEmpty()) {
				DrugService drugService = new DrugService();
				drugService.setOtherCodesValue(sfdaRequestModel.getSfdaCode());
				drugService.setCode(sfdaRequestModel.getGtinCode());
				drugService.setDisplay(sfdaRequestModel.getTradeName());
				drugService.setIngredients(sfdaRequestModel.getScientificName());
				drugService.setScientificCode(sfdaRequestModel.getScientificCode());
				drugService.setDosageForm(sfdaRequestModel.getDosageForm());
				drugService.setRoaSuggested(sfdaRequestModel.getAdministrationRoute());
				drugService.setPackageSize(sfdaRequestModel.getPackageSize());
				drugService.setPackageType(sfdaRequestModel.getPackageType());
				drugService.setGranularUnit(sfdaRequestModel.getGranularUnit());
				drugService.setStrength(sfdaRequestModel.getStrength());
				drugService.setStrengthUnit(sfdaRequestModel.getStrengthUnit());
				drugService.setPrice(sfdaRequestModel.getPrice());
				drugService.setDrugListId(drugListId);
				drugService.setLastUpdatedDate(new Date());
				drugService.setOtherCodesType("SFDA");
				drugService.setCategory("PHARMACEUTICAL");
				drugService.setWaseelDrugId(getWaseelDrugId());
				DrugService drug = drugServiceRepository.save(drugService);
				auditLogService.addDataInAuditLog(drug.getWaseelDrugId(), EntitiesName.DRUG_SERVICE,
						AuditLogAction.INSERT, drug);
				return new DrugResponseModel(drug.getWaseelDrugId());
			}
			throw new AdminException("SFDA Code already exists.");
		}
		throw new AdminException("DrugListId is not found.");
	}

	@Transactional
	public void deleteDrugDetails(Long waseelDrugId, Long drugListId) throws AdminException {
		Optional<DrugServiceMetaData> drugServiceMetaDataOpt = drugServiceMetaDataRepository
				.findByDrugListId(drugListId);
		if (drugServiceMetaDataOpt.isPresent()) {
			Optional<DrugService> drugServiceOpt = drugServiceRepository.findByWaseelDrugId(waseelDrugId);
			if (drugServiceOpt.isPresent()) {
				DrugService drugService = drugServiceOpt.get();
				auditLogService.addDataInAuditLog(drugService.getWaseelDrugId(), EntitiesName.DRUG_SERVICE,
						AuditLogAction.DELETE, drugService);
				drugServiceRepository.deleteByWaseelDrugId(waseelDrugId);
				logger.info("Data Deleted successfully for SFDA Drug: [{}] , WaseelDrugId: [{}]",
						drugService.getOtherCodesValue(), waseelDrugId);
				return;
			}
			throw new AdminException(STR_WASEEL_DRUGID_NOT_FOUND);
		}
		throw new AdminException(STR_DRUGLISTID_NOT_FOUND);
	}

	@Transactional
	public void deleteSFDAListDetails(Long drugListId) throws AdminException {
		Optional<DrugServiceMetaData> drugServiceMetaDataOpt = drugServiceMetaDataRepository
				.findByDrugListId(drugListId);
		if (drugServiceMetaDataOpt.isPresent()) {
			DrugServiceMetaData drugServiceMetaData = drugServiceMetaDataOpt.get();
			auditLogService.addDataInAuditLog(drugServiceMetaData.getDrugListId(), EntitiesName.DRUG_SERVICE_METADATA,
					AuditLogAction.DELETE, drugServiceMetaData);
			List<DrugService> drugServices = drugServiceRepository.findByDrugListId(drugListId);
			if (drugServices != null) {
				drugServices.forEach(drugService -> auditLogService.addDataInAuditLog(drugService.getDrugListId(),
						EntitiesName.DRUG_SERVICE, AuditLogAction.DELETE, drugService));
			}
			drugServiceRepository.deleteAllByDrugListId(drugListId);
			drugServiceMetaDataRepository.deleteByDrugListId(drugListId);
			logger.info("SFDA List data deleted successfully for drugListId: [{}]", drugListId);
			return;
		}
		throw new AdminException(STR_DRUGLISTID_NOT_FOUND);
	}

	private Long getWaseelDrugId() {
		Long waseelDrugId = drugServiceRepository.findFirstWaseelDrugId();
		if (waseelDrugId != null) {
			return ++waseelDrugId;
		} else {
			Long sequenceNo = drugServiceRepository.findLatestWaseelDrugIdFromSequence();
			String sequenceStr = "1000" + sequenceNo;
			return Long.valueOf(sequenceStr);
		}
	}
}