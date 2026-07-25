package com.waseel.prescription.persist.prescriptionservice;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * The persistent class for the PRESCRIPTION_APPROVAL_DRUG database table.
 * 
 */
@Entity
@Table(name = "PRESCRIPTION_APPROVAL_DRUG")
@NamedQuery(name = "PrescriptionApprovalDrug.findAll", query = "SELECT p FROM PrescriptionApprovalDrug p")
public class PrescriptionApprovalDrug implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PresciprionApprovalDrugSeq")
	@SequenceGenerator(name = "PresciprionApprovalDrugSeq", sequenceName = "SEQ_PRESCRIPTION_APPROVAL_DRUG", allocationSize = 0, initialValue = 1)
	@Column(name = "PRESCRIPTION_APPROVAL_DRUG_ID", unique = true, nullable = false, precision = 0)
	private Long prescriptionApprovalDrugId;

	@Column(name = "EPRESCRIPTION_REFERENCE_NUMBER")
	private String eprescriptionReferenceNumber;

	@Column(name = "LATEST_UPDATE_DATE")
	private Timestamp latestUpdateDate;

	@Column(name = "SCIENTIFIC_CODE")
	private String scientificCode;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "SUGGESTED_DRUG_CODE")
	private String suggestedDrugCode;

	public PrescriptionApprovalDrug() {
	}

	public PrescriptionApprovalDrug(String eprescriptionReferenceNumber, Timestamp latestUpdateDate,
			String scientificCode, String status, String suggestedDrugCode) {
		this.eprescriptionReferenceNumber = eprescriptionReferenceNumber;
		this.latestUpdateDate = latestUpdateDate;
		this.scientificCode = scientificCode;
		this.status = status;
		this.suggestedDrugCode = suggestedDrugCode;
	}

	public Long getPrescriptionApprovalDrugId() {
		return prescriptionApprovalDrugId;
	}

	public void setPrescriptionApprovalDrugId(Long prescriptionApprovalDrugId) {
		this.prescriptionApprovalDrugId = prescriptionApprovalDrugId;
	}

	public String getEprescriptionReferenceNumber() {
		return this.eprescriptionReferenceNumber;
	}

	public void setEprescriptionReferenceNumber(String eprescriptionReferenceNumber) {
		this.eprescriptionReferenceNumber = eprescriptionReferenceNumber;
	}

	public Timestamp getLatestUpdateDate() {
		return this.latestUpdateDate;
	}

	public void setLatestUpdateDate(Timestamp latestUpdateDate) {
		this.latestUpdateDate = latestUpdateDate;
	}

	public String getScientificCode() {
		return this.scientificCode;
	}

	public void setScientificCode(String scientificCode) {
		this.scientificCode = scientificCode;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSuggestedDrugCode() {
		return this.suggestedDrugCode;
	}

	public void setSuggestedDrugCode(String suggestedDrugCode) {
		this.suggestedDrugCode = suggestedDrugCode;
	}

	public PrescriptionApprovalDrug(Long prescriptionApprovalDrugId, String eprescriptionReferenceNumber,
			Timestamp latestUpdateDate, String scientificCode, String status, String suggestedDrugCode) {
		super();
		this.prescriptionApprovalDrugId = prescriptionApprovalDrugId;
		this.eprescriptionReferenceNumber = eprescriptionReferenceNumber;
		this.latestUpdateDate = latestUpdateDate;
		this.scientificCode = scientificCode;
		this.status = status;
		this.suggestedDrugCode = suggestedDrugCode;
	}
}