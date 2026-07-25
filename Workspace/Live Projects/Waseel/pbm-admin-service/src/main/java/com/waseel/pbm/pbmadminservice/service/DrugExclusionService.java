package com.waseel.pbm.pbmadminservice.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.waseel.pbm.pbmadminservice.enums.AuditUpdatedType;
import com.waseel.pbm.pbmadminservice.enums.DrugExclusionMessage;
import com.waseel.pbm.pbmadminservice.enums.EntitiesName;
import com.waseel.pbm.pbmadminservice.enums.drugexclusion.DrugExclusionMetadataDefaultData;
import com.waseel.pbm.pbmadminservice.enums.drugexclusion.ExclusionMessages;
import com.waseel.pbm.pbmadminservice.enums.drugexclusion.ExclusionType;
import com.waseel.pbm.pbmadminservice.exceptions.AdminException;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.DrugExclusionDrugDetailsModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.DrugExclusionDrugDetailsRequestModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.DrugExclusionMetaDataRequestModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.DrugExclusionMetaDataResponseModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.DrugExclusionRequestModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.DrugExclusionResponseModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.ExclusionListDrugDetailsRequestModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.ExclusionTypeRequestModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.ExclusionTypeSearchModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.ExclusionTypeSearchResponseModel;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugExclusionDetails;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugExclusionMetadata;
import com.waseel.pbm.pbmadminservice.persist.businessrules.ExclusionAsscTypeList;
import com.waseel.pbm.pbmadminservice.persist.businessrules.HighCostExclusionAssc;
import com.waseel.pbm.pbmadminservice.persist.businessrules.NetworkExclusionAssc;
import com.waseel.pbm.pbmadminservice.persist.businessrules.ProviderExclusionAssc;
import com.waseel.pbm.pbmadminservice.persist.businessrules.SpecialityExclusionAssc;
import com.waseel.pbm.pbmadminservice.persist.mdss.DrugService;
import com.waseel.pbm.pbmadminservice.persist.mdss.DrugServiceMetaData;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugExclusionDetailsRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugExclusionMetadataRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.ExclusionAsscTypeListRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.HighCostExclusionAsscRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.NetworkExclusionAsscRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.ProviderExclusionAsscRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.SpecialityExclusionAsscRepository;
import com.waseel.pbm.pbmadminservice.repository.mdss.DrugServiceMetaDataRepository;
import com.waseel.pbm.pbmadminservice.repository.mdss.DrugServiceRepository;
import com.waseel.pbm.pbmadminservice.specification.DrugExclusionDrugDetailsSpecification;
import com.waseel.pbm.pbmadminservice.specification.DrugExclusionSpecification;
import com.waseel.pbm.pbmadminservice.specification.DrugExclusionTypesSpecification;
import com.waseel.pbm.pbmadminservice.util.UserInfoUtil;

@Service
public class DrugExclusionService {

	private final Logger log = LoggerFactory.getLogger(DrugExclusionService.class);
	private static final String EXCLUSION_ID_NOT_FOUND = "No such exclusion list is not found.";

	@Autowired
	DrugExclusionMetadataRepository drugExclusionMetadataRepository;
	@Autowired
	private AuditLogService auditLogService;
	@Autowired
	private HighCostExclusionAsscRepository highCostExclusionAsscRepository;
	@Autowired
	private DrugExclusionDetailsRepository drugExclusionDetailsRepository;
	@Autowired
	private DrugExclusionSpecification drugExclusionSpecification;
	@Autowired
	private NetworkExclusionService networkExclusionService;
	@Autowired
	private NetworkExclusionAsscRepository networkExclusionAsscRepository;
	@Autowired
	private DrugServiceRepository drugServiceRepository;
	@Autowired
	private DrugExclusionDrugDetailsSpecification drugExclusionDrugDetailsSpecification;
	@Autowired
	private ProviderExclusionAsscRepository providerExclusionAsscRepository;
	@Autowired
	private DrugServiceMetaDataRepository drugServiceMetaDataRepository;
	@Autowired
	private ProviderExclusionService providerExclusionService;
	@Autowired
	private DrugExclusionTypesSpecification drugExclusionTypesSpecification;
	@Autowired
	private SpecialityExclusionAsscRepository specialityExclusionAsscRepository;
	@Autowired
	private ExclusionAsscTypeListRepository exclusionAsscTypeListRepository;
	@Autowired
	private SpecialityExclusionService specialityExclusionService;

