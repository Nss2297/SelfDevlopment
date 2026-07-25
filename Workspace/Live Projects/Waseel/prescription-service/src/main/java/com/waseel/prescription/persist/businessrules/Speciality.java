package com.waseel.prescription.persist.businessrules;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "SPECIALITY", schema = "PBM_BUSINESS_RULES")
public class Speciality implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "SPECIALITY_ID", nullable = false, updatable = false)
    private BigDecimal specialityId;

    @Column(name = "SPECIALITY_NAME", nullable = false, unique = true, length = 100)
    private String specialityName;

    @Column(name = "LAST_UPDATE_DATE", nullable = false)
    private Date lastUpdateDate;

    @Column(name = "IS_DELETED", nullable = false, columnDefinition = "CHAR(1) default ('0')")
    private Boolean isDeleted = false;

    public BigDecimal getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(BigDecimal specialityId) {
        this.specialityId = specialityId;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

	public Speciality() {
		super();
	}

	public Speciality(BigDecimal specialityId, String specialityName, Date lastUpdateDate, Boolean isDeleted) {
		super();
		this.specialityId = specialityId;
		this.specialityName = specialityName;
		this.lastUpdateDate = lastUpdateDate;
		this.isDeleted = isDeleted;
	}
}
