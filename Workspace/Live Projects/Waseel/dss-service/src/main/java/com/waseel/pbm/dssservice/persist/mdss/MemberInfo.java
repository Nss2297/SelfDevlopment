package com.waseel.pbm.dssservice.persist.mdss;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * MemberInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "MemberInfo", schema = "MDSS")

public class MemberInfo implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5246344558813836343L;
	// Fields
	private String requestId;
	private String memberId;
	private String memberGender;
	private String memberWeight;
	private String memberHeight;
	private String dateOfBirth;
	// Constructors
	/** default constructor */
	public MemberInfo() {
	}

	/** minimal constructor */
	public MemberInfo(String id) {
		this.requestId = id;
	}

	/** full constructor */
	public MemberInfo(String id, String memberId,String memberGender, String memberWeight, String memberHeight,
			String dateOfBirth) {
		super();
		this.requestId = id;
		this.memberId = memberId;
		this.memberGender = memberGender;
		this.memberWeight = memberWeight;
		this.memberHeight = memberHeight;
		this.dateOfBirth = dateOfBirth;
	}

	// Property accessors
	@Id
	@Column(name = "RequestId", length = 100)
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	

	@Column(name = "MemberGender", length = 10)
	public String getMemberGender() {
		return this.memberGender;
	}
	
	public void setMemberGender(String memberGender) {
		this.memberGender = memberGender;
	}

	@Column(name = "MemberWeight", length = 30)
	public String getMemberWeight() {
		return this.memberWeight;
	}

	public void setMemberWeight(String memberWeight) {
		this.memberWeight = memberWeight;
	}

	@Column(name = "MemberHeight", length = 30)
	public String getMemberHeight() {
		return this.memberHeight;
	}

	public void setMemberHeight(String memberHeight) {
		this.memberHeight = memberHeight;
	}

	@Column(name = "DateOfBirth", length = 10)
	public String getDateOfBirth() {
		return this.dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	@Column(name = "MemberId", length = 30)
	public String getMemberId() {
		return this.memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	
}