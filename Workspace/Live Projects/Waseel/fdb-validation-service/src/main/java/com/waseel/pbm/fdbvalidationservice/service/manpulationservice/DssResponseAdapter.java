package com.waseel.pbm.fdbvalidationservice.service.manpulationservice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.waseel.pbm.fdbvalidationservice.enums.RequestStatus;
import com.waseel.pbm.fdbvalidationservice.enums.ServiceStatus;
import com.waseel.pbm.fdbvalidationservice.model.DrugList;
import com.waseel.pbm.fdbvalidationservice.model.DssRequest;
import com.waseel.pbm.fdbvalidationservice.model.DssResponse;
import com.waseel.pbm.fdbvalidationservice.model.Error;
import com.waseel.pbm.fdbvalidationservice.model.FdbDrugList;
import com.waseel.pbm.fdbvalidationservice.model.FdbDrugResult;
import com.waseel.pbm.fdbvalidationservice.model.FdbRequest;
import com.waseel.pbm.fdbvalidationservice.model.FdbResponse;
import com.waseel.pbm.fdbvalidationservice.model.Result;

@Service
public class DssResponseAdapter {

	int rejectedDrugsCount;

	public DssResponse combine(DssRequest dssRequest, FdbRequest fdbRequest, FdbResponse drugToDrugInteractionResponse,
			FdbResponse drugToDiseaseResponse, FdbResponse drugToGenderInteractionResponse,
			FdbResponse drugToAgeInteractionResponse, FdbResponse duplicatedTherapyResponse,
			FdbResponse quantityLimitCheckResponse) {

		rejectedDrugsCount = 0;
		DssResponse dssResponse = new DssResponse();
		List<Result> drugResults = new ArrayList<>();
		dssResponse.setRequestId(fdbRequest.getRequestId());

		fdbRequest.getDrugList().forEach(drug -> {
			Result drugResult = new Result();
			List<Error> rejectionReasons = new ArrayList<>();
			if (drug.getIsDrugCodeMappedFromScientificCode() == false) {
				drugResult.setNdcDrugCode(drug.getDrugCode());
			}
			if (drug.getScientificCode() != null && !drug.getScientificCode().isEmpty()) {
				drugResult.setScientificCode(drug.getScientificCode());
			}
			drugResult.setDispensedQuantity(drug.getDispensedQuantity());
			drugResult.setAmount(drug.getAmount());
			drugResult.setDaysOfSupply(drug.getDaysOfSupply());

			// go through all modules results to set final response ..
			if (drugToDrugInteractionResponse != null)
				addRejectionReasons(drugToDrugInteractionResponse, rejectionReasons, drugResult);

			if (drugToDiseaseResponse != null)
				addRejectionReasons(drugToDiseaseResponse, rejectionReasons, drugResult);

			if (drugToGenderInteractionResponse != null)
				addRejectionReasons(drugToGenderInteractionResponse, rejectionReasons, drugResult);

			if (drugToAgeInteractionResponse != null)
				addRejectionReasons(drugToAgeInteractionResponse, rejectionReasons, drugResult);

			if (duplicatedTherapyResponse != null)
				addRejectionReasons(duplicatedTherapyResponse, rejectionReasons, drugResult);

			if (quantityLimitCheckResponse != null)
				addRejectionReasons(quantityLimitCheckResponse, rejectionReasons, drugResult);

			if (!rejectionReasons.isEmpty()) {
				drugResult.setStatus(ServiceStatus.REJECTED.value());
				drugResult.setErrors(rejectionReasons);
				rejectedDrugsCount = rejectedDrugsCount + 1;
			} else {
				drugResult.setStatus(ServiceStatus.APPROVED.value());
			}
			drugResults.add(drugResult);
		});
		// Need to Approved services that is excluded from FDB request
		approvedExcludedDrug(drugResults, dssRequest.getDrugList(), fdbRequest.getDrugList());
		dssResponse.setResults(drugResults);
		manageRequestStatus(dssResponse, dssRequest.getDrugList());
		return dssResponse;
	}

