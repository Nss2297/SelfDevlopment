package com.waseel.eligibility.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.waseel.eligibility.enums.RequestParameters;
import com.waseel.eligibility.exception.EligibilityException;
import com.waseel.eligibility.model.EligibilityRequestModel;
import com.waseel.eligibility.model.EligibilityResponseModel;
import com.waseel.eligibility.service.EligibilityService;

@RestController
@CrossOrigin("*")
@RequestMapping("/patients/{idNumber}/eligibility")
public class EligibilityController {

	private final Logger log = LoggerFactory.getLogger(EligibilityController.class);

	@Autowired
	EligibilityService eligibilityService;

	@GetMapping
	public ResponseEntity<EligibilityResponseModel> getPatientEligibility(
			@PathVariable(name = "idNumber") String idNumber, @Valid EligibilityRequestModel eligibilityRequestModel,
			ContentCachingRequestWrapper requestWrapper, ContentCachingResponseWrapper cachingResponseWrapper)
			throws EligibilityException {
		log.info("Checking eligibility for patient with id number: {}", idNumber);
		return ResponseEntity.ok(eligibilityService.eligibilityController(idNumber, eligibilityRequestModel,
				requestWrapper, cachingResponseWrapper));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(EligibilityException.class)
	public ResponseEntity<Object> handleSubmissionExceptions(EligibilityException ex, HttpServletRequest request,
			ContentCachingRequestWrapper requestWrapper) {
		log.info("400 Http Response for Request Id: {} Has been Returned From Eligibility-Service.",
				ex.getInvalidResponse().getRequestId());
		EligibilityResponseModel response = eligibilityService.populateEligibilityResponseModel(ex, request,
				requestWrapper);
		return ResponseEntity.badRequest().body(response);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex,
			ContentCachingRequestWrapper requestWrapper, HttpServletRequest request) {
		EligibilityResponseModel invalidResponse = eligibilityService.populateInvalidEligibilityResponse(ex,
				requestWrapper, request);
		log.info("400 Http Response for Request Id: {} Has been Returned From Eligibility-Service.",
				request.getParameter(RequestParameters.REQUEST_ID.value()));
		return ResponseEntity.badRequest().body(invalidResponse);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleValidationExceptions(Exception ex, ContentCachingRequestWrapper requestWrapper,
			HttpServletRequest request) {
		EligibilityResponseModel invalidResponse = eligibilityService.populateFailedEligibilityResponse(requestWrapper,
				request);
		log.info("Internal Server Error 500 : Has Been Returned From Eligibility-Service due to :  ", ex);
		return new ResponseEntity<>(invalidResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
