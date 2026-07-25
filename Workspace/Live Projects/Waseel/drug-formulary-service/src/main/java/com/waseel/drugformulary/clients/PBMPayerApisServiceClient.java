package com.waseel.drugformulary.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.waseel.drugformulary.model.pbmpayerapis.MemberDemographicDataResponseModel;

@FeignClient(name = "PBMPayerApisServiceClient", url = "${pbmPayerApisServiceClient.url}")
public interface PBMPayerApisServiceClient {

	@GetMapping("/payers/tawuniya/member-demographic")
	public ResponseEntity<MemberDemographicDataResponseModel> getMemberDemographicData(
			@RequestParam(name = "idNumber", required = false) Long idNumber);

}
