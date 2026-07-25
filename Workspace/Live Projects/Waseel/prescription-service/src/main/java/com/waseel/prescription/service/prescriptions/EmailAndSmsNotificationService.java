package com.waseel.prescription.service.prescriptions;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.waseel.prescription.model.authentication.JwtResponse;
import com.waseel.prescription.model.authentication.OneTimeAccessTokenRequest;
import com.waseel.prescription.model.enums.NotificationUrl;
import com.waseel.prescription.model.notification.EmailNotificationRequestModel;
import com.waseel.prescription.model.notification.EmailNotificationResponseModel;
import com.waseel.prescription.model.notification.SmsNotificationRequestModel;
import com.waseel.prescription.model.notification.SmsNotificationResponseModel;
import com.waseel.prescription.model.pbmpayerapis.MemberDemographicDataResponseModel;
import com.waseel.prescription.service.clienthandler.AuthenticationRestHandler;
import com.waseel.prescription.service.clienthandler.LinkShorteningService;
import com.waseel.prescription.service.clienthandler.PbmNotificationRestHandler;
import com.waseel.prescription.service.clienthandler.PbmPayerApisRestHandler;
import com.waseel.prescription.util.UserInfoUtil;

@Service
public class EmailAndSmsNotificationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailAndSmsNotificationService.class);

	@Autowired
	private PbmNotificationRestHandler pbmNotificationRestHandler;

	@Autowired
	private AuthenticationRestHandler authenticationRestHandler;

	@Autowired
	private PbmPayerApisRestHandler pbmPayerApisRestHandler;

	@Autowired
	private LinkShorteningService linkShorteningService;

	@Value("${authentication.username}")
	private String username;

	@Value("${authentication.password}")
	private String password;

	@Value("${patientPortal.url}")
	private String patientPortalUrl;

	public void notifyPatientByEmailAndSMS(String requestId, String idNumber, String ePrescriptionReferenceNumber,
			String requestType) {
		ResponseEntity<MemberDemographicDataResponseModel> resModel = pbmPayerApisRestHandler
				.sendRequestToGetMemberDemographicData(Long.valueOf(idNumber));
		if (resModel != null && resModel.getStatusCode() == HttpStatus.OK) {
			MemberDemographicDataResponseModel memberDemographicModel = resModel.getBody();
			if (memberDemographicModel != null) {
				String providerName = UserInfoUtil.getAccName(SecurityContextHolder.getContext().getAuthentication());
				String mobileNumber = memberDemographicModel.getMobileNumber().trim();
				String url = generateUrl(idNumber, ePrescriptionReferenceNumber);
				String memberName = memberDemographicModel.getMemberName();
				String message = generateSmsMessage(url, memberName, providerName, ePrescriptionReferenceNumber);
				sendSmsToPatient(new SmsNotificationRequestModel(mobileNumber, url, requestType, requestId,
						ePrescriptionReferenceNumber, message));
				List<String> emails = new ArrayList<>();
				emails.add(memberDemographicModel.getEmail());
				sendEmailNotificationToPatient(new EmailNotificationRequestModel(url, requestType, requestId,
						ePrescriptionReferenceNumber, emails, memberName, providerName));
			}
		}
	}

	private String generateUrl(String idNumber, String ePrescriptionReferenceNumber) {
		String url = "";
		Long timeToLive = 604800000l;
		List<String> ePrescriptionReferenceNumbers = new ArrayList<>();
		ePrescriptionReferenceNumbers.add(ePrescriptionReferenceNumber);
		JwtResponse jwtResponse = authenticationRestHandler.generatePatientAccessToken(idNumber,
				new OneTimeAccessTokenRequest(idNumber, ePrescriptionReferenceNumbers, BigInteger.valueOf(timeToLive)),
				generateAuthorizationHeader());
		if (null != jwtResponse && StringUtils.isNotBlank(jwtResponse.getAccessToken())) {
			url = patientPortalUrl + NotificationUrl.URL.value() + jwtResponse.getAccessToken();
			url = linkShorteningService.shortenLink(url, timeToLive / 1000);
		}
		return url;
	}

	private String generateSmsMessage(String url, String memberName, String providerName,
			String ePrescriptionReferenceNumber) {
		return "Hello " + memberName + ", we have received your prescription(Ref. No. " + ePrescriptionReferenceNumber
				+ ") that created/updated by " + providerName + ". Please click the link to view details. " + url;
	}

	private String generateAuthorizationHeader() {
		String credentials = username + ":" + password;
		byte[] encodedCredentials = Base64.encodeBase64(credentials.getBytes(StandardCharsets.US_ASCII));
		return NotificationUrl.BASIC.value() + new String(encodedCredentials);
	}

	private SmsNotificationResponseModel sendSmsToPatient(SmsNotificationRequestModel smsNotificationRequestModel) {
		return pbmNotificationRestHandler.sendNotificationToPatient(smsNotificationRequestModel);
	}

	private void sendEmailNotificationToPatient(EmailNotificationRequestModel emailNotificationRequestModel) {
		EmailNotificationResponseModel response = pbmNotificationRestHandler
				.sendEmailNotification(emailNotificationRequestModel);
		LOGGER.info("Return Response from Email Notification Api with status {} and email is {}", response.getStatus(),
				emailNotificationRequestModel.getEmails());
	}

}
