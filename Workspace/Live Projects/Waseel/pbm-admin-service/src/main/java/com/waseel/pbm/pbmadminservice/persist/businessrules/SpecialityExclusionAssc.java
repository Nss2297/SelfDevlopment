package com.waseel.pbm.pbmadminservice.persist.businessrules;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the SPECIALITY_EXCLUSION_ASSC database table.
 */
@Entity
@Table(name = "SPECIALITY_EXCLUSION_ASSC", schema = "PBM_BUSINESS_RULES")
@NamedQuery(name = "SpecialityExclusionAssc.findAll", query = "SELECT s FROM SpecialityExclusionAssc s")
public class SpecialityExclusionAssc implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "SPECIALITY_EXCLUSION_ASSC_ID")
    @GeneratedValue(generator = "SpecialityExclusionAsscSeq")
    @SequenceGenerator(name = "SpecialityExclusionAsscSeq", sequenceName = "SPECIALITY_EXCLUSION_ASSC_SEQ", allocationSize = 0)
    private long specialityExclusionAsscId;

    @Column(name = "SPECIALITY_ID", nullable = false)
    private BigDecimal specialityId;

    @Column(name = "EXCLUSION_ID", nullable = false)
    private Long exclusionId;

    @Column(name = "IS_ENABLED")
    private Boolean isEnabled = true;

    public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Temporal(TemporalType.DATE)
    @Column(name = "LAST_UPDATE_DATE")
    private Date lastUpdateDate;

    public SpecialityExclusionAssc() {
    }

    public long getSpecialityExclusionAsscId() {
        return this.specialityExclusionAsscId;
    }

    public void setSpecialityExclusionAsscId(long specialityExclusionAsscId) {
        this.specialityExclusionAsscId = specialityExclusionAsscId;
    }

    public BigDecimal getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(BigDecimal specialityId) {
        this.specialityId = specialityId;
    }

    public Long getExclusionId() {
        return exclusionId;
    }

    public void setExclusionId(Long exclusionId) {
        this.exclusionId = exclusionId;
    }


    public Date getLastUpdateDate() {
        return this.lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public SpecialityExclusionAssc(long specialityExclusionAsscId, BigDecimal specialityId, Long exclusionId,
                                   Boolean isEnabled, Date lastUpdateDate) {
        super();
        this.specialityExclusionAsscId = specialityExclusionAsscId;
        this.specialityId = specialityId;
        this.exclusionId = exclusionId;
        this.isEnabled = isEnabled;
        this.lastUpdateDate = lastUpdateDate;
    }

}