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
@Table(name = "PROVIDER_NETWORK", schema = "PBM_BUSINESS_RULES")
public class ProviderNetwork implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "ProviderNetworkAsscSeq")
	@SequenceGenerator(name = "ProviderNetworkAsscSeq", sequenceName = "PROVIDER_NETWORK_ASSC_SEQ", allocationSize = 0)
	@Column(name = "NETWORK_ID")
	private Long networkId;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@Column(name = "DELETED_BY")
	private String deletedBy;

	@Column(name = "IS_DELETED", nullable = false, columnDefinition = "CHAR(1) default ('0')")
	private Boolean isDeleted;

	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;

	@Column(name = "NETWORK_NAME")
	private String networkName;

	@Column(name = "PAYER_ID")
	private Long payerId;

	public ProviderNetwork() {
	}

	public ProviderNetwork(Long networkId, String createdBy, String deletedBy, Boolean isDeleted, Date lastUpdateDate,
			String networkName, Long payerId) {
		this.networkId = networkId;
		this.createdBy = createdBy;
		this.deletedBy = deletedBy;
		this.isDeleted = isDeleted;
		this.lastUpdateDate = lastUpdateDate;
		this.networkName = networkName;
		this.payerId = payerId;
	}

	public Long getNetworkId() {
		return networkId;
	}

	public void setNetworkId(Long networkId) {
		this.networkId = networkId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getDeletedBy() {
		return deletedBy;
	}

	public void setDeletedBy(String deletedBy) {
		this.deletedBy = deletedBy;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	public Long getPayerId() {
		return payerId;
	}

	public void setPayerId(Long payerId) {
		this.payerId = payerId;
	}
}