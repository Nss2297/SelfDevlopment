package com.waseel.pbmpayerapisservice.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.waseel.pbmpayerapisservice.validator.customannotation.IsValidCaseType;
import com.waseel.pbmpayerapisservice.validator.customannotation.IsValidEPrescriptionStatus;
import com.waseel.pbmpayerapisservice.validator.customannotation.IsValidGender;
import com.waseel.pbmpayerapisservice.validator.customannotation.IsValidPhysicianCategory;
import com.waseel.pbmpayerapisservice.validator.customannotation.IsValidRequestType;
import com.waseel.pbmpayerapisservice.validator.customannotation.NoMoreThan100Length;
import com.waseel.pbmpayerapisservice.validator.customannotation.NoMoreThan10Length;
import com.waseel.pbmpayerapisservice.validator.customannotation.NoMoreThan200Length;
import com.waseel.pbmpayerapisservice.validator.customannotation.NoMoreThan20Length;
import com.waseel.pbmpayerapisservice.validator.customannotation.NoMoreThan250Length;
import com.waseel.pbmpayerapisservice.validator.customannotation.NoMoreThan3000Length;
import com.waseel.pbmpayerapisservice.validator.customannotation.NoMoreThan50Length;
import com.waseel.pbmpayerapisservice.validator.customannotation.NoMoreThan60Length;
import com.waseel.pbmpayerapisservice.validator.customannotation.NoSpecialCharacter;

public class EprescriptionRequestModel {

	@NotEmpty(message = "requestType {notEmptyValidation}")
	@NoMoreThan20Length(message = "requestType {noMoreThan20LengthValidation}")
	@IsValidRequestType(message = "{requestTypeValidation}")
	private String requestType;

	@NotEmpty(message = "caseType {notEmptyValidation}")
	@NoMoreThan50Length(message = "caseType {noMoreThan50LengthValidation}")
	@IsValidCaseType(message = "{caseTypeValidation}")
	private String caseType;

	@NotEmpty(message = "ePrescriptionReferenceNumber {notEmptyValidation}")
	@NoMoreThan100Length(message = "ePrescriptionReferenceNumber {noMoreThan100LengthValidation}")
	private String ePrescriptionReferenceNumber;

	@NotEmpty(message = "ePrescriptionStatus {notEmptyValidation}")
	@NoMoreThan60Length(message = "ePrescriptionStatus {noMoreThan60LengthValidation}")
	@IsValidEPrescriptionStatus(message = "{ePrescriptionStatusValidation}")
	private String ePrescriptionStatus;

	@NoMoreThan3000Length(message = "ePrescriptionStatusDescription {noMoreThan3000LengthValidation}")
	private String ePrescriptionStatusDescription;

	@NotEmpty(message = "payerId {notEmptyValidation}")
	@NoMoreThan20Length(message = "payerId {noMoreThan20LengthValidation}")
	@Pattern(regexp = "^(?!\\s*$).+", message = "payerId {noWhiteSpaceCharacterValidation}")
	private String payerId;

	@NotEmpty(message = "providerId {notEmptyValidation}")
	@NoMoreThan20Length(message = "providerId {noMoreThan20LengthValidation}")
	@Pattern(regexp = "^(?!\\s*$).+", message = "providerId {noWhiteSpaceCharacterValidation}")
	private String providerId;

	@NotEmpty(message = "memberName {notEmptyValidation}")
	@NoMoreThan200Length(message = "memberName {noMoreThan200LengthValidation}")
	private String memberName;

	@NotNull(message = "idNumber {notEmptyValidation}")
	private Long idNumber;

	@NotNull(message = "dateOfBirth {notEmptyValidation}")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private Date dateOfBirth;

	@NoMoreThan50Length(message = "policyNumber {noMoreThan50LengthValidation}")
	@Pattern(regexp = "^(?!.*\\s)\\S*$", message = "policyNumber {noWhiteSpaceCharacterValidation}")
	private String policyNumber;

