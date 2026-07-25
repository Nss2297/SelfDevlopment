package com.waseel.policy.service.clienthandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.waseel.policy.clients.PbmPayerApisServiceClient;
import com.waseel.policy.enums.ConnectionIssueStatus;
import com.waseel.policy.model.client.MemberDetailsResponseModel;
import com.waseel.policy.model.client.PbmPayerApiResponseModel;
import com.waseel.policy.service.ConnectionFailedService;
import com.waseel.policy.service.mapper.MapperService;

import feign.FeignException;

@Service
public class PbmPayerApisRestHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(PbmPayerApisRestHandler.class);

	@Autowired
	private PbmPayerApisServiceClient pbmPayerApisServiceClient;

	@Autowired
	private MapperService mapperService;

	@Autowired
	private ConnectionFailedService connectionIssueService;

	public PbmPayerApiResponseModel getMemberDetails(Long idNumber, String providerPayerCode) {
		return fetchMemberDetailsFromPayer(idNumber, providerPayerCode);
	}

	private PbmPayerApiResponseModel fetchMemberDetailsFromPayer(Long idNumber, String providerPayerCode) {
		PbmPayerApiResponseModel pbmPayerApiResponseModel = null;
		try {
			LOGGER.info("Send member IdNumber: {} to Pbm Payer Apis Service", idNumber);
			ResponseEntity<MemberDetailsResponseModel> response = pbmPayerApisServiceClient.getMemberDetails(idNumber,
					null, null, providerPayerCode);
			pbmPayerApiResponseModel = new PbmPayerApiResponseModel(response.getBody(), null);
			return pbmPayerApiResponseModel;
		} catch (FeignException e) {
			LOGGER.error(
					"FeignException Has Been Thrown While Reading The Response From Pbm Payer Apis Service For IdNumber : {}, failed with status [{}]",
					idNumber, e.status(), e);
			if (e.status() == HttpStatus.BAD_REQUEST.value()
					|| e.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				pbmPayerApiResponseModel = new PbmPayerApiResponseModel(null,
						mapperService.mapPbmPayerApisService(e.contentUTF8()));
				return pbmPayerApiResponseModel;
			}
			if (e.status() == -1) {
				return connectionIssueService
						.pbmPayerApiResponseModelForConnectionFailure(ConnectionIssueStatus.ERROR_MESSAGE.value());
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception Has Been Thrown While Reading The Response From pbm-payer-apis-service For IdNumber :{} Error: {}",
					idNumber, e);
		}
		return null;
	}

}
