package com.waseel.prescription.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.prescription.expections.PrescriptionException;
import com.waseel.prescription.model.common.CommonPrescriptionUpdationResponseModel;
import com.waseel.prescription.model.inquiry.eprescription.EPrescriptionInquiryRequestModel;
import com.waseel.prescription.model.modifydecision.InvalidResPrescriptionUpdationService;
import com.waseel.prescription.model.modifydecision.ModifyDecisionRequestModel;
import com.waseel.prescription.model.modifydecision.ModifyDecisionResponseModel;
import com.waseel.prescription.service.management.AuditLogService;
import com.waseel.prescription.service.modifydecision.ModifyDecisionService;
import com.waseel.prescription.service.prescriptions.PrescriptionUpdationService;

import io.swagger.v3.oas.annotations.Hidden;

@RestController
@CrossOrigin("*")
@Hidden
@RequestMapping(value = "/payers/{payerId}")
public class PrescriptionUpdationController {

	private final Logger log = LoggerFactory.getLogger(PrescriptionUpdationController.class);

	@Autowired
	private PrescriptionUpdationService prescriptionUpdationService;
	@Autowired
	private AuditLogService auditLogService;
	@Autowired
	private InvalidResPrescriptionUpdationService invalidResPrescriptionUpdationService;
	@Autowired
	private ModifyDecisionService modifyDecisionService;

	@PutMapping("/prescription/update-status")
	public ResponseEntity<Object> updatePrescriptionStatus(
			@Valid @RequestBody EPrescriptionInquiryRequestModel ePrescriptionRequestModel)
			throws PrescriptionException {
		auditLogService.saveEPrescriptionInquiryAuditData(ePrescriptionRequestModel);
		prescriptionUpdationService.updatePrescriptionStatus(ePrescriptionRequestModel);
		return ResponseEntity.ok().build();
	}

	@PreAuthorize("@securityService.hasPayerEditPrescriptionDecisionAccess(authentication)")
	@PutMapping("/prescriptions/{ePrescriptionReferenceNumber}/modify-decision")
	public ResponseEntity<ModifyDecisionResponseModel> modifyDecisionByPayer(
			@PathVariable("ePrescriptionReferenceNumber") String ePrescriptionReferenceNumber,
			@PathVariable("payerId") String payerId,
			@RequestHeader(value = HttpHeaders.ORIGIN, required = false) String headerOrigin,
			@Valid @RequestBody ModifyDecisionRequestModel modifyDecisionRequestModel,
			ContentCachingRequestWrapper requestWrapper) throws PrescriptionException {
		return ResponseEntity.ok(modifyDecisionService.modifyDecisionByPayer(modifyDecisionRequestModel,
				ePrescriptionReferenceNumber, payerId, requestWrapper, headerOrigin));
	}

	@ExceptionHandler({ MethodArgumentNotValidException.class, PrescriptionException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleValidationExceptions(Exception ex,
			ContentCachingRequestWrapper requestWrapper) {
		log.info("Validation Exception: 400 Http Response has been returned from Prescription-Service due to: ", ex);
		return ResponseEntity.badRequest()
				.body(invalidResPrescriptionUpdationService.populateInvalidFailedResponse(ex, requestWrapper));
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleUnauthorizedException(AccessDeniedException ex,
			ContentCachingRequestWrapper requestWrapper, HttpServletRequest request) {
		ex.printStackTrace();
		log.info("AccessDenied Exception: 403 Http Response Has been Returned From Prescription-Service", ex);
		return new ResponseEntity<>(
				invalidResPrescriptionUpdationService.populateUnAuthorizedResponse(ex, requestWrapper),
				HttpStatus.UNAUTHORIZED);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleExceptions(Exception ex, ContentCachingRequestWrapper requestWrapper) {
		CommonPrescriptionUpdationResponseModel invalidResponse = invalidResPrescriptionUpdationService
				.populateInvalidFailedResponse(ex, requestWrapper);
		log.info("Internal Server Error 500 : Has Been Returned From Prescription-Service Due To : ", ex.getCause());
		ex.printStackTrace();
		return new ResponseEntity<>(invalidResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
