package com.waseel.pbm.authentication.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.waseel.pbm.authentication.model.Error;

@Service
public class ErrorResponseService {

	@Autowired
	I18nMessagesService messagesService;

	public Error getErrorObject(String messageKey, String fieldName) {
		Error error = new Error();
		error.setErrorMessageAr(messagesService.getArabicMessage(messageKey));
		error.setErrorMessageEn(messagesService.getEnglishMessage(messageKey));
		error.setFieldName(fieldName);
		return error;
	}

	public Error getErrorObject(String messageKey, List<String> messageArgs, String fieldName) {
		Error error = new Error();

		error.setErrorMessageAr(messagesService.getArabicMessage(messageKey,
				messageArgs.stream().map(arg -> arg.startsWith("fields.") ? messagesService.getArabicMessage(arg) : arg)
						.collect(Collectors.toList())));
		error.setErrorMessageEn(messagesService.getEnglishMessage(messageKey,
				messageArgs.stream()
						.map(arg -> arg.startsWith("fields.") ? messagesService.getEnglishMessage(arg) : arg)
						.collect(Collectors.toList())));
		error.setFieldName(fieldName);
		return error;
	}

}
