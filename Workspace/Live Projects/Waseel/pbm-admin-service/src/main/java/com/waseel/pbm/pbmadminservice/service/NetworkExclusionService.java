package com.waseel.pbm.pbmadminservice.service;

import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waseel.pbm.pbmadminservice.enums.AuditUpdatedType;
import com.waseel.pbm.pbmadminservice.enums.EntitiesName;
import com.waseel.pbm.pbmadminservice.enums.drugexclusion.ExclusionMessages;
import com.waseel.pbm.pbmadminservice.enums.drugexclusion.ExclusionType;
import com.waseel.pbm.pbmadminservice.exceptions.AdminException;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.network.NetworkExclusionModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.network.NetworkExclusionRequestModel;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugExclusionMetadata;
import com.waseel.pbm.pbmadminservice.persist.businessrules.ExclusionAsscTypeList;
import com.waseel.pbm.pbmadminservice.persist.businessrules.NetworkExclusionAssc;
import com.waseel.pbm.pbmadminservice.persist.businessrules.ProviderNetwork;
import com.waseel.pbm.pbmadminservice.repository.businessrules.DrugExclusionMetadataRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.ExclusionAsscTypeListRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.NetworkExclusionAsscRepository;
import com.waseel.pbm.pbmadminservice.repository.businessrules.ProviderNetworkRepository;
import com.waseel.pbm.pbmadminservice.specification.NetworkExclusionSpecification;
import com.waseel.pbm.pbmadminservice.util.UserInfoUtil;

@Service
public class NetworkExclusionService {
	private final Logger log = LoggerFactory.getLogger(NetworkExclusionService.class);

	@Autowired
	private ProviderNetworkRepository providerNetworkRepository;
	@Autowired
	private NetworkExclusionAsscRepository networkExclusionAsscRepository;
	@Autowired
	private DrugExclusionMetadataRepository drugExclusionMetadataRepository;
	@Autowired
	private AuditLogService auditLogService;
	@Autowired
	private NetworkExclusionSpecification networkExclusionSpecification;
	@Autowired
	private ExclusionAsscTypeListRepository exclusionAsscTypeListRepository;

	@Transactional(value = "BusinessRulesTransactionManager")
	public NetworkExclusionModel addNetworkExclusion(NetworkExclusionRequestModel networkExclusionRequestModel,
			Long exclusionId) throws AdminException {
		Long payerId = getPayerIdFromAuthentication();
		Long networkId = Long.parseLong(networkExclusionRequestModel.getNetworkId());
		DrugExclusionMetadata drugExclusionMetadata = validateExclusionId(exclusionId, payerId);
		String networkName = validateNetworkId(networkId, payerId);
		NetworkExclusionAssc networkExclusionAssc = createOrUpdateNetworkExclusionAssc(exclusionId, networkId);
		saveNetworkExclusionInExclusionAsscTypeList(networkExclusionAssc, payerId, networkName);
		updateLastUpdateDateInDrugExclusionMetadata(drugExclusionMetadata);
		return prepareNetworkExclusionResponse(networkExclusionAssc.getNetworkExclusionAsscId());
	}

	private NetworkExclusionModel prepareNetworkExclusionResponse(Long networkExclusionAsscId) {
		NetworkExclusionModel networkExclusionModel = new NetworkExclusionModel();
		networkExclusionModel.setNetworkExclusionAsscId(networkExclusionAsscId);
		return networkExclusionModel;
	}

	private NetworkExclusionAssc saveNetworkExclusionAssc(NetworkExclusionAssc networkExclusionAssc) {
		NetworkExclusionAssc savedNetworkExclusionAssc = networkExclusionAsscRepository.save(networkExclusionAssc);
		log.info("Network exclusion added successfully for exclusionId {} networkId{}",
				savedNetworkExclusionAssc.getExclusionId(), savedNetworkExclusionAssc.getNetworkId());
		return savedNetworkExclusionAssc;
	}

	private DrugExclusionMetadata validateExclusionId(Long exclusionId, Long payerId) throws AdminException {
		Optional<DrugExclusionMetadata> drugExclusionMetadataOpt = drugExclusionMetadataRepository
				.findByExclusionIdAndPayerIdAndIsDeleted(exclusionId, payerId, false);
		if (drugExclusionMetadataOpt.isEmpty()) {
			throw new AdminException(ExclusionMessages.EXCLUSIONID_NOT_FOUND.value());
		}
		return drugExclusionMetadataOpt.get();
	}

	private String validateNetworkId(Long networkId, Long payerId) throws AdminException {
		Optional<ProviderNetwork> providerNetworkOpt = getProviderNetwork(networkId, payerId);
		if (providerNetworkOpt.isEmpty()) {
			throw new AdminException(ExclusionMessages.NETWORK_ID_NOT_FOUND.value());
		}
		return providerNetworkOpt.get().getNetworkName();
	}

