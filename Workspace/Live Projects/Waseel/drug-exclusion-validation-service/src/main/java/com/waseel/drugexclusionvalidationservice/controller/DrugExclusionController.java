package com.waseel.drugexclusionvalidationservice.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.drugexclusionvalidationservice.model.DrugExclusionRequestModel;
import com.waseel.drugexclusionvalidationservice.model.DrugExclusionResponseModel;
import com.waseel.drugexclusionvalidationservice.service.InvalidResponseService;
import com.waseel.drugexclusionvalidationservice.service.exclusions.DrugExclusionService;

@RestController
@RequestMapping("/drug-exclusion")
public class DrugExclusionController {

	private final Logger log = LoggerFactory.getLogger(DrugExclusionController.class);

	@Autowired
	private DrugExclusionService drugExclusionService;

	@Autowired
	private InvalidResponseService invalidResponseService;

	@PostMapping
	public ResponseEntity<DrugExclusionResponseModel> checkDrugExclusion(
			@Valid @RequestBody DrugExclusionRequestModel requestModel,
			ContentCachingRequestWrapper requestWrapper) {
		return ResponseEntity.ok(drugExclusionService.checkDrugExclusion(requestModel, requestWrapper));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	ResponseEntity<DrugExclusionResponseModel> handleValidationExceptions(MethodArgumentNotValidException ex,
																		  ContentCachingRequestWrapper requestWrapper) {
		DrugExclusionResponseModel invalidResponse = invalidResponseService.populateInvalidResponse(ex,
				requestWrapper);
		log.info("Bad Request 400 : Has Been Returned From Drug exclusion validation service,Due To : {}",
				ex.getMessage());
		return ResponseEntity.badRequest().body(invalidResponse);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<DrugExclusionResponseModel> handleMessageNotReadableException(
			HttpMessageNotReadableException ex, ContentCachingRequestWrapper requestWrapper) {
		DrugExclusionResponseModel invalidResponse = invalidResponseService.populateInvalidResponse(ex,
				requestWrapper);
		log.info("HttpMessageNotReadableException : Has Been Returned From Drug exclusion validation service, "
				+ " Due To : {}", ex.getCause());
		ex.printStackTrace();
		return new ResponseEntity<>(invalidResponse, HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	ResponseEntity<DrugExclusionResponseModel> handleValidationExceptions(Exception ex,
																		  ContentCachingRequestWrapper requestWrapper) {
		DrugExclusionResponseModel invalidResponse = invalidResponseService
				.populateFailedResponse(requestWrapper);
		log.info("Internal Server Error 500 : Has Been Returned From Drug exclusion validation service, "
				+ " Due To : {}", ex.getCause());
		ex.printStackTrace();
		return new ResponseEntity<>(invalidResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
