package com.waseel.prescription.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.prescription.expections.PrescriptionException;
import com.waseel.prescription.model.cancellation.PrescriptionCancellationRequestModel;
import com.waseel.prescription.model.cancellation.PrescriptionCancellationResponseModel;
import com.waseel.prescription.model.prescription.DiagnosisCodes;
import com.waseel.prescription.model.prescription.EligibilityValidationModel;
import com.waseel.prescription.model.prescription.PayerMemberPhysicianInfoModel;
import com.waseel.prescription.model.prescription.PrescriptionRequestModel;
import com.waseel.prescription.model.prescription.PrescriptionResponseModel;
import com.waseel.prescription.model.prescription.ServiceRejectionModel;
import com.waseel.prescription.service.prescriptions.PrescriptionService;
import com.waseel.prescription.service.validation.TechnicalValidationService;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@CrossOrigin("*")
@RequestMapping(value = "/payers/{payerId}/prescriptions")
public class PrescriptionController {

	private final Logger log = LoggerFactory.getLogger(PrescriptionController.class);

	@Autowired
	private TechnicalValidationService technicalValidationService;

	@Autowired
	private PrescriptionService prescriptionService;

	@PostMapping
	@PreAuthorize("@securityService.hasNewOrFollowUpAccessForValidResponse(authentication,#payerId,"
			+ " #prescriptionRequest.getePrescriptionReferenceNumber())")
	@Operation(summary = "Submit new or follow-up prescription", description = "This API is used to send new or follow-up prescription requests to be validated by the PBM engine.", responses = {
			@ApiResponse(responseCode = "200", description = "Prescription is submitted successfully"),
			@ApiResponse(responseCode = "400", description = "Bad Request - Data missing from request body", content = {
					@Content(schema = @Schema(implementation = PrescriptionResponseModel.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized Access - The user do not have privilege to access this API", content = {
					@Content(schema = @Schema(implementation = PrescriptionResponseModel.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal Server Error - Reach out to Waseel for further details", content = {
					@Content(schema = @Schema(implementation = PrescriptionResponseModel.class)) }) })
	public ResponseEntity<PrescriptionResponseModel> manageNewOrFollowUpPrescriptionRequest(
			@RequestHeader(value = HttpHeaders.ORIGIN, required = false) String headerOrigin,
			@PathVariable @Parameter(name = "payerId", description = "Example: 102 i.e. Tawuniya code in Waseel system") String payerId,
			@Valid @RequestBody PrescriptionRequestModel prescriptionRequest,
			ContentCachingRequestWrapper requestWrapper) throws PrescriptionException {
		return ResponseEntity.ok(prescriptionService.manageNewOrFollowUpPrescriptionRequest(prescriptionRequest,
				requestWrapper, headerOrigin, payerId));
	}

	@DeleteMapping("/{ePrescriptionReferenceNumber}")
	@PreAuthorize("@securityService.hasCancellationAccessForValidResponse(authentication,#payerId)")
	@Operation(summary = "Cancel the prescription", description = "This API is used to cancel an already submitted prescription request.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Prescription is cancelled successfully"),
			@ApiResponse(responseCode = "400", description = "Bad Request - The ePrescriptionReferenceNumber is invalid", content = {
					@Content(schema = @Schema(implementation = PrescriptionResponseModel.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized Access - The user do not have privilege to access this API", content = {
					@Content(schema = @Schema(implementation = PrescriptionResponseModel.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal Server Error - Reach out to Waseel for further details", content = {
					@Content(schema = @Schema(implementation = PrescriptionResponseModel.class)) }) })

	public ResponseEntity<PrescriptionCancellationResponseModel> managePrescriptionCancellationRequest(
			@RequestHeader(value = HttpHeaders.ORIGIN, required = false) String headerOrigin,
			@PathVariable @Parameter(name = "payerId", description = "Example: 102 i.e. Tawuniya code in Waseel system") String payerId,
			@PathVariable @Parameter(name = "ePrescriptionReferenceNumber", description = "An ID that uniquely identifies a prescription e.g. 2023-4302") String ePrescriptionReferenceNumber,
			ContentCachingRequestWrapper requestWrapper) throws PrescriptionException {
		return ResponseEntity.ok(prescriptionService.managePrescriptionCancellationRequest(
				new PrescriptionCancellationRequestModel(payerId, ePrescriptionReferenceNumber), requestWrapper,
				headerOrigin));
	}

	@GetMapping("/{ePrescriptionReferenceNumber}")
	@Hidden
	@PreAuthorize("@securityService.hasAccessToFetchDrugDiagnosisPayerMemberPhysicianDetails(authentication,#payerId,#ePrescriptionReferenceNumber)")
	public ResponseEntity<PayerMemberPhysicianInfoModel> getPayerMemberPhysicianDetails(
			@RequestHeader(value = HttpHeaders.ORIGIN, required = false) String headerOrigin,
			@PathVariable(name = "ePrescriptionReferenceNumber") String ePrescriptionReferenceNumber,
			@PathVariable(name = "payerId") String payerId, ContentCachingRequestWrapper requestWrapper)
			throws PrescriptionException {
		return ResponseEntity.ok(prescriptionService.getPayerMemberPhysicianDetails(ePrescriptionReferenceNumber,
				requestWrapper, payerId, headerOrigin));
	}

	@Hidden
	@GetMapping("/{ePrescriptionReferenceNumber}/diagnosis")
	@PreAuthorize("@securityService.hasAccessToFetchDrugDiagnosisPayerMemberPhysicianDetails(authentication,#payerId,#ePrescriptionReferenceNumber)")
	public ResponseEntity<Page<DiagnosisCodes>> getDiagnosis(
			@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(name = "recordSize", defaultValue = "10") int recordSize,
			@PathVariable(name = "ePrescriptionReferenceNumber") String ePrescriptionReferenceNumber,
			@PathVariable(name = "payerId") String payerId) throws PrescriptionException {
		return ResponseEntity
				.ok(prescriptionService.getDiagnosis(ePrescriptionReferenceNumber, pageNumber, recordSize));
	}

	@Hidden
	@GetMapping("/{ePrescriptionReferenceNumber}/drugs")
	@PreAuthorize("@securityService.hasAccessToFetchDrugDiagnosisPayerMemberPhysicianDetails(authentication,#payerId,#ePrescriptionReferenceNumber)")
	public ResponseEntity<Object> getDrugs(@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "recordSize", defaultValue = "10") Integer recordSize,
			@PathVariable(name = "ePrescriptionReferenceNumber") String ePrescriptionReferenceNumber,
			@PathVariable(name = "payerId") String payerId,
			@RequestParam(name = "isPaginated", defaultValue = "true") boolean isPaginated)
			throws PrescriptionException {
		return ResponseEntity
				.ok(prescriptionService.getDrugs(ePrescriptionReferenceNumber, pageNumber, recordSize, isPaginated));
	}

	@Hidden
	@GetMapping("/{ePrescriptionReferenceNumber}/validations/{category}")
	@PreAuthorize("@securityService.hasAccessToFetchValidation(authentication,#payerId)")
	public ResponseEntity<List<ServiceRejectionModel>> getDrugsMedicalOrBusinessValidation(
			@PathVariable(name = "ePrescriptionReferenceNumber") String ePrescriptionReferenceNumber,
			@PathVariable(name = "category") String category, @PathVariable(name = "payerId") String payerId)
			throws PrescriptionException {
		return ResponseEntity
				.ok(prescriptionService.getDrugsMedicalOrBusinessValidation(ePrescriptionReferenceNumber, category));
	}

	@Hidden
	@GetMapping("/{ePrescriptionReferenceNumber}/eligibility-validation")
	@PreAuthorize("@securityService.hasAccessToFetchValidation(authentication,#payerId)")
	public ResponseEntity<Page<EligibilityValidationModel>> getPrescriptionEligibilityValidation(
			@PathVariable(name = "ePrescriptionReferenceNumber", required = true) String ePrescriptionReferenceNumber,
			@RequestParam(name = "pageNumber", defaultValue = "0") int pageNumber,
			@RequestParam(name = "recordSize", defaultValue = "10") int recordSize,
			@PathVariable(name = "payerId") String payerId) throws PrescriptionException {
		return ResponseEntity.ok(prescriptionService
				.getEligibilityValidationsOfPrescription(ePrescriptionReferenceNumber, pageNumber, recordSize));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(PrescriptionException.class)
	public ResponseEntity<Object> handleValidationExceptions(PrescriptionException ex) {
		if (null != ex.getInvalidResponse()) {
			log.error("{} Http Response for Request Id: {} Has been Returned From Prescription-Service.",
					ex.getInvalidResponse().getHttpStatusCode(), ex.getInvalidResponse().getRequestId(), ex);
		} else {
			log.error("Exception is returned From Prescription-Service: ", ex);
		}
		return ResponseEntity.badRequest().body(ex.getInvalidResponse());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex,
			ContentCachingRequestWrapper requestWrapper, HttpServletRequest request) {
		PrescriptionResponseModel invalidResponse = technicalValidationService.populateInvalidPrescriptionResponse(ex,
				requestWrapper, request);
		log.error("{} Http Response Has been Returned From Prescription-Service", invalidResponse.getHttpStatusCode(),
				ex);
		return ResponseEntity.badRequest().body(invalidResponse);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleValidationExceptions(Exception ex, ContentCachingRequestWrapper requestWrapper,
			HttpServletRequest request) {
		ex.printStackTrace();
		PrescriptionResponseModel invalidDssResponse = technicalValidationService
				.populateFailedPrescriptionResponse(requestWrapper, request);
		log.error("Internal Server Error 500 : Has Been Returned From Prescription-Service,"
				+ " for Request Id: {} Due To : {} ", invalidDssResponse.getRequestId(), ex.getCause(), ex);
		return new ResponseEntity<>(invalidDssResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	ResponseEntity<Object> handleValidationExceptions(HttpMessageNotReadableException ex,
			ContentCachingRequestWrapper requestWrapper) {
		PrescriptionResponseModel invalidResponse = technicalValidationService.populateInvalidPrescriptionResponse(ex,
				requestWrapper);
		log.error("Exception:-", ex);
		return ResponseEntity.badRequest().body(invalidResponse);
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
