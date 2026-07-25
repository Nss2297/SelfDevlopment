package com.waseel.pbm.pbmadminservice.model;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberDetail {

	@JsonProperty("memberName")
	private String memberName;
	@JsonProperty("idNumber")
	private Long idNumber;
	@JsonProperty("gender")
	private String gender;
	@JsonProperty("dateOfBirth")
	private String dateOfBirth;
	@JsonProperty("maritalStatus")
	private String maritalStatus;
	@JsonProperty("nationality")
	private String nationality;
	@JsonProperty("mobileNumber")
	private String mobileNumber;
	@JsonProperty("email")
	private String email;
	@JsonProperty("policyInformation")
	private List<PolicyInformation> policyInformation;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

	@JsonProperty("memberName")
	public String getMemberName() {
		return memberName;
	}

	@JsonProperty("memberName")
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	@JsonProperty("idNumber")
	public Long getIdNumber() {
		return idNumber;
	}

	@JsonProperty("idNumber")
	public void setIdNumber(Long idNumber) {
		this.idNumber = idNumber;
	}

	@JsonProperty("gender")
	public String getGender() {
		return gender;
	}

	@JsonProperty("gender")
	public void setGender(String gender) {
		this.gender = gender;
	}

	@JsonProperty("dateOfBirth")
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	@JsonProperty("dateOfBirth")
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	@JsonProperty("maritalStatus")
	public String getMaritalStatus() {
		return maritalStatus;
	}

	@JsonProperty("maritalStatus")
	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	@JsonProperty("nationality")
	public String getNationality() {
		return nationality;
	}

	@JsonProperty("nationality")
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	@JsonProperty("mobileNumber")
	public String getMobileNumber() {
		return mobileNumber;
	}

	@JsonProperty("mobileNumber")
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	@JsonProperty("email")
	public String getEmail() {
		return email;
	}

	@JsonProperty("email")
	public void setEmail(String email) {
		this.email = email;
	}

	@JsonProperty("policyInformation")
	public List<PolicyInformation> getPolicyInformation() {
		return policyInformation;
	}

	@JsonProperty("policyInformation")
	public void setPolicyInformation(List<PolicyInformation> policyInformation) {
		this.policyInformation = policyInformation;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}
}
