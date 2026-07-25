package com.waseel.emailservice.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.waseel.emailservice.response.EmailSenderResponseModel;

@Service
public class TechnicalValidationService {

    private static final String FAILED_STRING = "Failed";
    private static final String STR_INVALID = "Invalid";

    public EmailSenderResponseModel populateFailedEmailSenderResponse() {
        EmailSenderResponseModel invalidResponse = new EmailSenderResponseModel();
        invalidResponse.setStatus(FAILED_STRING);
        invalidResponse.setStatusDescription(HttpStatus.INTERNAL_SERVER_ERROR.name());
        return invalidResponse;
    }

    public EmailSenderResponseModel populateInvalidPrescriptionResponse(MethodArgumentNotValidException ex) {
        EmailSenderResponseModel invalidResponse = new EmailSenderResponseModel();
        invalidResponse.setStatus(STR_INVALID);
        Set<String> errors = new HashSet<>();
        ex.getBindingResult().getAllErrors().forEach(error -> errors.add(error.getDefaultMessage()));
        String error = errors.toString().replace("[", "").replace("]", "");
        invalidResponse.setStatusDescription(error);
        return invalidResponse;
    }
}
