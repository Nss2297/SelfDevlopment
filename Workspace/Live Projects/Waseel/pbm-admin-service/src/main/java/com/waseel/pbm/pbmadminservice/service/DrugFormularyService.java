package com.waseel.pbm.pbmadminservice.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.waseel.pbm.pbmadminservice.enums.AuditUpdatedType;
import com.waseel.pbm.pbmadminservice.enums.DrugFormularyMessage;
import com.waseel.pbm.pbmadminservice.enums.EntitiesName;
import com.waseel.pbm.pbmadminservice.exceptions.AdminException;
import com.waseel.pbm.pbmadminservice.model.drugformulary.AddMemberPolicyDetailsResponseModel;
import com.waseel.pbm.pbmadminservice.model.drugformulary.DrugFormularyDrugDetailsModel;
import com.waseel.pbm.pbmadminservice.model.drugformulary.DrugFormularyDrugDetailsRequestModel;
import com.waseel.pbm.pbmadminservice.model.drugformulary.DrugFormularyDrugDetailsResponseModel;
import com.waseel.pbm.pbmadminservice.model.drugformulary.DrugFormularyInvalidResponseModel;
import com.waseel.pbm.pbmadminservice.model.drugformulary.DrugFormularyMetaDataResponseModel;
import com.waseel.pbm.pbmadminservice.model.drugformulary.DrugFormularyMetaDataSearchModel;
import com.waseel.pbm.pbmadminservice.model.drugformulary.DrugFormularyMetadataRequestModel;
import com.waseel.pbm.pbmadminservice.model.drugformulary.DrugFormularyRequestModel;
import com.waseel.pbm.pbmadminservice.model.drugformulary.MemberPolicyMetaDataModel;
import com.waseel.pbm.pbmadminservice.model.drugformulary.PolicyClassesModel;
import com.waseel.pbm.pbmadminservice.model.drugformulary.PolicyDetailsModel;
import com.waseel.pbm.pbmadminservice.model.drugformulary.PolicyDetailsRequestModel;
import com.waseel.pbm.pbmadminservice.model.drugformulary.PolicyMetaDataModel;
import com.waseel.pbm.pbmadminservice.model.drugformulary.PolicyResponseModel;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugFormularyDetails;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugFormularyMetadata;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugFormularyPolicyAssociation;
import com.waseel.pbm.pbmadminservice.persist.businessrules.MemberPolicyAssociation;
import com.waseel.pbm.pbmadminservice.persist.businessrules.MemberProfile;
import com.waseel.pbm.pbmadminservice.persist.businessrules.PolicyClasses;
import com.waseel.pbm.pbmadminservice.persist.businessrules.PolicyInformation;
import com.waseel.pbm.pbmadminservice.persist.mdss.DrugService;
import com.waseel.pbm.pbmadminservice.persist.mdss.DrugServiceMetaData;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugFormularyDetailsRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugFormularyMetadataRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugFormularyPolicyAssociationRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.MemberPolicyAssociationRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.MemberProfileRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.PolicyClassRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.PolicyInformationRepository;
import com.waseel.pbm.pbmadminservice.repository.mdss.DrugServiceMetaDataRepository;
import com.waseel.pbm.pbmadminservice.repository.mdss.DrugServiceRepository;
import com.waseel.pbm.pbmadminservice.specification.DrugFormularyDrugDetailsSpecification;
import com.waseel.pbm.pbmadminservice.specification.DrugFormularyMetadataSpecification;
import com.waseel.pbm.pbmadminservice.specification.PolicyDetailsSpecification;
import com.waseel.pbm.pbmadminservice.util.UserInfoUtil;

@Service
public class DrugFormularyService {

	private final Logger log = LoggerFactory.getLogger(DrugFormularyService.class);

	private static final String FORMULARY_ID_NOT_FOUND = "FormularyId is not found or exists.";
	private static final String DATE_PATTERN = "dd-MM-yyyy";

	@Autowired
	private DrugFormularyMetadataSpecification drugFormularyMetadataSpecification;
	@Autowired
	private PolicyDetailsSpecification policyDetailsSpecification;
	@Autowired
	private DrugFormularyDrugDetailsSpecification drugFormularyDrugDetailsSpecification;
	@Autowired
	private DrugFormularyMetadataRepository drugFormularyMetadataRepository;
	@Autowired
	private PolicyInformationRepository policyInformationRepository;
	@Autowired
	private PolicyClassRepository policyClassRepository;
	@Autowired
	private MemberProfileRepository memberProfileRepository;
	@Autowired
	private MemberPolicyAssociationRepository memberPolicyAssociationRepository;
	@Autowired
	private DrugFormularyDetailsRepository drugFormularyDetailsRepository;
	@Autowired
	private DrugServiceRepository drugServiceRepository;
	@Autowired
	private DrugFormularyPolicyAssociationRepository drugFormularyPolicyAssociationRepository;
	@Autowired
	private AuditLogService auditLogService;
	@Autowired
	private DrugServiceMetaDataRepository drugServiceMetaDataRepository;

	public Page<DrugFormularyMetaDataResponseModel> getAllDrugFormularyMetaData(
			DrugFormularyMetaDataSearchModel dfmdSearchModel) {
		log.info("PageNumber {} RecordSize {}", dfmdSearchModel.getPageNumber(), dfmdSearchModel.getRecordSize());
		return drugFormularyMetadataSpecification.findFormulariesWithPagination(dfmdSearchModel);
	}

	public Page<DrugFormularyDrugDetailsModel> getAllDrugFormularyDrugsDetails(
			DrugFormularyDrugDetailsModel dfddModel) {
		log.info("FormularyId {},PageNumber {}, RecordSize {}", dfddModel.getFormularyId(), dfddModel.getPageNumber(),
				dfddModel.getRecordSize());
		return drugFormularyDrugDetailsSpecification.findDrugFormularyDrugDetailsWithPagination(dfddModel);
	}

	public Page<PolicyDetailsModel> getPolicyDetailsUsingFormularyId(PolicyDetailsModel policyDetailsModel) {
		return policyDetailsSpecification.findByRequestIdWithPagination(policyDetailsModel);
	}

	public DrugFormularyMetaDataResponseModel getDrugFormularyMetadataDetails(Long formularyId) throws AdminException {
		String payerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		Optional<DrugFormularyMetadata> drugFormularyMetadataOpt = drugFormularyMetadataRepository
				.findByFormularyIdAndPayerIdAndIsDeleted(formularyId, payerId, false);
		if (drugFormularyMetadataOpt.isPresent()) {
			DrugFormularyMetadata dfmd = drugFormularyMetadataOpt.get();
			return new DrugFormularyMetaDataResponseModel(dfmd.getFormularyId(), dfmd.getFormularyName(),
					dfmd.getCreatedDate(), dfmd.getLastUpdateDate(), dfmd.getPayerId(), dfmd.getCreatedBy());
		}
		throw new AdminException(FORMULARY_ID_NOT_FOUND);
	}

