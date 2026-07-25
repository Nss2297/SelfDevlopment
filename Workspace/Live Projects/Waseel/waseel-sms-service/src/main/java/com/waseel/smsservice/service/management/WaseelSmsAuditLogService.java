package com.waseel.smsservice.service.management;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.waseel.smsservice.enums.UrlDetails;
import com.waseel.smsservice.model.SmsRequestModel;
import com.waseel.smsservice.model.UnifonicResponseModel;
import com.waseel.smsservice.persist.mongodb.SmsAuditTrail;
import com.waseel.smsservice.repository.mongodb.SmsAuditTrailRepository;

@Service
public class WaseelSmsAuditLogService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WaseelSmsAuditLogService.class);

	@Autowired
	private SmsAuditTrailRepository unifonicAuditTrailRepository;

	@Autowired
	private MapperService mapperService;

	public void manageWaseelSmsAuditlogFromResponse(ContentCachingRequestWrapper requestWrapper,
			ContentCachingResponseWrapper responseWrapper) {
		try {
			if (requestWrapper.getRequestURI().endsWith(UrlDetails.WASEEL_SMS_URL.value())) {
				SmsRequestModel smsRequestModel = mapperService.mapSmsRequestRequestModel(requestWrapper);
				UnifonicResponseModel unifonicResponseModel = mapperService.mapUnifonicResponseModel(responseWrapper);
				if (null != smsRequestModel && null != unifonicResponseModel) {
					saveAuditLogInMongoDb(smsRequestModel, unifonicResponseModel);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("WaseelSmsAuditLogService exception:-", e);
		}
	}

	public void saveAuditLogInMongoDb(SmsRequestModel smsRequestModel, UnifonicResponseModel unifonicResponseModel) {
		try {
			CompletableFuture.runAsync(() -> unifonicAuditTrailRepository
					.save(setUnifonicAuditData(smsRequestModel, unifonicResponseModel)));
		} catch (Exception e) {
			LOGGER.error("", e);
		}
	}

	private SmsAuditTrail setUnifonicAuditData(SmsRequestModel smsRequestModel,
			UnifonicResponseModel unifonicResponseModel) {
		LOGGER.info("Set audit log data for Mobile No: {}", smsRequestModel.getMemberMobileNo());
		SmsAuditTrail audit = new SmsAuditTrail();
		audit.setSmsRequestModel(smsRequestModel);
		audit.setUnifonicResponseModel(unifonicResponseModel);
		audit.setDateTime(new Date());
		return audit;
	}

}
