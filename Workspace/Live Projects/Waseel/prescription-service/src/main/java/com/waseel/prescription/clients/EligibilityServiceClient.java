package com.waseel.prescription.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.waseel.prescription.model.eligibility.EligibilityResponseModel;

@FeignClient(name = "EligibilityServiceClient", url = "${eligibilityservice.url}")
public interface EligibilityServiceClient {

	@GetMapping("/patients/{idNumber}/eligibility")
	public ResponseEntity<EligibilityResponseModel> checkMemberEligibility(@PathVariable String idNumber,
			@RequestParam(name = "payerId", required = true) String payerId,
			@RequestParam(name = "providerId", required = true) String providerId,
			@RequestParam(name = "requestId", required = true) String requestId);
}
