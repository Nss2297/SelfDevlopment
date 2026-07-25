package com.waseel.pbmnotificationservice.service.eprescription.inquiry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.waseel.pbmnotificationservice.model.eprescription.inquiry.EPrescriptionInquiryResponseModel;
import com.waseel.pbmnotificationservice.model.eprescription.notification.NotificationRequestModel;
import com.waseel.pbmnotificationservice.service.clienthandler.RestClientHandlerService;

@Service
public class EPrescriptionInquiryService {

	@Autowired
	private RestClientHandlerService restClientHandlerService;

	@Async("ePrescriptionInquiryTaskExecutor")
	public void getEPrescriptionInquiryAndUpdatePrescription(NotificationRequestModel notificationRequestModel) {
		EPrescriptionInquiryResponseModel ePreInquiryRes = getEPrescriptionInquiry(notificationRequestModel.getePrescriptionReferenceNumber(), notificationRequestModel.getApprovalReferenceNumber());
		if (ePreInquiryRes != null) {
			restClientHandlerService.sendRequestToUpdatePrescription(ePreInquiryRes, ePreInquiryRes.getPayerId());
		}
	}

	private EPrescriptionInquiryResponseModel getEPrescriptionInquiry(String ePrescriptionReferenceNumber, String approvalReferenceNumber) {
		ResponseEntity<EPrescriptionInquiryResponseModel> response = restClientHandlerService
				.sendRequestToGetEPrescriptionInquiry(ePrescriptionReferenceNumber, approvalReferenceNumber);
		if (response.getStatusCode() == HttpStatus.OK) {
			return response.getBody();
		}
		return null;
	}
}
