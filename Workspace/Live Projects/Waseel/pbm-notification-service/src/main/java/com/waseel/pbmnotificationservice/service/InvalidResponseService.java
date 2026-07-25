package com.waseel.pbmnotificationservice.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.pbmnotificationservice.exceptions.NotificationException;
import com.waseel.pbmnotificationservice.model.common.CommonResponseModel;
import com.waseel.pbmnotificationservice.model.email.EmailNotificationRequestModel;
import com.waseel.pbmnotificationservice.model.enums.HeadersName;
import com.waseel.pbmnotificationservice.model.enums.ResponseStatusType;
import com.waseel.pbmnotificationservice.model.enums.url;
import com.waseel.pbmnotificationservice.model.eprescription.notification.NotificationRequestModel;
import com.waseel.pbmnotificationservice.model.eprescription.notification.NotificationResponseModel;
import com.waseel.pbmnotificationservice.model.sms.SmsNotificationRequestModel;
import com.waseel.pbmnotificationservice.persist.prescriptionservice.PayerNotifications;
import com.waseel.pbmnotificationservice.repository.prescriptionservice.PayerNotificationsRepository;

@Service
public class InvalidResponseService {

	@Autowired
	private MapperService mapperService;

	@Autowired
	private PayerNotificationsRepository payerNotificationsRepository;

	@Autowired
	private EmailSmsAuditLogService emailSmsAuditLogService;

	public NotificationResponseModel populateInvalidFailedResponse(Exception ex,
			ContentCachingRequestWrapper requestWrapper, HttpServletRequest httpServletRequest) {
		Date date = new Date();
		String errorMessage;
		if (ex instanceof ConstraintViolationException) {
			ConstraintViolationException cve = (ConstraintViolationException) ex;
			List<String> errors = cve.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
					.collect(Collectors.toList());
			errorMessage = StringUtils.strip(errors.toString(), "[]");
		} else if (ex instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException methodArgEx = (MethodArgumentNotValidException) ex;
			List<String> errors = methodArgEx.getBindingResult().getAllErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
			errorMessage = StringUtils.strip(errors.toString(), "[]");
		} else if (ex instanceof MissingRequestHeaderException) {
			MissingRequestHeaderException missingHeaderEx = (MissingRequestHeaderException) ex;
			errorMessage = "Required header '" + missingHeaderEx.getHeaderName() + "' is missing.";
		} else if (ex instanceof NotificationException) {
			NotificationException notificationEx = (NotificationException) ex;
			errorMessage = notificationEx.getMessage();
		} else {
			errorMessage = HttpStatus.INTERNAL_SERVER_ERROR.name();
		}
		NotificationRequestModel requestModel = mapperService.mapNotificationRequestModel(requestWrapper);
		saveDataInPayerNotificationsTable(requestModel, date, errorMessage, httpServletRequest);
		return prepareNotificationResponseModel(errorMessage, requestModel, date);
	}

	public NotificationResponseModel populateUnauthorizedResponse(AccessDeniedException ex,
			ContentCachingRequestWrapper requestWrapper) {
		String errorMessage = ex.getMessage();
		NotificationRequestModel requestModel = mapperService.mapNotificationRequestModel(requestWrapper);
		return prepareNotificationResponseModel(errorMessage, requestModel, new Date());
	}

	private NotificationResponseModel prepareNotificationResponseModel(String statusDesc,
			NotificationRequestModel requestModel, Date date) {
		NotificationResponseModel responseModel = new NotificationResponseModel();
		responseModel.setAcknowledgementDateAndTime(convertCurrentDateToString(date));
		if (requestModel != null) {
			responseModel.setApprovalReferenceNumber(requestModel.getApprovalReferenceNumber());
			responseModel.setePrescriptionReferenceNumber(requestModel.getePrescriptionReferenceNumber());
		}
		responseModel.setStatus(ResponseStatusType.FAILED.value());
		responseModel.setStatusDescription(statusDesc);
		return responseModel;
	}

	private PayerNotifications saveDataInPayerNotificationsTable(NotificationRequestModel notificationRequestModel,
			Date date, String statusDesc, HttpServletRequest httpServletRequest) {
		String payerId = httpServletRequest.getHeader(HeadersName.SENDER_CODE.value());
		PayerNotifications payerNotifications = new PayerNotifications(payerId,
				notificationRequestModel.getePrescriptionReferenceNumber(),
				notificationRequestModel.getApprovalReferenceNumber(), ResponseStatusType.FAILED.value(),
				notificationRequestModel.getePrescriptionStatus(), statusDesc, date);
		return payerNotificationsRepository.save(payerNotifications);
	}

	private String convertCurrentDateToString(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		return formatter.format(date);
	}

	public CommonResponseModel populateInvalidFailedResponseForEmailAndSMS(Exception ex,
			ContentCachingRequestWrapper requestWrapper) {
		CommonResponseModel responseModel = new CommonResponseModel();
		String status = ResponseStatusType.FAILED.value();
		String errorMessage = HttpStatus.INTERNAL_SERVER_ERROR.name();
		if (ex instanceof MethodArgumentNotValidException) {
			MethodArgumentNotValidException methodArgEx = (MethodArgumentNotValidException) ex;
			List<String> errors = methodArgEx.getBindingResult().getAllErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
			errorMessage = StringUtils.strip(errors.toString(), "[]");
			status = ResponseStatusType.INVALID.value();
		}
		responseModel.setStatus(status);
		responseModel.setStatusDescription(errorMessage);
		addDataInEmailSmsAuditLog(requestWrapper);
		return responseModel;
	}

	private void addDataInEmailSmsAuditLog(ContentCachingRequestWrapper requestWrapper) {
		if (requestWrapper.getRequestURI().endsWith(url.EMAIL_URL.value())) {
			EmailNotificationRequestModel emailNotificationRequestModel = mapperService
					.mapEmailNotificationRequestModel(requestWrapper);
			if (emailNotificationRequestModel != null) {
				String emails = StringUtils.strip(emailNotificationRequestModel.getEmails().toString(), "[]");
				emailSmsAuditLogService.addDataInEmailSMSAuditLog(emails, null,
						emailNotificationRequestModel.getRequestType(), emailNotificationRequestModel.getRequestId(),
						emailNotificationRequestModel.getePrescriptionReferenceNumber(),
						emailNotificationRequestModel.getUrl(), requestWrapper);
			}
		} else if (requestWrapper.getRequestURI().endsWith(url.SMS_URL.value())) {
			SmsNotificationRequestModel smsNotificationRequestModel = mapperService
					.mapSmsNotificationRequestModel(requestWrapper);
			if (null != smsNotificationRequestModel) {
				emailSmsAuditLogService.addDataInEmailSMSAuditLog("", smsNotificationRequestModel.getMobileNumber(),
						smsNotificationRequestModel.getRequestType(), smsNotificationRequestModel.getRequestId(),
						smsNotificationRequestModel.getePrescriptionReferenceNumber(),
						smsNotificationRequestModel.getUrl(), requestWrapper);
			}
		}
	}
}
