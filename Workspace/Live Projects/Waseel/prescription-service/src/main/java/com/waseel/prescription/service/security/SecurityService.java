package com.waseel.prescription.service.security;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

	private static final String PBM_PRESCRIPTION = ";PBM_PRESCRIPTION";
	private static final String PBM_PRESCRIPTION_INQUIRY = ";PRESCRIPTION_INQUIRY";
	private static final String PBM_PRESCRIPTION_SUBMISSION = ";PRESCRIPTION_SUBMISSION";
	private static final String PBM_FOLLOW_UP_PRESCRIPTION = ";FOLLOW_UP_PRESCRIPTION";
	private static final String PBM_DETAIL_INQUIRY = ";DETAIL_INQUIRY";
	private static final String PBM_VIEW_PRESCRIPTION = "101;VIEW_PRESCRIPTION";

	public boolean hasNewOrFollowUpAccessForValidResponse(Authentication authentication, String payerId,
			String ePrescriptionRefNum) {
		boolean isAuthorized = false;
		if (!StringUtils.isBlank(payerId) && StringUtils.isBlank(ePrescriptionRefNum)) {
			isAuthorized = authentication.getAuthorities().stream()
					.anyMatch(auth -> auth.getAuthority().equals(payerId + PBM_PRESCRIPTION)
							|| auth.getAuthority().equals(payerId + PBM_PRESCRIPTION_SUBMISSION)
							|| auth.getAuthority().equals(payerId + ";NEW_PRESCRIPTION"));
		}

		if (!StringUtils.isBlank(payerId) && !StringUtils.isBlank(ePrescriptionRefNum)) {
			isAuthorized = authentication.getAuthorities().stream()
					.anyMatch(auth -> auth.getAuthority().equals(payerId + PBM_PRESCRIPTION)
							|| auth.getAuthority().equals(payerId + PBM_PRESCRIPTION_SUBMISSION)
							|| auth.getAuthority().equals(payerId + PBM_FOLLOW_UP_PRESCRIPTION));
		}
		return isAuthorized;
	}

	public boolean hasOverrideMedicationAccess(Authentication authentication) {
		return authentication.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().contains(";OVERRIDE_MEDICATION"));
	}

	public boolean hasCancellationAccessForValidResponse(Authentication authentication, String payerId) {
		if (!StringUtils.isBlank(payerId)) {
			return authentication.getAuthorities().stream()
					.anyMatch(auth -> auth.getAuthority().equals(payerId + PBM_PRESCRIPTION)
							|| auth.getAuthority().equals(payerId + PBM_PRESCRIPTION_SUBMISSION)
							|| auth.getAuthority().equals(payerId + ";PRESCRIPTION_CANCELLATION"));
		}
		return false;
	}

	public boolean hasDispenseAccessForValidResponse(Authentication authentication, String payerId) {
		if (!StringUtils.isBlank(payerId)) {
			return authentication.getAuthorities().stream()
					.anyMatch(auth -> auth.getAuthority().equals(payerId + PBM_PRESCRIPTION)
							|| auth.getAuthority().equals(payerId + ";PRESCRIPTION_DISPENSE"));
		}
		return false;
	}

	public boolean hasDetailInquiryApiAccess(Authentication authentication, String payerId) {
		if (!StringUtils.isBlank(payerId)) {
			return authentication.getAuthorities().stream()
					.anyMatch(auth -> auth.getAuthority().contains(payerId + PBM_PRESCRIPTION)
							|| auth.getAuthority().equals(payerId + PBM_PRESCRIPTION_INQUIRY)
							|| auth.getAuthority().equals(payerId + PBM_DETAIL_INQUIRY));
		}
		return false;
	}

	public boolean hasSummaryInquiryApiAccess(Authentication authentication, String payerId) {
		if (!StringUtils.isBlank(payerId)) {
			return authentication.getAuthorities().stream()
					.anyMatch(auth -> auth.getAuthority().contains(payerId + PBM_PRESCRIPTION)
							|| auth.getAuthority().equals(payerId + PBM_PRESCRIPTION_INQUIRY)
							|| auth.getAuthority().equals(payerId + ";SUMMARY_INQUIRY"));
		}
		return false;
	}

	public boolean hasAccessToFetchDrugDiagnosisPayerMemberPhysicianDetails(Authentication authentication,
			String payerId, String ePrescriptionReferenceNumber) {
		List<String> listOfReferenceNumber = getPatientReferenceNumbersFromToken(authentication);
		return !StringUtils.isBlank(payerId) && authentication.getAuthorities().stream().anyMatch(auth -> auth
				.getAuthority().equals(payerId + PBM_PRESCRIPTION)
				|| auth.getAuthority().equals(payerId + PBM_PRESCRIPTION_SUBMISSION)
				|| auth.getAuthority().equals(payerId + PBM_FOLLOW_UP_PRESCRIPTION)
				|| auth.getAuthority().equals(payerId + PBM_PRESCRIPTION_INQUIRY)
				|| auth.getAuthority().equals(payerId + PBM_DETAIL_INQUIRY)
				|| auth.getAuthority().equals(PBM_VIEW_PRESCRIPTION)
				|| (listOfReferenceNumber != null && listOfReferenceNumber.contains(ePrescriptionReferenceNumber)));
	}

	public boolean hasAccessToFetchValidation(Authentication authentication, String payerId) {
		return !StringUtils.isBlank(payerId) && authentication.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().equals(payerId + PBM_PRESCRIPTION)
						|| auth.getAuthority().equals(payerId + PBM_PRESCRIPTION_SUBMISSION)
						|| auth.getAuthority().equals(payerId + PBM_FOLLOW_UP_PRESCRIPTION)
						|| auth.getAuthority().equals(payerId + PBM_PRESCRIPTION_INQUIRY)
						|| auth.getAuthority().equals(payerId + PBM_DETAIL_INQUIRY)
						|| auth.getAuthority().equals(PBM_VIEW_PRESCRIPTION));
	}

	public boolean hasPayerViewPrescriptionAccess(Authentication authentication) {
		return authentication.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().equals(101 + ";VIEW_PRESCRIPTION"));
	}

	public boolean hasPayerEditPrescriptionDecisionAccess(Authentication authentication) {
		return authentication.getAuthorities().stream()
				.anyMatch(auth -> auth.getAuthority().equals(101 + ";EDIT_PRESCRIPTION_DECISION"));
	}

	private List<String> getPatientReferenceNumbersFromToken(Authentication authentication) {
		return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.filter(authority -> authority.startsWith("prescription-service|"))
				.map(authority -> authority.substring("prescription-service|".length())).collect(Collectors.toList());
	}

}
