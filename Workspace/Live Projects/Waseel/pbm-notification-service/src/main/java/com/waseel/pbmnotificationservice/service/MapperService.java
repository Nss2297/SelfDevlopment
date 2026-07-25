package com.waseel.pbmnotificationservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waseel.pbmnotificationservice.model.email.EmailNotificationRequestModel;
import com.waseel.pbmnotificationservice.model.email.EmailNotificationResponseModel;
import com.waseel.pbmnotificationservice.model.eprescription.notification.NotificationRequestModel;
import com.waseel.pbmnotificationservice.model.sms.SmsNotificationRequestModel;
import com.waseel.pbmnotificationservice.model.sms.SmsNotificationResponseModel;
import com.waseel.pbmnotificationservice.model.sso.JwtResponse;
import com.waseel.pbmnotificationservice.model.unifonic.UnifonicReponseModel;

@Service
public class MapperService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MapperService.class);

	public NotificationRequestModel mapNotificationRequestModel(ContentCachingRequestWrapper requestWrapper) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(requestWrapper.getContentAsByteArray(), NotificationRequestModel.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public EmailNotificationResponseModel mapEmailNotificationResponseModel(String response) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(response, EmailNotificationResponseModel.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public UnifonicReponseModel mapUnifonicResponse(String response) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(response, UnifonicReponseModel.class);
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return null;
	}

	public EmailNotificationResponseModel mapEmailNotificationResponseModel(
			ContentCachingResponseWrapper requestWrapper) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(new String(requestWrapper.getContentAsByteArray()),
					EmailNotificationResponseModel.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public JwtResponse mapJwtResponse(String response) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(response, JwtResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("", e);
		}
		return null;
	}

	public EmailNotificationRequestModel mapEmailNotificationRequestModel(ContentCachingRequestWrapper requestWrapper) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(new String(requestWrapper.getContentAsByteArray()),
					EmailNotificationRequestModel.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public SmsNotificationRequestModel mapSmsNotificationRequestModel(ContentCachingRequestWrapper requestWrapper) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(new String(requestWrapper.getContentAsByteArray()),
					SmsNotificationRequestModel.class);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("", e);
		}
		return null;
	}

	public SmsNotificationResponseModel mapSmsNotificationResponseModel(ContentCachingResponseWrapper requestWrapper) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(new String(requestWrapper.getContentAsByteArray()),
					SmsNotificationResponseModel.class);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("", e);
		}
		return null;
	}
}
