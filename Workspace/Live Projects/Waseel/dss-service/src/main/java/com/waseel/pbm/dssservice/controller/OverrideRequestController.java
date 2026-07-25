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
import com.waseel.pbm.dssservice.service.dssservice.OverrideRequestService;
import com.waseel.pbm.dssservice.service.validationservice.TechnicalValidationService;

@RestController
public class OverrideRequestController {

	private final Logger log = LoggerFactory.getLogger(OverrideRequestController.class);

	@Autowired
	private TechnicalValidationService validationService;
	
	@Autowired
	private OverrideRequestService overrideRequestService;
	
	private static final String STR_HTTPRESMSG = "{} Http Response for Request Id: {} Has been Returned From Dss-Service Override API";
	
	@PostMapping("/validate/override")
//	@PreAuthorize("@securityService.hasDssOverrideAccess(authentication)")
	public ResponseEntity<CancelOverrideResponse> validateOverrideRequest(@Valid @RequestBody CancellationOverrideRequest overrideRequest, ContentCachingRequestWrapper requestWrapper) throws DssException {
		log.info("Request Id:"+ overrideRequest.getRequestId()+" Has Been received in Dss-Service Override API");
		validationService.validateCancellationOverrideReqId(overrideRequest, requestWrapper, RequestType.OVERRIDE);
		CancelOverrideResponse response = overrideRequestService.manageOverrideRequest(overrideRequest,requestWrapper,RequestType.OVERRIDE);
		log.info("Valid Response Has been Returned From Dss-Service Override API Request Id: "+ overrideRequest.getRequestId());
		return ResponseEntity.ok().body(response);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex,
			ContentCachingRequestWrapper request) {
		DssResponse invalidDssReponse = validationService.populateInvalidDssResponse(ex, request, RequestType.OVERRIDE);
		log.info(STR_HTTPRESMSG,invalidDssReponse.getHttpStatusCode(),invalidDssReponse.getRequestId());
		return ResponseEntity.badRequest().body(invalidDssReponse);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DssException.class)
	ResponseEntity<Object> handleValidationExceptions(DssException ex) {
		log.info(STR_HTTPRESMSG,ex.getDssInvalidResponse().getHttpStatusCode(),ex.getDssInvalidResponse().getRequestId());
		return ResponseEntity.badRequest().body(ex.getDssInvalidResponse());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	ResponseEntity<Object> handleValidationExceptions(HttpMessageNotReadableException ex, ContentCachingRequestWrapper request) {
		DssResponse invalidDssReponse = validationService.populateHTTPMsgInvalidDssResponse(request, RequestType.OVERRIDE);
		log.info(STR_HTTPRESMSG,invalidDssReponse.getHttpStatusCode(),invalidDssReponse.getRequestId());
		ex.printStackTrace();
		return ResponseEntity.badRequest().body(invalidDssReponse);
	}
	
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	ResponseEntity<Object> handleValidationExceptions(Exception ex, ContentCachingRequestWrapper request) {
		DssResponse invalidDssReponse = validationService.populateInvalidDssResponse(request, RequestType.OVERRIDE);
		log.info("Internal Server Error 500 : Has Been Returned From Dss-Service Override API , for Request Id:" + invalidDssReponse.getRequestId() + "Due To : " + ex.getCause());
		return ResponseEntity.badRequest().body(invalidDssReponse);
	}

}
