package com.waseel.pbmnotificationservice.service.clienthandler;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.waseel.pbmnotificationservice.clients.EmailServiceClient;
import com.waseel.pbmnotificationservice.clients.PrescriptionClient;
import com.waseel.pbmnotificationservice.clients.TawuniyaClient;
import com.waseel.pbmnotificationservice.model.email.EmailNotificationResponseModel;
import com.waseel.pbmnotificationservice.model.email.EmailRequestModel;
import com.waseel.pbmnotificationservice.model.eprescription.inquiry.EPrescriptionInquiryResponseModel;
import com.waseel.pbmnotificationservice.service.ConnectionFailedService;
import com.waseel.pbmnotificationservice.service.MapperService;

import feign.FeignException;

@Service
public class RestClientHandlerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestClientHandlerService.class);

	@Autowired
	private TawuniyaClient tawuniyaClient;

	@Autowired
	private PrescriptionClient prescriptionClient;

	@Autowired
	private EmailServiceClient emailServiceClient;

	@Autowired
	private MapperService mapperService;

	@Autowired
	private ConnectionFailedService connectionFailedService;

	@Retryable(value = FeignException.class, backoff = @Backoff(delay = 100))
	public ResponseEntity<EPrescriptionInquiryResponseModel> sendRequestToGetEPrescriptionInquiry(
			String ePrescriptionReferenceNumber, String approvalReferenceNumber) throws FeignException {
		LOGGER.info("Send ePrescriptionReferenceNumber: {} to Tawuniya for ePrescription Inquiry",
				ePrescriptionReferenceNumber);
		return tawuniyaClient.getEPrescriptionInquiry(approvalReferenceNumber, ePrescriptionReferenceNumber);
	}

	@Retryable(value = FeignException.class, backoff = @Backoff(delay = 100))
	public ResponseEntity<Object> sendRequestToUpdatePrescription(EPrescriptionInquiryResponseModel requestModel,
			String payerId) throws FeignException {
		LOGGER.info("Send ePrescriptionReferenceNumber: {} to Prescription service for update Prescription status",
				requestModel.getePrescriptionReferenceNumber());
		return prescriptionClient.updatePrescription(payerId, requestModel);
	}

	public ResponseEntity<EmailNotificationResponseModel> sendRequestToSendEmail(EmailRequestModel emailRequestModel) {
		List<String> emailIds = emailRequestModel.getRecipients();
		try {
			LOGGER.info("Send emailIds: {} to the Email service for sending the email", emailIds);
			return emailServiceClient.sendEmailNofitication(emailRequestModel);
		} catch (FeignException e) {
			LOGGER.error("FeignException Has Been Thrown While Reading The Response From Email service "
					+ "service For emailIds: {} , failed with status [{}]", emailIds, e.status());
			if (e.status() == HttpStatus.BAD_REQUEST.value()
					|| e.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {

				return ResponseEntity.status(e.status())
						.body(mapperService.mapEmailNotificationResponseModel(e.contentUTF8()));
			}
			if (e.status() == -1) {
				return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE.value())
						.body(connectionFailedService.emailServiceForConnectionFailure());
			}
		} catch (Exception e) {
			LOGGER.error("Exception Has Been Thrown While Reading The Response From Email service"
					+ " service For For emailId: {}, Error: {}", emailIds, e);
		}
		return null;
	}
}
