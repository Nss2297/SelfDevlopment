package com.waseel.pbm.payercustomizationservice.service;

import com.waseel.pbm.payercustomizationservice.enums.*;
import com.waseel.pbm.payercustomizationservice.exceptions.PayerCustomizationException;
import com.waseel.pbm.payercustomizationservice.model.CustomizationListingResponse;
import com.waseel.pbm.payercustomizationservice.model.CustomizationRequestModel;
import com.waseel.pbm.payercustomizationservice.model.CustomizationSearchModel;
import com.waseel.pbm.payercustomizationservice.model.DeleteResponseModel;
import com.waseel.pbm.payercustomizationservice.persist.*;
import com.waseel.pbm.payercustomizationservice.repository.*;
import com.waseel.pbm.payercustomizationservice.service.management.CustomizationRequestsAuditLogService;
import com.waseel.pbm.payercustomizationservice.specification.CustomizationRequestSpecification;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Service
public class CustomizationListService {
    @Autowired
    private CustomizationRequestSpecification customizationRequestSpecification;
    @Autowired
    private CustomizationRequestMetadataRepository customizationRequestMetadataRepository;
    @Autowired
    private CustomizationRequestDetailsRepository customizationRequestDetailsRepository;
    @Autowired
    private PCDrugToDiagnosisRepository pcDrugToDiagnosisRepository;
    @Autowired
    private PCAgeRepository pcAgeRepository;
    @Autowired
    private PCDrugToDrugRepository pcDrugToDrugRepository;
    @Autowired
    private PCDuplicateTherapyRepository pcDuplicateTherapyRepository;
    @Autowired
    private PCGenderRepository pcGenderRepository;
    @Autowired
    private PCQuantityLimitCheckRepository pcQuantityLimitCheckRepository;
    @Autowired
    private CustomizationResponseService customizationResponseService;
    @Autowired
    private CustomizationRequestsAuditLogService customizationRequestsAuditLogService;

    public Page<CustomizationListingResponse> getCustomizationRequests(
            CustomizationSearchModel customizationSearchModel) {
        return customizationRequestSpecification.getCustomizationResponsesPaginated(customizationSearchModel);
    }

    @Transactional
    public DeleteResponseModel deleteCustomizationRequest(Long customizationRequestId) {
        Optional<CustomizationRequestMetadata> metadata = customizationRequestMetadataRepository
                .findByCustomizationRequestsIdAndIsDeleted(customizationRequestId, false);
        DeleteResponseModel deleteResponseModel = new DeleteResponseModel();
        if (metadata.isPresent()) {
            metadata.get().setDeleted(true);
            Optional<CustomizationRequestDetail> requestDetails = customizationRequestDetailsRepository
                    .findByCustomizationRequestsIdAndCustomizationKey(customizationRequestId,
                            CustomizationRequestDetailKeys.CUSTOMIZABLE.toString());
            if (requestDetails.isPresent()) {
                requestDetails.get().setCustomizationValue("1");
                customizationRequestDetailsRepository.save(requestDetails.get());
            }
            customizationRequestMetadataRepository.save(metadata.get());
            String change = metadata.get().toString();
            customizationRequestsAuditLogService.populateCustomizationRequestsAudit(customizationRequestId,
                    EntityNames.CUSTOMIZATION_REQUEST_METADATA.name(), "Delete", change);
        } else {
            deleteResponseModel.setErrors("Customization request does not exist");
        }
        deleteResponseModel.setCustomizationRequestId(customizationRequestId);
        return deleteResponseModel;
    }

