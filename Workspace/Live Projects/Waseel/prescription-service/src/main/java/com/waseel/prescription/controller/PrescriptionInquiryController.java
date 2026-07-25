package com.waseel.prescription.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.prescription.expections.PrescriptionException;
import com.waseel.prescription.model.inquiry.InquiryInvalidResponseModel;
import com.waseel.prescription.model.inquiry.detail.PrescriptionDetailInquiryRequestModel;
import com.waseel.prescription.model.inquiry.detail.PrescriptionDetailInquiryResponseModel;
import com.waseel.prescription.model.inquiry.summary.PrescriptionSummaryRequestModel;
import com.waseel.prescription.model.inquiry.summary.PrescriptionSummaryResponseModel;
import com.waseel.prescription.service.inquiry.PrescriptionDetailInquiryService;
import com.waseel.prescription.service.inquiry.PrescriptionSummaryInquiryService;
import com.waseel.prescription.service.validation.InquiryTechnicalValidationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@CrossOrigin
@RequestMapping(value = "/payers/{payerId}/prescriptions/inquiry")
public class PrescriptionInquiryController {

	private final Logger log = LoggerFactory.getLogger(PrescriptionInquiryController.class);

	@Autowired
	private PrescriptionSummaryInquiryService prescriptionInquiryService;
	@Autowired
	InquiryTechnicalValidationService inquiryTechnicalValidationService;

	@Autowired
	private PrescriptionDetailInquiryService detailInquiryService;

	@PreAuthorize("@securityService.hasSummaryInquiryApiAccess(authentication,#payerId)")
	@PostMapping("/summary")
	@Operation(summary = "Summary inquiry over a prescription", description = "This API is used to perform a summary inquiry over a prescription.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Summary inquiry performed successfully"),
			@ApiResponse(responseCode = "400", description = "Bad Request - Data missing from request body", content = {
					@Content(schema = @Schema(implementation = InquiryInvalidResponseModel.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized Access - The user do not have privilege to access this API", content = {
					@Content(schema = @Schema(implementation = InquiryInvalidResponseModel.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal Server Error - Reach out to Waseel for further details", content = {
					@Content(schema = @Schema(implementation = InquiryInvalidResponseModel.class)) }) })
	public ResponseEntity<PrescriptionSummaryResponseModel> managePrescriptionSummaryRequest(
			@PathVariable @Parameter(name = "payerId", description = "Example: 102 i.e. Tawuniya code in Waseel system") String payerId,
			@RequestHeader(value = HttpHeaders.ORIGIN, required = false) String headerOrigin,
			@Valid @RequestBody PrescriptionSummaryRequestModel prescriptionSummaryRequestModel,
			ContentCachingRequestWrapper requestWrapper) throws PrescriptionException, ParseException {
		return ResponseEntity.ok(prescriptionInquiryService.managePrescriptionSummaryRequest(payerId,
				prescriptionSummaryRequestModel, requestWrapper, headerOrigin));
	}

	@PreAuthorize("@securityService.hasDetailInquiryApiAccess(authentication,#payerId)")
	@GetMapping("/detail")
	@Operation(summary = "Detail inquiry over a prescription", description = "This API is used to perform a detail inquiry over a prescription.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Detail inquiry performed successfully"),
			@ApiResponse(responseCode = "400", description = "Bad Request - Data missing from request body", content = {
					@Content(schema = @Schema(implementation = InquiryInvalidResponseModel.class)) }),
			@ApiResponse(responseCode = "401", description = "Unauthorized Access - The user do not have privilege to access this API", content = {
					@Content(schema = @Schema(implementation = InquiryInvalidResponseModel.class)) }),
			@ApiResponse(responseCode = "500", description = "Internal Server Error - Reach out to Waseel for further details", content = {
					@Content(schema = @Schema(implementation = InquiryInvalidResponseModel.class)) }) })
	public ResponseEntity<PrescriptionDetailInquiryResponseModel> managePrescriptionDetailRequest(
			@RequestHeader(value = HttpHeaders.ORIGIN, required = false) String headerOrigin,
			@Valid @RequestBody PrescriptionDetailInquiryRequestModel detailInquiryRequestModel,
			@PathVariable @Parameter(name = "payerId", description = "Tawuniya code in Waseel system is 102") String payerId,
			ContentCachingRequestWrapper requestWrapper) throws PrescriptionException {
		return ResponseEntity.ok(detailInquiryService.managePrescriptionDetailInquiryRequest(detailInquiryRequestModel,
				requestWrapper, headerOrigin));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(PrescriptionException.class)
	public ResponseEntity<Object> handleValidationExceptions(PrescriptionException ex) {
		log.info("{} Http Response has been returned From Prescription-Service for requestId: {}.",
				ex.getInvalidInquiryResponse(), ex.getInvalidInquiryResponse().getRequestId());
		return ResponseEntity.badRequest().body(ex.getInvalidInquiryResponse());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex,
			ContentCachingRequestWrapper requestWrapper) {
		InquiryInvalidResponseModel invalidResponse = inquiryTechnicalValidationService
				.populateInvalidInquiryPrescriptionResponse(ex, requestWrapper);
		log.info("{} Http Response Has been Returned From Prescription-Service for requestId: {}.", invalidResponse,
				invalidResponse.getRequestId());
		return ResponseEntity.badRequest().body(invalidResponse);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleValidationExceptions(Exception ex,
			ContentCachingRequestWrapper requestWrapper) {
		InquiryInvalidResponseModel invalidResponse = inquiryTechnicalValidationService
				.populateFailedInquiryPrescriptionResponse(requestWrapper);
		log.info("{} Internal Server Error 500 : Has Been Returned From Prescription-Service for requestId: {}.",
				ex.getCause(), invalidResponse.getRequestId());
		ex.printStackTrace();
		return new ResponseEntity<>(invalidResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	ResponseEntity<Object> handleValidationExceptions(HttpMessageNotReadableException ex,
			ContentCachingRequestWrapper requestWrapper) {
		InquiryInvalidResponseModel invalidResponse = inquiryTechnicalValidationService
				.populateInvalidInquiryPrescriptionResponse(ex, requestWrapper);
		log.error("Exception:-", ex);
		return ResponseEntity.badRequest().body(invalidResponse);
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleUnautorizedException(AccessDeniedException ex,
			ContentCachingRequestWrapper requestWrapper, HttpServletRequest request) {
		InquiryInvalidResponseModel invalidResponse = inquiryTechnicalValidationService
				.populateUnautorizedPrescriptionResponse(ex, requestWrapper, request);
		log.info("{} Http Response Has been Returned From Inquiry Prescription-Service for requestId: {}.",
				invalidResponse, invalidResponse.getRequestId());
		return new ResponseEntity<>(invalidResponse, HttpStatus.UNAUTHORIZED);
	}
}
