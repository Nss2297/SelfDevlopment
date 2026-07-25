package com.waseel.policy.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.policy.exception.PolicyException;
import com.waseel.policy.model.CancellAndDispensePolicyRequestModel;
import com.waseel.policy.model.DeactivatePrescriptionRequestModel;
import com.waseel.policy.model.DispensibleDrugsRequestModel;
import com.waseel.policy.model.PolicyRequestModel;
import com.waseel.policy.model.PolicyResponseModel;
import com.waseel.policy.service.PayerAndPatientShareCalculationService;
import com.waseel.policy.service.PolicyConsumptionService;

@RestController
@RequestMapping("/patients/{idNumber}/policyConsumption")
public class PolicyController {

	private final Logger log = LoggerFactory.getLogger(PolicyController.class);

	@Autowired
	PolicyConsumptionService policyConsumptionService;

	@Autowired
	private PayerAndPatientShareCalculationService payerAndPatientShareCalculationService;

	@PostMapping
	public ResponseEntity<PolicyResponseModel> checkPolicyDetails(@PathVariable String idNumber,
			@RequestBody @Valid PolicyRequestModel policyRequestModel,
			ContentCachingRequestWrapper contentCachingRequestWrapper) throws PolicyException {
		log.info("Checking policy details for patient with id number: {}", idNumber);
		return ResponseEntity.ok(
				policyConsumptionService.getPolicyDetails(idNumber, policyRequestModel, contentCachingRequestWrapper));
	}

	@PutMapping("/cancel")
	public ResponseEntity<PolicyResponseModel> cancelPrescription(@PathVariable String idNumber,
			@RequestBody @Valid CancellAndDispensePolicyRequestModel cancellAndDispensePolicyRequestModel,
			ContentCachingRequestWrapper contentCachingRequestWrapper) throws PolicyException {
		log.info("Policy consumption check for cancelled prescription. RequestId:{} ",
				cancellAndDispensePolicyRequestModel.getRequestId());
		return ResponseEntity.ok(policyConsumptionService.policyCheckForCancellation(idNumber,
				cancellAndDispensePolicyRequestModel.getRequestId(), cancellAndDispensePolicyRequestModel.getPayerId(),
				cancellAndDispensePolicyRequestModel.getBenefitCase(),
				cancellAndDispensePolicyRequestModel.getBenefitCode(),
				cancellAndDispensePolicyRequestModel.getProviderId(), contentCachingRequestWrapper));
	}

	@PutMapping("/dispense")
	public ResponseEntity<PolicyResponseModel> dispensePrescription(@PathVariable String idNumber,
			@RequestBody @Valid CancellAndDispensePolicyRequestModel cancellAndDispensePolicyRequestModel,
			ContentCachingRequestWrapper contentCachingRequestWrapper) throws PolicyException {
		String requestId = cancellAndDispensePolicyRequestModel.getRequestId();
		log.info("Policy consumption check for cancelled prescription. RequestId:{} ", requestId);
		return ResponseEntity.ok(policyConsumptionService.handleDispensePrescription(idNumber, requestId,
				cancellAndDispensePolicyRequestModel.getPayerId(),
				cancellAndDispensePolicyRequestModel.getBenefitCode(),
				cancellAndDispensePolicyRequestModel.getBenefitCase(),
				cancellAndDispensePolicyRequestModel.getDrugList(),
				cancellAndDispensePolicyRequestModel.getProviderId(), contentCachingRequestWrapper));
	}

	@PutMapping("/deactivate-prescription")
	public ResponseEntity<PolicyResponseModel> dispensePrescription(@PathVariable String idNumber,
			@RequestBody @Valid DeactivatePrescriptionRequestModel deactivatePrescriptionRequestModel,
			ContentCachingRequestWrapper contentCachingRequestWrapper) throws PolicyException {
		String requestId = deactivatePrescriptionRequestModel.getRequestId();
		log.info("Deactivate prescription RequestId:{} ", requestId);
		return ResponseEntity.ok(policyConsumptionService.deactivatePrescription(idNumber, requestId,
				contentCachingRequestWrapper, deactivatePrescriptionRequestModel.getPayerId(),
				deactivatePrescriptionRequestModel.getProviderId()));
	}

	@GetMapping("/dispensible-drugs")
	public ResponseEntity<PolicyResponseModel> fetchPayerAndPatientShareForAllDispensableDrugs(
			@PathVariable String idNumber, DispensibleDrugsRequestModel dispensibleDrugsRequestModel,
			ContentCachingRequestWrapper contentCachingRequestWrapper) throws PolicyException {
		return ResponseEntity.ok(payerAndPatientShareCalculationService.fetchPatientShareForDispensableDrugs(idNumber,
				dispensibleDrugsRequestModel, contentCachingRequestWrapper));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(PolicyException.class)
	public ResponseEntity<Object> handleSubmissionExceptions(PolicyException ex, HttpServletRequest request) {
		log.info("400 Http Response for Request Id has been returned From Policy-Service.");
		return ResponseEntity.badRequest().body(ex.getInvalidResponse());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex,
			HttpServletRequest request, ContentCachingRequestWrapper requestWrapper) {
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage)
				.collect(Collectors.toList());
		PolicyResponseModel invalidResponse = policyConsumptionService.populateBadRequestResponse(errors, request,
				requestWrapper);
		return ResponseEntity.badRequest().body(invalidResponse);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleValidationExceptions(Exception ex, HttpServletRequest request,
			ContentCachingRequestWrapper requestWrapper) {
		log.info("Internal Server Error 500 : Has Been Returned From Policy-Service due to :  ", ex);
		PolicyResponseModel invalidResponse = policyConsumptionService.populateServerErrorResponse(ex, request,
				requestWrapper);
		return new ResponseEntity<>(invalidResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}