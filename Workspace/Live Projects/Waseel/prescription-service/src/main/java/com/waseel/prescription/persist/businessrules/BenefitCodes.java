package com.waseel.prescription.persist.businessrules;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BENEFIT_CODES", schema = "PBM_BUSINESS_RULES")
public class BenefitCodes implements Serializable {

	private static final long serialVersionUID = -6726823890169491887L;

	@Id
	@Column(name = "BENEFIT_CODE_ID", nullable = false)
	private Long benefitCodeId;

	@Column(name = "BENEFIT_CODE_NAME", nullable = false)
	private String benefitCodeName;

	public Long getBenefitCodeId() {
		return benefitCodeId;
	}

	public String getBenefitCodeName() {
		return benefitCodeName;
	}

	public void setBenefitCodeId(Long benefitCodeId) {
		this.benefitCodeId = benefitCodeId;
	}

	public void setBenefitCodeName(String benefitCodeName) {
		this.benefitCodeName = benefitCodeName;
	}

	public BenefitCodes() {
		super();
	}

	public BenefitCodes(Long benefitCodeId, String benefitCodeName) {
		super();
		this.benefitCodeId = benefitCodeId;
		this.benefitCodeName = benefitCodeName;
	}

}
