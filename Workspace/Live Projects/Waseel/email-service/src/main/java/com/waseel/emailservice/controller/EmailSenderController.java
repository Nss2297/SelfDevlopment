package com.waseel.emailservice.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.waseel.emailservice.model.EmailDetails;
import com.waseel.emailservice.response.EmailSenderResponseModel;
import com.waseel.emailservice.service.EmailService;
import com.waseel.emailservice.service.TechnicalValidationService;
import com.waseel.emailservice.service.management.AuditLogService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping(value = "/emails")
public class EmailSenderController {

	private final Logger log = LoggerFactory.getLogger(EmailSenderController.class);

	@Autowired
	private EmailService emailService;
	@Autowired
	private TechnicalValidationService technicalValidationService;
	@Autowired
	private AuditLogService auditLogService;

	@PostMapping
	@SecurityRequirement(name = "bearerAuth")
	@Operation(summary = "Send Email", description = "This API is used to send email for multiple or one recipients",
			responses = {
			@ApiResponse(responseCode = "200", description = "Email send successfully"),
			@ApiResponse(responseCode = "400", description = "Bad Request - Data missing or invalid from request body",
			content = {@Content(schema = @Schema(implementation = EmailSenderResponseModel.class),mediaType = "application/json")}),
			@ApiResponse(responseCode = "401",
						 description = "Unauthorized Access - The user do not have privilege to access this API"),
			@ApiResponse(responseCode = "500", 
						 description = "Internal Server Error - Reach out to Waseel for further details", 
			content = {@Content(schema = @Schema(implementation = EmailSenderResponseModel.class),mediaType = "application/json")})})
	public ResponseEntity<Object> sendEmail(@Valid @RequestBody EmailDetails emailDetails) {
		auditLogService.saveEmailRequestAuditData(emailDetails);
		emailService.sendEmail(emailDetails);
		return ResponseEntity.ok().build();
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<EmailSenderResponseModel> handleValidationExceptions(MethodArgumentNotValidException ex) {
		EmailSenderResponseModel invalidResponse = technicalValidationService.populateInvalidPrescriptionResponse(ex);
		auditLogService.saveInvalidResponseAuditData(invalidResponse);
		log.info("400 Response Has been Returned From Email-Service due to '{}'",
				invalidResponse.getStatusDescription());
		return ResponseEntity.badRequest().body(invalidResponse);
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<EmailSenderResponseModel> handleValidationExceptions(Exception ex) {
		ex.printStackTrace();
		EmailSenderResponseModel invalidResponse = technicalValidationService.populateFailedEmailSenderResponse();
		auditLogService.saveInvalidResponseAuditData(invalidResponse);
		log.info("Internal Server Error 500 : Has Been Returned From Email-Service," + " Due To : {} ", ex.getCause());
		return new ResponseEntity<>(invalidResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
