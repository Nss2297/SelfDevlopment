package com.waseel.pbm.dssservice.persist.mdss;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Icd10info entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "ICDDiagnosisInfo", schema = "MDSS")

public class IcdDiagnosisInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5810872623909942789L;
	private IcdDiagnosisInfoId id;
	private Character isDeletedFromProvider = '0';

	/** default constructor */
	public IcdDiagnosisInfo() {
	}

	/** minimal constructor */
	public IcdDiagnosisInfo(IcdDiagnosisInfoId id) {
		this.id = id;
	}
	// Property accessors
	@EmbeddedId
	@AttributeOverride(name = "requestId", column = @Column(name = "RequestId", precision = 0))
	@AttributeOverride(name = "icd10code", column = @Column(name = "ICD10Code", length = 10)) 
	public IcdDiagnosisInfoId getId() {
		return this.id;
	}

	public void setId(IcdDiagnosisInfoId id) {
		this.id = id;
	}

	@Column(name = "IsDeletedFromProvider", columnDefinition = "CHAR(1) default ('0')")
	public Character getIsDeletedFromProvider() {
		return isDeletedFromProvider;
	}

	public void setIsDeletedFromProvider(Character isDeletedFromProvider) {
		this.isDeletedFromProvider = isDeletedFromProvider;
	}
}