	@Transactional(value = "BusinessRulesTransactionManager")
	public DrugFormularyMetaDataResponseModel updateDrugFormularyMetadataDetails(Long formularyId,
			DrugFormularyMetadataRequestModel requestModel) throws AdminException {
		String payerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		Optional<DrugFormularyMetadata> drugFormularyMetadataOpt = drugFormularyMetadataRepository
				.findByFormularyIdAndPayerIdAndIsDeleted(formularyId, payerId, false);
		String name = requestModel.getFormularyName();
		if (drugFormularyMetadataOpt.isPresent()) {
			DrugFormularyMetadata drugFormularyMetadata = drugFormularyMetadataOpt.get();
			if (!drugFormularyMetadata.getFormularyName().equalsIgnoreCase(name)) {
				Optional<DrugFormularyMetadata> formularyMetadataOptional = drugFormularyMetadataRepository
						.findByFormularyNameIgnoreCaseAndIsDeleted(name, false);
				if (formularyMetadataOptional.isPresent()) {
					throw new AdminException(DrugFormularyMessage.FORMULARY_NAME_ALREADY_EXISTS.value());
				}
			}
			drugFormularyMetadata.setFormularyName(name);
			drugFormularyMetadata.setLastUpdateDate(new Date());
			DrugFormularyMetadata updatedDrugFormularyMetadata = drugFormularyMetadataRepository
					.save(drugFormularyMetadata);
			auditLogService.addDataInAuditLog(AuditUpdatedType.UPDATE, updatedDrugFormularyMetadata.getFormularyId(),
					EntitiesName.DRUG_FORMULARY_METADATA, updatedDrugFormularyMetadata);
			log.info("Drug formulary updated successfully for formularyId {} ",
					updatedDrugFormularyMetadata.getFormularyId());
			return new DrugFormularyMetaDataResponseModel("Updated Successfully.");
		}
		throw new AdminException(FORMULARY_ID_NOT_FOUND);
	}

	@Transactional(value = "BusinessRulesTransactionManager")
	public void deleteDrugFormulary(Long formularyId) throws AdminException {
		String payerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		Optional<DrugFormularyMetadata> drugFormularyMetadataOpt = drugFormularyMetadataRepository
				.findByFormularyIdAndPayerIdAndIsDeleted(formularyId, payerId, false);
		if (drugFormularyMetadataOpt.isPresent()) {
			deleteDrugFormularyMetadata(drugFormularyMetadataOpt.get());
			log.info("Drug Formulary successfully Deleted for FormularyId: {}", formularyId);
			return;
		}
		log.info("Drug Formulary Not found or exists for FormularyId: {}", formularyId);
		throw new AdminException(FORMULARY_ID_NOT_FOUND);
	}

	private void deleteDrugFormularyMetadata(DrugFormularyMetadata drugFormularyMetadata) {
		String userName = UserInfoUtil.getAccName(SecurityContextHolder.getContext().getAuthentication());
		Long formularyId = drugFormularyMetadata.getFormularyId();
		deleteDrugFormularyDrugDetails(formularyId, userName);
		deleteDrugFormularyPolicyAssociation(formularyId);
		drugFormularyMetadata.setLastUpdateDate(new Date());
		drugFormularyMetadata.setIsDeleted(true);
		drugFormularyMetadata.setDeletedBy(userName);
		drugFormularyMetadata.setFormularyName(
				drugFormularyMetadata.getFormularyName().concat("_").concat(UUID.randomUUID().toString()));
		DrugFormularyMetadata updatedDrugFormularyMetadata = drugFormularyMetadataRepository
				.save(drugFormularyMetadata);
		auditLogService.addDataInAuditLog(AuditUpdatedType.DELETE, updatedDrugFormularyMetadata.getFormularyId(),
				EntitiesName.DRUG_FORMULARY_METADATA, null);
	}

	private void deleteDrugFormularyDrugDetails(Long formularyId, String userName) {
		List<DrugFormularyDetails> drugFormularyDetails = drugFormularyDetailsRepository
				.findByFormularyIdAndIsDeleted(formularyId, false);
		if (drugFormularyDetails != null && !drugFormularyDetails.isEmpty()) {
			drugFormularyDetails.forEach(drugDetail -> {
				drugDetail.setIsDeleted(true);
				drugDetail.setDeletedBy(userName);
			});
			List<DrugFormularyDetails> updatedDrugFormularyDetails = drugFormularyDetailsRepository
					.saveAll(drugFormularyDetails);
			updatedDrugFormularyDetails.forEach(details -> auditLogService.addDataInAuditLog(AuditUpdatedType.DELETE,
					details.getDrugFormularyDetailsId(), EntitiesName.DRUG_FORMULARY_DETAILS, null));
		}
	}

	private void deleteDrugFormularyPolicyAssociation(Long formularyId) {
		List<DrugFormularyPolicyAssociation> drugFormularyPolicyAssociations = drugFormularyPolicyAssociationRepository
				.findByFormularyId(formularyId);
		if (drugFormularyPolicyAssociations != null && !drugFormularyPolicyAssociations.isEmpty()) {
			drugFormularyPolicyAssociations.forEach(dfPolicyAsso -> dfPolicyAsso.setIsEnabled(false));
			List<DrugFormularyPolicyAssociation> updatedDrugFormularyPolicyAssociations = drugFormularyPolicyAssociationRepository
					.saveAll(drugFormularyPolicyAssociations);
			updatedDrugFormularyPolicyAssociations
					.forEach(details -> auditLogService.addDataInAuditLog(AuditUpdatedType.DELETE,
							details.getDrugFormularyAssociationId(), EntitiesName.DRUG_FORMULARY_POLICY_ASSOCIATION,
							null));
		}
	}

	public DrugFormularyInvalidResponseModel populateInvalidFailedResponse(Exception ex) {
		String strInvalid = "INVALID";
		String errorMessage = HttpStatus.INTERNAL_SERVER_ERROR.name();
		String errorCode = "FAILED";
		if (ex instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException methodArgEx = (MethodArgumentNotValidException) ex;
			List<String> errors = methodArgEx.getBindingResult().getAllErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
			errorMessage = StringUtils.strip(errors.toString(), "[]");
			errorCode = strInvalid;
		} else if (ex instanceof ConstraintViolationException) {
			ConstraintViolationException cve = (ConstraintViolationException) ex;
			List<String> errors = cve.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
					.collect(Collectors.toList());
			errorMessage = StringUtils.strip(errors.toString(), "[]");
			errorCode = strInvalid;
		} else if (ex instanceof BindException) {
			BindException bindException = (BindException) ex;
			List<String> errors = bindException.getBindingResult().getFieldErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
			errorCode = strInvalid;
			errorMessage = StringUtils.strip(errors.toString(), "[]");
		} else if (ex instanceof AdminException) {
			AdminException adminException = (AdminException) ex;
			errorCode = strInvalid;
			errorMessage = adminException.getMessage();
		}
		return new DrugFormularyInvalidResponseModel(errorCode, errorMessage);
	}

