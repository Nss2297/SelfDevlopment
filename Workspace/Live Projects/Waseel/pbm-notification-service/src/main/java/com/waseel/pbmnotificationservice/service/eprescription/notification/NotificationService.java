package com.waseel.pbmnotificationservice.service.eprescription.notification;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.waseel.pbmnotificationservice.exceptions.NotificationException;
import com.waseel.pbmnotificationservice.model.enums.HeadersName;
import com.waseel.pbmnotificationservice.model.enums.ResponseStatusType;
import com.waseel.pbmnotificationservice.model.eprescription.notification.NotificationRequestModel;
import com.waseel.pbmnotificationservice.model.eprescription.notification.NotificationResponseModel;
import com.waseel.pbmnotificationservice.persist.prescriptionservice.PayerNotifications;
import com.waseel.pbmnotificationservice.repository.prescriptionservice.PayerNotificationsRepository;
import com.waseel.pbmnotificationservice.service.TechnicalValidationService;
import com.waseel.pbmnotificationservice.service.eprescription.inquiry.EPrescriptionInquiryService;

@Service
public class NotificationService {

	@Autowired
	private PayerNotificationsRepository payerNotificationsRepository;

	@Autowired
	private TechnicalValidationService technicalValidationService;

	@Autowired
	private EPrescriptionInquiryService ePrescriptionInquiryService;

	public NotificationResponseModel receiveNotificationFromTawuniya(NotificationRequestModel notificationRequestModel,
			String payerId) throws NotificationException {
		Date date = new Date();
		technicalValidationService.checkEPrescriptionReferenceNumberIsExistsOrNot(
				notificationRequestModel.getePrescriptionReferenceNumber());
		saveDataInPayerNotificationsTable(notificationRequestModel, payerId, date);
		ePrescriptionInquiryService.getEPrescriptionInquiryAndUpdatePrescription(notificationRequestModel);
		return prepareNotificationResponseModel(notificationRequestModel, date);
	}

	private NotificationResponseModel prepareNotificationResponseModel(NotificationRequestModel requestModel,
			Date date) {
		NotificationResponseModel responseModel = new NotificationResponseModel();
		responseModel.setAcknowledgementDateAndTime(convertCurrentDateToString(date));
		responseModel.setApprovalReferenceNumber(requestModel.getApprovalReferenceNumber());
		responseModel.setePrescriptionReferenceNumber(requestModel.getePrescriptionReferenceNumber());
		responseModel.setStatus(ResponseStatusType.RECEIVED.value());
		return responseModel;
	}

	public HttpHeaders setResponseHeaders(String transactionId) {
		HttpHeaders responseHeaders = new HttpHeaders();
		Timestamp timestamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
		responseHeaders.set(HeadersName.SENDER_CODE.value(), "101");
		responseHeaders.set(HeadersName.RECEIVER_CODE.value(), "102");
		responseHeaders.set(HeadersName.TRANSACTION_ID.value(), transactionId);
		responseHeaders.setContentType(MediaType.APPLICATION_JSON);
		responseHeaders.set(HeadersName.TIME_STAMP.value(), timestamp.toString());
		responseHeaders.set(HeadersName.DIRECTION.value(), "RESPONSE");
		return responseHeaders;
	}

	private PayerNotifications saveDataInPayerNotificationsTable(NotificationRequestModel notificationRequestModel,
			String payerId, Date date) {
		PayerNotifications payerNotifications = new PayerNotifications(payerId,
				notificationRequestModel.getePrescriptionReferenceNumber(),
				notificationRequestModel.getApprovalReferenceNumber(), ResponseStatusType.RECEIVED.value(),
				notificationRequestModel.getePrescriptionStatus(), null, date);
		return payerNotificationsRepository.save(payerNotifications);
	}

	private String convertCurrentDateToString(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		return formatter.format(date);
	}
}