	private void addRejectionReasons(FdbResponse fdbResponse, List<Error> rejectionReasons, Result drugResult) {
		for (FdbDrugResult fdbDrugResult : fdbResponse.getDrugResults()) {
			if (drugResult.getNdcDrugCode() != null && !drugResult.getNdcDrugCode().isEmpty()
					&& fdbDrugResult.getDrugInfo() != null && fdbDrugResult.getDrugInfo().getDrugCode() != null
					&& fdbDrugResult.getDrugInfo().getDrugCode().equals(drugResult.getNdcDrugCode())) {
				rejectionReasons.addAll(fdbDrugResult.getRejectionReason());
				break;
			} else if (drugResult.getScientificCode() != null && !drugResult.getScientificCode().isEmpty()
					&& fdbDrugResult.getDrugInfo() != null && fdbDrugResult.getDrugInfo().getScientificCode() != null
					&& fdbDrugResult.getDrugInfo().getScientificCode().equals(drugResult.getScientificCode())) {
				rejectionReasons.addAll(fdbDrugResult.getRejectionReason());
				break;
			}
		}
	}

	private void manageRequestStatus(DssResponse dssResponse, List<DrugList> dssDrugList) {
		if (rejectedDrugsCount == 0) {
			dssResponse.setStatus(RequestStatus.APPROVED.value());
		} else if (rejectedDrugsCount == dssDrugList.size()) {
			dssResponse.setStatus(RequestStatus.REJECTED.value());
		} else {
			dssResponse.setStatus(RequestStatus.PARTIAL_APPROVED.value());
		}
		dssResponse.setHttpStatusCode(HttpStatus.OK.value());
	}

	private void approvedExcludedDrug(List<Result> drugResults, List<DrugList> dssDrugList,
			List<FdbDrugList> fdbDrugList) {
		// Need to Approved services that is excluded from FDB request
		List<DrugList> notMatchedDrugs = getNoneMatchedDrugs(dssDrugList, fdbDrugList);

		if (notMatchedDrugs != null && !notMatchedDrugs.isEmpty()) {
			notMatchedDrugs.forEach(result -> {
				Result drugResult = new Result();
				if (result.getScientificCode() != null && !result.getScientificCode().isEmpty()) {
					drugResult.setScientificCode(result.getScientificCode());
				} else {
					drugResult.setNdcDrugCode(result.getNdcDrugCode());
				}
				drugResult.setDispensedQuantity(result.getDispensedQuantity());
				drugResult.setAmount(result.getAmount());
				drugResult.setDaysOfSupply(result.getDaysOfSupply());
				drugResult.setStatus(ServiceStatus.APPROVED.value());
				drugResults.add(drugResult);
			});
		}
	}

	private List<DrugList> getNoneMatchedDrugs(List<DrugList> dssDrugList, List<FdbDrugList> fdbDrugList) {
//		List<DrugList> notMatchedDrugs = dssDrugList.stream()
//		.filter(dssDrug -> fdbDrugList.stream().noneMatch(fdbDrug -> 
//				dssDrug.getNdcDrugCode().trim().equals(fdbDrug.getDrugCode().trim())))
//		.collect(Collectors.toList());
//		

		List<DrugList> matchedDrugs = new ArrayList<>();
		for (DrugList dssDrug : dssDrugList) {
			for (FdbDrugList fdbDrug : fdbDrugList) {
				if (dssDrug.getScientificCode() != null && !dssDrug.getScientificCode().isEmpty()
						&& fdbDrug.getScientificCode() != null && !fdbDrug.getScientificCode().isEmpty()
						&& dssDrug.getScientificCode().trim().equals(fdbDrug.getScientificCode().trim())) {
					matchedDrugs.add(dssDrug);
					break;
				} else if (dssDrug.getNdcDrugCode() != null && !dssDrug.getNdcDrugCode().isEmpty()
						&& fdbDrug.getDrugCode() != null && !fdbDrug.getDrugCode().isEmpty()
						&& dssDrug.getNdcDrugCode().trim().equals(fdbDrug.getDrugCode().trim())) {
					matchedDrugs.add(dssDrug);
					break;
				}

			}
		}

		return dssDrugList.stream().filter(dssDrug -> !matchedDrugs.contains(dssDrug)).collect(Collectors.toList());
	}
}