package com.waseel.pbm.fdbvalidationservice.service.screeningservice;

import com.fdb.mkfi.core.ArgumentException;
import com.fdb.mkfi.screening.*;
import com.waseel.pbm.fdbvalidationservice.enums.FdbRejectionCodes;
import com.waseel.pbm.fdbvalidationservice.enums.ServiceStatus;
import com.waseel.pbm.fdbvalidationservice.model.Error;
import com.waseel.pbm.fdbvalidationservice.model.*;
import com.waseel.pbm.fdbvalidationservice.repository.mdss.CommonRejectionReasonRepository;
import com.waseel.pbm.fdbvalidationservice.repository.mdss.FDBPediatricSeverityLevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DrugToAgeInteractionService {

    @Autowired
    CommonRejectionReasonRepository commonRejectionReasonRepo;

    @Autowired
    FDBPediatricSeverityLevelRepository fdbPediatricSeverityLevelRepository;

    public FdbResponse validate(FdbRequest fdbRequest) throws ArgumentException {

        FdbResponse drugAgeValidationResponse = new FdbResponse();
        drugAgeValidationResponse.setRequestId(fdbRequest.getRequestId());
        List<FdbDrugResult> drugAgeValidationResults = null;
        if (fdbRequest.getPatientProfile().getFdbProfile().getAge().getAgeInYears() < 18) {
            drugAgeValidationResults = validateDrugToPediatricPrecaution(fdbRequest, fdbRequest.getDrugList());
        } else if (fdbRequest.getPatientProfile().getFdbProfile().getAge().getAgeInYears() > 64) {
            drugAgeValidationResults = validateDrugToGeriatricPrecaution(fdbRequest.getPatientProfile().getFdbProfile(),
                    fdbRequest.getDrugList());
        }
        if (drugAgeValidationResults != null && !drugAgeValidationResults.isEmpty()) {
            drugAgeValidationResponse.setDrugResults(drugAgeValidationResults);
            return drugAgeValidationResponse;
        }
        return null;
    }

    private List<FdbDrugResult> validateDrugToPediatricPrecaution(FdbRequest fdbRequest,
                                                                  List<FdbDrugList> reqDrugList)
            throws ArgumentException {
        List<FdbDrugList> clonedDrugList = new ArrayList<>();
        clonedDrugList.addAll(reqDrugList);
        List<FdbDrugResult> drugPediatricValidationResults = new ArrayList<>();
        if (fdbRequest.getPrecautionProfile().getDrugs().length > 0) {
            PediatricScreenResult[] pediatricScreenResult =
                    Screening.pediatricScreen(fdbRequest.getPrecautionProfile(), false,
                            PediatricSeverityCode.PRECAUTION, new CustomExclusionInclusionCategories("", "", ""));
            validateDrugPediatric(pediatricScreenResult, clonedDrugList, drugPediatricValidationResults);
        }
        if (fdbRequest.getNonPrecautionProfile().getDrugs().length > 0) {
            PediatricScreenResult[] pediatricScreenResult =
                    Screening.pediatricScreen(fdbRequest.getNonPrecautionProfile(), false,
                            PediatricSeverityCode.SEVERE_PRECAUTION, new CustomExclusionInclusionCategories("", "", ""));
            validateDrugPediatric(pediatricScreenResult, clonedDrugList, drugPediatricValidationResults);
        }
        return drugPediatricValidationResults;
    }

    private void validateDrugPediatric(PediatricScreenResult[] pediatricScreenResult,
                                       List<FdbDrugList> clonedDrugList,
                                       List<FdbDrugResult> drugPediatricValidationResults) {
        if (pediatricScreenResult.length > 0) {
            for (PediatricScreenResult drugPediatricResult : pediatricScreenResult) {
                for (ScreenDrug screenDrug : drugPediatricResult.getDrugs()) {
                    for (FdbDrugList reqDrug : clonedDrugList) {
                        if (reqDrug.getDispensableGeneric().toString().equals(screenDrug.toString())) {
                            FdbDrugResult drugResult = new FdbDrugResult();
                            drugResult.setDrugInfo(reqDrug);
                            drugResult.setStatus(ServiceStatus.REJECTED.value());
                            drugResult.setRejectionReason(setRejectionReasons(reqDrug.getIsDrugCodeMappedFromScientificCode()==true? reqDrug.getScientificCode():reqDrug.getDrugCode()));
                            drugPediatricValidationResults.add(drugResult);
                            /* Need to remove from clonedDrugList because of break,
                             *otherwise it consider only first element always*/
                            clonedDrugList.remove(reqDrug);
                            break;
                        }
                    }
                }
            }
        }
    }

    private List<FdbDrugResult> validateDrugToGeriatricPrecaution(FDBProfile fdbProfile,
                                                                  List<FdbDrugList> reqDrugList) {
        List<FdbDrugResult> drugGeriatricValidationResults = new ArrayList<>();
        GeriatricScreenResult[] geriatricScreenResult = Screening.geriatricScreen(fdbProfile, false,
                GeriatricSeverityCode.ABSOLUTE_CONTRAINDICATION, new CustomExclusionInclusionCategories("", "", ""));
        if (geriatricScreenResult.length > 0) {
            for (GeriatricScreenResult drugGeriatricResult : geriatricScreenResult) {
                for (FdbDrugList reqDrug : reqDrugList) {
                    if (reqDrug.getDispensableGeneric().toString().equals(Arrays.toString(drugGeriatricResult.getDrugs()))) {
                        FdbDrugResult drugResult = new FdbDrugResult();
                        drugResult.setDrugInfo(reqDrug);
                        drugResult.setStatus(ServiceStatus.REJECTED.value());
                        drugResult.setRejectionReason(
                                setRejectionReasons(reqDrug.getIsDrugCodeMappedFromScientificCode()==true? reqDrug.getScientificCode():reqDrug.getDrugCode()));
                        drugGeriatricValidationResults.add(drugResult);
                        break;
                    }
                }
            }
        }
        return drugGeriatricValidationResults;
    }

    private List<Error> setRejectionReasons(String drugCode) {
        List<Error> ageRejections = new ArrayList<>();
        Error ageRejection = new Error();
        ageRejection.setCode(FdbRejectionCodes.DRUG_TO_AGE_REJECTIONCODE.value());
        ageRejection.setDescription(
                commonRejectionReasonRepo.findByRejectionCode(FdbRejectionCodes.DRUG_TO_AGE_REJECTIONCODE.value())
                        .replace("<DrugName> (<DrugCode>)", drugCode));
        ageRejections.add(ageRejection);
        return ageRejections;
    }
}
