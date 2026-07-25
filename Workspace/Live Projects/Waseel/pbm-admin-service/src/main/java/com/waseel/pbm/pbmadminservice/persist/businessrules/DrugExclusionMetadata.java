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
@Table(name = "DRUG_EXCLUSION_METADATA", schema = "PBM_BUSINESS_RULES")
public class DrugExclusionMetadata implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7395861637313350032L;

	@Id
	@GeneratedValue(generator = "MetadataSeq")
	@SequenceGenerator(name = "MetadataSeq", sequenceName = "DRUG_EXCLUSION_METADATA_SEQ", allocationSize = 0)
	@Column(name = "EXCLUSION_ID", nullable = false, updatable = false, unique = true)
	private Long exclusionId;

	@Column(name = "PAYER_ID", nullable = false, unique = true, length = 100)
	private Long payerId;

	@Column(name = "EXCLUSION_NAME", nullable = false, unique = true, length = 100)
	private String exclusionName;
	
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "CREATED_BY", nullable = false, length = 30)
	private String createdBy;
	
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_UPDATE_DATE", nullable = false)
	private Date lastUpdateDate;

	@Column(name = "IS_DELETED", nullable = false)
	private Boolean isDeleted;

	@Column(name = "DELETED_BY", nullable = false, length = 30)
	private String deletedBy;

	public Long getExclusionId() {
		return exclusionId;
	}

	public void setExclusionId(Long exclusionId) {
		this.exclusionId = exclusionId;
	}

	public String getExclusionName() {
		return exclusionName;
	}

	public void setExclusionName(String exclusionName) {
		this.exclusionName = exclusionName;
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

	public Long getPayerId() {
		return payerId;
	}

	public void setPayerId(Long payerId) {
		this.payerId = payerId;
	}

	public DrugExclusionMetadata(Long exclusionId, Long payerId, String exclusionName, Date createdDate,
			String createdBy, Date lastUpdateDate, Boolean isDeleted, String deletedBy) {
		this.exclusionId = exclusionId;
		this.payerId = payerId;
		this.exclusionName = exclusionName;
		this.createdDate = createdDate;
		this.createdBy = createdBy;
		this.lastUpdateDate = lastUpdateDate;
		this.isDeleted = isDeleted;
		this.deletedBy = deletedBy;
	}

	public DrugExclusionMetadata() {
		super();
	}
}
