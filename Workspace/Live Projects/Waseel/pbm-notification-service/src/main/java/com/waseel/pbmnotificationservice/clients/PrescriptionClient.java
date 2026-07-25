package com.waseel.pbmnotificationservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.waseel.pbmnotificationservice.model.eprescription.inquiry.EPrescriptionInquiryResponseModel;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@FeignClient(name = "PrescriptionClient", url = "${client.prescription.url}")
public interface PrescriptionClient {

	@PutMapping("/payers/{payerId}/prescription/update-status")
	ResponseEntity<Object> updatePrescription(@PathVariable(name = "payerId") String payerId,
			@RequestBody EPrescriptionInquiryResponseModel ePrescriptionInquiryResponseModel);
}



