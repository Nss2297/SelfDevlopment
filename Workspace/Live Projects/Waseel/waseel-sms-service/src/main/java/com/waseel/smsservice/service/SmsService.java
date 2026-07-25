package com.waseel.smsservice.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.waseel.smsservice.client.SmsClient;
import com.waseel.smsservice.exception.SMSException;
import com.waseel.smsservice.model.Data;
import com.waseel.smsservice.model.SmsRequestModel;
import com.waseel.smsservice.model.UnifonicRequestModel;
import com.waseel.smsservice.model.UnifonicResponseModel;
import com.waseel.smsservice.model.sms.SmsResponseModel;

import feign.FeignException;

@Service
public class SmsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(SmsService.class);

	@Autowired
	private SmsClient smsClient;
	@Autowired
	InvalidResponseService invalidResponseService;

	@Autowired
	Environment environment;

	@Value("#{${allowed-phone-numbers-for-development}}")
	private List<String> allowedPhoneNumbersInDev;

	public UnifonicResponseModel sendSMS(SmsRequestModel smsRequestModel) throws Exception, SMSException {
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
				SmsResponseModel smsResponseModel = smsClient.sendSmsNotification(
						"c681c821ed5f5df56df75092d11171f240436f44", unifonicRequest.getRecipient(),
						unifonicRequest.getBody(), "sms");
				return mapToUnifonicResponse(smsResponseModel);
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

	private UnifonicResponseModel mapToUnifonicResponse(SmsResponseModel smsResponseModel) throws SMSException {
		if (smsResponseModel != null) {
			UnifonicResponseModel unifonicResponseModel = new UnifonicResponseModel();
			unifonicResponseModel.setSuccess(smsResponseModel.getSuccess());
			if (smsResponseModel.getData() != null && smsResponseModel.getData().getMessages() != null
					&& !smsResponseModel.getData().getMessages().isEmpty()) {
				Data data = new Data();
				data.setRecipient(smsResponseModel.getData().getMessages().get(0).getNumber());
				if (smsResponseModel.getData().getMessages().get(0).getId() != null)
					data.setMessageID(smsResponseModel.getData().getMessages().get(0).getId().longValue());
				data.setStatus(smsResponseModel.getData().getMessages().get(0).getStatus());
				data.setTimeCreated(smsResponseModel.getData().getMessages().get(0).getSentDate());
				unifonicResponseModel.setMessage(smsResponseModel.getData().getMessages().get(0).getMessage());
				unifonicResponseModel.setErrorCode(smsResponseModel.getData().getMessages().get(0).getErrorCode());
				unifonicResponseModel.setData(data);
			}
			unifonicResponseModel.setSuccess(smsResponseModel.getSuccess());
			return unifonicResponseModel;
		}
		throw new SMSException(invalidResponseService.populateSmsNoResponse());
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
