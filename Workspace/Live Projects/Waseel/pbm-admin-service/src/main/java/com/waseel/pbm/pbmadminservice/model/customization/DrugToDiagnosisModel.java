package com.waseel.pbm.pbmadminservice.model.customization;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class DrugToDiagnosisModel {

	private Long id;
	private String serviceCode;
	private String icdCode;
	private String payer;
	private String categoryOfApproval;
	private String rejectionCategory;
	private String serviceStatus;
	private String moduleName;
	private String rejectionReason;
	private Date updateDateAndTime;
	private Long rowNumber;
	@JsonInclude(Include.NON_DEFAULT)
	@JsonProperty("isDuplicateRecord")
	private boolean duplicateRecord;
	private List<String> errorDescriptions;

	public DrugToDiagnosisModel() {
	}

	public DrugToDiagnosisModel(Long id, String serviceCode, String icdCode, String payer, String categoryOfApproval,
			String rejectionCategory, String serviceStatus, String moduleName, String rejectionReason,
			Date updateDateAndTime) {
		this.id = id;
		this.serviceCode = serviceCode;
		this.icdCode = icdCode;
		this.payer = payer;
		this.categoryOfApproval = categoryOfApproval;
		this.rejectionCategory = rejectionCategory;
		this.serviceStatus = serviceStatus;
		this.moduleName = moduleName;
		this.rejectionReason = rejectionReason;
		this.updateDateAndTime = updateDateAndTime;
	}

	public boolean isDuplicateRecord() {
		return duplicateRecord;
	}

	public void setDuplicateRecord(boolean duplicateRecord) {
		this.duplicateRecord = duplicateRecord;
	}

	public List<String> getErrorDescriptions() {
		return errorDescriptions;
	}

	public void setErrorDescriptions(List<String> errorDescriptions) {
		this.errorDescriptions = errorDescriptions;
	}

	public Long getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Long rowNumber) {
		this.rowNumber = rowNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getIcdCode() {
		return icdCode;
	}

	public void setIcdCode(String icdCode) {
		this.icdCode = icdCode;
	}

	public String getCategoryOfApproval() {
		return categoryOfApproval;
	}

	public void setCategoryOfApproval(String categoryOfApproval) {
		this.categoryOfApproval = categoryOfApproval;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public String getRejectionCategory() {
		return rejectionCategory;
	}

	public void setRejectionCategory(String rejectionCategory) {
		this.rejectionCategory = rejectionCategory;
	}

	public String getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public Date getUpdateDateAndTime() {
		return updateDateAndTime;
	}

	public void setUpdateDateAndTime(Date updateDateAndTime) {
		this.updateDateAndTime = updateDateAndTime;
	}
}
