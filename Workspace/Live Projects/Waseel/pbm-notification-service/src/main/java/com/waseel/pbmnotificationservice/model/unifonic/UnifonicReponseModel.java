package com.waseel.pbmnotificationservice.model.unifonic;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class UnifonicReponseModel {

	private boolean success;
	private String message;
	@JsonProperty("Status")
	private String status;
	private String errorCode;
	@JsonProperty("data")
	private UnifonicDataModel unifoniDataModel;

	public boolean isSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public UnifonicDataModel getUnifoniDataModel() {
		return unifoniDataModel;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public void setUnifoniDataModel(UnifonicDataModel unifoniDataModel) {
		this.unifoniDataModel = unifoniDataModel;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public UnifonicReponseModel() {
		super();
	}

	public UnifonicReponseModel(boolean success, String message, String status, String errorCode,
			UnifonicDataModel unifoniDataModel) {
		super();
		this.success = success;
		this.message = message;
		this.status = status;
		this.errorCode = errorCode;
		this.unifoniDataModel = unifoniDataModel;
	}

}
