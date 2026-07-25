package com.waseel.dssadminservice.service.customization;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import com.waseel.dssadminservice.enums.DrugToDrugFileHeaders;
import com.waseel.dssadminservice.enums.DssAdminConstants;
import com.waseel.dssadminservice.enums.DssAdminMessages;
import com.waseel.dssadminservice.enums.EntitiesName;
import com.waseel.dssadminservice.enums.PCRule;
import com.waseel.dssadminservice.enums.ServiceStatus;
import com.waseel.dssadminservice.exceptions.AdminException;
import com.waseel.dssadminservice.mapper.PcDrugToDrugMapper;
import com.waseel.dssadminservice.model.CommonInvalidResponseModel;
import com.waseel.dssadminservice.model.customization.PayerConfigModel;
import com.waseel.dssadminservice.model.customization.ServiceCodeModel;
import com.waseel.dssadminservice.model.customization.pcdrugtodrug.DrugToDrugResponseModel;
import com.waseel.dssadminservice.model.customization.pcdrugtodrug.DrugToDrugSearchModel;
import com.waseel.dssadminservice.model.customization.pcdrugtodrug.PcDrugToDrugRequestModel;
import com.waseel.dssadminservice.model.excelupload.BulkUploadResponseModel;
import com.waseel.dssadminservice.model.excelupload.ErrorList;
import com.waseel.dssadminservice.persist.mdss.CustomizationBatch;
import com.waseel.dssadminservice.persist.mdss.DrugServiceMetaData;
import com.waseel.dssadminservice.persist.mdss.PCDrugCommonId;
import com.waseel.dssadminservice.persist.mdss.PcDrugToDrug;
import com.waseel.dssadminservice.repository.mdss.CustomizationBatchRepository;
import com.waseel.dssadminservice.repository.mdss.DrugServiceMetaDataRepository;
import com.waseel.dssadminservice.repository.mdss.DrugServiceRepository;
import com.waseel.dssadminservice.repository.mdss.PCDrugToDrugRepository;
import com.waseel.dssadminservice.repository.mdss.PayerConfigRepository;
import com.waseel.dssadminservice.service.AuditLogService;
import com.waseel.dssadminservice.specification.PCDrugToDrugSpecification;
import com.waseel.dssadminservice.util.ExcelValidationUtils;
import com.waseel.dssadminservice.util.UserInfoUtil;

@Service
public class PCDrugToDrugService {

	private final Logger logger = LoggerFactory.getLogger(PCDrugToDrugService.class);
	private static final String STR_PAYER_CATEGORY = "payer";
	private static final String STR_SERVICE_CODE = "ServiceCode";
	private static final String STR_INTERACTED_SERVICE_CODE = "InteractedServiceCode";
	private static final String STR_NOT_FOUND = "] not found";
	private static final String STR_PAYER_ID = "PayerId";

	@Autowired
	private PCDrugToDrugRepository drugToDrugRepository;

	@Autowired
	private DrugServiceRepository drugServiceRepository;

	@Autowired
	private DrugServiceMetaDataRepository drugServiceMetaDataRepository;

	@Autowired
	private PayerConfigRepository payerConfigRepository;

	@Autowired
	private CustomizationBatchRepository customizationBatchRepository;

	@Autowired
	private PCDrugToDrugSpecification pcDrugToDrugSpecification;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private AuditLogService auditLogService;

	@Autowired
	private ExcelValidationUtils excelValidationUtils;

