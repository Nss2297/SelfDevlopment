package com.waseel.pbm.authentication.configuration;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.waseel.pbm.authentication.model.pbmbusinessrules.entity.PayerApiKeyInformation;
import com.waseel.pbm.authentication.repository.pbmbusinessrules.PayerApiKeyInformationRepository;
import com.waseel.pbm.authentication.service.ApiKeysService;

@Component
@Profile("!test")
public class ApiKeysInitializer {

	@Autowired
	private ApiKeysService apiKeysService;

	@Autowired
	private PayerApiKeyInformationRepository payerApiKeyInformationRepository;

	@PostConstruct
	public void initializeApiKeys() {
		Stream<PayerApiKeyInformation> payers = StreamSupport
				.stream(payerApiKeyInformationRepository.findAll().spliterator(), false);
		payers.filter(payer -> payer.getApiKey() == null)
				.forEach(payer -> apiKeysService.generateApiKeyForPayer(payer));
	}
}
