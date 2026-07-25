package com.waseel.pbmpayerapisservice.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InvalidResponseModel {

	private String status;
	private String statusDescription;
	private List<String> errors;
	private String memberName;
	private Long idNumber;
	private String gender;
	private Date dateOfBirth;
	private String maritalStatus;
	private String nationality;
	private String mobileNumber;
	private String email;
	private List<PolicyInformationModel> policyInformation;
	private String ePrescriptionReferenceNumber;
	private String approvalReferenceNumber;
	private PolicyMetaDataModel policyMetadata;

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getePrescriptionReferenceNumber() {
		return ePrescriptionReferenceNumber;
	}

	public void setePrescriptionReferenceNumber(String ePrescriptionReferenceNumber) {
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
	}

	public String getApprovalReferenceNumber() {
		return approvalReferenceNumber;
	}

	public void setApprovalReferenceNumber(String approvalReferenceNumber) {
		this.approvalReferenceNumber = approvalReferenceNumber;
	}

	public PolicyMetaDataModel getPolicyMetadata() {
		return policyMetadata;
	}

	public void setPolicyMetadata(PolicyMetaDataModel policyMetadata) {
		this.policyMetadata = policyMetadata;
	}

}
