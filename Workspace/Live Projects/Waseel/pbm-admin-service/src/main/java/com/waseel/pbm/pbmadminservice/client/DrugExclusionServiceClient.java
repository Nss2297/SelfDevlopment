package com.waseel.pbm.pbmadminservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import com.waseel.pbm.pbmadminservice.model.drugexclusion.DrugExclusionRequestModel;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@FeignClient(name = "DrugExclusionServiceClient", url = "${drugexclusionservice.url}")
public interface DrugExclusionServiceClient {

	@PostMapping("/drug-exclusion")
	public ResponseEntity<Long> sendDrugExclusionRequest(@RequestBody DrugExclusionRequestModel model);
}
