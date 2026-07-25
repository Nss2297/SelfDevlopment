package com.waseel.policy.persist.businessrules;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the GENERIC_IRREPLICABLE_BRAND database table.
 * 
 */
@Entity
@Table(name = "GENERIC_IRREPLICABLE_BRAND", schema = "PBM_BUSINESS_RULES")
@NamedQuery(name = "GenericIrreplicableBrand.findAll", query = "SELECT g FROM GenericIrreplicableBrand g")
public class GenericIrreplicableBrand implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "GENERIC_IRREPLICABLE_BRAND_ID")
	private long genericIrreplicableBrandId;

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

	public GenericIrreplicableBrand() {
	}

	public long getGenericIrreplicableBrandId() {
		return this.genericIrreplicableBrandId;
	}

	public void setGenericIrreplicableBrandId(long genericIrreplicableBrandId) {
		this.genericIrreplicableBrandId = genericIrreplicableBrandId;
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