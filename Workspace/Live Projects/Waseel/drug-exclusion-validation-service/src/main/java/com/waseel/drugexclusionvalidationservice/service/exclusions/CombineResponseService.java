package com.waseel.drugexclusionvalidationservice.service.exclusions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.waseel.drugexclusionvalidationservice.model.DrugExclusionModel;
import com.waseel.drugexclusionvalidationservice.model.DrugExclusionResponseModel;
import com.waseel.drugexclusionvalidationservice.model.DrugList;
import com.waseel.drugexclusionvalidationservice.model.Rejections;
import com.waseel.drugexclusionvalidationservice.model.enums.ServiceStatus;

@Service
public class CombineResponseService {

	protected void combineResponse(DrugExclusionResponseModel drugExclusionResponseModel, List<String> drugList,
			List<DrugExclusionModel> specialityResponseList, List<DrugExclusionModel> highCostDrugsResponseList,
			List<DrugExclusionModel> providerExclusionCheckResposne, List<DrugExclusionModel> networkResponseList) {
		List<DrugList> drugLists = new ArrayList<>();
		drugList.forEach(drug -> {
			DrugList drugs = new DrugList();
			drugs.setDrugCode(drug);
			drugs.setStatusCode(ServiceStatus.APPROVED.value());
			List<Rejections> rejectionsList = new ArrayList<>();
			if (specialityResponseList != null && !specialityResponseList.isEmpty()) {
				addRejections(rejectionsList, specialityResponseList, drug);
			}
			if (highCostDrugsResponseList != null && !highCostDrugsResponseList.isEmpty()) {
				addRejections(rejectionsList, highCostDrugsResponseList, drug);
			}
			if (providerExclusionCheckResposne != null && !providerExclusionCheckResposne.isEmpty()) {
				addRejections(rejectionsList, providerExclusionCheckResposne, drug);
			}
			if (networkResponseList != null && !networkResponseList.isEmpty()) {
				addRejections(rejectionsList, networkResponseList, drug);
			}
			if (!rejectionsList.isEmpty()) {
				drugs.setStatusCode(ServiceStatus.REJECTED.value());
				drugs.setRejectionsList(rejectionsList);
			}
			drugLists.add(drugs);
		});
		drugExclusionResponseModel.setDrugList(drugLists);
	}

	private void addRejections(List<Rejections> rejectionsList, List<DrugExclusionModel> responseModelList,
			String drugCode) {
		responseModelList.forEach(model -> {
			if (model.getDrugCode().equals(drugCode) && model.getStatusCode().equals(ServiceStatus.REJECTED.value())) {
				Rejections rejections = new Rejections();
				rejections.setDenialCode(model.getDenialCode());
				rejections.setStatusDescription(model.getStatusDescription());
				rejectionsList.add(rejections);
			}
		});
	}
}
