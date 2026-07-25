package com.waseel.pbm.fdbvalidationservice.persist.mdss;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "FDBPediatricAgeSeverityLevel", schema = "MDSS")
public class FDBPediatricAgeSeverityLevel implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    @AttributeOverride(name = "serviceCode", column = @Column(name = "ServiceCode"))
    @AttributeOverride(name = "payerId", column = @Column(name = "PayerId"))
    private FDBPediatricAgeSeverityLevelId levelId;
    @Column(name = "PediatricAgeSeverityLevel")
    private String pediatricAgeSeverityLevel;
    @Column(name = "IsDeleted")
    private Character isDeleted = '0';
    @Column(name = "LastUpdatedDateTime")
    private Timestamp lastUpdatedDateTime = Timestamp.from(Instant.now());
    @Column(name = "Id")
    private Long id;

    public FDBPediatricAgeSeverityLevelId getLevelId() {
        return this.levelId;
    }

    public void setLevelId(FDBPediatricAgeSeverityLevelId levelId) {
        this.levelId = levelId;
    }

    public String getPediatricAgeSeverityLevel() {
        return pediatricAgeSeverityLevel;
    }

    public void setPediatricAgeSeverityLevel(String pediatricAgeSeverityLevel) {
        this.pediatricAgeSeverityLevel = pediatricAgeSeverityLevel;
    }

    public Character getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Character isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Timestamp getLastUpdatedDateTime() {
        return lastUpdatedDateTime;
    }

    public void setLastUpdatedDateTime(Timestamp lastUpdatedDateTime) {
        this.lastUpdatedDateTime = lastUpdatedDateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
