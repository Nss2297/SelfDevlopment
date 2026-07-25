package com.waseel.pbm.authentication.model;

public class Error {

	private String fieldName;
	private String errorMessageEn;
	private String errorMessageAr;	

	public Error() {
		super();
	}
	
	public Error(String fieldName, String errorMessageEn, String errorMessageAr) {
		super();
		this.fieldName = fieldName;
		this.errorMessageEn = errorMessageEn;
		this.errorMessageAr = errorMessageAr;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getErrorMessageEn() {
		return errorMessageEn;
	}

	public void setErrorMessageEn(String errorMessageEn) {
		this.errorMessageEn = errorMessageEn;
	}

	public String getErrorMessageAr() {
		return errorMessageAr;
	}

	public void setErrorMessageAr(String errorMessageAr) {
		this.errorMessageAr = errorMessageAr;
	}
}
