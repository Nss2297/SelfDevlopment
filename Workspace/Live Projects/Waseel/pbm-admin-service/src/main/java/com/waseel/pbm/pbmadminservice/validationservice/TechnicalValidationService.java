package com.waseel.pbm.pbmadminservice.validationservice;

import com.waseel.pbm.pbmadminservice.model.SFDAResponseModel;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Service
public class TechnicalValidationService {

    private static final String INVALID = "Invalid";

    public SFDAResponseModel populateInvalidResponse(MethodArgumentNotValidException ex) {
        SFDAResponseModel sfdaResponseModel = new SFDAResponseModel();
        sfdaResponseModel.setErrorCode(INVALID);
        var ref = new Object() {
            String errors = "";
        };
        ex.getBindingResult().getAllErrors().forEach(error -> {
            if (ref.errors.isEmpty()) {
                ref.errors = error.getDefaultMessage();
            } else {
                ref.errors += ", " + error.getDefaultMessage();
            }
        });
        sfdaResponseModel.setErrorDescription(ref.errors);
        return sfdaResponseModel;
    }

    public SFDAResponseModel populateInvalidResponse(Exception ex) {
        SFDAResponseModel sfdaResponseModel = new SFDAResponseModel();
        sfdaResponseModel.setErrorCode(INVALID);
        sfdaResponseModel.setErrorDescription(ex.getMessage());
        return sfdaResponseModel;
    }
}