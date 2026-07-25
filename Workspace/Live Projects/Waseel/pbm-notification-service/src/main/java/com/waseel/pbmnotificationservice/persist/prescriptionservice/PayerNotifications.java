package com.waseel.pbmnotificationservice.persist.prescriptionservice;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "PAYER_NOTIFICATIONS", schema = "PRESCRIPTION_SERVICE")
public class PayerNotifications implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PAYER_NOTIFICATION_ID", nullable = false, updatable = false)
	private Long payerNotificationId;

	@Column(name = "PAYER_ID", nullable = false)
	private String payerId;

	@Column(name = "E_PRESCRIPTION_REFERENCE_NUMBER", nullable = false)
	private String ePrescriptionReferenceNumber;

	@Column(name = "APPROVAL_REFERENCE_NUMBER", nullable = false)
	private String approvalReferenceNumber;

	@Column(name = "STATUS", nullable = false)
	private String status;

	@Column(name = "ACK_DATE_TIME", nullable = false)
	private Date ackDateTime;

	@Column(name = "EPRESCRIPTION_STATUS", nullable = false)
	private String ePrescriptionstatus;

	@Column(name = "STATUS_DESCRIPTION", nullable = true)
	private String statusDescription;

	public String getePrescriptionstatus() {
		return ePrescriptionstatus;
	}

	public void setePrescriptionstatus(String ePrescriptionstatus) {
		this.ePrescriptionstatus = ePrescriptionstatus;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public Long getPayerNotificationId() {
		return payerNotificationId;
	}

	public void setPayerNotificationId(Long payerNotificationId) {
		this.payerNotificationId = payerNotificationId;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getePrescriptionReferenceNumber() {
		return ePrescriptionReferenceNumber;
	}

	public void setePrescriptionReferenceNumber(String ePrescriptionReferenceNumber) {
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
	}

	public String getApprovalReferenceNumber() {
		return approvalReferenceNumber;
	}

	public void setApprovalReferenceNumber(String approvalReferenceNumber) {
		this.approvalReferenceNumber = approvalReferenceNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getAckDateTime() {
		return ackDateTime;
	}

	public void setAckDateTime(Date ackDateTime) {
		this.ackDateTime = ackDateTime;
	}

	public PayerNotifications(String payerId, String ePrescriptionReferenceNumber, String approvalReferenceNumber,
			String status, String ePrescriptionstatus, String statusDescription, Date ackDateTime) {
		super();
		this.payerId = payerId;
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber.getBytes().length <= 100
				? ePrescriptionReferenceNumber
				: null;
		this.approvalReferenceNumber = approvalReferenceNumber.getBytes().length <= 100 ? approvalReferenceNumber
				: null;
		this.status = status;
		this.ePrescriptionstatus = ePrescriptionstatus.getBytes().length <= 60 ? ePrescriptionstatus : null;
		this.statusDescription = statusDescription;
		this.ackDateTime = ackDateTime;
	}

	public PayerNotifications() {
		super();
	}
}
