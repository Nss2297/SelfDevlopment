package com.waseel.pbmnotificationservice.service.clienthandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.waseel.pbmnotificationservice.clients.WaseelSmsServiceClient;
import com.waseel.pbmnotificationservice.model.unifonic.UnifonicReponseModel;
import com.waseel.pbmnotificationservice.model.unifonic.UnifonicRequestModel;
import com.waseel.pbmnotificationservice.service.ConnectionFailedService;
import com.waseel.pbmnotificationservice.service.MapperService;

import feign.FeignException;

@Service
public class WaseelSmsServiceRestHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(WaseelSmsServiceRestHandler.class);

	@Autowired
	private WaseelSmsServiceClient waseelSmsServiceClient;

	@Autowired
	private MapperService mapperService;

	@Autowired
	private ConnectionFailedService connectionIssueService;

	public UnifonicReponseModel sendMemberDetailsToWaseelSmsService(String requestId,
			UnifonicRequestModel unifonicRequestModel) {
		try {
			LOGGER.info("Send member requestId: {} to Waseel Sms Service", requestId);
			ResponseEntity<UnifonicReponseModel> responseEntity = waseelSmsServiceClient
					.sendMemberDetailsToUnifonic(unifonicRequestModel);
			return responseEntity.getBody();
		} catch (FeignException e) {
			LOGGER.error(
					"FeignException Has Been Thrown While Reading The Response From Waseel Sms service For requestId : {}, failed with status [{}]",
					requestId, e.status(), e);
			if (e.status() == HttpStatus.BAD_REQUEST.value()
					|| e.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return mapperService.mapUnifonicResponse(e.contentUTF8());
			}
			if (e.status() == -1) {
				return connectionIssueService.smsServiceReponseForConnectionFailure();
			}
		} catch (Exception e) {
			LOGGER.error(
					"Exception Has Been Thrown While Reading The Response From Waseel Sms service For requestId :{} Error: {}",
					requestId, e);
		}
		return null;
	}

}
