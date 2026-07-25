package com.waseel.pbm.pbmadminservice.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class CommonMessageService {

	@Autowired
	private MessageSource messageSource;
	
	public CommonMessageService() {
		super();
	}

	public String getMessage(String msg) {
		return messageSource.getMessage(msg,null, Locale.ENGLISH);
	}
}