	public BulkUploadResponseModel uploadDrugToDrugCustomizationsFile(MultipartFile drugToDrugCustomizationsFile,
			boolean isOverride) throws AdminException, IOException {
		String fileName = drugToDrugCustomizationsFile.getOriginalFilename();
		String category = UserInfoUtil.getAccCategory(SecurityContextHolder.getContext().getAuthentication());
		List<ErrorList> errors = new ArrayList<>();
		if (drugToDrugCustomizationsFile.isEmpty()) {
			throw new AdminException(DssAdminMessages.INVALID_FILE_EMPTY_MESSAGE.message());
		}
		excelValidationUtils.validateFileExtension(drugToDrugCustomizationsFile);
		excelValidationUtils.validateFileSize(drugToDrugCustomizationsFile, 5);
		Sheet sheet = excelValidationUtils.getSheet(drugToDrugCustomizationsFile);
		validateDrugToDrugCustomizationsFileHeaders(sheet, category);
		excelValidationUtils.validateEmptyRecordsExceptHeader(sheet);
		List<String> duplicateCustomizationMessages = new ArrayList<>();
		Map<Integer, List<Integer>> duplicateCustomizationsRowNumber = new HashMap<>();
		List<PcDrugToDrugRequestModel> drugToDrugCustomizationRequests = new ArrayList<>();
		Map<String, Integer> duplicateCustomizations = new HashMap<>();
		Map<String, Integer> requestsWithDifferentServiceStatus = new HashMap<>();
		Map<Integer, List<Integer>> requestsWithDifferentStatusRowNumber = new HashMap<>();
		validateExcelDrugToDrugCustomizations(sheet, drugToDrugCustomizationRequests, errors, duplicateCustomizations,
				duplicateCustomizationsRowNumber, category, requestsWithDifferentServiceStatus,
				requestsWithDifferentStatusRowNumber);
		int duplicateRecordCount = addDuplicateRecordMessageInList(duplicateCustomizationsRowNumber,
				duplicateCustomizationMessages, requestsWithDifferentStatusRowNumber, drugToDrugCustomizationRequests);
		logger.info("Pc Drug to Drug file: {} validated", fileName);
		for (PcDrugToDrugRequestModel requestModel : drugToDrugCustomizationRequests) {
			try {
				validateDrugServices(requestModel);
			} catch (AdminException e) {
				ErrorList error = new ErrorList();
				error.setRowNumber(Long.valueOf(requestModel.getRowNumber()));
				error.setErrorDescriptions(Collections.singletonList(DssAdminMessages.CANNOT_ADD_DRUG_CODE_MESSAGE
						.message().replace("{serviceCode}", requestModel.getServiceCode())
						.replace("{interactedServiceCode}", requestModel.getInteractedServiceCode())));
				error.setDuplicateRecord(false);
				errors.add(error);
			}
			duplicateRecordCount++;
		}
		Long successfullyAddedRecords = 0L;
		if (errors.isEmpty() && duplicateCustomizationMessages.isEmpty()) {
			// Save or update data in PCDrugToDrug table
			Long batch = getCustomizationBatch(drugToDrugCustomizationsFile.getOriginalFilename(),
					UserInfoUtil.getUsername(SecurityContextHolder.getContext().getAuthentication()));
			successfullyAddedRecords = saveOrUpdateDataIntoPCDrugToDrugTable(drugToDrugCustomizationRequests,
					isOverride, errors, batch);
			logger.info("Saving completed for Pc Drug to Drug File: {} ", fileName);
		}
		return prepareBulkUploadResponse(duplicateRecordCount, duplicateCustomizationMessages, errors,
				(successfullyAddedRecords > 1 || successfullyAddedRecords == 0
						? DssAdminMessages.CUSTOMIZATION_SUCCESS_MESSAGE.message().replace(
								DssAdminConstants.RECORD_FIELD.value(), DssAdminConstants.RECORDS_FIELD.value())
						: DssAdminMessages.CUSTOMIZATION_SUCCESS_MESSAGE.message())
								.replace(DssAdminConstants.REQUESTED_RECORDS_FIELD.value(),
										String.valueOf(successfullyAddedRecords))
								.replace(DssAdminConstants.TOTAL_RECORDS_FIELD.value(),
										String.valueOf(drugToDrugCustomizationRequests.size())));
	}

	private Long saveOrUpdateDataIntoPCDrugToDrugTable(List<PcDrugToDrugRequestModel> pcDrugToDrugReqList,
			boolean isOverride, List<ErrorList> errorList, Long batch) {
		var count = new Long[] { 0L };
		List<PcDrugToDrugRequestModel> pushedRecords = new ArrayList<PcDrugToDrugRequestModel>();
		pcDrugToDrugReqList.forEach(model -> {
			ErrorList errorListModel = new ErrorList();
			errorListModel.setRowNumber(Long.valueOf(model.getRowNumber()));
			if (!pushedRecords.isEmpty()) {
				boolean matchFound = pushedRecords.stream().anyMatch(pushedRecord -> pushedRecord.equals(model));
				if (matchFound) {
					count[0] = maintainSuccessfullyAddedRecordCount(count[0]);
					return;
				}
			}
			Optional<PcDrugToDrug> optionalPCDrug = drugToDrugRepository
					.findByServiceCodeAndInteractedServiceCodeAndPayerIdAndModuleName(model.getServiceCode(),
							model.getInteractedServiceCode(), model.getPayerId(), model.getModuleName());
			if (optionalPCDrug.isPresent()) {
				PcDrugToDrug pcDrugToDrug = optionalPCDrug.get();
				String errorMessage = "Customization rule is already present with details like "
						+ "DrugCode: {drug} with PayerId: {payerId}, Interacted Service Code: {interactedServiceCode} "
						+ "Module: {module} & Status: {status}.";
				errorMessage = errorMessage.replace("{drug}", pcDrugToDrug.getId().getServiceCode())
						.replace("{payerId}", pcDrugToDrug.getId().getPayerId())
						.replace("{module}", pcDrugToDrug.getId().getModuleName())
						.replace("{interactedServiceCode}", pcDrugToDrug.getId().getInteractedServiceCode())
						.replace("{status}", pcDrugToDrug.getServiceStatus());
				if (model.getServiceStatus().equals(pcDrugToDrug.getServiceStatus())
						&& (model.getPayerId().equals(pcDrugToDrug.getId().getPayerId())
								|| pcDrugToDrug.getId().getPayerId().equals("101"))) {
					List<String> errorDescList = new ArrayList<>();
					errorDescList.add(errorMessage);
					errorListModel.setErrorDescriptions(errorDescList);
					errorListModel.setDuplicateRecord(true);
					errorList.add(errorListModel);
				} else if (!model.getServiceStatus().equals(pcDrugToDrug.getServiceStatus())
						&& model.getPayerId().equals(pcDrugToDrug.getId().getPayerId())) {
					if (isOverride) {
						pcDrugToDrug.setServiceStatus(model.getServiceStatus());
						pcDrugToDrug.setAdditionalRejectionReason(model.getAdditionalRejectionReason());
						pcDrugToDrug.setLastUpdatedDateTime(Timestamp.from(Instant.now()));
						PcDrugToDrug addedPcDrugToDrug = drugToDrugRepository.save(pcDrugToDrug);
						auditLogService.addDataInCustomizationUploadAudit(AuditLogAction.UPDATE,
								addedPcDrugToDrug.getRuleId(), EntitiesName.PC_DRUG_TO_DRUG, addedPcDrugToDrug);
						count[0] = maintainSuccessfullyAddedRecordCount(count[0]);
					} else {
						List<String> errorDescList = new ArrayList<>();
						errorDescList.add(errorMessage
								+ " If you want override you can re-upload by checking is override check box.");
						errorListModel.setErrorDescriptions(errorDescList);
						errorList.add(errorListModel);
					}
				} else {
					count[0] = insertRecordIntoPCDrugToDrug(model, batch, count[0], pushedRecords);
				}
				return;
			}
			count[0] = insertRecordIntoPCDrugToDrug(model, batch, count[0], pushedRecords);
		});
		return count[0];
	}

