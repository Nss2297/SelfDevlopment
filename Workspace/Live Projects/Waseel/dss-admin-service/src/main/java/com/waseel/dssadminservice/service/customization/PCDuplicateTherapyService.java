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
import com.waseel.dssadminservice.enums.DssAdminConstants;
import com.waseel.dssadminservice.enums.DssAdminMessages;
import com.waseel.dssadminservice.enums.DuplicateTherapyFileHeaders;
import com.waseel.dssadminservice.enums.EntitiesName;
import com.waseel.dssadminservice.enums.PCRule;
import com.waseel.dssadminservice.enums.ServiceStatus;
import com.waseel.dssadminservice.exceptions.AdminException;
import com.waseel.dssadminservice.mapper.PcDuplicateTherapyMapper;
import com.waseel.dssadminservice.model.CommonInvalidResponseModel;
import com.waseel.dssadminservice.model.customization.PayerConfigModel;
import com.waseel.dssadminservice.model.customization.ServiceCodeModel;
import com.waseel.dssadminservice.model.customization.pcduplicatetherapy.DuplicateTherapyResponseModel;
import com.waseel.dssadminservice.model.customization.pcduplicatetherapy.DuplicateTherapySearchModel;
import com.waseel.dssadminservice.model.customization.pcduplicatetherapy.PcDuplicateTherapyRequestModel;
import com.waseel.dssadminservice.model.excelupload.BulkUploadResponseModel;
import com.waseel.dssadminservice.model.excelupload.ErrorList;
import com.waseel.dssadminservice.persist.mdss.CustomizationBatch;
import com.waseel.dssadminservice.persist.mdss.DrugServiceMetaData;
import com.waseel.dssadminservice.persist.mdss.PCDrugCommonId;
import com.waseel.dssadminservice.persist.mdss.PCDuplicateTherapy;
import com.waseel.dssadminservice.repository.mdss.CustomizationBatchRepository;
import com.waseel.dssadminservice.repository.mdss.DrugServiceMetaDataRepository;
import com.waseel.dssadminservice.repository.mdss.DrugServiceRepository;
import com.waseel.dssadminservice.repository.mdss.PCDuplicateTherapyRepository;
import com.waseel.dssadminservice.repository.mdss.PayerConfigRepository;
import com.waseel.dssadminservice.service.AuditLogService;
import com.waseel.dssadminservice.specification.PCDuplicateTherapySpecification;
import com.waseel.dssadminservice.util.ExcelValidationUtils;
import com.waseel.dssadminservice.util.UserInfoUtil;

@Service
public class PCDuplicateTherapyService {

    private static final Logger logger = LoggerFactory.getLogger(PCDuplicateTherapyService.class);

    @Autowired
    PCDuplicateTherapyRepository duplicateTherapyRepository;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private DrugServiceRepository drugServiceRepository;

    @Autowired
    private PCDuplicateTherapySpecification pcDuplicateTherapySpecification;

    @Autowired
    private PCDuplicateTherapyRepository pcDuplicateTherapyRepository;

    @Autowired
    private ExcelValidationUtils excelValidationUtils;

    @Autowired
    private DrugServiceMetaDataRepository drugServiceMetaDataRepository;

    @Autowired
    private PayerConfigRepository payerConfigRepository;

    @Autowired
    private MessageSource messageSource;
    
    @Autowired
	private CustomizationBatchRepository customizationBatchRepository;

    private static final String STR_PAYER_CATEGORY = "payer";
    private static final String STR_SERVICE_CODE = "ServiceCode";
    private static final String STR_INTERACTED_SERVICE_CODE = "InteractedServiceCode";
    private static final String STR_NOT_FOUND = "] not found";
    private static final String STR_PAYER_ID = "PayerId";
    private static final String STR_DUPLICATE_THERAPY = "Duplicate Therapy";

