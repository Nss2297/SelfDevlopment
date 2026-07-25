package com.waseel.dssadminservice.service.customization;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.multipart.MultipartFile;

import com.waseel.dssadminservice.enums.AccountCategory;
import com.waseel.dssadminservice.enums.AuditLogAction;
import com.waseel.dssadminservice.enums.DrugToAgeFileHeaders;
import com.waseel.dssadminservice.enums.DssAdminConstants;
import com.waseel.dssadminservice.enums.DssAdminMessages;
import com.waseel.dssadminservice.enums.EntitiesName;
import com.waseel.dssadminservice.enums.PCRule;
import com.waseel.dssadminservice.exceptions.AdminException;
import com.waseel.dssadminservice.mapper.PcDrugToAgeMapper;
import com.waseel.dssadminservice.model.CommonInvalidResponseModel;
import com.waseel.dssadminservice.model.customization.PayerConfigModel;
import com.waseel.dssadminservice.model.customization.ServiceCodeModel;
import com.waseel.dssadminservice.model.customization.pcdrugtoage.DrugToAgeResponseModel;
import com.waseel.dssadminservice.model.customization.pcdrugtoage.DrugToAgeSearchModel;
import com.waseel.dssadminservice.model.customization.pcdrugtoage.PcDrugToAgeRequestModel;
import com.waseel.dssadminservice.model.excelupload.BulkUploadResponseModel;
import com.waseel.dssadminservice.model.excelupload.ErrorList;
import com.waseel.dssadminservice.persist.mdss.CustomizationBatch;
import com.waseel.dssadminservice.persist.mdss.DrugServiceMetaData;
import com.waseel.dssadminservice.persist.mdss.PCAge;
import com.waseel.dssadminservice.persist.mdss.PCAgeGenderId;
import com.waseel.dssadminservice.repository.mdss.CustomizationBatchRepository;
import com.waseel.dssadminservice.repository.mdss.DrugServiceMetaDataRepository;
import com.waseel.dssadminservice.repository.mdss.DrugServiceRepository;
import com.waseel.dssadminservice.repository.mdss.PCDrugToAgeRepository;
import com.waseel.dssadminservice.repository.mdss.PayerConfigRepository;
import com.waseel.dssadminservice.service.AuditLogService;
import com.waseel.dssadminservice.specification.PCDrugToAgeSpecification;
import com.waseel.dssadminservice.util.ExcelValidationUtils;
import com.waseel.dssadminservice.util.UserInfoUtil;

@Service
public class PCDrugToAgeService {

	private final Logger logger = LoggerFactory.getLogger(PCDrugToAgeService.class);
	private static final String STR_SERVICE_CODE = "ServiceCode";
	private static final String STR_NOT_FOUND = "] not found";
	private static final String STR_PAYER_ID = "PayerId";

	@Autowired
	PCDrugToAgeSpecification pcDrugToAgeSpecification;

	@Autowired
	private ExcelValidationUtils excelValidationUtils;

	@Autowired
	private PayerConfigRepository payerConfigRepository;

	@Autowired
	private DrugServiceRepository drugServiceRepository;

	@Autowired
	private DrugServiceMetaDataRepository drugServiceMetaDataRepository;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private AuditLogService auditLogService;

	@Autowired
	private CustomizationBatchRepository customizationBatchRepository;

	@Autowired
	private PCDrugToAgeRepository pcDrugToAgeRepository;

	public CommonInvalidResponseModel populateUnAuthorizedResponse(AccessDeniedException ex) {
		List<String> errors = new ArrayList<>();
		errors.add(ex.getMessage());
		return new CommonInvalidResponseModel(errors);
	}

	public CommonInvalidResponseModel populateFailedResponse(Exception exception) {
		List<String> errors = new ArrayList<>();
		errors.add(exception.getMessage());
		return new CommonInvalidResponseModel(errors);
	}

	public CommonInvalidResponseModel populateInvalidResponse(AdminException adminException) {
		List<String> errors = new ArrayList<>();
		errors.add(adminException.getMessage());
		return new CommonInvalidResponseModel(errors);
	}

	public CommonInvalidResponseModel populateInvalidResponseForConstraints(Exception exception) {
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
		return new CommonInvalidResponseModel(errors);
	}

