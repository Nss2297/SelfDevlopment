package com.waseel.pbm.pbmadminservice.persist.mdss;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "IDFDrugToDiagnosisIndications", schema = "MDSS")
@IdClass(IdfDrugToDiagnosisIndicationsId.class)
public class IdfDrugToDiagnosisIndications implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "IDFDrugToDiagnosisIndicationsSEQ")
	@SequenceGenerator(name = "IDFDrugToDiagnosisIndicationsSEQ", sequenceName = "IDFDrugToDiagnosisIndications_SEQ", allocationSize = 0, initialValue = 1)
	@Column(name = "Id")
	private Long id;

	@Id
	@Column(name = "ICDDiagnosisCode", length = 100)
	private String icdDiagnosisCode;

	@Id
	@Column(name = "ServiceCode", length = 100)
	private String serviceCode;

	@Column(name = "OldServiceCode", length = 100)
	private String oldServiceCode;

	@Column(name = "LastUpdatedDateTime")
	private Timestamp lastUpdatedDateTime = Timestamp.from(Instant.now());

	@Column(name = "IsDeleted", columnDefinition = "CHAR(1) default ('0')")
	private Character isDeleted = '0';
	
	public Timestamp getLastUpdatedDateTime() {
		return lastUpdatedDateTime;
	}

	public void setLastUpdatedDateTime(Timestamp lastUpdatedDateTime) {
		this.lastUpdatedDateTime = lastUpdatedDateTime;
	}

	public Character getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Character isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getIcdDiagnosisCode() {
		return icdDiagnosisCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setIcdDiagnosisCode(String iCDDiagnosisCode) {
		this.icdDiagnosisCode = iCDDiagnosisCode.trim();
	}

	public String getServiceCode() {
		return this.serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode.trim();
	}

	public String getOldServiceCode() {
		return this.oldServiceCode;
	}

	public void setOldServiceCode(String oldServiceCode) {
		this.oldServiceCode = oldServiceCode;
	}

	@PreUpdate
	protected void preUpdate() {
		this.lastUpdatedDateTime = Timestamp.from(Instant.now());
	}
}