package com.waseel.pbm.pbmadminservice.persist.businessrules;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the EXCLUSION_ASSC_TYPE_LIST database table.
 * 
 */
@Entity
@Table(name = "EXCLUSION_ASSC_TYPE_LIST", schema = "PBM_BUSINESS_RULES")
public class ExclusionAsscTypeList implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "ExcListSeq")
	@SequenceGenerator(name = "ExcListSeq", sequenceName = "EXC_ASS_SEQ", allocationSize = 0)
	@Column(name = "EXC_LIST_ID")
	private Long excListId;

	@Column(name = "EXCLUSION_ASSC_ID")
	private Long exclusionAsscId;

	@Column(name = "EXCLUSION_ID")
	private Long exclusionId;

	@Column(name = "EXCLUSION_TYPE")
	private String exclusionType;

	@Column(name = "EXCLUSION_TYPE_NAME")
	private String exclusionTypeName;

	@Column(name = "NETWORK_ID")
	private Long networkId;

	@Column(name = "PAYER_ID")
	private Long payerId;

	@Column(name = "PROVIDER_ID")
	private Long providerId;

	@Column(name = "SPECIALITY_ID")
	private BigDecimal specialityId;

	public ExclusionAsscTypeList() {
	}

	public Long getExcListId() {
		return this.excListId;
	}

	public void setExcListId(Long excListId) {
		this.excListId = excListId;
	}

	public String getExclusionType() {
		return this.exclusionType;
	}

	public void setExclusionType(String exclusionType) {
		this.exclusionType = exclusionType;
	}

	public String getExclusionTypeName() {
		return this.exclusionTypeName;
	}

	public void setExclusionTypeName(String exclusionTypeName) {
		this.exclusionTypeName = exclusionTypeName;
	}

	public Long getExclusionAsscId() {
		return exclusionAsscId;
	}

	public void setExclusionAsscId(Long exclusionAsscId) {
		this.exclusionAsscId = exclusionAsscId;
	}

	public Long getExclusionId() {
		return exclusionId;
	}

	public void setExclusionId(Long exclusionId) {
		this.exclusionId = exclusionId;
	}

	public Long getNetworkId() {
		return networkId;
	}

	public void setNetworkId(Long networkId) {
		this.networkId = networkId;
	}

	public Long getPayerId() {
		return payerId;
	}

	public void setPayerId(Long payerId) {
		this.payerId = payerId;
	}

	public Long getProviderId() {
		return providerId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	public BigDecimal getSpecialityId() {
		return specialityId;
	}

	public void setSpecialityId(BigDecimal specialityId) {
		this.specialityId = specialityId;
	}

}