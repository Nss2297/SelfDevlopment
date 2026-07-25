package com.waseel.pbm.pbmadminservice.persist.businessrules;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PROVIDER_EXCLUSION_ASSC", schema = "PBM_BUSINESS_RULES")
public class ProviderExclusionAssc implements Serializable {

	private static final long serialVersionUID = -5796224986187087792L;

	@Id
	@GeneratedValue(generator = "ProviderExclusionAsscSeq")
	@SequenceGenerator(name = "ProviderExclusionAsscSeq", sequenceName = "PROVIDER_EXCLUSION_ASSC_SEQ", allocationSize = 0)
	@Column(name = "PROVIDER_EXCLUSION_ASSC_ID", nullable = false, updatable = false)
	private Long providerExclusionAsscId;

	@Column(name = "PROVIDER_ID", nullable = false)
	private Long providerId;

	@Column(name = "EXCLUSION_ID", nullable = false)
	private Long exclusionId;

	@Column(name = "PROVIDER_NAME", nullable = false, length = 100)
	private String providerName;

	@Column(name = "IS_ENABLED", nullable = false)
	private Boolean isEnabled = true;

	@Column(name = "PAYER_ID", nullable = false)
	private Long payerId;

	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;

	public Long getProviderExclusionAsscId() {
		return providerExclusionAsscId;
	}

	public Long getProviderId() {
		return providerId;
	}

	public Long getExclusionId() {
		return exclusionId;
	}

	public String getProviderName() {
		return providerName;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setProviderExclusionAsscId(Long providerExclusionAsscId) {
		this.providerExclusionAsscId = providerExclusionAsscId;
	}

	public void setProviderId(Long providerId) {
		this.providerId = providerId;
	}

	public void setExclusionId(Long exclusionId) {
		this.exclusionId = exclusionId;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Long getPayerId() {
		return payerId;
	}

	public void setPayerId(Long payerId) {
		this.payerId = payerId;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public ProviderExclusionAssc() {
		super();
	}

	public ProviderExclusionAssc(Long providerExclusionAsscId, Long providerId, Long exclusionId, String providerName,
			Boolean isEnabled, Long payerId) {
		super();
		this.providerExclusionAsscId = providerExclusionAsscId;
		this.providerId = providerId;
		this.exclusionId = exclusionId;
		this.providerName = providerName;
		this.isEnabled = isEnabled;
		this.payerId = payerId;
	}

	public ProviderExclusionAssc(Long providerId, Long exclusionId, String providerName, Boolean isEnabled,
			Long payerId) {
		super();
		this.providerId = providerId;
		this.exclusionId = exclusionId;
		this.providerName = providerName;
		this.isEnabled = isEnabled;
		this.payerId = payerId;
	}
	
	public ProviderExclusionAssc(Long providerId, Long exclusionId, String providerName, Long payerId,
			Date lastUpdateDate) {
		this.providerId = providerId;
		this.exclusionId = exclusionId;
		this.providerName = providerName;
		this.payerId = payerId;
		this.lastUpdateDate = lastUpdateDate;
	}
}
