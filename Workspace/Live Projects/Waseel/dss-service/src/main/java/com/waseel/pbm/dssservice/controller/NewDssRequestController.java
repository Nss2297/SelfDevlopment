package com.waseel.pbm.dssservice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.pbm.dssservice.enums.RequestType;
import com.waseel.pbm.dssservice.exceptions.DssException;
import com.waseel.pbm.dssservice.model.DssRequest;
import com.waseel.pbm.dssservice.model.DssResponse;
import com.waseel.pbm.dssservice.service.dssservice.NewRequestService;
import com.waseel.pbm.dssservice.service.validationservice.TechnicalValidationService;

@RestController
@Validated
public class NewDssRequestController {

	private final Logger log = LoggerFactory.getLogger(NewDssRequestController.class);

	@Autowired
	private NewRequestService newRequestService;

	@Autowired
	TechnicalValidationService techValidationService;

	private static final String HTTPRESMSG = "{} Http Response for Request Id: {} Has been Returned From Dss-Service New API";

	@PostMapping("/validate/new")
	// @PreAuthorize("@securityService.hasDssNewAccess(authentication)")

	public ResponseEntity<DssResponse> validateNewDssRequest(@Valid @RequestBody DssRequest dssRequest,
			ContentCachingRequestWrapper requestWrapper, HttpServletRequest request) throws DssException {
		log.info("Request Id:" + dssRequest.getRequestId() + " Has Been received in Dss-Service New API");
		techValidationService.validateNewRequest(dssRequest, requestWrapper, RequestType.NEW);
		DssResponse dssResponse = newRequestService.manageNewDssRequest(dssRequest, requestWrapper, RequestType.NEW);
		log.info(HTTPRESMSG, dssResponse.getHttpStatusCode(), dssRequest.getRequestId());
		return ResponseEntity.ok().body(dssResponse);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex,
			ContentCachingRequestWrapper request) {
		DssResponse invalidDssReponse = techValidationService.populateInvalidDssResponse(ex, request, RequestType.NEW);
		log.info(HTTPRESMSG, invalidDssReponse.getHttpStatusCode(), invalidDssReponse.getRequestId());
		return ResponseEntity.badRequest().body(invalidDssReponse);
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
		DssResponse invalidDssReponse = techValidationService.populateHTTPMsgInvalidDssResponse(request,
				RequestType.NEW);
		log.info(HTTPRESMSG, invalidDssReponse.getHttpStatusCode(), invalidDssReponse.getRequestId());
		ex.printStackTrace();
		return ResponseEntity.badRequest().body(invalidDssReponse);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	ResponseEntity<Object> handleValidationExceptions(Exception ex, ContentCachingRequestWrapper request) {
		DssResponse invalidDssReponse = techValidationService.populateInvalidDssResponse(request, RequestType.NEW);
		log.info("Internal Server Error 500 : Has Been Returned From Dss-Service New API , for Request Id:"
				+ invalidDssReponse.getRequestId() + "Due To : " + ex.getCause());
		ex.printStackTrace();
		return ResponseEntity.badRequest().body(invalidDssReponse);
	}

}
