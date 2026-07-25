package com.waseel.pbm.pbmadminservice.persist.businessrules;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "DRUG_FORMULARY_METADATA", schema = "PBM_BUSINESS_RULES")
public class DrugFormularyMetadata implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "Seq")
    @SequenceGenerator(name = "Seq", sequenceName = "DRUG_FORMULARY_METADATA_SEQ", allocationSize = 0)
    @Column(name = "FORMULARY_ID", nullable = false, updatable = false)
    private Long formularyId;

    @Column(name = "PAYER_ID", nullable = false, unique = true, length = 20)
    private String payerId;

    @Column(name = "FORMULARY_NAME", nullable = false, unique = true, length = 100)
    private String formularyName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "CREATED_BY", nullable = false, length = 30)
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UPDATE_DATE", nullable = false)
    private Date lastUpdateDate;

    @Column(name = "IS_DELETED", nullable = false, columnDefinition = "CHAR(1) default ('0')")
    private Boolean isDeleted = false;

    @Column(name = "DELETED_BY", nullable = false, length = 30)
    private String deletedBy;
    
    public DrugFormularyMetadata() {
	}

	public DrugFormularyMetadata(Long formularyId, String payerId, String formularyName, Date createdDate,
			String createdBy, Date lastUpdateDate, Boolean isDeleted, String deletedBy) {
		this.formularyId = formularyId;
		this.payerId = payerId;
		this.formularyName = formularyName;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.lastUpdateDate = lastUpdateDate;
		this.isDeleted = isDeleted;
		this.deletedBy = deletedBy;
	}



	public Long getFormularyId() {
        return formularyId;
    }

    public void setFormularyId(Long formularyId) {
        this.formularyId = formularyId;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getFormularyName() {
        return formularyName;
    }

    public void setFormularyName(String formularyName) {
        this.formularyName = formularyName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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

    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }
}
