package com.waseel.pbm.dssservice.controller;

import com.waseel.pbm.dssservice.enums.RequestType;
import com.waseel.pbm.dssservice.exceptions.DssException;
import com.waseel.pbm.dssservice.model.DssRequest;
import com.waseel.pbm.dssservice.model.DssResponse;
import com.waseel.pbm.dssservice.service.dssservice.FollowupRequestService;
import com.waseel.pbm.dssservice.service.dssservice.NewRequestService;
import com.waseel.pbm.dssservice.service.managementservice.DMLService;
import com.waseel.pbm.dssservice.service.validationservice.TechnicalValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.validation.Valid;

@RestController
public class FollowupRequestController {

    private final Logger log = LoggerFactory.getLogger(FollowupRequestController.class);

    @Autowired
    private FollowupRequestService updateReqService;

    @Autowired
    private NewRequestService newRequestService;

    @Autowired
    TechnicalValidationService techValidationService;

    @Autowired
    DMLService dmlService;

    private static final String HTTPRESMSG = "{} Http Response for Request Id: {} Has been Returned From Dss-Service Followup API";

    @PutMapping("/validate/followup")
    public ResponseEntity<DssResponse> validateFollowUpDssRequest(@Valid @RequestBody DssRequest dssRequest,
                                                                  ContentCachingRequestWrapper requestWrapper) throws DssException {
        log.info("Request Id:" + dssRequest.getRequestId() + " Has Been received in Dss-Service Follwoup API");
        boolean isNewRequest = techValidationService.validateFollowupRequest(dssRequest, requestWrapper, RequestType.FOLLOWUP);
        DssResponse dssResponse = null;
        if (!isNewRequest) {
            dssResponse = updateReqService.manageFollowUpDssRequest(dssRequest, requestWrapper, RequestType.FOLLOWUP);
        } else {
            dssResponse = newRequestService.manageNewDssRequest(dssRequest, requestWrapper, RequestType.NEW);
        }
        log.info(HTTPRESMSG, dssResponse.getHttpStatusCode(), dssRequest.getRequestId());
        return ResponseEntity.ok().body(dssResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex,
                                                      ContentCachingRequestWrapper request) {
        DssResponse invalidDssResponse = techValidationService.populateInvalidDssResponse(ex, request, RequestType.FOLLOWUP);
        log.info(HTTPRESMSG, invalidDssResponse.getHttpStatusCode(), invalidDssResponse.getRequestId());
        return ResponseEntity.badRequest().body(invalidDssResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DssException.class)
    ResponseEntity<Object> handleValidationExceptions(DssException ex) {
        log.info(HTTPRESMSG, ex.getDssInvalidResponse().getHttpStatusCode(), ex.getDssInvalidResponse().getRequestId());
        return ResponseEntity.badRequest().body(ex.getDssInvalidResponse());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<Object> handleValidationExceptions(HttpMessageNotReadableException ex,
                                                      ContentCachingRequestWrapper request) {
        DssResponse invalidDssResponse = techValidationService.populateHTTPMsgInvalidDssResponse(request, RequestType.FOLLOWUP);
        log.info(HTTPRESMSG, invalidDssResponse.getHttpStatusCode(), invalidDssResponse.getRequestId());
        ex.printStackTrace();
        return ResponseEntity.badRequest().body(invalidDssResponse);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    ResponseEntity<Object> handleValidationExceptions(Exception ex, ContentCachingRequestWrapper request) {
        DssResponse invalidDssResponse = techValidationService.populateInvalidDssResponse(request, RequestType.FOLLOWUP);
        log.info("Internal Server Error 500 : Has Been Returned Form Dss-Service Followup API , for Request Id:" + invalidDssResponse.getRequestId() + "Due To : " + ex.getCause());
        return ResponseEntity.badRequest().body(invalidDssResponse);
    }

}
