package com.waseel.pbmnotificationservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.waseel.pbmnotificationservice.clients.configuration.TawuniyaClientConfigurations;
import com.waseel.pbmnotificationservice.model.eprescription.inquiry.EPrescriptionInquiryResponseModel;

@FeignClient(name = "TawuniyaClient", url = "${client.tawuniya.url}", configuration = TawuniyaClientConfigurations.class)
public interface TawuniyaClient {

	@GetMapping("/eprescription/inquiry")
	public ResponseEntity<EPrescriptionInquiryResponseModel> getEPrescriptionInquiry(
			@RequestParam("approvalReferenceNumber") String approvalReferenceNumber,
			@RequestParam("ePrescriptionReferenceNumber") String ePrescriptionReferenceNumber);
}
