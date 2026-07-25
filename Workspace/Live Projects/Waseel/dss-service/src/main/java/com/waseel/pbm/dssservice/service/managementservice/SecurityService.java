package com.waseel.pbm.dssservice.service.managementservice;

import org.springframework.stereotype.Service;

@Service
public class SecurityService {

//	public boolean hasDssNewAccess(Authentication authentication) {
//		String providerId = "101";
//		String payerId = "101";
//		boolean isAuthorized = false;
//		if (providerId != null && providerId != "") {
//
//			isAuthorized = authentication.getAuthorities().stream()
//					.anyMatch(auth -> auth.getAuthority().contains(providerId + "|50.0|")
//							|| auth.getAuthority().contains(providerId + "|50.1|")
//							|| auth.getAuthority().contains(providerId + "|50.11|"));
//			System.out.println("auth New: " + isAuthorized);
//		}
//		return isAuthorized;
//	}
//
//	public boolean hasDssFollowUpAccess(Authentication authentication) {
//		String providerId = "101"; 
//		boolean isAuthorized = false;
//		if (providerId != null && providerId != "") {
//
//			isAuthorized = authentication.getAuthorities().stream()
//					.anyMatch(auth -> auth.getAuthority().contains(providerId + "|50.0|")
//							|| auth.getAuthority().contains(providerId + "|50.1|")
//							|| auth.getAuthority().contains(providerId + "|50.12|"));
//			System.out.println("auth followup: " + isAuthorized);
//		}
//		return isAuthorized;
//	}
//
//	public boolean hasDssCancellationAccess(Authentication authentication) {
//		String providerId = "101"; 
//		boolean isAuthorized = false;
//		if (providerId != null && providerId != "") {
//
//			isAuthorized = authentication.getAuthorities().stream()
//					.anyMatch(auth -> auth.getAuthority().contains(providerId + "|50.0|")
//							|| auth.getAuthority().contains(providerId + "|50.1|")
//							|| auth.getAuthority().contains(providerId + "|50.13|"));
//			System.out.println("auth Cancellation: " + isAuthorized);
//		}
//		return isAuthorized;
//	}
//	
//	public boolean hasDssOverrideAccess(Authentication authentication) {
//		String providerId = "101";
//		boolean isAuthorized = false;
//		if (providerId != null && providerId != "") {
//
//			isAuthorized = authentication.getAuthorities().stream()
//					.anyMatch(auth -> auth.getAuthority().contains(providerId + "|50.0|")
//							|| auth.getAuthority().contains(providerId + "|50.1|")
//							|| auth.getAuthority().contains(providerId + "|50.14|"));
//			System.out.println("auth Override: " + isAuthorized);
//		}
//		return isAuthorized;
//	}
}