	private Long insertRecordIntoPCDrugToDrug(PcDrugToDrugRequestModel model, Long batch, Long successfullyAddedRecords,
			List<PcDrugToDrugRequestModel> pushedRecords) {
		PcDrugToDrug pcDrugToDrug = PcDrugToDrugMapper.INSTANCE.mapModelToEntity(model);
		String newId = setRuleIdAndId(pcDrugToDrug);
		pcDrugToDrug.setLastUpdatedDateTime(Timestamp.from(Instant.now()));
		pcDrugToDrug.setBatch(batch);
		PcDrugToDrug updatedPcDrugToDrug = drugToDrugRepository.save(pcDrugToDrug);
		auditLogService.addDataInCustomizationUploadAudit(AuditLogAction.INSERT, updatedPcDrugToDrug.getRuleId(),
				EntitiesName.PC_DRUG_TO_DRUG, updatedPcDrugToDrug);
		String payerId = model.getPayerId();
		PcDrugToDrug reversedPcDrugDetails = findRecordInDB(model.getInteractedServiceCode(), model.getServiceCode(),
				payerId, model.getModuleName());
		if (reversedPcDrugDetails == null) {
			PcDrugToDrug reversedPcDrugToDrug = PcDrugToDrugMapper.INSTANCE
					.mapModelToEntity(new PcDrugToDrugRequestModel(model.getInteractedServiceCode(),
							model.getServiceCode(), payerId, model.getModuleName(), model.getServiceStatus(),
							model.getAdditionalRejectionReason(), model.getUpdateDateAndTime()));
			reversedPcDrugToDrug.setSeqId(Long.parseLong(newId) + 1);
			reversedPcDrugToDrug.setRuleId(PCRule.PC_DRUG_TO_DRUG.value() + "_" + reversedPcDrugToDrug.getSeqId());
			reversedPcDrugToDrug.setLastUpdatedDateTime(Timestamp.from(Instant.now()));
			reversedPcDrugToDrug.setBatch(batch);
			PcDrugToDrug updatedReversePcDrugToDrug = drugToDrugRepository.save(reversedPcDrugToDrug);
			auditLogService.addDataInCustomizationUploadAudit(AuditLogAction.INSERT,
					updatedReversePcDrugToDrug.getRuleId(), EntitiesName.PC_DRUG_TO_DRUG, updatedReversePcDrugToDrug);
		}
		managePushedRecords(model, payerId, pushedRecords);
		return maintainSuccessfullyAddedRecordCount(successfullyAddedRecords);
	}

	private void managePushedRecords(PcDrugToDrugRequestModel model, String payerId,
			List<PcDrugToDrugRequestModel> pushedRecords) {
		pushedRecords.add(getReversedModel(model, payerId));
		pushedRecords.add(model);
	}

	private PcDrugToDrugRequestModel getReversedModel(PcDrugToDrugRequestModel model, String payerId) {
		return new PcDrugToDrugRequestModel(model.getInteractedServiceCode(), model.getServiceCode(), payerId,
				model.getModuleName(), model.getServiceStatus(), model.getAdditionalRejectionReason(),
				model.getUpdateDateAndTime());
	}

	private String setRuleIdAndId(PcDrugToDrug drugToDrug) {
		Long id = drugToDrugRepository.findLatestId();
		Long newId = id != null ? ++id : 1L;
		drugToDrug.setSeqId(newId);
		drugToDrug.setRuleId(PCRule.PC_DRUG_TO_DRUG.value() + "_" + newId);
		return newId.toString();
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

	public Page<DrugToDrugResponseModel> getPCDrugToDrugList(DrugToDrugSearchModel searchCriteria) {
		String category = UserInfoUtil.getAccCategory(SecurityContextHolder.getContext().getAuthentication());
		if (category.equalsIgnoreCase(STR_PAYER_CATEGORY))
			searchCriteria.setPayerId(UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication()));
		return pcDrugToDrugSpecification.getPCDrugToDrug(searchCriteria);
	}

