package com.waseel.drugexclusionvalidationservice.persist.businessrules;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the NETWORK_EXCLUSION_ASSC database table.
 * 
 */
@Entity
@Table(name = "NETWORK_EXCLUSION_ASSC")
@NamedQuery(name = "NetworkExclusionAssc.findAll", query = "SELECT n FROM NetworkExclusionAssc n")
public class NetworkExclusionAssc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "NetworkExclusionAsscSeq")
	@SequenceGenerator(name = "NetworkExclusionAsscSeq", sequenceName = "NETWORK_EXCLUSION_ASSC_SEQ", allocationSize = 0)
	@Column(name = "NETWORK_EXCLUSION_ASSC_ID")
	private long networkExclusionAsscId;

	@Column(name = "EXCLUSION_ID")
	private java.math.BigDecimal exclusionId;

	@Column(name = "IS_ENABLED")
	private Boolean isEnabled = true;

	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;

	// bi-directional many-to-one association to ProviderNetwork
	@ManyToOne
	@JoinColumn(name = "NETWORK_ID")
	private ProviderNetwork providerNetwork;

	public NetworkExclusionAssc() {
	}

	public long getNetworkExclusionAsscId() {
		return this.networkExclusionAsscId;
	}

	public void setNetworkExclusionAsscId(long networkExclusionAsscId) {
		this.networkExclusionAsscId = networkExclusionAsscId;
	}

	public java.math.BigDecimal getExclusionId() {
		return this.exclusionId;
	}

	public void setExclusionId(java.math.BigDecimal exclusionId) {
		this.exclusionId = exclusionId;
	}

	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public ProviderNetwork getProviderNetwork() {
		return this.providerNetwork;
	}

	public void setProviderNetwork(ProviderNetwork providerNetwork) {
		this.providerNetwork = providerNetwork;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
}