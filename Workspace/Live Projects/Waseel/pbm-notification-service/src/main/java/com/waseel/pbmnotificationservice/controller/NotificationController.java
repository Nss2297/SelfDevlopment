package com.waseel.pbmnotificationservice.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.pbmnotificationservice.exceptions.NotificationException;
import com.waseel.pbmnotificationservice.model.enums.HeadersName;
import com.waseel.pbmnotificationservice.model.eprescription.notification.NotificationRequestModel;
import com.waseel.pbmnotificationservice.model.eprescription.notification.NotificationResponseModel;
import com.waseel.pbmnotificationservice.service.InvalidResponseService;
import com.waseel.pbmnotificationservice.service.eprescription.notification.NotificationService;
import com.waseel.pbmnotificationservice.validator.customannotation.ValidHeader;

@RestController
@RequestMapping(value = "/eprescription/notifications")
@Validated
public class NotificationController {

	private final Logger log = LoggerFactory.getLogger(NotificationController.class);

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private InvalidResponseService invalidResponseService;

	@PostMapping
	@PreAuthorize("hasAuthority('101;PRESCRIPTION_NOTIFICATION')")
	public ResponseEntity<NotificationResponseModel> receiveNotificationFromTawuniya(
			@Valid @RequestBody NotificationRequestModel notificationRequestModel,
			@ValidHeader(message = "Sender-Code {nullHeaderValidation}") 
				@RequestHeader(value = "Sender-Code", required = true) String senderCode,
			@ValidHeader(message = "Receiver-Code {nullHeaderValidation}") 
				@RequestHeader(value = "Receiver-Code", required = true) String receiverCode,
			@ValidHeader(message = "Transaction-Id {nullHeaderValidation}")
				@RequestHeader(value = "Transaction-Id", required = true) String transactionId,
			@ValidHeader(message = "Time-Stamp {nullHeaderValidation}") 
				@RequestHeader(value = "Time-Stamp", required = true) String timeStamp,
			@ValidHeader(message = "Direction {nullHeaderValidation}") 
				@RequestHeader(value = "Direction", required = true) String direction,
			ContentCachingRequestWrapper requestWrapper) throws NotificationException {
		log.info("Get Notification from Tawuniya with ePrescriptionReferenceNumber {}",
				notificationRequestModel.getePrescriptionReferenceNumber());
		return ResponseEntity.ok().headers(notificationService.setResponseHeaders(transactionId))
				.body(notificationService.receiveNotificationFromTawuniya(notificationRequestModel, senderCode));
	}
	
	@ExceptionHandler({ ConstraintViolationException.class, MethodArgumentNotValidException.class,
			MissingRequestHeaderException.class, NotificationException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleValidationExceptions(Exception ex, HttpServletRequest httpServletRequest,
			ContentCachingRequestWrapper requestWrapper) {
		log.info("Validation Exception: 400 Http Response has been returned from PBM Notification Service");
		String transactionId = httpServletRequest.getHeader(HeadersName.TRANSACTION_ID.value());
		return ResponseEntity.badRequest().headers(notificationService.setResponseHeaders(transactionId))
				.body(invalidResponseService.populateInvalidFailedResponse(ex, requestWrapper, httpServletRequest));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleExceptions(Exception ex, HttpServletRequest httpServletRequest,
			ContentCachingRequestWrapper requestWrapper) {
		log.info("Internal Server Error 500 : Has Been Returned From PBM Notification Service Due To : ",
				ex.getCause());
		ex.printStackTrace();
		String transactionId = httpServletRequest.getHeader(HeadersName.TRANSACTION_ID.value());
		return new ResponseEntity<>(
				invalidResponseService.populateInvalidFailedResponse(ex, requestWrapper, httpServletRequest),
				notificationService.setResponseHeaders(transactionId), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleUnauthorizedException(AccessDeniedException ex,
			ContentCachingRequestWrapper requestWrapper, HttpServletRequest httpServletRequest) {
		log.info("{} Http Response Has been Returned From PBM Notification Service", ex.getMessage());
		String transactionId = httpServletRequest.getHeader(HeadersName.TRANSACTION_ID.value());
		return new ResponseEntity<>(invalidResponseService.populateUnauthorizedResponse(ex, requestWrapper),
				notificationService.setResponseHeaders(transactionId), HttpStatus.UNAUTHORIZED);
	}
}
