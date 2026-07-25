package com.waseel.prescription.model.inquiry.eprescription;

import java.math.BigDecimal;
import java.util.List;

public class CommonInquiryModel {

	private BigDecimal requestedAmount;
	private BigDecimal approvedAmount;
	private String status;
	private List<EPrescriptionInquiryError> errors;

	public List<EPrescriptionInquiryError> getErrors() {
		return errors;
	}

	public void setErrors(List<EPrescriptionInquiryError> errors) {
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

	public CommonInquiryModel() {
		super();
	}

	public CommonInquiryModel(BigDecimal requestedAmount, BigDecimal approvedAmount, String status,
			List<EPrescriptionInquiryError> errors) {
		super();
		this.requestedAmount = requestedAmount;
		this.approvedAmount = approvedAmount;
		this.status = status;
		this.errors = errors;
	}

}
