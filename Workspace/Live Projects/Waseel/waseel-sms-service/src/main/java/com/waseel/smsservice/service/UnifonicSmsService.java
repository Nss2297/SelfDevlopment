package com.waseel.smsservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.waseel.smsservice.client.UnifonicClient;
import com.waseel.smsservice.exception.SMSException;
import com.waseel.smsservice.model.SmsRequestModel;
import com.waseel.smsservice.model.UnifonicRequestModel;
import com.waseel.smsservice.model.UnifonicResponseModel;
import com.waseel.smsservice.persist.WaseelSmsConfiguration;
import com.waseel.smsservice.repository.hira.WaseelSmsConfigurationRepository;

import feign.FeignException;

@Service
public class UnifonicSmsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SmsService.class);

	@Autowired
	private UnifonicClient unifonicSmsClient;
	@Autowired
	InvalidResponseService invalidResponseService;

	@Autowired
	Environment environment;

	@Value("#{${allowed-phone-numbers-for-development}}")
	private List<String> allowedPhoneNumbersInDev;

	@Autowired
	private WaseelSmsConfigurationRepository waseelSmsConfigurationRepository;

	public UnifonicResponseModel sendUnifonicSMS(SmsRequestModel smsRequestModel) throws Exception, SMSException {
		UnifonicRequestModel unifonicRequest = getUnifonicRequest(smsRequestModel);
		if (Arrays.asList(environment.getActiveProfiles()).contains("qa")
				|| Arrays.asList(environment.getActiveProfiles()).contains("local")) {
			if (!allowedPhoneNumbersInDev.contains(unifonicRequest.getRecipient())) {
				throw new SMSException(
						invalidResponseService.populateSmsNotAllowedPhoneNumber(unifonicRequest.getRecipient()));
			}
		}
		if (unifonicRequest != null) {
			try {
				Optional<WaseelSmsConfiguration> waseelSmsConfigurationOpt = waseelSmsConfigurationRepository
						.findByUnifonicAppIdAndIsEnabled(unifonicRequest.getAppSid(), "1");
				if (waseelSmsConfigurationOpt.isPresent()) {
					return unifonicSmsClient.sendSmsNotification(unifonicRequest).getBody();
				} else {
					LOGGER.error("Unconfigured or disabled App.");
					return invalidResponseService.populateUnconfiguredAppResponse();
				}
			} catch (FeignException e) {
				LOGGER.error("FeignException Has Been Thrown while sending the sms.");
				if (e.status() == HttpStatus.BAD_REQUEST.value()
						|| e.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
					throw new SMSException(invalidResponseService.populateSmsInvalidFailedResponse(e));
				}
			} catch (Exception ex) {
				LOGGER.error("Exception Has Been Thrown While sending the sms.");
				throw new Exception(ex);
			}
		}
		throw new SMSException(invalidResponseService.populateSmsInvalidRequest());
	}

	private UnifonicRequestModel getUnifonicRequest(SmsRequestModel smsRequestModel) {
		UnifonicRequestModel unifonicRequest = null;
		if (smsRequestModel != null) {
			unifonicRequest = new UnifonicRequestModel();
			unifonicRequest.setAppSid(smsRequestModel.getAppSid());
			unifonicRequest.setBody(smsRequestModel.getMessage());
			if (smsRequestModel.getMemberMobileNo() != null)
				unifonicRequest.setRecipient(
						smsRequestModel.getMemberMobileNo().trim().replaceAll("^[/+]", "").replaceFirst("00", ""));
			unifonicRequest.setSenderID(smsRequestModel.getSenderID());
		}
		return unifonicRequest;
	}

}
