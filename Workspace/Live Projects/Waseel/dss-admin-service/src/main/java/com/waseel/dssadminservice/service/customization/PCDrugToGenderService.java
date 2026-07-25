package com.waseel.dssadminservice.service.customization;

import java.io.IOException;
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
import com.waseel.dssadminservice.enums.DrugToGenderFileHeaders;
import com.waseel.dssadminservice.enums.DssAdminConstants;
import com.waseel.dssadminservice.enums.DssAdminMessages;
import com.waseel.dssadminservice.enums.EntitiesName;
import com.waseel.dssadminservice.enums.PCRule;
import com.waseel.dssadminservice.exceptions.AdminException;
import com.waseel.dssadminservice.mapper.PcDrugToGenderMapper;
import com.waseel.dssadminservice.model.CommonInvalidResponseModel;
import com.waseel.dssadminservice.model.customization.PayerConfigModel;
import com.waseel.dssadminservice.model.customization.ServiceCodeModel;
import com.waseel.dssadminservice.model.customization.pcdrugtogender.DrugToGenderCustomizationRequestModel;
import com.waseel.dssadminservice.model.customization.pcdrugtogender.DrugToGenderResponseModel;
import com.waseel.dssadminservice.model.customization.pcdrugtogender.PcDrugToGenderRequestModel;
import com.waseel.dssadminservice.model.excelupload.BulkUploadResponseModel;
import com.waseel.dssadminservice.model.excelupload.ErrorList;
import com.waseel.dssadminservice.persist.mdss.CustomizationBatch;
import com.waseel.dssadminservice.persist.mdss.DrugServiceMetaData;
import com.waseel.dssadminservice.persist.mdss.PCAgeGenderId;
import com.waseel.dssadminservice.persist.mdss.PCGender;
import com.waseel.dssadminservice.repository.mdss.CustomizationBatchRepository;
import com.waseel.dssadminservice.repository.mdss.DrugServiceMetaDataRepository;
import com.waseel.dssadminservice.repository.mdss.DrugServiceRepository;
import com.waseel.dssadminservice.repository.mdss.PCDrugToGenderRepository;
import com.waseel.dssadminservice.repository.mdss.PayerConfigRepository;
import com.waseel.dssadminservice.service.AuditLogService;
import com.waseel.dssadminservice.specification.PCDrugToGenderSpecification;
import com.waseel.dssadminservice.util.ExcelValidationUtils;
import com.waseel.dssadminservice.util.UserInfoUtil;

@Service
public class PCDrugToGenderService {

	private final Logger log = LoggerFactory.getLogger(PCDrugToGenderService.class);

	private static final String STR_NOT_FOUND = "] not found";
	private static final String STR_SERVICE_CODE = "ServiceCode";
	private static final String STR_PAYER_ID = "PayerId";
	private static final String STR_PAYER_CATEGORY = "payer";

	@Autowired
	PCDrugToGenderSpecification pcDrugToGenderSpecification;
	@Autowired
	private PCDrugToGenderRepository pcDrugToGenderRepository;
	@Autowired
	private AuditLogService auditLogService;
	@Autowired
	private PCDrugToGenderTechnicalvalidation drugToGenderTechnicalvalidation;
	@Autowired
	private CustomizationBatchRepository customizationBatchRepository;
	@Autowired
	private ExcelValidationUtils excelValidationUtils;
	@Autowired
	private PayerConfigRepository payerConfigRepository;
	@Autowired
	private DrugServiceMetaDataRepository drugServiceMetaDataRepository;
	@Autowired
	private DrugServiceRepository drugServiceRepository;
	@Autowired
	private MessageSource messageSource;

