package com.waseel.prescription.model.dispense;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class PrescriptionDispenseResponseModel {

	private String payerId;
	private String providerId;
	@JsonProperty("ePrescriptionReferenceNumber")
	private String ePrescriptionReferenceNumber;
	private String status;
	private String statusDescription;
	private Long countOfService;
	private String approvalReferenceNumber;

	// FOR DISPANSABLE DRUG REST API
	private List<String> errors;

	public PrescriptionDispenseResponseModel(String ePrescriptionReferenceNumber, String status,
			String statusDescription) {
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
		this.status = status;
		this.statusDescription = statusDescription;
	}

	public PrescriptionDispenseResponseModel() {
		super();
	}

	public Long getCountOfService() {
		return countOfService;
	}

	public void setCountOfService(Long countOfService) {
		this.countOfService = countOfService;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getePrescriptionReferenceNumber() {
		return ePrescriptionReferenceNumber;
	}

	public void setePrescriptionReferenceNumber(String ePrescriptionReferenceNumber) {
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public String getApprovalReferenceNumber() {
		return approvalReferenceNumber;
	}

	public void setApprovalReferenceNumber(String approvalReferenceNumber) {
		this.approvalReferenceNumber = approvalReferenceNumber;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
