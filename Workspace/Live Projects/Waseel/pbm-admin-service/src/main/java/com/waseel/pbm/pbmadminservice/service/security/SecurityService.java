package com.waseel.pbm.pbmadminservice.service.security;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.waseel.pbm.pbmadminservice.enums.management.SecurityPrivileges;

@Service
public class SecurityService {

	public static final String SFDA_MANAGEMENT_AUTHORITY = ";SFDA_MANAGEMENT";
	public static final String PBM_ADMIN_AUTHORITY = ";PBM_ADMIN";

	public boolean hasSFDAAccessForValidResponse(Authentication authentication, String payerId) {
		boolean isAuthorized = false;
		if (!StringUtils.isBlank(payerId)) {
			isAuthorized = authentication.getAuthorities().stream()
					.anyMatch(auth -> auth.getAuthority().equals(payerId + SFDA_MANAGEMENT_AUTHORITY)
							|| auth.getAuthority().equals(payerId + PBM_ADMIN_AUTHORITY));
		}
		return isAuthorized;
	}

	public boolean hasSFDAAccessForInvalidFailedResponse(Authentication authentication) {
		return authentication.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().endsWith(SFDA_MANAGEMENT_AUTHORITY)
						|| auth.getAuthority().endsWith(PBM_ADMIN_AUTHORITY));
	}

	public boolean hasAccessForDrugFormulary(Authentication authentication) {
		return authentication.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().equals(101 + ";DRUG_FORMULARY_MANAGEMENT")
						|| auth.getAuthority().equals(101 + ";BUSINESS_RULE_ADMINISTRATION"));
	}

	public boolean hasAccessForCustomizationUpload(Authentication authentication) {
		return authentication.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().equals(101 + ";CUSTOMIZATION_UPLOAD")
						|| auth.getAuthority().equals(101 + PBM_ADMIN_AUTHORITY));
	}

	public boolean hasAccessForDrugToDiagnosisCustomization(Authentication authentication) {
		return authentication.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().equals(101 + ";DRUG_TO_DIAGNOSIS_CUSTOMIZATION")
						|| auth.getAuthority().equals(101 + PBM_ADMIN_AUTHORITY));
	}

	public boolean hasAccessForDrugToGenderCustomization(Authentication authentication) {
		return authentication.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().equals(101 + PBM_ADMIN_AUTHORITY));
	}

	public boolean hasAccessForExclusionManagement(Authentication authentication) {
		return authentication.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().equals(101 + ";EXCLUSION_MANAGEMENT"));
	}

	public boolean hasAccessForMemberManagement(Authentication authentication) {
		return authentication.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().equals(SecurityPrivileges.MEMBER_MANAGEMENT.value())
						|| auth.getAuthority().equals(SecurityPrivileges.BUSINESS_RULE_ADMINISTRATION.value()));
	}
}
