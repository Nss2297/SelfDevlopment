package com.waseel.pbmnotificationservice.service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.waseel.pbmnotificationservice.model.email.EmailNotificationResponseModel;
import com.waseel.pbmnotificationservice.model.enums.TransactionStatusType;
import com.waseel.pbmnotificationservice.model.enums.url;
import com.waseel.pbmnotificationservice.model.sms.SmsNotificationResponseModel;
import com.waseel.pbmnotificationservice.persist.businessrules.EmailSmsAuditLog;
import com.waseel.pbmnotificationservice.repository.businessrules.EmailSmsAuditlogRepository;

@Service
public class EmailSmsAuditLogService {

	private static final Logger log = LoggerFactory.getLogger(EmailSmsAuditLogService.class);

	@Autowired
	private EmailSmsAuditlogRepository emailSmsAuditlogRepository;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private MapperService mapperService;

	public EmailSmsAuditLog addDataInEmailSMSAuditLog(String email, String mobileNumber, String requestType,
			String requestId, String ePrescriptionReferenceNumber, String patientUrl,
			ContentCachingRequestWrapper requestWrapper) {
		try {
			String emailSmsAuditLogId = sessionService.getTransactionLogIdFromSession(requestWrapper);
			if (StringUtils.isNotBlank(emailSmsAuditLogId)) {
				Optional<EmailSmsAuditLog> emailSmsauditlogOpt = emailSmsAuditlogRepository
						.findById(Long.valueOf(emailSmsAuditLogId));
				if (emailSmsauditlogOpt.isPresent()) {
					log.info("EmailSmsAuditLog fetched for EmailSmsAuditLogId[{}]", emailSmsAuditLogId);
					return emailSmsauditlogOpt.get();
				}
			} else {
				EmailSmsAuditLog auditLog = new EmailSmsAuditLog();
				auditLog.setEmail(email);
				auditLog.setMobileNumber(mobileNumber);
				auditLog.setRequestType(requestType);
				auditLog.setTransactionStatus(TransactionStatusType.SENT.value());
				auditLog.setSendingRequestDateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
				auditLog.setRequestId(requestId);
				auditLog.setEprescriptionReferenceNumber(ePrescriptionReferenceNumber);
				auditLog.setPatientUrl(patientUrl);
				EmailSmsAuditLog updateAuditLog = emailSmsAuditlogRepository.save(auditLog);
				log.info("EmailSmsAuditLog added for EmailSmsAuditLogId[{}]", updateAuditLog.getEmailSmsAuditLogId());
				sessionService.setTransactionLogIdInSession(requestWrapper, updateAuditLog.getEmailSmsAuditLogId());
				return updateAuditLog;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception occur while adding data in EmailSmsAuditlog:", e);
		}
		return null;
	}

	public EmailSmsAuditLog updateDataInEmailSMSAuditLog(Long emailSmsAuditlogId, String status,
			String statusDescription, String unifonicMessageId, String unifonicTimeCreated,
			ContentCachingRequestWrapper requestWrapper) {
		try {
			Optional<EmailSmsAuditLog> emailSmsauditlogOpt = emailSmsAuditlogRepository.findById(emailSmsAuditlogId);
			if (emailSmsauditlogOpt.isPresent()) {
				EmailSmsAuditLog auditlog = emailSmsauditlogOpt.get();
				auditlog.setStatus(status);
				auditlog.setStatusDescription(statusDescription);
				auditlog.setReceivingResponseDateTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
				auditlog.setTransactionStatus(TransactionStatusType.RECEIVED.value());
				auditlog.setUnifonicMessageId(unifonicMessageId);
				auditlog.setUnifonicTimeCreated(unifonicTimeCreated);
				EmailSmsAuditLog updateAuditlog = emailSmsAuditlogRepository.save(auditlog);
				log.info("EmailSmsAuditLog updated for EmailSmsAuditLogId[{}]", updateAuditlog.getEmailSmsAuditLogId());
				sessionService.removeTransactionLogIdFromSession(requestWrapper);
				return updateAuditlog;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Exception occure while updating data in EmailSmsAuditlog:", e);
		}
		return null;
	}

	public void manageEmailSmsAuditlogFromResponse(ContentCachingRequestWrapper requestWrapper,
			ContentCachingResponseWrapper responseWrapper) {
		try {
			if (requestWrapper.getRequestURI().endsWith(url.EMAIL_URL.value())) {
				manageEmailAuditLog(requestWrapper, responseWrapper);
			} else if (requestWrapper.getRequestURI().endsWith(url.SMS_URL.value())) {
				manageSmsAuditLog(requestWrapper, responseWrapper);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("EmailSmsAuditLogService Response exception:-", e);
		}
	}

	private void manageEmailAuditLog(ContentCachingRequestWrapper requestWrapper,
			ContentCachingResponseWrapper responseWrapper) {
		String emailSmsAuditlogId = sessionService.getTransactionLogIdFromSession(requestWrapper);
		EmailNotificationResponseModel responseModel = mapperService.mapEmailNotificationResponseModel(responseWrapper);
		String status = null;
		String statusDescription = null;
		if (responseModel != null) {
			status = responseModel.getStatus();
			statusDescription = responseModel.getStatusDescription();
		}
		if(!StringUtils.isBlank(emailSmsAuditlogId)) {
			updateDataInEmailSMSAuditLog(Long.parseLong(emailSmsAuditlogId), status, statusDescription, null, null,
					requestWrapper);	
		}
	}

	private void manageSmsAuditLog(ContentCachingRequestWrapper requestWrapper,
			ContentCachingResponseWrapper responseWrapper) {
		String emailSmsAuditlogId = sessionService.getTransactionLogIdFromSession(requestWrapper);
		SmsNotificationResponseModel smsNotificationResponseModel = mapperService
				.mapSmsNotificationResponseModel(responseWrapper);
		String status = "";
		String statusDescription = "";
		String mesageId = "";
		String timeCreated = "";
		if (null != smsNotificationResponseModel) {
			status = smsNotificationResponseModel.getStatus();
			statusDescription = smsNotificationResponseModel.getStatusDescription();
			mesageId = smsNotificationResponseModel.getMessageId();
			timeCreated = smsNotificationResponseModel.getTimeCreated();

		}
		if (StringUtils.isNotBlank(emailSmsAuditlogId)) {
			updateDataInEmailSMSAuditLog(Long.parseLong(emailSmsAuditlogId), status, statusDescription, mesageId,
					timeCreated, requestWrapper);
		}
	}
}