    // @Transactional
    public void updateCustomizationRequest(CustomizationRequestModel customizationRequestModel,
                                           Long customizationRequestId) throws PayerCustomizationException {
        String inputStatus = customizationRequestModel.getStatus();
        if (!inputStatus.equals(CustomizationRequestStatus.ACCEPTED.value())
                && !inputStatus.equals(CustomizationRequestStatus.REJECTED.value())) {
            throw new PayerCustomizationException(
                    customizationResponseService.invalidCustomizationResponse("Status should be Accepted Or Rejected"));
        }
        Optional<CustomizationRequestMetadata> optionalCustomizationRequestMetadata = customizationRequestMetadataRepository
                .findById(customizationRequestId);
        if (optionalCustomizationRequestMetadata.isPresent()) {
            CustomizationRequestMetadata customizationRequestMetadata = optionalCustomizationRequestMetadata.get();
            customizationRequestMetadata.setStatus(inputStatus);
            customizationRequestMetadata.setRejectionReason(customizationRequestModel.getRejectionReason());
            customizationRequestMetadata.setLastUpdatedDate(Timestamp.from(Instant.now()));
            Optional<CustomizationRequestDetail> requestDetails = customizationRequestDetailsRepository
                    .findByCustomizationRequestsIdAndCustomizationKey(customizationRequestId,
                            CustomizationRequestDetailKeys.CUSTOMIZABLE.toString());
            if (requestDetails.isPresent()) {
                requestDetails.get().setCustomizationValue("1");
                if (inputStatus.equals(CustomizationRequestStatus.ACCEPTED.value())) {
                    requestDetails.get().setCustomizationValue("0");
                }
                customizationRequestDetailsRepository.save(requestDetails.get());
            }
            if (inputStatus.equals(CustomizationRequestStatus.ACCEPTED.value())) {
                insertDataIntoRespectiveModules(customizationRequestMetadata);
            }
            customizationRequestMetadataRepository.save(customizationRequestMetadata);
            return;
        }
        throw new PayerCustomizationException(
                customizationResponseService.invalidCustomizationResponse("Customization request does not exists"));
    }

    private void insertDataIntoRespectiveModules(CustomizationRequestMetadata customizationRequestMetadata) {
        String moduleName = customizationRequestMetadata.getModuleName();
        if (moduleName.equals(CustomizationModuleName.DRUG_TO_DISEASE_INTERACTION_RULE.value())) {
            setPcDrugToDiagnosisData(customizationRequestMetadata);
        } else if (moduleName.equals(CustomizationModuleName.DRUG_TO_AGE_INTERACTION_RULE.value())) {
            setPcAgeData(customizationRequestMetadata);
        } else if (moduleName.equals(CustomizationModuleName.DUPLICATE_THERAPY_RULE.value())) {
            setPcDuplicateTherapyData(customizationRequestMetadata);
        } else if (moduleName.equals(CustomizationModuleName.DRUG_TO_GENDER_INTERACTION_RULE.value())) {
            setPcGenderData(customizationRequestMetadata);
        } else if (moduleName.equals(CustomizationModuleName.DRUG_TO_DRUG_INTERACTION_RULE.value())) {
            setPcDrugToDrugData(customizationRequestMetadata);
        } else if (moduleName.equals(CustomizationModuleName.QUANTITY_LIMIT_CHECK_RULE.value())) {
            setPcQuantityLimitCheckData(customizationRequestMetadata);
        }
    }

    private void setPcQuantityLimitCheckData(CustomizationRequestMetadata customizationRequestMetadata) {
        String icdCode = fetchValueFromDetails(customizationRequestMetadata,
                CustomizationRequestDetailKeys.ICD_CODE.name());
        PCQuantityLimitCheck pcQuantityLimitCheck = getPCQuantityLimitCheck(customizationRequestMetadata, icdCode);
        PCCommonId commonId = pcQuantityLimitCheck.getId() != null ? pcQuantityLimitCheck.getId() : new PCCommonId();
        commonId.setPayerId(customizationRequestMetadata.getPayerId());
        commonId.setModuleName(ModuleName.ALL.value());
        commonId.setServiceCode(customizationRequestMetadata.getDrugCode());
        commonId.setIcdCode(icdCode);
        pcQuantityLimitCheck.setId(commonId);
        String fromAgeInDays = fetchValueFromDetails(customizationRequestMetadata,
                CustomizationRequestDetailKeys.FROM_AGE_IN_DAYS.name());
        if (!StringUtils.isBlank(fromAgeInDays)) {
            pcQuantityLimitCheck.setFromAgeInDays(Long.parseLong(fromAgeInDays));
        }
        String toAgeInDays = fetchValueFromDetails(customizationRequestMetadata,
                CustomizationRequestDetailKeys.TO_AGE_IN_DAYS.name());
        if (!StringUtils.isBlank(toAgeInDays)) {
            pcQuantityLimitCheck.setToAgeInDays(Long.parseLong(toAgeInDays));
        }
        pcQuantityLimitCheck.setDrugType(
                fetchValueFromDetails(customizationRequestMetadata, CustomizationRequestDetailKeys.DRUG_TYPE.name()));
        String maxValuePerDay = fetchValueFromDetails(customizationRequestMetadata,
                CustomizationRequestDetailKeys.MAX_VALUE_PER_DAY.name());
        if (!StringUtils.isBlank(maxValuePerDay)) {
            pcQuantityLimitCheck.setMaxValuePerDay(Integer.parseInt(maxValuePerDay));
        }
        String productPackageSize = fetchValueFromDetails(customizationRequestMetadata,
                CustomizationRequestDetailKeys.PRODUCT_PACKAGE_SIZE.name());
        if (!StringUtils.isBlank(productPackageSize)) {
            pcQuantityLimitCheck.setProductPackageSize(Integer.parseInt(productPackageSize));
        }
        pcQuantityLimitCheck.setUnitType(
                fetchValueFromDetails(customizationRequestMetadata, CustomizationRequestDetailKeys.UNIT_TYPE.name()));
        pcQuantityLimitCheck.setLastUpdatedDateTime(Timestamp.from(Instant.now()));
        Long id = pcQuantityLimitCheckRepository.findLatestId();
        if (id != null) {
            Long newId = ++id;
            pcQuantityLimitCheck.setSeqId(newId);
            pcQuantityLimitCheck.setRuleId(PCRule.PC_QUANTITY_LIMIT_CHECK.value() + "_" + newId);
        }
        pcQuantityLimitCheckRepository.save(pcQuantityLimitCheck);
    }

