package com.waseel.pbm.payercustomizationservice.exceptions;

import com.waseel.pbm.payercustomizationservice.model.CustomizationResponseModel;

public class PayerCustomizationException extends Exception {

	private static final long serialVersionUID = 8102853135027185134L;
	private CustomizationResponseModel invalidCustomizationResponse;

	public CustomizationResponseModel getInvalidCustomizationResponse() {
		return invalidCustomizationResponse;
	}

	public void setInvalidCustomizationResponse(CustomizationResponseModel invalidCustomizationResponse) {
		this.invalidCustomizationResponse = invalidCustomizationResponse;
	}

	public PayerCustomizationException(CustomizationResponseModel invalidCustomizationResponse) {
		super();
		this.invalidCustomizationResponse = invalidCustomizationResponse;
	}

}
