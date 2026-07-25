package com.waseel.pbm.authentication.service;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class I18nMessagesService {

	@Autowired
	MessageSource messageSource;

	public String getEnglishMessage(String key) {
		return getLocaleMessage(key, null, Locale.ENGLISH);
	}

	public String getEnglishMessage(String key, List<String> args) {
		return getLocaleMessage(key, args, Locale.ENGLISH);
	}

	public String getArabicMessage(String key) {
		return getLocaleMessage(key, null, Locale.forLanguageTag("ar"));
	}

	public String getArabicMessage(String key, List<String> args) {
		return getLocaleMessage(key, args, Locale.forLanguageTag("ar"));
	}

	public String getLocaleMessage(String key, List<String> argsList, Locale locale) {
		if (argsList != null) {
			Object[] args = new Object[argsList.size()];
			args = argsList.toArray(args);
			return messageSource.getMessage(key, args, locale);
		}
		return messageSource.getMessage(key, null, locale);
	}
}
