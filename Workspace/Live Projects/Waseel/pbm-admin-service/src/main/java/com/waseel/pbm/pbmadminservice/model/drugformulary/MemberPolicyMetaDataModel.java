package com.waseel.pbm.pbmadminservice.model.drugformulary;

import javax.validation.constraints.NotEmpty;

import com.waseel.pbm.pbmadminservice.validator.customannotation.IsNumber;
import com.waseel.pbm.pbmadminservice.validator.customannotation.IsValidDateFormat;
import com.waseel.pbm.pbmadminservice.validator.customannotation.NoMoreThan10Length;
import com.waseel.pbm.pbmadminservice.validator.customannotation.NoMoreThan15Length;
import com.waseel.pbm.pbmadminservice.validator.customannotation.NoMoreThan200Length;
import com.waseel.pbm.pbmadminservice.validator.customannotation.NoMoreThan30Length;
import com.waseel.pbm.pbmadminservice.validator.customannotation.NoMoreThan56Length;
import com.waseel.pbm.pbmadminservice.validator.customannotation.NoMoreThan64Length;

public class MemberPolicyMetaDataModel {

	@NotEmpty(message = "memberName {notNullOrEmpty}")
	@NoMoreThan200Length(message = "memberName {noMoreThan200LengthValidation}")
	private String memberName;

	@NotEmpty(message = "idNumber {notNullOrEmpty}")
	@IsNumber(message = "idNumber {onlyAllowDigits}")
	@NoMoreThan15Length(message = "idNumber {noMoreThan15LengthValidation}")
	private String idNumber;

	@NotEmpty(message = "gender {notNullOrEmpty}")
	@NoMoreThan10Length(message = "idNumber {noMoreThan10LengthValidation}")
	private String gender;

	@NotEmpty(message = "dateOfBirth {notNullOrEmpty}")
	@IsValidDateFormat(message = "dateOfBirth {invalidDateFormat}")
	private String dateOfBirth;

	@NoMoreThan30Length(message = "maritalStatus {noMoreThan30LengthValidation}")
	private String maritalStatus;

	@NotEmpty(message = "nationality {notNullOrEmpty}")
	@NoMoreThan56Length(message = "nationality {noMoreThan56LengthValidation}")
	private String nationality;

	@NotEmpty(message = "mobileNumber {notNullOrEmpty}")
	@NoMoreThan15Length(message = "mobileNumber {noMoreThan15LengthValidation}")
	private String mobileNumber;

	@NoMoreThan64Length(message = "email {noMoreThan64LengthValidation}")
	private String email;

	public String getMemberName() {
		return memberName;
	}

	public String getIdNumber() {
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

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public void setIdNumber(String idNumber) {
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

}
