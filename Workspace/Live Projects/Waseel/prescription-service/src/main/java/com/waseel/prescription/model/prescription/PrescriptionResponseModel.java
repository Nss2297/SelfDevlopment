package com.waseel.prescription.model.prescription;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waseel.prescription.model.common.CommonPrescriptionResponseModel;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrescriptionResponseModel extends CommonPrescriptionResponseModel {

	private int httpStatusCode;
	@Schema(hidden = true)
	private String httpStatusDescription;

	public PrescriptionResponseModel() {
		super();
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public String getHttpStatusDescription() {
		return httpStatusDescription;
	}

	public void setHttpStatusDescription(String httpStatusDescription) {
		this.httpStatusDescription = httpStatusDescription;
	}

	public PrescriptionResponseModel(int httpStatusCode, String httpStatusDescription) {
		this.httpStatusCode = httpStatusCode;
		this.httpStatusDescription = httpStatusDescription;
	}
}