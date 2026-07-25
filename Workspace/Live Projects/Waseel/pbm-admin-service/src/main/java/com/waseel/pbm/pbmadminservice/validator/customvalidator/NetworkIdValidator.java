package com.waseel.pbm.pbmadminservice.validator.customvalidator;

import java.util.Optional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.security.core.context.SecurityContextHolder;

import com.waseel.pbm.pbmadminservice.persist.businessrules.ProviderNetwork;
import com.waseel.pbm.pbmadminservice.repository.businessrules.ProviderNetworkRepository;
import com.waseel.pbm.pbmadminservice.util.UserInfoUtil;
import com.waseel.pbm.pbmadminservice.validator.customannotation.IsValidNetworkId;

public class NetworkIdValidator implements ConstraintValidator<IsValidNetworkId, String> {
	private final ProviderNetworkRepository providerNetworkRepository;

	public NetworkIdValidator(ProviderNetworkRepository providerNetworkRepository) {
		this.providerNetworkRepository = providerNetworkRepository;
	}

	@Override
	public boolean isValid(String networkId, ConstraintValidatorContext context) {
		if (networkId == null) {
			return true;
		}
		if (!networkId.matches("\\d+")) {
			return true;
		}
		try {
			Long payerId = Long.valueOf(UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication()));
			Optional<ProviderNetwork> providerNetworkOpt = providerNetworkRepository
					.findByNetworkIdAndPayerIdAndIsDeleted(Long.parseLong(networkId), payerId, false);
			if (!providerNetworkOpt.isPresent()) {
				context.disableDefaultConstraintViolation();
				context.buildConstraintViolationWithTemplate("exclusionNetwork[" + networkId + "] not found or exists ")
						.addConstraintViolation();
				return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
}
