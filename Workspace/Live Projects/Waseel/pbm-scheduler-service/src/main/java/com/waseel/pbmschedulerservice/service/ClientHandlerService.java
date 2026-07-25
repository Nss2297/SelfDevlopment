package com.waseel.pbmschedulerservice.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.waseel.pbmschedulerservice.clients.PolicyDetailsServiceClient;
import com.waseel.pbmschedulerservice.model.policydetails.BenefitCasesResponseModel;
import com.waseel.pbmschedulerservice.model.policydetails.BenefitSubCoverageResponseModel;
import com.waseel.pbmschedulerservice.model.policydetails.ClassBenefitsResponseModel;
import com.waseel.pbmschedulerservice.model.policydetails.PolicyClassesResponseModel;
import com.waseel.pbmschedulerservice.model.policydetails.PolicyEndorsementResponseModel;
import com.waseel.pbmschedulerservice.model.policydetails.PolicyMetaDataResponseModel;
import com.waseel.pbmschedulerservice.model.policydetails.PolicyMetadataModel;

import feign.FeignException;

@Service
public class ClientHandlerService {

	@Autowired
	private PolicyDetailsServiceClient policyDetailsServiceClient;

	public ResponseEntity<PolicyMetaDataResponseModel> apiCallToGetPolicyMetadataDetails(int pageSize, int pageNumber)
			throws FeignException {
		return policyDetailsServiceClient.getPolicyMetadataDetails(getCurrentDateAsString(), getCurrentDateAsString(),
				getCurrentDateAsString(), pageSize, pageNumber);
	}

	public ResponseEntity<PolicyMetadataModel> apiCallToGetPolicyMetadataDetailsByPolicyNumber(String policyNumber)
			throws FeignException {
		return policyDetailsServiceClient.getPolicyMetadataDetailsByPolicyNumber(policyNumber);
	}

	public ResponseEntity<PolicyClassesResponseModel> apicallToGetPolicyClassesDetails(String policyNumber)
			throws FeignException {
		return policyDetailsServiceClient.getPolicyClassesDetails(policyNumber);
	}

	public ResponseEntity<ClassBenefitsResponseModel> apicallToGetClassBenefitsDetails(String policyNumber,
			String classCode) throws FeignException {
		return policyDetailsServiceClient.getPolicyClassBenefitsDetails(policyNumber, classCode);
	}

	public ResponseEntity<BenefitCasesResponseModel> apicallToGetBenefitCasesDetails(String policyNumber,
			String classCode, String benefitCode) throws FeignException {
		return policyDetailsServiceClient.getPolicyClassBenefitCasesDetails(policyNumber, classCode, benefitCode);
	}

	public ResponseEntity<BenefitSubCoverageResponseModel> apicallToGetBenefitSubCoverageDetails(String policyNumber,
			String classCode, String benefitCode, int pageSize, int pageNumber) throws FeignException {
		return policyDetailsServiceClient.getPolicyClassBenefitSubCoverageDetails(policyNumber, classCode, benefitCode,
				pageSize, pageNumber);
	}

	public ResponseEntity<PolicyEndorsementResponseModel> apicallToGetPolicyEndorsementsDetails(String policyNumber)
			throws FeignException {
		return policyDetailsServiceClient.getPolicyEndorsementsDetails(policyNumber);
	}

	private String getCurrentDateAsString() {
		LocalDate localDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return localDate.format(formatter);
	}
}
