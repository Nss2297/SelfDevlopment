package com.waseel.pbmnotificationservice.service.email;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.pbmnotificationservice.model.email.EmailNotificationRequestModel;
import com.waseel.pbmnotificationservice.model.email.EmailNotificationResponseModel;
import com.waseel.pbmnotificationservice.model.email.EmailRequestModel;
import com.waseel.pbmnotificationservice.model.enums.EmailInformation;
import com.waseel.pbmnotificationservice.model.enums.ResponseStatusType;
import com.waseel.pbmnotificationservice.service.EmailSmsAuditLogService;
import com.waseel.pbmnotificationservice.service.clienthandler.RestClientHandlerService;

@Service
public class EmailService {

	@Autowired
	private RestClientHandlerService restClientHandlerService;

	@Autowired
	private EmailMessageBodyService emailMessageBodyService;

	@Autowired
	private EmailSmsAuditLogService emailSmsAuditLogService;

	public EmailNotificationResponseModel sendEmailToPatient(
			EmailNotificationRequestModel emailNotificationRequestModel, ContentCachingRequestWrapper requestWrapper) {
		addDataInEmailSmsAuditLog(emailNotificationRequestModel, requestWrapper);
		EmailNotificationResponseModel emailNotificationResponseModel = new EmailNotificationResponseModel();
		EmailRequestModel emailRequestModel = prepareEmailRequestModel(emailNotificationRequestModel);
		ResponseEntity<EmailNotificationResponseModel> emailResponse = restClientHandlerService
				.sendRequestToSendEmail(emailRequestModel);
		if (emailResponse != null && emailResponse.getStatusCode() != HttpStatus.OK) {
			return emailResponse.getBody();
		}
		emailNotificationResponseModel.setStatus(ResponseStatusType.SUCCESS.value());
		return emailNotificationResponseModel;
	}

	private EmailRequestModel prepareEmailRequestModel(EmailNotificationRequestModel emailNotificationRequestModel) {
		String emailMsgBody = prepareEmailMessageBody(emailNotificationRequestModel);
		return new EmailRequestModel(emailNotificationRequestModel.getEmails(), emailMsgBody,
				EmailInformation.SUBJECT.value(), true, EmailInformation.SENDER_NAME.value());
	}

	private String prepareEmailMessageBody(EmailNotificationRequestModel emailNotificationRequestModel) {
		String memberName = emailNotificationRequestModel.getMemberName();
		String providerName = emailNotificationRequestModel.getProviderName();
		String ePrescriptionReferenceNumber = emailNotificationRequestModel.getePrescriptionReferenceNumber();
		String patientUrl = emailNotificationRequestModel.getUrl();
		return emailMessageBodyService.getHtmlContentOfEmail(memberName, providerName, ePrescriptionReferenceNumber,
				patientUrl);
	}

	private void addDataInEmailSmsAuditLog(EmailNotificationRequestModel emailNotificationRequestModel,
			ContentCachingRequestWrapper requestWrapper) {
		String emails = StringUtils.strip(emailNotificationRequestModel.getEmails().toString(), "[]");
		emailSmsAuditLogService.addDataInEmailSMSAuditLog(emails, null, emailNotificationRequestModel.getRequestType(),
				emailNotificationRequestModel.getRequestId(),
				emailNotificationRequestModel.getePrescriptionReferenceNumber(), emailNotificationRequestModel.getUrl(),
				requestWrapper);
	}
}
