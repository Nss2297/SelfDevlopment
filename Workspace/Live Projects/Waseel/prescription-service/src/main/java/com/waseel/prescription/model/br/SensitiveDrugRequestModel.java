package com.waseel.prescription.model.br;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.waseel.prescription.validator.customannotation.IsNumber;
import com.waseel.prescription.validator.customannotation.NoMoreThan100Length;


public class SensitiveDrugRequestModel {
	
	@NotEmpty(message = "drugList {notEmptyValidation}")
	private List<@NotBlank(message = "drugList {notEmptyValidation}") String> drugList;
	
	@NotEmpty(message = "providerId {notEmptyValidation}")
	@Pattern(regexp = "^(?!\\s*$).+", message = "providerId {noWhiteSpaceCharacterValidation}")
	@IsNumber(message = "providerId {notANumberValidation}")
	private String providerId;

	@NotEmpty(message = "payerId {notEmptyValidation}")
	@Pattern(regexp = "^(?!\\s*$).+", message = "payerId {noWhiteSpaceCharacterValidation}")
	@IsNumber(message = "payerId {notANumberValidation}")
	private String payerId;
	
	@NotEmpty(message = "requestId {notEmptyValidation}")
	@NoMoreThan100Length(message = "requestId {noMoreThan100LengthValidation}")
	@Pattern(regexp = "^(?!\\s*$).+", message = "requestId {noWhiteSpaceCharacterValidation}")
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
