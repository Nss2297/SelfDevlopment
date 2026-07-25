package com.waseel.pbm.authentication.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.waseel.pbm.authentication.configuration.JwtTokenUtil;
import com.waseel.pbm.authentication.model.pbmbusinessrules.entity.PayerApiKeyInformation;
import com.waseel.pbm.authentication.model.portal.enity.SwitchAccount;
import com.waseel.pbm.authentication.repository.SwitchAccountRepository;
import com.waseel.pbm.authentication.repository.pbmbusinessrules.PayerApiKeyInformationRepository;

@Service
public class ApiKeysService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private JwtTokenUtil jwtUtil;

	@Autowired
	private SwitchAccountRepository switchAccountRepository;

	@Autowired
	private PayerApiKeyInformationRepository payerApiKeyInformationRepository;

	@Autowired
	private Environment environment;

	public PayerApiKeyInformation generateApiKeyForPayer(PayerApiKeyInformation payer) {
		Optional<SwitchAccount> accountResult = switchAccountRepository
				.findBySwitchAccountIdAndIsEnabledAndCategory(new BigDecimal(payer.getPayerId()), "1", "PAYER");
		if (accountResult.isPresent()) {
			SwitchAccount account = accountResult.get();
			payer.setApiKey(jwtUtil.generateApiKey(account));
			if (Arrays.asList(environment.getActiveProfiles()).contains("dev")) {
				log.info("API key generated for payer {} is: {}", payer.getPayerId(), payer.getApiKey());
				return payer;
			}
			return payerApiKeyInformationRepository.save(payer);
		}
		return null;
	}
	
	public List<GrantedAuthority> addAuthoritiesByPayerId(String payerId) {
		List<PayerApiKeyInformation> payerList = payerApiKeyInformationRepository.findByPayerId(payerId);
		List<GrantedAuthority> authorities = new ArrayList<>();
		payerList.stream().filter(payerInfo -> !StringUtils.isBlank(payerInfo.getStandardTransactionName()))
				.forEach(payerInfo -> authorities
						.add(new SimpleGrantedAuthority("101;" + payerInfo.getStandardTransactionName())));
		return authorities;
	}
}
