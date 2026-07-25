package com.waseel.pbm.pbmadminservice.persist.businessrules;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the SPECIALITY database table.
 * 
 */
@Entity
@Table(name = "SPECIALITY", schema = "PBM_BUSINESS_RULES")
public class Speciality implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SPECIALITY_ID")
	private BigDecimal specialityId;

	@Column(name = "IS_DELETED")
	private Boolean isDeleted;

	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;

	@Column(name = "SPECIALITY_NAME")
	private String specialityName;

	public Speciality() {
	}

	public BigDecimal getSpecialityId() {
		return this.specialityId;
	}

	public void setSpecialityId(BigDecimal specialityId) {
		this.specialityId = specialityId;
	}

	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getSpecialityName() {
		return this.specialityName;
	}

	public void setSpecialityName(String specialityName) {
		this.specialityName = specialityName;
	}
}