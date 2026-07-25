package com.waseel.policy.persist.businessrules;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the "PayerMemberInfo" database table.
 * 
 */
@Entity
@Table(name="\"PayerMemberInfo\"")
@NamedQuery(name="PayerMemberInfo.findAll", query="SELECT p FROM PayerMemberInfo p")
public class PayerMemberInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private long id;

	@Temporal(TemporalType.DATE)
	@Column(name="\"CancelledDate\"")
	private Date cancelledDate;

	@Temporal(TemporalType.DATE)
	private Date dob;

	@Column(name="\"Email\"")
	private String email;

	@Column(name="\"Gender\"")
	private String gender;

	@Column(name="\"IDNumber\"")
	private BigDecimal IDNumber;

	@Column(name="\"IsCancelled\"")
	private String isCancelled;

	@Temporal(TemporalType.DATE)
	@Column(name="\"IssueDate\"")
	private Date issueDate;

	@Column(name="\"MemberID\"")
	private String memberID;

	@Column(name="\"MemberName\"")
	private String memberName;

	@Column(name="\"MobileNumber\"")
	private String mobileNumber;

	@Column(name="\"Nationality\"")
	private String nationality;

	@Column(name="\"PayerId\"")
	private String payerId;

	@Column(name="\"Status\"")
	private String status;

	public PayerMemberInfo() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCancelledDate() {
		return this.cancelledDate;
	}

	public void setCancelledDate(Date cancelledDate) {
		this.cancelledDate = cancelledDate;
	}

	public Date getDob() {
		return this.dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public BigDecimal getIDNumber() {
		return this.IDNumber;
	}

	public void setIDNumber(BigDecimal IDNumber) {
		this.IDNumber = IDNumber;
	}

	public String getIsCancelled() {
		return this.isCancelled;
	}

	public void setIsCancelled(String isCancelled) {
		this.isCancelled = isCancelled;
	}

	public Date getIssueDate() {
		return this.issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public String getMemberID() {
		return this.memberID;
	}

	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}

	public String getMemberName() {
		return this.memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMobileNumber() {
		return this.mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getNationality() {
		return this.nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getPayerId() {
		return this.payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}