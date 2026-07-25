package com.waseel.pbm.payercustomizationservice.service.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.waseel.pbm.payercustomizationservice.enums.CustomizationRequestsAuthority;

@Service
public class SecurityService {

	public boolean hasAccessToAddCustomizationRequest(Authentication authentication) {
		return authentication.getAuthorities().stream().anyMatch(
				auth -> auth.getAuthority().equals(CustomizationRequestsAuthority.CUSTOMIZATION_REQUEST.value())
						|| auth.getAuthority().equals(CustomizationRequestsAuthority.MEDICAL_CUSTOMIZATION.value())
						|| auth.getAuthority().equals(CustomizationRequestsAuthority.BUSINESS_CUSTOMIZATION.value()));
	}

	public boolean hasAccessToUpdateCustomizationRequest(Authentication authentication) {
		return authentication.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().equals(CustomizationRequestsAuthority.PBM_ADMIN.value()));
	}

	public boolean hasAccessToDeleteCustomizationRequest(Authentication authentication) {
		return authentication.getAuthorities().stream().anyMatch(
				auth -> auth.getAuthority().equals(CustomizationRequestsAuthority.CUSTOMIZATION_REQUEST.value()));
	}

	public boolean hasAccessToGetCustomizationRequest(Authentication authentication) {
		return authentication.getAuthorities().stream().anyMatch(
				auth -> auth.getAuthority().equals(CustomizationRequestsAuthority.CUSTOMIZATION_REQUEST.value())
						|| auth.getAuthority().equals(CustomizationRequestsAuthority.MEDICAL_CUSTOMIZATION.value())
						|| auth.getAuthority().equals(CustomizationRequestsAuthority.BUSINESS_CUSTOMIZATION.value())
						|| auth.getAuthority().equals(CustomizationRequestsAuthority.PBM_ADMIN.value()));
	}
}
