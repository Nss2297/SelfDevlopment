package com.waseel.pbm.pbmadminservice.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waseel.pbm.pbmadminservice.model.mdss.MemberChronicDiseaseResponseModel;
import com.waseel.pbm.pbmadminservice.model.payer.PolicyMetadata;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberDetailsResponseModel {

	private String memberName;

	private Long idNumber;

	private String gender;

	private String dateOfBirth;

	private String maritalStatus;

	private String nationality;

	private String mobileNumber;

	private String email;

	private List<PolicyMetadata> memberPolicyDetails;

	private List<MemberChronicDiseaseResponseModel> memberChronicDiseaseResponseModel;

	private List<String> errors;

	public String getMemberName() {
		return memberName;
	}

	public Long getIdNumber() {
		return idNumber;
	}

	public String getGender() {
		return gender;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public String getNationality() {
		return nationality;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public List<PolicyMetadata> getMemberPolicyDetails() {
		return memberPolicyDetails;
	}

	public List<MemberChronicDiseaseResponseModel> getMemberChronicDiseaseResponseModel() {
		return memberChronicDiseaseResponseModel;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public void setIdNumber(Long idNumber) {
		this.idNumber = idNumber;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setMemberPolicyDetails(List<PolicyMetadata> memberPolicyDetails) {
		this.memberPolicyDetails = memberPolicyDetails;
	}

	public void setMemberChronicDiseaseResponseModel(List<MemberChronicDiseaseResponseModel> memberChronicDiseaseResponseModel) {
		this.memberChronicDiseaseResponseModel = memberChronicDiseaseResponseModel;
	}


	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}
