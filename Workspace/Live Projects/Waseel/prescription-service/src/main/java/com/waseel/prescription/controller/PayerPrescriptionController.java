package com.waseel.prescription.controller;

import com.waseel.prescription.model.pbmpayerapis.PayerPrescriptionRequestModel;
import com.waseel.prescription.model.pbmpayerapis.PayerPrescriptionResponseModel;
import com.waseel.prescription.model.prescription.PrescriptionResponseModel;
import com.waseel.prescription.service.prescriptions.PayerPrescriptionService;
import com.waseel.prescription.service.validation.TechnicalValidationService;
import io.swagger.v3.oas.annotations.Hidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
@RequestMapping(value = "/prescriptions/payers/{payerId}")
@Hidden
public class PayerPrescriptionController {

    private final Logger log = LoggerFactory.getLogger(PayerPrescriptionController.class);

    @Autowired
    private PayerPrescriptionService payerPrescriptionService;
    @Autowired
    private TechnicalValidationService technicalValidationService;

    @GetMapping
    @PreAuthorize("@securityService.hasPayerViewPrescriptionAccess(authentication)")
    public ResponseEntity<Page<PayerPrescriptionResponseModel>> getProvidersList(
            PayerPrescriptionRequestModel requestModel,
            @PathVariable("payerId") String payerId) {
        requestModel.setPayerId(payerId);
        return ResponseEntity.ok(payerPrescriptionService.getProvidersList(requestModel));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(AccessDeniedException ex,
                                                              ContentCachingRequestWrapper requestWrapper,
                                                              HttpServletRequest request) {
        PrescriptionResponseModel invalidResponse = technicalValidationService
                .populateUnautorizedPrescriptionResponse(ex, requestWrapper, request);
        log.info("{} Http Response Has been Returned From Prescription-Service", invalidResponse.getHttpStatusCode());
        return new ResponseEntity<>(invalidResponse, HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleValidationExceptions(Exception ex, ContentCachingRequestWrapper requestWrapper,
                                                             HttpServletRequest request) {
        ex.printStackTrace();
        PrescriptionResponseModel invalidDssResponse = technicalValidationService
                .populateFailedPrescriptionResponse(requestWrapper, request);
        log.info("Internal Server Error 500 : Has Been Returned From Prescription-Service,"
                + " for Request Id: {} Due To : {} ", invalidDssResponse.getRequestId(), ex.getCause());
        return new ResponseEntity<>(invalidDssResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
