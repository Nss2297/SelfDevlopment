package com.waseel.pbm.fdbvalidationservice.service.screeningservice;

import com.fdb.mkfi.core.IDNotFoundException;
import com.fdb.mkfi.core.NoUnivIDFactoryException;
import com.fdb.mkfi.core.UnivIDLoadException;
import com.fdb.mkfi.screening.DrugDrugScreenResult;
import com.fdb.mkfi.screening.DrugDrugSeverityCode;
import com.fdb.mkfi.screening.ScreenDrug;
import com.fdb.mkfi.screening.Screening;
import com.waseel.pbm.fdbvalidationservice.enums.FdbRejectionCodes;
import com.waseel.pbm.fdbvalidationservice.enums.ServiceStatus;
import com.waseel.pbm.fdbvalidationservice.model.Error;
import com.waseel.pbm.fdbvalidationservice.model.*;
import com.waseel.pbm.fdbvalidationservice.repository.mdss.CommonRejectionReasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DrugToDrugInteractionService {
	@Autowired
	CommonRejectionReasonRepository commonRejectionReasonRepo;

	public FdbResponse validate(FdbRequest fdbRequest)
			throws NumberFormatException, NoUnivIDFactoryException, UnivIDLoadException, IDNotFoundException {
		DrugDrugScreenResult[] drugToDrugInteractionResults = Screening
				.drugDrugScreen(fdbRequest.getPatientProfile().getFdbProfile(), false, DrugDrugSeverityCode.SEVERE);
		if (drugToDrugInteractionResults.length > 0)
			return mapDrugToDrugInteractionResult(fdbRequest, drugToDrugInteractionResults);
		return null;
	}

	private FdbResponse mapDrugToDrugInteractionResult(FdbRequest fdbRequest,
			DrugDrugScreenResult[] drugToDrugInteractionResults) {
		FdbResponse drugDrugValidationResponse = new FdbResponse();
		drugDrugValidationResponse.setRequestId(fdbRequest.getRequestId());
		List<FdbDrugResult> drugDrugValidationResults = new ArrayList<>();
		for (DrugDrugScreenResult result : drugToDrugInteractionResults) {
			// 1- Convert screen drugs to SFDA Codes
			List<String> interactedDrugSfdaCodes = convertScreenDrugsToSfdaCodes(result.getDrugs(),
					fdbRequest.getDrugList());
			interactedDrugSfdaCodes.forEach(interactedDrugSfdaCode -> {
				// 2- set Drug Result
				for (FdbDrugList reqDrug : fdbRequest.getDrugList()) {
					if (reqDrug.getIsDrugCodeMappedFromScientificCode() == false
							&& reqDrug.getDrugCode().equalsIgnoreCase(interactedDrugSfdaCode)) {
						setDrugToDrugInteractionValidationResults(drugDrugValidationResults, reqDrug,
								interactedDrugSfdaCodes);
						break;
					} else if (reqDrug.getIsDrugCodeMappedFromScientificCode() == true
							&& reqDrug.getScientificCode().equalsIgnoreCase(interactedDrugSfdaCode)) {
						setDrugToDrugInteractionValidationResults(drugDrugValidationResults, reqDrug,
								interactedDrugSfdaCodes);
						break;
					}
				}

			});

		}
		drugDrugValidationResponse.setDrugResults(drugDrugValidationResults);
		return drugDrugValidationResponse;
	}

	private List<String> convertScreenDrugsToSfdaCodes(ScreenDrug[] screenDrugs, List<FdbDrugList> fdbDrugList) {
		List<FdbDrugList> clonedDrugList = new ArrayList<>();
		clonedDrugList.addAll(fdbDrugList);
		List<ScreenDrug> interactedScreenDrugs = Arrays.asList(screenDrugs);
		List<String> interactedDrugCodes = new ArrayList<>();
		interactedScreenDrugs.forEach(interactedScreenDrug -> {
			for (FdbDrugList reqDrug : clonedDrugList) {
				if (reqDrug.getDispensableGeneric().toString().equalsIgnoreCase(interactedScreenDrug.toString())) {
					interactedDrugCodes
							.add(reqDrug.getIsDrugCodeMappedFromScientificCode() == true ? reqDrug.getScientificCode()
									: reqDrug.getDrugCode());
					clonedDrugList.remove(reqDrug);
					break;
				}
			}
		});
		return interactedDrugCodes;
	}

	private void setDrugToDrugInteractionValidationResults(List<FdbDrugResult> drugDrugValidationResults,
			FdbDrugList reqDrug, List<String> interactedDrugSfdaCodes) {
		Boolean isDrugResultUpdated = false;
		if (!drugDrugValidationResults.isEmpty()) {
			for (FdbDrugResult drugsInteractionResult : drugDrugValidationResults) {
				if (reqDrug.getIsDrugCodeMappedFromScientificCode() == false
						&& drugsInteractionResult.getDrugInfo().getDrugCode().equalsIgnoreCase(reqDrug.getDrugCode())) {
					drugsInteractionResult.getRejectionReason().addAll(setRejectionReason(
							drugsInteractionResult.getDrugInfo().getDrugCode(), interactedDrugSfdaCodes));
					isDrugResultUpdated = true;
					break;
				} else if (reqDrug.getIsDrugCodeMappedFromScientificCode() == true && drugsInteractionResult
						.getDrugInfo().getScientificCode().equalsIgnoreCase(reqDrug.getScientificCode())) {
					drugsInteractionResult.getRejectionReason().addAll(setRejectionReason(
							drugsInteractionResult.getDrugInfo().getScientificCode(), interactedDrugSfdaCodes));
					isDrugResultUpdated = true;
				}
			}
		}

		if (drugDrugValidationResults.isEmpty() || isDrugResultUpdated == false) {
			setFdbDrugResult(drugDrugValidationResults, reqDrug, interactedDrugSfdaCodes);
		}

	}

	private void setFdbDrugResult(List<FdbDrugResult> drugDrugValidationResults, FdbDrugList reqDrug,
			List<String> interactedDrugSfdaCodes) {
		FdbDrugResult drugResult = new FdbDrugResult();
		drugResult.setDrugInfo(reqDrug);
		drugResult.setStatus(ServiceStatus.REJECTED.value());
		drugResult.setRejectionReason(
				setRejectionReason(reqDrug.getIsDrugCodeMappedFromScientificCode() == true ? reqDrug.getScientificCode()
						: reqDrug.getDrugCode(), interactedDrugSfdaCodes));
		drugDrugValidationResults.add(drugResult);
	}

	private List<Error> setRejectionReason(String reqDrugCode, List<String> interactedDrugSfdaCodes) {
		List<Error> rejectionReasons = new ArrayList<>();
		for (String interactedDrugCode : interactedDrugSfdaCodes) {
			if (!interactedDrugCode.equalsIgnoreCase(reqDrugCode)) {
				Error rejReason = new Error();
				rejReason.setCode(FdbRejectionCodes.DRUG_TODRUG_INTERACTION_REJECTIONCODE.value());
				rejReason.setDescription(commonRejectionReasonRepo
						.findByRejectionCode(FdbRejectionCodes.DRUG_TODRUG_INTERACTION_REJECTIONCODE.value())
						.replace("<DrugName> (<DrugCode>)", reqDrugCode)
						.replace("<InteractedDrugName> (<InteractedDrugCode>)", interactedDrugCode));
				rejectionReasons.add(rejReason);
			}
		}
		return rejectionReasons;
	}
}