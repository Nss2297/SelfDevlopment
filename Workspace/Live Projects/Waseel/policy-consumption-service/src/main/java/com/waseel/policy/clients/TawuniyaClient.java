package com.waseel.policy.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.waseel.policy.model.client.MemberDetailsResponseModel;

@FeignClient(name = "TawuniyaClient", url = "${clients.tawuniya.url}")
public interface TawuniyaClient {

	@GetMapping(path = "/members-details")
	public ResponseEntity<MemberDetailsResponseModel> getMemberDetails(@RequestParam(name = "idNumber") Long idNumber,
			@RequestParam(name = "memberId") String memberId, @RequestParam(name = "policyNumber") String policyNumber,
			@RequestParam(name = "providerPayerCode") String providerPayerCode);
}
