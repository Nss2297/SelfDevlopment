package com.waseel.prescription.model.pbmpayerapis;

import java.math.BigDecimal;
import java.util.List;

public class PbmValidationResult {

	private BigDecimal requestedAmount;
	private BigDecimal approvedAmount;
	private String status;
	private List<EPrescriptionError> errors;
	
	public List<EPrescriptionError> getErrors() {
		return errors;
	}

	public void setErrors(List<EPrescriptionError> errors) {
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
