package com.waseel.pbm.pbmadminservice.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CommonResponse {

	@JsonInclude(Include.NON_DEFAULT)
	private int responseCode;
	private String responseDescription;
	List<String> errorDescriptions;

	public CommonResponse() {
	}

	public CommonResponse(List<String> errorDescriptions) {
		this.errorDescriptions = errorDescriptions;
	}

	public List<String> getErrorDescriptions() {
		return errorDescriptions;
	}

	public void setErrorDescriptions(List<String> errorDescriptions) {
		this.errorDescriptions = errorDescriptions;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseDescription() {
		return responseDescription;
	}

	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	public CommonResponse(int responseCode, String responseDescription) {
		super();
		this.responseCode = responseCode;
		this.responseDescription = responseDescription;
	}

}
