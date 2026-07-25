package com.waseel.prescription.service.prescriptions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.waseel.prescription.model.common.PayerConfigModel;
import com.waseel.prescription.persist.businessrules.PayerConfiguration;
import com.waseel.prescription.persist.hira.AccountToAccountAssociation;
import com.waseel.prescription.repository.hira.AccountToAccountAssociationRepository;
import com.waseel.prescription.specification.PayerConfigSpecification;
import com.waseel.prescription.util.UserInfoUtil;

@Service
public class PayerConfigService {

	private final Logger log = LoggerFactory.getLogger(PayerConfigService.class);

	@Autowired
	private PayerConfigSpecification payerConfigSpecification;

	@Autowired
	private AccountToAccountAssociationRepository accountToAccountAssociationRepository;

	public List<PayerConfigModel> getAllPayerDetails(String payer) {
		log.info("payer is : {}", payer);
		List<PayerConfigModel> payerDetails = new ArrayList<>();
		// Source =ProviderId , destination = Payerid 
		Map<String, String> map = new HashMap<>();
		String providerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		List<BigDecimal> payerIds = new ArrayList<>();
		List<PayerConfiguration> payerConfig = payerConfigSpecification.findByPayerIdAndPayerName(payer);
		payerConfig.forEach(payerDetail -> {
			payerIds.add(BigDecimal.valueOf(Double.valueOf(payerDetail.getPayerId())));
			map.put(payerDetail.getPayerId(), payerDetail.getPayerName());
		});
		List<AccountToAccountAssociation> accToaccAssociation = accountToAccountAssociationRepository
				.findByIdSourceAndIdDestinationsAndIsEnabled(BigDecimal.valueOf(Double.valueOf(providerId)), payerIds,
						true);
		accToaccAssociation.forEach(acc -> {
			if (map.containsKey(acc.getId().getDestination().toString())) {
				payerDetails.add(new PayerConfigModel(acc.getId().getDestination().toString(),
						map.get(acc.getId().getDestination().toString())));
			}
		});
		return payerDetails;
	}
}
