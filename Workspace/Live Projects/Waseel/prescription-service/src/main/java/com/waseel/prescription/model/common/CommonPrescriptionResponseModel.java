package com.waseel.prescription.model.common;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waseel.prescription.model.prescription.DiagnosisCodes;
import com.waseel.prescription.model.prescription.ServiceResponse;

public class CommonPrescriptionResponseModel {

	private String requestId;

	private String status;

	private String statusDescription;

	@JsonProperty("ePrescriptionReferenceNumber")
	private String ePrescriptionReferenceNumber;

	private List<DiagnosisCodes> diagnosisCodes;

	private List<ServiceResponse> results = null;

	private boolean canCancel;
	private boolean canFollowUp;

	private BigDecimal patientShare;

	private BigDecimal payerShare;

	private String patientShareCurrency;

	private String payerShareCurrency;

	public CommonPrescriptionResponseModel() {
		super();
	}

	public List<DiagnosisCodes> getDiagnosisCodes() {
		return diagnosisCodes;
	}

	public void setDiagnosisCodes(List<DiagnosisCodes> diagnosisCodes) {
		this.diagnosisCodes = diagnosisCodes;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
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

	public String getePrescriptionReferenceNumber() {
		return ePrescriptionReferenceNumber;
	}

	public void setePrescriptionReferenceNumber(String ePrescriptionReferenceNumber) {
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
	}

	public List<ServiceResponse> policyResponseModel() {
		return results;
	}

	public void setResults(List<ServiceResponse> results) {
		this.results = results;
	}

	public boolean isCanCancel() {
		return canCancel;
	}

	public void setCanCancel(boolean canCancel) {
		this.canCancel = canCancel;
	}

	public boolean isCanFollowUp() {
		return canFollowUp;
	}

	public void setCanFollowUp(boolean canFollowUp) {
		this.canFollowUp = canFollowUp;
	}

	public BigDecimal getPatientShare() {
		return patientShare;
	}

	public BigDecimal getPayerShare() {
		return payerShare;
	}

	public void setPatientShare(BigDecimal patientShare) {
		this.patientShare = patientShare;
	}

	public void setPayerShare(BigDecimal payerShare) {
		this.payerShare = payerShare;
	}

	public String getPatientShareCurrency() {
		return patientShareCurrency;
	}

	public String getPayerShareCurrency() {
		return payerShareCurrency;
	}

	public void setPatientShareCurrency(String patientShareCurrency) {
		this.patientShareCurrency = patientShareCurrency;
	}

	public void setPayerShareCurrency(String payerShareCurrency) {
		this.payerShareCurrency = payerShareCurrency;
	}

	public List<ServiceResponse> getResults() {
		return results;
	}

}
