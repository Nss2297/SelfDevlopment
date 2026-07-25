package com.waseel.drugexclusionvalidationservice.persist.businessrules;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the PROVIDER_NETWORK_ASSC database table.
 * 
 */
@Entity
@Table(name="PROVIDER_NETWORK_ASSC")
@NamedQuery(name="ProviderNetworkAssc.findAll", query="SELECT p FROM ProviderNetworkAssc p")
public class ProviderNetworkAssc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "ProviderNetworkAsscSeq")
	@SequenceGenerator(name = "ProviderNetworkAsscSeq", sequenceName = "PROVIDER_NETWORK_ASSC_SEQ", allocationSize = 0)
	@Column(name="PROVIDER_NETWORK_ASSC_ID")
	private long providerNetworkAsscId;

	@Column(name="IS_ENABLED")
	private Boolean isEnabled;

	@Temporal(TemporalType.DATE)
	@Column(name="LAST_UPDATE_DATE")
	private Date lastUpdateDate;

	@Column(name="PROVIDER_ID")
	private BigDecimal providerId;

	//bi-directional many-to-one association to ProviderNetwork
	@ManyToOne
	@JoinColumn(name="NETWORK_ID")
	private ProviderNetwork providerNetwork;

	public ProviderNetworkAssc() {
	}

	public long getProviderNetworkAsscId() {
		return this.providerNetworkAsscId;
	}

	public void setProviderNetworkAsscId(long providerNetworkAsscId) {
		this.providerNetworkAsscId = providerNetworkAsscId;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public BigDecimal getProviderId() {
		return this.providerId;
	}

	public void setProviderId(BigDecimal providerId) {
		this.providerId = providerId;
	}

	public ProviderNetwork getProviderNetwork() {
		return this.providerNetwork;
	}

	public void setProviderNetwork(ProviderNetwork providerNetwork) {
		this.providerNetwork = providerNetwork;
	}

}