    private PCQuantityLimitCheck getPCQuantityLimitCheck(CustomizationRequestMetadata request, String icdCode) {
        Optional<PCQuantityLimitCheck> optionalPCQuantityLimitCheck = pcQuantityLimitCheckRepository
                .findByPayerIdAndServiceCodeAndIcdCodeAndModuleName(request.getPayerId(), request.getDrugCode(),
                        icdCode, ModuleName.ALL.value());
        if (optionalPCQuantityLimitCheck.isPresent()) {
            return optionalPCQuantityLimitCheck.get();
        }
        PCQuantityLimitCheck pcQuantityLimitCheck = new PCQuantityLimitCheck();
        Long id = pcQuantityLimitCheckRepository.findLatestId();
        Long newId = id != null ? ++id : 1L;
        pcQuantityLimitCheck.setSeqId(newId);
        pcQuantityLimitCheck.setRuleId(PCRule.PC_QUANTITY_LIMIT_CHECK.value() + "_" + newId);
        return pcQuantityLimitCheck;
    }

    private void setPcDrugToDrugData(CustomizationRequestMetadata customizationRequestMetadata) {
        String interactedServiceCode = fetchValueFromDetails(customizationRequestMetadata,
                CustomizationRequestDetailKeys.INTERACTED_DRUG_CODE.name());
        PcDrugToDrug pcDrugToDrug = getPcDrugToDrug(customizationRequestMetadata, interactedServiceCode);
        PCDrugCommonId commonId = pcDrugToDrug.getId() != null ? pcDrugToDrug.getId() : new PCDrugCommonId();
        commonId.setPayerId(customizationRequestMetadata.getPayerId());
        commonId.setModuleName(ModuleName.ALL.value());
        commonId.setServiceCode(customizationRequestMetadata.getDrugCode());
        commonId.setInteractedServiceCode(interactedServiceCode);
        pcDrugToDrug.setId(commonId);
        pcDrugToDrug.setServiceStatus(ServiceStatus.APPROVED.value());
        pcDrugToDrug.setLastUpdatedDateTime(Timestamp.from(Instant.now()));
        pcDrugToDrugRepository.save(pcDrugToDrug);
    }

    private PcDrugToDrug getPcDrugToDrug(CustomizationRequestMetadata request, String interactedServiceCode) {
        Optional<PcDrugToDrug> optionalPcDrugToDrug = pcDrugToDrugRepository
                .findByPayerIdAndServiceCodeAndModuleNameAndInteractedServiceCode(request.getPayerId(),
                        request.getDrugCode(), ModuleName.ALL.value(), interactedServiceCode);
        if (optionalPcDrugToDrug.isPresent()) {
            return optionalPcDrugToDrug.get();
        }
        PcDrugToDrug pcDrugToDrug = new PcDrugToDrug();
        Long id = pcDrugToDrugRepository.findLatestId();
        Long newId = id != null ? ++id : 1L;
        pcDrugToDrug.setSeqId(newId);
        pcDrugToDrug.setRuleId(PCRule.PC_DRUG_TO_DRUG.value() + "_" + newId);
        return pcDrugToDrug;
    }

