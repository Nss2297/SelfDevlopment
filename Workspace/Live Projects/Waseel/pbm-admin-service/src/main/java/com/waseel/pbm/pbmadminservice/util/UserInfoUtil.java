package com.waseel.pbm.pbmadminservice.util;

import java.util.Map;

import org.springframework.security.core.Authentication;

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

	public static String getAccName(Authentication token) {
		return getUserInfo("accName", token);
	}

	public static String getAccCode(Authentication token) {
		return getUserInfo("accCode", token);
	}

	public static String getAccCategory(Authentication token) {
		return getUserInfo("accCategory", token);
	}

	public static String getUsername(Authentication token) {
		return getUserInfo("username", token);
	}

	public static String getEmail(Authentication token) {
		return getUserInfo("email", token);
	}
}
