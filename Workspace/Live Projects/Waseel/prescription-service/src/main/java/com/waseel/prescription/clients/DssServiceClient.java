package com.waseel.prescription.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.waseel.prescription.model.dss.DssCancellationResponse;
import com.waseel.prescription.model.dss.DssCancellationRequest;
import com.waseel.prescription.model.dss.DssRequest;
import com.waseel.prescription.model.dss.DssResponse;

@FeignClient(name = "DssServiceClient", url = "${dssservice.url}")
public interface DssServiceClient {

	@PostMapping("/validate/new")
	public ResponseEntity<DssResponse> sendNewPrescriptionToDssServiceNewApi(@RequestBody DssRequest dssRequest);

	@PutMapping("/validate/followup")
	public ResponseEntity<DssResponse> sendPrescriptionFollowUpToDssFollowupApi(@RequestBody DssRequest dssRequest);

	@PostMapping("/validate/cancellation")
	public ResponseEntity<DssCancellationResponse> sendPrescriptionForCancellationToDss(
			@RequestBody DssCancellationRequest cancelRequest);
}
