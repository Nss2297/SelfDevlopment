package com.waseel.smsservice.exception;

import com.waseel.smsservice.model.UnifonicResponseModel;

public class SMSException extends Exception {

	private static final long serialVersionUID = 1L;

	private UnifonicResponseModel unifonicResponseModel;

	public SMSException(UnifonicResponseModel unifonicResponseModel) {
		super();
		this.unifonicResponseModel = unifonicResponseModel;
	}

	public UnifonicResponseModel getUnifonicResponseModel() {
		return unifonicResponseModel;
	}

	public void setUnifonicResponseModel(UnifonicResponseModel unifonicResponseModel) {
		this.unifonicResponseModel = unifonicResponseModel;
	}

}
