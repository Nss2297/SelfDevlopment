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
 * The persistent class for the OVERRIDED_MEDICATION database table.
 * 
 */
@Entity
@Table(name = "OVERRIDDEN_MEDICATION", schema = "PRESCRIPTION_SERVICE")
@NamedQuery(name = "OverriddenMedication.findAll", query = "SELECT o FROM OverriddenMedication o")
public class OverriddenMedication implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "seqOverriddenMedicationGen")
	@SequenceGenerator(name = "seqOverriddenMedicationGen", sequenceName = "SEQ_OVERRIDDEN_MEDICATION", allocationSize = 0, initialValue = 1)
	@Column(name = "OVERRIDDEN_MEDICATION_ID", unique = true, nullable = false, precision = 0)
	private Long overriddenMedicationId;

	@Column(name = "EPRESCRIPTION_REFERENCE_NUMBER", nullable = false, length = 100)
	private String ePrescriptionReferenceNumber;

	@Column(name = "PROVIDER_ID", nullable = false, length = 20)
	private String providerId;

	@Column(name = "PROVIDER_NAME", nullable = false, length = 100)
	private String providerName;

	@Column(name = "PAYER_ID", nullable = false, length = 20)
	private String payerId;

	@Column(name = "DRUG_CODE", nullable = true, length = 50)
	private String drugCode;

	@Column(name = "DRUG_NAME", nullable = true, length = 50)
	private String drugName;

	@Column(name = "SCIENTIFIC_CODE", nullable = true, length = 50)
	private String scientificCode;

	@Column(name = "SCIENTIFIC_NAME", nullable = true, length = 50)
	private String scientificName;

	@Column(name = "DENIAL_CODE", nullable = false, length = 30)
	private String denialCode;

	@Column(name = "REJECTION_REASON", nullable = false, length = 2500)
	private String rejectionReason;

	@Column(name = "OVERRIDING_REASON", length = 2500)
	private String overridingReason;

	@Column(name = "OVERRIDDEN_DATE", nullable = false)
	private Timestamp overriddenDate;

	@Column(name = "OVERRIDDEN_BY", nullable = false, length = 50)
	private String overriddenBy;

	public Long getOverriddenMedicationId() {
		return overriddenMedicationId;
	}

	public void setOverriddenMedicationId(Long overriddenMedicationId) {
		this.overriddenMedicationId = overriddenMedicationId;
	}

	public String getePrescriptionReferenceNumber() {
		return ePrescriptionReferenceNumber;
	}

	public void setePrescriptionReferenceNumber(String ePrescriptionReferenceNumber) {
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public String getDenialCode() {
		return denialCode;
	}

	public void setDenialCode(String denialCode) {
		this.denialCode = denialCode;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public String getOverridingReason() {
		return overridingReason;
	}

	public void setOverridingReason(String overridingReason) {
		this.overridingReason = overridingReason;
	}

	public Timestamp getOverriddenDate() {
		return overriddenDate;
	}

	public void setOverriddenDate(Timestamp overriddenDate) {
		this.overriddenDate = overriddenDate;
	}

	public String getOverriddenBy() {
		return overriddenBy;
	}

	public void setOverriddenBy(String overriddenBy) {
		this.overriddenBy = overriddenBy;
	}

	public String getScientificCode() {
		return scientificCode;
	}

	public void setScientificCode(String scientificCode) {
		this.scientificCode = scientificCode;
	}

	public String getScientificName() {
		return scientificName;
	}

	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}

	public OverriddenMedication() {
	
	}

	public OverriddenMedication(Long overriddenMedicationId, String ePrescriptionReferenceNumber, String providerId,
			String providerName, String payerId, String drugCode, String denialCode, String rejectionReason,
			String overridingReason, Timestamp overriddenDate, String overriddenBy) {
		this.overriddenMedicationId = overriddenMedicationId;
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
		this.providerId = providerId;
		this.providerName = providerName;
		this.payerId = payerId;
		this.drugCode = drugCode;
		this.denialCode = denialCode;
		this.rejectionReason = rejectionReason;
		this.overridingReason = overridingReason;
		this.overriddenDate = overriddenDate;
		this.overriddenBy = overriddenBy;
	}
}