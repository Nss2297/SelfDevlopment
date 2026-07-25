package com.waseel.pbmnotificationservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.pbmnotificationservice.model.enums.ResponseStatusType;
import com.waseel.pbmnotificationservice.model.sms.SmsNotificationRequestModel;
import com.waseel.pbmnotificationservice.model.sms.SmsNotificationResponseModel;
import com.waseel.pbmnotificationservice.model.unifonic.UnifonicReponseModel;
import com.waseel.pbmnotificationservice.model.unifonic.UnifonicRequestModel;
import com.waseel.pbmnotificationservice.service.clienthandler.SsoRestHandler;
import com.waseel.pbmnotificationservice.service.clienthandler.WaseelSmsServiceRestHandler;

@Service
public class PbmSmsService {

	@Autowired
	private WaseelSmsServiceRestHandler waseelSmsServiceRestHandler;

	@Autowired
	private SsoRestHandler ssoRestHandler;

	@Value("${unifonic.appSid}")
	private String appSid;

	@Value("${unifonic.senderId}")
	private String senderId;

	@Value("${unifonic.appName}")
	private String appName;

	@Value("${sso.auth.username}")
	private String ssoUsername;

	@Value("${sso.auth.password}")
	private String ssoPassword;

	@Value("${sso.body.key}")
	private String requestBodyKey;

	@Value("${sso.body.value}")
	private String requestBodyValue;

	@Autowired
	private EmailSmsAuditLogService emailSmsAuditLogService;

	public SmsNotificationResponseModel sendSmsNotificationToMember(
			SmsNotificationRequestModel smsNotificationRequestModel,
			ContentCachingRequestWrapper contentCachingRequestWrapper) {
		manageSmsAuditLogs(smsNotificationRequestModel, contentCachingRequestWrapper);
		UnifonicRequestModel unifonicRequestModel = generateUnifonicRequest(smsNotificationRequestModel);
		UnifonicReponseModel unifoniReponseModel = waseelSmsServiceRestHandler
				.sendMemberDetailsToWaseelSmsService(smsNotificationRequestModel.getRequestId(), unifonicRequestModel);
		return generateSmsNotificationResponse(unifoniReponseModel);
	}

	private void manageSmsAuditLogs(SmsNotificationRequestModel smsNotificationRequest,
			ContentCachingRequestWrapper contentCachingRequestWrapper) {
		emailSmsAuditLogService.addDataInEmailSMSAuditLog("", smsNotificationRequest.getMobileNumber(),
				smsNotificationRequest.getRequestType(), smsNotificationRequest.getRequestId(),
				smsNotificationRequest.getePrescriptionReferenceNumber(), smsNotificationRequest.getUrl(),
				contentCachingRequestWrapper);
	}

	private UnifonicRequestModel generateUnifonicRequest(SmsNotificationRequestModel smsNotificationRequestModel) {
		return new UnifonicRequestModel(appSid, senderId, appName, smsNotificationRequestModel.getMessage(),
				smsNotificationRequestModel.getMobileNumber());
	}

	private SmsNotificationResponseModel generateSmsNotificationResponse(UnifonicReponseModel unifoniReponseModel) {
		String status = !unifoniReponseModel.isSuccess() ? ResponseStatusType.INVALID.value()
				: ResponseStatusType.SUCCESS.value();
		String messageId = "";
		String timeCreated = "";
		if (null != unifoniReponseModel.getUnifoniDataModel()) {
			messageId = unifoniReponseModel.getUnifoniDataModel().getMessageId();
			timeCreated = unifoniReponseModel.getUnifoniDataModel().getTimeCreated();
		}
		return new SmsNotificationResponseModel(status, unifoniReponseModel.getMessage(), messageId, timeCreated);
	}
}
