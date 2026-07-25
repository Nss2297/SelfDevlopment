package com.waseel.pbm.pbmadminservice.service;

import com.waseel.pbm.pbmadminservice.enums.AuditUpdatedType;
import com.waseel.pbm.pbmadminservice.enums.EntitiesName;
import com.waseel.pbm.pbmadminservice.enums.drugexclusion.ExclusionMessages;
import com.waseel.pbm.pbmadminservice.enums.drugexclusion.ExclusionType;
import com.waseel.pbm.pbmadminservice.exceptions.AdminException;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.provider.ProviderExclusionRequestModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.provider.ProviderExclusionResponseModel;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugExclusionMetadata;
import com.waseel.pbm.pbmadminservice.persist.businessrules.ExclusionAsscTypeList;
import com.waseel.pbm.pbmadminservice.persist.businessrules.ProviderExclusionAssc;
import com.waseel.pbm.pbmadminservice.persist.hira.AccountToAccountAssociation;
import com.waseel.pbm.pbmadminservice.persist.hira.SwitchAccount;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugExclusionMetadataRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.ExclusionAsscTypeListRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.ProviderExclusionAsscRepository;
import com.waseel.pbm.pbmadminservice.repository.hira.AccountToAccountAssociationRepository;
import com.waseel.pbm.pbmadminservice.repository.hira.SwitchAccountRepository;
import com.waseel.pbm.pbmadminservice.util.UserInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Service
public class ProviderExclusionService {
    private final Logger log = LoggerFactory.getLogger(ProviderExclusionService.class);

    @Autowired
    private ProviderExclusionAsscRepository providerExclusionAsscRepository;
    @Autowired
    private AuditLogService auditLogService;
    @Autowired
    private ExclusionAsscTypeListRepository exclusionAsscTypeListRepository;
    @Autowired
    private DrugExclusionMetadataRepository drugExclusionMetadataRepository;
    @Autowired
    private AccountToAccountAssociationRepository accountToAccountAssociationRepository;
    @Autowired
    private SwitchAccountRepository switchAccountRepository;

    public Long deleteProviderExclusionAss(Long id) throws AdminException {
        return providerExclusionAsscRepository.findByProviderExclusionAsscIdAndIsEnabled(id, true)
                .map(providerExclusionAssc -> {
                    providerExclusionAssc.setIsEnabled(false);
                    providerExclusionAssc.setLastUpdateDate(new Date());
                    providerExclusionAsscRepository.save(providerExclusionAssc);
                    auditLogService.addDataInAuditLog(AuditUpdatedType.DELETE, id, EntitiesName.PROVIDER_EXCLUSION_ASSC,
                            null);
                    return providerExclusionAssc.getExclusionId();
                }).orElseThrow(() -> new AdminException(ExclusionMessages.EXCLUSION_ASSC_ID_NOT_FOUND.value()));
    }

    public void saveProviderExclusionInExclusionAsscTypeList(ProviderExclusionAssc providerExclusionAssc) {
        if (providerExclusionAssc != null) {
            Optional<ExclusionAsscTypeList> exclusionAsscTypeListOpt = exclusionAsscTypeListRepository
                    .findByExclusionIdAndExclusionAsscIdAndExclusionTypeAndPayerId(providerExclusionAssc.getExclusionId(),
                            providerExclusionAssc.getProviderExclusionAsscId(), ExclusionType.PROVIDER_EXCLUSION.value(),
                            providerExclusionAssc.getPayerId());
            if (exclusionAsscTypeListOpt.isEmpty()) {
                ExclusionAsscTypeList exclusionAsscTypeList = new ExclusionAsscTypeList();
                exclusionAsscTypeList.setExclusionId(providerExclusionAssc.getExclusionId());
                exclusionAsscTypeList.setExclusionType(ExclusionType.PROVIDER_EXCLUSION.value());
                exclusionAsscTypeList.setExclusionAsscId(providerExclusionAssc.getProviderExclusionAsscId());
                exclusionAsscTypeList.setPayerId(providerExclusionAssc.getPayerId());
                exclusionAsscTypeList.setProviderId(providerExclusionAssc.getProviderId());
                exclusionAsscTypeList.setExclusionTypeName(providerExclusionAssc.getProviderName());
                exclusionAsscTypeListRepository.save(exclusionAsscTypeList);
            }
        }
    }

