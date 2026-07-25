package com.waseel.pbm.pbmadminservice.service;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import com.waseel.pbm.pbmadminservice.enums.AuditUpdatedType;
import com.waseel.pbm.pbmadminservice.enums.EntitiesName;
import com.waseel.pbm.pbmadminservice.enums.ModuleName;
import com.waseel.pbm.pbmadminservice.enums.PCRule;
import com.waseel.pbm.pbmadminservice.enums.PbmAdminConstants;
import com.waseel.pbm.pbmadminservice.enums.PbmAdminMessages;
import com.waseel.pbm.pbmadminservice.enums.RejectionCategory;
import com.waseel.pbm.pbmadminservice.enums.ServiceStatus;
import com.waseel.pbm.pbmadminservice.exceptions.AdminException;
import com.waseel.pbm.pbmadminservice.model.PCDrugToDiagnosisRequest;
import com.waseel.pbm.pbmadminservice.model.customization.DrugToDiagnosisApprovalCategoryModel;
import com.waseel.pbm.pbmadminservice.model.customization.DrugToDiagnosisModel;
import com.waseel.pbm.pbmadminservice.model.customization.IcdDiagnosisModel;
import com.waseel.pbm.pbmadminservice.model.customization.PCDrugToDiagnosisRequestModel;
import com.waseel.pbm.pbmadminservice.model.customization.PayerConfigModel;
import com.waseel.pbm.pbmadminservice.model.customization.ServiceCodeModel;
import com.waseel.pbm.pbmadminservice.persist.mdss.CustomizationBatch;
import com.waseel.pbm.pbmadminservice.persist.mdss.DrugServiceMetaData;
import com.waseel.pbm.pbmadminservice.persist.mdss.PCDrugToDiagnosis;
import com.waseel.pbm.pbmadminservice.repository.hira.ICDDiagnosisRepository;
import com.waseel.pbm.pbmadminservice.repository.mdss.CustomizationBatchRepository;
import com.waseel.pbm.pbmadminservice.repository.mdss.DrugServiceMetaDataRepository;
import com.waseel.pbm.pbmadminservice.repository.mdss.DrugServiceRepository;
import com.waseel.pbm.pbmadminservice.repository.mdss.DrugToDiagnosisApprovalCategoryRepository;
import com.waseel.pbm.pbmadminservice.repository.mdss.PCDrugToDiagnosisRepository;
import com.waseel.pbm.pbmadminservice.repository.mdss.PayerConfigRepository;
import com.waseel.pbm.pbmadminservice.specification.PCDrugToDiagnosisSpecification;
import com.waseel.pbm.pbmadminservice.util.UserInfoUtil;

@Service
public class PCDrugToDiagnosisService {

	private final Logger log = LoggerFactory.getLogger(PCDrugToDiagnosisService.class);

	@Autowired
	private PCDrugToDiagnosisRepository pcDrugToDiagnosisRepository;
	@Autowired
	private PCDrugToDiagnosisSpecification pcDrugToDiagnosisSpecification;
	@Autowired
	private CommonMessageService messageService;
	@Autowired
	private PayerConfigRepository payerConfigRepository;
	@Autowired
	private DrugToDiagnosisApprovalCategoryRepository drugToDiagnosisApprovalCategoryRepository;
	@Autowired
	private ICDDiagnosisRepository icdDiagnosisRepository;
	@Autowired
	private DrugServiceRepository drugServiceRepository;
	@Autowired
	private CustomizationBatchRepository customizationBatchRepository;
	@Autowired
	private AuditLogService auditLogService;
	@Autowired
	private MessageSource messageSource;
	@Autowired
	private DrugServiceMetaDataRepository drugServiceMetaDataRepository;

	private static final String DATA_NOT_FOUND = "Data not found";
	private static final String INVALID_EXTENSION_MESSAGE = "Invalid file format, only .xls or .xlsx are allowed.";
	private static final String INVALID_FILE_EMPTY_MESSAGE = "Please select a file to upload.";
	private static final String INVALID_FILE_SIZE_MESSAGE = "File size exceeds the maximum allowed size (5MB).";
	private static final String INVALID_HEADERS_MESSAGE = "Invalid format. Please reorder/rename the headers or check "
			+ "the values or refer the format of Sample File.";
	private static final String NO_RECORDS_MESSAGE = "File must have at least one record.";
	private static final String NO_HEADERS_MESSAGE = "Headers not found.";
	private static final int BATCH_SIZE = 999;
	private static final String STR_NOT_FOUND = "] not found";
	private static final String STR_PAYER = "payer";
	private static final String STR_ICD_CODE = "IcdCode";
	private static final String STR_SERVICE_CODE = "ServiceCode";
	private static final String STR_PAYER_ID = "PayerId";

