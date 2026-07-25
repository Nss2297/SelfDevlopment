package com.waseel.pbm.pbmadminservice.model.membermanagement;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class MembersResponseModel implements Serializable {

	private static final long serialVersionUID = 5865746283417135461L;

	private String name;

	private Long idNumber;

	private String gender;

	private Date dateOfBirth;

	private String nationality;

	private List<String> errors;

	public String getName() {
		return name;
	}

	public Long getIdNumber() {
		return idNumber;
	}

	public String getGender() {
		return gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public String getNationality() {
		return nationality;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIdNumber(Long idNumber) {
		this.idNumber = idNumber;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public MembersResponseModel() {
		super();
	}

	public MembersResponseModel(String name, Long idNumber, String gender, Date dateOfBirth, String nationality) {
		super();
		this.name = name;
		this.idNumber = idNumber;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.nationality = nationality;
	}

	public MembersResponseModel(List<String> errors) {
		super();
		this.errors = errors;
	}

}