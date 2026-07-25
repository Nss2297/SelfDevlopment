package com.waseel.pbm.pbmadminservice.persist.businessrules;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "NETWORK_EXCLUSION_ASSC", schema = "PBM_BUSINESS_RULES")
public class NetworkExclusionAssc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "NetworkExclusionAsscSeq")
	@SequenceGenerator(name = "NetworkExclusionAsscSeq", sequenceName = "NETWORK_EXCLUSION_ASSC_SEQ", allocationSize = 0)
	@Column(name = "NETWORK_EXCLUSION_ASSC_ID")
	private Long networkExclusionAsscId;

	@Column(name = "NETWORK_ID")
	private Long networkId;

	@Column(name = "EXCLUSION_ID")
	private Long exclusionId;

	@Column(name = "IS_ENABLED", nullable = false, columnDefinition = "CHAR(1) default ('1')")
	private Boolean isEnabled = true;

	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;

	public NetworkExclusionAssc() {

	}

	public NetworkExclusionAssc(Long networkExclusionAsscId, Long networkId, Long exclusionId, Date lastUpdateDate) {
		this.networkExclusionAsscId = networkExclusionAsscId;
		this.networkId = networkId;
		this.exclusionId = exclusionId;
		this.lastUpdateDate = lastUpdateDate;
	}

	public NetworkExclusionAssc(Long networkId, Long exclusionId, Date lastUpdateDate) {
		this.networkId = networkId;
		this.exclusionId = exclusionId;
		this.lastUpdateDate = lastUpdateDate;
	}

	public Long getNetworkExclusionAsscId() {
		return networkExclusionAsscId;
	}

	public void setNetworkExclusionAsscId(Long networkExclusionAsscId) {
		this.networkExclusionAsscId = networkExclusionAsscId;
	}

	public Long getExclusionId() {
		return exclusionId;
	}

	public void setExclusionId(Long exclusionId) {
		this.exclusionId = exclusionId;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Long getNetworkId() {
		return networkId;
	}

	public void setNetworkId(Long networkId) {
		this.networkId = networkId;
	}
}