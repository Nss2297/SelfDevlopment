package com.waseel.pbm.idfvalidationservice.service.screeningservice;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.pbm.idfvalidationservice.model.DrugList;
import com.waseel.pbm.idfvalidationservice.model.DssRequest;
import com.waseel.pbm.idfvalidationservice.model.EnumTypes.IdfRejectionCode;
import com.waseel.pbm.idfvalidationservice.model.Error;
import com.waseel.pbm.idfvalidationservice.persist.IDFDrugToAge;
import com.waseel.pbm.idfvalidationservice.repository.CommonRejectionReasonRepository;
import com.waseel.pbm.idfvalidationservice.repository.IDFDrugToAgeRepository;
import com.waseel.pbm.idfvalidationservice.service.PatientAgeConverterService;

@Service
public class DrugToAgeService {
    @Autowired
    IDFDrugToAgeRepository idfDrugToAgeRepository;
    @Autowired
    CommonRejectionReasonRepository commonRejectionReasonRepo;
    @Autowired
    private PatientAgeConverterService ageConverterService;

    public void validate(DssRequest dssRequest, DrugList drug, List<Error> errorList) {
        List<Error> errors = validateDrugToAge(dssRequest, drug);
        if (!errors.isEmpty() && errors.size() > 0)
            errorList.addAll(errors);
    }

    private List<Error> validateDrugToAge(DssRequest request, DrugList drug) {
        List<Error> drugToAgeErrors = new ArrayList<>();
        Optional<IDFDrugToAge> idfDrugToAgeInteraction = idfDrugToAgeRepository
                .findByServiceCode(drug.getNdcDrugCode());
        if (idfDrugToAgeInteraction.isPresent()) {
            Integer ageInDays = ageConverterService.patientAgeConverter(request.getDateOfBirth());
            List<Error> errorList = null;
            if (ageInDays != null && !(ageInDays >= Integer.valueOf(idfDrugToAgeInteraction.get().getFromAgeInDays())
                    && ageInDays <= Integer.valueOf(idfDrugToAgeInteraction.get().getToAgeInDays()))) {
                errorList = populateServiceErrorsList(drug.getNdcDrugCode());
            }
            if (errorList != null && !errorList.isEmpty())
                drugToAgeErrors.addAll(errorList);
        }
        return drugToAgeErrors;
    }

    public List<Error> populateServiceErrorsList(String serviceCode) {
        List<Error> serviceErrorList = new ArrayList<>();
        Error serviceError = new Error();
        serviceError.setCode(IdfRejectionCode.DRUG_TO_AGE_MISMATCH.value());
        serviceError.setDescription(prepareRejectionDescription(serviceCode));
        serviceErrorList.add(serviceError);
        return serviceErrorList;
    }

    private String prepareRejectionDescription(String serviceCode) {
        String rejectionDescription = commonRejectionReasonRepo
                .findByRejectionCode(IdfRejectionCode.DRUG_TO_AGE_MISMATCH.value());
        return rejectionDescription.replace("<DrugName> (<DrugCode>)", serviceCode);
    }

}
