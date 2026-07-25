package com.waseel.prescription.service.clienthandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.waseel.prescription.clients.EligibilityServiceClient;
import com.waseel.prescription.model.eligibility.EligibilityResponseModel;
import com.waseel.prescription.model.enums.ConnectionIssueStatus;
import com.waseel.prescription.service.management.ConnectionFailedService;
import com.waseel.prescription.service.mapper.MapperService;
import com.waseel.prescription.service.validation.TechnicalValidationService;

import feign.FeignException;

@Service
public class EligibilityRestHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(EligibilityRestHandler.class);

	@Autowired
	private EligibilityServiceClient eligibilityServiceClient;

	@Autowired
	private MapperService mapperService;

	@Autowired
	private TechnicalValidationService technicalValidationService;

	@Autowired
	private ConnectionFailedService connectionIssueService;

	public EligibilityResponseModel handleMemberEligibility(String idNumber, String payerId, String providerId,
			String requestId) {
		return sendMemberDetailsToEligibilityService(idNumber, payerId, providerId, requestId);
	}

	public EligibilityResponseModel sendMemberDetailsToEligibilityService(String idNumber, String payerId,
			String providerId, String requestId) {
		try {
			LOGGER.info("Send member IdNumber: {} to Eligibility Service", idNumber);
			ResponseEntity<EligibilityResponseModel> response = eligibilityServiceClient
					.checkMemberEligibility(idNumber, payerId, providerId, requestId);
			return response.getBody();
		} catch (FeignException e) {
			LOGGER.error(
					"FeignException Has Been Thrown While Reading The Response From Eligibility service For IdNumber : {}, failed with status [{}]",
					idNumber, e.status(), e);
			if (e.status() == HttpStatus.BAD_REQUEST.value()
					|| e.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return mapperService.mapEligibilityResponse(e.contentUTF8());
			}
			if (e.status() == -1) {
				return connectionIssueService.eligibilityResponseForConnectionFailure(requestId,
						ConnectionIssueStatus.ERROR_MESSAGE.value());
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception Has Been Thrown While Reading The Response From Eligibility service For IdNumber :{} Error: {}",
					idNumber, e);
		}
		return null;
	}

}
