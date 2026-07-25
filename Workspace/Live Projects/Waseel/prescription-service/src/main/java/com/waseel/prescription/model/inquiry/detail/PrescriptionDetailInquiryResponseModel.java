package com.waseel.prescription.model.inquiry.detail;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.waseel.prescription.model.policyconsumption.MaxPatientShareValueModel;
import com.waseel.prescription.model.prescription.DiagnosisCodes;

@JsonInclude(Include.NON_NULL)
public class PrescriptionDetailInquiryResponseModel {

	private String requestId;

	private String status;

	private String statusDescription;

	@JsonProperty("ePrescriptionReferenceNumber")
	private String ePrescriptionReferenceNumber;

	private List<DiagnosisCodes> diagnosisCodes;

	@JsonIgnore
	private MaxPatientShareValueModel replaceableBrand;

	@JsonIgnore
	private MaxPatientShareValueModel irreplaceableBrand;
	
	@JsonIgnore
	private MaxPatientShareValueModel outpatientCase;

	private List<ServiceInquiryResponse> results = null;

	private BigDecimal totalPatientShare;

	private BigDecimal totalPayerShare;

	private String totalPatientShareCurrency;

	private String totalPayerShareCurrency;

	private boolean canCancel;
	private boolean canFollowUp;

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

	public List<DiagnosisCodes> getDiagnosisCodes() {
		return diagnosisCodes;
	}

	public void setDiagnosisCodes(List<DiagnosisCodes> diagnosisCodes) {
		this.diagnosisCodes = diagnosisCodes;
	}

	public List<ServiceInquiryResponse> getResults() {
		return results;
	}

	public void setResults(List<ServiceInquiryResponse> results) {
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

	public BigDecimal getTotalPatientShare() {
		return totalPatientShare;
	}

	public void setTotalPatientShare(BigDecimal totalPatientShare) {
		this.totalPatientShare = totalPatientShare;
	}

	public BigDecimal getTotalPayerShare() {
		return totalPayerShare;
	}

	public void setTotalPayerShare(BigDecimal totalPayerShare) {
		this.totalPayerShare = totalPayerShare;
	}

	public String getTotalPatientShareCurrency() {
		return totalPatientShareCurrency;
	}

	public void setTotalPatientShareCurrency(String totalPatientShareCurrency) {
		this.totalPatientShareCurrency = totalPatientShareCurrency;
	}

	public String getTotalPayerShareCurrency() {
		return totalPayerShareCurrency;
	}

	public void setTotalPayerShareCurrency(String totalPayerShareCurrency) {
		this.totalPayerShareCurrency = totalPayerShareCurrency;
	}

	public MaxPatientShareValueModel getReplaceableBrand() {
		return replaceableBrand;
	}

	public void setReplaceableBrand(MaxPatientShareValueModel replaceableBrand) {
		this.replaceableBrand = replaceableBrand;
	}

	public MaxPatientShareValueModel getIrreplaceableBrand() {
		return irreplaceableBrand;
	}

	public void setIrreplaceableBrand(MaxPatientShareValueModel irreplaceableBrand) {
		this.irreplaceableBrand = irreplaceableBrand;
	}

	public MaxPatientShareValueModel getOutpatientCase() {
		return outpatientCase;
	}

	public void setOutpatientCase(MaxPatientShareValueModel outpatientCase) {
		this.outpatientCase = outpatientCase;
	}
	
}
