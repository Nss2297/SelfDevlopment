package com.waseel.pbmpayerapisservice.controller;

import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.waseel.pbmpayerapisservice.clients.MockServerClient;
import com.waseel.pbmpayerapisservice.clients.TawuniyaClient;
import com.waseel.pbmpayerapisservice.model.EPrescriptionResponseModel;
import com.waseel.pbmpayerapisservice.model.EprescriptionRequestModel;
import com.waseel.pbmpayerapisservice.model.InvalidResponseModel;
import com.waseel.pbmpayerapisservice.model.PolicyDetailsResponseModel;
import com.waseel.pbmpayerapisservice.service.InvalidResponseService;

import feign.FeignException;

@RestController
@RequestMapping("/payers/tawuniya")
public class TawuniyaApisController {

	@Autowired
	private InvalidResponseService invalidResponseService;

	private Logger log = LoggerFactory.getLogger(TawuniyaApisController.class);

	@Autowired
	private TawuniyaClient tawuniyaClient;

	@Autowired
	private MockServerClient mockServerClient;

	@GetMapping("/member-demographic")
	public ResponseEntity<?> getMemberDemographicData(@RequestParam(name = "idNumber", required = false) Long idNumber,
			@RequestParam(name = "memberId", required = false) String memberId,
			@RequestParam(name = "policyNumber", required = false) String policyNumber) throws Exception {
		InvalidResponseModel invalidResponseModel = invalidResponseService
				.populateInvalidResponseForPolicyDetails(idNumber, memberId, policyNumber);
		if (null != invalidResponseModel) {
			log.error("Error fetching member demographic data.");
			return ResponseEntity.badRequest().body(invalidResponseModel);
		}
		if (StringUtils.isNotBlank(policyNumber) && StringUtils.isNotBlank(memberId)) {
			log.info("Fetch member-demographic data with MemberId: {}, and PolicyNumber: {}", memberId, policyNumber);
		} else {
			log.info("Fetch member-demographic data with IdNumber: {}", idNumber);
		}
		return mockServerClient.getMemberDemographicData(idNumber, memberId, policyNumber);
	}

	@PostMapping("/eprescription")
	public ResponseEntity<EPrescriptionResponseModel> getEPrescriptionApproval(
			@Valid @RequestBody EprescriptionRequestModel eprescriptionRequestModel) {
		return mockServerClient.getEPrescriptionApproval(eprescriptionRequestModel);
	}

	@GetMapping("/member-details")
	public ResponseEntity<?> getMemberDetails(@RequestParam(name = "idNumber", required = false) Long idNumber,
			@RequestParam(name = "memberId", required = false) String memberId,
			@RequestParam(name = "policyNumber", required = false) String policyNumber,
			@RequestParam(name = "providerPayerCode", required = false) String providerPayerCode) {
		InvalidResponseModel invalidResponseModel = invalidResponseService
				.populateInvalidResponseForMemberDetails(idNumber, memberId, policyNumber, providerPayerCode);
		if (null != invalidResponseModel) {
			log.error("Error fetching member details data.");
			return ResponseEntity.badRequest().body(invalidResponseModel);
		}
		return mockServerClient.getMemberDetails(idNumber, memberId, policyNumber, providerPayerCode);
	}

	@GetMapping("/policy-details/{policyNumber}")
	public ResponseEntity<PolicyDetailsResponseModel> getPolicyDetails(
			@PathVariable(name = "policyNumber") String policyNumber) {
		log.info("Fetch details policy number: {}", policyNumber);
		return mockServerClient.fetchPolicyDetails(policyNumber);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
		log.info("MethodArgumentNotValidException 400 Http Response Has been Returned From PBM Payer Apis Service");
		return ResponseEntity.badRequest().body(invalidResponseService.createMethodArgumentNotValidInvalidResponse(ex));
	}

	@ExceptionHandler(FeignException.class)
	ResponseEntity<InvalidResponseModel> handleFeignExceptions(FeignException ex) {
		log.info("FeignException for status {}", ex.status());
		int status = ex.status() == -1 ? HttpStatus.SERVICE_UNAVAILABLE.value() : ex.status();
		return ResponseEntity.status(status).body(invalidResponseService.createInvalidResponseModel(ex));
	}

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleValidationExceptions(Exception ex) {
		log.info("Internal Server Error 500 : Has Been Returned From PBM Payer Apis Service Due To : ", ex.getCause());
		ex.printStackTrace();
		return new ResponseEntity<>(invalidResponseService.createFailedResponse(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