    @Transactional(value = "BusinessRulesTransactionManager")
    public ProviderExclusionResponseModel addProviderExclusion(
            ProviderExclusionRequestModel providerExclusionRequestModel, Long exclusionId) throws AdminException {
        Long payerId = getPayerIdFromAuthentication();
        String providerId = providerExclusionRequestModel.getProviderId();
        DrugExclusionMetadata drugExclusionMetadata = validateExclusionId(exclusionId, payerId);
        validateProviderId(providerId, payerId);
        validateProviderName(providerExclusionRequestModel.getProviderName(), providerId);
        ProviderExclusionAssc providerExclusionAssc = createOrUpdateProviderExclusionAssc(payerId, exclusionId,
                providerExclusionRequestModel);
        saveProviderExclusionInExclusionAsscTypeList(providerExclusionAssc, payerId);
        updateLastUpdateDateInDrugExclusionMetadata(drugExclusionMetadata);
        return prepareProviderExclusionResponse(providerExclusionAssc.getProviderExclusionAsscId());
    }

    private ProviderExclusionAssc createOrUpdateProviderExclusionAssc(Long payerId, Long exclusionId,
                                                                      ProviderExclusionRequestModel providerExclusionRequestModel) throws AdminException {
        Long providerId = Long.parseLong(providerExclusionRequestModel.getProviderId());
        Optional<ProviderExclusionAssc> providerExclusionAsscOpt = providerExclusionAsscRepository
                .findByProviderIdAndPayerIdAndExclusionId(providerId, payerId, exclusionId);
        if (providerExclusionAsscOpt.isPresent()) {
            ProviderExclusionAssc providerExclusionAssc = providerExclusionAsscOpt.get();
            if (Boolean.TRUE.equals(providerExclusionAssc.getIsEnabled())) {
                throw new AdminException(ExclusionMessages.PROVIDER_EXCLUSION_ALREADY_EXISTS.value());
            }
            providerExclusionAssc.setIsEnabled(true);
            providerExclusionAssc.setLastUpdateDate(new Date());
            ProviderExclusionAssc updatedProviderExclusionAssc = saveProviderExclusionAssc(providerExclusionAssc);
            manageAuditLog(AuditUpdatedType.UPDATE, updatedProviderExclusionAssc.getProviderExclusionAsscId(),
                    EntitiesName.PROVIDER_EXCLUSION_ASSC, updatedProviderExclusionAssc);
            return updatedProviderExclusionAssc;
        }
        ProviderExclusionAssc providerExclusionAssc = new ProviderExclusionAssc(providerId, exclusionId,
                providerExclusionRequestModel.getProviderName(), payerId, new Date());
        ProviderExclusionAssc addedProviderExclusionAssc = saveProviderExclusionAssc(providerExclusionAssc);
        manageAuditLog(AuditUpdatedType.INSERT, addedProviderExclusionAssc.getProviderExclusionAsscId(),
                EntitiesName.PROVIDER_EXCLUSION_ASSC, addedProviderExclusionAssc);
        return addedProviderExclusionAssc;
    }

    private void validateProviderName(String providerName, String providerId) throws AdminException {
        SwitchAccount switchAccount = switchAccountRepository
                .findBySwitchAccountIdAndIsEnabledAndCategoryIgnoreCase(new BigDecimal(providerId), "1", "PROVIDER")
                .orElseThrow(() -> new AdminException(ExclusionMessages.PROVIDER_NAME_NOT_FOUND.value()));
        if (!switchAccount.getName().equalsIgnoreCase(providerName)) {
            throw new AdminException(ExclusionMessages.PROVIDER_NAME_NOT_FOUND.value());
        }
    }

