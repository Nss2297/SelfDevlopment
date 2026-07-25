package com.waseel.pbmnotificationservice.controller;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.pbmnotificationservice.model.common.CommonResponseModel;
import com.waseel.pbmnotificationservice.model.email.EmailNotificationRequestModel;
import com.waseel.pbmnotificationservice.model.email.EmailNotificationResponseModel;
import com.waseel.pbmnotificationservice.model.sms.SmsNotificationRequestModel;
import com.waseel.pbmnotificationservice.model.sms.SmsNotificationResponseModel;
import com.waseel.pbmnotificationservice.service.InvalidResponseService;
import com.waseel.pbmnotificationservice.service.PbmSmsService;
import com.waseel.pbmnotificationservice.service.email.EmailService;

@RestController
@RequestMapping("/notifications")
public class EmailAndSMSController {

	private final Logger log = LoggerFactory.getLogger(EmailAndSMSController.class);

	@Autowired
	private InvalidResponseService invalidResponseService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private PbmSmsService pbmSmsService;

	@PostMapping("/email")
	public ResponseEntity<EmailNotificationResponseModel> sendEmailToPatient(
			@Valid @RequestBody EmailNotificationRequestModel emailRequestModel,
			ContentCachingRequestWrapper requestWrapper) {
		log.info("Send email to emailIds: {} for requestId: {}", emailRequestModel.getEmails(),
				emailRequestModel.getRequestId());
		return ResponseEntity.ok().body(emailService.sendEmailToPatient(emailRequestModel, requestWrapper));
	}

	@PostMapping("/sms")
	public ResponseEntity<SmsNotificationResponseModel> sendNotificationsToPatient(
			@Valid @RequestBody SmsNotificationRequestModel smsNotificationRequestModel,
			ContentCachingRequestWrapper contentCachingRequestWrapper) {
		log.info("Send sms request to mobile number: {} for requestId: {}",
				smsNotificationRequestModel.getMobileNumber(), smsNotificationRequestModel.getRequestId());
		return ResponseEntity.ok().body(
				pbmSmsService.sendSmsNotificationToMember(smsNotificationRequestModel, contentCachingRequestWrapper));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<CommonResponseModel> handleValidationExceptions(Exception ex,
			HttpServletRequest httpServletRequest, ContentCachingRequestWrapper requestWrapper) {
		log.info("Validation Exception: 400 Http Response has been returned from PBM Notification Service");
		return ResponseEntity.badRequest()
				.body(invalidResponseService.populateInvalidFailedResponseForEmailAndSMS(ex, requestWrapper));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<CommonResponseModel> handleExceptions(Exception ex, HttpServletRequest httpServletRequest,
			ContentCachingRequestWrapper requestWrapper) {
		log.info("Internal Server Error 500 : Has Been Returned From PBM Notification Service Due To : ",
				ex.getCause());
		ex.printStackTrace();
		return new ResponseEntity<>(
				invalidResponseService.populateInvalidFailedResponseForEmailAndSMS(ex, requestWrapper),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
