package com.waseel.pbm.fdbvalidationservice.service.screeningservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdb.mkfi.core.UnitOfMeasure;
import com.fdb.mkfi.screening.DuplicateTherapyScreenResult;
import com.fdb.mkfi.screening.DuplicateTherapySourceCode;
import com.fdb.mkfi.screening.FDBProfile;
import com.fdb.mkfi.screening.PatientAge;
import com.fdb.mkfi.screening.PatientWeight;
import com.fdb.mkfi.screening.Profile;
import com.fdb.mkfi.screening.ScreenDrug;
import com.fdb.mkfi.screening.Screening;
import com.waseel.pbm.fdbvalidationservice.enums.FdbRejectionCodes;
import com.waseel.pbm.fdbvalidationservice.enums.ServiceStatus;
import com.waseel.pbm.fdbvalidationservice.model.Error;
import com.waseel.pbm.fdbvalidationservice.model.FdbDrugList;
import com.waseel.pbm.fdbvalidationservice.model.FdbDrugResult;
import com.waseel.pbm.fdbvalidationservice.model.FdbRequest;
import com.waseel.pbm.fdbvalidationservice.model.FdbResponse;
import com.waseel.pbm.fdbvalidationservice.repository.mdss.CommonRejectionReasonRepository;

@Service
public class DuplicateTherapyService {

	@Autowired
	CommonRejectionReasonRepository commonRejectionReasonRepo;

	public FdbResponse validate(FdbRequest fdbRequest) {

		FDBProfile duplicateTherapyPatientProfile = setDuplicateTherpayPatientProfile(fdbRequest);
		DuplicateTherapyScreenResult[] duplicatedTherapy = null;

		if (duplicateTherapyPatientProfile != null && duplicateTherapyPatientProfile.getDrugs() != null
				&& duplicateTherapyPatientProfile.getDrugs().length > 0) {
			duplicatedTherapy = Screening.duplicateTherapyScreen(setDuplicateTherpayPatientProfile(fdbRequest), false,
					DuplicateTherapySourceCode.FDB_ONLY, null, null);
		}
		if (duplicatedTherapy!=null && duplicatedTherapy.length > 0)
			return mapDuplicateTherapyResult(fdbRequest, duplicatedTherapy);
		return null;
	}

	private FDBProfile setDuplicateTherpayPatientProfile(FdbRequest fdbRequest) {
		FDBProfile duplicateTherapyPatientProfile = new FDBProfile();
		duplicateTherapyPatientProfile.addDrug(excludeDrugsWithSameScientificName(fdbRequest.getDrugList()));
		return duplicateTherapyPatientProfile;
	}

	private ScreenDrug[] excludeDrugsWithSameScientificName(List<FdbDrugList> druglist) {
		List<ScreenDrug> screenDrugsList = new ArrayList<>();
		ScreenDrug[] screenDrugs = null;
		List<FdbDrugList> duplicateTherapyDrugList = druglist.stream()
				.filter(drug1 -> druglist.stream()
						.noneMatch(drug2 -> drug1.getScientificName().equalsIgnoreCase(drug2.getScientificName())
								&& !drug1.getDrugCode().equalsIgnoreCase(drug2.getDrugCode())))
				.collect(Collectors.toList());
		duplicateTherapyDrugList.forEach(drug -> {
			ScreenDrug screenDrug = drug.getDispensableGeneric().toScreenDrug();
			screenDrugsList.add(screenDrug);
		});
		if (!screenDrugsList.isEmpty()) {
			screenDrugs = new ScreenDrug[screenDrugsList.size()];
			screenDrugs = screenDrugsList.toArray(screenDrugs);
		}
		return screenDrugs;
	}

	private FdbResponse mapDuplicateTherapyResult(FdbRequest fdbRequest,
			DuplicateTherapyScreenResult[] duplicatedTherapy) {

		FdbResponse duplicateTherapyResponse = new FdbResponse();
		duplicateTherapyResponse.setRequestId(fdbRequest.getRequestId());
		List<FdbDrugResult> duplicateTherapyValidationResults = new ArrayList<>();

		for (DuplicateTherapyScreenResult result : duplicatedTherapy) {
			if (!result.getScreenMessage().toString().contains(
					"not screened in Duplicate Therapy because it does not meet inclusion criteria for screening")) {

				// 1- Convert screen drugs to SFDA Codes
				List<String> duplicatedDrugSfdaCodes = convertScreenDrugsToSfdaCodes(result.getDrugs(),
						fdbRequest.getDrugList());

				duplicatedDrugSfdaCodes.forEach(duplicatedDrugSfdaCode -> {

					// 2- set Drug Result
					for (FdbDrugList reqDrug : fdbRequest.getDrugList()) {
						if (reqDrug.getIsDrugCodeMappedFromScientificCode() == false
								&& reqDrug.getDrugCode().equalsIgnoreCase(duplicatedDrugSfdaCode)) {
							setDuplicateTherapyValidationResults(duplicateTherapyValidationResults, reqDrug,
									duplicatedDrugSfdaCodes);
							break;
						} else if (reqDrug.getIsDrugCodeMappedFromScientificCode() == true
								&& reqDrug.getScientificCode().equalsIgnoreCase(duplicatedDrugSfdaCode)) {
							setDuplicateTherapyValidationResults(duplicateTherapyValidationResults, reqDrug,
									duplicatedDrugSfdaCodes);
							break;
						}
					}

				});
			}
		}

		duplicateTherapyResponse.setDrugResults(duplicateTherapyValidationResults);
		return duplicateTherapyResponse;

	}