	@NotEmpty(message = "memberGender {notEmptyValidation}")
	@NoMoreThan10Length(message = "memberGender {noMoreThan10LengthValidation}")
	@IsValidGender(message = "{memberGenderValidation}")
	private String memberGender;

	@Digits(integer = 3, fraction = 2, message = "memberWeight {noMoreThan3With2DecimalPrecisionValidation}")
	private BigDecimal memberWeight;

	@Digits(integer = 3, fraction = 2, message = "memberHeight {noMoreThan3With2DecimalPrecisionValidation}")
	private BigDecimal memberHeight;

	@NotEmpty(message = "physicianLicenseNumber {notEmptyValidation}")
	@NoMoreThan20Length(message = "physicianLicenseNumber {noMoreThan20LengthValidation}")
	@Pattern(regexp = "^(?!.*\\s)\\S*$", message = "physicianLicenseNumber {noWhiteSpaceCharacterValidation}")
	@NoSpecialCharacter(message = "physicianLicenseNumber {noSpecialCharactersValidation}")
	private String physicianLicenseNumber;

	@NotEmpty(message = "physicianSpeciality {notEmptyValidation}")
	@NoMoreThan100Length(message = "physicianSpeciality {noMoreThan100LengthValidation}")
	@Pattern(regexp = "^(?!\\s*$).+", message = "physicianSpeciality {noWhiteSpaceCharacterValidation}")
	private String physicianSpeciality;

	@NoMoreThan250Length(message = "physicianName {noMoreThan250LengthValidation}")
	private String physicianName;

	@IsValidPhysicianCategory(message = "{physicianCategoryValidation}")
	private String physicianCategory;

	@NotNull(message = "canCancel {notEmptyValidation}")
	private boolean canCancel;

	@NotNull(message = "canFollowUp {notEmptyValidation}")
	private boolean canFollowUp;

	@NotNull(message = "totalPrescriptionPrice {notEmptyValidation}")
	private BigDecimal totalPrescriptionPrice;

	@NotNull(message = "totalPatientShareValue {notEmptyValidation}")
	private BigDecimal totalPatientShareValue;

	@NotNull(message = "totalPatientShareCurrency {notEmptyValidation}")
	private String totalPatientShareCurrency;

	@NotNull(message = "totalPayerShareValue {notEmptyValidation}")
	private BigDecimal totalPayerShareValue;

	@NotNull(message = "payerShareCurrency {notEmptyValidation}")
	private String totalPayerShareCurrency;

	@NotEmpty(message = "diagnosisCodes {notEmptyValidation}")
	@Valid
	private List<DiagnosisCodes> diagnosisCodes;

	@NotEmpty(message = "drugList {notEmptyValidation}")
	@Valid
	private List<DrugList> drugList;

	private BigDecimal patientShareVatAmount = BigDecimal.ZERO;

	private String patientShareVatCurrency = "SAR";

	public List<DiagnosisCodes> getDiagnosisCodes() {
		return diagnosisCodes;
	}

	public void setDiagnosisCodes(List<DiagnosisCodes> diagnosisCodes) {
		this.diagnosisCodes = diagnosisCodes;
	}

	public List<DrugList> getDrugList() {
		return drugList;
	}

	public void setDrugList(List<DrugList> drugList) {
		this.drugList = drugList;
	}

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

	public BigDecimal getPatientShareVatAmount() {
		return patientShareVatAmount;
	}

	public void setPatientShareVatAmount(BigDecimal patientShareVatAmount) {
		this.patientShareVatAmount = patientShareVatAmount;
	}

	public String getPatientShareVatCurrency() {
		return patientShareVatCurrency;
	}

	public void setPatientShareVatCurrency(String patientShareVatCurrency) {
		this.patientShareVatCurrency = patientShareVatCurrency;
	}
}
