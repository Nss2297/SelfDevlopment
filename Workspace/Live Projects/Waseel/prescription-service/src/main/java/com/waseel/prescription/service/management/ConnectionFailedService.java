package com.waseel.prescription.service.management;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.waseel.prescription.model.eligibility.EligibilityResponseModel;
import com.waseel.prescription.model.enums.ConnectionIssueStatus;
import com.waseel.prescription.model.notification.EmailNotificationResponseModel;
import com.waseel.prescription.model.notification.SmsNotificationResponseModel;
import com.waseel.prescription.model.pbmpayerapis.EPrescriptionResponseModel;

@Service
public class ConnectionFailedService {

	public EligibilityResponseModel eligibilityResponseForConnectionFailure(String requestId, String error) {
		return new EligibilityResponseModel(ConnectionIssueStatus.FAILED.value(), error, "", "", requestId,
				HttpStatus.INTERNAL_SERVER_ERROR.value());
	}

	public EPrescriptionResponseModel ePrescriptionResponseModelForConnectionFailure() {
		return new EPrescriptionResponseModel(ConnectionIssueStatus.FAILED.value(),
				"Not able to call PBM-Payer-Apis-Service");
	}

	public SmsNotificationResponseModel pbmNotificationResponseForConnectionFailure() {
		return new SmsNotificationResponseModel(ConnectionIssueStatus.FAILED.value(),
				"Not able to call PBM-Notification-service for Sms.", "", "");
	}

	public EmailNotificationResponseModel pbmEmailNotificationResponseForConnectionFailure() {
		return new EmailNotificationResponseModel(ConnectionIssueStatus.FAILED.value(),
				"Not able to call PBM-Notification-service for Email");
	}
}