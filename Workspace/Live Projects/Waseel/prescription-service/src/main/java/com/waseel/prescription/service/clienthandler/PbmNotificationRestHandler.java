package com.waseel.prescription.service.clienthandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.waseel.prescription.clients.PbmNotificationServiceClient;
import com.waseel.prescription.model.notification.EmailNotificationRequestModel;
import com.waseel.prescription.model.notification.EmailNotificationResponseModel;
import com.waseel.prescription.model.notification.SmsNotificationRequestModel;
import com.waseel.prescription.model.notification.SmsNotificationResponseModel;
import com.waseel.prescription.service.management.ConnectionFailedService;
import com.waseel.prescription.service.mapper.MapperService;

import feign.FeignException;

@Service
public class PbmNotificationRestHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(PbmNotificationRestHandler.class);

	@Autowired
	private PbmNotificationServiceClient notificationServiceClient;

	@Autowired
	private MapperService mapperService;

	@Autowired
	private ConnectionFailedService connectionIssueService;

	public SmsNotificationResponseModel sendNotificationToPatient(
			SmsNotificationRequestModel smsNotificationRequestModel) {
		return sendSmsNotificationToPatient(smsNotificationRequestModel);
	}

	public SmsNotificationResponseModel sendSmsNotificationToPatient(
			SmsNotificationRequestModel smsNotificationRequestModel) {
		String requestId = smsNotificationRequestModel.getRequestId();
		String ePrescriptionReferenceNumber = smsNotificationRequestModel.getePrescriptionReferenceNumber();
		try {
			LOGGER.info("Send member requestId: {} to Pbm notification service", requestId);
			ResponseEntity<SmsNotificationResponseModel> response = notificationServiceClient
					.sentNotificationToPatient(smsNotificationRequestModel);
			return response.getBody();
		} catch (FeignException e) {
			LOGGER.error(
					"FeignException Has Been Thrown While Reading The Response From Pbm notification service For requestId : {},"
							+ " failed with status [{}]",
					requestId, e.status(), e);
			if (e.status() == HttpStatus.BAD_REQUEST.value()
					|| e.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return mapperService.mapSmsNotificationResponse(e.contentUTF8());
			}
			if (e.status() == -1) {
				return connectionIssueService.pbmNotificationResponseForConnectionFailure();
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception Has Been Thrown While Reading The Response From Pbm notification service For requestId :{}"
							+ "  and ePrescriptionReferenceNumber: {} with Error: {}",
					requestId, ePrescriptionReferenceNumber, e);
		}
		return null;
	}

	public EmailNotificationResponseModel sendEmailNotification(
			EmailNotificationRequestModel emailNotificationRequestModel) {
		String requestId = emailNotificationRequestModel.getRequestId();
		String ePrescriptionReferenceNumber = emailNotificationRequestModel.getePrescriptionReferenceNumber();
		try {
			LOGGER.info("Send requestId: {} and ePrescriptionReferenceNumber: {} to Pbm notification service",
					requestId, ePrescriptionReferenceNumber);
			ResponseEntity<EmailNotificationResponseModel> response = notificationServiceClient
					.sendEmailNotification(emailNotificationRequestModel);
			return response.getBody();
		} catch (FeignException e) {
			LOGGER.error("FeignException Has Been Thrown While Reading The Response From Pbm notification service "
					+ "service For requestId: {} and ePrescriptionReferenceNumber: {} , failed with status [{}]",
					requestId, ePrescriptionReferenceNumber, e.status(), e);
			if (e.status() == HttpStatus.BAD_REQUEST.value()
					|| e.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return mapperService.mapEmailNotificationResponseModel(e.contentUTF8());
			}
			if (e.status() == -1) {
				return connectionIssueService.pbmEmailNotificationResponseForConnectionFailure();
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception Has Been Thrown While Reading The Response From Pbm notification service"
							+ " service For For requestId: {} and ePrescriptionReferenceNumber: {}  Error: {}",
					requestId, ePrescriptionReferenceNumber, e);
		}
		return null;
	}
}
