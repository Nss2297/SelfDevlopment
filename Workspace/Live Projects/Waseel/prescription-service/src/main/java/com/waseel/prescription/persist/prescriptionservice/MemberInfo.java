package com.waseel.prescription.persist.prescriptionservice;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "MemberInfo", schema = "PRESCRIPTION_SERVICE")
@NamedEntityGraphs({ @NamedEntityGraph(name = "SummaryInquiry", attributeNodes = {
		@NamedAttributeNode(value = "prescriptionRequest") }) })
public class MemberInfo implements Serializable {

	private static final long serialVersionUID = 2L;

	@Id
	@GeneratedValue(generator = "PsMemberInfoSeq")
	@SequenceGenerator(name = "PsMemberInfoSeq", sequenceName = "PS_MemberInfo_Seq", allocationSize = 0, initialValue = 1)
	@Column(name = "ID", nullable = false)
	private long id;

	@Column(name = "MemberID", length = 50)
	private String memberId;

	@Column(name = "MemberName", length = 200, nullable = false)
	private String memberName;

	@Column(name = "IDNumber")
	private long idNumber;

	@Column(name = "PolicyNumber", length = 50)
	private String policyNumber;

	@Column(name = "DOB")
	private Date dob;

	@Column(name = "Weight", precision = 2)
	private Double weight;

	@Column(name = "Height", precision = 2)
	private Double height;

	@Column(name = "Gender", length = 10)
	private String gender;

	@Column(name = "RequestID", length = 100, updatable = false)
	private String requestId;

	@Column(name = "Nationality", length = 56, updatable = false)
	private String nationality;

	@OneToMany(mappedBy = "memberInfo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<PrescriptionRequest> prescriptionRequest;

	public Set<PrescriptionRequest> getPrescriptionRequest() {
		return prescriptionRequest;
	}

	public void setPrescriptionRequest(Set<PrescriptionRequest> prescriptionRequest) {
		this.prescriptionRequest = prescriptionRequest;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Long getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(long idNumber) {
		this.idNumber = idNumber;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public MemberInfo(String memberId, long iDNumber, String policyNumber, Date dob, Double weight, Double height,
			String gender, String requestId, String memberName, String nationality) {
		super();
		this.memberId = memberId;
		this.idNumber = iDNumber;
		this.policyNumber = policyNumber;
		this.dob = dob;
		this.weight = weight;
		this.height = height;
		this.gender = gender;
		this.requestId = requestId;
		this.memberName = memberName;
		this.nationality = nationality;
	}

	public MemberInfo() {
		super();
	}

}