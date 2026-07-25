package com.waseel.pbm.idfvalidationservice.service;

import com.waseel.pbm.idfvalidationservice.enums.ScreeningModules;
import com.waseel.pbm.idfvalidationservice.exceptions.DssException;
import com.waseel.pbm.idfvalidationservice.model.DrugList;
import com.waseel.pbm.idfvalidationservice.model.DssRequest;
import com.waseel.pbm.idfvalidationservice.model.DssResponse;
import com.waseel.pbm.idfvalidationservice.repository.PayerModuleConfigurationRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ValidationService {

    @Autowired
    private PayerModuleConfigurationRepository modulesConfigurationRepo;

    /**
     * It TRUE whenever request is valid otherwise it's false Used to store
     * requestId if request is valid
     */
    private boolean flag = false;

    public boolean validate(DssRequest dssRequest) throws DssException {
        daysOfSupplyValidation(dssRequest);
        return flag;
    }

    private void daysOfSupplyValidation(DssRequest rtsRequest) throws DssException {
        flag = true;
        String isQLCEnabled = modulesConfigurationRepo
                .findByPayerIdAndModuleId(rtsRequest.getPayerId(), ScreeningModules.IDF_QUANTITY_LIMIT_CHECK.value().doubleValue());
        if (isQLCEnabled != null && isQLCEnabled.equalsIgnoreCase("1")) {
            for (DrugList drug : rtsRequest.getDrugList()) {
                if (StringUtils.isBlank(drug.getDaysOfSupply())) {
                    flag = false;
                    throw new DssException(populateInvalidRtsResponse(rtsRequest,
                            "DaysOfSupply : Should not be Null or Empty."));
                }
            }
        }
    }

    private DssResponse populateInvalidRtsResponse(DssRequest dssRequest, String errorMessage) {
        DssResponse invalidRtsResponse = new DssResponse();
        invalidRtsResponse.setRequestId(dssRequest.getRequestId());
        invalidRtsResponse.setStatus("Invalid");
        List<String> errors = new ArrayList<>();
        errors.add(errorMessage);
        invalidRtsResponse.setErrors(errors);
        invalidRtsResponse.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
        invalidRtsResponse.setHttpStatusDescription(errors.toString());
        return invalidRtsResponse;
    }
}
