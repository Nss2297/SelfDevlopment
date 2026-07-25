package com.waseel.prescription.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.waseel.prescription.expections.PrescriptionException;
import com.waseel.prescription.model.modifydecision.ProviderOverrideDecisionRequestModel;
import com.waseel.prescription.model.prescription.ModifyDssDecisionResponseModel;
import com.waseel.prescription.model.prescription.PrescriptionResponseModel;
import com.waseel.prescription.model.prescription.PrescriptionsSearchModel;
import com.waseel.prescription.model.prescription.ProviderPrescriptionResponseModel;
import com.waseel.prescription.service.prescriptions.PrescriptionListService;
import com.waseel.prescription.service.prescriptions.PrescriptionService;
import com.waseel.prescription.service.prescriptions.ProviderPrescriptionUpdationService;
import com.waseel.prescription.service.validation.TechnicalValidationService;

import io.swagger.v3.oas.annotations.Hidden;

@CrossOrigin
@RestController
@RequestMapping(value = "/prescriptions")
@Hidden
public class ProvidersPrescriptionsController {

	private final Logger log = LoggerFactory.getLogger(ProvidersPrescriptionsController.class);

	@Autowired
	PrescriptionListService prescriptionListService;
	@Autowired
	ProviderPrescriptionUpdationService providerPrescriptionUpdationService;
	@Autowired
	PrescriptionService prescriptionService;
	@Autowired
	private TechnicalValidationService technicalValidationService;

	@GetMapping
	public ResponseEntity<Page<ProviderPrescriptionResponseModel>> getPrescriptionsList(
			PrescriptionsSearchModel searchCriteria) {
		return ResponseEntity.ok(prescriptionListService.getProviderPrescriptions(searchCriteria));
	}

	@PutMapping("/{ePrescriptionReferenceNumber}/drug-status")
	@PreAuthorize("@securityService.hasOverrideMedicationAccess(authentication)")
	public ResponseEntity<?> updateDssDecision(
			@PathVariable(name = "ePrescriptionReferenceNumber") String ePrescriptionReferenceNumber,
			@Valid @RequestBody ProviderOverrideDecisionRequestModel overrideDecisionRequestModel)
			throws PrescriptionException, JsonMappingException, JsonProcessingException {
		ModifyDssDecisionResponseModel prescription = providerPrescriptionUpdationService
				.updateDssDecision(ePrescriptionReferenceNumber, overrideDecisionRequestModel);
		return ResponseEntity.ok(prescription);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(PrescriptionException.class)
	public ResponseEntity<Object> handleValidationExceptions(PrescriptionException ex) {
		return ResponseEntity.badRequest().body(ex.getInvalidResponse());
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleUnauthorizedException(AccessDeniedException ex,
			ContentCachingRequestWrapper requestWrapper, HttpServletRequest request) {
		PrescriptionResponseModel invalidResponse = technicalValidationService
				.populateUnautorizedPrescriptionResponse(ex, requestWrapper, request);
		log.error("{} Http Response Has been Returned From Prescription-Service", invalidResponse.getHttpStatusCode(),
				ex);
		return new ResponseEntity<>(invalidResponse, HttpStatus.UNAUTHORIZED);
	}
}
