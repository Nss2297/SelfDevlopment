package com.waseel.pbm.pbmadminservice.model.payer;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PolicyMetadata {

	@JsonProperty("policyNumber")
	private String policyNumber;
	@JsonProperty("policyHolderName")
	private String policyHolderName;
	@JsonProperty("memberId")
	private String memberId;
	@JsonProperty("policyType")
	private String policyType;
	@JsonProperty("issueDate")
	private String issueDate;
	@JsonProperty("startDate")
	private String startDate;
	@JsonProperty("endDate")
	private String endDate;
	private Boolean isChiPolicy;
	@JsonProperty("policyClasses")
	private List<PolicyClass> policyClasses;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

	@JsonProperty("policyNumber")
	public String getPolicyNumber() {
		return policyNumber;
	}

	@JsonProperty("policyNumber")
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	@JsonProperty("policyHolderName")
	public String getPolicyHolderName() {
		return policyHolderName;
	}

	@JsonProperty("policyHolderName")
	public void setPolicyHolderName(String policyHolderName) {
		this.policyHolderName = policyHolderName;
	}

	@JsonProperty("policyType")
	public String getPolicyType() {
		return policyType;
	}

	@JsonProperty("policyType")
	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	@JsonProperty("issueDate")
	public String getIssueDate() {
		return issueDate;
	}

	@JsonProperty("issueDate")
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	@JsonProperty("startDate")
	public String getStartDate() {
		return startDate;
	}

	@JsonProperty("startDate")
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	@JsonProperty("endDate")
	public String getEndDate() {
		return endDate;
	}

	@JsonProperty("endDate")
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@JsonProperty("policyClasses")
	public List<PolicyClass> getPolicyClasses() {
		return policyClasses;
	}

	@JsonProperty("policyClasses")
	public void setPolicyClasses(List<PolicyClass> policyClasses) {
		this.policyClasses = policyClasses;
	}

	public Boolean getIsChiPolicy() {
		return isChiPolicy;
	}

	public void setIsChiPolicy(Boolean isChiPolicy) {
		this.isChiPolicy = isChiPolicy;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

}
