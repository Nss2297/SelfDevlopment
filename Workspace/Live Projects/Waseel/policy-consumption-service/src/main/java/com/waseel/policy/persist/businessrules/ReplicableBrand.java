package com.waseel.policy.persist.businessrules;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the REPLICABLE_BRAND database table.
 * 
 */
@Entity
@Table(name = "REPLICABLE_BRAND", schema = "PBM_BUSINESS_RULES")
@NamedQuery(name = "ReplicableBrand.findAll", query = "SELECT r FROM ReplicableBrand r")
public class ReplicableBrand implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "REPLICABLE_BRAND_ID")
	private long replicableBrandId;

	@Column(name = "DELETED_BY")
	private String deletedBy;

	@Column(name = "DRUGCODE")
	private String drugcode;

	@Column(name = "IS_DELETED")
	private String isDeleted;

	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;

	@Column(name = "PAYERID")
	private BigDecimal payerid;

	@Column(name = "TRADE_NAME")
	private String tradeName;

	public ReplicableBrand() {
	}

	public long getReplicableBrandId() {
		return this.replicableBrandId;
	}

	public void setReplicableBrandId(long replicableBrandId) {
		this.replicableBrandId = replicableBrandId;
	}

	public String getDeletedBy() {
		return this.deletedBy;
	}

	public void setDeletedBy(String deletedBy) {
		this.deletedBy = deletedBy;
	}

	public String getDrugcode() {
		return this.drugcode;
	}

	public void setDrugcode(String drugcode) {
		this.drugcode = drugcode;
	}

	public String getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public BigDecimal getPayerid() {
		return this.payerid;
	}

	public void setPayerid(BigDecimal payerid) {
		this.payerid = payerid;
	}

	public String getTradeName() {
		return this.tradeName;
	}

	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}

}