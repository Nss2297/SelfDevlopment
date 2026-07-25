package com.waseel.pbm.payercustomizationservice.controller;

import com.waseel.pbm.payercustomizationservice.exceptions.PayerCustomizationException;
import com.waseel.pbm.payercustomizationservice.model.*;
import com.waseel.pbm.payercustomizationservice.service.AuditService;
import com.waseel.pbm.payercustomizationservice.service.CustomizationListService;
import com.waseel.pbm.payercustomizationservice.service.CustomizationRequestService;
import com.waseel.pbm.payercustomizationservice.service.CustomizationResponseService;
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

@RestController
@RequestMapping("/customizations/requests")
public class CustomizationRequestController {

    private final Logger logger = LoggerFactory.getLogger(CustomizationRequestController.class);

    @Autowired
    private CustomizationListService customizationListService;
    @Autowired
    private CustomizationRequestService customizationRequestService;
    @Autowired
    private CustomizationResponseService customizationResponseService;
    @Autowired
    private AuditService auditService;

    @GetMapping
    @PreAuthorize("@securityService.hasAccessToGetCustomizationRequest(authentication)")
    public ResponseEntity<Page<CustomizationListingResponse>> getCustomizationRequestList(
            CustomizationSearchModel customizationSearchModel) {
        return ResponseEntity.ok(customizationListService.getCustomizationRequests(customizationSearchModel));
    }

    @DeleteMapping("/{customizationRequestId}")
    @PreAuthorize("@securityService.hasAccessToDeleteCustomizationRequest(authentication)")
    public ResponseEntity<DeleteResponseModel> deleteCustomization(@PathVariable Long customizationRequestId) {
        return ResponseEntity.ok(customizationListService.deleteCustomizationRequest(customizationRequestId));
    }

    @PostMapping
    @PreAuthorize("@securityService.hasAccessToAddCustomizationRequest(authentication)")
    public ResponseEntity<CustomizationResponseModel> addPayerCustomizationRequest(
            @RequestBody CustomizationRequestModel customizationRequestModel, HttpServletRequest httpServletRequest)
            throws PayerCustomizationException {
        logger.info("Customization request received for ePrescriptionReferenceNo: {}",
                customizationRequestModel.getePrescriptionReferenceNo());
        return ResponseEntity.ok(customizationRequestService.managePayerCustomizationRequest(customizationRequestModel,
                httpServletRequest));
    }

    @PutMapping("/{customizationRequestId}")
    @PreAuthorize("@securityService.hasAccessToUpdateCustomizationRequest(authentication)")
    public ResponseEntity<CustomizationResponseModel> updateCustomizationRequest(
            @RequestBody CustomizationRequestModel customizationRequestModel, @PathVariable Long customizationRequestId)
            throws PayerCustomizationException {
        auditService.saveUpdateCustomizationRequestData(customizationRequestModel, customizationRequestId);
        customizationListService.updateCustomizationRequest(customizationRequestModel, customizationRequestId);
        return ResponseEntity.ok().build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PayerCustomizationException.class)
    public ResponseEntity<Object> handlePayerCustomizationException(
            PayerCustomizationException payerCustomizationException) {
        if (null != payerCustomizationException.getInvalidCustomizationResponse()
                && null != payerCustomizationException.getInvalidCustomizationResponse().getCustomizationRequestId()) {
            logger.error(
                    "{} Http Response for CustomizationRequestId: [{}] Has been Returned From payer-customization-service.",
                    HttpStatus.BAD_REQUEST.value(),
                    payerCustomizationException.getInvalidCustomizationResponse().getCustomizationRequestId());
        } else {
            logger.error("{} Http Response returned from payer-customization-service with exception: ",
                    HttpStatus.BAD_REQUEST.value(), payerCustomizationException);
        }
        return ResponseEntity.badRequest().body(payerCustomizationException.getInvalidCustomizationResponse());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(AccessDeniedException ex,
                                                              ContentCachingRequestWrapper requestWrapper,
                                                              HttpServletRequest request) {
        logger.error("AccessDenied Exception: {} Http Response Has been Returned From payer-customization-service",
                HttpStatus.FORBIDDEN.value(), ex);
        return new ResponseEntity<>(customizationResponseService.unAuthorizedCustomizationResponse(ex),
                HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> handleValidationExceptions(Exception ex) {
        ex.printStackTrace();
        logger.error("Internal Server Error {} : Has Been Returned From payer-customization-service," + " exception : {} ",
                HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getCause());
        return ResponseEntity.internalServerError().body(customizationResponseService.failedCustomizationResponse(ex));
    }
}
