package com.waseel.drugformulary.persist.businessrules;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "COMMON_DENIALS", schema = "PBM_BUSINESS_RULES")
public class CommonDenials implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "COMMON_DENIALS_ID", nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commonDenialsId;

	@Column(name = "DENIAL_CODE", nullable = false, length = 30, unique = true)
	private String denialCode;

	@Column(name = "DENIAL_DESCRIPTION", nullable = false, length = 200, unique = true)
	private String denialDescription;

	public Long getCommonDenialsId() {
		return commonDenialsId;
	}

	public void setCommonDenialsId(Long commonDenialsId) {
		this.commonDenialsId = commonDenialsId;
	}

	public String getDenialCode() {
		return denialCode;
	}

	public void setDenialCode(String denialCode) {
		this.denialCode = denialCode;
	}

	public String getDenialDescription() {
		return denialDescription;
	}

	public void setDenialDescription(String denialDescription) {
		this.denialDescription = denialDescription;
	}
}