	private List<String> convertScreenDrugsToSfdaCodes(ScreenDrug[] screenDrugs, List<FdbDrugList> fdbDrugList) {

		List<FdbDrugList> clonedDrugList = new ArrayList<>();
		clonedDrugList.addAll(fdbDrugList);
		List<ScreenDrug> duplicatedScreenDrugs = Arrays.asList(screenDrugs);
		List<String> duplicatedDrugCodes = new ArrayList<>();

		duplicatedScreenDrugs.forEach(duplicatedScreenDrug -> {
			for (FdbDrugList reqDrug : clonedDrugList) {
				if (reqDrug.getDispensableGeneric().toString().equalsIgnoreCase(duplicatedScreenDrug.toString())) {
					duplicatedDrugCodes
							.add(reqDrug.getIsDrugCodeMappedFromScientificCode() == true ? reqDrug.getScientificCode()
									: reqDrug.getDrugCode());
					clonedDrugList.remove(reqDrug);
					break;
				}
			}
		});

		return duplicatedDrugCodes;
	}

	private void setDuplicateTherapyValidationResults(List<FdbDrugResult> duplicateTherapyValidationResults,
			FdbDrugList reqDrug, List<String> duplicatedDrugSfdaCodes) {

		Boolean isDrugResultUpdated = false;

		if (!duplicateTherapyValidationResults.isEmpty()) {
			for (FdbDrugResult duplicateTherapyResult : duplicateTherapyValidationResults) {
				if (reqDrug.getIsDrugCodeMappedFromScientificCode() == false
						&& duplicateTherapyResult.getDrugInfo().getDrugCode().equalsIgnoreCase(reqDrug.getDrugCode())) {
					duplicateTherapyResult.getRejectionReason().addAll(setRejectionReason(
							duplicateTherapyResult.getDrugInfo().getDrugCode(), duplicatedDrugSfdaCodes));
					isDrugResultUpdated = true;
					break;
				} else if (reqDrug.getIsDrugCodeMappedFromScientificCode() == true && duplicateTherapyResult
						.getDrugInfo().getScientificCode().equalsIgnoreCase(reqDrug.getScientificCode())) {
					duplicateTherapyResult.getRejectionReason().addAll(setRejectionReason(
							duplicateTherapyResult.getDrugInfo().getDrugCode(), duplicatedDrugSfdaCodes));
					isDrugResultUpdated = true;

				}
			}
		}

		if (duplicateTherapyValidationResults.isEmpty() || isDrugResultUpdated == false) {
			setFdbDrugResult(duplicateTherapyValidationResults, reqDrug, duplicatedDrugSfdaCodes);
		}

	}

	private void setFdbDrugResult(List<FdbDrugResult> duplicateTherapyValidationResults, FdbDrugList reqDrug,
			List<String> duplicatedDrugSfdaCodes) {
		FdbDrugResult drugResult = new FdbDrugResult();
		drugResult.setDrugInfo(reqDrug);
		drugResult.setStatus(ServiceStatus.REJECTED.value());
		drugResult.setRejectionReason(
				setRejectionReason(reqDrug.getIsDrugCodeMappedFromScientificCode() == true ? reqDrug.getScientificCode()
						: reqDrug.getDrugCode(), duplicatedDrugSfdaCodes));
		duplicateTherapyValidationResults.add(drugResult);
	}

	private List<Error> setRejectionReason(String reqDrugCode, List<String> duplicatedDrugSfdaCodes) {
		List<Error> rejectionReasons = new ArrayList<>();
		for (String duplicatedDrugCode : duplicatedDrugSfdaCodes) {
			if (!duplicatedDrugCode.equalsIgnoreCase(reqDrugCode)) {
				Error rejReason = new Error();
				rejReason.setCode(FdbRejectionCodes.DUPLICATE_THERAPY_CODE.value());
				rejReason.setDescription(
						commonRejectionReasonRepo.findByRejectionCode(FdbRejectionCodes.DUPLICATE_THERAPY_CODE.value())
								.replace("<DrugName> (<DrugCode>)", reqDrugCode)
								.replace("<DrugName> (<DuplicatedDrugCode>)", duplicatedDrugCode));
				rejectionReasons.add(rejReason);
			}
		}
		return rejectionReasons;
	}

}
