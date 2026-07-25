package com.waseel.pbmnotificationservice.model.common;

public class CommonResponseModel {

	private String status;
	private String statusDescription;

	public CommonResponseModel() {
		super();
	}

	public CommonResponseModel(String status, String statusDescription) {
		this.status = status;
		this.statusDescription = statusDescription;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
}