	public DrugFormularyInvalidResponseModel populateUnAuthorizedResponse(AccessDeniedException ex) {
		return new DrugFormularyInvalidResponseModel(HttpStatus.UNAUTHORIZED.name(), ex.getMessage());
	}

	@Transactional(value = "BusinessRulesTransactionManager")
	public void addDrugFormularyDetails(DrugFormularyRequestModel requestModel) throws AdminException {
		String name = requestModel.getFormularyName();
		Optional<DrugFormularyMetadata> formularyMetadataOptional = drugFormularyMetadataRepository
				.findByFormularyName(name);
		if (formularyMetadataOptional.isPresent()) {
			throw new AdminException(DrugFormularyMessage.FORMULARY_NAME_ALREADY_EXISTS.value());
		}
		Authentication token = SecurityContextHolder.getContext().getAuthentication();
		DrugFormularyMetadata drugFormularyMetadata = new DrugFormularyMetadata();
		drugFormularyMetadata.setFormularyName(name);
		drugFormularyMetadata.setCreatedBy(UserInfoUtil.getAccName(token));
		drugFormularyMetadata.setPayerId(UserInfoUtil.getAccId(token));
		drugFormularyMetadata.setDeletedBy("NA");
		drugFormularyMetadata.setCreatedDate(new Date());
		drugFormularyMetadata.setLastUpdateDate(new Date());
		DrugFormularyMetadata addedDrugFormularyMetadata = drugFormularyMetadataRepository.save(drugFormularyMetadata);
		auditLogService.addDataInAuditLog(AuditUpdatedType.INSERT, addedDrugFormularyMetadata.getFormularyId(),
				EntitiesName.DRUG_FORMULARY_METADATA, addedDrugFormularyMetadata);
		saveDrugFormularyDetails(addedDrugFormularyMetadata.getFormularyId(), requestModel);
	}

	private void saveDrugFormularyDetails(Long formularyId, DrugFormularyRequestModel requestModel) {
		List<DrugFormularyDetails> drugFormularyDetailsList = new ArrayList<>();
		Long drugListId = getDrugListId();
		requestModel.getDrugDetails().forEach(model -> {
			DrugFormularyDetails details = new DrugFormularyDetails();
			details.setFormularyId(formularyId);
			details.setLastUpdateDate(new Date());
			details.setPrice(model.getPrice());
			String drugCode = model.getDrugCode();
			details.setRegistrationNumber(drugCode);
			details.setScientificName(model.getGenericName());
			Optional<DrugService> optionalDrugService = drugServiceRepository
					.findByOtherCodesValueAndDrugListId(drugCode, drugListId);
			String scientificCode = "UNDEFINED";
			if (optionalDrugService.isPresent()) {
				if (optionalDrugService.get().getScientificCode() != null) {
					scientificCode = optionalDrugService.get().getScientificCode();
				}
				details.setWaseelDrugId(optionalDrugService.get().getWaseelDrugId());
			}
			details.setScientificCode(scientificCode);
			details.setTradeName(model.getDrugName());
			details.setPatientShare(model.getPatientShare());
			drugFormularyDetailsList.add(details);
		});
		if (!drugFormularyDetailsList.isEmpty()) {
			List<DrugFormularyDetails> addedDrugFormularyDetailsList = drugFormularyDetailsRepository
					.saveAll(drugFormularyDetailsList);
			addedDrugFormularyDetailsList.forEach(details -> auditLogService.addDataInAuditLog(AuditUpdatedType.INSERT,
					details.getDrugFormularyDetailsId(), EntitiesName.DRUG_FORMULARY_DETAILS, details));
		}
		savePolicyInformationData(requestModel, formularyId);
	}

	private Long getDrugListId() {
		return drugServiceMetaDataRepository
				.findFirstByEffectiveDateLessThanEqualOrderByEffectiveDateDescUploadDateTimeDesc(new Date())
				.map(DrugServiceMetaData::getDrugListId).orElse(0L);
	}

	private void verifyDrugFormularyDrugDetailsModel(List<DrugFormularyDrugDetailsModel> drugDetails,
			List<String> errors) {
		drugDetails.stream().forEach(drug -> {
			if (StringUtils.isBlank(drug.getDrugCode())) {
				errors.add(DrugFormularyMessage.NULL_OR_EMPTY_MESSAGE.value().replace(
						DrugFormularyMessage.FIELD_PARAMETER.value(), DrugFormularyMessage.DRUG_CODE_FIELD.value()));
			}
			if (StringUtils.isBlank(drug.getDrugName())) {
				errors.add(DrugFormularyMessage.NULL_OR_EMPTY_ERROR_MESSAGE.value()
						.replace(DrugFormularyMessage.FIELD_PARAMETER.value(),
								DrugFormularyMessage.DRUG_NAME_FIELD.value())
						.replaceFirst(DrugFormularyMessage.DRUG_CODE_FIELD.value(), drug.getDrugCode()));
			}
			if (StringUtils.isBlank(drug.getGenericName())) {
				errors.add(DrugFormularyMessage.NULL_OR_EMPTY_ERROR_MESSAGE.value()
						.replace(DrugFormularyMessage.FIELD_PARAMETER.value(),
								DrugFormularyMessage.GENERIC_NAME_FIELD.value())
						.replaceFirst(DrugFormularyMessage.DRUG_CODE_FIELD.value(), drug.getDrugCode()));
			}
			if (null == drug.getPrice()) {
				errors.add(DrugFormularyMessage.NULL_OR_EMPTY_ERROR_MESSAGE.value()
						.replace(DrugFormularyMessage.FIELD_PARAMETER.value(), DrugFormularyMessage.PRICE_FIELD.value())
						.replaceFirst(DrugFormularyMessage.DRUG_CODE_FIELD.value(), drug.getDrugCode()));
			}
		});
	}