    public BulkUploadResponseModel uploadDuplicateTherapyCustomizationsFile(
            MultipartFile duplicateTherapyCustomizationsFile,
            boolean isOverride) throws AdminException, IOException {
        String fileName = duplicateTherapyCustomizationsFile.getOriginalFilename();
        String category = UserInfoUtil.getAccCategory(SecurityContextHolder.getContext().getAuthentication());
        List<ErrorList> errors = new ArrayList<>();
        if (duplicateTherapyCustomizationsFile.isEmpty()) {
            throw new AdminException(DssAdminMessages.INVALID_FILE_EMPTY_MESSAGE.message());
        }
        excelValidationUtils.validateFileExtension(duplicateTherapyCustomizationsFile);
        excelValidationUtils.validateFileSize(duplicateTherapyCustomizationsFile, 5);
        Sheet sheet = excelValidationUtils.getSheet(duplicateTherapyCustomizationsFile);
        validateDuplicateTherapyCustomizationsFileHeaders(sheet, category);
        excelValidationUtils.validateEmptyRecordsExceptHeader(sheet);
        List<String> duplicateCustomizationMessages = new ArrayList<>();
        Map<Integer, List<Integer>> duplicateCustomizationsRowNumber = new HashMap<>();
        List<PcDuplicateTherapyRequestModel> dplicateTherapyCustomizationRequests = new ArrayList<>();
        Map<String, Integer> duplicateCustomizations = new HashMap<>();
        Map<String, Integer> requestsWithDifferentServiceStatus = new HashMap<>();
        Map<Integer, List<Integer>> requestsWithDifferentStatusRowNumber = new HashMap<>();
        validateExcelDuplicateTherapyCustomizations(sheet, dplicateTherapyCustomizationRequests, errors,
                duplicateCustomizations,
                duplicateCustomizationsRowNumber, category, requestsWithDifferentServiceStatus,
                requestsWithDifferentStatusRowNumber);
        int duplicateRecordCount = addDuplicateRecordMessageInList(duplicateCustomizationsRowNumber,
                duplicateCustomizationMessages, requestsWithDifferentStatusRowNumber,
                dplicateTherapyCustomizationRequests);
        logger.info("Pc Duplicate Therapy file: {} validated", fileName);
        for (PcDuplicateTherapyRequestModel requestModel : dplicateTherapyCustomizationRequests) {
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
            // Save or update data in PCDuplicateTherapy table
        	Long batch = getCustomizationBatch(fileName,
					UserInfoUtil.getUsername(SecurityContextHolder.getContext().getAuthentication()));
        	successfullyAddedRecords = saveDrugDuplicateThory(dplicateTherapyCustomizationRequests, isOverride, errors, batch);
            logger.info("Saving completed for Pc Duplicate Therapy File: {} ", fileName);
        }
        return prepareBulkUploadResponse(duplicateRecordCount, duplicateCustomizationMessages, errors,
                (successfullyAddedRecords > 1 || successfullyAddedRecords == 0
                        ? DssAdminMessages.CUSTOMIZATION_SUCCESS_MESSAGE.message().replace(
                                DssAdminConstants.RECORD_FIELD.value(), DssAdminConstants.RECORDS_FIELD.value())
                        : DssAdminMessages.CUSTOMIZATION_SUCCESS_MESSAGE.message())
                        .replace(DssAdminConstants.REQUESTED_RECORDS_FIELD.value(),
                                String.valueOf(successfullyAddedRecords))
                        .replace(DssAdminConstants.TOTAL_RECORDS_FIELD.value(),
                                String.valueOf(dplicateTherapyCustomizationRequests.size())));
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

    private void validateDuplicateTherapyCustomizationsFileHeaders(Sheet sheet, String category) throws AdminException {
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
                && headerRow.getCell(0).getStringCellValue().trim()
                        .equals(DuplicateTherapyFileHeaders.SERVICE_CODE.header())
                && headerRow.getCell(1).getStringCellValue().trim()
                        .equals(DuplicateTherapyFileHeaders.INTERACTED_SERVICE_CODE.header())
                && headerRow.getCell(2).getStringCellValue().trim()
                        .equals(DuplicateTherapyFileHeaders.MODULE_NAME.header())
                && headerRow.getCell(3).getStringCellValue().trim()
                        .equals(DuplicateTherapyFileHeaders.SERVICE_STATUS.header())
                && headerRow.getCell(4).getStringCellValue().trim()
                        .equals(DuplicateTherapyFileHeaders.ADDITIONAL_REJECTION_REASON.header());
    }

    private boolean areValidHeadersForAdmin(Row headerRow, int cellCount) {
        return cellCount == 6
                && headerRow.getCell(0).getStringCellValue().trim()
                        .equals(DuplicateTherapyFileHeaders.SERVICE_CODE.header())
                && headerRow.getCell(1).getStringCellValue().trim()
                        .equals(DuplicateTherapyFileHeaders.INTERACTED_SERVICE_CODE.header())
                && headerRow.getCell(2).getStringCellValue().trim()
                        .equals(DuplicateTherapyFileHeaders.PAYER_ID.header())
                && headerRow.getCell(3).getStringCellValue().trim()
                        .equals(DuplicateTherapyFileHeaders.MODULE_NAME.header())
                && headerRow.getCell(4).getStringCellValue().trim()
                        .equals(DuplicateTherapyFileHeaders.SERVICE_STATUS.header())
                && headerRow.getCell(5).getStringCellValue().trim()
                        .equals(DuplicateTherapyFileHeaders.ADDITIONAL_REJECTION_REASON.header());
    }

    private void validateExcelDuplicateTherapyCustomizations(Sheet sheet,
            List<PcDuplicateTherapyRequestModel> duplicateTherapyCustomizationRequests, List<ErrorList> errors,
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
                PcDuplicateTherapyRequestModel duplicateTherapyCustomizationRequest = populateRequestModelFromRowData(
                        row,
                        rowNumber, initialRow, category);
                if (null != duplicateTherapyCustomizationRequest) {
                    findDuplicateRecordsWithRowNumbers(duplicateTherapyCustomizationRequest, duplicateCustomizations,
                            rowNumber, duplicateCustomizationRowNumbers, requestsWithDifferentServiceStatus,
                            requestsWithDifferentStatusRowNumber);
                    validatePCDuplicateTherapyRequestModel(duplicateTherapyCustomizationRequest, rowNumber, errors,
                            duplicateTherapyCustomizationRequests, payerConfigList, drugServiceList, category);
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

    private PcDuplicateTherapyRequestModel populateRequestModelFromRowData(Row row, int rowNumber, Row initialRow,
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
        return new PcDuplicateTherapyRequestModel(excelValidationUtils.getCellValue(row, 0),
                excelValidationUtils.getCellValueForDecimals(row, 1), payerId, serviceStatus, additionalRejectionReason,
                moduleName, rowNumber);
    }

    private void findDuplicateRecordsWithRowNumbers(PcDuplicateTherapyRequestModel duplicateTherapyCustomizationRequest,
            Map<String, Integer> duplicateRecordMessages, int rowNumber,
            Map<Integer, List<Integer>> duplicateCustomizationsRowNumber,
            Map<String, Integer> duplicateCustomizationsWithDifferentServiceStatus,
            Map<Integer, List<Integer>> requestsWithDifferentStatusRowNumber) {
        String serviceCode = duplicateTherapyCustomizationRequest.getServiceCode();
        String payerId = duplicateTherapyCustomizationRequest.getPayerId();
        String moduleName = duplicateTherapyCustomizationRequest.getModuleName();
        String customizationRequest = (serviceCode + ":" + payerId + ":" + moduleName).toLowerCase();
        if (!duplicateRecordMessages.containsKey(customizationRequest)) {
            duplicateRecordMessages.put(customizationRequest, rowNumber);
        } else {
            Integer originalRowNumber = duplicateRecordMessages.get(customizationRequest);
            duplicateCustomizationsRowNumber.computeIfAbsent(originalRowNumber, k -> new ArrayList<>()).add(rowNumber);
        }
        duplicateRequestWithDifferentServiceStatus(duplicateCustomizationsWithDifferentServiceStatus, rowNumber,
                serviceCode, duplicateTherapyCustomizationRequest, requestsWithDifferentStatusRowNumber, payerId,
                moduleName);
    }

	private void duplicateRequestWithDifferentServiceStatus(
			Map<String, Integer> duplicateCustomizationsWithDifferentServiceStatus, int rowNumber, String serviceCode,
			PcDuplicateTherapyRequestModel duplicateTherapyCustomizationRequest,
			Map<Integer, List<Integer>> requestsWithDifferentStatusRowNumber, String payerId, String moduleName) {
		String customizationRequest = (serviceCode + ":"
				+ duplicateTherapyCustomizationRequest.getInteractedServiceCode() + ":"
				+ duplicateTherapyCustomizationRequest.getServiceStatus() + ":" + payerId + ":" + moduleName)
				.toLowerCase();
		duplicateTherapyCustomizationRequest.setServiceStatus(
				duplicateTherapyCustomizationRequest.getServiceStatus().equals(ServiceStatus.APPROVED.value())
						? ServiceStatus.REJECTED.value()
						: ServiceStatus.APPROVED.value());
		String requestWithDifferentServiceStatus = (duplicateTherapyCustomizationRequest.getInteractedServiceCode()
				+ ":" + serviceCode + ":" + duplicateTherapyCustomizationRequest.getServiceStatus() + ":" + payerId
				+ ":" + moduleName).toLowerCase();
		duplicateCustomizationsWithDifferentServiceStatus.put(requestWithDifferentServiceStatus, rowNumber);
		if (duplicateCustomizationsWithDifferentServiceStatus.containsKey(customizationRequest)) {
			Integer originalRowNumber = duplicateCustomizationsWithDifferentServiceStatus.get(customizationRequest);
			requestsWithDifferentStatusRowNumber.computeIfAbsent(originalRowNumber, k -> new ArrayList<>())
					.add(rowNumber);
		}
	}

    private void validatePCDuplicateTherapyRequestModel(
            PcDuplicateTherapyRequestModel pcDuplicateTherapyCustomizationRequest,
            int rowNumber, List<ErrorList> errorList, List<PcDuplicateTherapyRequestModel> duplicateTherapyRequests,
            List<PayerConfigModel> payerConfigList, List<ServiceCodeModel> drugServiceList, String category) {
        List<String> errorMessages = new ArrayList<>();
        validatePcDuplicateTherapyRequest(pcDuplicateTherapyCustomizationRequest, errorMessages);
        validateServiceCode(drugServiceList, pcDuplicateTherapyCustomizationRequest.getServiceCode(), errorMessages);
        validateInteractedServiceCode(drugServiceList,
                pcDuplicateTherapyCustomizationRequest.getInteractedServiceCode(),
                errorMessages);
        if (category.equals(AccountCategory.WASEEL.name())) {
            validatePayerId(payerConfigList, pcDuplicateTherapyCustomizationRequest.getPayerId(), errorMessages);
        }
        if (!errorMessages.isEmpty()) {
            ErrorList errorModel = new ErrorList();
            errorModel.setErrorDescriptions(errorMessages);
            errorModel.setRowNumber(Long.valueOf(rowNumber));
            errorList.add(errorModel);
            return;
        }
        duplicateTherapyRequests.add(pcDuplicateTherapyCustomizationRequest);
    }

    private void validatePcDuplicateTherapyRequest(
            PcDuplicateTherapyRequestModel pcDuplicateTherapyCustomizationRequest,
            List<String> errorMessages) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<PcDuplicateTherapyRequestModel>> violations = validator
                .validate(pcDuplicateTherapyCustomizationRequest);
        for (ConstraintViolation<PcDuplicateTherapyRequestModel> c : violations) {
            errorMessages.add(c.getMessage());
        }
    }

    private int addDuplicateRecordMessageInList(Map<Integer, List<Integer>> duplicateCustomizationsRowNumber,
            List<String> duplicateCustomizationMessages,
            Map<Integer, List<Integer>> requestsWithDifferentStatusRowNumber,
            List<PcDuplicateTherapyRequestModel> duplicateTherapyCustomizationRequest) {
        AtomicInteger duplicateRecordCount = new AtomicInteger(0);
        Map<String, List<Integer>> seenCombinations = new HashMap<>();
        for (PcDuplicateTherapyRequestModel request : duplicateTherapyCustomizationRequest) {
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

    public Page<DuplicateTherapyResponseModel> getPCDuplicateTherapyList(DuplicateTherapySearchModel searchCriteria) {
        String category = UserInfoUtil.getAccCategory(SecurityContextHolder.getContext().getAuthentication());
        if (category.equalsIgnoreCase(STR_PAYER_CATEGORY))
            searchCriteria.setPayerId(UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication()));
        return pcDuplicateTherapySpecification.getPCDuplicateTherapy(searchCriteria);
    }

    @Transactional
    public void updatePCDuplicateTherapyConfiguration(@Valid PcDuplicateTherapyRequestModel requestModel, Long id)
            throws AdminException {
        String payerId = getPayerId(requestModel.getPayerId());
        Optional<PCDuplicateTherapy> pcDuplicateTherapyOpt = getPcDuplicateTherapyDetailBasedOnCategory(id);
        PCDuplicateTherapy pcDuplicateTherapy = pcDuplicateTherapyOpt
                .orElseThrow(() -> new AdminException("Id is not found or exists."));
        PCDuplicateTherapy reversedPcDuplicateTherapyDetails = findRecordInDB(requestModel.getInteractedServiceCode(),
                requestModel.getServiceCode(), pcDuplicateTherapy.getId().getPayerId(),
                pcDuplicateTherapy.getId().getModuleName());
        validationForServiceCodeAndInteractedServiceCode(requestModel.getServiceCode(),
                requestModel.getInteractedServiceCode(), id);
        validateUniqueRecord(requestModel.getServiceCode(), requestModel.getInteractedServiceCode(), payerId,
                requestModel.getModuleName(), pcDuplicateTherapy.getSeqId());
        manageUpdation(pcDuplicateTherapy, reversedPcDuplicateTherapyDetails, requestModel, payerId);
    }

    @Transactional
    public Map<String, Long> addPCDuplicateTherapyConfiguration(@Valid PcDuplicateTherapyRequestModel requestModel)
            throws AdminException {
        String payerId = getPayerId(requestModel.getPayerId());
        validateDrugServices(requestModel);
        PCDuplicateTherapy pcDuplicateTherapyDetails = findRecordInDB(requestModel.getServiceCode(),
                requestModel.getInteractedServiceCode(), payerId, requestModel.getModuleName());
        Map<String, Long> dataMap = new HashMap<>();
        if (pcDuplicateTherapyDetails == null) {
            List<PCDuplicateTherapy> pcDuplicateTherapyList = new ArrayList<>();
            PCDuplicateTherapy pcDuplicateTherapy = getDuplicateTherapy(requestModel.getServiceCode(),
                    requestModel.getInteractedServiceCode(), requestModel, payerId);
            pcDuplicateTherapyList.add(pcDuplicateTherapy);
            manageInReverseAddition(requestModel, payerId, pcDuplicateTherapyList, pcDuplicateTherapy.getSeqId());
            Iterable<PCDuplicateTherapy> addedpcDuplicateTherapyIte = duplicateTherapyRepository
                    .saveAll(pcDuplicateTherapyList);
            manageAudits(addedpcDuplicateTherapyIte, dataMap);
            logger.info("Data added successfully for PC Duplicate Therapy.");
            return dataMap;
        }
        logger.info("Data already exist for PC Duplicate Therapy.");
        throw new AdminException(DssAdminMessages.DUPLICATE_DRUG_CUSTOMIZATION_REQUEST.message()
                .replace(DssAdminConstants.PAYER_ID.value(), payerId)
                .replace(DssAdminConstants.SERVICE_CODE.value(), requestModel.getServiceCode())
                .replace(DssAdminConstants.INTERACTED_SERVICE_CODE.value(), requestModel.getServiceCode())
                .replace(DssAdminConstants.MODULE_NAME.value(), requestModel.getModuleName())
                .replace(DssAdminConstants.CUSTOMIZATION_NAME.value(), STR_DUPLICATE_THERAPY));
    }

    private String getPayerId(String requestedPayerId) {
        String category = UserInfoUtil.getAccCategory(SecurityContextHolder.getContext().getAuthentication());
        if (category != null && StringUtils.isNotBlank(category)
                && category.equalsIgnoreCase(AccountCategory.PAYER.name())) {
            return UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
        }
        return requestedPayerId;
    }

    private void validateDrugServices(PcDuplicateTherapyRequestModel duplicateTherapyRequestModel)
            throws AdminException {
        if (duplicateTherapyRequestModel.getServiceCode()
                .equalsIgnoreCase(duplicateTherapyRequestModel.getInteractedServiceCode())) {
            throw new AdminException(DssAdminMessages.CANNOT_ADD_DRUG_CODE_MESSAGE.message()
                    .replace(DssAdminConstants.SERVICE_CODE.value(), duplicateTherapyRequestModel.getServiceCode())
                    .replace(DssAdminConstants.INTERACTED_SERVICE_CODE.value(),
                            duplicateTherapyRequestModel.getInteractedServiceCode()));
        }
    }

    private PCDuplicateTherapy findRecordInDB(String serviceCode, String interactedServiceCode, String payerId,
            String moduleName) {
        return duplicateTherapyRepository
                .findByIdServiceCodeAndIdInteractedServiceCodeAndIdPayerIdAndIdModuleNameIgnoreCase(
                        serviceCode, interactedServiceCode, payerId, moduleName)
                .orElse(null);
    }

    private PCDuplicateTherapy getDuplicateTherapy(String serviceCode, String interactedServiceCode,
            PcDuplicateTherapyRequestModel requestModel, String payerId) {
        PCDrugCommonId pcDrugCommonId = new PCDrugCommonId(serviceCode, interactedServiceCode, payerId,
                requestModel.getModuleName());
        PCDuplicateTherapy pcDuplicateTherapy = new PCDuplicateTherapy(pcDrugCommonId, requestModel.getServiceStatus(),
                requestModel.getAdditionalRejectionReason());
        pcDuplicateTherapy.setLastUpdatedDateTime(new Timestamp(System.currentTimeMillis()));
        setRuleIdAndId(pcDuplicateTherapy);
        return pcDuplicateTherapy;
    }

    private String setRuleIdAndId(PCDuplicateTherapy duplicateTherapy) {
        Long id = duplicateTherapyRepository.findLatestId();
        Long newId = id != null ? ++id : 1L;
        duplicateTherapy.setSeqId(newId);
        duplicateTherapy.setRuleId(PCRule.PC_DUPLICATE_THERAPY.value() + "_" + newId);
        return newId.toString();
    }

    private void manageInReverseAddition(PcDuplicateTherapyRequestModel requestModel, String payerId,
            List<PCDuplicateTherapy> pcDuplicateTherapyList, Long seqId) {
        PCDuplicateTherapy reversedPcDuplicateTherapyDetails = findRecordInDB(requestModel.getInteractedServiceCode(),
                requestModel.getServiceCode(), payerId, requestModel.getModuleName());
        if (reversedPcDuplicateTherapyDetails == null) {
            PCDuplicateTherapy reversedPcDuplicateTherapy = getDuplicateTherapy(requestModel.getInteractedServiceCode(),
                    requestModel.getServiceCode(), requestModel, payerId);
            reversedPcDuplicateTherapy.setSeqId(++seqId);
            reversedPcDuplicateTherapy.setRuleId(PCRule.PC_DUPLICATE_THERAPY.value() + "_" + seqId);
            pcDuplicateTherapyList.add(reversedPcDuplicateTherapy);
        }
    }

    private void manageAudits(Iterable<PCDuplicateTherapy> addedpcDuplicateTherapyIte, Map<String, Long> dataMap) {
        AtomicInteger counter = new AtomicInteger(1);
        addedpcDuplicateTherapyIte.forEach(addedpcDuplicateTherapy -> {
            String key = "id_" + counter.getAndIncrement();
            auditLogService.addDataInCustomizationUploadAudit(AuditLogAction.INSERT,
                    addedpcDuplicateTherapy.getRuleId(),
                    EntitiesName.PC_DUPLICATE_THERAPY, addedpcDuplicateTherapy);
            dataMap.put(key, addedpcDuplicateTherapy.getSeqId());
        });
    }

    public DuplicateTherapyResponseModel getDuplicateTherapyDetails(Long id) throws AdminException {
        Optional<PCDuplicateTherapy> pcDuplicateTherapyOpt = getPcDuplicateTherapyDetailBasedOnCategory(id);
        if (pcDuplicateTherapyOpt.isPresent()) {
            return PcDuplicateTherapyMapper.INSTANCE.pcDuplicateTherapyResponseModel(pcDuplicateTherapyOpt.get());
        }
        throw new AdminException("Id doesn't exist.");
    }

    private Optional<PCDuplicateTherapy> getPcDuplicateTherapyDetailBasedOnCategory(Long id) {
        String category = UserInfoUtil.getAccCategory(SecurityContextHolder.getContext().getAuthentication());
        if (category != null && StringUtils.isNotBlank(category)
                && category.equalsIgnoreCase(AccountCategory.PAYER.name())) {
            return duplicateTherapyRepository.findBySeqIdAndId_PayerId(id,
                    UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication()));
        } else {
            return duplicateTherapyRepository.findBySeqId(id);
        }
    }

    private void validationForServiceCodeAndInteractedServiceCode(String serviceCode, String interactedServiceCode,
            Long id) throws AdminException {
        Optional<PCDuplicateTherapy> pcDuplicateTherapyOpt = duplicateTherapyRepository.findBySeqIdAndIdServiceCode(id,
                serviceCode);
        if (pcDuplicateTherapyOpt.isEmpty()) {
            throw new AdminException(DssAdminMessages.CANNOT_EDIT_DRUG_CODE_MESSAGE.message()
                    .replace(DssAdminConstants.SERVICE_CODE.value(), serviceCode)
                    .replace(DssAdminConstants.CUSTOMIZATION_NAME.value(), STR_DUPLICATE_THERAPY));
        }
        Optional<PCDuplicateTherapy> pcDuplicateTherapyOptForInteracted = duplicateTherapyRepository
                .findBySeqIdAndIdInteractedServiceCode(id,
                        interactedServiceCode);
        if (pcDuplicateTherapyOptForInteracted.isEmpty()) {
            throw new AdminException(DssAdminMessages.CANNOT_EDIT_INTERACTED_SERVICE_CODE_MESSAGE.message()
                    .replace(DssAdminConstants.INTERACTED_SERVICE_CODE.value(), interactedServiceCode)
                    .replace(DssAdminConstants.CUSTOMIZATION_NAME.value(), STR_DUPLICATE_THERAPY));
        }
    }

    private void validateUniqueRecord(String serviceCode, String interactedServiceCode, String payerId,
            String moduleName, Long pcDuplicateTherapySeqId) throws AdminException {
        PCDuplicateTherapy pcDuplicateTherapyInDBDetails = findRecordInDB(serviceCode, interactedServiceCode, payerId,
                moduleName);
        if (pcDuplicateTherapyInDBDetails != null
                && !pcDuplicateTherapyInDBDetails.getSeqId().equals(pcDuplicateTherapySeqId)) {
            throw new AdminException(DssAdminMessages.DUPLICATE_DRUG_CUSTOMIZATION_REQUEST.message()
                    .replace(DssAdminConstants.PAYER_ID.value(), payerId)
                    .replace(DssAdminConstants.SERVICE_CODE.value(), serviceCode)
                    .replace(DssAdminConstants.INTERACTED_SERVICE_CODE.value(), interactedServiceCode)
                    .replace(DssAdminConstants.MODULE_NAME.value(), moduleName)
                    .replace(DssAdminConstants.CUSTOMIZATION_NAME.value(), STR_DUPLICATE_THERAPY));
        }
    }

    private void manageUpdation(PCDuplicateTherapy pcDuplicateTherapy,
            PCDuplicateTherapy reversedPcDuplicateTherapyDetails,
            PcDuplicateTherapyRequestModel requestModel, String payerId) {
        List<Long> seqIds = new ArrayList<>();
        seqIds.add(pcDuplicateTherapy.getSeqId());
        if (reversedPcDuplicateTherapyDetails != null) {
            seqIds.add(reversedPcDuplicateTherapyDetails.getSeqId());
        }
        int updateStatus = duplicateTherapyRepository.updatePCDuplicateTherapyCustomizationRequestByIds(
                requestModel.getServiceStatus(),
                payerId, requestModel.getAdditionalRejectionReason(), requestModel.getModuleName(), seqIds,
                Timestamp.from(Instant.now()));
        if (updateStatus > 0) {
            logger.info("Data updated successfully for PC Duplicate Therapy with Ids: {} ", seqIds);
            if (seqIds.size() > 1)
                auditLogService.addDataInCustomizationUploadAudit(AuditLogAction.UPDATE,
                		pcDuplicateTherapy.getRuleId(), EntitiesName.PC_DUPLICATE_THERAPY,
                        reversedPcDuplicateTherapyDetails);
            auditLogService.addDataInCustomizationUploadAudit(AuditLogAction.UPDATE, pcDuplicateTherapy.getRuleId(),
                    EntitiesName.PC_DUPLICATE_THERAPY, pcDuplicateTherapy);
        }
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

    public void deletePCDrugToDrugCustomization(Long id) {
        if (id < 1) {
            throw new IllegalArgumentException("Invalid id.");
        }
        Optional<PCDuplicateTherapy> pcDuplicateOpt = getpcDuplicateTheoryBasedOnCategory(id);
        if (pcDuplicateOpt.isPresent()) {
            List<PCDuplicateTherapy> pcDuplicateTheoryList = new ArrayList<>();
            PCDuplicateTherapy pcDrug = pcDuplicateOpt.get();
            pcDuplicateTheoryList.add(pcDrug);
            manageInReverseDeletion(pcDrug, pcDuplicateTheoryList);
            pcDuplicateTherapyRepository.deleteAll(pcDuplicateTheoryList);

            pcDuplicateTheoryList.forEach(data -> logger
                    .info("Data deleted successfully for PC Duplicate Theory Customization.Id: {} ", data.getSeqId()));
            manageDeleteAudits(pcDuplicateTheoryList);
        } else {
            throw new IllegalArgumentException(HttpStatus.NOT_FOUND.name());
        }
    }
    
    private void manageDeleteAudits(List<PCDuplicateTherapy> pcDuplicateTheoryList) {
    	pcDuplicateTheoryList.forEach(deletedpcDrugToDrug -> {
			auditLogService.addDataInCustomizationUploadAudit(AuditLogAction.DELETE,
					deletedpcDrugToDrug.getSeqId().toString(), EntitiesName.PC_DUPLICATE_THERAPY, pcDuplicateTheoryList);
			logger.info("Data deleted successfully for PC Duplicate Theory Customization.Id: {} ", deletedpcDrugToDrug.getSeqId());
		});
	}

    private void manageInReverseDeletion(PCDuplicateTherapy pcDrug, List<PCDuplicateTherapy> pcDuplicateTheoryList) {
        PCDuplicateTherapy reversedPcDuplicateDetails = findRecordInDB(pcDrug.getId().getInteractedServiceCode(),
                pcDrug.getId().getServiceCode(), pcDrug.getId().getPayerId(), pcDrug.getId().getModuleName());
        if (reversedPcDuplicateDetails != null) {
            pcDuplicateTheoryList.add(reversedPcDuplicateDetails);
        }
    }

    private Optional<PCDuplicateTherapy> getpcDuplicateTheoryBasedOnCategory(Long id) {
        String category = UserInfoUtil.getAccCategory(SecurityContextHolder.getContext().getAuthentication());
        if (category != null && StringUtils.isNotBlank(category)
                && category.equalsIgnoreCase(AccountCategory.PAYER.name())) {
            return pcDuplicateTherapyRepository.findBySeqIdAndId_PayerId(id,
                    UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication()));
        } else {
            return pcDuplicateTherapyRepository.findBySeqId(id);
        }
    }
    
	private Long saveDrugDuplicateThory(List<PcDuplicateTherapyRequestModel> duplicateTherapyRequestModels,
			boolean isOverride, List<ErrorList> errorList, Long batch) {
		var count = new Long[] { 0L };
		List<PcDuplicateTherapyRequestModel> pushedRecords = new ArrayList<>();
		duplicateTherapyRequestModels.stream().forEach(model -> {
			ErrorList errorListModel = new ErrorList();
			errorListModel.setRowNumber(Long.valueOf(model.getRowNumber()));
			if (!pushedRecords.isEmpty()) {
				boolean matchFound = pushedRecords.stream().anyMatch(pushedRecord -> pushedRecord.equals(model));
				if (matchFound) {
					count[0] = maintainSuccessfullyAddedRecordCount(count[0]);
					return;
				}
			}
			Optional<PCDuplicateTherapy> optionalDuplicateDrug = pcDuplicateTherapyRepository
					.findByIdServiceCodeAndIdInteractedServiceCodeAndIdPayerIdAndIdModuleNameIgnoreCase(
							model.getServiceCode(), model.getInteractedServiceCode(), model.getPayerId(),
							model.getModuleName());
			if (optionalDuplicateDrug.isPresent()) {
				PCDuplicateTherapy pcDrugToDrug = optionalDuplicateDrug.get();
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
						pcDrugToDrug = pcDuplicateTherapyRepository.save(pcDrugToDrug);
						auditLogService.addDataInCustomizationUploadAudit(AuditLogAction.UPDATE,
								pcDrugToDrug.getRuleId(), EntitiesName.PC_DUPLICATE_THERAPY, pcDrugToDrug);
						count[0] = maintainSuccessfullyAddedRecordCount(count[0]);
					} else {
						List<String> errorDescList = new ArrayList<>();
						errorDescList.add(errorMessage
								+ " If you want override you can re-upload by checking is override check box.");
						errorListModel.setErrorDescriptions(errorDescList);
						errorList.add(errorListModel);
					}
				} else {
					count[0] = insertRecordIntoPCDuplicateDrug(model, batch, count[0], pushedRecords);
				}
				return;
			}
			count[0] = insertRecordIntoPCDuplicateDrug(model, batch, count[0], pushedRecords);
		});
		return count[0];
	}

