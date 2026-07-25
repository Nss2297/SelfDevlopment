package com.waseel.pbm.idfvalidationservice.persist;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Idfindications entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "IDFDrugToDiagnosisIndications", schema = "MDSS")

public class IdfDrugToDiagnosisIndications implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields
	@EmbeddedId
	@AttributeOverride(name = "icdDiagnosisCode", column = @Column(name = "ICDDiagnosisCode", length = 100))
	@AttributeOverride(name = "oldServiceCode", column = @Column(name = "OldServiceCode", length = 100))
	@AttributeOverride(name = "serviceCode", column = @Column(name = "ServiceCode", length = 100))
	private IdfDrugToDiagnosisIndicationsId id;

	@Column(name = "LastUpdatedDateTime")
	private Timestamp lastUpdatedDateTime;

	@Column(name = "IsDeleted", columnDefinition = "CHAR(1) default ('0')")
	private Character isDeleted = '0';
	// Constructors

	/** default constructor */
	public IdfDrugToDiagnosisIndications() {
	}

	/** full constructor */
	public IdfDrugToDiagnosisIndications(IdfDrugToDiagnosisIndicationsId id) {
		this.id = id;
	}

	// Property accessors
	public IdfDrugToDiagnosisIndicationsId getId() {
		return this.id;
	}

	public void setId(IdfDrugToDiagnosisIndicationsId id) {
		this.id = id;
	}
	
	public Timestamp getLastUpdatedDateTime() {
		return lastUpdatedDateTime;
	}

	public Character getIsDeleted() {
		return isDeleted;
	}

	public void setLastUpdatedDateTime(Timestamp lastUpdatedDateTime) {
		this.lastUpdatedDateTime = lastUpdatedDateTime;
	}

	public void setIsDeleted(Character isDeleted) {
		this.isDeleted = isDeleted;
	}

}