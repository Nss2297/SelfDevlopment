package com.waseel.prescription.model.br;

import java.util.List;

public class InvalidResponseModel {

	private String requestId;
	private String errorCode;
	private List<String> errorDescription;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public List<String> getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(List<String> errorDescription) {
		this.errorDescription = errorDescription;
	}
}