	private Long insertRecordIntoPCDuplicateDrug(PcDuplicateTherapyRequestModel model, Long batch,
			Long successfullyAddedRecords, List<PcDuplicateTherapyRequestModel> pushedRecords) {
		PCDuplicateTherapy duplicateTherapy = PcDuplicateTherapyMapper.INSTANCE.mapModelToEntity(model);
		String newId = setRuleIdAndId(duplicateTherapy);
		duplicateTherapy.setLastUpdatedDateTime(Timestamp.from(Instant.now()));
		duplicateTherapy.setBatch(batch);
		duplicateTherapy = pcDuplicateTherapyRepository.save(duplicateTherapy);
		auditLogService.addDataInCustomizationUploadAudit(AuditLogAction.INSERT, duplicateTherapy.getRuleId(),
				EntitiesName.PC_DUPLICATE_THERAPY, duplicateTherapy);
		String payerId = model.getPayerId();
		PCDuplicateTherapy reversedPcDrugDetails = findRecordInDB(model.getInteractedServiceCode(),
				model.getServiceCode(), payerId, model.getModuleName());
		if (reversedPcDrugDetails == null) {
			PCDuplicateTherapy reversedDuplicateDrug = PcDuplicateTherapyMapper.INSTANCE
					.mapModelToEntity(new PcDuplicateTherapyRequestModel(model.getInteractedServiceCode(),
							model.getServiceCode(), payerId, model.getModuleName(), model.getServiceStatus(),
							model.getAdditionalRejectionReason()));
			reversedDuplicateDrug.setSeqId(Long.parseLong(newId) + 1);
			reversedDuplicateDrug.setRuleId(PCRule.PC_DUPLICATE_THERAPY.value() + "_" + reversedDuplicateDrug.getSeqId());
			reversedDuplicateDrug.setLastUpdatedDateTime(Timestamp.from(Instant.now()));
			reversedDuplicateDrug.setBatch(batch);
			reversedDuplicateDrug = pcDuplicateTherapyRepository.save(reversedDuplicateDrug);
			auditLogService.addDataInCustomizationUploadAudit(AuditLogAction.INSERT, reversedDuplicateDrug.getRuleId(),
					EntitiesName.PC_DUPLICATE_THERAPY, reversedDuplicateDrug);
		}
		managePushedRecords(model, payerId, pushedRecords);
		return maintainSuccessfullyAddedRecordCount(successfullyAddedRecords);
	}

	private Long maintainSuccessfullyAddedRecordCount(Long successfullyAddedRecords) {
		return successfullyAddedRecords + 1;
	}

	private void managePushedRecords(PcDuplicateTherapyRequestModel model, String payerId,
			List<PcDuplicateTherapyRequestModel> pushedRecords) {
		pushedRecords.add(getReversedModel(model, payerId));
		pushedRecords.add(model);
	}

	private PcDuplicateTherapyRequestModel getReversedModel(PcDuplicateTherapyRequestModel model, String payerId) {
		return new PcDuplicateTherapyRequestModel(model.getInteractedServiceCode(), model.getServiceCode(), payerId,
				model.getModuleName(), model.getServiceStatus(), model.getAdditionalRejectionReason());
	}
	
}
