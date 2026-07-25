package com.waseel.drugformulary.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.drugformulary.model.DrugFormularyDetailsModel;
import com.waseel.drugformulary.model.DrugFormularyRequestModel;
import com.waseel.drugformulary.model.DrugFormularyResponseModel;
import com.waseel.drugformulary.service.DrugFormularyService;
import com.waseel.drugformulary.service.InvalidResponseService;

@RestController
@RequestMapping("/payers/{payerId}/patients/{idNumber}/formulary")
public class DrugFormularyController {

	private final Logger log = LoggerFactory.getLogger(DrugFormularyController.class);

	@Autowired
	private DrugFormularyService drugFormularyService;

	@Autowired
	private InvalidResponseService invalidResponseService;

	@PostMapping
	public ResponseEntity<List<DrugFormularyResponseModel>> createDrugFormulary(
			@Valid @RequestBody DrugFormularyRequestModel requestModel, @PathVariable("idNumber") Long idNumber,
			@PathVariable("payerId") String payerId, ContentCachingRequestWrapper requestWrapper) {
		return ResponseEntity
				.ok(drugFormularyService.createDrugFormulary(payerId, idNumber, requestModel, requestWrapper));
	}

	@GetMapping
	public ResponseEntity<DrugFormularyDetailsModel> getDrugFormularyDetailsModel(
			@PathVariable("payerId") String payerId, @PathVariable("idNumber") Long idNumber) {
		return ResponseEntity.ok(drugFormularyService.getDrugFormularyDetailsModel(idNumber, payerId));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex,
			ContentCachingRequestWrapper requestWrapper) {
		DrugFormularyResponseModel invalidResponse = invalidResponseService.populateInvalidResponse(ex, requestWrapper);
		log.info("Bad Request 400 : Has Been Returned From Drug-formulary-Service,Due To : {}", ex.getMessage());
		return ResponseEntity.badRequest().body(invalidResponse);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<DrugFormularyResponseModel> handleMessageNotReadableException(
			HttpMessageNotReadableException ex, ContentCachingRequestWrapper requestWrapper) {
		DrugFormularyResponseModel invalidResponse = invalidResponseService.populateInvalidResponse(ex, requestWrapper);
		log.info("HttpMessageNotReadableException : Has Been Returned From Drug-formulary-Service, " + " Due To : {}",
				ex.getCause());
		ex.printStackTrace();
		return new ResponseEntity<>(invalidResponse, HttpStatus.BAD_REQUEST);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	ResponseEntity<DrugFormularyResponseModel> handleValidationExceptions(Exception ex,
			ContentCachingRequestWrapper requestWrapper) {
		DrugFormularyResponseModel invalidResponse = invalidResponseService.populateFailedResponse(requestWrapper);
		log.info("Internal Server Error 500 : Has Been Returned From Drug-formulary-Service, " + " Due To : {}",
				ex.getCause());
		ex.printStackTrace();
		return new ResponseEntity<>(invalidResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
