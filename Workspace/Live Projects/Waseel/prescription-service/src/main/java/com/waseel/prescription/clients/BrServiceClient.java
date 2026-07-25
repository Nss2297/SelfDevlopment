package com.waseel.prescription.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.waseel.prescription.model.br.SensitiveDrugRequestModel;
import com.waseel.prescription.model.br.SensitiveDrugResponseModel;

@FeignClient(name = "BrServiceClient", url = "${brservice.url}")
public interface BrServiceClient {

	@PostMapping("/sensitive-drug/validate")
	public ResponseEntity<SensitiveDrugResponseModel> sendPresecriptionToBrService(
			@RequestBody SensitiveDrugRequestModel sensitiveDrugRequestModel);

}
