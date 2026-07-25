package com.waseel.prescription.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.waseel.prescription.model.policyconsumption.CancellAndDispensePolicyRequestModel;
import com.waseel.prescription.model.policyconsumption.DeactivatePrescriptionRequestModel;
import com.waseel.prescription.model.policyconsumption.PolicyRequestModel;
import com.waseel.prescription.model.policyconsumption.PolicyResponseModel;

@FeignClient(name = "PolicyConsumptionServiceClient", url = "${policyConsumptionService.url}")
public interface PolicyConsumptionClient {

	@PostMapping("/patients/{idNumber}/policyConsumption")
	public ResponseEntity<PolicyResponseModel> checkPolicyConsumption(@PathVariable String idNumber,
			@RequestBody(required = true) PolicyRequestModel policyRequestModel);

	@PutMapping("/patients/{idNumber}/policyConsumption/cancel")
	public ResponseEntity<PolicyResponseModel> checkPolicyConsumptionForCancelPrescription(
			@PathVariable String idNumber,
			@RequestBody CancellAndDispensePolicyRequestModel policyCancellationRequestModel);

	@PutMapping("/patients/{idNumber}/policyConsumption/dispense")
	public ResponseEntity<PolicyResponseModel> checkPolicyConsumptionForDispensePrescription(
			@PathVariable String idNumber,
			@RequestBody CancellAndDispensePolicyRequestModel policyDispensinRequestModel);

	@PutMapping("/patients/{idNumber}/policyConsumption/deactivate-prescription")
	public ResponseEntity<PolicyResponseModel> markPrescriptionInactive(@PathVariable String idNumber,
			@RequestBody DeactivatePrescriptionRequestModel deactivatePrescriptionRequestModel);

	@GetMapping("/patients/{idNumber}/policyConsumption/dispensible-drugs")
	public ResponseEntity<PolicyResponseModel> fetchPayerAndPatientShareForDispensibleDrugs(
			@PathVariable String idNumber, @RequestParam(name = "benefitCode") String benefitCode,
			@RequestParam(name = "benefitCase") String benefitCase, @RequestParam(name = "payerId") String payerId,
			@RequestParam(name = "requestId") String requestId, @RequestParam(name = "providerId") String providerId,
			@RequestParam(name = "dispensibleDrugs") List<String> dispensibleDrugs);
}
