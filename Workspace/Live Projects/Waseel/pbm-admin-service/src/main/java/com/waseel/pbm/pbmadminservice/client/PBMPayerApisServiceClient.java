package com.waseel.pbm.pbmadminservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.waseel.pbm.pbmadminservice.model.MemberDetail;
import com.waseel.pbm.pbmadminservice.model.payer.PolicyMetadataResponseModel;

@FeignClient(name = "PBMPayerApisServiceClient", url = "${pbmPayerApisServiceClient.url}")
public interface PBMPayerApisServiceClient {

	@GetMapping("/payers/tawuniya/member-demographic")
	public ResponseEntity<MemberDetail> getMemberDemographicData(@RequestParam(name = "idNumber") Long idNumber);

	@GetMapping("/payers/tawuniya/policy-details/{policyNumber}")
	public ResponseEntity<PolicyMetadataResponseModel> getPolicyData(
			@PathVariable(name = "policyNumber") String policyNumber);
}
