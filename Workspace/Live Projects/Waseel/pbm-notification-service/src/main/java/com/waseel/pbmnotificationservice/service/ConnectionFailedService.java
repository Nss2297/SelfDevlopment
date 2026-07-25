package com.waseel.pbmnotificationservice.service;

import org.springframework.stereotype.Service;

import com.waseel.pbmnotificationservice.model.email.EmailNotificationResponseModel;
import com.waseel.pbmnotificationservice.model.enums.ConnectionIssueStatus;
import com.waseel.pbmnotificationservice.model.enums.ResponseStatusType;
import com.waseel.pbmnotificationservice.model.enums.ServiceName;
import com.waseel.pbmnotificationservice.model.unifonic.UnifonicReponseModel;

@Service
public class ConnectionFailedService {

	public EmailNotificationResponseModel emailServiceForConnectionFailure() {
		return new EmailNotificationResponseModel(ResponseStatusType.FAILED.value(),
				"Not able to call Email-service");
	}
	
	public UnifonicReponseModel smsServiceReponseForConnectionFailure() {
		return new UnifonicReponseModel(false,
				ConnectionIssueStatus.ERROR_MESSAGE.value() + ServiceName.WASEEL_SMS_SERVICE.value(),
				ConnectionIssueStatus.FAILED.value(), ConnectionIssueStatus.FAILED.value(), null);
	}
}
