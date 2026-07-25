package com.waseel.prescription.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.waseel.prescription.model.pbmpayerapis.EPrescriptionRequestModel;
import com.waseel.prescription.model.pbmpayerapis.EPrescriptionResponseModel;
import com.waseel.prescription.model.pbmpayerapis.MemberDemographicDataResponseModel;

@FeignClient(name = "PBMPayerApisServiceClient", url = "${pbmPayerApisServiceClient.url}")
public interface PBMPayerApisServiceClient {

	@GetMapping("/payers/tawuniya/member-demographic")
	public ResponseEntity<MemberDemographicDataResponseModel> getMemberDemographicData(
			@RequestParam(name = "idNumber", required = false) Long idNumber);

	@PostMapping("/payers/tawuniya/eprescription")
	public ResponseEntity<EPrescriptionResponseModel> getEPrescriptionApproval(
			@RequestBody EPrescriptionRequestModel eprescriptionRequestModel);
}
