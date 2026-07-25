package com.waseel.pbmnotificationservice.model.eprescription.inquiry;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EPrescriptionInquiryResponseModel {

	private String requestType;
	private String caseType;
	private String ePrescriptionReferenceNumber;
	private String ePrescriptionStatus;
	private String ePrescriptionStatusDescription;
	private String payerId;
	private String providerId;
	private String memberName;
	private Long idNumber;
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dateOfBirth;
	private String policyNumber;
	private String memberGender;
	private BigDecimal memberWeight;
	private BigDecimal memberHeight;
	private String physicianLicenseNumber;
	private String physicianSpeciality;
	private String physicianName;
	private String physicianCategory;
	private boolean canCancel;
	private boolean canFollowUp;
	private BigDecimal totalPrescriptionPrice;
	private BigDecimal totalPatientShareValue;
	private String totalPatientShareCurrency;
	private BigDecimal totalPayerShareValue;
	private String totalPayerShareCurrency;
	private BigDecimal totalPatientShareVatAmountValue = BigDecimal.ZERO;
	private String totalPatientShareVatAmountCurrency = "SAR";
	private List<DiagnosisCodes> diagnosisCodes;
	@JsonProperty(value = "drugList")
	private List<EPrescriptionInquiryDrugList> drugList;
	
	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getePrescriptionReferenceNumber() {
		return ePrescriptionReferenceNumber;
	}

	public void setePrescriptionReferenceNumber(String ePrescriptionReferenceNumber) {
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
	}

	public String getePrescriptionStatus() {
		return ePrescriptionStatus;
	}

	public void setePrescriptionStatus(String ePrescriptionStatus) {
		this.ePrescriptionStatus = ePrescriptionStatus;
	}

	public String getePrescriptionStatusDescription() {
		return ePrescriptionStatusDescription;
	}

	public void setePrescriptionStatusDescription(String ePrescriptionStatusDescription) {
		this.ePrescriptionStatusDescription = ePrescriptionStatusDescription;
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getMemberGender() {
		return memberGender;
	}

	public void setMemberGender(String memberGender) {
		this.memberGender = memberGender;
	}

	public BigDecimal getMemberWeight() {
		return memberWeight;
	}

	public void setMemberWeight(BigDecimal memberWeight) {
		this.memberWeight = memberWeight;
	}

	public BigDecimal getMemberHeight() {
		return memberHeight;
	}

	public void setMemberHeight(BigDecimal memberHeight) {
		this.memberHeight = memberHeight;
	}

	public String getPhysicianLicenseNumber() {
		return physicianLicenseNumber;
	}

	public void setPhysicianLicenseNumber(String physicianLicenseNumber) {
		this.physicianLicenseNumber = physicianLicenseNumber;
	}

	public String getPhysicianSpeciality() {
		return physicianSpeciality;
	}

	public void setPhysicianSpeciality(String physicianSpeciality) {
		this.physicianSpeciality = physicianSpeciality;
	}

	public String getPhysicianName() {
		return physicianName;
	}

	public void setPhysicianName(String physicianName) {
		this.physicianName = physicianName;
	}

	public String getPhysicianCategory() {
		return physicianCategory;
	}

	public void setPhysicianCategory(String physicianCategory) {
		this.physicianCategory = physicianCategory;
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

	public BigDecimal getTotalPrescriptionPrice() {
		return totalPrescriptionPrice;
	}

	public void setTotalPrescriptionPrice(BigDecimal totalPrescriptionPrice) {
		this.totalPrescriptionPrice = totalPrescriptionPrice;
	}

	public List<DiagnosisCodes> getDiagnosisCodes() {
		return diagnosisCodes;
	}

	public void setDiagnosisCodes(List<DiagnosisCodes> diagnosisCodes) {
		this.diagnosisCodes = diagnosisCodes;
	}

	public List<EPrescriptionInquiryDrugList> getDrugList() {
		return drugList;
	}

	public void setDrugList(List<EPrescriptionInquiryDrugList> drugList) {
		this.drugList = drugList;
	}

	public BigDecimal getTotalPatientShareValue() {
		return totalPatientShareValue;
	}

	public void setTotalPatientShareValue(BigDecimal totalPatientShareValue) {
		this.totalPatientShareValue = totalPatientShareValue;
	}

	public String getTotalPatientShareCurrency() {
		return totalPatientShareCurrency;
	}

	public void setTotalPatientShareCurrency(String totalPatientShareCurrency) {
		this.totalPatientShareCurrency = totalPatientShareCurrency;
	}

	public BigDecimal getTotalPayerShareValue() {
		return totalPayerShareValue;
	}

	public void setTotalPayerShareValue(BigDecimal totalPayerShareValue) {
		this.totalPayerShareValue = totalPayerShareValue;
	}

	public String getTotalPayerShareCurrency() {
		return totalPayerShareCurrency;
	}

	public void setTotalPayerShareCurrency(String totalPayerShareCurrency) {
		this.totalPayerShareCurrency = totalPayerShareCurrency;
	}

	public BigDecimal getTotalPatientShareVatAmountValue() {
		return totalPatientShareVatAmountValue;
	}

	public void setTotalPatientShareVatAmountValue(BigDecimal totalPatientShareVatAmountValue) {
		this.totalPatientShareVatAmountValue = totalPatientShareVatAmountValue;
	}

	public String getTotalPatientShareVatAmountCurrency() {
		return totalPatientShareVatAmountCurrency;
	}

	public void setTotalPatientShareVatAmountCurrency(String totalPatientShareVatAmountCurrency) {
		this.totalPatientShareVatAmountCurrency = totalPatientShareVatAmountCurrency;
	}
}
