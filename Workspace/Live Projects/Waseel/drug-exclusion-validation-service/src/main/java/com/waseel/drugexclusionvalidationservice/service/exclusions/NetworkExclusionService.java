package com.waseel.drugexclusionvalidationservice.service.exclusions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.drugexclusionvalidationservice.model.DrugExclusionModel;
import com.waseel.drugexclusionvalidationservice.model.DrugExclusionRequestModel;
import com.waseel.drugexclusionvalidationservice.model.enums.DenialCode;
import com.waseel.drugexclusionvalidationservice.model.enums.ServiceStatus;
import com.waseel.drugexclusionvalidationservice.persist.businessrules.CommonDenials;
import com.waseel.drugexclusionvalidationservice.persist.businessrules.DrugExclusionDetails;
import com.waseel.drugexclusionvalidationservice.persist.businessrules.DrugExclusionMetadata;
import com.waseel.drugexclusionvalidationservice.persist.businessrules.NetworkExclusionAssc;
import com.waseel.drugexclusionvalidationservice.persist.businessrules.ProviderNetworkAssc;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.CommonDenialsRepository;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.DrugExclusionDetailsRepository;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.DrugExclusionMetadataRepository;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.NetworkExclusionAsscRepository;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.ProviderNetworkAsscRepository;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.ProviderNetworkRepository;

@Service
public class NetworkExclusionService {

	@Autowired
	ProviderNetworkRepository providerNetworkRepository;
	@Autowired
	ProviderNetworkAsscRepository providerNetworkAsscRepository;
	@Autowired
	NetworkExclusionAsscRepository networkExclusionAsscRepository;
	@Autowired
	DrugExclusionMetadataRepository drugExclusionMetadataRepository;
	@Autowired
	DrugExclusionDetailsRepository drugExclusionDetailsRepository;
	@Autowired
	CommonDenialsRepository commonDenialsRepository;

	public List<DrugExclusionModel> checkNetworkExclusion(DrugExclusionRequestModel requestModel) {
		if (requestModel != null) {
			BigDecimal payerId = new BigDecimal(requestModel.getPayerId());
			List<ProviderNetworkAssc> providerNetworkAsscList = providerNetworkAsscRepository
					.findByProviderIdAndIsEnabled(new BigDecimal(requestModel.getProviderId()), true);
			if (providerNetworkAsscList != null && !providerNetworkAsscList.isEmpty()) {
				List<Long> networkIds = getAllNetworkIdsFromList(providerNetworkAsscList, payerId);
				List<NetworkExclusionAssc> networkExclusionAsscList = networkExclusionAsscRepository
						.findByProviderNetwork_NetworkIdInAndIsEnabled(networkIds, true);
				List<DrugExclusionDetails> drugExclusionDetails = getDrugExclusionDetails(networkExclusionAsscList,
						payerId);
				if (drugExclusionDetails != null && !drugExclusionDetails.isEmpty()) {
					return validateDrugListAgainstNetwork(drugExclusionDetails, requestModel.getDrugList());
				}
			}
		}
		return new ArrayList<>();
	}

	private List<DrugExclusionDetails> getDrugExclusionDetails(List<NetworkExclusionAssc> networkExclusionAsscList,
			BigDecimal payerId) {
		if (networkExclusionAsscList != null && !networkExclusionAsscList.isEmpty()) {
			List<Long> exclusionIds = getAllExclusionIdsFromList(networkExclusionAsscList);
			List<DrugExclusionMetadata> drugExclusionMetadata = drugExclusionMetadataRepository
					.findByExclusionIdInAndPayerIdAndIsDeleted(exclusionIds, payerId.longValue(), false);
			if (drugExclusionMetadata != null && !drugExclusionMetadata.isEmpty()) {
				return drugExclusionDetailsRepository.findByExclusionIdInAndIsDeleted(exclusionIds, false);
			}
		}
		return Collections.emptyList();
	}

	private List<DrugExclusionModel> validateDrugListAgainstNetwork(List<DrugExclusionDetails> drugExclusionDetailsList,
			List<String> drugList) {
		List<DrugExclusionModel> responseModelList = new ArrayList<>();
		String denialCodeDesc = getDenialCodeDescription();
		drugList.forEach(drug -> {
			DrugExclusionModel responseModel = new DrugExclusionModel();
			responseModel.setDrugCode(drug);
			responseModel.setStatusCode(ServiceStatus.APPROVED.value());
			if (drugExclusionDetailsList.stream()
					.anyMatch(drugExclusionDetails -> drugExclusionDetails.getRegistrationNumber().equals(drug))) {
				responseModel.setDenialCode(DenialCode.NETWORK_EXCLUSION.value());
				responseModel.setStatusCode(ServiceStatus.REJECTED.value());
				if (denialCodeDesc != null)
					responseModel.setStatusDescription(denialCodeDesc.replace("<drugcode> <DrugName>", drug));
			}
			responseModelList.add(responseModel);
		});
		return responseModelList;
	}

	private String getDenialCodeDescription() {
		Optional<CommonDenials> commonDenialsOptional = commonDenialsRepository
				.findByDenialCode(DenialCode.NETWORK_EXCLUSION.value());
		if (commonDenialsOptional.isPresent()) {
			return commonDenialsOptional.get().getDenialDescription();
		}
		return null;
	}

	public List<Long> getAllExclusionIdsFromList(List<NetworkExclusionAssc> networkExclusionAsscList) {
		return networkExclusionAsscList.stream().map(networkAssc -> networkAssc.getExclusionId().longValue())
				.collect(Collectors.toList());
	}

	public List<Long> getAllNetworkIdsFromList(List<ProviderNetworkAssc> providerNetworkAsscList, BigDecimal payerId) {
		return providerNetworkAsscList.stream()
				.filter(providerNetworkAssc -> !providerNetworkAssc.getProviderNetwork().getIsDeleted()
						&& providerNetworkAssc.getProviderNetwork().getPayerId().equals(payerId))
				.map(providerNetworkAssc -> providerNetworkAssc.getProviderNetwork().getNetworkId())
				.collect(Collectors.toList());
	}
}
