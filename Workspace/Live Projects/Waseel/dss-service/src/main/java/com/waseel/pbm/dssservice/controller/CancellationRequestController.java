package com.waseel.pbm.dssservice.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.pbm.dssservice.enums.RequestType;
import com.waseel.pbm.dssservice.exceptions.DssException;
import com.waseel.pbm.dssservice.model.CancelOverrideResponse;
import com.waseel.pbm.dssservice.model.CancellationOverrideRequest;
import com.waseel.pbm.dssservice.model.DssResponse;
import com.waseel.pbm.dssservice.service.dssservice.CancellationRequestService;
import com.waseel.pbm.dssservice.service.validationservice.TechnicalValidationService;

@RestController
public class CancellationRequestController {

    private final Logger log = LoggerFactory.getLogger(CancellationRequestController.class);
    private static final String COMMON_LOG_MESSAGE =
            "{} Http Response for Request Id: {} Has been Returned From Dss-Service Cancellation API";

    @Autowired
    TechnicalValidationService validationService;

    @Autowired
    private CancellationRequestService cancellationRequestService;

    @PostMapping("/validate/cancellation")
//	@PreAuthorize("@securityService.hasDssCancellationAccess(authentication)")
    public ResponseEntity<CancelOverrideResponse> validateCancellationRequest(
            @Valid @RequestBody CancellationOverrideRequest cancelRequest,
            ContentCachingRequestWrapper requestWrapper) throws DssException {
        log.info("Request Id:{} Has Been received in Dss-Service Cancellation API", cancelRequest.getRequestId());
        validationService.validateCancellationOverrideReqId(cancelRequest, requestWrapper,
                RequestType.CANCELLATION);
        CancelOverrideResponse response = cancellationRequestService.manageCancellationRequest(cancelRequest
                , requestWrapper, RequestType.CANCELLATION);
        log.info("Valid Response Has been Returned From Dss-Service Cancellation API Request Id: {}",
                cancelRequest.getRequestId());
        return ResponseEntity.ok().body(response);
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex,
                                                      ContentCachingRequestWrapper request) {
        DssResponse invalidDssResponse = validationService.populateInvalidDssResponse(ex, request,
                RequestType.CANCELLATION);
        log.info(COMMON_LOG_MESSAGE, invalidDssResponse.getHttpStatusCode(), invalidDssResponse.getRequestId());
        return ResponseEntity.badRequest().body(invalidDssResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DssException.class)
    ResponseEntity<Object> handleValidationExceptions(DssException ex) {
        log.info(COMMON_LOG_MESSAGE, ex.getDssInvalidResponse().getHttpStatusCode()
                , ex.getDssInvalidResponse().getRequestId());
        return ResponseEntity.badRequest().body(ex.getDssInvalidResponse());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<Object> handleValidationExceptions(HttpMessageNotReadableException ex,
                                                      ContentCachingRequestWrapper request) {
        DssResponse invalidDssResponse = validationService.populateHTTPMsgInvalidDssResponse(request,
                RequestType.CANCELLATION);
        log.info(COMMON_LOG_MESSAGE, invalidDssResponse.getHttpStatusCode(), invalidDssResponse.getRequestId());
        ex.printStackTrace();
        return ResponseEntity.badRequest().body(invalidDssResponse);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> handleValidationExceptions(Exception ex, ContentCachingRequestWrapper request) {
        DssResponse invalidDssResponse = validationService.populateInvalidDssResponse(request,
                RequestType.CANCELLATION);
        log.info("Internal Server Error 500 : Has Been Returned From Dss-Service Cancellation API , " +
                        "for Request Id:{} Due To : {}"
                , invalidDssResponse.getRequestId(), ex.getCause());
        ex.printStackTrace();
        return ResponseEntity.badRequest().body(invalidDssResponse);
    }

}
