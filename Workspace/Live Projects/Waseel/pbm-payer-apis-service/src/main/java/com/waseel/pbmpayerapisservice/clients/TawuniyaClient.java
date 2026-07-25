package com.waseel.pbmpayerapisservice.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.waseel.pbmpayerapisservice.model.EPrescriptionResponseModel;
import com.waseel.pbmpayerapisservice.model.EprescriptionRequestModel;
import com.waseel.pbmpayerapisservice.model.MemberDemographicDataResponseModel;
import com.waseel.pbmpayerapisservice.model.MemberDetailsResponseModel;
import com.waseel.pbmpayerapisservice.model.PolicyDetailsResponseModel;

@FeignClient(name = "TawuniyaClient", url = "${client.tawuniya.url}", configuration = TawuniyaClientConfigurations.class)
public interface TawuniyaClient {

	@GetMapping(path = "/member/demographic")
	public ResponseEntity<MemberDemographicDataResponseModel> getMemberDemographicData(
			@RequestParam(name = "idNumber") Long idNumber, @RequestParam(name = "memberId") String memberId,
			@RequestParam(name = "policyNumber") String policyNumber);

	@PostMapping(path = "/eprescription")
	public ResponseEntity<EPrescriptionResponseModel> getEPrescriptionApproval(
			@RequestBody EprescriptionRequestModel eprescriptionRequestModel);

	@GetMapping(path = "/member/details")
	public ResponseEntity<MemberDetailsResponseModel> getMemberDetails(@RequestParam(name = "idNumber") Long idNumber,
			@RequestParam(name = "memberId") String memberId, @RequestParam(name = "policyNumber") String policyNumber,
			@RequestParam(name = "providerPayerCode") String providerPayerCode);

	@GetMapping(path = "/policies/{policyNumber}")
	public ResponseEntity<PolicyDetailsResponseModel> fetchPolicyDetails(
			@PathVariable(name = "policyNumber") String policyNumber);
}