    private void setPcGenderData(CustomizationRequestMetadata customizationRequestMetadata) {
        PCGender pcGender = getPCGender(customizationRequestMetadata);
        pcGender.setPayerId(customizationRequestMetadata.getPayerId());
        pcGender.setModuleName(ModuleName.ALL.value());
        pcGender.setServiceCode(customizationRequestMetadata.getDrugCode());
        pcGender.setGender(
                fetchValueFromDetails(customizationRequestMetadata, CustomizationRequestDetailKeys.GENDER.name()));
        pcGender.setServiceStatus(ServiceStatus.APPROVED.value());
        pcGender.setLastUpdatedDateTime(Timestamp.from(Instant.now()));
        pcGenderRepository.save(pcGender);
    }

    private PCGender getPCGender(CustomizationRequestMetadata request) {
        Optional<PCGender> optionalPCGender = pcGenderRepository.findByPayerIdAndServiceCodeAndModuleName(
                request.getPayerId(), request.getDrugCode(), ModuleName.ALL.value());
        if (optionalPCGender.isPresent()) {
            return optionalPCGender.get();
        }
        PCGender pcGender = new PCGender();
        Long id = pcAgeRepository.findLatestId();
        Long newId = id != null ? ++id : 1L;
        pcGender.setSeqId(newId);
        pcGender.setRuleId(PCRule.PC_DRUG_TO_GENDER.value() + "_" + newId);
        return pcGender;
    }

    private void setPcDuplicateTherapyData(CustomizationRequestMetadata customizationRequestMetadata) {
        String interactedServiceCode = fetchValueFromDetails(customizationRequestMetadata,
                CustomizationRequestDetailKeys.INTERACTED_DRUG_CODE.name());
        PCDuplicateTherapy pcDuplicateTherapy = getPCDuplicateTherapy(customizationRequestMetadata,
                interactedServiceCode);
        PCDrugCommonId commonId = pcDuplicateTherapy.getId() != null ? pcDuplicateTherapy.getId()
                : new PCDrugCommonId();
        commonId.setPayerId(customizationRequestMetadata.getPayerId());
        commonId.setModuleName(ModuleName.ALL.value());
        commonId.setServiceCode(customizationRequestMetadata.getDrugCode());
        commonId.setInteractedServiceCode(interactedServiceCode);
        pcDuplicateTherapy.setId(commonId);
        pcDuplicateTherapy.setServiceStatus(ServiceStatus.APPROVED.value());
        pcDuplicateTherapy.setLastUpdatedDateTime(Timestamp.from(Instant.now()));
        pcDuplicateTherapyRepository.save(pcDuplicateTherapy);
    }

    private PCDuplicateTherapy getPCDuplicateTherapy(CustomizationRequestMetadata request,
                                                     String interactedServiceCode) {
        Optional<PCDuplicateTherapy> optionalPCDuplicateTherapy = pcDuplicateTherapyRepository
                .findByPayerIdAndServiceCodeAndModuleNameAndInteractedServiceCode(request.getPayerId(),
                        request.getDrugCode(), ModuleName.ALL.value(), interactedServiceCode);
        if (optionalPCDuplicateTherapy.isPresent()) {
            return optionalPCDuplicateTherapy.get();
        }
        PCDuplicateTherapy pcDuplicateTherapy = new PCDuplicateTherapy();
        Long id = pcDuplicateTherapyRepository.findLatestId();
        Long newId = id != null ? ++id : 1L;
        pcDuplicateTherapy.setSeqId(newId);
        pcDuplicateTherapy.setRuleId(PCRule.PC_DUPLICATE_THERAPY.value() + "_" + newId);
        return pcDuplicateTherapy;
    }

