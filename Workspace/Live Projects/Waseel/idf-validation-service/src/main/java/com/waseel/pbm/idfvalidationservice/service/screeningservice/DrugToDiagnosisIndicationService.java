package com.waseel.pbm.idfvalidationservice.service.screeningservice;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.pbm.idfvalidationservice.model.DrugList;
import com.waseel.pbm.idfvalidationservice.model.DssRequest;
import com.waseel.pbm.idfvalidationservice.model.EnumTypes.IdfRejectionCode;
import com.waseel.pbm.idfvalidationservice.model.Error;
import com.waseel.pbm.idfvalidationservice.repository.CommonRejectionReasonRepository;
import com.waseel.pbm.idfvalidationservice.repository.IdfDrugToDiagnosisIndicationsRepository;

@Service
public class DrugToDiagnosisIndicationService {

    @Autowired
    IdfDrugToDiagnosisIndicationsRepository idfIndicationsRepo;
    @Autowired
    CommonRejectionReasonRepository commonRejectionReasonRepo;

    public void validate(DssRequest request, DrugList drug, List<Error> errorList) {
        List<Error> errors = validateDrugToDiagnosisIndication(request, drug);
        if (!errors.isEmpty())
            errorList.addAll(errors);
    }

    private List<Error> validateDrugToDiagnosisIndication(DssRequest request, DrugList drug) {
        List<Error> drugToDiagnosisErrors = new ArrayList<>();
        List<String> allIndicatedICDs = idfIndicationsRepo.findByServiceCode(drug.getNdcDrugCode());
        if (allIndicatedICDs != null && !allIndicatedICDs.isEmpty()) {
            List<String> indicatedToICDsList = request.getIcdCodes().stream()
                    .filter(element -> allIndicatedICDs.contains(element)).collect(Collectors.toList());
            if (indicatedToICDsList == null || indicatedToICDsList.isEmpty()) {
                List<Error> validatedService = populateServiceErrorsList(drug.getNdcDrugCode(), request.getIcdCodes());
                drugToDiagnosisErrors.addAll(validatedService);
            }

        }

        return drugToDiagnosisErrors;
    }

    public List<Error> populateServiceErrorsList(String serviceCode, List<String> notIndicatedToIcdsList) {
        List<Error> serviceErrorList = new ArrayList<>();
        for (String IcdCode : notIndicatedToIcdsList) {
            Error serviceError = new Error();
            serviceError.setCode(IdfRejectionCode.DRUG_TO_DIAGNOSIS_INDICATION.value());
            serviceError.setDescription(prepareRejectionDescription(serviceCode, IcdCode));
            serviceErrorList.add(serviceError);
        }
        return serviceErrorList;
    }

    private String prepareRejectionDescription(String serviceCode, String icdCode) {
        String rejectionDescription = commonRejectionReasonRepo
                .findByRejectionCode(IdfRejectionCode.DRUG_TO_DIAGNOSIS_INDICATION.value());
        return rejectionDescription.replace("<DrugName> (<DrugCode>)", serviceCode).replace("<ICD>", icdCode);
    }

}
