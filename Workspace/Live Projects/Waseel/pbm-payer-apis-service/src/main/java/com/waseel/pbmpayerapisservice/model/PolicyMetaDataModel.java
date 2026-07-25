package com.waseel.pbmpayerapisservice.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonInclude(Include.NON_NULL)
@JsonTypeName("policyMetadata")
public class PolicyMetaDataModel {

	private String policyNumber;
	private String policyHolderName;
	private String policyType;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date issueDate;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date startDate;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date endDate;
	private Boolean isChiPolicy;
	private List<PolicyClassesModel> policyClasses;

	public String getPolicyNumber() {
		return policyNumber;
	}

	public String getPolicyHolderName() {
		return policyHolderName;
	}

	public String getPolicyType() {
		return policyType;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public List<PolicyClassesModel> getPolicyClasses() {
		return policyClasses;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public void setPolicyHolderName(String policyHolderName) {
		this.policyHolderName = policyHolderName;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setPolicyClasses(List<PolicyClassesModel> policyClasses) {
		this.policyClasses = policyClasses;
	}

	public Boolean getIsChiPolicy() {
		return isChiPolicy;
	}

	public void setIsChiPolicy(Boolean isChiPolicy) {
		this.isChiPolicy = isChiPolicy;
	}
}