	private void savePolicyInformationData(DrugFormularyRequestModel requestModel, Long formularyId) {
		List<DrugFormularyPolicyAssociation> drugFormularyPolicyAssociationList = new ArrayList<>();
		List<PolicyMetaDataModel> policyMetaDataModelList = requestModel.getPolicyDetails();
		policyMetaDataModelList.forEach(model -> {
			DrugFormularyPolicyAssociation association = new DrugFormularyPolicyAssociation();
			String policyNumber = model.getPolicyNumber();
			PolicyInformation policyInformation = saveOrUpdatePolicyInfoAndClassInfo(policyNumber, model);
			Long policyInfoId = policyInformation.getPolicyInformationId();
			Long policyClassId = getPolicyClassId(model.getPolicyClassName(), policyInfoId);
			Long memberPolicyAssociationId = getMemberPolicyAssId(model, policyInfoId, requestModel.getMemberDetails());
			Optional<DrugFormularyPolicyAssociation> optionalDrugFormularyPolicyAssociation = drugFormularyPolicyAssociationRepository
					.findByPolicyInformationIdAndAndPolicyClassIdAndMemberPolicyAssociationIdAndIsEnabled(policyInfoId,
							policyClassId, memberPolicyAssociationId, true);
			if (optionalDrugFormularyPolicyAssociation.isPresent()) {
				association = optionalDrugFormularyPolicyAssociation.get();
			}

			association.setFormularyId(formularyId);
			association.setPolicyInformationId(policyInfoId);
			association.setPolicyClassId(policyClassId);
			association.setMemberPolicyAssociationId(memberPolicyAssociationId);
			drugFormularyPolicyAssociationList.add(association);
		});
		if (!drugFormularyPolicyAssociationList.isEmpty()) {
			List<DrugFormularyPolicyAssociation> addedDrugFormularyPolicyAssociationList = drugFormularyPolicyAssociationRepository
					.saveAll(drugFormularyPolicyAssociationList);
			addedDrugFormularyPolicyAssociationList
					.forEach(details -> auditLogService.addDataInAuditLog(AuditUpdatedType.INSERT,
							details.getDrugFormularyAssociationId(), EntitiesName.DRUG_FORMULARY_POLICY_ASSOCIATION,
							details));
		}
	}

	private PolicyInformation saveOrUpdatePolicyInfoAndClassInfo(String policyNumber, PolicyMetaDataModel model) {
		Optional<PolicyInformation> optionalPolicyInformation = policyInformationRepository
				.findByPolicyNumber(policyNumber);
		PolicyInformation savedPolicyInformation;
		if (!optionalPolicyInformation.isPresent()) {
			savedPolicyInformation = savePolicyInformation(model);
		} else {
			savedPolicyInformation = optionalPolicyInformation.get();
		}
		saveOrUpdatePolicyClasses(savedPolicyInformation, model);
		return savedPolicyInformation;
	}