    private void validateProviderId(String providerId, Long payerId) throws AdminException {
        Optional<AccountToAccountAssociation> accToAccAsscOpt = accountToAccountAssociationRepository
                .findByIdSourceAndIdDestinationAndIsEnabled(new BigDecimal(providerId), new BigDecimal(payerId), true);
        if (accToAccAsscOpt.isEmpty()) {
            throw new AdminException(ExclusionMessages.PROVIDER_ID_NOT_FOUND.value());
        }
    }

    private void manageAuditLog(AuditUpdatedType auditUpdatedType, Long id, EntitiesName entityName,
                                ProviderExclusionAssc providerExclusionAssc) {
        auditLogService.addDataInAuditLog(auditUpdatedType, id, entityName, providerExclusionAssc);
    }

    private ProviderExclusionAssc saveProviderExclusionAssc(ProviderExclusionAssc providerExclusionAssc) {
        ProviderExclusionAssc savedProviderExclusionAssc = providerExclusionAsscRepository.save(providerExclusionAssc);
        log.info("Provider exclusion added successfully for exclusionId {} providerId{} payerId{} ",
                savedProviderExclusionAssc.getExclusionId(), savedProviderExclusionAssc.getProviderId(),
                savedProviderExclusionAssc.getPayerId());
        return savedProviderExclusionAssc;
    }

    private DrugExclusionMetadata validateExclusionId(Long exclusionId, Long payerId) throws AdminException {
        Optional<DrugExclusionMetadata> drugExclusionMetadataOpt = drugExclusionMetadataRepository
                .findByExclusionIdAndPayerIdAndIsDeleted(exclusionId, payerId, false);
        if (drugExclusionMetadataOpt.isEmpty()) {
            throw new AdminException(ExclusionMessages.EXCLUSIONID_NOT_FOUND.value());
        }
        return drugExclusionMetadataOpt.get();
    }

    private Long getPayerIdFromAuthentication() {
        return Long.parseLong(UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication()));
    }

    private ProviderExclusionResponseModel prepareProviderExclusionResponse(Long providerExclusionAsscId) {
        ProviderExclusionResponseModel responseModel = new ProviderExclusionResponseModel();
        responseModel.setProviderExclusionAsscId(providerExclusionAsscId);
        return responseModel;
    }

    private void updateLastUpdateDateInDrugExclusionMetadata(DrugExclusionMetadata drugExclusionMetadata) {
        if (drugExclusionMetadata != null) {
            drugExclusionMetadata.setLastUpdateDate(new Date());
            drugExclusionMetadataRepository.save(drugExclusionMetadata);
        }
    }

    public void saveProviderExclusionInExclusionAsscTypeList(ProviderExclusionAssc providerExclusionAssc,
                                                             Long payerId) {
        if (providerExclusionAssc == null) {
            return;
        }
        Optional<ExclusionAsscTypeList> exclusionAsscTypeListOpt = exclusionAsscTypeListRepository
                .findByExclusionIdAndExclusionAsscIdAndExclusionTypeAndPayerId(providerExclusionAssc.getExclusionId(),
                        providerExclusionAssc.getProviderExclusionAsscId(), ExclusionType.PROVIDER_EXCLUSION.value(),
                        payerId);
        if (exclusionAsscTypeListOpt.isEmpty()) {
            ExclusionAsscTypeList networkAssc = createExclusionAsscTypeList(providerExclusionAssc, payerId);
            exclusionAsscTypeListRepository.save(networkAssc);
        }
    }

    private ExclusionAsscTypeList createExclusionAsscTypeList(ProviderExclusionAssc providerExclusionAssc,
                                                              Long payerId) {
        ExclusionAsscTypeList networkAssc = new ExclusionAsscTypeList();
        networkAssc.setExclusionId(providerExclusionAssc.getExclusionId());
        networkAssc.setExclusionType(ExclusionType.PROVIDER_EXCLUSION.value());
        networkAssc.setExclusionAsscId(providerExclusionAssc.getProviderExclusionAsscId());
        networkAssc.setPayerId(payerId);
        networkAssc.setProviderId(providerExclusionAssc.getProviderId());
        networkAssc.setExclusionTypeName(providerExclusionAssc.getProviderName());
        return networkAssc;
    }
}