	public DrugToDrugResponseModel getDrugToDrugDetails(Long id) throws AdminException {
		Optional<PcDrugToDrug> pcDrugOpt = getPcDrugDetailBasedOnCategory(id);
		if (pcDrugOpt.isPresent()) {
			return PcDrugToDrugMapper.INSTANCE.pcDrugToDrugResponseModel(pcDrugOpt.get());
		}
		throw new AdminException("Id doesn't exist.");

	}

	private Optional<PcDrugToDrug> getPcDrugDetailBasedOnCategory(Long id) {
		String category = UserInfoUtil.getAccCategory(SecurityContextHolder.getContext().getAuthentication());
		if (category != null && StringUtils.isNotBlank(category)
				&& category.equalsIgnoreCase(AccountCategory.PAYER.name())) {
			return drugToDrugRepository.findBySeqIdAndId_PayerId(id,
					UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication()));
		} else {
			return drugToDrugRepository.findBySeqId(id);
		}
	}

	private void validateDrugToDrugCustomizationsFileHeaders(Sheet sheet, String category) throws AdminException {
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
		return cellCount == 5
				&& headerRow.getCell(0).getStringCellValue().trim().equals(DrugToDrugFileHeaders.SERVICE_CODE.header())
				&& headerRow.getCell(1).getStringCellValue().trim()
						.equals(DrugToDrugFileHeaders.INTERACTED_SERVICE_CODE.header())
				&& headerRow.getCell(2).getStringCellValue().trim().equals(DrugToDrugFileHeaders.MODULE_NAME.header())
				&& headerRow.getCell(3).getStringCellValue().trim()
						.equals(DrugToDrugFileHeaders.SERVICE_STATUS.header())
				&& headerRow.getCell(4).getStringCellValue().trim()
						.equals(DrugToDrugFileHeaders.ADDITIONAL_REJECTION_REASON.header());
	}

	private boolean areValidHeadersForAdmin(Row headerRow, int cellCount) {
		return cellCount == 6
				&& headerRow.getCell(0).getStringCellValue().trim().equals(DrugToDrugFileHeaders.SERVICE_CODE.header())
				&& headerRow.getCell(1).getStringCellValue().trim()
						.equals(DrugToDrugFileHeaders.INTERACTED_SERVICE_CODE.header())
				&& headerRow.getCell(2).getStringCellValue().trim().equals(DrugToDrugFileHeaders.PAYER_ID.header())
				&& headerRow.getCell(3).getStringCellValue().trim().equals(DrugToDrugFileHeaders.MODULE_NAME.header())
				&& headerRow.getCell(4).getStringCellValue().trim()
						.equals(DrugToDrugFileHeaders.SERVICE_STATUS.header())
				&& headerRow.getCell(5).getStringCellValue().trim()
						.equals(DrugToDrugFileHeaders.ADDITIONAL_REJECTION_REASON.header());
	}

	private void validateExcelDrugToDrugCustomizations(Sheet sheet,
			List<PcDrugToDrugRequestModel> drugToDrugCustomizationRequests, List<ErrorList> errors,
			Map<String, Integer> duplicateCustomizations, Map<Integer, List<Integer>> duplicateCustomizationRowNumbers,
			String category, Map<String, Integer> requestsWithDifferentServiceStatus,
			Map<Integer, List<Integer>> requestsWithDifferentStatusRowNumber) {
		List<PayerConfigModel> payerConfigList = null;
		if (category.equals(AccountCategory.WASEEL.name())) {
			payerConfigList = excelValidationUtils.matchDataFromDbToExcel(sheet, 2, (batchOfValues, resultList) -> {
				findPayerIdsFromDb(batchOfValues, resultList);
				return resultList;
			}, new ArrayList<>());
		}
		List<ServiceCodeModel> drugServiceList = excelValidationUtils.matchDataFromDbToExcelForMultiCells(sheet,
				Arrays.asList(0, 1), (batchOfValues, resultList) -> {
					findServiceCodesFromDb(batchOfValues, resultList);
					return resultList;
				}, new ArrayList<>());
		final Row initialRow = sheet.getRow(0);
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row != null) {
				int rowNumber = i + 1;
				PcDrugToDrugRequestModel drugToDrugCustomizationRequest = populateRequestModelFromRowData(row,
						rowNumber, initialRow, category);
				if (null != drugToDrugCustomizationRequest) {
					findDuplicateRecordsWithRowNumbers(drugToDrugCustomizationRequest, duplicateCustomizations,
							rowNumber, duplicateCustomizationRowNumbers, requestsWithDifferentServiceStatus,
							requestsWithDifferentStatusRowNumber);
					validatePCDrugToDrugRequestModel(drugToDrugCustomizationRequest, rowNumber, errors,
							drugToDrugCustomizationRequests, payerConfigList, drugServiceList, category);
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

	private PcDrugToDrugRequestModel populateRequestModelFromRowData(Row row, int rowNumber, Row initialRow,
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
		return new PcDrugToDrugRequestModel(excelValidationUtils.getCellValue(row, 0),
				excelValidationUtils.getCellValueForDecimals(row, 1), payerId, serviceStatus, additionalRejectionReason,
				moduleName, rowNumber);
	}

	private void findDuplicateRecordsWithRowNumbers(PcDrugToDrugRequestModel drugToDrugCustomizationRequest,
			Map<String, Integer> duplicateRecordMessages, int rowNumber,
			Map<Integer, List<Integer>> duplicateCustomizationsRowNumber,
			Map<String, Integer> duplicateCustomizationsWithDifferentServiceStatus,
			Map<Integer, List<Integer>> requestsWithDifferentStatusRowNumber) {
		String serviceCode = drugToDrugCustomizationRequest.getServiceCode();
		String payerId = drugToDrugCustomizationRequest.getPayerId();
		String moduleName = drugToDrugCustomizationRequest.getModuleName();
		String customizationRequest = (serviceCode + ":" + payerId + ":" + moduleName).toLowerCase();
		if (!duplicateRecordMessages.containsKey(customizationRequest)) {
			duplicateRecordMessages.put(customizationRequest, rowNumber);
		} else {
			Integer originalRowNumber = duplicateRecordMessages.get(customizationRequest);
			duplicateCustomizationsRowNumber.computeIfAbsent(originalRowNumber, k -> new ArrayList<>()).add(rowNumber);
		}
		duplicateRequestWithDifferentServiceStatus(duplicateCustomizationsWithDifferentServiceStatus, rowNumber,
				serviceCode, drugToDrugCustomizationRequest.getInteractedServiceCode(),
				drugToDrugCustomizationRequest.getServiceStatus(), requestsWithDifferentStatusRowNumber, payerId,
				moduleName);
	}

	private void duplicateRequestWithDifferentServiceStatus(
			Map<String, Integer> duplicateCustomizationsWithDifferentServiceStatus, int rowNumber, String serviceCode,
			String interactedServiceCode, String serviceStatus,
			Map<Integer, List<Integer>> requestsWithDifferentStatusRowNumber, String payerId, String moduleName) {
		String customizationRequest = (serviceCode + ":" + interactedServiceCode + ":" + serviceStatus + ":" + payerId
				+ ":" + moduleName).toLowerCase();
		serviceStatus = serviceStatus.equals(ServiceStatus.APPROVED.value()) ? ServiceStatus.REJECTED.value()
				: ServiceStatus.APPROVED.value();
		String requestWithDifferentServiceStatus = (interactedServiceCode + ":" + serviceCode + ":" + serviceStatus
				+ ":" + payerId + ":" + moduleName).toLowerCase();
		duplicateCustomizationsWithDifferentServiceStatus.put(requestWithDifferentServiceStatus, rowNumber);
		if (duplicateCustomizationsWithDifferentServiceStatus.containsKey(customizationRequest)) {
			Integer originalRowNumber = duplicateCustomizationsWithDifferentServiceStatus.get(customizationRequest);
			requestsWithDifferentStatusRowNumber.computeIfAbsent(originalRowNumber, k -> new ArrayList<>())
					.add(rowNumber);
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

	private void validateInteractedServiceCode(List<ServiceCodeModel> drugServiceList, String interactedServiceCode,
			List<String> errorMessages) {
		if (hasNotNullValidationMessage(STR_INTERACTED_SERVICE_CODE, errorMessages)) {
			return;
		}
		ServiceCodeModel matchedServiceCode = drugServiceList.stream()
				.filter(serviceInfo -> serviceInfo.getserviceCode().equalsIgnoreCase(interactedServiceCode)
						&& serviceInfo.getIsValid() == 1)
				.findAny().orElse(null);
		if (matchedServiceCode == null || interactedServiceCode.getBytes().length > 250) {
			errorMessages.add(STR_INTERACTED_SERVICE_CODE + "[" + interactedServiceCode + STR_NOT_FOUND);
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

	private void validatePCDrugToDrugRequestModel(PcDrugToDrugRequestModel pcDrugToDrugCustomizationRequest,
			int rowNumber, List<ErrorList> errorList, List<PcDrugToDrugRequestModel> drugToDrugRequests,
			List<PayerConfigModel> payerConfigList, List<ServiceCodeModel> drugServiceList, String category) {
		List<String> errorMessages = new ArrayList<>();
		validatePcDrugToDrugRequest(pcDrugToDrugCustomizationRequest, errorMessages);
		validateServiceCode(drugServiceList, pcDrugToDrugCustomizationRequest.getServiceCode(), errorMessages);
		validateInteractedServiceCode(drugServiceList, pcDrugToDrugCustomizationRequest.getInteractedServiceCode(),
				errorMessages);
		if (category.equals(AccountCategory.WASEEL.name())) {
			validatePayerId(payerConfigList, pcDrugToDrugCustomizationRequest.getPayerId(), errorMessages);
		}
		if (!errorMessages.isEmpty()) {
			ErrorList errorModel = new ErrorList();
			errorModel.setErrorDescriptions(errorMessages);
			errorModel.setRowNumber(Long.valueOf(rowNumber));
			errorList.add(errorModel);
			return;
		}
		drugToDrugRequests.add(pcDrugToDrugCustomizationRequest);
	}

	private void validatePcDrugToDrugRequest(PcDrugToDrugRequestModel pcDrugToDrugCustomizationRequest,
			List<String> errorMessages) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<PcDrugToDrugRequestModel>> violations = validator
				.validate(pcDrugToDrugCustomizationRequest);
		for (ConstraintViolation<PcDrugToDrugRequestModel> c : violations) {
			errorMessages.add(c.getMessage());
		}
	}

	private int addDuplicateRecordMessageInList(Map<Integer, List<Integer>> duplicateCustomizationsRowNumber,
			List<String> duplicateCustomizationMessages,
			Map<Integer, List<Integer>> requestsWithDifferentStatusRowNumber,
			List<PcDrugToDrugRequestModel> drugToDrugCustomizationRequests) {
		AtomicInteger duplicateRecordCount = new AtomicInteger(0);
		Map<String, List<Integer>> seenCombinations = new HashMap<>();
		for (PcDrugToDrugRequestModel request : drugToDrugCustomizationRequests) {
			String key = request.getServiceCode() + "|" + request.getInteractedServiceCode();
			seenCombinations.computeIfAbsent(key, k -> new ArrayList<>()).add(request.getRowNumber());
		}
		seenCombinations.forEach((key, rowNumbers) -> {
			if (rowNumbers.size() > 1) {
				List<Integer> duplicatedRows = new ArrayList<>(rowNumbers);
				Integer originalRow = duplicatedRows.remove(0);
				duplicateCustomizationMessages.add(String.format(
						"Duplicate record found for ServiceCode and InteractedServiceCode combination '%s' at row number(s) %s",
						key.replace("|", " and "), duplicatedRows));
				duplicateRecordCount.addAndGet(duplicatedRows.size());
				duplicateCustomizationsRowNumber.put(originalRow, duplicatedRows);
			}
		});
		requestsWithDifferentStatusRowNumber.forEach((key, value) -> {
			duplicateCustomizationMessages.add("Same/reverse rule with different status found for row number " + key
					+ " at row number(s) " + StringUtils.strip(value.toString(), "[]"));
			duplicateRecordCount.addAndGet(value.size());
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

	public CommonInvalidResponseModel populateInvalidResponse(AdminException adminException) {
		List<String> errors = new ArrayList<>();
		errors.add(adminException.getMessage());
		return new CommonInvalidResponseModel(errors);
	}

	public CommonInvalidResponseModel populateFailedResponse(Exception exception) {
		List<String> errors = new ArrayList<>();
		errors.add(exception.getMessage());
		return new CommonInvalidResponseModel(errors);
	}

	public CommonInvalidResponseModel populateUnAuthorizedResponse(AccessDeniedException ex) {
		List<String> errors = new ArrayList<>();
		errors.add(ex.getMessage());
		return new CommonInvalidResponseModel(errors);
	}

	@Transactional
	public Map<String, Long> addPCDrugToDrugConfiguration(@Valid PcDrugToDrugRequestModel requestModel)
			throws AdminException {
		String payerId = getPayerId(requestModel.getPayerId());
		validateDrugServices(requestModel);
		PcDrugToDrug pcDrugDetails = findRecordInDB(requestModel.getServiceCode(),
				requestModel.getInteractedServiceCode(), payerId, requestModel.getModuleName());
		Map<String, Long> dataMap = new HashMap<>();
		if (pcDrugDetails == null) {
			List<PcDrugToDrug> pcDrugToDrugList = new ArrayList<PcDrugToDrug>();
			PcDrugToDrug pcDrugToDrug = getPcDrugToDrug(requestModel.getServiceCode(),
					requestModel.getInteractedServiceCode(), requestModel, payerId);
			pcDrugToDrugList.add(pcDrugToDrug);
			manageInReverseAddition(requestModel, payerId, pcDrugToDrugList, pcDrugToDrug.getSeqId());
			Iterable<PcDrugToDrug> addedpcDrugToDrugIte = drugToDrugRepository.saveAll(pcDrugToDrugList);
			manageAudits(addedpcDrugToDrugIte, dataMap);
			logger.info("Data added successfully for PC Drug to Drug.");
			return dataMap;
		}
		logger.info("Data already exist for PC Drug to drug.");
		throw new AdminException(DssAdminMessages.DUPLICATE_DRUG_CUSTOMIZATION_REQUEST.message()
				.replace(DssAdminConstants.PAYER_ID.value(), payerId)
				.replace(DssAdminConstants.SERVICE_CODE.value(), requestModel.getServiceCode())
				.replace(DssAdminConstants.INTERACTED_SERVICE_CODE.value(), requestModel.getServiceCode())
				.replace(DssAdminConstants.MODULE_NAME.value(), requestModel.getModuleName())
				.replace(DssAdminConstants.CUSTOMIZATION_NAME.value(), "Drug to Drug"));
	}

	private void validateDrugServices(PcDrugToDrugRequestModel drugToDrugCustomizationRequest) throws AdminException {
		if (drugToDrugCustomizationRequest.getServiceCode()
				.equalsIgnoreCase(drugToDrugCustomizationRequest.getInteractedServiceCode())) {
			throw new AdminException(DssAdminMessages.CANNOT_ADD_DRUG_CODE_MESSAGE.message()
					.replace(DssAdminConstants.SERVICE_CODE.value(), drugToDrugCustomizationRequest.getServiceCode())
					.replace(DssAdminConstants.INTERACTED_SERVICE_CODE.value(),
							drugToDrugCustomizationRequest.getInteractedServiceCode()));
		}
	}

	private void manageAudits(Iterable<PcDrugToDrug> addedpcDrugToDrugIte, Map<String, Long> dataMap) {
		AtomicInteger counter = new AtomicInteger(1);
		addedpcDrugToDrugIte.forEach(addedpcDrugToDrug -> {
			String key = "id_" + counter.getAndIncrement();
			auditLogService.addDataInCustomizationUploadAudit(AuditLogAction.INSERT, addedpcDrugToDrug.getRuleId(),
					EntitiesName.PC_DRUG_TO_DRUG, addedpcDrugToDrug);
			dataMap.put(key, addedpcDrugToDrug.getSeqId());
		});
	}

	private void manageInReverseAddition(PcDrugToDrugRequestModel requestModel, String payerId,
			List<PcDrugToDrug> pcDrugToDrugList, Long seqId) {
		PcDrugToDrug reversedPcDrugDetails = findRecordInDB(requestModel.getInteractedServiceCode(),
				requestModel.getServiceCode(), payerId, requestModel.getModuleName());
		if (reversedPcDrugDetails == null) {
			PcDrugToDrug reversedPcDrugToDrug = getPcDrugToDrug(requestModel.getInteractedServiceCode(),
					requestModel.getServiceCode(), requestModel, payerId);
			reversedPcDrugToDrug.setSeqId(++seqId);
			reversedPcDrugToDrug.setRuleId(PCRule.PC_DRUG_TO_DRUG.value() + "_" + seqId);
			pcDrugToDrugList.add(reversedPcDrugToDrug);
		}
	}

	@Transactional
	public void updatePCDrugToDrugConfiguration(@Valid PcDrugToDrugRequestModel requestModel, Long id)
			throws AdminException {
		String payerId = getPayerId(requestModel.getPayerId());
		Optional<PcDrugToDrug> pcDrugOpt = getPcDrugDetailBasedOnCategory(id);
		PcDrugToDrug pcDrugToDrug = pcDrugOpt.orElseThrow(() -> new AdminException("Id is not found or exists."));
		PcDrugToDrug reversedPcDrugDetails = findRecordInDB(requestModel.getInteractedServiceCode(),
				requestModel.getServiceCode(), pcDrugToDrug.getId().getPayerId(), pcDrugToDrug.getId().getModuleName());
		validationForServiceCodeAndInteractedServiceCode(requestModel.getServiceCode(),
				requestModel.getInteractedServiceCode(), id);
		validateUniqueRecord(requestModel.getServiceCode(), requestModel.getInteractedServiceCode(), payerId,
				requestModel.getModuleName(), pcDrugToDrug.getSeqId());
		manageUpdation(pcDrugToDrug, reversedPcDrugDetails, requestModel, id, payerId);
	}

	private void manageUpdation(PcDrugToDrug pcDrugToDrug, PcDrugToDrug reversedPcDrugDetails,
			PcDrugToDrugRequestModel requestModel, Long id, String payerId) {
		List<Long> seqIds = new ArrayList<Long>();
		seqIds.add(pcDrugToDrug.getSeqId());
		if (reversedPcDrugDetails != null) {
			seqIds.add(reversedPcDrugDetails.getSeqId());
		}
		int updateStatus = drugToDrugRepository.updatePCDrugCustomizationRequestByIds(requestModel.getServiceStatus(),
				payerId, requestModel.getAdditionalRejectionReason(), requestModel.getModuleName(), seqIds,
				Timestamp.from(Instant.now()));
		if (updateStatus > 0) {
			logger.info("Data updated successfully for PC Drug to Drug with Ids: {} ", seqIds);
			if (seqIds.size() > 1)
				auditLogService.addDataInCustomizationUploadAudit(AuditLogAction.UPDATE,
						reversedPcDrugDetails.getRuleId(), EntitiesName.PC_DRUG_TO_DRUG, reversedPcDrugDetails);
			auditLogService.addDataInCustomizationUploadAudit(AuditLogAction.UPDATE, pcDrugToDrug.getRuleId(),
					EntitiesName.PC_DRUG_TO_DRUG, pcDrugToDrug);
		}
	}

	private void validationForServiceCodeAndInteractedServiceCode(String serviceCode, String interactedServiceCode,
			Long id) throws AdminException {
		Optional<PcDrugToDrug> pcDrugOpt = drugToDrugRepository.findBySeqIdAndIdServiceCode(id, serviceCode);
		if (pcDrugOpt.isEmpty()) {
			throw new AdminException(DssAdminMessages.CANNOT_EDIT_DRUG_CODE_MESSAGE.message()
					.replace(DssAdminConstants.SERVICE_CODE.value(), serviceCode)
					.replace(DssAdminConstants.CUSTOMIZATION_NAME.value(), "Drug to drug"));
		}
		Optional<PcDrugToDrug> pcDrugOptForInteracted = drugToDrugRepository.findBySeqIdAndIdInteractedServiceCode(id,
				interactedServiceCode);
		if (pcDrugOptForInteracted.isEmpty()) {
			throw new AdminException(DssAdminMessages.CANNOT_EDIT_INTERACTED_SERVICE_CODE_MESSAGE.message()
					.replace(DssAdminConstants.INTERACTED_SERVICE_CODE.value(), interactedServiceCode)
					.replace(DssAdminConstants.CUSTOMIZATION_NAME.value(), "Drug to Drug"));
		}
	}

	private void validateUniqueRecord(String serviceCode, String interactedServiceCode, String payerId,
			String moduleName, Long pcDrugSeqId) throws AdminException {
		PcDrugToDrug pcDrugInDBDetails = findRecordInDB(serviceCode, interactedServiceCode, payerId, moduleName);
		if (pcDrugInDBDetails != null && !pcDrugInDBDetails.getSeqId().equals(pcDrugSeqId)) {
			throw new AdminException(DssAdminMessages.DUPLICATE_DRUG_CUSTOMIZATION_REQUEST.message()
					.replace(DssAdminConstants.PAYER_ID.value(), payerId)
					.replace(DssAdminConstants.SERVICE_CODE.value(), serviceCode)
					.replace(DssAdminConstants.INTERACTED_SERVICE_CODE.value(), interactedServiceCode)
					.replace(DssAdminConstants.MODULE_NAME.value(), moduleName)
					.replace(DssAdminConstants.CUSTOMIZATION_NAME.value(), "Drug to Drug"));
		}
	}

	private PcDrugToDrug getPcDrugToDrug(String serviceCode, String interactedServiceCode,
			PcDrugToDrugRequestModel requestModel, String payerId) {
		PCDrugCommonId pcDrugCommonId = new PCDrugCommonId(serviceCode, interactedServiceCode, payerId,
				requestModel.getModuleName());
		PcDrugToDrug pcDrugToDrug = new PcDrugToDrug(pcDrugCommonId, requestModel.getServiceStatus(),
				requestModel.getAdditionalRejectionReason());
		setRuleIdAndId(pcDrugToDrug);
		return pcDrugToDrug;
	}

	private String getPayerId(String requestedPayerId) {
		String category = UserInfoUtil.getAccCategory(SecurityContextHolder.getContext().getAuthentication());
		if (category != null && StringUtils.isNotBlank(category)
				&& category.equalsIgnoreCase(AccountCategory.PAYER.name())) {
			return UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		}
		return requestedPayerId;
	}

	private PcDrugToDrug findRecordInDB(String serviceCode, String interactedServiceCode, String payerId,
			String moduleName) {
		return drugToDrugRepository.findByIdServiceCodeAndIdInteractedServiceCodeAndIdPayerIdAndIdModuleNameIgnoreCase(
				serviceCode, interactedServiceCode, payerId, moduleName).orElse(null);
	}

	@Transactional
	public void deletePCDrugToDrugCustomization(Long id) {
		if (id < 1) {
			throw new IllegalArgumentException("Invalid id.");
		}
		Optional<PcDrugToDrug> pcDrugOpt = getPcDrugDetailBasedOnCategory(id);
		if (pcDrugOpt.isPresent()) {
			List<PcDrugToDrug> pcDrugToDrugList = new ArrayList<PcDrugToDrug>();
			PcDrugToDrug pcDrug = pcDrugOpt.get();
			pcDrugToDrugList.add(pcDrug);
			manageInReverseDeletion(pcDrug, pcDrugToDrugList);
			drugToDrugRepository.deleteAll(pcDrugToDrugList);
			manageDeleteAudits(pcDrugToDrugList);
		} else {
			throw new IllegalArgumentException(HttpStatus.NOT_FOUND.name());
		}
	}

	private void manageDeleteAudits(List<PcDrugToDrug> pcDrugToDrugList) {
		pcDrugToDrugList.forEach(deletedpcDrugToDrug -> {
			auditLogService.addDataInCustomizationUploadAudit(AuditLogAction.DELETE,
					deletedpcDrugToDrug.getSeqId().toString(), EntitiesName.PC_DRUG_TO_DRUG, pcDrugToDrugList);
			logger.info("Data deleted successfully for PC Drug to Drug.Id: {} ", deletedpcDrugToDrug.getSeqId());
		});
	}

	private void manageInReverseDeletion(PcDrugToDrug pcDrug, List<PcDrugToDrug> pcDrugToDrugList) {
		PcDrugToDrug reversedPcDrugDetails = findRecordInDB(pcDrug.getId().getInteractedServiceCode(),
				pcDrug.getId().getServiceCode(), pcDrug.getId().getPayerId(), pcDrug.getId().getModuleName());
		if (reversedPcDrugDetails != null) {
			pcDrugToDrugList.add(reversedPcDrugDetails);
		}
	}

	private Long maintainSuccessfullyAddedRecordCount(Long successfullyAddedRecords) {
		return successfullyAddedRecords + 1;
	}
}