	private PolicyInformation savePolicyInformation(PolicyMetaDataModel model) {
		PolicyInformation policyInformation = new PolicyInformation();
		policyInformation.setPolicyHolderName(model.getPolicyHolderName());
		policyInformation.setPolicyNumber(model.getPolicyNumber());
		policyInformation.setPolicyType(model.getPolicyType());
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
		Date issueDate = null;
		try {
			issueDate = sdf.parse(model.getIssueDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date startDate = null;
		try {
			startDate = sdf.parse(model.getStartDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date endDate = null;
		try {
			endDate = sdf.parse(model.getEndDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		policyInformation.setIssueDate(issueDate);
		policyInformation.setStartDate(startDate);
		policyInformation.setEndDate(endDate);
		policyInformation.setPayerId(UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication()));
		policyInformation.setLastUpdateDate(Timestamp.from(Instant.now()));
		Long id = policyInformationRepository.findLatestId();
		Long policyInfoId = id != null ? ++id : 1L;
		policyInformation.setPolicyInformationId(policyInfoId);
		return policyInformationRepository.save(policyInformation);
	}

	private void saveOrUpdatePolicyClasses(PolicyInformation savedPolicyInformation, PolicyMetaDataModel model) {
		Long policyInfoId = savedPolicyInformation.getPolicyInformationId();
		List<PolicyClassesModel> policyClassesList = model.getPolicyClasses();
		if (policyClassesList != null) {
			List<PolicyClasses> policyClassesList1 = new ArrayList<>();
			Long id = policyClassRepository.findLatestId();
			for (PolicyClassesModel policyClassesModel : policyClassesList) {
				Optional<PolicyClasses> optionalPolicyClasses = policyClassRepository
						.findByPolicyInformationIdAndClassCode(policyInfoId, policyClassesModel.getClassCode());
				PolicyClasses policyClasses = new PolicyClasses();
				if (optionalPolicyClasses.isPresent()) {
					policyClasses = optionalPolicyClasses.get();
					if (Boolean.FALSE.equals(policyClasses.getIsEnabled())) {
						policyClasses.setIsEnabled(true);
						policyClassesList1.add(policyClasses);
					}
				} else {
					Long policyClassId = id != null ? ++id : 1L;
					policyClasses.setPolicyClassId(policyClassId);
					policyClasses.setClassCode(policyClassesModel.getClassCode());
					policyClasses.setPolicyInformationId(policyInfoId);
					policyClasses.setClassLimitCurrency("SAR");
					policyClassesList1.add(policyClasses);
					id = policyClassId;
				}
			}
			if (!policyClassesList1.isEmpty()) {
				policyClassRepository.saveAll(policyClassesList1);
			}
		}
	}

	private Long getMemberPolicyAssId(PolicyMetaDataModel model, Long policyInfoId,
			MemberPolicyMetaDataModel memberModel) {
		String idNumber = memberModel.getIdNumber();
		if (!StringUtils.isBlank(idNumber)) {
			MemberProfile memberProfile = getMemberProfile(memberModel);
			return getMemberPolicyAssId(memberProfile, policyInfoId, model);
		}
		return null;
	}

	private Long getMemberPolicyAssId(MemberProfile memberProfile, Long policyInfoId, PolicyMetaDataModel model) {
		List<PolicyClassesModel> policyClassesModels = model.getPolicyClasses();
		String classCode = policyClassesModels != null && !policyClassesModels.isEmpty()
				? policyClassesModels.get(0).getClassCode()
				: null;
		Long policyClassId = getPolicyClassId(classCode, policyInfoId);
		Optional<MemberPolicyAssociation> optionalMemberPolicyAssociation = memberPolicyAssociationRepository
				.findByPolicyInformationIdAndMemberProfileIdAndPolicyClassId(policyInfoId,
						memberProfile.getMemberProfileId(), policyClassId);
		if (optionalMemberPolicyAssociation.isPresent()) {
			return optionalMemberPolicyAssociation.get().getMemberPolicyAssociationId();
		}
		MemberPolicyAssociation mpa = new MemberPolicyAssociation();
		mpa.setMemberId(memberProfile.getIdNumber() + "");
		mpa.setMemberProfileId(memberProfile.getMemberProfileId());
		mpa.setPolicyInformationId(policyInfoId);
		mpa.setPolicyClassId(policyClassId);
		Long id = memberPolicyAssociationRepository.findLatestId();
		Long memberPolicyAssId = id != null ? ++id : 1L;
		mpa.setMemberPolicyAssociationId(memberPolicyAssId);
		return memberPolicyAssociationRepository.save(mpa).getMemberPolicyAssociationId();
	}

	private MemberProfile getMemberProfile(MemberPolicyMetaDataModel model) {
		Long idNumber = Long.parseLong(model.getIdNumber());
		Optional<MemberProfile> optionalMemberProfile = memberProfileRepository.findByIdNumber(idNumber);
		if (optionalMemberProfile.isPresent()) {
			return optionalMemberProfile.get();
		}
		MemberProfile mp = new MemberProfile();
		mp.setEmail(model.getEmail());
		mp.setMemberName(model.getMemberName());
		mp.setNationality(model.getNationality());
		mp.setGender(model.getGender());
		mp.setMobileNumber(model.getMobileNumber());
		mp.setEmail(model.getEmail());
		mp.setIdNumber(idNumber);
		mp.setLastUpdateDate(new Date());
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DATE_PATTERN);
		mp.setDob(LocalDate.parse(model.getDateOfBirth(), dateFormat));
		Long id = memberProfileRepository.findLatestId();
		Long memberProfileId = id != null ? ++id : 1L;
		mp.setMemberProfileId(memberProfileId);
		return memberProfileRepository.save(mp);
	}

	private Long getPolicyClassId(String className, Long policyInformationId) {
		if (!StringUtils.isBlank(className)) {
			Optional<PolicyClasses> optionalPolicyClasses = policyClassRepository
					.findByPolicyInformationIdAndClassCodeAndIsEnabled(policyInformationId, className, true);
			if (optionalPolicyClasses.isPresent()) {
				return optionalPolicyClasses.get().getPolicyClassId();
			}
		}
		return null;
	}

	public PolicyResponseModel verifyPolicyDetails(String idNumber, String classCode, String policyNumber) {
		PolicyResponseModel policyResponseModel = new PolicyResponseModel();
		if (policyNumber != null && classCode == null && idNumber == null) {
			Optional<PolicyInformation> policyInformation = policyInformationRepository
					.findByPolicyNumber(policyNumber);
			if (policyInformation.isPresent()) {
				Optional<List<PolicyClasses>> policyClass = policyClassRepository
						.findByPolicyInformationId(policyInformation.get().getPolicyInformationId());
				if (policyClass.isPresent()) {
					String classCount = Integer.toString(policyClass.get().size());
					Optional<List<MemberPolicyAssociation>> members = memberPolicyAssociationRepository
							.findByPolicyInformationId(policyInformation.get().getPolicyInformationId());
					if (members.isPresent()) {
						policyResponseModel.setWarningMessage(
								"This Policy shall replace all other Policy Classes and Members that were previously specified within the system.");
					} else {
						policyResponseModel.setWarningMessage(
								"This Policy shall replace all other Policy Classes that were previously specified within the system. <Class_Count> class/es will be removed from the system."
										.replace("<Class_Count>", classCount));
					}
				}
			}
		} else if (policyNumber != null && classCode != null && idNumber == null) {
			Optional<PolicyInformation> policyInformation = policyInformationRepository
					.findByPolicyNumber(policyNumber);
			if (policyInformation.isPresent()) {
				Optional<List<PolicyClasses>> policyClasses = policyClassRepository
						.findByPolicyInformationId(policyInformation.get().getPolicyInformationId());
				if (!policyClasses.isPresent()) {
					Optional<PolicyClasses> policyClass = policyClassRepository.findByClassCode(classCode);
					if (policyClasses.isPresent()) {
						Optional<List<MemberPolicyAssociation>> members = memberPolicyAssociationRepository
								.findByPolicyClassIdAndPolicyInformationId(policyClass.get().getPolicyClassId(),
										policyInformation.get().getPolicyInformationId());
						if (members.isPresent()) {
							String memberCount = Integer.toString(members.get().size());
							policyResponseModel.setWarningMessage(
									"All members within this Policy Class will be associated with this formulary. <Count> existing members will be removed from the system as they are included in this class."
											.replace("<Count>", memberCount));
						}
					}
				} else {
					policyResponseModel.setWarningMessage("This Formulary will be specific only to this Policy Class.");
				}
			}
		} else if (idNumber != null && policyNumber != null && classCode != null) {
			Optional<PolicyClasses> policyClass = policyClassRepository.findByClassCode(classCode);
			Optional<PolicyInformation> policyInformation = policyInformationRepository
					.findByPolicyNumber(policyNumber);
			if (policyClass.isPresent() && policyInformation.isPresent()) {
				Optional<List<MemberPolicyAssociation>> members = memberPolicyAssociationRepository
						.findByPolicyClassIdAndPolicyInformationId(policyClass.get().getPolicyClassId(),
								policyInformation.get().getPolicyInformationId());
				if (!members.isPresent()) {
					policyResponseModel.setWarningMessage(
							"This policy will be specific only to this member. Other members within this class will not be included.");
				}
			}
		}
		return policyResponseModel;
	}

	@Transactional(value = "BusinessRulesTransactionManager")
	public void deleteDrugFormularyAndPolicyAssociation(Long drugFormularyAssociationId) throws AdminException {
		Optional<DrugFormularyPolicyAssociation> drugFormularyPolicyAssociationOpt = drugFormularyPolicyAssociationRepository
				.findByDrugFormularyAssociationIdAndIsEnabled(drugFormularyAssociationId, true);
		if (drugFormularyPolicyAssociationOpt.isPresent()) {
			drugFormularyPolicyAssociationRepository.deleteById(drugFormularyAssociationId);
			Optional<DrugFormularyPolicyAssociation> deletedDrugFormularyPolicyAssociationOpt = drugFormularyPolicyAssociationRepository
					.findByDrugFormularyAssociationIdAndIsEnabled(drugFormularyAssociationId, false);
			if (deletedDrugFormularyPolicyAssociationOpt.isPresent()) {
				String accId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
				Optional<DrugFormularyMetadata> drugFormularyMetadataOpt = drugFormularyMetadataRepository
						.findByFormularyIdAndPayerIdAndIsDeleted(
								deletedDrugFormularyPolicyAssociationOpt.get().getFormularyId(), accId, false);
				if (drugFormularyMetadataOpt.isPresent())
					updateLastUpdateTimeInDrugFormularyMetadata(drugFormularyMetadataOpt.get());
				auditLogService.addDataInAuditLog(AuditUpdatedType.UPDATE, drugFormularyAssociationId,
						EntitiesName.DRUG_FORMULARY_POLICY_ASSOCIATION, null);
			} else {
				throw new AdminException(DrugFormularyMessage.FAILED_TO_DELETE_DRUG_FORMULARY_ASSOCIATION.value());
			}
		} else {
			throw new AdminException(DrugFormularyMessage.INVALID_DRUG_FORMULARY_ASSOCIATION_ID.value());
		}
	}

	@Transactional(value = "BusinessRulesTransactionManager")
	public AddMemberPolicyDetailsResponseModel addOrUpdateMemberPolicyDetails(Long formularyId,
			PolicyDetailsRequestModel policyDetailsRequestModel) throws AdminException {
		List<PolicyMetaDataModel> policyDetailsList = policyDetailsRequestModel.getPolicyDetails();
		if (null != policyDetailsList && !policyDetailsList.isEmpty()) {
			AddMemberPolicyDetailsResponseModel responseModel = new AddMemberPolicyDetailsResponseModel();
			Long drugFormularyAssociationId = Long.valueOf(policyDetailsRequestModel.getDrugFormularyAssociationId());
			MemberPolicyMetaDataModel memberPolicyMetaDataModel = policyDetailsRequestModel.getMemberDetails();
			if (null != memberPolicyMetaDataModel) {
				manageMemberPolicyDetailsForFormulary(memberPolicyMetaDataModel, drugFormularyAssociationId,
						policyDetailsList, responseModel, formularyId);
			} else {
				managePolicyDetailsForFormulary(policyDetailsList, drugFormularyAssociationId, responseModel,
						formularyId);
			}
			if (null == responseModel.getErrors() && responseModel.getErrors().isEmpty()) {
				responseModel.setFormularyId(formularyId);
			}
			return responseModel;
		}
		throw new AdminException(DrugFormularyMessage.POLICY_DETAILS_DOES_NOT_FOUND.value());
	}

	@Transactional(value = "BusinessRulesTransactionManager")
	public void deleteDrugFormularyDrugDetails(Long drugFormularyDetailsId) throws AdminException {
		Optional<DrugFormularyDetails> drugFormularyDetailsOpt = drugFormularyDetailsRepository
				.findByDrugFormularyDetailsIdAndIsDeleted(drugFormularyDetailsId, false);
		if (drugFormularyDetailsOpt.isPresent()) {
			String userName = UserInfoUtil.getAccName(SecurityContextHolder.getContext().getAuthentication());
			String accId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
			DrugFormularyDetails drugFormularyDetails = drugFormularyDetailsOpt.get();
			drugFormularyDetails.setIsDeleted(true);
			drugFormularyDetails.setDeletedBy(userName);
			drugFormularyDetails.setRegistrationNumber(
					drugFormularyDetails.getRegistrationNumber().concat("_").concat(UUID.randomUUID().toString()));
			drugFormularyDetails.setLastUpdateDate(new Date());
			DrugFormularyDetails updatedDrugFormularyDetails = drugFormularyDetailsRepository
					.save(drugFormularyDetails);
			Optional<DrugFormularyMetadata> drugFormularyMetadataOpt = drugFormularyMetadataRepository
					.findByFormularyIdAndPayerIdAndIsDeleted(drugFormularyDetails.getFormularyId(), accId, false);
			if (drugFormularyMetadataOpt.isPresent())
				updateLastUpdateTimeInDrugFormularyMetadata(drugFormularyMetadataOpt.get());
			auditLogService.addDataInAuditLog(AuditUpdatedType.DELETE,
					updatedDrugFormularyDetails.getDrugFormularyDetailsId(), EntitiesName.DRUG_FORMULARY_METADATA,
					null);
			log.info("Drug Formulary Drug detail successfully Deleted for drugFormularyDetailsId: {}",
					drugFormularyDetailsId);
			return;
		}
		log.info("Drug Formulary Drug detail Not found or exists for drugFormularyDetailsId: {}",
				drugFormularyDetailsId);
		throw new AdminException("DrugFormularyDetailsId is not found or exists.");
	}

	@Transactional(value = "BusinessRulesTransactionManager")
	public DrugFormularyDrugDetailsResponseModel addDrugFormularyDrugDetails(Long formularyId,
			DrugFormularyDrugDetailsRequestModel requestModel) throws AdminException {
		String payerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		Optional<DrugFormularyMetadata> drugFormularyMetadataOpt = drugFormularyMetadataRepository
				.findByFormularyIdAndPayerIdAndIsDeleted(formularyId, payerId, false);
		if (drugFormularyMetadataOpt.isEmpty()) {
			throw new AdminException(FORMULARY_ID_NOT_FOUND);
		}
		DrugService drugService = getDrugServiceDetail(requestModel.getDrugCode());
		if (drugService == null) {
			throw new AdminException("DrugCode is not found.");
		}
		Optional<DrugFormularyDetails> drugFormularyDetailsOpt = drugFormularyDetailsRepository
				.findByFormularyIdAndWaseelDrugIdAndRegistrationNumberAndIsDeleted(formularyId,
						drugService.getWaseelDrugId(), requestModel.getDrugCode(), false);
		if (drugFormularyDetailsOpt.isPresent()) {
			throw new AdminException("Drug details already exists.");
		}
		DrugFormularyDetails addedDrugFormularyDetails = saveDataInDrugFormularyDetailTable(requestModel, formularyId,
				drugService);
		updateLastUpdateTimeInDrugFormularyMetadata(drugFormularyMetadataOpt.get());
		auditLogService.addDataInAuditLog(AuditUpdatedType.INSERT,
				addedDrugFormularyDetails.getDrugFormularyDetailsId(), EntitiesName.DRUG_FORMULARY_DETAILS,
				addedDrugFormularyDetails);
		return new DrugFormularyDrugDetailsResponseModel(addedDrugFormularyDetails.getDrugFormularyDetailsId());
	}

	private void manageMemberPolicyDetailsForFormulary(MemberPolicyMetaDataModel memberPolicyMetaDataModel,
			Long drugFormularyAssociationId, List<PolicyMetaDataModel> policyDetailsList,
			AddMemberPolicyDetailsResponseModel responseModel, Long formularyId) {
		List<String> errors = new ArrayList<>();
		MemberProfile memberProfile = memberProfileRepository.save(populateMemberProfile(memberPolicyMetaDataModel));
		String payerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		manageDrugFormularyMetadata(formularyId, payerId);
		policyDetailsList.stream().forEach(policyDetails -> {
			String policyNumber = policyDetails.getPolicyNumber();
			Optional<PolicyInformation> policyInformationOpt = policyInformationRepository
					.findByPolicyNumberAndPayerId(policyNumber, payerId);
			try {
				addOrUpdatePolicyInformation(policyInformationOpt, policyNumber, payerId, policyDetails,
						drugFormularyAssociationId, memberProfile.getMemberProfileId(), policyDetails.getMemberId(),
						formularyId);
			} catch (Exception ex) {
				errors.add(DrugFormularyMessage.INVALID_POLICY_DETAILS.value()
						.replace(DrugFormularyMessage.POLICY_NUMBER_FIELD.value(), policyNumber));
				log.error("Exception: ", ex);
			}
		});
		responseModel.setErrors(errors);
	}

	private DrugFormularyDetails saveDataInDrugFormularyDetailTable(DrugFormularyDrugDetailsRequestModel requestModel,
			Long formularyId, DrugService drugService) {
		Long waseelDrugId = drugService.getWaseelDrugId();
		String drugCode = requestModel.getDrugCode();
		Optional<DrugFormularyDetails> alreadyExistsdrugFormularyDetailsOpt = drugFormularyDetailsRepository
				.findByFormularyIdAndWaseelDrugIdAndRegistrationNumberAndIsDeleted(formularyId, waseelDrugId, drugCode,
						true);
		if (alreadyExistsdrugFormularyDetailsOpt.isPresent()) {
			DrugFormularyDetails drugFormularyDetails = alreadyExistsdrugFormularyDetailsOpt.get();
			drugFormularyDetails.setIsDeleted(false);
			drugFormularyDetails.setDeletedBy(null);
			if (requestModel.getPatientShare() != null) {
				drugFormularyDetails.setPatientShare(requestModel.getPatientShare());
			}
			return drugFormularyDetailsRepository.save(drugFormularyDetails);
		} else {
			String scientificCode = !StringUtils.isBlank(drugService.getScientificCode())
					? drugService.getScientificCode()
					: "UNDEFINED";
			DrugFormularyDetails drugFormularyDetails = new DrugFormularyDetails(formularyId, waseelDrugId, drugCode,
					requestModel.getDrugName(), requestModel.getGenericName(), scientificCode, requestModel.getPrice(),
					new Date());
			if (requestModel.getPatientShare() != null) {
				drugFormularyDetails.setPatientShare(requestModel.getPatientShare());
			}
			return drugFormularyDetailsRepository.save(drugFormularyDetails);
		}
	}

	private DrugService getDrugServiceDetail(String drugCode) {
		Long drugListId = getDrugListId();
		Optional<DrugService> optionalDrugService = drugServiceRepository.findByOtherCodesValueAndDrugListId(drugCode,
				drugListId);
		if (optionalDrugService.isPresent()) {
			return optionalDrugService.get();
		}
		return null;
	}

	private void updateLastUpdateTimeInDrugFormularyMetadata(DrugFormularyMetadata drugFormularyMetadata) {
		drugFormularyMetadata.setLastUpdateDate(new Date());
		drugFormularyMetadataRepository.save(drugFormularyMetadata);
	}

	private MemberProfile populateMemberProfile(MemberPolicyMetaDataModel memberPolicyMetaDataModel) {
		Long idNumber = Long.valueOf(memberPolicyMetaDataModel.getIdNumber());
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(DATE_PATTERN);
		LocalDate dob = LocalDate.parse(memberPolicyMetaDataModel.getDateOfBirth(), dateFormat);
		Optional<MemberProfile> memberProfileOpt = memberProfileRepository.findById(idNumber);
		MemberProfile memberProfile = null;
		if (memberProfileOpt.isPresent()) {
			memberProfile = memberProfileOpt.get();
		} else {
			memberProfile = new MemberProfile();
		}
		memberProfile.setMemberName(memberPolicyMetaDataModel.getMemberName());
		memberProfile.setIdNumber(idNumber);
		memberProfile.setDob(dob);
		memberProfile.setGender(memberPolicyMetaDataModel.getGender());
		memberProfile.setNationality(memberPolicyMetaDataModel.getNationality());
		memberProfile.setMobileNumber(memberPolicyMetaDataModel.getMobileNumber());
		memberProfile.setEmail(memberPolicyMetaDataModel.getEmail());
		memberProfile.setLastUpdateDate(new Date());
		memberProfile.setMaritalStatus(memberPolicyMetaDataModel.getMaritalStatus());
		return memberProfile;
	}

	private void managePolicyDetailsForFormulary(List<PolicyMetaDataModel> policyDetailsList,
			Long drugFormularyAssociationId, AddMemberPolicyDetailsResponseModel responseModel, Long formularyId) {
		String payerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		List<String> errors = new ArrayList<>();
		policyDetailsList.stream().forEach(policyDetails -> {
			String policyNumber = policyDetails.getPolicyNumber();
			Optional<PolicyInformation> policyInformationOpt = policyInformationRepository
					.findByPolicyNumberAndPayerId(policyNumber, payerId);
			try {
				addOrUpdatePolicyInformation(policyInformationOpt, policyNumber, payerId, policyDetails,
						drugFormularyAssociationId, null, policyDetails.getMemberId(), formularyId);
			} catch (Exception ex) {
				errors.add(DrugFormularyMessage.INVALID_POLICY_DETAILS.value()
						.replace(DrugFormularyMessage.POLICY_NUMBER_FIELD.value(), policyNumber));
				log.error("Exception: ", ex);
			}
		});
		responseModel.setErrors(errors);
	}

	private void addOrUpdatePolicyInformation(Optional<PolicyInformation> policyInformationOpt, String policyNumber,
			String payerId, PolicyMetaDataModel policyDetails, Long drugFormularyAssociationId, Long memberProfileId,
			String memberId, Long formularyId) throws ParseException {
		PolicyInformation policyInformation;
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN);
		Date issueDate = dateFormat.parse(policyDetails.getIssueDate());
		Date startDate = dateFormat.parse(policyDetails.getStartDate());
		Date endDate = dateFormat.parse(policyDetails.getEndDate());
		if (policyInformationOpt.isEmpty()) {
			policyInformation = new PolicyInformation();
			policyInformation.setPolicyNumber(policyNumber);
			policyInformation.setPayerId(payerId);
		} else {
			policyInformation = policyInformationOpt.get();
		}
		policyInformation.setIssueDate(issueDate);
		policyInformation.setStartDate(startDate);
		policyInformation.setEndDate(endDate);
		policyInformation.setPolicyHolderName(policyDetails.getPolicyHolderName());
		policyInformation.setPolicyType(policyDetails.getPolicyType());
		policyInformation.setLastUpdateDate(new Date());
		policyInformation = policyInformationRepository.save(policyInformation);
		populatePolicyClassesAndManageDrugFormularyPolicyAssociation(policyInformation.getPolicyInformationId(),
				policyDetails.getPolicyClasses(), drugFormularyAssociationId, memberProfileId, memberId, formularyId);
	}

	private void populatePolicyClassesAndManageDrugFormularyPolicyAssociation(Long policyInformationId,
			List<PolicyClassesModel> policyClassModelList, Long drugFormularyAssociationId, Long memberProfileId,
			String memberId, Long formularyId) {
		Optional<List<PolicyClasses>> policyClassesOpt = policyClassRepository
				.findByPolicyInformationId(policyInformationId);
		if (policyClassesOpt.isPresent() && !policyClassesOpt.get().isEmpty()) {
			List<PolicyClasses> policyClasses = policyClassesOpt.get();
			if (null != policyClassModelList && !policyClassModelList.isEmpty()) {
				// disable existing policy details if class not found
				policyClasses.stream()
						.filter(policyClass -> policyClassModelList.stream().noneMatch(
								policyClassModel -> policyClass.getClassCode().equals(policyClassModel.getClassCode())))
						.forEach(policyClass -> disablePolicyClassAssociations(policyClass, formularyId));
				List<PolicyClassesModel> filtredList = policyClassModelList.stream()
						.filter(policyClassModel -> policyClasses.stream().noneMatch(
								policyClass -> policyClass.getClassCode().equals(policyClassModel.getClassCode())))
						.collect(Collectors.toList());
				if (!filtredList.isEmpty()) {
					filtredList
							.forEach(policyClassModel -> addPolicyClassAndManageDrugFormularyAndMemberPolicyAssociation(
									policyClassModel.getClassCode(), policyInformationId, memberProfileId, memberId,
									formularyId));
				} else {
					policyClassModelList.stream()
							.forEach(policyClassModel -> addPolicyClassAndManageDrugFormularyAndMemberPolicyAssociation(
									policyClassModel.getClassCode(), policyInformationId, memberProfileId, memberId,
									formularyId));
				}
			}
		} else {
			policyClassModelList.stream()
					.forEach(policyClassModel -> addPolicyClassAndManageDrugFormularyAndMemberPolicyAssociation(
							policyClassModel.getClassCode(), policyInformationId, memberProfileId, memberId,
							formularyId));
		}
	}

	private void disablePolicyClassAssociations(PolicyClasses policyClass, Long formularyId) {
		Long policyInformationId = policyClass.getPolicyInformationId();
		Long policyClassId = policyClass.getPolicyClassId();
		policyClassRepository.save(disablePolicyClasses(policyClass));
		disableMemberPolicyAssociation(policyInformationId, policyClassId);
		disableDrugFormularyPolicyAssociation(policyInformationId, policyClassId, formularyId);
	}

	private PolicyClasses disablePolicyClasses(PolicyClasses policyClass) {
		policyClass.setIsEnabled(false);
		return policyClass;
	}

	private void disableMemberPolicyAssociation(Long policyInformationId, Long policyClassId) {
		Optional<List<MemberPolicyAssociation>> memberPolicyAssociationOpt = memberPolicyAssociationRepository
				.findByPolicyClassIdAndPolicyInformationId(policyClassId, policyInformationId);
		if (memberPolicyAssociationOpt.isPresent()) {
			List<MemberPolicyAssociation> memberPolicyAssociations = memberPolicyAssociationOpt.get();
			if (null != memberPolicyAssociations && !memberPolicyAssociations.isEmpty()) {
				memberPolicyAssociations.stream()
						.forEach(memberPolicyAssociation -> memberPolicyAssociation.setIsEnabled(false));
				memberPolicyAssociationRepository.saveAll(memberPolicyAssociations);
			}
		}
	}

	private void disableDrugFormularyPolicyAssociation(Long policyInformationId, Long policyClassId, Long formularyId) {
		Optional<DrugFormularyPolicyAssociation> drugFormularyPolicyAssociationOpt = drugFormularyPolicyAssociationRepository
				.findByPolicyInformationIdAndPolicyClassIdAndIsEnabledAndFormularyId(policyInformationId, policyClassId,
						true, formularyId);
		if (drugFormularyPolicyAssociationOpt.isPresent()) {
			DrugFormularyPolicyAssociation drugFormularyPolicyAssociation = drugFormularyPolicyAssociationOpt.get();
			drugFormularyPolicyAssociation.setIsEnabled(false);
			drugFormularyPolicyAssociationRepository.save(drugFormularyPolicyAssociation);
		}
	}

	private void addPolicyClassAndManageDrugFormularyAndMemberPolicyAssociation(String classCode,
			Long policyInformationId, Long memberProfileId, String memberId, Long formularyId) {
		Long memberPolicyAssociationId = null;
		PolicyClasses policyClass = policyClassRepository.save(populatePolicyClass(classCode, policyInformationId));
		if (null != memberProfileId && memberProfileId > 0 && StringUtils.isNotBlank(memberId)) {
			MemberPolicyAssociation memberPolicyAssociation = memberPolicyAssociationRepository
					.save(populateMemberPolicyAssociation(policyClass, memberProfileId, memberId));
			memberPolicyAssociationId = memberPolicyAssociation.getMemberPolicyAssociationId();
		}
		drugFormularyPolicyAssociationRepository.save(populateDrugFormularyPolicyAssociation(policyClass,
				memberPolicyAssociationId, formularyId, policyInformationId));
	}

	private MemberPolicyAssociation populateMemberPolicyAssociation(PolicyClasses policyClass, Long memberProfileId,
			String memberId) {
		MemberPolicyAssociation memberPolicyAssociation = new MemberPolicyAssociation();
		memberPolicyAssociation.setMemberProfileId(memberProfileId);
		memberPolicyAssociation.setPolicyInformationId(policyClass.getPolicyInformationId());
		memberPolicyAssociation.setPolicyClassId(policyClass.getPolicyClassId());
		memberPolicyAssociation.setMemberId(memberId);
		return memberPolicyAssociation;
	}

	private PolicyClasses populatePolicyClass(String classCode, Long policyInformationId) {
		Optional<PolicyClasses> optionalPolicyClasses = policyClassRepository
				.findByPolicyInformationIdAndClassCode(policyInformationId, classCode);
		if (optionalPolicyClasses.isPresent()) {
			return optionalPolicyClasses.get();
		} else {
			Long id = policyClassRepository.findLatestId();
			Long policyClassId = id != null ? ++id : 1L;
			PolicyClasses policyClass = new PolicyClasses();
			policyClass.setPolicyClassId(policyClassId);
			policyClass.setClassCode(classCode);
			policyClass.setPolicyInformationId(policyInformationId);
			policyClass.setClassLimitCurrency("SAR");
			return policyClass;
		}
	}

	private DrugFormularyPolicyAssociation populateDrugFormularyPolicyAssociation(PolicyClasses policyClass,
			Long memberPolicyAssociationId, Long formularyId, Long policyInformationId) {
		DrugFormularyPolicyAssociation formularyPolicyAssociation = new DrugFormularyPolicyAssociation();
		formularyPolicyAssociation.setPolicyInformationId(policyInformationId);
		formularyPolicyAssociation.setPolicyClassId(policyClass.getPolicyClassId());
		formularyPolicyAssociation.setMemberPolicyAssociationId(memberPolicyAssociationId);
		formularyPolicyAssociation.setFormularyId(formularyId);
		return formularyPolicyAssociation;
	}

	private void manageDrugFormularyMetadata(Long formularyId, String payerId) {
		Optional<DrugFormularyMetadata> drugFormularyMetadataOpt = drugFormularyMetadataRepository
				.findByFormularyIdAndPayerIdAndIsDeleted(formularyId, payerId, false);
		if (drugFormularyMetadataOpt.isPresent()) {
			updateLastUpdateTimeInDrugFormularyMetadata(drugFormularyMetadataOpt.get());
		}
	}
}