	public Page<DrugToGenderResponseModel> getPCDrugToGenderList(
			DrugToGenderCustomizationRequestModel drugToGenderCustomizationRequestModel) {
		String category = UserInfoUtil.getAccCategory(SecurityContextHolder.getContext().getAuthentication());
		if (category.equalsIgnoreCase(STR_PAYER_CATEGORY))
			drugToGenderCustomizationRequestModel
					.setPayerId(UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication()));
		return pcDrugToGenderSpecification.getPCDrugToGender(drugToGenderCustomizationRequestModel);
	}

	@Transactional
	public Map<String, String> addPcDrugToGenderConfiguration(PcDrugToGenderRequestModel pcDrugToGenderReqModel)
			throws AdminException {
		String payerId = getPayerId(pcDrugToGenderReqModel.getPayerId());
		PCGender pcGenderDetails = findRecordInDB(pcDrugToGenderReqModel.getServiceCode(), payerId,
				pcDrugToGenderReqModel.getModuleName());
		Map<String, String> dataMap = new HashMap<>();
		if (pcGenderDetails == null) {
			PCAgeGenderId pcGenderId = new PCAgeGenderId(pcDrugToGenderReqModel.getServiceCode(), payerId,
					pcDrugToGenderReqModel.getModuleName());
			PCGender pcGender = new PCGender(pcGenderId, pcDrugToGenderReqModel.getGender(),
					pcDrugToGenderReqModel.getServiceStatus(), pcDrugToGenderReqModel.getAdditionalRejectionReason(),
					pcDrugToGenderReqModel.getScientificCode());
			String id = setRuleIdAndId(pcGender);
			PCGender addedPcDrugToGender = pcDrugToGenderRepository.save(pcGender);
			auditLogService.addDataInCustomizationUploadAudit(AuditLogAction.INSERT, addedPcDrugToGender.getRuleId(),
					EntitiesName.PC_DRUG_TO_GENDER, addedPcDrugToGender);
			log.info("Data added successfully for PC Drug to Gender.");
			dataMap.put("id", id);
			return dataMap;
		}
		String msg = new AdminException(DssAdminMessages.DUPLICATE_CUSTOMIZATION_REQUEST.message()
				.replace(DssAdminConstants.PAYER_ID.value(), payerId)
				.replace(DssAdminConstants.SERVICE_CODE.value(), pcDrugToGenderReqModel.getServiceCode())
				.replace(DssAdminConstants.MODULE_NAME.value(), pcDrugToGenderReqModel.getModuleName())
				.replace(DssAdminConstants.CUSTOMIZATION_NAME.value(), "Gender")).getMessage();
		log.error(msg);
		throw new AdminException(msg);
	}

	private PCGender findRecordInDB(String serviceCode, String payerId, String moduleName) {
		return pcDrugToGenderRepository
				.findByIdServiceCodeAndIdPayerIdAndIdModuleNameIgnoreCase(serviceCode, payerId, moduleName)
				.orElse(null);
	}

	private String getPayerId(String requestedPayerId) {
		String category = UserInfoUtil.getAccCategory(SecurityContextHolder.getContext().getAuthentication());
		if (category != null && StringUtils.isNotBlank(category) && category.equalsIgnoreCase(STR_PAYER_CATEGORY)) {
			return UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		}
		return requestedPayerId;
	}

	private String setRuleIdAndId(PCGender drugToGender) {
		Long id = pcDrugToGenderRepository.findLatestId();
		Long newId = id != null ? ++id : 1L;
		drugToGender.setSeqId(newId);
		drugToGender.setRuleId(PCRule.PC_DRUG_TO_GENDER.value() + "_" + newId);
		return newId + "";
	}

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
	public void deletePCDrugToGender(Long id) {
		if (id < 1) {
			throw new IllegalArgumentException("Invalid Field");
		}
		Optional<PCGender> pcGenderOpt = getPcGenderDetailBasedOnCategory(id);
		if (pcGenderOpt.isPresent()) {
			PCGender pcGender = pcGenderOpt.get();
			pcDrugToGenderRepository.delete(pcGender);
			auditLogService.addDataInCustomizationUploadAudit(AuditLogAction.DELETE, id.toString(),
					EntitiesName.PC_DRUG_TO_GENDER, pcGender);
			log.info("Data deleted successfully for PC Drug to Gender.Id: {} ", pcGender.getId());
		} else {
			throw new IllegalArgumentException(HttpStatus.NOT_FOUND.name());
		}
	}

	public DrugToGenderResponseModel getPcDrugToGenderDetails(Long id) throws AdminException {
		Optional<PCGender> pcGenderOpt = getPcGenderDetailBasedOnCategory(id);
		if (pcGenderOpt.isPresent()) {
			return PcDrugToGenderMapper.INSTANCE.pcGenderToDrugToGenderResponseModel(pcGenderOpt.get());
		}
		throw new AdminException("Id is not found or exists.");
	}

	private Optional<PCGender> getPcGenderDetailBasedOnCategory(Long id) {
		String category = UserInfoUtil.getAccCategory(SecurityContextHolder.getContext().getAuthentication());
		if (category != null && StringUtils.isNotBlank(category) && category.equalsIgnoreCase(STR_PAYER_CATEGORY)) {
			return pcDrugToGenderRepository.findBySeqIdAndId_PayerId(id,
					UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication()));
		} else {
			return pcDrugToGenderRepository.findBySeqId(id);
		}
	}

	@Transactional
	public void updateDrugToGenderCustomization(Long id, PcDrugToGenderRequestModel drugToGenderRequestModel)
			throws AdminException {
		String message = DssAdminMessages.FAILED_TO_UPDATE_CUSTOMIZATION_REQUEST.message();
		String category = UserInfoUtil.getAccCategory(SecurityContextHolder.getContext().getAuthentication());
		if (category.equalsIgnoreCase(AccountCategory.PAYER.name()))
			drugToGenderRequestModel
					.setPayerId(UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication()));
		PCGender pcGender = drugToGenderTechnicalvalidation.validatePCDrugToGenderCustomizationRequest(id,
				drugToGenderRequestModel);
		Integer updateCount = pcDrugToGenderRepository.updateByPCGenderCustomizationRequest(
				drugToGenderRequestModel.getGender(), drugToGenderRequestModel.getServiceStatus(),
				drugToGenderRequestModel.getAdditionalRejectionReason(), drugToGenderRequestModel.getModuleName(),
				drugToGenderRequestModel.getPayerId(), Timestamp.from(Instant.now()), id);
		if (updateCount == 1) {
			String ruleId = pcGender.getRuleId();
			auditLogService.addDataInCustomizationUploadAudit(AuditLogAction.UPDATE, ruleId,
					EntitiesName.PC_DRUG_TO_GENDER, pcGender);
			message = DssAdminMessages.GENDER_CUSTOMIZATION_REQUEST_UPDATED.message()
					.replace(DssAdminConstants.RULE_ID.value(), ruleId);
			log.info("{}", message);
			return;
		}
		log.info("{}", message);
		throw new AdminException(message);
	}

	public BulkUploadResponseModel uploadDrugToGenderCustomizationsFile(MultipartFile drugToGenderCustomizationsFile,
			boolean isOverride) throws AdminException, IOException {
		String fileName = drugToGenderCustomizationsFile.getOriginalFilename();
		String category = UserInfoUtil.getAccCategory(SecurityContextHolder.getContext().getAuthentication());
		List<ErrorList> errors = new ArrayList<>();
		if (drugToGenderCustomizationsFile.isEmpty()) {
			throw new AdminException(DssAdminMessages.INVALID_FILE_EMPTY_MESSAGE.message());
		}
		excelValidationUtils.validateFileExtension(drugToGenderCustomizationsFile);
		excelValidationUtils.validateFileSize(drugToGenderCustomizationsFile, 5);
		Sheet sheet = excelValidationUtils.getSheet(drugToGenderCustomizationsFile);
		validateDrugToGenderCustomizationsFileHeaders(sheet, category);
		excelValidationUtils.validateEmptyRecordsExceptHeader(sheet);
		List<String> duplicateCustomizationMessages = new ArrayList<>();
		Map<Integer, List<Integer>> duplicateCustomizationsRowNumber = new HashMap<>();
		List<PcDrugToGenderRequestModel> drugToGenderCustomizationRequests = new ArrayList<>();
		Map<String, Integer> duplicateCustomizations = new HashMap<>();
		validateExcelDrugToGenderCustomizations(sheet, drugToGenderCustomizationRequests, errors,
				duplicateCustomizations, duplicateCustomizationsRowNumber, category);
		int duplicateRecordCount = addDuplicateRecordMessageInList(duplicateCustomizationsRowNumber,
				duplicateCustomizationMessages);
		log.info("Pc Drug to Gender file: {} validated", fileName);
		Long successfullyAddedRecords = 0L;
		if (errors.isEmpty() && duplicateCustomizationMessages.isEmpty()) {
			// Save or update data in PCGender table
			log.info("Started saving Pc Drug to Gender File: {} ",
					drugToGenderCustomizationsFile.getOriginalFilename());
			Long batch = getCustomizationBatch(drugToGenderCustomizationsFile.getOriginalFilename(),
					UserInfoUtil.getUsername(SecurityContextHolder.getContext().getAuthentication()));
			successfullyAddedRecords = saveOrUpdateDataIntoPCDrugToGenderTable(drugToGenderCustomizationRequests,
					isOverride, errors, batch);
			log.info("Saving completed for Pc Drug to Gender File: {} ", fileName);
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

	private Long saveOrUpdateDataIntoPCDrugToGenderTable(List<PcDrugToGenderRequestModel> pcDrugToGenderReqList,
			boolean isOverride, List<ErrorList> errorList, Long batch) {
		var count = new Long[] { 0L };
		pcDrugToGenderReqList.forEach(model -> {
			ErrorList errorListModel = new ErrorList();
			errorListModel.setRowNumber(Long.valueOf(model.getRowNumber()));
			Optional<PCGender> optionalPCGender = pcDrugToGenderRepository
					.findByServiceCodeAndModuleNameAndGenderAndPayerId(model.getServiceCode(), model.getModuleName(),
							model.getGender(), model.getPayerId());
			if (optionalPCGender.isPresent()) {
				PCGender pcGender = optionalPCGender.get();
				String errorMessage = "Customization rule is already present with details like "
						+ "DrugCode: {drug} with PayerId: {payerId}, Gender: {gender} "
						+ "Module: {module} & Status: {status}.";
				errorMessage = errorMessage.replace("{drug}", pcGender.getId().getServiceCode())
						.replace("{payerId}", pcGender.getId().getPayerId())
						.replace("{module}", pcGender.getId().getModuleName()).replace("{gender}", pcGender.getGender())
						.replace("{status}", pcGender.getServiceStatus());
				if (model.getServiceStatus().equals(pcGender.getServiceStatus())
						&& (model.getPayerId().equals(pcGender.getId().getPayerId())
								|| pcGender.getId().getPayerId().equals("101"))) {
					List<String> errorDescList = new ArrayList<>();
					errorDescList.add(errorMessage);
					errorListModel.setErrorDescriptions(errorDescList);
					errorListModel.setDuplicateRecord(true);
					errorList.add(errorListModel);
				} else if (!model.getServiceStatus().equals(pcGender.getServiceStatus())
						&& model.getPayerId().equals(pcGender.getId().getPayerId())) {
					if (isOverride) {
						pcGender.setServiceStatus(model.getServiceStatus());
						pcGender.setAdditionalRejectionReason(model.getAdditionalRejectionReason());
						pcGender.setLastUpdatedDateTime(Timestamp.from(Instant.now()));
						PCGender addedPcGender = pcDrugToGenderRepository.save(pcGender);
						auditLogService.addDataInCustomizationUploadAudit(AuditLogAction.UPDATE,
								addedPcGender.getRuleId(), EntitiesName.PC_DRUG_TO_GENDER, addedPcGender);
						count[0] = maintainSuccessfullyAddedRecordCount(count[0]);
					} else {
						List<String> errorDescList = new ArrayList<>();
						errorDescList.add(errorMessage
								+ " If you want override you can re-upload by checking is override check box.");
						errorListModel.setErrorDescriptions(errorDescList);
						errorList.add(errorListModel);
					}
				} else {
					count[0] = insertRecordIntoPCDrugToGender(model, batch, count[0]);
				}
				return;
			}
			count[0] = insertRecordIntoPCDrugToGender(model, batch, count[0]);
		});
		return count[0];
	}

	private Long insertRecordIntoPCDrugToGender(PcDrugToGenderRequestModel model, Long batch,
			Long successfullyAddedRecords) {
		PCGender pcDrugToGender = PcDrugToGenderMapper.INSTANCE.mapModelToEntity(model);
		setRuleIdAndId(pcDrugToGender);
		pcDrugToGender.setLastUpdatedDateTime(Timestamp.from(Instant.now()));
		pcDrugToGender.setBatch(batch);
		PCGender updatedPcDrugToGender = pcDrugToGenderRepository.save(pcDrugToGender);
		auditLogService.addDataInCustomizationUploadAudit(AuditLogAction.INSERT, updatedPcDrugToGender.getRuleId(),
				EntitiesName.PC_DRUG_TO_GENDER, updatedPcDrugToGender);
		return maintainSuccessfullyAddedRecordCount(successfullyAddedRecords);
	}

	private void validateDrugToGenderCustomizationsFileHeaders(Sheet sheet, String category) throws AdminException {
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

	private PcDrugToGenderRequestModel populateRequestModelFromRowData(Row row, int rowNumber, Row initialRow,
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
			moduleName = excelValidationUtils.getCellValue(row, 2);
			serviceStatus = excelValidationUtils.getCellValue(row, 3);
			additionalRejectionReason = excelValidationUtils.getCellValue(row, 4);
		} else {
			payerId = excelValidationUtils.getCellValue(row, 2);
			moduleName = excelValidationUtils.getCellValue(row, 3);
			serviceStatus = excelValidationUtils.getCellValue(row, 4);
			additionalRejectionReason = excelValidationUtils.getCellValue(row, 5);
		}
		return new PcDrugToGenderRequestModel(excelValidationUtils.getCellValue(row, 0),
				excelValidationUtils.getCellValueForDecimals(row, 1), payerId, serviceStatus, additionalRejectionReason,
				moduleName, rowNumber);
	}

	private boolean areValidHeadersBasedOnCategory(String category, int cellCount, Row headerRow) {
		if (category.equals(AccountCategory.PAYER.name())) {
			return areValidHeadersForPayer(headerRow, cellCount);
		}
		return areValidHeadersForAdmin(headerRow, cellCount);
	}

	private boolean areValidHeadersForPayer(Row headerRow, int cellCount) {
		return cellCount == 5
				&& headerRow.getCell(0).getStringCellValue().trim()
						.equals(DrugToGenderFileHeaders.SERVICE_CODE.header())
				&& headerRow.getCell(1).getStringCellValue().trim().equals(DrugToGenderFileHeaders.GENDER.header())
				&& headerRow.getCell(2).getStringCellValue().trim().equals(DrugToGenderFileHeaders.MODULE_NAME.header())
				&& headerRow.getCell(3).getStringCellValue().trim()
						.equals(DrugToGenderFileHeaders.SERVICE_STATUS.header())
				&& headerRow.getCell(4).getStringCellValue().trim()
						.equals(DrugToGenderFileHeaders.REJECTION_REASON.header());
	}

	private boolean areValidHeadersForAdmin(Row headerRow, int cellCount) {
		return cellCount == 6
				&& headerRow.getCell(0).getStringCellValue().trim()
						.equals(DrugToGenderFileHeaders.SERVICE_CODE.header())
				&& headerRow.getCell(1).getStringCellValue().trim().equals(DrugToGenderFileHeaders.GENDER.header())
				&& headerRow.getCell(2).getStringCellValue().trim().equals(DrugToGenderFileHeaders.PAYER_ID.header())
				&& headerRow.getCell(3).getStringCellValue().trim().equals(DrugToGenderFileHeaders.MODULE_NAME.header())
				&& headerRow.getCell(4).getStringCellValue().trim()
						.equals(DrugToGenderFileHeaders.SERVICE_STATUS.header())
				&& headerRow.getCell(5).getStringCellValue().trim()
						.equals(DrugToGenderFileHeaders.REJECTION_REASON.header());
	}

	private void validateExcelDrugToGenderCustomizations(Sheet sheet,
			List<PcDrugToGenderRequestModel> drugToGenderCustomizationRequests, List<ErrorList> errors,
			Map<String, Integer> duplicateCustomizations, Map<Integer, List<Integer>> duplicateCustomizationRowNumbers,
			String category) {
		List<PayerConfigModel> payerConfigList = null;
		if (category.equals(AccountCategory.WASEEL.name())) {
			payerConfigList = excelValidationUtils.matchDataFromDbToExcel(sheet, 2, (batchOfValues, resultList) -> {
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
				PcDrugToGenderRequestModel drugToGenderCustomizationRequest = populateRequestModelFromRowData(row,
						rowNumber, initialRow, category);
				if (null != drugToGenderCustomizationRequest) {
					findDuplicateRecordsWithRowNumbers(drugToGenderCustomizationRequest, duplicateCustomizations,
							rowNumber, duplicateCustomizationRowNumbers);
					validatePCDrugToGenderRequestModel(drugToGenderCustomizationRequest, rowNumber, errors,
							drugToGenderCustomizationRequests, payerConfigList, drugServiceList, category);
				}
			}
		}
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

	private void validatePayerId(List<PayerConfigModel> payerConfigList, String payerId, List<String> errorMessages) {
		if (!isPayerUser()) {
			if (hasNotNullValidationMessage(STR_PAYER_ID, errorMessages)) {
				return;
			}
			PayerConfigModel payerConfig = payerConfigList.stream()
					.filter(config -> config.getPayerId().equalsIgnoreCase(payerId) && config.getIsValid() == 1)
					.findAny().orElse(null);
			if (payerConfig == null || payerId.getBytes().length > 20) {
				errorMessages.add(STR_PAYER_ID + "[" + payerId + STR_NOT_FOUND);
			}
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

	private boolean hasNotNullValidationMessage(String modelFieldName, List<String> errorMessages) {
		String notNullValidationMsg = messageSource.getMessage("emptyDataValidation", null, Locale.getDefault());
		return errorMessages.contains(modelFieldName + " " + notNullValidationMsg);
	}

	private void findDuplicateRecordsWithRowNumbers(PcDrugToGenderRequestModel drugToGenderCustomizationRequest,
			Map<String, Integer> duplicateRecordMessages, int rowNumber,
			Map<Integer, List<Integer>> duplicateCustomizationsRowNumber) {
		String customizationRequest = (drugToGenderCustomizationRequest.getServiceCode() + ":"
				+ drugToGenderCustomizationRequest.getPayerId() + ":"
				+ drugToGenderCustomizationRequest.getModuleName()).toLowerCase();
		if (!duplicateRecordMessages.containsKey(customizationRequest)) {
			duplicateRecordMessages.put(customizationRequest, rowNumber);
		} else {
			Integer originalRowNumber = duplicateRecordMessages.get(customizationRequest);
			duplicateCustomizationsRowNumber.computeIfAbsent(originalRowNumber, k -> new ArrayList<>()).add(rowNumber);
		}
	}

	private void validatePCDrugToGenderRequestModel(PcDrugToGenderRequestModel pcDrugToGenderCustomizationRequest,
			int rowNumber, List<ErrorList> errorList, List<PcDrugToGenderRequestModel> drugToGenderRequests,
			List<PayerConfigModel> payerConfigList, List<ServiceCodeModel> drugServiceList, String category) {
		List<String> errorMessages = new ArrayList<>();
		validatePcDrugToGenderRequest(pcDrugToGenderCustomizationRequest, errorMessages);
		validateServiceCode(drugServiceList, pcDrugToGenderCustomizationRequest.getServiceCode(), errorMessages);
		if (category.equals(AccountCategory.WASEEL.name())) {
			validatePayerId(payerConfigList, pcDrugToGenderCustomizationRequest.getPayerId(), errorMessages);
		}
		if (!errorMessages.isEmpty()) {
			ErrorList errorModel = new ErrorList();
			errorModel.setErrorDescriptions(errorMessages);
			errorModel.setRowNumber(Long.valueOf(rowNumber));
			errorList.add(errorModel);
			return;
		}
		drugToGenderRequests.add(pcDrugToGenderCustomizationRequest);
	}

	private void validatePcDrugToGenderRequest(PcDrugToGenderRequestModel pcDrugToGenderCustomizationRequest,
			List<String> errorMessages) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<PcDrugToGenderRequestModel>> violations = validator
				.validate(pcDrugToGenderCustomizationRequest);
		for (ConstraintViolation<PcDrugToGenderRequestModel> c : violations) {
			errorMessages.add(c.getMessage());
		}
	}

	private void findPayerIdsFromDb(List<String> currentBatchOfPayerIds, List<PayerConfigModel> finalPayerIdsList) {
		List<PayerConfigModel> payerConfigList = payerConfigRepository.findByPayerIds(currentBatchOfPayerIds);
		finalPayerIdsList.addAll(payerConfigList);
	}

	private Long getDrugListId() {
		return drugServiceMetaDataRepository
				.findFirstByEffectiveDateLessThanEqualOrderByEffectiveDateDescUploadDateTimeDesc(new Date())
				.map(DrugServiceMetaData::getDrugListId).orElse(0L);
	}

	private void findServiceCodesFromDb(List<String> currentBatchOfServiceCodes,
			List<ServiceCodeModel> finalServiceCodesList) {
		Long drugListId = getDrugListId();
		List<ServiceCodeModel> drugServiceList = drugServiceRepository.findByServiceCodes(currentBatchOfServiceCodes,
				drugListId);
		finalServiceCodesList.addAll(drugServiceList);
	}

	private boolean isPayerUser() {
		String accCategory = UserInfoUtil.getAccCategory(SecurityContextHolder.getContext().getAuthentication());
		return accCategory != null && StringUtils.isNotBlank(accCategory)
				&& accCategory.equalsIgnoreCase(STR_PAYER_CATEGORY);
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
				PcDrugToGenderRequestModel drugToGenderCustomizationRequest = populateRequestModelFromRowData(row,
						rowNumber, initialRow, category);
				totalRows = null != drugToGenderCustomizationRequest ? ++totalRows : totalRows;
			}
		}
		return String.valueOf(totalRows);
	}
}