package com.waseel.pbm.payercustomizationservice.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomizationResponseModel {

	private Date lastUpdatedDate;
	private String drugCode;
	private String moduleName;
	private String status;
	private Long customizationRequestId;
	private List<ErrorMessage> errorMessage;
	private String message;
	private String lable;
	private String value;

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ErrorMessage> getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(List<ErrorMessage> errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Long getCustomizationRequestId() {
		return customizationRequestId;
	}

	public void setCustomizationRequestId(Long customizationRequestId) {
		this.customizationRequestId = customizationRequestId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLable() {
		return lable;
	}

	public void setLable(String lable) {
		this.lable = lable;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public CustomizationResponseModel() {
		super();
	}

	public CustomizationResponseModel(Date lastUpdatedDate, String drugCode, String moduleName, String status) {
		super();
		this.lastUpdatedDate = lastUpdatedDate;
		this.drugCode = drugCode;
		this.moduleName = moduleName;
		this.status = status;
	}

	public CustomizationResponseModel(Date lastUpdatedDate, String drugCode, String moduleName, String status,
			Long customizationRequestId) {
		super();
		this.lastUpdatedDate = lastUpdatedDate;
		this.drugCode = drugCode;
		this.moduleName = moduleName;
		this.status = status;
		this.customizationRequestId = customizationRequestId;
	}

	public CustomizationResponseModel(Date lastUpdatedDate, String drugCode, String moduleName, String status,
			Long customizationRequestId, String lable, String value) {
		super();
		this.lastUpdatedDate = lastUpdatedDate;
		this.drugCode = drugCode;
		this.moduleName = moduleName;
		this.status = status;
		this.customizationRequestId = customizationRequestId;
		this.lable = lable;
		this.value = value;
	}

	public CustomizationResponseModel(Long customizationRequestId) {
		super();
		this.customizationRequestId = customizationRequestId;
	}

	public CustomizationResponseModel(List<ErrorMessage> errorMessage) {
		super();
		this.errorMessage = errorMessage;
	}
}
