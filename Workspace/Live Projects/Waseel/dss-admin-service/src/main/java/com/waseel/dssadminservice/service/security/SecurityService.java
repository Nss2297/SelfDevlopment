package com.waseel.dssadminservice.service.security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.waseel.dssadminservice.enums.SecurityPrivileges;

@Service
public class SecurityService {

	public static final String PBM_ADMIN_AUTHORITY = ";PBM_ADMIN";

	public boolean hasSFDAManagementAccessForValidResponse(Authentication authentication) {
		return authentication.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().equals(SecurityPrivileges.PBM_ADMIN.value())
						|| auth.getAuthority().equals(SecurityPrivileges.SFDA_MANAGEMENT.value()));
	}

	public boolean hasAccessForDrugToGenderCustomization(Authentication authentication) {
		return authentication.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().equals(101 + PBM_ADMIN_AUTHORITY)
						|| auth.getAuthority().equals(SecurityPrivileges.DRUG_TO_GENDER_CUSTOMIZATION.value()));
	}

	public boolean hasAccessForUploadGenderCustomization(Authentication authentication) {
		return authentication.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().equals(SecurityPrivileges.PBM_ADMIN.value())
						|| auth.getAuthority().equals(SecurityPrivileges.GENDER_CUSTOMIZATION_UPLOAD.value()));
	}

	public boolean hasAccessForDrugToAgeCustomization(Authentication authentication) {
		return authentication.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().equals(101 + PBM_ADMIN_AUTHORITY)
						|| auth.getAuthority().equals(SecurityPrivileges.DRUG_TO_AGE_CUSTOMIZATION.value()));
	}

	public boolean hasAccessForUploadAgeCustomization(Authentication authentication) {
		return authentication.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().equals(SecurityPrivileges.PBM_ADMIN.value())
						|| auth.getAuthority().equals(SecurityPrivileges.AGE_CUSTOMIZATION_UPLOAD.value()));
	}

	public boolean hasAccessForDrugToDrugCustomization(Authentication authentication) {
		return authentication.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().equals(101 + PBM_ADMIN_AUTHORITY)
						|| auth.getAuthority().equals(SecurityPrivileges.DRUG_TO_DRUG_CUSTOMIZATION.value()));
	}

	public boolean hasAccessForUploadDrugCustomization(Authentication authentication) {
		return authentication.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().equals(SecurityPrivileges.PBM_ADMIN.value())
						|| auth.getAuthority().equals(SecurityPrivileges.DRUG_CUSTOMIZATION_UPLOAD.value()));
	}

	public boolean hasAccessForDuplicateTherapyCustomization(Authentication authentication) {
		return authentication.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().equals(101 + PBM_ADMIN_AUTHORITY)
						|| auth.getAuthority().equals(SecurityPrivileges.DUPLICATE_THERAPY_CUSTOMIZATION.value()));
	}

	public boolean hasAccessForUploadDuplicateTherapyCustomization(Authentication authentication) {
		return authentication.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().equals(SecurityPrivileges.PBM_ADMIN.value())
						|| auth.getAuthority().equals(SecurityPrivileges.DUPLICATE_THERAPY_CUSTOMIZATION_UPLOAD.value()));
	}
}
