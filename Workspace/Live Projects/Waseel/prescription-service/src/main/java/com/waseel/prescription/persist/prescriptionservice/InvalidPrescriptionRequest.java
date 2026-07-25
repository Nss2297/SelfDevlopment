package com.waseel.prescription.persist.prescriptionservice;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(schema = "PRESCRIPTION_SERVICE", name = "InvalidPrescriptionRequest")
public class InvalidPrescriptionRequest implements Serializable {

	private static final long serialVersionUID = -7460067111789914194L;

	@Id
	@GeneratedValue(generator = "PsInvalidPrescriptionRequestSeq")
	@SequenceGenerator(name = "PsInvalidPrescriptionRequestSeq", sequenceName = "PS_InvalidPrescriptionRequest_SEQ", allocationSize = 0, initialValue = 1)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;

	@Column(name = "RequestID", length = 100)
	private String requestId;

	@Column(name = "EPrescriptionReferenceNumber", length = 100)
	private String ePrescriptionReferenceNumber;

	@Column(name = "SendDateTime", length = 6, nullable = false)
	private Timestamp sendDateTime;

	@Column(name = "ReceivedDateTime", length = 6, nullable = false)
	private Timestamp receivedDateTime;

	@Column(name = "Status", length = 60)
	private String status;

	@Column(name = "StatusDescription", length = 500)
	private String statusDescription;

	@Column(name = "MemberID", length = 50)
	private String memberId;

	@Column(name = "IDNumber")
	private long idNumber;

	@Column(name = "PolicyNumber", length = 50)
	private String policyNumber;

	@Column(name = "PayerID", length = 70)
	private String payerId;

	@Column(name = "ProviderID", length = 70)
	private String providerId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getePrescriptionReferenceNumber() {
		return ePrescriptionReferenceNumber;
	}

	public void setePrescriptionReferenceNumber(String ePrescriptionReferenceNumber) {
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
	}

	public Timestamp getSendDateTime() {
		return sendDateTime;
	}

	public void setSendDateTime(Timestamp sendDateTime) {
		this.sendDateTime = sendDateTime;
	}

	public Timestamp getReceivedDateTime() {
		return receivedDateTime;
	}

	public void setReceivedDateTime(Timestamp receivedDateTime) {
		this.receivedDateTime = receivedDateTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public long getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(long idNumber) {
		this.idNumber = idNumber;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public InvalidPrescriptionRequest(String requestId, String ePrescriptionReferenceNumber, Timestamp sendDateTime,
			Timestamp receivedDateTime, String status, String statusDescription, String memberId, String policyNumber,
			String payerId, String providerId) {
		super();
		this.requestId = requestId;
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
		this.sendDateTime = sendDateTime;
		this.receivedDateTime = receivedDateTime;
		this.status = status;
		this.statusDescription = statusDescription;
		this.memberId = memberId;
		this.policyNumber = policyNumber;
		this.payerId = payerId;
		this.providerId = providerId;
	}

	public InvalidPrescriptionRequest() {
		super();
	}

	public InvalidPrescriptionRequest(Long id, String requestId, String ePrescriptionReferenceNumber,
			Timestamp sendDateTime, Timestamp receivedDateTime, String status, String statusDescription,
			String memberId, long idNumber, String policyNumber, String payerId, String providerId) {
		super();
		this.id = id;
		this.requestId = requestId;
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
		this.sendDateTime = sendDateTime;
		this.receivedDateTime = receivedDateTime;
		this.status = status;
		this.statusDescription = statusDescription;
		this.memberId = memberId;
		this.idNumber = idNumber;
		this.policyNumber = policyNumber;
		this.payerId = payerId;
		this.providerId = providerId;
	}
	
	public InvalidPrescriptionRequest(String requestId, String ePrescriptionReferenceNumber, Timestamp sendDateTime,
			Timestamp receivedDateTime, String status, String statusDescription, String memberId,long idNumber, String policyNumber,
			String payerId, String providerId) {
		super();
		this.requestId = requestId;
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
		this.sendDateTime = sendDateTime;
		this.receivedDateTime = receivedDateTime;
		this.status = status;
		this.statusDescription = statusDescription;
		this.memberId = memberId;
		this.policyNumber = policyNumber;
		this.payerId = payerId;
		this.providerId = providerId;
		this.idNumber = idNumber;
	}
}