	public void createDrugExclusion(Long exclusionId, String exclusionName) throws AdminException {
		long payerId = Long.parseLong(UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication()));
		Optional<DrugExclusionMetadata> drugExclusionMetadataOpt = drugExclusionMetadataRepository
				.findByExclusionIdAndPayerIdAndIsDeleted(exclusionId, payerId, false);
		if (drugExclusionMetadataOpt.isPresent()) {
			DrugExclusionMetadata drugExclusionMetadata = drugExclusionMetadataOpt.get();
			if (!drugExclusionMetadata.getExclusionName().equalsIgnoreCase(exclusionName)) {
				Optional<DrugExclusionMetadata> exclusionMetadataOptional = drugExclusionMetadataRepository
						.findByExclusionNameIgnoreCaseAndIsDeletedAndPayerId(exclusionName, false, payerId);
				if (exclusionMetadataOptional.isPresent()) {
					throw new AdminException(DrugExclusionMessage.EXCLUSION_NAME_ALREADY_EXISTS.value());
				}
			}
			drugExclusionMetadata.setExclusionName(exclusionName);
			drugExclusionMetadata.setLastUpdateDate(new Date());
			DrugExclusionMetadata updatedDrugExclusionMetadata = drugExclusionMetadataRepository
					.save(drugExclusionMetadata);
			auditLogService.addDataInAuditLog(AuditUpdatedType.UPDATE, updatedDrugExclusionMetadata.getExclusionId(),
					EntitiesName.DRUG_EXCLUSION_METADATA, updatedDrugExclusionMetadata);
			log.info("Drug exclusion updated successfully for exclusionId {} ",
					updatedDrugExclusionMetadata.getExclusionId());
		} else {
			throw new AdminException(EXCLUSION_ID_NOT_FOUND);
		}
	}

	@Transactional(value = "BusinessRulesTransactionManager")
	public DrugExclusionResponseModel addNewDrugExclusionList(DrugExclusionRequestModel drugExclusionRequestModel)
			throws AdminException {
		Long payerId = Long.parseLong(UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication()));
		DrugExclusionResponseModel drugExclusionResponseModel = new DrugExclusionResponseModel();
		String drugExclusionListName = drugExclusionRequestModel.getExclusionListName();
		Optional<DrugExclusionMetadata> drugExclusionMetadataOpt = drugExclusionMetadataRepository
				.findByExclusionNameIgnoreCaseAndIsDeletedAndPayerId(drugExclusionListName, false, payerId);
		if (drugExclusionMetadataOpt.isPresent()) {
			throw new AdminException(ExclusionMessages.DUPLICATE_EXCLUSION_NAME.value());
		} else {
			validateDuplicateExclusionNetworks(drugExclusionRequestModel.getExclusionTypeDetails());
			validateDuplicateExclusionProviders(drugExclusionRequestModel.getExclusionTypeDetails());
			validateDuplicateExclusionSpecialities(drugExclusionRequestModel.getExclusionTypeDetails());
			String payerName = UserInfoUtil.getAccName(SecurityContextHolder.getContext().getAuthentication());
			manageDrugExclusionData(drugExclusionListName, drugExclusionRequestModel.getExclusionDrugDetails(),
					drugExclusionRequestModel.getExclusionTypeDetails(), payerId, payerName,
					drugExclusionResponseModel);
		}
		return drugExclusionResponseModel;
	}

	private DrugExclusionMetadata populateDrugExclusionMetadata(String drugExclusionListName, Long payerId,
			String payerName, Date date) {
		DrugExclusionMetadata drugExclusionMetadata = new DrugExclusionMetadata();
		drugExclusionMetadata.setCreatedBy(payerName);
		drugExclusionMetadata.setCreatedDate(date);
		drugExclusionMetadata.setDeletedBy(DrugExclusionMetadataDefaultData.NOT_APPLICABLE.value());
		drugExclusionMetadata.setExclusionName(drugExclusionListName);
		drugExclusionMetadata.setIsDeleted(false);
		drugExclusionMetadata.setLastUpdateDate(date);
		drugExclusionMetadata.setPayerId(payerId);
		return drugExclusionMetadata;
	}

	private void manageDrugExclusionData(String drugExclusionListName,
			List<ExclusionListDrugDetailsRequestModel> exclusionDrugDetails,
			List<ExclusionTypeRequestModel> exclusionTypeDetails, Long payerId, String payerName,
			DrugExclusionResponseModel drugExclusionResponseModel) throws AdminException {
		List<String> errors = new ArrayList<>();
		Date date = new Date();
		DrugExclusionMetadata drugExclusionMetadataObj = populateDrugExclusionMetadata(drugExclusionListName, payerId,
				payerName, date);
		DrugExclusionMetadata drugExclusionMetadata = drugExclusionMetadataRepository.save(drugExclusionMetadataObj);
		Long exclusionId = drugExclusionMetadata.getExclusionId();
		auditLogService.addDataInAuditLog(AuditUpdatedType.INSERT, exclusionId, EntitiesName.DRUG_EXCLUSION_METADATA,
				drugExclusionMetadataObj);
		manageAssociationDataBasedOnExclusion(exclusionId, date, exclusionTypeDetails, payerId);
		manageDrugExclusionDetails(exclusionId, date, exclusionDrugDetails, errors);
		if (!errors.isEmpty()) {
			populateDrugExclusionResponseModel(null, errors, drugExclusionResponseModel);
		} else {
			populateDrugExclusionResponseModel(exclusionId, null, drugExclusionResponseModel);
		}
	}

	private HighCostExclusionAssc populateHighCostExclusionAssc(Long exclusionId, Long payerId, Date date) {
		HighCostExclusionAssc highCostExclusionAssc = new HighCostExclusionAssc();
		highCostExclusionAssc.setExclusionId(exclusionId);
		highCostExclusionAssc.setIsEnabled(true);
		highCostExclusionAssc.setLastUpdateDate(date);
		highCostExclusionAssc.setPayerId(payerId);
		return highCostExclusionAssc;
	}

	private void manageDrugExclusionDetails(Long exclusionId, Date date,
			List<ExclusionListDrugDetailsRequestModel> exclusionDrugDetails, List<String> errors) {
		exclusionDrugDetails.stream().forEach(drugDetails -> {
			try {
				DrugExclusionDetails drug = new DrugExclusionDetails();
				drug.setExclusionId(exclusionId);
				drug.setIsDeleted(false);
				drug.setLastUpdateDate(date);
				drug.setPrice(drugDetails.getPrice());
				drug.setRegistrationNumber(drugDetails.getDrugCode());
				drug.setScientificCode(drugDetails.getScientificCode());
				drug.setScientificName(drugDetails.getScientificName());
				drug.setTradeName(drugDetails.getDrugName());
				drug.setWaseelDrugId(Long.valueOf(drugDetails.getWaseelDrugId()));
				DrugExclusionDetails drugExclusionDetails = drugExclusionDetailsRepository.save(drug);
				auditLogService.addDataInAuditLog(AuditUpdatedType.INSERT,
						drugExclusionDetails.getDrugExclusionDetailsId(), EntitiesName.DRUG_EXCLUSION_DETAILS, drug);
			} catch (Exception ex) {
				errors.add(ExclusionMessages.INVALID_DRUG.value().replace(ExclusionMessages.DRUG_CODE_FIELD.value(),
						drugDetails.getDrugCode()));
				log.error("Exception: ", ex);
			}
		});
	}

	private void manageAssociationDataBasedOnExclusion(Long exclusionId, Date date,
			List<ExclusionTypeRequestModel> exclusionTypeDetails, Long payerId) {
		exclusionTypeDetails.stream().forEach(exclusionType -> {
			if (exclusionType.getExclusionType().equals(ExclusionType.HIGH_COST_EXCLUSION.value())) {
				addNewHighCost(exclusionId, payerId, date);
			} else if (exclusionType.getExclusionType().equalsIgnoreCase(ExclusionType.NETWORK_EXCLUSION.value())) {
				addNewNetworkExclusionAssc(exclusionType.getExclusionNetwork(), exclusionId, payerId);
			} else if (exclusionType.getExclusionType().equalsIgnoreCase(ExclusionType.PROVIDER_EXCLUSION.value())) {
				addNewProviderExclusionAssc(exclusionType.getExclusionProvider(),
						exclusionType.getExclusionProviderName(), exclusionId, payerId);
			} else if (exclusionType.getExclusionType().equalsIgnoreCase(ExclusionType.SPECIALITY_EXCLUSION.value())) {
				addNewSpecialityExclusionAssc(exclusionType.getExclusionSpecialty(), exclusionId, payerId);
			}
		});
	}

	private void validateDuplicateExclusionProviders(List<ExclusionTypeRequestModel> exclusionTypeDetails)
			throws AdminException {
		Set<String> seenExclusionProviders = new HashSet<>();
		List<String> duplicateProviders = exclusionTypeDetails.stream()
				.filter(exclusion -> exclusion.getExclusionType()
						.equalsIgnoreCase(ExclusionType.PROVIDER_EXCLUSION.value()))
				.map(ExclusionTypeRequestModel::getExclusionProvider)
				.filter(exclusionNetwork -> exclusionNetwork != null && !seenExclusionProviders.add(exclusionNetwork))
				.collect(Collectors.toList());
		if (!duplicateProviders.isEmpty()) {
			throw new AdminException(ExclusionMessages.DUPLICATE_EXCLUSION_PROVIDER.value() + duplicateProviders);
		}
	}

	private void validateDuplicateExclusionSpecialities(List<ExclusionTypeRequestModel> exclusionTypeDetails)
			throws AdminException {
		Set<String> seenExclusionSpecialities = new HashSet<>();
		List<String> duplicateSpecialities = exclusionTypeDetails.stream()
				.filter(exclusion -> exclusion.getExclusionType()
						.equalsIgnoreCase(ExclusionType.SPECIALITY_EXCLUSION.value()))
				.map(ExclusionTypeRequestModel::getExclusionSpecialty)
				.filter(exclusionSpeciality -> exclusionSpeciality != null
						&& !seenExclusionSpecialities.add(exclusionSpeciality))
				.collect(Collectors.toList());
		if (!duplicateSpecialities.isEmpty()) {
			throw new AdminException(ExclusionMessages.DUPLICATE_EXCLUSION_SPECIALITY.value() + duplicateSpecialities);
		}
	}

	private DrugExclusionResponseModel populateDrugExclusionResponseModel(Long exclusionId, List<String> errors,
			DrugExclusionResponseModel drugExclusionResponseModel) {
		if (null != errors && !errors.isEmpty()) {
			drugExclusionResponseModel.setErrors(errors);
		}
		if (null != exclusionId) {
			drugExclusionResponseModel.setExclusionId(exclusionId);
		}
		return drugExclusionResponseModel;
	}

	public DrugExclusionResponseModel populateInvalidResponse(Exception ex) {
		DrugExclusionResponseModel response = new DrugExclusionResponseModel();
		List<String> errors = null;
		if (ex instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException methodArgEx = (MethodArgumentNotValidException) ex;
			errors = methodArgEx.getBindingResult().getAllErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
		} else if (ex instanceof ConstraintViolationException) {
			ConstraintViolationException cve = (ConstraintViolationException) ex;
			errors = cve.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
					.collect(Collectors.toList());
		}
		response.setErrors(errors);
		return response;
	}
	
	public DrugExclusionResponseModel populateInvalidResponse(ConstraintViolationException exception) {
		DrugExclusionResponseModel response = new DrugExclusionResponseModel();
		List<String> errors = exception.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
				.collect(Collectors.toList());
		response.setErrors(errors);
		return response;
	}

	public DrugExclusionResponseModel populateInvalidResponse(AdminException exception) {
		DrugExclusionResponseModel response = new DrugExclusionResponseModel();
		List<String> errorMsg = new ArrayList<>();
		errorMsg.add(exception.getMessage());
		response.setErrors(errorMsg);
		return response;
	}

	public DrugExclusionResponseModel populateUnAuthorizedResponse(AccessDeniedException ex) {
		DrugExclusionResponseModel response = new DrugExclusionResponseModel();
		List<String> errorMsg = new ArrayList<>();
		errorMsg.add(ex.getMessage());
		response.setErrors(errorMsg);
		return response;
	}

	public DrugExclusionResponseModel populateFailedResponse() {
		DrugExclusionResponseModel response = new DrugExclusionResponseModel();
		List<String> errorMsg = new ArrayList<>();
		errorMsg.add(HttpStatus.INTERNAL_SERVER_ERROR.name());
		response.setErrors(errorMsg);
		return response;
	}

	public Page<DrugExclusionMetaDataResponseModel> getDrugExclusionMetadataWithPagination(int pageNumber,
			int recordSize, DrugExclusionMetaDataRequestModel requestModel) {
		log.info("Page Number :- {} Record Size :- {}", pageNumber, recordSize);
		return drugExclusionSpecification.getDrugExclusionMetadataWithPagination(pageNumber, recordSize, requestModel);
	}

	@Transactional(value = "BusinessRulesTransactionManager")
	public void deleteExclusionType(String exclusionType, String exclusionAsscId) throws AdminException {
		Long id = Long.parseLong(exclusionAsscId);
		Long exclusionId;
		if (exclusionType.equalsIgnoreCase(ExclusionType.NETWORK_EXCLUSION.value())) {
			exclusionId = networkExclusionService.deleteNetworkExclusionAssc(id);
			deleteExcFromListingTable(exclusionId, id, ExclusionType.NETWORK_EXCLUSION.value());
		} else if (exclusionType.equalsIgnoreCase(ExclusionType.HIGH_COST_EXCLUSION.value())) {
			exclusionId = deleteHighCostDrugExclusion(id);
			deleteExcFromListingTable(exclusionId, id, ExclusionType.HIGH_COST_EXCLUSION.value());
		} else if (exclusionType.equalsIgnoreCase(ExclusionType.PROVIDER_EXCLUSION.value())) {
			exclusionId = providerExclusionService.deleteProviderExclusionAss(id);
			deleteExcFromListingTable(exclusionId, id, ExclusionType.PROVIDER_EXCLUSION.value());
		} else if (exclusionType.equalsIgnoreCase(ExclusionType.SPECIALITY_EXCLUSION.value())) {
			exclusionId = specialityExclusionService.deleteSpecialityExclusion(id);
			deleteExcFromListingTable(exclusionId, id, ExclusionType.SPECIALITY_EXCLUSION.value());
		} else {
			throw new AdminException("exclusionType is not found or exists.");
		}
		updateLastUpdateDateInDrugExclusionMetadata(exclusionId);
	}

	private void deleteExcFromListingTable(Long exclusionId, Long exclusionAssId, String exclusionType) {
		try {
			Long payerId = Long.valueOf(UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication()));
			exclusionAsscTypeListRepository.deleteByExclusionIdAndExclusionAsscIdAndExclusionTypeAndPayerId(exclusionId,
					exclusionAssId, exclusionType, payerId);
		} catch (Exception e) {
			log.error("Exception: ", e);
		}
	}

	@Transactional(value = "BusinessRulesTransactionManager")
	public void deleteDrugExclusionMetadata(Long exclusionId) throws AdminException {
		Optional<DrugExclusionMetadata> drugExclusionMetadataOpt = drugExclusionMetadataRepository
				.findById(exclusionId);
		if (drugExclusionMetadataOpt.isPresent()) {
			DrugExclusionMetadata drugExclusionMetadata = drugExclusionMetadataOpt.get();
			drugExclusionMetadata.setIsDeleted(Boolean.TRUE);
			drugExclusionMetadata.setLastUpdateDate(new Date());
			drugExclusionMetadata
					.setDeletedBy(UserInfoUtil.getUsername(SecurityContextHolder.getContext().getAuthentication()));
			drugExclusionMetadataRepository.save(drugExclusionMetadata);
			manageChildrenTables(exclusionId);
			return;
		}
		throw new AdminException(ExclusionMessages.EXCLUSIONID_NOT_FOUND.value());
	}

	private void manageChildrenTables(Long exclusionId) {
		deleteHighCost(exclusionId);
		deleteNetwork(exclusionId);
		deleteProvider(exclusionId);
		deleteSpeciality(exclusionId);
		deleteExclusionAsscTypeList(exclusionId);
		deleteDrugs(exclusionId);
	}

	private void deleteDrugs(Long exclusionId) {
		Optional<List<DrugExclusionDetails>> drugExclusionDetailsOptional = drugExclusionDetailsRepository
				.findByExclusionId(exclusionId);
		if (drugExclusionDetailsOptional.isPresent()) {
			List<DrugExclusionDetails> updatedDrugList = drugExclusionDetailsOptional.get().stream().map(drug -> {
				drug.setIsDeleted(true);
				drug.setLastUpdateDate(new Date());
				drug.setDeletedBy(UserInfoUtil.getAccName(SecurityContextHolder.getContext().getAuthentication()));
				return drug;
			}).collect(Collectors.toList());
			drugExclusionDetailsRepository.saveAll(updatedDrugList);
		}
	}

	private void deleteExclusionAsscTypeList(Long exclusionId) {
		exclusionAsscTypeListRepository.deleteAllByExclusionId(exclusionId);
	}

	private void deleteSpeciality(Long exclusionId) {
		Optional<List<SpecialityExclusionAssc>> specialityOpt = specialityExclusionAsscRepository
				.findByExclusionId(exclusionId);
		if (specialityOpt.isPresent()) {
			List<SpecialityExclusionAssc> updatedSpecialityList = specialityOpt.get().stream().map(speciality -> {
				speciality.setIsEnabled(false);
				speciality.setLastUpdateDate(new Date());
				return speciality;
			}).collect(Collectors.toList());
			specialityExclusionAsscRepository.saveAll(updatedSpecialityList);
		}
	}

	private void deleteProvider(Long exclusionId) {
		Optional<List<ProviderExclusionAssc>> providerOpt = providerExclusionAsscRepository
				.findByExclusionId(exclusionId);
		if (providerOpt.isPresent()) {
			List<ProviderExclusionAssc> updatedProviderList = providerOpt.get().stream().map(provider -> {
				provider.setIsEnabled(false);
				provider.setLastUpdateDate(new Date());
				return provider;
			}).collect(Collectors.toList());
			providerExclusionAsscRepository.saveAll(updatedProviderList);
		}
	}

	private void deleteNetwork(Long exclusionId) {
		Optional<List<NetworkExclusionAssc>> networkOpt = networkExclusionAsscRepository.findByExclusionId(exclusionId);
		if (networkOpt.isPresent()) {
			List<NetworkExclusionAssc> updatedNetworkList = networkOpt.get().stream().map(network -> {
				network.setIsEnabled(false);
				network.setLastUpdateDate(new Date());
				return network;
			}).collect(Collectors.toList());
			networkExclusionAsscRepository.saveAll(updatedNetworkList);
		}
	}

	private void deleteHighCost(Long exclusionId) {
		Optional<HighCostExclusionAssc> highCostOpt = highCostExclusionAsscRepository.findByExclusionId(exclusionId);
		if (highCostOpt.isPresent()) {
			highCostOpt.get().setIsEnabled(Boolean.FALSE);
			highCostOpt.get().setLastUpdateDate(new Date());
			highCostExclusionAsscRepository.save(highCostOpt.get());
		}
	}

	private void validateDuplicateExclusionNetworks(List<ExclusionTypeRequestModel> exclusionTypeDetails)
			throws AdminException {
		Set<String> seenExclusionNetworks = new HashSet<>();
		List<String> duplicateNetworks = exclusionTypeDetails.stream()
				.filter(exclusion -> exclusion.getExclusionType()
						.equalsIgnoreCase(ExclusionType.NETWORK_EXCLUSION.value()))
				.map(ExclusionTypeRequestModel::getExclusionNetwork)
				.filter(exclusionNetwork -> exclusionNetwork != null && !seenExclusionNetworks.add(exclusionNetwork))
				.collect(Collectors.toList());

		if (!duplicateNetworks.isEmpty()) {
			throw new AdminException(
					ExclusionMessages.DUPLICATE_EXCLUSION_NETWORK.value() + duplicateNetworks.toString());
		}
	}

	private void addNewNetworkExclusionAssc(String exclusionNetwork, Long exclusionId, Long payerId) {
		Long networkId = Long.parseLong(exclusionNetwork);
		NetworkExclusionAssc networkExclusionAssc = new NetworkExclusionAssc(networkId, exclusionId, new Date());
		NetworkExclusionAssc savedExclusionAssc = networkExclusionAsscRepository.save(networkExclusionAssc);
		String networkName = networkExclusionService.getProviderNetworkName(networkId, payerId);
		networkExclusionService.saveNetworkExclusionInExclusionAsscTypeList(networkExclusionAssc, payerId, networkName);
		auditLogService.addDataInAuditLog(AuditUpdatedType.INSERT, savedExclusionAssc.getNetworkExclusionAsscId(),
				EntitiesName.NETWORK_EXCLUSION_ASSC, savedExclusionAssc);
	}

	private void addNewHighCost(Long exclusionId, Long payerId, Date date) {
		HighCostExclusionAssc highCostExclusionAssc = populateHighCostExclusionAssc(exclusionId, payerId, date);
		highCostExclusionAsscRepository.save(highCostExclusionAssc);
		saveHighCostType(highCostExclusionAssc);
		auditLogService.addDataInAuditLog(AuditUpdatedType.INSERT, highCostExclusionAssc.getHighCostExclusionAsscId(),
				EntitiesName.HIGH_COST_EXCLUSION_ASSC, highCostExclusionAssc);
	}

	private void addNewProviderExclusionAssc(String exclusionProvider, String exclusionProviderName, Long exclusionId,
			Long payerId) {
		ProviderExclusionAssc providerExclusionAssc = new ProviderExclusionAssc();
		providerExclusionAssc.setExclusionId(exclusionId);
		providerExclusionAssc.setProviderId(Long.parseLong(exclusionProvider));
		providerExclusionAssc.setProviderName(exclusionProviderName);
		providerExclusionAssc.setPayerId(payerId);
		providerExclusionAssc.setLastUpdateDate(new Date());
		ProviderExclusionAssc savedExclusionAssc = providerExclusionAsscRepository.save(providerExclusionAssc);
		providerExclusionService.saveProviderExclusionInExclusionAsscTypeList(savedExclusionAssc);
		auditLogService.addDataInAuditLog(AuditUpdatedType.INSERT, savedExclusionAssc.getProviderExclusionAsscId(),
				EntitiesName.PROVIDER_EXCLUSION_ASSC, savedExclusionAssc);
	}

	private void addNewSpecialityExclusionAssc(String exclusionSpeciality, Long exclusionId, Long payerId) {
		SpecialityExclusionAssc specialityExclusionAssc = new SpecialityExclusionAssc();
		specialityExclusionAssc.setExclusionId(exclusionId);
		specialityExclusionAssc.setSpecialityId(new BigDecimal(exclusionSpeciality));
		specialityExclusionAssc.setLastUpdateDate(new Date());
		SpecialityExclusionAssc savedExclusionAssc = specialityExclusionAsscRepository.save(specialityExclusionAssc);
		specialityExclusionService.saveSpecialityExclusionInExclusionAsscTypeList(savedExclusionAssc, payerId);
		auditLogService.addDataInAuditLog(AuditUpdatedType.INSERT, savedExclusionAssc.getSpecialityExclusionAsscId(),
				EntitiesName.SPECIALITY_EXCLUSION_ASSC, savedExclusionAssc);
	}

	@Transactional(value = "BusinessRulesTransactionManager")
	public void addDrugExclusionDrugDetails(DrugExclusionDrugDetailsRequestModel requestModel, Long exclusionId)
			throws AdminException {
		Optional<DrugExclusionMetadata> drugExclusionMetadataOptional = drugExclusionMetadataRepository
				.findByExclusionIdAndIsDeleted(exclusionId, false);
		if (!drugExclusionMetadataOptional.isPresent()) {
			throw new AdminException(ExclusionMessages.EXCLUSIONID_NOT_FOUND.value());
		}
		DrugService drugService = getDrugServiceDetail(requestModel.getDrugCode());
		if (drugService == null) {
			throw new AdminException("DrugCode is not found.");
		}
		Optional<DrugExclusionDetails> drugExclusionDetailsOptional = drugExclusionDetailsRepository
				.findByExclusionIdAndWaseelDrugIdAndRegistrationNumberAndIsDeleted(exclusionId,
						drugService.getWaseelDrugId(), requestModel.getDrugCode(), false);
		if (drugExclusionDetailsOptional.isPresent()) {
			throw new AdminException("Drug details already exists.");
		}
		DrugExclusionDetails addedDrugExclusionDetails = saveDataInDrugExclusionDetailsTable(requestModel, exclusionId,
				drugService);
		updateLastUpdateTimeInDrugExclusionMetadata(drugExclusionMetadataOptional.get());
		auditLogService.addDataInAuditLog(AuditUpdatedType.INSERT, addedDrugExclusionDetails.getExclusionId(),
				EntitiesName.DRUG_EXCLUSION_DETAILS, addedDrugExclusionDetails);
	}

	private void updateLastUpdateTimeInDrugExclusionMetadata(DrugExclusionMetadata drugExclusionMetadata) {
		drugExclusionMetadata.setLastUpdateDate(new Date());
		drugExclusionMetadataRepository.save(drugExclusionMetadata);
	}

	private DrugExclusionDetails saveDataInDrugExclusionDetailsTable(DrugExclusionDrugDetailsRequestModel requestModel,
			Long exclusionId, DrugService drugService) {
		Long waseelDrugId = drugService.getWaseelDrugId();
		String drugCode = requestModel.getDrugCode();
		Optional<DrugExclusionDetails> alreadyExistsDrugExclusionDetailsOpt = drugExclusionDetailsRepository
				.findByExclusionIdAndWaseelDrugIdAndRegistrationNumberAndIsDeleted(exclusionId, waseelDrugId, drugCode,
						true);
		DrugExclusionDetails drugExclusionDetails;
		if (alreadyExistsDrugExclusionDetailsOpt.isPresent()) {
			drugExclusionDetails = alreadyExistsDrugExclusionDetailsOpt.get();
			drugExclusionDetails.setIsDeleted(false);
			drugExclusionDetails.setLastUpdateDate(new Date());
			drugExclusionDetails.setDeletedBy(null);
		} else {
			String scientificCode = !StringUtils.isBlank(drugService.getScientificCode())
					? drugService.getScientificCode()
					: "UNDEFINED";
			drugExclusionDetails = new DrugExclusionDetails(exclusionId, waseelDrugId, drugCode,
					requestModel.getDrugName(), requestModel.getGenericName(), scientificCode, requestModel.getPrice(),
					new Date());
			drugExclusionDetails.setIsDeleted(Boolean.FALSE);
		}
		return drugExclusionDetailsRepository.save(drugExclusionDetails);
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

	private Long getDrugListId() {
		return drugServiceMetaDataRepository
				.findFirstByEffectiveDateLessThanEqualOrderByEffectiveDateDescUploadDateTimeDesc(new Date())
				.map(DrugServiceMetaData::getDrugListId).orElse(0L);
	}

	public void addHighCostDrugExclusion(String exclusionId) throws AdminException {
		Optional<DrugExclusionMetadata> drugExclusionMetadataOpt = drugExclusionMetadataRepository
				.findById(Long.valueOf(exclusionId));
		if (drugExclusionMetadataOpt.isPresent()) {
			Optional<HighCostExclusionAssc> highCostExclusionAssc = highCostExclusionAsscRepository
					.findByExclusionId(Long.valueOf(exclusionId));
			if (highCostExclusionAssc.isPresent()) {
				if (highCostExclusionAssc.get().getIsEnabled().equals(true)) {
					throw new AdminException(ExclusionMessages.HIGH_COST_ALREADY_EXITS.value());
				} else if (highCostExclusionAssc.get().getIsEnabled().equals(false)) {
					highCostExclusionAssc.get().setLastUpdateDate(new Date());
					highCostExclusionAssc.get().setIsEnabled(true);
					highCostExclusionAsscRepository.save(highCostExclusionAssc.get());
					saveHighCostType(highCostExclusionAssc.get());
					auditLogService.addDataInAuditLog(AuditUpdatedType.UPDATE,
							highCostExclusionAssc.get().getHighCostExclusionAsscId(),
							EntitiesName.HIGH_COST_EXCLUSION_ASSC, highCostExclusionAssc);
				}
			} else {
				Long payerId = Long
						.valueOf(UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication()));
				HighCostExclusionAssc highCostExclusionAsscObj = highCostExclusionAsscRepository
						.save(populateHighCostExclusionAssc(Long.valueOf(exclusionId), payerId, new Date()));
				saveHighCostType(highCostExclusionAsscObj);
				auditLogService.addDataInAuditLog(AuditUpdatedType.INSERT,
						highCostExclusionAsscObj.getHighCostExclusionAsscId(), EntitiesName.HIGH_COST_EXCLUSION_ASSC,
						highCostExclusionAsscObj);
			}
			updateLastUpdateDateInDrugExclusionMetadata(drugExclusionMetadataOpt.get());
		} else {
			throw new AdminException(ExclusionMessages.EXCLUSIONID_NOT_FOUND.value());
		}
	}

	@Transactional(value = "BusinessRulesTransactionManager")
	public void deleteDrugExclusionDrugDetails(Long drugExclusionDetailsId) throws AdminException {
		Optional<DrugExclusionDetails> drugExclusionDetailsOptional = drugExclusionDetailsRepository
				.findByDrugExclusionDetailsIdAndIsDeleted(drugExclusionDetailsId, false);
		if (drugExclusionDetailsOptional.isPresent()) {
			DrugExclusionDetails drugExclusionDetails = drugExclusionDetailsOptional.get();
			drugExclusionDetails.setIsDeleted(true);
			drugExclusionDetails
					.setDeletedBy(UserInfoUtil.getAccName(SecurityContextHolder.getContext().getAuthentication()));
			drugExclusionDetails.setLastUpdateDate(new Date());
			DrugExclusionDetails updatedDrugExclusionDetails = drugExclusionDetailsRepository
					.save(drugExclusionDetails);
			Optional<DrugExclusionMetadata> drugExclusionMetadataOptional = drugExclusionMetadataRepository
					.findByExclusionIdAndPayerIdAndIsDeleted(drugExclusionDetails.getExclusionId(),
							Long.parseLong(
									UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication())),
							false);
			if (drugExclusionMetadataOptional.isPresent())
				updateLastUpdateTimeInDrugExclusionMetadata(drugExclusionMetadataOptional.get());
			auditLogService.addDataInAuditLog(AuditUpdatedType.DELETE,
					updatedDrugExclusionDetails.getDrugExclusionDetailsId(), EntitiesName.DRUG_EXCLUSION_DETAILS,
					updatedDrugExclusionDetails);
			log.info("Drug Exclusion Drug detail successfully Deleted for drugExclusionDetailsId: {}",
					drugExclusionDetailsId);
			return;
		}
		log.info("Drug Exclusion Drug detail Not found or exists for drugExclusionDetailsId: {}",
				drugExclusionDetailsId);
		throw new AdminException("DrugExclusionDetailsId is not found or exists.");
	}

	public Long deleteHighCostDrugExclusion(Long exclusionId) throws AdminException {
		return highCostExclusionAsscRepository.findByHighCostExclusionAsscIdAndIsEnabled(exclusionId, true)
				.map(highCostExclusionAssc -> {
					highCostExclusionAssc.setIsEnabled(false);
					highCostExclusionAssc.setLastUpdateDate(new Date());
					highCostExclusionAsscRepository.save(highCostExclusionAssc);
					auditLogService.addDataInAuditLog(AuditUpdatedType.DELETE, exclusionId,
							EntitiesName.HIGH_COST_EXCLUSION_ASSC, null);
					return highCostExclusionAssc.getExclusionId();
				}).orElseThrow(() -> new AdminException("exclusionAsscId is not found."));
	}

	private void updateLastUpdateDateInDrugExclusionMetadata(Long exclusionId) {
		Long payerId = Long.parseLong(UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication()));
		drugExclusionMetadataRepository.findByExclusionIdAndPayerIdAndIsDeleted(exclusionId, payerId, false)
				.ifPresent(drugExclusionMetadata -> {
					drugExclusionMetadata.setLastUpdateDate(new Date());
					drugExclusionMetadataRepository.save(drugExclusionMetadata);
				});
	}

	private void updateLastUpdateDateInDrugExclusionMetadata(DrugExclusionMetadata drugExclusionMetadata) {
		if (drugExclusionMetadata != null) {
			drugExclusionMetadata.setLastUpdateDate(new Date());
			drugExclusionMetadataRepository.save(drugExclusionMetadata);
		}
	}

	public Page<DrugExclusionDrugDetailsModel> getAllDrugExclusionDrugsDetails(
			DrugExclusionDrugDetailsModel drugDetailsModel) {
		log.info("ExclusionId {},PageNumber {}, RecordSize {}", drugDetailsModel.getExclusionId(),
				drugDetailsModel.getPageNumber(), drugDetailsModel.getRecordSize());
		return drugExclusionDrugDetailsSpecification.findDrugExclusionDrugDetailsWithPagination(drugDetailsModel);
	}

	public Page<ExclusionTypeSearchResponseModel> getExclusionTypes(ExclusionTypeSearchModel exclusionTypeSearchModel,
			Long exclusionId) {
		log.info("ExclusionId {},PageNumber {}, RecordSize {}", exclusionId, exclusionTypeSearchModel.getPageNumber(),
				exclusionTypeSearchModel.getRecordSize());
		return drugExclusionTypesSpecification.findDrugExclusionTypes(exclusionTypeSearchModel, exclusionId);
	}

	private void saveHighCostType(HighCostExclusionAssc highCostExclusionAssc) {
		if (highCostExclusionAssc != null) {
			if (!exclusionAsscTypeListRepository
					.findByExclusionIdAndExclusionAsscIdAndExclusionTypeAndPayerId(
							highCostExclusionAssc.getExclusionId(), highCostExclusionAssc.getHighCostExclusionAsscId(),
							ExclusionType.HIGH_COST_EXCLUSION.value(), highCostExclusionAssc.getPayerId())
					.isPresent()) {
				ExclusionAsscTypeList highCost = new ExclusionAsscTypeList();
				highCost.setExclusionId(highCostExclusionAssc.getExclusionId());
				highCost.setExclusionType(ExclusionType.HIGH_COST_EXCLUSION.value());
				highCost.setExclusionAsscId(highCostExclusionAssc.getHighCostExclusionAsscId());
				highCost.setPayerId(highCostExclusionAssc.getPayerId());
				exclusionAsscTypeListRepository.save(highCost);
			}
		}
	}

	public ExclusionTypeSearchResponseModel getExclusionName(Long exclusionId) throws AdminException {
		long payerId = Long.parseLong(UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication()));
		Optional<DrugExclusionMetadata> drugExclusionMetadataOpt = drugExclusionMetadataRepository
				.findByExclusionIdAndPayerIdAndIsDeleted(exclusionId, payerId, false);
		if (drugExclusionMetadataOpt.isPresent()) {
			DrugExclusionMetadata drugExclusionMetadata = drugExclusionMetadataOpt.get();
			return new ExclusionTypeSearchResponseModel(null, drugExclusionMetadata.getExclusionName());
		}
		throw new AdminException(DrugExclusionMessage.NO_LIST.value());
	}
}
