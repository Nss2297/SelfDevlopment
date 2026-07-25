package com.waseel.pbm.idfvalidationservice.service.screeningservice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waseel.pbm.idfvalidationservice.model.DrugList;
import com.waseel.pbm.idfvalidationservice.model.DssRequest;
import com.waseel.pbm.idfvalidationservice.model.EnumTypes.IdfRejectionCode;
import com.waseel.pbm.idfvalidationservice.model.Error;
import com.waseel.pbm.idfvalidationservice.persist.IDFDrugToGenderInteraction;
import com.waseel.pbm.idfvalidationservice.repository.CommonRejectionReasonRepository;
import com.waseel.pbm.idfvalidationservice.repository.IDFDrugToGenderInteractionRepository;

@Service
public class DrugToGenderService {

    @Autowired
    IDFDrugToGenderInteractionRepository idfDrugToGenderInteractionRepository;
    @Autowired
    CommonRejectionReasonRepository commonRejectionReasonRepo;

    public void validate(DssRequest request, DrugList drug, List<Error> errorList) {
        List<Error> errors = validateDrugToGender(request, drug);
        if (!errors.isEmpty())
            errorList.addAll(errors);
    }

    private List<Error> validateDrugToGender(DssRequest request, DrugList drug) {
        List<Error> drugToGenderErrors = new ArrayList<>();
        IDFDrugToGenderInteraction idfDrugToGenderInteraction = idfDrugToGenderInteractionRepository
                .findByServiceCode(drug.getNdcDrugCode());
        if (idfDrugToGenderInteraction != null) {
            List<Error> errorList = null;
            if (!idfDrugToGenderInteraction.getGender().equalsIgnoreCase(request.getMemberGender())) {
                errorList = populateServiceErrorsList(idfDrugToGenderInteraction.getGender()+"__EXCLUSIVE",drug.getNdcDrugCode());
            }
            if (errorList != null && !errorList.isEmpty())
                drugToGenderErrors.addAll(errorList);
        }
        return drugToGenderErrors;
    }

    public List<Error> populateServiceErrorsList(String genderCondition,String serviceCode) {
        List<Error> serviceErrorList = new ArrayList<>();
        Error serviceError = new Error();
        serviceError.setCode(IdfRejectionCode.DRUG_TO_GENDER_MISMATCH.value());
        serviceError.setDescription(prepareRejectionDescription(genderCondition,serviceCode));
        serviceErrorList.add(serviceError);
        return serviceErrorList;
    }

    private String prepareRejectionDescription(String genderCondition,String serviceCode) {
        String rejectionDescription = commonRejectionReasonRepo
                .findByRejectionCode(IdfRejectionCode.DRUG_TO_GENDER_MISMATCH.value());
        return rejectionDescription.replace("<Condition>", genderCondition).replace("<DrugName> (<DrugCode>)", serviceCode);
    }

}
