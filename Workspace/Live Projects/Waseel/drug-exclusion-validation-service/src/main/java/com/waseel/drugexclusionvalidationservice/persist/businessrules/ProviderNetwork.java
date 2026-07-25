package com.waseel.drugexclusionvalidationservice.persist.businessrules;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the PROVIDER_NETWORK database table.
 * 
 */
@Entity
@Table(name="PROVIDER_NETWORK")
@NamedQuery(name="ProviderNetwork.findAll", query="SELECT p FROM ProviderNetwork p")
public class ProviderNetwork implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "ProviderNetworkSeq")
	@SequenceGenerator(name = "ProviderNetworkSeq", sequenceName = "PROVIDER_NETWORK_SEQ", allocationSize = 0)
	@Column(name="NETWORK_ID")
	private long networkId;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="DELETED_BY")
	private String deletedBy;

	@Column(name="IS_DELETED")
	private Boolean isDeleted;

	@Temporal(TemporalType.DATE)
	@Column(name="LAST_UPDATE_DATE")
	private Date lastUpdateDate;

	@Column(name="NETWORK_NAME")
	private String networkName;

	@Column(name="PAYER_ID")
	private BigDecimal payerId;

	//bi-directional many-to-one association to NetworkExclusionAssc
	@OneToMany(mappedBy="providerNetwork")
	private List<NetworkExclusionAssc> networkExclusionAsscs;

	//bi-directional many-to-one association to ProviderNetworkAssc
	@OneToMany(mappedBy="providerNetwork")
	private List<ProviderNetworkAssc> providerNetworkAsscs;

	public ProviderNetwork() {
	}

	public long getNetworkId() {
		return this.networkId;
	}

	public void setNetworkId(long networkId) {
		this.networkId = networkId;
	}

	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getDeletedBy() {
		return this.deletedBy;
	}

	public void setDeletedBy(String deletedBy) {
		this.deletedBy = deletedBy;
	}

	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getNetworkName() {
		return this.networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	public BigDecimal getPayerId() {
		return this.payerId;
	}

	public void setPayerId(BigDecimal payerId) {
		this.payerId = payerId;
	}

	public List<NetworkExclusionAssc> getNetworkExclusionAsscs() {
		return this.networkExclusionAsscs;
	}

	public void setNetworkExclusionAsscs(List<NetworkExclusionAssc> networkExclusionAsscs) {
		this.networkExclusionAsscs = networkExclusionAsscs;
	}

	public NetworkExclusionAssc addNetworkExclusionAssc(NetworkExclusionAssc networkExclusionAssc) {
		getNetworkExclusionAsscs().add(networkExclusionAssc);
		networkExclusionAssc.setProviderNetwork(this);

		return networkExclusionAssc;
	}

	public NetworkExclusionAssc removeNetworkExclusionAssc(NetworkExclusionAssc networkExclusionAssc) {
		getNetworkExclusionAsscs().remove(networkExclusionAssc);
		networkExclusionAssc.setProviderNetwork(null);

		return networkExclusionAssc;
	}

	public List<ProviderNetworkAssc> getProviderNetworkAsscs() {
		return this.providerNetworkAsscs;
	}

	public void setProviderNetworkAsscs(List<ProviderNetworkAssc> providerNetworkAsscs) {
		this.providerNetworkAsscs = providerNetworkAsscs;
	}

	public ProviderNetworkAssc addProviderNetworkAssc(ProviderNetworkAssc providerNetworkAssc) {
		getProviderNetworkAsscs().add(providerNetworkAssc);
		providerNetworkAssc.setProviderNetwork(this);

		return providerNetworkAssc;
	}

	public ProviderNetworkAssc removeProviderNetworkAssc(ProviderNetworkAssc providerNetworkAssc) {
		getProviderNetworkAsscs().remove(providerNetworkAssc);
		providerNetworkAssc.setProviderNetwork(null);

		return providerNetworkAssc;
	}

}