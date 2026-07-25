package com.waseel.brservice.model;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.waseel.brservice.validator.customannotation.IsNumber;
import com.waseel.brservice.validator.customannotation.NoMoreThan100Length;


public class SensitiveDrugRequestModel {
	
	@NotEmpty(message = "drugList {notEmptyValidation}")
	private List<@NotBlank(message = "drugList value {notEmptyValidation}") String> drugList;
	
	@NotBlank(message = "providerId {notEmptyValidation}")
	@IsNumber(message = "providerId {notANumberValidation}")
	private String providerId;

	@NotBlank(message = "payerId {notEmptyValidation}")
	@IsNumber(message = "payerId {notANumberValidation}")
	private String payerId;
	
	@NotBlank(message = "requestId {notEmptyValidation}")
	@NoMoreThan100Length(message = "requestId {noMoreThan100LengthValidation}")
	private String requestId;

	public List<String> getDrugList() {
		return drugList;
	}

	public void setDrugList(List<String> drugList) {
		this.drugList = drugList;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
}
