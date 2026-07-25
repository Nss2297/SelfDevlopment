package com.waseel.pbmpayerapisservice.model;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.waseel.pbmpayerapisservice.validator.customannotation.IsValidServiceStatus;
import com.waseel.pbmpayerapisservice.validator.customannotation.NoMoreThan60Length;

public class PbmValidationResult {

	@NotNull(message = "requestedAmount {notEmptyValidation}")
	private BigDecimal requestedAmount;

	@NotNull(message = "approvedAmount {notEmptyValidation}")
	private BigDecimal approvedAmount;

	@NotEmpty(message = "status {notEmptyValidation}")
    @NoMoreThan60Length(message = "status {noMoreThan60LengthValidation}")
	@IsValidServiceStatus(message = "{serviceStatusValidation}")
	private String status;
	
	private List<Error> errors;
	
	public List<Error> getErrors() {
		return errors;
	}

	public void setErrors(List<Error> errors) {
		this.errors = errors;
	}

	public BigDecimal getRequestedAmount() {
		return requestedAmount;
	}

	public void setRequestedAmount(BigDecimal requestedAmount) {
		this.requestedAmount = requestedAmount;
	}

	public BigDecimal getApprovedAmount() {
		return approvedAmount;
	}

	public void setApprovedAmount(BigDecimal approvedAmount) {
		this.approvedAmount = approvedAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