    private void setPcAgeData(CustomizationRequestMetadata customizationRequestMetadata) {
        PCAge pcAge = getPCAge(customizationRequestMetadata);
        pcAge.setPayerId(customizationRequestMetadata.getPayerId());
        pcAge.setModuleName(ModuleName.ALL.value());
        pcAge.setServiceCode(customizationRequestMetadata.getDrugCode());
        String fromAgeInDays = fetchValueFromDetails(customizationRequestMetadata,
                CustomizationRequestDetailKeys.FROM_AGE_IN_DAYS.name());
        if (!StringUtils.isBlank(fromAgeInDays)) {
            pcAge.setFromAgeInDays(Long.parseLong(fromAgeInDays));
        }
        String toAgeInDays = fetchValueFromDetails(customizationRequestMetadata,
                CustomizationRequestDetailKeys.TO_AGE_IN_DAYS.name());
        if (!StringUtils.isBlank(toAgeInDays)) {
            pcAge.setToAgeInDays(Long.parseLong(toAgeInDays));
        }
        pcAge.setServiceStatus(ServiceStatus.APPROVED.value());
        pcAge.setLastUpdatedDateTime(Timestamp.from(Instant.now()));
        pcAgeRepository.save(pcAge);
    }

    private PCAge getPCAge(CustomizationRequestMetadata request) {
        Optional<PCAge> optionalPCAge = pcAgeRepository.findByPayerIdAndServiceCodeAndModuleName(
                request.getPayerId(), request.getDrugCode(), ModuleName.ALL.value());
        if (optionalPCAge.isPresent()) {
            return optionalPCAge.get();
        }
        PCAge pcAge = new PCAge();
        Long id = pcAgeRepository.findLatestId();
        Long newId = id != null ? ++id : 1L;
        pcAge.setSeqId(newId);
        pcAge.setRuleId(PCRule.PC_DRUG_TO_AGE.value() + "_" + newId);
        return pcAge;
    }

    private void setPcDrugToDiagnosisData(CustomizationRequestMetadata customizationRequestMetadata) {
        String icdCode = fetchValueFromDetails(customizationRequestMetadata,
                CustomizationRequestDetailKeys.ICD_CODE.name());
        String rejCat = fetchValueFromDetails(customizationRequestMetadata,
                CustomizationRequestDetailKeys.REJECTION_CATEGORY.name());
        PCDrugToDiagnosis pcDrugToDiagnosis = getPCDrugToDiagnosis(customizationRequestMetadata, icdCode);
        pcDrugToDiagnosis.setPayerId(customizationRequestMetadata.getPayerId());
        pcDrugToDiagnosis.setModuleName(ModuleName.ALL.value());
        pcDrugToDiagnosis.setServiceCode(customizationRequestMetadata.getDrugCode());
        pcDrugToDiagnosis.setIcdCode(icdCode);
        pcDrugToDiagnosis.setCategoryOfApproval("AsPerPBMExperts");
        pcDrugToDiagnosis.setRejectionCategory(rejCat);
        pcDrugToDiagnosis.setServiceStatus(ServiceStatus.APPROVED.value());
        pcDrugToDiagnosis.setLastUpdatedDateTime(Timestamp.from(Instant.now()));
        pcDrugToDiagnosisRepository.save(pcDrugToDiagnosis);
    }

    private PCDrugToDiagnosis getPCDrugToDiagnosis(CustomizationRequestMetadata request, String icdCode) {
        Optional<PCDrugToDiagnosis> optionalPCDrugToDiagnosis = pcDrugToDiagnosisRepository
                .findByServiceCodeAndRejectionCategoryAndModuleNameAndIcdCodeAndPayerId(request.getDrugCode(),
                        ModuleName.ALL.value(), icdCode, request.getPayerId());
        if (optionalPCDrugToDiagnosis.isPresent()) {
            return optionalPCDrugToDiagnosis.get();
        }
        PCDrugToDiagnosis pcDrugToDiagnosis = new PCDrugToDiagnosis();
        Long id = pcDrugToDiagnosisRepository.findLatestId();
        Long newId = id != null ? ++id : 1L;
        pcDrugToDiagnosis.setSeqId(newId);
        pcDrugToDiagnosis.setRuleId(PCRule.PC_DRUG_TO_DIAGNOSIS_INDICATION_CONTRAINDICATION.value() + "_" + newId);
        return pcDrugToDiagnosis;
    }

    private String fetchValueFromDetails(CustomizationRequestMetadata customizationRequestMetadata, String key) {
        Optional<CustomizationRequestDetail> customizationRequestDetailOptional = customizationRequestMetadata
                .getCustomizationRequestDetailList().stream()
                .filter(customizationRequestDetail -> customizationRequestDetail.getCustomizationKey().equals(key))
                .findAny();
        if (customizationRequestDetailOptional.isPresent()) {
            return customizationRequestDetailOptional.get().getCustomizationValue();
        }
        return null;
    }
}