	@Transactional
	public void deletePCDrugToAge(Long id) {
		if (id < 1) {
			throw new IllegalArgumentException("Invalid Field");
		}
		Optional<PCAge> pcAgeOpt = getPcAgeDetailBasedOnCategory(id);
		if (pcAgeOpt.isPresent()) {
			PCAge pcAge = pcAgeOpt.get();
			pcDrugToAgeRepository.delete(pcAge);
			auditLogService.addDataInCustomizationUploadAudit(AuditLogAction.DELETE, id.toString(),
					EntitiesName.PC_DRUG_TO_AGE, pcAge);
			logger.info("Data deleted successfully for PC Drug to Age.Id: {} ", pcAge.getId());
		} else {
			throw new IllegalArgumentException(HttpStatus.NOT_FOUND.name());
		}
	}

	private Optional<PCAge> getPcAgeDetailBasedOnCategory(Long id) {
		String category = UserInfoUtil.getAccCategory(SecurityContextHolder.getContext().getAuthentication());
		if (category != null && StringUtils.isNotBlank(category)
				&& category.equalsIgnoreCase(AccountCategory.PAYER.name())) {
			return pcDrugToAgeRepository.findBySeqIdAndId_PayerId(id,
					UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication()));
		} else {
			return pcDrugToAgeRepository.findBySeqId(id);
		}
	}

	public Page<DrugToAgeResponseModel> getPCDrugToAgeList(DrugToAgeSearchModel searchCriteria) {
		String category = UserInfoUtil.getAccCategory(SecurityContextHolder.getContext().getAuthentication());
		if (category.equalsIgnoreCase(AccountCategory.PAYER.name()))
			searchCriteria.setPayerId(UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication()));
		return pcDrugToAgeSpecification.getPCDrugToAge(searchCriteria);
	}

	public BulkUploadResponseModel uploadDrugToAgeCustomizationsFile(MultipartFile drugToAgeCustomizationsFile,
			boolean isOverride) throws AdminException, IOException {
		String fileName = drugToAgeCustomizationsFile.getOriginalFilename();
		String category = UserInfoUtil.getAccCategory(SecurityContextHolder.getContext().getAuthentication());
		List<ErrorList> errors = new ArrayList<>();
		if (drugToAgeCustomizationsFile.isEmpty()) {
			throw new AdminException(DssAdminMessages.INVALID_FILE_EMPTY_MESSAGE.message());
		}
		excelValidationUtils.validateFileExtension(drugToAgeCustomizationsFile);
		excelValidationUtils.validateFileSize(drugToAgeCustomizationsFile, 5);
		Sheet sheet = excelValidationUtils.getSheet(drugToAgeCustomizationsFile);
		validateDrugToAgeCustomizationsFileHeaders(sheet, category);
		excelValidationUtils.validateEmptyRecordsExceptHeader(sheet);
		List<String> duplicateCustomizationMessages = new ArrayList<>();
		Map<Integer, List<Integer>> duplicateCustomizationsRowNumber = new HashMap<>();
		List<PcDrugToAgeRequestModel> drugToAgeCustomizationRequests = new ArrayList<>();
		Map<String, Integer> duplicateCustomizations = new HashMap<>();
		validateExcelDrugToAgeCustomizations(sheet, drugToAgeCustomizationRequests, errors, duplicateCustomizations,
				duplicateCustomizationsRowNumber, category);
		int duplicateRecordCount = addDuplicateRecordMessageInList(duplicateCustomizationsRowNumber,
				duplicateCustomizationMessages);
		logger.info("Pc Drug to Age file: {} validated", fileName);
		Long successfullyAddedRecords = 0L;
		if (errors.isEmpty() && duplicateCustomizationMessages.isEmpty()) {
			// Save or update data in PcAge table
			Long batch = getCustomizationBatch(drugToAgeCustomizationsFile.getOriginalFilename(),
					UserInfoUtil.getUsername(SecurityContextHolder.getContext().getAuthentication()));
			successfullyAddedRecords = saveOrUpdateDataIntoPCDrugToAgeTable(drugToAgeCustomizationRequests, isOverride,
					errors, batch);
			logger.info("Saving completed for Pc Drug to Age File: {} ", fileName);
		}
		return prepareBulkUploadResponse(duplicateRecordCount, duplicateCustomizationMessages, errors,
				(successfullyAddedRecords > 1 || successfullyAddedRecords == 0
						? DssAdminMessages.CUSTOMIZATION_SUCCESS_MESSAGE.message().replace(
								DssAdminConstants.RECORD_FIELD.value(), DssAdminConstants.RECORDS_FIELD.value())
						: DssAdminMessages.CUSTOMIZATION_SUCCESS_MESSAGE.message())
								.replace(DssAdminConstants.REQUESTED_RECORDS_FIELD.value(),
										String.valueOf(successfullyAddedRecords))
								.replace(DssAdminConstants.TOTAL_RECORDS_FIELD.value(),
										getTotalNumberOfRecords(sheet, category)));
	}

	private void validateDrugToAgeCustomizationsFileHeaders(Sheet sheet, String category) throws AdminException {
		Row headerRow = sheet.getRow(0);
		if (excelValidationUtils.isRowEmpty(headerRow, sheet.getRow(0))) {
			throw new AdminException(DssAdminMessages.HEADERS_NOT_FOUND.message());
		}
		excelValidationUtils.checkHeaderCellHasNumericOrBlankCellType(headerRow);
		int cellCount = headerRow.getPhysicalNumberOfCells();
		if (StringUtils.isNotBlank(category) && !areValidHeadersBasedOnCategory(category, cellCount, headerRow)) {
			throw new AdminException(DssAdminMessages.INVALID_FILE_HEADERS.message());
		}
	}

	private boolean areValidHeadersBasedOnCategory(String category, int cellCount, Row headerRow) {
		if (category.equals(AccountCategory.PAYER.name())) {
			return areValidHeadersForPayer(headerRow, cellCount);
		}
		return areValidHeadersForAdmin(headerRow, cellCount);
	}

	private boolean areValidHeadersForPayer(Row headerRow, int cellCount) {
		return cellCount == 6
				&& headerRow.getCell(0).getStringCellValue().trim().equals(DrugToAgeFileHeaders.SERVICE_CODE.header())
				&& headerRow.getCell(1).getStringCellValue().trim()
						.equals(DrugToAgeFileHeaders.FROM_AGE_IN_DAYS.header())
				&& headerRow.getCell(2).getStringCellValue().trim().equals(DrugToAgeFileHeaders.TO_AGE_IN_DAYS.header())
				&& headerRow.getCell(3).getStringCellValue().trim().equals(DrugToAgeFileHeaders.MODULE_NAME.header())
				&& headerRow.getCell(4).getStringCellValue().trim().equals(DrugToAgeFileHeaders.SERVICE_STATUS.header())
				&& headerRow.getCell(5).getStringCellValue().trim()
						.equals(DrugToAgeFileHeaders.REJECTION_REASON.header());
	}

	private boolean areValidHeadersForAdmin(Row headerRow, int cellCount) {
		return cellCount == 7
				&& headerRow.getCell(0).getStringCellValue().trim().equals(DrugToAgeFileHeaders.SERVICE_CODE.header())
				&& headerRow.getCell(1).getStringCellValue().trim()
						.equals(DrugToAgeFileHeaders.FROM_AGE_IN_DAYS.header())
				&& headerRow.getCell(2).getStringCellValue().trim().equals(DrugToAgeFileHeaders.TO_AGE_IN_DAYS.header())
				&& headerRow.getCell(3).getStringCellValue().trim().equals(DrugToAgeFileHeaders.PAYER_ID.header())
				&& headerRow.getCell(4).getStringCellValue().trim().equals(DrugToAgeFileHeaders.MODULE_NAME.header())
				&& headerRow.getCell(5).getStringCellValue().trim().equals(DrugToAgeFileHeaders.SERVICE_STATUS.header())
				&& headerRow.getCell(6).getStringCellValue().trim()
						.equals(DrugToAgeFileHeaders.REJECTION_REASON.header());
	}

	private void validateExcelDrugToAgeCustomizations(Sheet sheet,
			List<PcDrugToAgeRequestModel> drugToAgeCustomizationRequests, List<ErrorList> errors,
			Map<String, Integer> duplicateCustomizations, Map<Integer, List<Integer>> duplicateCustomizationRowNumbers,
			String category) {
		List<PayerConfigModel> payerConfigList = null;
		if (category.equals(AccountCategory.WASEEL.name())) {
			payerConfigList = excelValidationUtils.matchDataFromDbToExcel(sheet, 3, (batchOfValues, resultList) -> {
				findPayerIdsFromDb(batchOfValues, resultList);
				return resultList;
			}, new ArrayList<>());
		}
		List<ServiceCodeModel> drugServiceList = excelValidationUtils.matchDataFromDbToExcel(sheet, 0,
				(batchOfValues, resultList) -> {
					findServiceCodesFromDb(batchOfValues, resultList);
					return resultList;
				}, new ArrayList<>());
		final Row initialRow = sheet.getRow(0);
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row != null) {
				int rowNumber = i + 1;
				PcDrugToAgeRequestModel drugToAgeCustomizationRequest = populateRequestModelFromRowData(row, rowNumber,
						initialRow, category);
				if (null != drugToAgeCustomizationRequest) {
					findDuplicateRecordsWithRowNumbers(drugToAgeCustomizationRequest, duplicateCustomizations,
							rowNumber, duplicateCustomizationRowNumbers);
					validatePCDrugToAgeRequestModel(drugToAgeCustomizationRequest, rowNumber, errors,
							drugToAgeCustomizationRequests, payerConfigList, drugServiceList, category);
				}
			}
		}
	}

	private void findPayerIdsFromDb(List<String> currentBatchOfPayerIds, List<PayerConfigModel> finalPayerIdsList) {
		List<PayerConfigModel> payerConfigList = payerConfigRepository.findByPayerIds(currentBatchOfPayerIds);
		finalPayerIdsList.addAll(payerConfigList);
	}

	private void findServiceCodesFromDb(List<String> currentBatchOfServiceCodes,
			List<ServiceCodeModel> finalServiceCodesList) {
		Long drugListId = getDrugListId();
		List<ServiceCodeModel> drugServiceList = drugServiceRepository.findByServiceCodes(currentBatchOfServiceCodes,
				drugListId);
		finalServiceCodesList.addAll(drugServiceList);
	}

	private Long getDrugListId() {
		return drugServiceMetaDataRepository
				.findFirstByEffectiveDateLessThanEqualOrderByEffectiveDateDescUploadDateTimeDesc(new Date())
				.map(DrugServiceMetaData::getDrugListId).orElse(0L);
	}

	private PcDrugToAgeRequestModel populateRequestModelFromRowData(Row row, int rowNumber, Row initialRow,
			String category) {
		if (excelValidationUtils.isRowEmpty(row, initialRow)) {
			return null;
		}
		String payerId = "";
		String moduleName = "";
		String serviceStatus = "";
		String additionalRejectionReason = "";
		if (category.equals(AccountCategory.PAYER.name())) {
			payerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
			moduleName = excelValidationUtils.getCellValue(row, 3);
			serviceStatus = excelValidationUtils.getCellValue(row, 4);
			additionalRejectionReason = excelValidationUtils.getCellValue(row, 5);
		} else {
			payerId = excelValidationUtils.getCellValue(row, 3);
			moduleName = excelValidationUtils.getCellValue(row, 4);
			serviceStatus = excelValidationUtils.getCellValue(row, 5);
			additionalRejectionReason = excelValidationUtils.getCellValue(row, 6);
		}
		return new PcDrugToAgeRequestModel(excelValidationUtils.getCellValue(row, 0),
				excelValidationUtils.getCellValueForDecimals(row, 1),
				excelValidationUtils.getCellValueForDecimals(row, 2), payerId, serviceStatus, additionalRejectionReason,
				moduleName, rowNumber);
	}

	private void findDuplicateRecordsWithRowNumbers(PcDrugToAgeRequestModel drugToAgeCustomizationRequest,
			Map<String, Integer> duplicateRecordMessages, int rowNumber,
			Map<Integer, List<Integer>> duplicateCustomizationsRowNumber) {
		String customizationRequest = (drugToAgeCustomizationRequest.getServiceCode() + ":"
				+ drugToAgeCustomizationRequest.getPayerId() + ":" + drugToAgeCustomizationRequest.getModuleName())
						.toLowerCase();
		if (!duplicateRecordMessages.containsKey(customizationRequest)) {
			duplicateRecordMessages.put(customizationRequest, rowNumber);
		} else {
			Integer originalRowNumber = duplicateRecordMessages.get(customizationRequest);
			duplicateCustomizationsRowNumber.computeIfAbsent(originalRowNumber, k -> new ArrayList<>()).add(rowNumber);
		}
	}

	private void validatePCDrugToAgeRequestModel(PcDrugToAgeRequestModel pcDrugToAgeCustomizationRequest, int rowNumber,
			List<ErrorList> errorList, List<PcDrugToAgeRequestModel> drugToAgeRequests,
			List<PayerConfigModel> payerConfigList, List<ServiceCodeModel> drugServiceList, String category) {
		List<String> errorMessages = new ArrayList<>();
		validatePcDrugToAgeRequest(pcDrugToAgeCustomizationRequest, errorMessages);
		validateServiceCode(drugServiceList, pcDrugToAgeCustomizationRequest.getServiceCode(), errorMessages);
		if (category.equals(AccountCategory.WASEEL.name())) {
			validatePayerId(payerConfigList, pcDrugToAgeCustomizationRequest.getPayerId(), errorMessages);
		}
		if (!errorMessages.isEmpty()) {
			ErrorList errorModel = new ErrorList();
			errorModel.setErrorDescriptions(errorMessages);
			errorModel.setRowNumber(Long.valueOf(rowNumber));
			errorList.add(errorModel);
			return;
		}
		drugToAgeRequests.add(pcDrugToAgeCustomizationRequest);
	}

	private void validatePcDrugToAgeRequest(PcDrugToAgeRequestModel pcDrugToAgeCustomizationRequest,
			List<String> errorMessages) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<PcDrugToAgeRequestModel>> violations = validator
				.validate(pcDrugToAgeCustomizationRequest);
		String fromAgeInDays = pcDrugToAgeCustomizationRequest.getFromAgeInDays();
		String toAgeInDays = pcDrugToAgeCustomizationRequest.getToAgeInDays();
		if (StringUtils.isNotBlank(fromAgeInDays) && StringUtils.isNotBlank(toAgeInDays)
				&& isANumber(fromAgeInDays, toAgeInDays)) {
			try {
				validateFromAgeInDaysAndToAgeInDays(new BigDecimal(fromAgeInDays), new BigDecimal(toAgeInDays),
						errorMessages);
			} catch (NumberFormatException e) {
				errorMessages.add(DssAdminMessages.NUMBER_FORMAT_EXCEPTION_FOR_AGE.message());
			}
		}
		for (ConstraintViolation<PcDrugToAgeRequestModel> c : violations) {
			errorMessages.add(c.getMessage());
		}
	}

	private void validateServiceCode(List<ServiceCodeModel> drugServiceList, String serviceCode,
			List<String> errorMessages) {
		if (hasNotNullValidationMessage(STR_SERVICE_CODE, errorMessages)) {
			return;
		}
		ServiceCodeModel matchedServiceCode = drugServiceList.stream()
				.filter(serviceInfo -> serviceInfo.getserviceCode().equalsIgnoreCase(serviceCode)
						&& serviceInfo.getIsValid() == 1)
				.findAny().orElse(null);
		if (matchedServiceCode == null || serviceCode.getBytes().length > 250) {
			errorMessages.add(STR_SERVICE_CODE + "[" + serviceCode + STR_NOT_FOUND);
		}
	}

	private void validatePayerId(List<PayerConfigModel> payerConfigList, String payerId, List<String> errorMessages) {
		if (hasNotNullValidationMessage(STR_PAYER_ID, errorMessages)) {
			return;
		}
		PayerConfigModel payerConfig = payerConfigList.stream()
				.filter(config -> config.getPayerId().equalsIgnoreCase(payerId) && config.getIsValid() == 1).findAny()
				.orElse(null);
		if (payerConfig == null || payerId.getBytes().length > 20) {
			errorMessages.add(STR_PAYER_ID + "[" + payerId + STR_NOT_FOUND);
		}
	}

	private boolean hasNotNullValidationMessage(String fieldName, List<String> errorMessages) {
		String notEmptyMessage = messageSource.getMessage("emptyDataValidation", null, Locale.getDefault());
		return errorMessages.contains(fieldName + " " + notEmptyMessage);
	}

	private int addDuplicateRecordMessageInList(Map<Integer, List<Integer>> duplicateRecordRowNumberMap,
			List<String> duplicateRecordMessages) {
		AtomicInteger duplicateRecordCount = new AtomicInteger();
		duplicateRecordCount.set(0);
		duplicateRecordRowNumberMap.entrySet().forEach(entry -> {
			duplicateRecordMessages.add("Duplicate record found for row number " + entry.getKey() + " at row number(s) "
					+ StringUtils.strip(entry.getValue().toString(), "[]"));
			duplicateRecordCount.set(duplicateRecordCount.get() + entry.getValue().size());
		});
		return duplicateRecordCount.get();
	}

	private BulkUploadResponseModel prepareBulkUploadResponse(int duplicateRecordCount,
			List<String> duplicateRecordMessages, List<ErrorList> errorList, String message) {
		BulkUploadResponseModel responseModel = new BulkUploadResponseModel();
		if (duplicateRecordCount > 0)
			responseModel.setDuplicateRecordCount(duplicateRecordCount);
		responseModel.setDuplicateRecords(duplicateRecordMessages);
		responseModel.setErrorList(errorList);
		responseModel.setMessage(message);
		return responseModel;
	}

	private Long saveOrUpdateDataIntoPCDrugToAgeTable(List<PcDrugToAgeRequestModel> pcDrugToAgeReqList,
			boolean isOverride, List<ErrorList> errorList, Long batch) {
		var count = new Long[] { 0L };
		pcDrugToAgeReqList.forEach(model -> {
			ErrorList errorListModel = new ErrorList();
			errorListModel.setRowNumber(Long.valueOf(model.getRowNumber()));
			Optional<PCAge> pcDrugToAgeOpt = pcDrugToAgeRepository.findByIdServiceCodeAndIdPayerIdAndIdModuleName(
					model.getServiceCode(), model.getPayerId(), model.getModuleName());
			if (pcDrugToAgeOpt.isPresent()) {
				PCAge pcDrugToAge = pcDrugToAgeOpt.get();
				String errorMessage = "Customization rule is already present with details like "
						+ "ServiceCode: {serviceCode} with PayerId: {payerId}, From Age(in days): {fromAgeInDays} & "
						+ "To Age(in days): {toAgeInDays} & " + "Module: {module} & ServiceStatus: {status}.";
				errorMessage = errorMessage.replace("{serviceCode}", pcDrugToAge.getId().getServiceCode())
						.replace("{payerId}", pcDrugToAge.getId().getPayerId())
						.replace("{fromAgeInDays}", pcDrugToAge.getFromAgeInDays().toString())
						.replace("{toAgeInDays}", pcDrugToAge.getToAgeInDays().toString())
						.replace("{module}", pcDrugToAge.getId().getModuleName())
						.replace("{status}", pcDrugToAge.getServiceStatus());
				if (model.getServiceStatus().equals(pcDrugToAge.getServiceStatus())
						&& (model.getPayerId().equals(pcDrugToAge.getId().getPayerId())
								|| pcDrugToAge.getId().getPayerId().equals("101"))) {
					List<String> errorDescList = new ArrayList<>();
					errorDescList.add(errorMessage);
					errorListModel.setErrorDescriptions(errorDescList);
					errorListModel.setDuplicateRecord(true);
					errorList.add(errorListModel);
				} else if (!model.getServiceStatus().equals(pcDrugToAge.getServiceStatus())
						&& model.getPayerId().equals(pcDrugToAge.getId().getPayerId())) {
					if (isOverride) {
						pcDrugToAge.setServiceStatus(model.getServiceStatus());
						pcDrugToAge.setBatch(batch);
						pcDrugToAge.setFromAgeInDays(Long.valueOf(model.getFromAgeInDays()));
						pcDrugToAge.setToAgeInDays(Long.valueOf(model.getToAgeInDays()));
						pcDrugToAge.setLastUpdatedDateTime(Timestamp.from(Instant.now()));
						pcDrugToAge.setAdditionalRejectionReason(model.getAdditionalRejectionReason());
						PCAge addedDrugToAge = pcDrugToAgeRepository.save(pcDrugToAge);
						auditLogService.addDataInCustomizationUploadAudit(AuditLogAction.UPDATE,
								addedDrugToAge.getRuleId(), EntitiesName.PC_DRUG_TO_AGE, addedDrugToAge);
						count[0] = maintainSuccessfullyAddedRecordCount(count[0]);
					} else {
						List<String> errorDescList = new ArrayList<>();
						errorDescList.add(errorMessage
								+ " If you want override you can re-upload by checking is override check box.");
						errorListModel.setErrorDescriptions(errorDescList);
						errorList.add(errorListModel);
					}
				} else {
					count[0] = insertRecordIntoPCDrugToAge(model, batch, count[0]);
				}
				return;
			}
			count[0] = insertRecordIntoPCDrugToAge(model, batch, count[0]);
		});
		return count[0];
	}

	private Long insertRecordIntoPCDrugToAge(PcDrugToAgeRequestModel model, Long batch, Long successfullyAddedRecords) {
		PCAge pcDrugToAge = PcDrugToAgeMapper.INSTANCE.mapModelToEntity(model);
		setRuleIdAndId(pcDrugToAge);
		pcDrugToAge.setBatch(batch);
		pcDrugToAge.setLastUpdatedDateTime(Timestamp.from(Instant.now()));
		PCAge updatedDrugToAge = pcDrugToAgeRepository.save(pcDrugToAge);
		auditLogService.addDataInCustomizationUploadAudit(AuditLogAction.INSERT, updatedDrugToAge.getRuleId(),
				EntitiesName.PC_DRUG_TO_AGE, updatedDrugToAge);
		return maintainSuccessfullyAddedRecordCount(successfullyAddedRecords);
	}

	private String setRuleIdAndId(PCAge drugToAge) {
		Long id = pcDrugToAgeRepository.findLatestId();
		Long newId = id != null ? ++id : 1L;
		drugToAge.setSeqId(newId);
		drugToAge.setRuleId(PCRule.PC_DRUG_TO_AGE.value() + "_" + newId);
		return newId + "";
	}

	private Long getCustomizationBatch(String originalFilename, String uploader) {
		Long id = customizationBatchRepository.findLatestId();
		Long batchId = id != null ? ++id : 1L;
		CustomizationBatch batch = new CustomizationBatch();
		batch.setBatchId(batchId);
		batch.setUploader(uploader);
		batch.setBatchReference(originalFilename);
		batch.setCreatedDate(Timestamp.from(Instant.now()));
		customizationBatchRepository.save(batch);
		return batchId;
	}

	private void validateFromAgeInDaysAndToAgeInDays(BigDecimal fromAgeInDays, BigDecimal toAgeInDays,
			List<String> errorMessages) {
		if (fromAgeInDays.compareTo(toAgeInDays) > 0) {
			errorMessages.add(DssAdminMessages.INVALID_FROM_AGE_IN_DAYS.message());
		}
	}

	private boolean isANumber(String fromAgeInDays, String toAgeInDays) {
		return Pattern.compile("[0-9.]").matcher(fromAgeInDays).find()
				&& Pattern.compile("[0-9.]").matcher(toAgeInDays).find();
	}

	@Transactional
	public void updatePCDrugToAgesConfiguration(@Valid PcDrugToAgeRequestModel requestModel, Long id)
			throws AdminException {
		String payerId = getPayerId(requestModel.getPayerId());
		Optional<PCAge> pcAgeOpt = getPcAgeDetailBasedOnCategory(id);
		PCAge pcAge = pcAgeOpt.orElseThrow(() -> new AdminException("Id is not found or exists."));
		validateRequestFields(requestModel, id, payerId, pcAge.getSeqId());
		int updateStatus = pcDrugToAgeRepository.updatePCAgeCustomizationRequestById(payerId,
				requestModel.getServiceStatus(), requestModel.getAdditionalRejectionReason(),
				requestModel.getModuleName(), id, Long.valueOf(requestModel.getFromAgeInDays()),
				Long.valueOf(requestModel.getToAgeInDays()), Timestamp.from(Instant.now()));
		if (updateStatus > 0) {
			logger.info("Data updated successfully for PC Drug to Age with Id: [{}] ", id);
			auditLogService.addDataInCustomizationUploadAudit(AuditLogAction.UPDATE, pcAge.getRuleId(),
					EntitiesName.PC_DRUG_TO_AGE, pcAge);
		}
	}

	private void validateRequestFields(PcDrugToAgeRequestModel requestModel, Long id, String payerId, Long validSeqId)
			throws AdminException {
		validationForServiceCode(requestModel.getServiceCode(), id);
		validateUniqueRecord(requestModel.getServiceCode(), payerId, requestModel.getModuleName(), validSeqId);
		if (Long.valueOf(requestModel.getFromAgeInDays()).compareTo(Long.valueOf(requestModel.getToAgeInDays())) > 0) {
			throw new AdminException(DssAdminMessages.INVALID_FROM_AGE_IN_DAYS.message());
		}
	}

	private void validationForServiceCode(String serviceCode, Long id) throws AdminException {
		Optional<PCAge> pcAgeOpt = pcDrugToAgeRepository.findBySeqIdAndIdServiceCode(id, serviceCode);
		if (pcAgeOpt.isEmpty()) {
			throw new AdminException(DssAdminMessages.CANNOT_EDIT_DRUG_CODE_MESSAGE.message()
					.replace(DssAdminConstants.SERVICE_CODE.value(), serviceCode)
					.replace(DssAdminConstants.CUSTOMIZATION_NAME.value(), "Age"));
		}
	}

	private String getPayerId(String requestedPayerId) {
		String category = UserInfoUtil.getAccCategory(SecurityContextHolder.getContext().getAuthentication());
		if (category != null && StringUtils.isNotBlank(category)
				&& category.equalsIgnoreCase(AccountCategory.PAYER.name())) {
			return UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		}
		return requestedPayerId;
	}

	private PCAge findRecordInDB(String serviceCode, String payerId, String moduleName) {
		return pcDrugToAgeRepository
				.findByIdServiceCodeAndIdPayerIdAndIdModuleNameIgnoreCase(serviceCode, payerId, moduleName)
				.orElse(null);
	}

	private void validateUniqueRecord(String serviceCode, String payerId, String moduleName, Long pcAgeSeqId)
			throws AdminException {
		PCAge pcAgeInDBDetails = findRecordInDB(serviceCode, payerId, moduleName);
		if (pcAgeInDBDetails != null && !pcAgeInDBDetails.getSeqId().equals(pcAgeSeqId)) {
			throw new AdminException(DssAdminMessages.DUPLICATE_CUSTOMIZATION_REQUEST.message()
					.replace(DssAdminConstants.PAYER_ID.value(), payerId)
					.replace(DssAdminConstants.SERVICE_CODE.value(), serviceCode)
					.replace(DssAdminConstants.MODULE_NAME.value(), moduleName)
					.replace(DssAdminConstants.CUSTOMIZATION_NAME.value(), "Age"));
		}
	}

	@Transactional
	public Map<String, String> addPCDrugToAgesConfiguration(@Valid PcDrugToAgeRequestModel requestModel)
			throws AdminException {
		String payerId = getPayerId(requestModel.getPayerId());
		if (Long.valueOf(requestModel.getFromAgeInDays()).compareTo(Long.valueOf(requestModel.getToAgeInDays())) > 0) {
			throw new AdminException(DssAdminMessages.INVALID_FROM_AGE_IN_DAYS.message());
		}
		PCAge pcAgeDetails = findRecordInDB(requestModel.getServiceCode(), payerId, requestModel.getModuleName());
		Map<String, String> dataMap = new HashMap<>();
		if (pcAgeDetails == null) {
			PCAgeGenderId pcAgeId = new PCAgeGenderId(requestModel.getServiceCode(), payerId,
					requestModel.getModuleName());
			PCAge pcAge = new PCAge(pcAgeId, Long.parseLong(requestModel.getFromAgeInDays()),
					Long.parseLong(requestModel.getToAgeInDays()), requestModel.getServiceStatus(),
					requestModel.getAdditionalRejectionReason(), requestModel.getScientificCode());
			String id = setRuleIdAndId(pcAge);
			PCAge addedPcDrugToAge = pcDrugToAgeRepository.save(pcAge);
			auditLogService.addDataInCustomizationUploadAudit(AuditLogAction.INSERT, addedPcDrugToAge.getRuleId(),
					EntitiesName.PC_DRUG_TO_AGE, addedPcDrugToAge);
			logger.info("Data added successfully for PC Drug to Age.");
			dataMap.put("id", id);
			return dataMap;
		}
		logger.info("Data already exist for PC Drug to Age.");
		throw new AdminException("Age customization rule already exists for Payer: [" + payerId + "], Service Code: ["
				+ requestModel.getServiceCode() + "], and Module: [" + requestModel.getModuleName() + "]");
	}

	public DrugToAgeResponseModel getDrugToAgeDetails(Long id) throws AdminException {
		Optional<PCAge> pcAgeOpt = getPcAgeDetailBasedOnCategory(id);
		if (pcAgeOpt.isPresent()) {
			return PcDrugToAgeMapper.INSTANCE.pcAgeToDrugToAgeResponseModel(pcAgeOpt.get());
		}
		throw new AdminException("Id doesn't exist.");
	}

	private Long maintainSuccessfullyAddedRecordCount(Long successfullyAddedRecords) {
		return successfullyAddedRecords + 1;
	}

	private String getTotalNumberOfRecords(Sheet sheet, String category) {
		int totalRows = 0;
		Row initialRow = sheet.getRow(1);
		for (Row row : sheet) {
			int rowNumber = row.getRowNum();
			if (rowNumber > 0) {
				PcDrugToAgeRequestModel drugToAgeCustomizationRequest = populateRequestModelFromRowData(row, rowNumber,
						initialRow, category);
				totalRows = null != drugToAgeCustomizationRequest ? ++totalRows : totalRows;
			}
		}
		return String.valueOf(totalRows);
	}
}
