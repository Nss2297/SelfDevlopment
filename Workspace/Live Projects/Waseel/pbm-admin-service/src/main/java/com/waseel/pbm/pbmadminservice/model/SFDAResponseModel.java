package com.waseel.pbm.pbmadminservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SFDAResponseModel {

	private String errorCode;
	private String errorDescription;
	private String sfdaCode;
	private Long rowNumber;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public String getSfdaCode() {
		return sfdaCode;
	}

	public void setSfdaCode(String sfdaCode) {
		this.sfdaCode = sfdaCode;
	}

	public Long getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Long rowNumber) {
		this.rowNumber = rowNumber;
	}

	public SFDAResponseModel(String errorCode, String errorDescription, String sfdaCode, Long rowNumber) {
		super();
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
		this.sfdaCode = sfdaCode;
		this.rowNumber = rowNumber;
	}

	public SFDAResponseModel() {
		super();
	}

}
