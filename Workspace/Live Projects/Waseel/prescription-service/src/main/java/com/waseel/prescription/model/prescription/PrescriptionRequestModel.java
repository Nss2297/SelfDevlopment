package com.waseel.prescription.model.prescription;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.waseel.prescription.model.policyconsumption.DrugListModel;
import com.waseel.prescription.validator.customannotation.HasTenCharactersLength;
import com.waseel.prescription.validator.customannotation.IsNumber;
import com.waseel.prescription.validator.customannotation.IsValidCaseType;
import com.waseel.prescription.validator.customannotation.IsValidDateFormat;
import com.waseel.prescription.validator.customannotation.IsValidGender;
import com.waseel.prescription.validator.customannotation.IsValidPhysicianCategory;
import com.waseel.prescription.validator.customannotation.NoLessThanThreeLength;
import com.waseel.prescription.validator.customannotation.NoMoreThan100Length;
import com.waseel.prescription.validator.customannotation.NoMoreThan200Length;
import com.waseel.prescription.validator.customannotation.NoMoreThan250Length;
import com.waseel.prescription.validator.customannotation.NoMoreThan56Length;
import com.waseel.prescription.validator.customannotation.NoMoreThanFifteenLength;
import com.waseel.prescription.validator.customannotation.NoMoreThanThirtyLength;
import com.waseel.prescription.validator.customannotation.NoMoreThanTwentyLength;
import com.waseel.prescription.validator.customannotation.NoSpecialCharacter;
import com.waseel.prescription.validator.customannotation.NoSpecialCharacterExceptHyphen;
import com.waseel.prescription.validator.customannotation.NoWhiteSpaceCharacter;
import com.waseel.prescription.validator.customannotation.NoWhiteSpaceCharacterAtBeginning;
import com.waseel.prescription.validator.customannotation.NoWhiteSpaceCharacterAtEnd;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PrescriptionRequestModel {

	@NotEmpty(message = "payerId {notEmptyValidation}")
	@IsNumber(message = "payerId {notAnumberValidation}")
	@NoMoreThanTwentyLength(message = "PayerId {noMoreThanTwentyLengthValidation}")
	private String payerId;

	@NoLessThanThreeLength(message = "memberId {notLessThanThreeLengthValidation}")
	@NoMoreThanThirtyLength(message = "memberId {notMoreThanThirtyLengthValidation}")
	@NoWhiteSpaceCharacter(message = "memberId {noWhiteSpaceCharacterValidation}")
	@NoSpecialCharacterExceptHyphen(message = "memberId {noSpecialCharacterExceptHyphenValidation}")
	private String memberId;

	@NotEmpty(message = "memberName {notEmptyValidation}")
	@NoMoreThan200Length(message = "memberName {noMoreThan200LengthValidation}")
	private String memberName;

	@JsonProperty("IdNumber")
	@NotEmpty(message = "IdNumber {notEmptyValidation}")
	@HasTenCharactersLength(message = "IdNumber {hasTenDigitsValidation}")
	@NoWhiteSpaceCharacter(message = "IdNumber {noWhiteSpaceCharacterValidation}")
	@NoSpecialCharacter(message = "IdNumber {noSpecialCharactersValidation}")
	@IsNumber(message = "IdNumber {notAnumberValidation}")
	private String idNumber;

	@NotEmpty(message = "dateOfBirth {notEmptyValidation}")
	@IsValidDateFormat(message = "dateOfBirth {dateFormatValidation}")
	private String dateOfBirth;

	@NoLessThanThreeLength(message = "policyNumber {notLessThanThreeCharactersLengthValidation}")
	@NoMoreThanFifteenLength(message = "policyNumber {noMoreThanFifteenLengthValidation}")
	@NoWhiteSpaceCharacter(message = "policyNumber {noWhiteSpaceCharacterValidation}")
	private String policyNumber;

	@NotEmpty(message = "memberGender {notEmptyValidation}")
	@IsValidGender(message = "{memberGenderValidation}")
	private String memberGender;

	@Digits(integer = 3, fraction = 2, message = "memberWeight {noMoreThanThreeWithTwoDecimalPrecisionValidation}")
	private BigDecimal memberWeight;

	@Digits(integer = 3, fraction = 2, message = "memberHeight {noMoreThanThreeWithTwoDecimalPrecisionValidation}")
	private BigDecimal memberHeight;

	@NotEmpty(message = "physicianLicenseNumber {notEmptyValidation}")
	@NoMoreThanTwentyLength(message = "physicianLicenseNumber {noMoreThanTwentyLengthValidation}")
	@NoWhiteSpaceCharacter(message = "physicianLicenseNumber {noWhiteSpaceCharacterValidation}")
	@NoSpecialCharacter(message = "physicianLicenseNumber {noSpecialCharactersValidation}")
	private String physicianLicenseNumber;

	@NotEmpty(message = "physicianSpeciality {notEmptyValidation}")
	@NoMoreThan100Length(message = "physicianSpeciality {noMoreThan100LengthValidation}")
	private String physicianSpeciality;

	@NoMoreThan250Length(message = "physicianName {noMoreThan250LengthValidation}")
	private String physicianName;

	@IsValidPhysicianCategory(message = "{physicianCategoryValidation}")
	private String physicianCategory;

	@NotEmpty(message = "diagnosisCodes {notEmptyValidation}")
	@Valid
	public List<DiagnosisCodes> diagnosisCodes = null;

	@NotEmpty(message = "drugList {notEmptyValidation}")
	@Valid
	public List<DrugList> drugList = null;

	@JsonProperty("ePrescriptionReferenceNumber")
	private String ePrescriptionReferenceNumber;

	@Schema(hidden = true)
	private BigDecimal totalPrice;

	@NotEmpty(message = "caseType {notEmptyValidation}")
	@IsValidCaseType(message = "Invalid caseType.")
	private String caseType;

	@Hidden
	public List<DrugListModel> policyConsumptionDrugList;

	@NotEmpty(message = "memberNationality {notEmptyValidation}")
	@NoMoreThan56Length(message = "memberNationality {noMoreThan56LengthValidation}")
	@NoWhiteSpaceCharacterAtEnd(message = "memberNationality {noWhiteSpaceCharacterAtTheEndValidation}")
	@NoWhiteSpaceCharacterAtBeginning(message = "memberNationality {noWhiteSpaceCharacterAtTheBeginningValidation}")
	@NoSpecialCharacter(message = "memberNationality {noSpecialCharactersValidation}")
	private String memberNationality;

	public String getPhysicianSpeciality() {
		return physicianSpeciality;
	}

	public void setPhysicianSpeciality(String physicianSpeciality) {
		this.physicianSpeciality = physicianSpeciality;
	}

	public String getePrescriptionReferenceNumber() {
		return ePrescriptionReferenceNumber;
	}

	public void setePrescriptionReferenceNumber(String ePrescriptionReferenceNumber) {
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
	}

	public PrescriptionRequestModel() {
		super();
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
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

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId == null ? payerId : payerId.trim();
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId == null ? memberId : memberId.trim();
	}

	public BigDecimal getMemberWeight() {
		return memberWeight;
	}

	public void setMemberWeight(BigDecimal memberWeight) {
		this.memberWeight = memberWeight;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth == null ? dateOfBirth : dateOfBirth.trim();
	}

	public List<DrugList> getDrugList() {
		return drugList;
	}

	public void setDrugList(List<DrugList> drugList) {
		this.drugList = drugList;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public List<DiagnosisCodes> getDiagnosisCodes() {
		return diagnosisCodes;
	}

	public void setDiagnosisCodes(List<DiagnosisCodes> diagnosisCodes) {
		this.diagnosisCodes = diagnosisCodes;
	}

	public String getMemberGender() {
		return memberGender;
	}

	public void setMemberGender(String memberGender) {
		this.memberGender = memberGender;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public List<DrugListModel> getPolicyConsumptionDrugList() {
		return policyConsumptionDrugList;
	}

	public void setPolicyConsumptionDrugList(List<DrugListModel> policyConsumptionDrugList) {
		this.policyConsumptionDrugList = policyConsumptionDrugList;
	}

	public String getMemberNationality() {
		return memberNationality;
	}

	public void setMemberNationality(String memberNationality) {
		this.memberNationality = memberNationality;
	}

	public PrescriptionRequestModel(String payerId, String memberId, String idNumber, String dateOfBirth,
			String policyNumber, String memberGender, BigDecimal memberWeight, BigDecimal memberHeight,
			String physicianLicenseNumber, String physicianName, String physicianCategory,
			List<DiagnosisCodes> diagnosisCodes, List<DrugList> drugLists, String memberName, String caseType,
			List<DrugListModel> policyConsumptionDrugList) {
		super();
		this.payerId = payerId;
		this.memberId = memberId;
		this.idNumber = idNumber;
		this.dateOfBirth = dateOfBirth;
		this.policyNumber = policyNumber;
		this.memberGender = memberGender;
		this.memberWeight = memberWeight;
		this.memberHeight = memberHeight;
		this.physicianLicenseNumber = physicianLicenseNumber;
		this.physicianName = physicianName;
		this.physicianCategory = physicianCategory;
		this.diagnosisCodes = diagnosisCodes;
		this.drugList = drugLists;
		this.memberName = memberName;
		this.caseType = caseType;
		this.policyConsumptionDrugList = policyConsumptionDrugList;
	}

}
