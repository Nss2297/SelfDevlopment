package com.waseel.prescription.persist.businessrules;

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

	private static final long serialVersionUID = -7501052781667043880L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "COMMON_DENIALS_ID", nullable = false)
	private Long commonDenialsId;
	@Column(name = "DENIAL_CODE", length = 30, nullable = false)
	private String denialCode;
	@Column(name = "DENIAL_DESCRIPTION", length = 200, nullable = false)
	private String denialDescription;

	public Long getCommonDenialsId() {
		return commonDenialsId;
	}

	public String getDenialCode() {
		return denialCode;
	}

	public String getDenialDescription() {
		return denialDescription;
	}

	public void setCommonDenialsId(Long commonDenialsId) {
		this.commonDenialsId = commonDenialsId;
	}

	public void setDenialCode(String denialCode) {
		this.denialCode = denialCode;
	}

	public void setDenialDescription(String denialDescription) {
		this.denialDescription = denialDescription;
	}

	public CommonDenials() {
		super();
	}

	public CommonDenials(String denialCode, String denialDescription) {
		super();
		this.denialCode = denialCode;
		this.denialDescription = denialDescription;
	}

}
