package com.waseel.brservice.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.brservice.model.InvalidResponseModel;
import com.waseel.brservice.model.SensitiveDrugRequestModel;
import com.waseel.brservice.model.SensitiveDrugResponseModel;
import com.waseel.brservice.service.InvalidResponseService;
import com.waseel.brservice.service.SensitiveDrugService;

@RestController
public class BusinessRuleController {

	private final Logger log = LoggerFactory.getLogger(BusinessRuleController.class);

	@Autowired
	private SensitiveDrugService sensitiveDrugService;
	@Autowired
	private InvalidResponseService invalidResponseService;

	@PostMapping("/sensitive-drug/validate")
	public ResponseEntity<SensitiveDrugResponseModel> checkSensitiveDrug(
			@Valid @RequestBody SensitiveDrugRequestModel sensitiveDrugRequestModel,
			ContentCachingRequestWrapper requestWrapper) {
		return ResponseEntity.ok(sensitiveDrugService.checkSensitiveDrug(sensitiveDrugRequestModel,requestWrapper));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	ResponseEntity<InvalidResponseModel> handleValidationExceptions(MethodArgumentNotValidException ex,
			ContentCachingRequestWrapper requestWrapper) {
		log.info("Bad Request 400 : Has Been Returned From BusinessRule-Service,Due To : {}", ex.getMessage());
		return ResponseEntity.badRequest().body(invalidResponseService.populateInvalidResponse(ex, requestWrapper));
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	ResponseEntity<InvalidResponseModel> handleTechnicalExceptions(Exception ex,
			ContentCachingRequestWrapper requestWrapper) {
		log.info("Internal Server Error 500 : Has Been Returned From Drug BusinessRule-service, "
				+ " Due To : {}", ex.getCause());
		ex.printStackTrace();
		return ResponseEntity.internalServerError().body(invalidResponseService.populateFailedResponse(requestWrapper));
	}
}
