package com.waseel.smsservice.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.smsservice.exception.SMSException;
import com.waseel.smsservice.model.SmsRequestModel;
import com.waseel.smsservice.model.UnifonicResponseModel;
import com.waseel.smsservice.service.InvalidResponseService;
import com.waseel.smsservice.service.SmsService;

@RestController
@RequestMapping(value = "/sms")
public class SMSController {
	@Autowired
	SmsService smsService;
	@Autowired
	InvalidResponseService invalidResponseService;

	private final Logger log = LoggerFactory.getLogger(SMSController.class);

	@PostMapping(value = "/send")
	public ResponseEntity<UnifonicResponseModel> receiveNotificationFromTawuniya(
			@Valid @RequestBody SmsRequestModel smsRequestModel, ContentCachingRequestWrapper requestWrapper)
			throws SMSException, Exception {
		log.info("Sending sms on from {}", smsRequestModel.getAppName());
		return ResponseEntity.ok(smsService.sendSMS(smsRequestModel));
	}

	@ExceptionHandler({ SMSException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleValidationExceptions(SMSException ex) {
		log.info("Validation Exception: 400 Http Response has been returned from SMS Service");
		return ResponseEntity.badRequest().body(ex.getUnifonicResponseModel());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<UnifonicResponseModel> handleValidationErrors(MethodArgumentNotValidException ex) {
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(FieldError::getDefaultMessage)
				.collect(Collectors.toList());
		return ResponseEntity.badRequest().body(invalidResponseService.getMethodArgumentNotValidResponse(errors));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleExceptions(Exception ex, HttpServletRequest httpServletRequest,
			ContentCachingRequestWrapper requestWrapper) {
		log.info("Internal Server Error 500 : Has Been Returned From SMS Service Due To : ", ex.getCause());
		ex.printStackTrace();
		return new ResponseEntity<>(invalidResponseService.populateInternalServerErrorResponse(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
