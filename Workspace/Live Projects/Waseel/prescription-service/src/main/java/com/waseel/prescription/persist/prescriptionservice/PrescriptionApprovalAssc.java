package com.waseel.prescription.persist.prescriptionservice;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "PRESCRIPTION_APPROVAL_ASSC", schema = "PRESCRIPTION_SERVICE")
public class PrescriptionApprovalAssc implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "PrescriptionApprovalAsscSeq")
	@SequenceGenerator(name = "PrescriptionApprovalAsscSeq", sequenceName = "SEQ_PRESCRIPTION_APPROVAL_ASSC", allocationSize = 0, initialValue = 1)
	@Column(name = "PRESCRIPTION_APPROVAL_ASSC_ID", nullable = false, length = 200)
	private Long prescriptionApprovalAsscId;

	@Column(name = "APPROVAL_REFERENCE_NUMBER", nullable = false, length = 200)
	private String approvalReferenceNumber;

	@Column(name = "CREATED_DATE", nullable = false)
	private Date createdDate;

	@Column(name = "EPRESCRIPTION_REFERENCE_NUMBER", nullable = false, length = 100)
	private String eprescriptionReferenceNumber;

	@Column(name = "REQUEST_TYPE", nullable = false, length = 100)
	private String requestType;

	public PrescriptionApprovalAssc() {
		super();
	}

	public PrescriptionApprovalAssc(String approvalReferenceNumber, Date createdDate,
			String eprescriptionReferenceNumber, String requestType) {
		this.approvalReferenceNumber = approvalReferenceNumber;
		this.createdDate = createdDate;
		this.eprescriptionReferenceNumber = eprescriptionReferenceNumber;
		this.requestType = requestType;
	}

	public Long getPrescriptionApprovalAsscId() {
		return prescriptionApprovalAsscId;
	}

	public void setPrescriptionApprovalAsscId(Long prescriptionApprovalAsscId) {
		this.prescriptionApprovalAsscId = prescriptionApprovalAsscId;
	}

	public String getApprovalReferenceNumber() {
		return approvalReferenceNumber;
	}

	public void setApprovalReferenceNumber(String approvalReferenceNumber) {
		this.approvalReferenceNumber = approvalReferenceNumber;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getEprescriptionReferenceNumber() {
		return eprescriptionReferenceNumber;
	}

	public void setEprescriptionReferenceNumber(String eprescriptionReferenceNumber) {
		this.eprescriptionReferenceNumber = eprescriptionReferenceNumber;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
}
