package com.waseel.pbm.pbmadminservice.validator.customvalidator;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.waseel.pbm.pbmadminservice.persist.mdss.PayerConfig;
import com.waseel.pbm.pbmadminservice.repository.mdss.PayerConfigRepository;
import com.waseel.pbm.pbmadminservice.validator.customannotation.IsValidPayerId;

public class IsValidPayerIdValidator implements ConstraintValidator<IsValidPayerId, String> {

	@Autowired
	private PayerConfigRepository payerConfigRepository;

	@Override
	public boolean isValid(String payerId, ConstraintValidatorContext arg1) {
		if (payerId == null) {
			return false;
		}
		Optional<PayerConfig> payerConfig = payerConfigRepository.findByIdPayerIdAndIdIsEnabled(payerId, '1');
		return payerConfig.isPresent();
	}
}