	public Page<DrugToDiagnosisModel> getPCDrugToDiagnosisConfigurationDetails(int pageNumber, int recordSize,
			PCDrugToDiagnosisRequest request) {
		log.info("Page Number :- {} Record Size :- {} ICD Code :- {} Service Code :- {}", pageNumber, recordSize,
				request.getIcdCode(), request.getServiceCode());
		String category = UserInfoUtil.getAccCategory(SecurityContextHolder.getContext().getAuthentication());
		if (category.equalsIgnoreCase("payer"))
			request.setPayerId(UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication()));
		return pcDrugToDiagnosisSpecification.getPCDrugToDiagnosisConfigurationDetailsWithPagination(pageNumber,
				recordSize, request);
	}

	public DrugToDiagnosisModel getPCDrugToDiagnosisConfiguration(Long id) {
		Optional<PCDrugToDiagnosis> result = pcDrugToDiagnosisRepository.findByNotDeletedConfiguration(id);
		if (result.isPresent()) {
			return result
					.map(customization -> new DrugToDiagnosisModel(customization.getId(),
							customization.getServiceCode(), customization.getIcdCode(), customization.getPayerId(),
							customization.getCategoryOfApproval(), customization.getRejectionCategory(),
							customization.getServiceStatus(), customization.getModuleName(),
							customization.getAdditionalRejectionReason(), customization.getLastUpdatedDateTime()))
					.get();
		}
		throw new EntityNotFoundException("customization [" + id + "] was not found");
	}

	public Map<String, String> addPCDrugToDiagnosisConfiguration(PCDrugToDiagnosisRequestModel pcDrugToDiagnosis) {
		PCDrugToDiagnosis pcDetails = findRecordInDB(pcDrugToDiagnosis.getServiceCode(), pcDrugToDiagnosis.getIcdCode(),
				pcDrugToDiagnosis.getPayerId(), pcDrugToDiagnosis.getModuleName());
		Map<String, String> dataMap = new HashMap<>();
		if (pcDetails == null) {
			PCDrugToDiagnosis drugToDiagnosis = new PCDrugToDiagnosis(pcDrugToDiagnosis.getServiceCode(),
					pcDrugToDiagnosis.getIcdCode(), pcDrugToDiagnosis.getPayerId(), pcDrugToDiagnosis.getModuleName(),
					pcDrugToDiagnosis.getCategoryOfApproval(), pcDrugToDiagnosis.getServiceStatus(),
					pcDrugToDiagnosis.getAdditionalRejectionReason(), pcDrugToDiagnosis.getRejectionCategory(),
					UserInfoUtil.getUsername(SecurityContextHolder.getContext().getAuthentication()));
			String id = setRuleIdAndId(drugToDiagnosis);
			PCDrugToDiagnosis addedPcDrugToDiagnosis = pcDrugToDiagnosisRepository.save(drugToDiagnosis);
			auditLogService.addDataInCustomizationUploadAudit(AuditUpdatedType.INSERT,
					addedPcDrugToDiagnosis.getRuleId(), EntitiesName.PC_DRUG_TO_DIAGNOSIS, addedPcDrugToDiagnosis);
			log.info("Data added successfully for PC Drug to Diagnosis.");
			dataMap.put("id", id);
			return dataMap;
		}
		String msg = new AdminException(PbmAdminMessages.DUPLICATE_CUSTOMIZATION_REQUEST.message()
				.replace(PbmAdminConstants.PAYER_ID.value(), pcDrugToDiagnosis.getPayerId())
				.replace(PbmAdminConstants.ICD_CODE.value(), pcDrugToDiagnosis.getIcdCode())
				.replace(PbmAdminConstants.SERVICE_CODE.value(), pcDrugToDiagnosis.getServiceCode())
				.replace(PbmAdminConstants.REJECTION_CATEGORY.value(), pcDrugToDiagnosis.getRejectionCategory())
				.replace(PbmAdminConstants.MODULE_NAME.value(), pcDrugToDiagnosis.getModuleName())).getMessage();
		log.info(msg);
		throw new DuplicateKeyException(msg);
	}

	public void deletePCDrugToDiagnosisConfiguration(Long id) {
		if (id < 1) {
			log.error(messageService.getMessage("InvalidFields"));
			throw new IllegalArgumentException(messageService.getMessage("InvalidFields"));
		}
		Optional<PCDrugToDiagnosis> pcDetails = pcDrugToDiagnosisRepository.findByNotDeletedConfiguration(id);
		if (pcDetails.isPresent()) {
			PCDrugToDiagnosis pcDrugToDiagnosis = pcDetails.get();
			pcDrugToDiagnosisRepository.delete(pcDrugToDiagnosis);
			auditLogService.addDataInCustomizationUploadAudit(AuditUpdatedType.DELETE, pcDrugToDiagnosis.getRuleId(),
					EntitiesName.PC_DRUG_TO_DIAGNOSIS, pcDrugToDiagnosis);
			log.info("Data deleted successfully for PC Drug to Diagnosis.Id: {} ", pcDrugToDiagnosis.getId());
			return;
		}
		log.debug(DATA_NOT_FOUND);
		throw new IllegalArgumentException(HttpStatus.NOT_FOUND.name());
	}

	public void updatePCDrugToDiagnosisConfiguration(PCDrugToDiagnosisRequestModel pcDrugToDiagnosis, Long id)
			throws AdminException {
		validatePCDrugToDiagnosisUniqueRecord(pcDrugToDiagnosis,id);
		int updateStatus = pcDrugToDiagnosisRepository.updateDataById(id, pcDrugToDiagnosis.getPayerId(),
				pcDrugToDiagnosis.getModuleName(), pcDrugToDiagnosis.getCategoryOfApproval(),
				pcDrugToDiagnosis.getRejectionCategory(), pcDrugToDiagnosis.getServiceStatus(),
				pcDrugToDiagnosis.getAdditionalRejectionReason(), Timestamp.from(Instant.now()),
				UserInfoUtil.getUsername(SecurityContextHolder.getContext().getAuthentication()));
		if (updateStatus > 0) {
			Optional<PCDrugToDiagnosis> PCDrugToDiagnosisOpt = pcDrugToDiagnosisRepository.findById(id);
			if (PCDrugToDiagnosisOpt.isPresent()) {
				PCDrugToDiagnosis PCDrugToDiagnosis = PCDrugToDiagnosisOpt.get();
				auditLogService.addDataInCustomizationUploadAudit(AuditUpdatedType.UPDATE,
						PCDrugToDiagnosis.getRuleId(), EntitiesName.PC_DRUG_TO_DIAGNOSIS, PCDrugToDiagnosis);
			}
			log.info("Data updated successfully for PC Drug to Diagnosis Id: {} ", id);
			return;
		}
		log.debug(DATA_NOT_FOUND);
		throw new IllegalArgumentException(HttpStatus.NOT_FOUND.name());
	}

	private void validatePCDrugToDiagnosisUniqueRecord(PCDrugToDiagnosisRequestModel pcDrugToDiagnosis, Long pcAgeSeqId)
			throws AdminException {
		String serviceCode = pcDrugToDiagnosis.getServiceCode();
		String icdCode = pcDrugToDiagnosis.getIcdCode();
		String payerId = pcDrugToDiagnosis.getPayerId();
		String moduleName = pcDrugToDiagnosis.getModuleName();
		PCDrugToDiagnosis pcDetails = findRecordInDB(pcDrugToDiagnosis.getServiceCode(), pcDrugToDiagnosis.getIcdCode(),
				pcDrugToDiagnosis.getPayerId(), pcDrugToDiagnosis.getModuleName());
		if (pcDetails != null && !pcDetails.getId().equals(pcAgeSeqId)) {
			throw new AdminException(PbmAdminMessages.DUPLICATE_CUSTOMIZATION_REQUEST.message()
					.replace(PbmAdminConstants.PAYER_ID.value(), payerId)
					.replace(PbmAdminConstants.ICD_CODE.value(), icdCode)
					.replace(PbmAdminConstants.SERVICE_CODE.value(), serviceCode)
					.replace(PbmAdminConstants.MODULE_NAME.value(), moduleName));
		}
	}

	private String setRuleIdAndId(PCDrugToDiagnosis drugToDiagnosis) {
		Long id = pcDrugToDiagnosisRepository.findLatestId();
		Long newId = id != null ? ++id : 1L;
		drugToDiagnosis.setId(newId);
		drugToDiagnosis.setRuleId(PCRule.PC_DRUG_TO_DIAGNOSIS_INDICATION_CONTRAINDICATION.value() + "_" + newId);
		return newId + "";
	}

	private PCDrugToDiagnosis findRecordInDB(String serviceCode, String icdCode, String payerId, String moduleName) {
		Optional<PCDrugToDiagnosis> optionalPCDrugToDiagnosis = pcDrugToDiagnosisRepository
				.findByServiceCodeAndPayerIdAndModuleNameAndIcdCode(serviceCode, payerId, moduleName, icdCode);
		if (optionalPCDrugToDiagnosis.isPresent()) {
			return optionalPCDrugToDiagnosis.get();
		}
		return null;
	}

	public List<String> getAllRejectionCategory() {
		log.info("get All RejectionCategory");
		return Arrays.stream(RejectionCategory.values()).map(RejectionCategory::value).collect(Collectors.toList());
	}

	public List<String> getAllServiceStatus() {
		log.info("get All ServiceStatus");
		return Arrays.stream(ServiceStatus.values()).map(ServiceStatus::value).collect(Collectors.toList());
	}

	public List<String> getAllModuleName() {
		log.info("get All ModuleName");
		return Arrays.stream(ModuleName.values()).map(ModuleName::value).collect(Collectors.toList());
	}

	private Long getBatchId(String originalFilename, String uploader) {
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

	private Long saveOrUpdateDataIntoPCDrugToDiagnosisTable(
			List<PCDrugToDiagnosisRequestModel> pcDrugToDiagnosisReqList, boolean isOverride,
			List<DrugToDiagnosisModel> errorList, Long batchId) {
		var count = new Long[] { 0L };
		pcDrugToDiagnosisReqList.forEach(model -> {
			DrugToDiagnosisModel drugToDiagnosisModel = new DrugToDiagnosisModel();
			drugToDiagnosisModel.setRowNumber(Long.valueOf(model.getRowNumber()));
			Optional<PCDrugToDiagnosis> optionalPCDrugToDiagnosis = pcDrugToDiagnosisRepository
					.findByServiceCodeAndModuleNameAndIcdCodeAndPayerId(model.getServiceCode(), model.getModuleName(),
							model.getIcdCode(), model.getPayerId());
			if (optionalPCDrugToDiagnosis.isPresent()) {
				PCDrugToDiagnosis pcDrugToDiagnosis = optionalPCDrugToDiagnosis.get();
				String errorMessage = "Customization rule is already present with details like "
						+ "DrugCode: {drug} with PayerId: {payerId}, DiagnosisCode: {icd} "
						+ "Module: {module} & Status: {status}.";
				errorMessage = errorMessage.replace("{drug}", pcDrugToDiagnosis.getServiceCode())
						.replace("{payerId}", pcDrugToDiagnosis.getPayerId())
						.replace("{icd}", pcDrugToDiagnosis.getIcdCode())
						.replace("{module}", pcDrugToDiagnosis.getModuleName())
						.replace("{status}", pcDrugToDiagnosis.getServiceStatus());
				if (model.getServiceStatus().equals(pcDrugToDiagnosis.getServiceStatus())
						&& (model.getPayerId().equals(pcDrugToDiagnosis.getPayerId())
								|| pcDrugToDiagnosis.getPayerId().equals("101"))) {
					List<String> errorDescList = new ArrayList<>();
					errorDescList.add(errorMessage);
					drugToDiagnosisModel.setErrorDescriptions(errorDescList);
					drugToDiagnosisModel.setDuplicateRecord(true);
					errorList.add(drugToDiagnosisModel);
				} else if (!model.getServiceStatus().equals(pcDrugToDiagnosis.getServiceStatus())
						&& model.getPayerId().equals(pcDrugToDiagnosis.getPayerId())) {
					if (isOverride) {
						pcDrugToDiagnosis.setServiceStatus(model.getServiceStatus());
						pcDrugToDiagnosis.setRejectionCategory(model.getRejectionCategory());
						pcDrugToDiagnosis.setCategoryOfApproval(model.getCategoryOfApproval());
						pcDrugToDiagnosis.setBatchId(batchId);
						pcDrugToDiagnosis.setLastUpdatedBy(
								UserInfoUtil.getUsername(SecurityContextHolder.getContext().getAuthentication()));
						pcDrugToDiagnosis.setAdditionalRejectionReason(model.getAdditionalRejectionReason());
						PCDrugToDiagnosis addedPcDrugToDiagnosis = pcDrugToDiagnosisRepository.save(pcDrugToDiagnosis);
						auditLogService.addDataInCustomizationUploadAudit(AuditUpdatedType.UPDATE,
								addedPcDrugToDiagnosis.getRuleId(), EntitiesName.PC_DRUG_TO_DIAGNOSIS,
								addedPcDrugToDiagnosis);
						count[0] = maintainSuccessfullyAddedRecordCount(count[0]);
					} else {
						List<String> errorDescList = new ArrayList<>();
						errorDescList.add(errorMessage
								+ " If you want override you can re-upload by checking is override check box.");
						drugToDiagnosisModel.setErrorDescriptions(errorDescList);
						errorList.add(drugToDiagnosisModel);
					}
				} else {
					count[0] = insertRecordIntoPCDrugToDiagnosis(model, batchId, count[0]);
				}
				return;
			}
			count[0] = insertRecordIntoPCDrugToDiagnosis(model, batchId, count[0]);
		});
		return count[0];
	}

	private Long insertRecordIntoPCDrugToDiagnosis(PCDrugToDiagnosisRequestModel model, Long batchId,
			Long successfullyAddedRecords) {
		PCDrugToDiagnosis pcDrugToDiagnosis = new PCDrugToDiagnosis();
		pcDrugToDiagnosis.setPayerId(model.getPayerId());
		pcDrugToDiagnosis.setModuleName(model.getModuleName());
		pcDrugToDiagnosis.setServiceCode(model.getServiceCode());
		pcDrugToDiagnosis.setIcdCode(model.getIcdCode());
		pcDrugToDiagnosis.setRejectionCategory(model.getRejectionCategory());
		pcDrugToDiagnosis.setAdditionalRejectionReason(model.getAdditionalRejectionReason());
		pcDrugToDiagnosis.setServiceStatus(model.getServiceStatus());
		pcDrugToDiagnosis.setCategoryOfApproval(model.getCategoryOfApproval());
		pcDrugToDiagnosis.setLastUpdatedDateTime(Timestamp.from(Instant.now()));
		pcDrugToDiagnosis.setBatchId(batchId);
		pcDrugToDiagnosis
				.setLastUpdatedBy(UserInfoUtil.getUsername(SecurityContextHolder.getContext().getAuthentication()));
		setRuleIdAndId(pcDrugToDiagnosis);
		PCDrugToDiagnosis updatedPcDrugToDiagnosis = pcDrugToDiagnosisRepository.save(pcDrugToDiagnosis);
		auditLogService.addDataInCustomizationUploadAudit(AuditUpdatedType.INSERT, updatedPcDrugToDiagnosis.getRuleId(),
				EntitiesName.PC_DRUG_TO_DIAGNOSIS, updatedPcDrugToDiagnosis);
		return maintainSuccessfullyAddedRecordCount(successfullyAddedRecords);
	}

	public Map<Object, Object> addPcDrugToDiagnosisDetailsFromFile(MultipartFile file, boolean isOverride)
			throws AdminException, IOException {
		Map<Object, Object> responseMap = new LinkedHashMap<>();
		List<DrugToDiagnosisModel> errorList = new ArrayList<>();
		if (file.isEmpty()) {
			throw new AdminException(INVALID_FILE_EMPTY_MESSAGE);
		}
		validateFileExtension(file);
		validateFileSize(file);
		Sheet sheet = getSheet(file);
		validateFileHeaders(sheet);
		validateEmptyRecordsExceptHeader(sheet);
		List<String> duplicateRecordMessages = new ArrayList<>();
		Map<Integer, List<Integer>> duplicateRecordRowNumberMap = new HashMap<>();
		Map<String, Integer> duplicateRecordMsgMap = new HashMap<>();
		List<PCDrugToDiagnosisRequestModel> pcDrugToDiagnosisReqList = new ArrayList<>();
		validateExcelRecords(sheet, pcDrugToDiagnosisReqList, errorList, duplicateRecordMsgMap,
				duplicateRecordRowNumberMap);
		int duplicateRecordCount = addDuplicateRecordMessageInList(duplicateRecordRowNumberMap,
				duplicateRecordMessages);
		Long successfullyAddedRecords = 0L;
		if (errorList.isEmpty() && duplicateRecordMessages.isEmpty()) {
			Long batchId = getBatchId(file.getOriginalFilename(),
					UserInfoUtil.getUsername(SecurityContextHolder.getContext().getAuthentication()));
			successfullyAddedRecords = saveOrUpdateDataIntoPCDrugToDiagnosisTable(pcDrugToDiagnosisReqList, isOverride,
					errorList, batchId);
		}
		if (duplicateRecordCount > 0)
			responseMap.put("duplicateRecordCount", duplicateRecordCount);
		responseMap.put("duplicateRecords", duplicateRecordMessages);
		responseMap.put("errorList", errorList);
		responseMap.put("message",
				(successfullyAddedRecords > 1 || successfullyAddedRecords == 0
						? PbmAdminMessages.CUSTOMIZATION_SUCCESS_MESSAGE.message().replace(
								PbmAdminConstants.RECORD_FIELD.value(), PbmAdminConstants.RECORDS_FIELD.value())
						: PbmAdminMessages.CUSTOMIZATION_SUCCESS_MESSAGE.message())
								.replace(PbmAdminConstants.REQUESTED_RECORDS_FIELD.value(),
										String.valueOf(successfullyAddedRecords))
								.replace(PbmAdminConstants.TOTAL_RECORDS_FIELD.value(),
										getTotalNumberOfRecords(sheet)));
		return responseMap;
	}

	private void validateExcelRecords(Sheet sheet, List<PCDrugToDiagnosisRequestModel> pcDrugToDiagnosisReqList,
			List<DrugToDiagnosisModel> errorList, Map<String, Integer> duplicateRecordMsgMap,
			Map<Integer, List<Integer>> duplicateRecordRowNumberMap) {
		List<DrugToDiagnosisApprovalCategoryModel> drugToDiagnosisApprovalCategories = null;
		List<PayerConfigModel> payerConfigList = null;
		if (!isPayerUser()) {
			drugToDiagnosisApprovalCategories = matchDataFromDbToExcel(sheet, 5, (batchOfValues, resultList) -> {
				findApprovalCategoriesFromDb(batchOfValues, resultList);
				return resultList;
			}, new ArrayList<>());

			payerConfigList = matchDataFromDbToExcel(sheet, 2, (batchOfValues, resultList) -> {
				findPayerIdsFromDb(batchOfValues, resultList);
				return resultList;
			}, new ArrayList<>());
		}
		List<IcdDiagnosisModel> icdDiagnosisList = matchDataFromDbToExcel(sheet, 1, (batchOfValues, resultList) -> {
			findIcdCodesFromDb(batchOfValues, resultList);
			return resultList;
		}, new ArrayList<>());

		List<ServiceCodeModel> drugServiceList = matchDataFromDbToExcel(sheet, 0, (batchOfValues, resultList) -> {
			findServiceCodesFromDb(batchOfValues, resultList);
			return resultList;
		}, new ArrayList<>());

		final Row initialRow = sheet.getRow(0);
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			if (row != null) {
				int rowNumber = i + 1;
				PCDrugToDiagnosisRequestModel pcDrugToDiagnosisRequestModel = mapRequestModelFromRow(row, rowNumber,
						initialRow);
				if (pcDrugToDiagnosisRequestModel != null) {
					findDuplicateRecordsWithRowNumbers(pcDrugToDiagnosisRequestModel, duplicateRecordMsgMap, rowNumber,
							duplicateRecordRowNumberMap);
					validatePCDrugToDiagnosisRequestModel(pcDrugToDiagnosisRequestModel, rowNumber, errorList,
							pcDrugToDiagnosisReqList, payerConfigList, drugToDiagnosisApprovalCategories,
							icdDiagnosisList, drugServiceList);
				}
			}
		}
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

	private void findDuplicateRecordsWithRowNumbers(PCDrugToDiagnosisRequestModel pcDrugToDiagnosisRequestModel,
			Map<String, Integer> duplicateRecordMsgMap, int i,
			Map<Integer, List<Integer>> duplicateRecordRowNumberMap) {
		String recordStr = pcDrugToDiagnosisRequestModel.getServiceCode() + "+"
				+ pcDrugToDiagnosisRequestModel.getIcdCode() + "+" + pcDrugToDiagnosisRequestModel.getPayerId() + "+"
				+ pcDrugToDiagnosisRequestModel.getModuleName();

		String recordStrLowerCase = recordStr.toLowerCase();
		if (!duplicateRecordMsgMap.containsKey(recordStrLowerCase)) {
			duplicateRecordMsgMap.put(recordStrLowerCase, i);
		} else {
			Integer originalRowNumber = duplicateRecordMsgMap.get(recordStrLowerCase);
			duplicateRecordRowNumberMap.computeIfAbsent(originalRowNumber, k -> new ArrayList<>()).add(i);
		}
	}

	private <T> List<T> matchDataFromDbToExcel(Sheet sheet, int cellNumber,
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

	private void findApprovalCategoriesFromDb(List<String> currentBatchOfApprovalCategories,
			List<DrugToDiagnosisApprovalCategoryModel> finalApprovalCategoryList) {
		List<DrugToDiagnosisApprovalCategoryModel> appCatList = drugToDiagnosisApprovalCategoryRepository
				.findByApprovalCategories(currentBatchOfApprovalCategories);
		finalApprovalCategoryList.addAll(appCatList);
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

	private void findIcdCodesFromDb(List<String> currentBatchOfIcdCode, List<IcdDiagnosisModel> finalIcdDiagnosisList) {
		List<IcdDiagnosisModel> icdDiagnosisList = icdDiagnosisRepository.findByIcdCodes(currentBatchOfIcdCode);
		finalIcdDiagnosisList.addAll(icdDiagnosisList);
	}

	private void validatePCDrugToDiagnosisRequestModel(PCDrugToDiagnosisRequestModel pcDrugToDiagnosisRequestModel,
			int rowNumber, List<DrugToDiagnosisModel> errorList,
			List<PCDrugToDiagnosisRequestModel> pcDrugToDiagnosisReqList, List<PayerConfigModel> payerConfigList,
			List<DrugToDiagnosisApprovalCategoryModel> drugToDiagnosisApprovalCategories,
			List<IcdDiagnosisModel> icdDiagnosisList, List<ServiceCodeModel> drugServiceList) {
		List<String> errorMessages = new ArrayList<>();
		validateBeanValidationOfRequestModel(pcDrugToDiagnosisRequestModel, errorMessages);
		validateIcdCode(icdDiagnosisList, pcDrugToDiagnosisRequestModel.getIcdCode(), errorMessages);
		validateServiceCode(drugServiceList, pcDrugToDiagnosisRequestModel.getServiceCode(), errorMessages);
		if (!isPayerUser()) {
			validatePayerId(payerConfigList, pcDrugToDiagnosisRequestModel.getPayerId(), errorMessages);
			validateCategoryOfApproval(drugToDiagnosisApprovalCategories,
					pcDrugToDiagnosisRequestModel.getCategoryOfApproval(), errorMessages);
		}
		if (!errorMessages.isEmpty()) {
			DrugToDiagnosisModel drugToDiagnosisModel = new DrugToDiagnosisModel();
			drugToDiagnosisModel.setErrorDescriptions(errorMessages);
			drugToDiagnosisModel.setRowNumber(Long.valueOf(rowNumber));
			errorList.add(drugToDiagnosisModel);
			return;
		}
		pcDrugToDiagnosisReqList.add(pcDrugToDiagnosisRequestModel);
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

	private void validateBeanValidationOfRequestModel(PCDrugToDiagnosisRequestModel pcDrugToDiagnosisRequestModel,
			List<String> errorMessages) {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<PCDrugToDiagnosisRequestModel>> violations = validator
				.validate(pcDrugToDiagnosisRequestModel);
		for (ConstraintViolation<PCDrugToDiagnosisRequestModel> c : violations) {
			errorMessages.add(c.getMessage());
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

	private boolean hasNotNullValidationMessage(String modelFieldName, List<String> errorMessages) {
		String notNullValidationMsg = messageSource.getMessage("notNullOrEmpty", null, Locale.getDefault());
		return errorMessages.contains(modelFieldName + " " + notNullValidationMsg);
	}

	private void validateIcdCode(List<IcdDiagnosisModel> icdDiagnosisList, String icdCode, List<String> errorMessages) {
		if (hasNotNullValidationMessage(STR_ICD_CODE, errorMessages)) {
			return;
		}
		IcdDiagnosisModel icdDiagnosis = icdDiagnosisList.stream()
				.filter(icdInfo -> icdInfo.getDiagnosisCode().equalsIgnoreCase(icdCode) && icdInfo.getIsValid() == 1)
				.findAny().orElse(null);
		if (icdDiagnosis == null || icdCode.getBytes().length > 20) {
			errorMessages.add(STR_ICD_CODE + "[" + icdCode + STR_NOT_FOUND);
		}
	}

	private void validateCategoryOfApproval(
			List<DrugToDiagnosisApprovalCategoryModel> drugToDiagnosisApprovalCategories, String categoryOfApproval,
			List<String> errorMessages) {
		if (hasNotNullValidationMessage("CategoryOfApproval", errorMessages)) {
			return;
		}
		DrugToDiagnosisApprovalCategoryModel drugToDiagnosisApprovalCategory = drugToDiagnosisApprovalCategories
				.stream().filter(approvalCat -> approvalCat.getName().equalsIgnoreCase(categoryOfApproval)
						&& approvalCat.getIsValid() == 1)
				.findAny().orElse(null);
		if (drugToDiagnosisApprovalCategory == null || categoryOfApproval.getBytes().length > 100) {
			errorMessages.add("CategoryOfApproval values should be like AsPerPBMExperts");
		}
	}

	private PCDrugToDiagnosisRequestModel mapRequestModelFromRow(Row row, int rowNumber, Row initialRow) {
		if (isRowEmpty(row, initialRow)) {
			return null;
		}
		PCDrugToDiagnosisRequestModel model = new PCDrugToDiagnosisRequestModel();
		model.setServiceCode(getCellValue(row, 0));
		model.setIcdCode(getCellValue(row, 1));
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (isPayerUser()) {
			model.setModuleName(getCellValue(row, 2));
			model.setRejectionCategory(getCellValue(row, 3));
			model.setServiceStatus(getCellValue(row, 4));
			model.setAdditionalRejectionReason(getCellValue(row, 5));
			model.setPayerId(UserInfoUtil.getAccId(authentication));
			model.setCategoryOfApproval(UserInfoUtil.getAccName(authentication));
		} else {
			model.setPayerId(getCellValue(row, 2));
			model.setModuleName(getCellValue(row, 3));
			model.setRejectionCategory(getCellValue(row, 4));
			model.setCategoryOfApproval(getCellValue(row, 5));
			model.setServiceStatus(getCellValue(row, 6));
			model.setAdditionalRejectionReason(getCellValue(row, 7));
		}
		model.setRowNumber(rowNumber);
		return model;
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

	private void validateFileHeaders(Sheet sheet) throws AdminException {
		Row headerRow = sheet.getRow(0);
		if (headerRow == null) {
			throw new AdminException(NO_HEADERS_MESSAGE);
		}
		checkHeaderCellHasNumericOrBlankCellType(headerRow);
		int cellCount = headerRow.getPhysicalNumberOfCells();
		if (isPayerUser()) {
			if (!(cellCount > 5 && headerRow.getCell(0).getStringCellValue().trim().equalsIgnoreCase(STR_SERVICE_CODE)
					&& headerRow.getCell(1).getStringCellValue().trim().equalsIgnoreCase(STR_ICD_CODE)
					&& headerRow.getCell(2).getStringCellValue().trim().equalsIgnoreCase("ModuleName")
					&& headerRow.getCell(3).getStringCellValue().trim().equalsIgnoreCase("RejectionCategory")
					&& headerRow.getCell(4).getStringCellValue().trim().equalsIgnoreCase("ServiceStatus") && headerRow
							.getCell(5).getStringCellValue().trim().equalsIgnoreCase("AdditionalRejectionReason"))) {
				throw new AdminException(INVALID_HEADERS_MESSAGE);
			}
		} else {
			if (!(cellCount > 7 && headerRow.getCell(0).getStringCellValue().trim().equalsIgnoreCase(STR_SERVICE_CODE)
					&& headerRow.getCell(1).getStringCellValue().trim().equalsIgnoreCase(STR_ICD_CODE)
					&& headerRow.getCell(2).getStringCellValue().trim().equalsIgnoreCase(STR_PAYER_ID)
					&& headerRow.getCell(3).getStringCellValue().trim().equalsIgnoreCase("ModuleName")
					&& headerRow.getCell(4).getStringCellValue().trim().equalsIgnoreCase("RejectionCategory")
					&& headerRow.getCell(5).getStringCellValue().trim().equalsIgnoreCase("CategoryOfApproval")
					&& headerRow.getCell(6).getStringCellValue().trim().equalsIgnoreCase("ServiceStatus") && headerRow
							.getCell(7).getStringCellValue().trim().equalsIgnoreCase("AdditionalRejectionReason"))) {
				throw new AdminException(INVALID_HEADERS_MESSAGE);
			}
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

	private void validateFileSize(MultipartFile file) throws AdminException {
		DataSize maxFileSize = DataSize.ofMegabytes(5);
		if (file.getSize() > maxFileSize.toBytes()) {
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

	public DrugToDiagnosisModel populateInvalidResponse(AdminException exception) {
		DrugToDiagnosisModel response = new DrugToDiagnosisModel();
		List<String> errorMsg = new ArrayList<>();
		errorMsg.add(exception.getMessage());
		response.setErrorDescriptions(errorMsg);
		return response;
	}

	public DrugToDiagnosisModel populateUnAuthorizedResponse(AccessDeniedException ex) {
		DrugToDiagnosisModel response = new DrugToDiagnosisModel();
		List<String> errorMsg = new ArrayList<>();
		errorMsg.add(ex.getMessage());
		response.setErrorDescriptions(errorMsg);
		return response;
	}

	public DrugToDiagnosisModel populateFailedResponse() {
		DrugToDiagnosisModel response = new DrugToDiagnosisModel();
		List<String> errorMsg = new ArrayList<>();
		errorMsg.add(HttpStatus.INTERNAL_SERVER_ERROR.name());
		response.setErrorDescriptions(errorMsg);
		return response;
	}

	private Long getDrugListId() {
		return drugServiceMetaDataRepository
				.findFirstByEffectiveDateLessThanEqualOrderByEffectiveDateDescUploadDateTimeDesc(new Date())
				.map(DrugServiceMetaData::getDrugListId).orElse(0L);
	}

	private boolean isPayerUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String accCat = UserInfoUtil.getAccCategory(authentication);
		return accCat != null && accCat.equalsIgnoreCase(STR_PAYER);
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

	private Long maintainSuccessfullyAddedRecordCount(Long successfullyAddedRecords) {
		return successfullyAddedRecords + 1;
	}

	private String getTotalNumberOfRecords(Sheet sheet) {
		int totalRows = 0;
		Row initialRow = sheet.getRow(1);
		for (Row row : sheet) {
			int rowNumber = row.getRowNum();
			if (rowNumber > 0) {
				PCDrugToDiagnosisRequestModel pcDrugToDiagnosisRequestModel = mapRequestModelFromRow(row, rowNumber,
						initialRow);
				totalRows = null != pcDrugToDiagnosisRequestModel ? ++totalRows : totalRows;
			}
		}
		return String.valueOf(totalRows);
	}
}