	private Long getPayerIdFromAuthentication() {
		return Long.parseLong(UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication()));
	}

	private NetworkExclusionAssc createOrUpdateNetworkExclusionAssc(Long exclusionId, Long networkId)
			throws AdminException {
		Optional<NetworkExclusionAssc> networkExclusionAsscOpt = networkExclusionAsscRepository
				.findByExclusionIdAndNetworkId(exclusionId, networkId);
		if (networkExclusionAsscOpt.isPresent()) {
			NetworkExclusionAssc networkExclusionAssc = networkExclusionAsscOpt.get();
			if (Boolean.TRUE.equals(networkExclusionAssc.getIsEnabled())) {
				throw new AdminException(ExclusionMessages.NETWORK_EXCLUSION_ALREADY_EXISTS.value());
			}
			networkExclusionAssc.setIsEnabled(true);
			networkExclusionAssc.setLastUpdateDate(new Date());
			NetworkExclusionAssc updatedNetworkExclusionAssc = saveNetworkExclusionAssc(networkExclusionAssc);
			manageAuditLog(AuditUpdatedType.UPDATE, updatedNetworkExclusionAssc.getNetworkExclusionAsscId(),
					EntitiesName.NETWORK_EXCLUSION_ASSC, updatedNetworkExclusionAssc);
			return updatedNetworkExclusionAssc;
		}
		NetworkExclusionAssc networkExclusionAssc = new NetworkExclusionAssc(networkId, exclusionId, new Date());
		NetworkExclusionAssc addedNetworkExclusionAssc = saveNetworkExclusionAssc(networkExclusionAssc);
		manageAuditLog(AuditUpdatedType.INSERT, addedNetworkExclusionAssc.getNetworkExclusionAsscId(),
				EntitiesName.NETWORK_EXCLUSION_ASSC, addedNetworkExclusionAssc);
		return addedNetworkExclusionAssc;
	}

	private void manageAuditLog(AuditUpdatedType auditUpdatedType, Long id, EntitiesName entityName,
			NetworkExclusionAssc networkExclusionAssc) {
		auditLogService.addDataInAuditLog(auditUpdatedType, id, entityName, networkExclusionAssc);
	}

	public Page<NetworkExclusionModel> getAllNetworkList(int pageNumber, int recordSize, String value) {
		log.info("Networks: Page Number :- {}, Record Size :- {} ", pageNumber, recordSize);
        String valueTrim = !StringUtils.isBlank(value) ? value.trim() : value;
		Long payerId = Long.parseLong(UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication()));
		NetworkExclusionModel networkExclusionModel = new NetworkExclusionModel(valueTrim, payerId);
		return networkExclusionSpecification.findNetworksWithPagination(pageNumber, recordSize, networkExclusionModel);
	}

	public Long deleteNetworkExclusionAssc(Long id) throws AdminException {
		return networkExclusionAsscRepository.findByNetworkExclusionAsscIdAndIsEnabled(id, true)
				.map(networkExclusionAssc -> {
					networkExclusionAssc.setIsEnabled(false);
					networkExclusionAssc.setLastUpdateDate(new Date());
					networkExclusionAsscRepository.save(networkExclusionAssc);
					auditLogService.addDataInAuditLog(AuditUpdatedType.DELETE, id, EntitiesName.NETWORK_EXCLUSION_ASSC,
							null);
					return networkExclusionAssc.getExclusionId();
				}).orElseThrow(() -> new AdminException(ExclusionMessages.EXCLUSION_ASSC_ID_NOT_FOUND.value()));
	}

	public void saveNetworkExclusionInExclusionAsscTypeList(NetworkExclusionAssc networkExclusionAssc, Long payerId,
			String networkName) {
		if (networkExclusionAssc == null) {
			return;
		}
		Optional<ExclusionAsscTypeList> exclusionAsscTypeListOpt = exclusionAsscTypeListRepository
				.findByExclusionIdAndExclusionAsscIdAndExclusionTypeAndPayerId(networkExclusionAssc.getExclusionId(),
						networkExclusionAssc.getNetworkExclusionAsscId(), ExclusionType.NETWORK_EXCLUSION.value(),
						payerId);
		if (exclusionAsscTypeListOpt.isEmpty()) {
			ExclusionAsscTypeList networkAssc = createExclusionAsscTypeList(networkExclusionAssc, payerId, networkName);
			exclusionAsscTypeListRepository.save(networkAssc);
		}
	}

	private ExclusionAsscTypeList createExclusionAsscTypeList(NetworkExclusionAssc networkExclusionAssc, Long payerId,
			String networkName) {
		ExclusionAsscTypeList networkAssc = new ExclusionAsscTypeList();
		networkAssc.setExclusionId(networkExclusionAssc.getExclusionId());
		networkAssc.setExclusionType(ExclusionType.NETWORK_EXCLUSION.value());
		networkAssc.setExclusionAsscId(networkExclusionAssc.getNetworkExclusionAsscId());
		networkAssc.setPayerId(payerId);
		networkAssc.setNetworkId(networkExclusionAssc.getNetworkId());
		networkAssc.setExclusionTypeName(networkName);
		return networkAssc;
	}
	
	public String getProviderNetworkName(Long networkId, Long payerId) {
		Optional<ProviderNetwork> providerNetworkOpt = getProviderNetwork(networkId, payerId);
		if (providerNetworkOpt.isPresent()) {
			ProviderNetwork providerNetwork = providerNetworkOpt.get();
			return providerNetwork.getNetworkName();
		}
		return null;
	}
	
	private Optional<ProviderNetwork> getProviderNetwork(Long networkId, Long payerId) {
		return providerNetworkRepository.findByNetworkIdAndPayerIdAndIsDeleted(networkId, payerId, false);
	}

	private void updateLastUpdateDateInDrugExclusionMetadata(DrugExclusionMetadata drugExclusionMetadata) {
		if (drugExclusionMetadata != null) {
			drugExclusionMetadata.setLastUpdateDate(new Date());
			drugExclusionMetadataRepository.save(drugExclusionMetadata);
		}
	}
}
