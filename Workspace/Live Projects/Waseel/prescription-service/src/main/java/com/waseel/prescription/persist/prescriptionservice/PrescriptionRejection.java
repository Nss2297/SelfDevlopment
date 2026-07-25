package com.waseel.prescription.persist.prescriptionservice;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PRESCRIPTION_REJECTION", schema = "PRESCRIPTION_SERVICE")
public class PrescriptionRejection implements Serializable {

	private static final long serialVersionUID = -5675476992101626927L;

	@Id
	@GeneratedValue(generator = "PsPrescriptionRejectionSeq")
	@SequenceGenerator(name = "PsPrescriptionRejectionSeq", sequenceName = "PS_PRESCRIPTION_REJECTION_SEQ", allocationSize = 0, initialValue = 1)
	@Column(name = "PRESCRIPTION_REJECTION_ID", unique = true, nullable = false, precision = 0)
	private Long prescriptionRejectionId;

	@Column(name = "DENIAL_CODE", nullable = false, length = 30)
	private String denialCode;

	@Column(name = "REJECTION_REASON", nullable = false, length = 2500)
	private String rejectionReason;

	@Column(name = "REQUEST_ID", nullable = false, length = 100)
	private String requestId;

	@Column(name = "ELIGIBILITY_REFERENCE_NUMBER", nullable = false, length = 40)
	private String eligibilityReferenceNumber;

	@Column(name = "SHOW_UNDER_BUSINESS_VALIDATION", nullable = false, columnDefinition = "CHAR(1) default ('1')")
	private boolean showUnderBusinessValidation;

	public Long getPrescriptionRejectionId() {
		return prescriptionRejectionId;
	}

	public String getDenialCode() {
		return denialCode;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public String getRequestId() {
		return requestId;
	}

	public String getEligibilityReferenceNumber() {
		return eligibilityReferenceNumber;
	}

	public void setPrescriptionRejectionId(Long prescriptionRejectionId) {
		this.prescriptionRejectionId = prescriptionRejectionId;
	}

	public void setDenialCode(String denialCode) {
		this.denialCode = denialCode;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public void setEligibilityReferenceNumber(String eligibilityReferenceNumber) {
		this.eligibilityReferenceNumber = eligibilityReferenceNumber;
	}

	public boolean isShowUnderBusinessValidation() {
		return showUnderBusinessValidation;
	}

	public void setShowUnderBusinessValidation(boolean showUnderBusinessValidation) {
		this.showUnderBusinessValidation = showUnderBusinessValidation;
	}

	public PrescriptionRejection() {
		super();
	}

	public PrescriptionRejection(String denialCode, String rejectionReason, String requestId,
			String eligibilityReferenceNumber, boolean showUnderBusinessValidation) {
		super();
		this.denialCode = denialCode;
		this.rejectionReason = rejectionReason;
		this.requestId = requestId;
		this.eligibilityReferenceNumber = eligibilityReferenceNumber;
		this.showUnderBusinessValidation = showUnderBusinessValidation;
	}

}