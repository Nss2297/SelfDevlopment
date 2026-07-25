package com.waseel.pbm.fdbvalidationservice.service.screeningservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.pbm.fdbvalidationservice.enums.FdbRejectionCodes;
import com.waseel.pbm.fdbvalidationservice.enums.Gender;
import com.waseel.pbm.fdbvalidationservice.enums.ServiceStatus;
import com.waseel.pbm.fdbvalidationservice.model.Error;
import com.waseel.pbm.fdbvalidationservice.model.FdbDrugList;
import com.waseel.pbm.fdbvalidationservice.model.FdbDrugResult;
import com.waseel.pbm.fdbvalidationservice.model.FdbRequest;
import com.waseel.pbm.fdbvalidationservice.model.FdbResponse;
import com.waseel.pbm.fdbvalidationservice.repository.mdss.CommonRejectionReasonRepository;

@Service
public class DrugToGenderInteractionService {

	@Autowired
	CommonRejectionReasonRepository commonRejectionReasonRepo;

	public FdbResponse validate(FdbRequest fdbRequest) {
		FdbResponse drugGenderValidationResponse = new FdbResponse();
		drugGenderValidationResponse.setRequestId(fdbRequest.getRequestId());
		List<FdbDrugResult> drugGenderValidationResults = new ArrayList<>();

		for (FdbDrugList reqDrug : fdbRequest.getDrugList()) {
			List<Error> genderRejections = validateDrugToGenderInteraction(
					fdbRequest.getPatientProfile().getPatientGender(),
					reqDrug.getDispensableGeneric().getGenderSpecificDrugCode().getCodeValue(), reqDrug.getIsDrugCodeMappedFromScientificCode()==true? reqDrug.getScientificCode():reqDrug.getDrugCode());

			if (!genderRejections.isEmpty()) {
				FdbDrugResult drugResult = new FdbDrugResult();
				drugResult.setDrugInfo(reqDrug);
				drugResult.setStatus(ServiceStatus.REJECTED.value());
				drugResult.setRejectionReason(genderRejections);
				drugGenderValidationResults.add(drugResult);
			}
		}
		if (!drugGenderValidationResults.isEmpty()) {
			drugGenderValidationResponse.setDrugResults(drugGenderValidationResults);
			return drugGenderValidationResponse;
		}
		return null;
	}

	private List<Error> validateDrugToGenderInteraction(String memgerGender, String genderSpecificDrugCodeValue,
			String drugCode) {
		if (genderSpecificDrugCodeValue.equals("1") && memgerGender.equalsIgnoreCase(Gender.FEMALE.value())) {
			// Used execlusivley in male
			return setRejectionReasons("MALE_EXCLUSIVE", drugCode);
		} else if (genderSpecificDrugCodeValue.equals("3") && memgerGender.equalsIgnoreCase(Gender.MALE.value())) {
			// Used execlusivley in female
			return setRejectionReasons("FEMALE_EXCLUSIVE", drugCode);
		}
		return new ArrayList<>();
	}

	private List<Error> setRejectionReasons(String genderSpecificDrug, String drugCode) {
		List<Error> genderRejectons = new ArrayList<>();
		Error genderRejecton = new Error();
		genderRejecton.setCode(FdbRejectionCodes.DRUG_TO_GENDER_REJECTIONCODE.value());
		genderRejecton.setDescription(
				commonRejectionReasonRepo.findByRejectionCode(FdbRejectionCodes.DRUG_TO_GENDER_REJECTIONCODE.value())
						.replace("<Condition>", genderSpecificDrug).replace("<DrugName> (<DrugCode>)", drugCode));
		genderRejectons.add(genderRejecton);
		return genderRejectons;
	}
}
