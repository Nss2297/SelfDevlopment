package com.waseel.drugexclusionvalidationservice.service.exclusions;

import java.util.ArrayList;
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
import com.waseel.drugexclusionvalidationservice.persist.businessrules.ProviderExclusionAssc;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.CommonDenialsRepository;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.DrugExclusionDetailsRepository;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.ProviderExclusionAsscRepository;

@Service
public class ProviderExclusionService {

	@Autowired
	private ProviderExclusionAsscRepository providerExclusionAsscRepository;

	@Autowired
	private DrugExclusionDetailsRepository drugExclusionDetailsRepository;

	@Autowired
	private CommonDenialsRepository commonDenialsRepository;

	public List<DrugExclusionModel> providerExclusionCheckForDrugs(DrugExclusionRequestModel requestModel) {
		List<DrugExclusionModel> drugExclusionModels = new ArrayList<>();
		List<ProviderExclusionAssc> providerExclusionAsscList = providerExclusionAsscRepository
				.findByProviderIdAndPayerIdAndIsEnabledAndIsDeleted(Long.valueOf(requestModel.getProviderId()),
						Long.valueOf(requestModel.getPayerId()), true, false);
		if (providerExclusionAsscList != null && !providerExclusionAsscList.isEmpty()) {
			List<DrugExclusionDetails> exclusionList = drugExclusionDetailsRepository
					.findByExclusionIdInAndIsDeleted(getAllExclusionIdsFromList(providerExclusionAsscList), false);
			if (null != exclusionList) {
				return checkDrugsInExclusionList(exclusionList, drugExclusionModels, requestModel.getDrugList());
			}
		}
		return drugExclusionModels;
	}

	private List<DrugExclusionModel> checkDrugsInExclusionList(List<DrugExclusionDetails> exclusionList,
			List<DrugExclusionModel> drugExclusionModels, List<String> drugList) {
		String denialDescription = getProviderExclusionDenialDescription();
		drugList.stream().forEach(drugCode -> {
			DrugExclusionModel drugExclusionModel = new DrugExclusionModel();
			drugExclusionModel.setDrugCode(drugCode);
			drugExclusionModel.setStatusCode(ServiceStatus.APPROVED.value());
			if (exclusionList.stream()
					.anyMatch(excludedDrug -> excludedDrug.getRegistrationNumber().equals(drugCode))) {
				drugExclusionModel.setDenialCode(DenialCode.PROVIDER_EXCLUSION.value());
				drugExclusionModel.setStatusCode(ServiceStatus.REJECTED.value());
				drugExclusionModel.setStatusDescription(denialDescription.replace("<drugcode> <DrugName>", drugCode));
			}
			drugExclusionModels.add(drugExclusionModel);
		});
		return drugExclusionModels;
	}

	private String getProviderExclusionDenialDescription() {
		Optional<CommonDenials> commonDenialOpt = commonDenialsRepository
				.findByDenialCode(DenialCode.PROVIDER_EXCLUSION.value());
		if (commonDenialOpt.isPresent()) {
			return commonDenialOpt.get().getDenialDescription();
		}
		return null;
	}
	
	private List<Long> getAllExclusionIdsFromList(List<ProviderExclusionAssc> providerExclusionAsscList) {
		return providerExclusionAsscList.stream().map(ProviderExclusionAssc::getExclusionId)
				.collect(Collectors.toList());
	}
}
