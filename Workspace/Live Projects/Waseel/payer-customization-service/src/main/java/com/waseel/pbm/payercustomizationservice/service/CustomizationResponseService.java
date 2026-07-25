package com.waseel.pbm.payercustomizationservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.waseel.pbm.payercustomizationservice.model.CustomizationResponseModel;
import com.waseel.pbm.payercustomizationservice.model.ErrorMessage;

@Service
public class CustomizationResponseService {

	public CustomizationResponseModel unAuthorizedCustomizationResponse(AccessDeniedException accessDeniedException) {
		ErrorMessage errorMessage = new ErrorMessage(accessDeniedException.getMessage());
		List<ErrorMessage> errorMessages = new ArrayList<>();
		errorMessages.add(errorMessage);
		return new CustomizationResponseModel(errorMessages);
	}

	public CustomizationResponseModel failedCustomizationResponse(Exception exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage());
		List<ErrorMessage> errorMessages = new ArrayList<>();
		errorMessages.add(errorMessage);
		return new CustomizationResponseModel(errorMessages);
	}

	public CustomizationResponseModel invalidCustomizationResponse(String error) {
		ErrorMessage errorMessage = new ErrorMessage(error);
		List<ErrorMessage> errorMessages = new ArrayList<>();
		errorMessages.add(errorMessage);
		return new CustomizationResponseModel(errorMessages);
	}
}
