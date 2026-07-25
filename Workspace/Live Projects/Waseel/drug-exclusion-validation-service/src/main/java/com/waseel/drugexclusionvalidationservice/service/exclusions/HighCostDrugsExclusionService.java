package com.waseel.drugexclusionvalidationservice.service.exclusions;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.drugexclusionvalidationservice.model.DrugExclusionModel;
import com.waseel.drugexclusionvalidationservice.model.DrugExclusionRequestModel;
import com.waseel.drugexclusionvalidationservice.model.enums.DenialCode;
import com.waseel.drugexclusionvalidationservice.model.enums.ServiceStatus;
import com.waseel.drugexclusionvalidationservice.persist.businessrules.CommonDenials;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.CommonDenialsRepository;
import com.waseel.drugexclusionvalidationservice.repository.businessrules.HighCostExclusionAsscRepository;

@Service
public class HighCostDrugsExclusionService {

	@Autowired
	private HighCostExclusionAsscRepository highCostExclusionAsscRepository;

	@Autowired
	private CommonDenialsRepository commonDenialsRepository;

	public List<DrugExclusionModel> checkHighCostDrugsExclusion(DrugExclusionRequestModel requestModel) {
		Long payerId = Long.valueOf(requestModel.getPayerId());
		Set<String> requestedDrugList = new HashSet<>(requestModel.getDrugList());
		List<String> rejectableDrugList = highCostExclusionAsscRepository
				.checkHighCostDrugsExclusionByPayerIdAndDrugList(payerId, requestedDrugList);
		return prepareResponseForHighCostDrugs(rejectableDrugList, requestedDrugList);
	}

	private List<DrugExclusionModel> prepareResponseForHighCostDrugs(List<String> rejectableDrugList,
			Set<String> requestedDrugList) {
		String denialCodeDesc = getDenialCodeDescription();
		return requestedDrugList.stream().map(drug -> {
			DrugExclusionModel responseModel = new DrugExclusionModel();
			responseModel.setDrugCode(drug);
			responseModel.setStatusCode(ServiceStatus.APPROVED.value());
			if (rejectableDrugList.contains(drug)) {
				responseModel.setDenialCode(DenialCode.HIGH_COST_DRUGS_EXCLUSION.value());
				responseModel.setStatusCode(ServiceStatus.REJECTED.value());
				responseModel.setStatusDescription(
						denialCodeDesc != null ? denialCodeDesc.replace("<drugcode> <DrugName>", drug) : null);
			}
			return responseModel;
		}).collect(Collectors.toList());
	}

	private String getDenialCodeDescription() {
		Optional<CommonDenials> commonDenialsOptional = commonDenialsRepository
				.findByDenialCode(DenialCode.HIGH_COST_DRUGS_EXCLUSION.value());
		return commonDenialsOptional.map(CommonDenials::getDenialDescription).orElse(null);
	}
}
