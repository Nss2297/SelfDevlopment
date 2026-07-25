package com.waseel.eligibility.client.settings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.waseel.eligibility.model.PortalSettings;

@Service
public class SettingsServiceClient {

	private static final Logger LOGGER = LoggerFactory.getLogger(SettingsServiceClient.class);

	@Value("${settings-service.url}")
	private String SETTINGS_SERVICE_URL;

	public String SETTINGS_SERVICE_URI_FOR_USER_VALIDATION = "/providers/{providerid}/portal-user";

	private RestTemplate restTemplate;

	public SettingsServiceClient(RestTemplateBuilder restTemplateBuilder) {
		restTemplate = restTemplateBuilder.build();
	}

	public PortalSettings getPortalUser(String providerid) {
		PortalSettings portalUser = null;
		ResponseEntity<PortalSettings> settingsServiceresponse = null;
		try {
			UriComponentsBuilder builder = UriComponentsBuilder
					.fromHttpUrl(SETTINGS_SERVICE_URL + SETTINGS_SERVICE_URI_FOR_USER_VALIDATION);

			String uri = builder.buildAndExpand(providerid).toUriString();
			LOGGER.info("Retrieving Portal User from Settings Service : " + uri);
			settingsServiceresponse = restTemplate.getForEntity(uri, PortalSettings.class);
			if (settingsServiceresponse.getStatusCode() == HttpStatus.OK) {
				portalUser = settingsServiceresponse.getBody();
				LOGGER.info("User " + portalUser.getUsername() + " for provider " + portalUser.getProviderId()
						+ " fetched from Settings ");
			}

		} catch (HttpClientErrorException clientException) {
			LOGGER.info("User settings not found for provider ".concat(providerid));
			LOGGER.error("User not found ", clientException);
		} catch (HttpServerErrorException serverException) {
			LOGGER.info(serverException.getMessage());
			LOGGER.error("An error has been occurred while getting user settings for provider ".concat(providerid),
					serverException);

		} catch (Exception E) {
			LOGGER.info(E.getMessage());
			LOGGER.error("An error has been occurred while getting user settings for provider ".concat(providerid), E);
		}
		return portalUser;
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

}
