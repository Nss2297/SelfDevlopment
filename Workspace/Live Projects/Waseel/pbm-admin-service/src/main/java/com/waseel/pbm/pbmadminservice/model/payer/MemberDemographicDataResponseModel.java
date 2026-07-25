package com.waseel.pbm.pbmadminservice.model.payer;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class MemberDemographicDataResponseModel {

	private String memberName;
	private Long idNumber;
	private String gender;
	@JsonFormat(pattern = "dd-MM-yyyy")
	private Date dateOfBirth;
	private String maritalStatus;
	private String nationality;
	private String mobileNumber;
	private String email;
    private String age;
	private List<PolicyInformationModel> policyInformation;
	private String status;
	private String statusDescription;
	private List<String> errors;
	
	public MemberDemographicDataResponseModel() {
		super();
	}

	public MemberDemographicDataResponseModel(String memberName, Long idNumber, String gender, Date dateOfBirth,
			String maritalStatus, String nationality, String mobileNumber, String email, String age,
			List<PolicyInformationModel> policyInformation) {
		this.memberName = memberName;
		this.idNumber = idNumber;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.maritalStatus = maritalStatus;
		this.nationality = nationality;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.age = age;
		this.policyInformation = policyInformation;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
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

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Long getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(Long idNumber) {
		this.idNumber = idNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<PolicyInformationModel> getPolicyInformation() {
		return policyInformation;
	}

	public void setPolicyInformation(List<PolicyInformationModel> policyInformation) {
		this.policyInformation = policyInformation;
	}
}
