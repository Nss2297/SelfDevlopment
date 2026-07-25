package com.waseel.policy.persist.businessrules;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the COMMON_DENIALS database table.
 * 
 */
@Entity
@Table(name="COMMON_DENIALS")
@NamedQuery(name="CommonDenial.findAll", query="SELECT c FROM CommonDenial c")
public class CommonDenial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="COMMON_DENIALS_ID")
	private long commonDenialsId;

	@Column(name="DENIAL_CODE")
	private String denialCode;

	@Column(name="DENIAL_DESCRIPTION")
	private String denialDescription;

	public CommonDenial() {
	}

	public long getCommonDenialsId() {
		return this.commonDenialsId;
	}

	public void setCommonDenialsId(long commonDenialsId) {
		this.commonDenialsId = commonDenialsId;
	}

	public String getDenialCode() {
		return this.denialCode;
	}

	public void setDenialCode(String denialCode) {
		this.denialCode = denialCode;
	}

	public String getDenialDescription() {
		return this.denialDescription;
	}

	public void setDenialDescription(String denialDescription) {
		this.denialDescription = denialDescription;
	}

}