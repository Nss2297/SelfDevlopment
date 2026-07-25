package com.waseel.pbm.payercustomizationservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.pbm.payercustomizationservice.exceptions.DssException;
import com.waseel.pbm.payercustomizationservice.model.DssResponse;
import com.waseel.pbm.payercustomizationservice.model.PCRequest;
import com.waseel.pbm.payercustomizationservice.service.AuditService;
import com.waseel.pbm.payercustomizationservice.service.DataPopulationService;
import com.waseel.pbm.payercustomizationservice.service.PayerCustomizationService;

@RestController
public class PayerCustomizationController {

	@Autowired
	private PayerCustomizationService customizationService;
	@Autowired
	private DataPopulationService dataPopulationService;
	@Autowired
	private AuditService auditService;

	@PostMapping("/validate")
	public ResponseEntity<DssResponse> validatePayerCustomization(@RequestBody PCRequest pcRequest) {
		return ResponseEntity.ok(customizationService.manageCustomization(pcRequest));
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	ResponseEntity<Object> handleValidationExceptions(Exception ex, ContentCachingRequestWrapper request) {
		ex.printStackTrace();
		DssResponse invalidDssResponse = dataPopulationService.populateFailedResponse(request);
		auditService.saveInvalidRequestsToMongoDb(request, invalidDssResponse);
		return ResponseEntity.badRequest().body(invalidDssResponse);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DssException.class)
	ResponseEntity<Object> handleValidationExceptions(DssException ex, ContentCachingRequestWrapper request) {
		auditService.saveInvalidRequestsToMongoDb(request, ex.getDssInvalidResponse());
		return ResponseEntity.badRequest().body(ex.getDssInvalidResponse());
	}
}
