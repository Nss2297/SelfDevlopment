package com.waseel.drugexclusionvalidationservice.persist.businessrules;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "SPECIALITY_EXCLUSION_ASSC", schema = "PBM_BUSINESS_RULES")
public class SpecialityExclusionAssc implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "SpecialityExclusionAsscSeq")
    @SequenceGenerator(name = "SpecialityExclusionAsscSeq", sequenceName = "SPECIALITY_EXCLUSION_ASSC_SEQ", allocationSize = 0)
    @Column(name = "SPECIALITY_EXCLUSION_ASSC_ID", nullable = false, updatable = false)
    private Long specialityExclusionAsscId;

    @Column(name = "SPECIALITY_ID", nullable = false)
    private BigDecimal specialityId;

    @Column(name = "EXCLUSION_ID", nullable = false)
    private Long exclusionId;

    @Column(name = "LAST_UPDATE_DATE", nullable = false)
    private Date lastUpdateDate;

    @Column(name = "IS_ENABLED", nullable = false, columnDefinition = "CHAR(1) default ('1')")
    private Boolean isEnabled = true;

    public Long getSpecialityExclusionAsscId() {
        return specialityExclusionAsscId;
    }

    public void setSpecialityExclusionAsscId(Long specialityExclusionAsscId) {
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
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public SpecialityExclusionAssc() {
    }

    public SpecialityExclusionAssc(Long specialityExclusionAsscId, BigDecimal specialityId, Long exclusionId,
                                   Date lastUpdateDate, Boolean isEnabled) {
        this.specialityExclusionAsscId = specialityExclusionAsscId;
        this.specialityId = specialityId;
        this.exclusionId = exclusionId;
        this.lastUpdateDate = lastUpdateDate;
        this.isEnabled = isEnabled;
    }
}
