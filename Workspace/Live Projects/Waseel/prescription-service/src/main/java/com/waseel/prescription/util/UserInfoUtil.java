package com.waseel.prescription.util;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UserInfoUtil {

	private UserInfoUtil() {
		super();
	}

	private static String getUserInfo(String key, Authentication token) {
		if (token != null && token.getPrincipal() != null && token.getPrincipal() instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<String, String> userData = (Map<String, String>) token.getPrincipal();
			return userData.get(key);
		}
		return null;
	}
	
	public static String getAccId(Authentication token) {
		return getUserInfo("accId", token);
	}

	public static String getPatientId(Authentication token) {
		return getUserInfo("patientId", token);
	}

	public static String getAccName(Authentication token) {
		return getUserInfo("accName", token);
	}

	public static String getAccCode(Authentication token) {
		return getUserInfo("accCode", token);
	}

	public static String getUsername(Authentication token) {
		return getUserInfo("username", token);
	}

	public static String getEmail(Authentication token) {
		return getUserInfo("email", token);
	}
	
	public static List<SimpleGrantedAuthority> getAuthority(Authentication token) {
		return token.getAuthorities().stream().map(SimpleGrantedAuthority.class::cast).collect(Collectors.toList());
	}
}
