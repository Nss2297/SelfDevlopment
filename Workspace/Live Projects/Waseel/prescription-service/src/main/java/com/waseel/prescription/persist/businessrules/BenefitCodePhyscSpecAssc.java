package com.waseel.prescription.persist.businessrules;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "BENEFIT_CODE_PHYSC_SPEC_ASSC", schema = "PBM_BUSINESS_RULES")
public class BenefitCodePhyscSpecAssc implements Serializable {

	private static final long serialVersionUID = -9159309118038982344L;

	@Id
	@Column(name = "BENEFIT_CODE_PHYSC_SPEC_ASSC_ID", nullable = false)
	private Long benefitCodePhyscSpecAsscId;

	@Column(name = "SPECIALITY_ID", nullable = false)
	private BigDecimal specialityId;

	@Column(name = "BENEFIT_CODE_ID", nullable = false)
	private Long benefitCodeId;

	@Column(name = "IS_ENABLED", nullable = false, columnDefinition = "CHAR(1) default ('0')")
	private Boolean isEnabled;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BENEFIT_CODE_ID", referencedColumnName = "BENEFIT_CODE_ID", insertable = false, updatable = false)
	private BenefitCodes benefitCodes;

	public Long getBenefitCodePhyscSpecAsscId() {
		return benefitCodePhyscSpecAsscId;
	}

	public BigDecimal getSpecialityId() {
		return specialityId;
	}

	public Long getBenefitCodeId() {
		return benefitCodeId;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setBenefitCodePhyscSpecAsscId(Long benefitCodePhyscSpecAsscId) {
		this.benefitCodePhyscSpecAsscId = benefitCodePhyscSpecAsscId;
	}

	public void setSpecialityId(BigDecimal specialityId) {
		this.specialityId = specialityId;
	}

	public void setBenefitCodeId(Long benefitCodeId) {
		this.benefitCodeId = benefitCodeId;
	}

	public BenefitCodes getBenefitCodes() {
		return benefitCodes;
	}

	public void setBenefitCodes(BenefitCodes benefitCodes) {
		this.benefitCodes = benefitCodes;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public BenefitCodePhyscSpecAssc() {
		super();
	}

	public BenefitCodePhyscSpecAssc(Long benefitCodePhyscSpecAsscId, BigDecimal specialityId, Long benefitCodeId,
			Boolean isEnabled) {
		super();
		this.benefitCodePhyscSpecAsscId = benefitCodePhyscSpecAsscId;
		this.specialityId = specialityId;
		this.benefitCodeId = benefitCodeId;
		this.isEnabled = isEnabled;
	}

}
