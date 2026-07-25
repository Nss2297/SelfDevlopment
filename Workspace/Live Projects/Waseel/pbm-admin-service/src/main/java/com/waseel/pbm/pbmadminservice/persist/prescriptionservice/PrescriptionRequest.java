package com.waseel.pbm.pbmadminservice.persist.prescriptionservice;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.waseel.pbm.pbmadminservice.persist.hira.SwitchAccount;

@Entity
@Table(name = "PrescriptionRequest", schema = "PRESCRIPTION_SERVICE")

public class PrescriptionRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "RequestID", nullable = false, length = 100, precision = 0)
	private String requestId;

	@Column(name = "PayerID", length = 20, precision = 0)
	private String payerId;

	@Column(name = "ProviderID", length = 20, precision = 0)
	private String providerId;

	@Column(name = "SendDateTime", length = 6)
	private Timestamp sendDateTime;

	@Column(name = "ReceivedDateTime", length = 6)
	private Timestamp receivedDateTime;

	@Column(name = "StatusCode")
	private String statusCode;

	@Column(name = "StatusDescription", length = 3000)
	private String statusDescription;

	@Column(name = "canCancel", columnDefinition = "CHAR(1) default ('1')")
	private boolean canCancel = true;

	@Column(name = "canFollowUp", columnDefinition = "CHAR(1) default ('1')")
	private boolean canFollowUp = true;

	@Column(name = "IsCancelled", columnDefinition = "CHAR(1) default ('0')")
	private boolean isCancelled = false;

	@Column(name = "EPrescriptionReferenceNumber", length = 100)
	private String ePrescriptionReferenceNumber;

	@Column(name = "CaseType", length = 50)
	private String caseType;

	@Column(name = "PatientShare")
	private BigDecimal patientShare;

	@Column(name = "PayerShare")
	private BigDecimal payerShare;

	@Column(name = "LAST_UPDATE_DATE")
	private Date lastUpdateDate;

	@Column(name = "PATIENT_SHARE_CURRENCY", length = 10)
	private String patientShareCurrency;

	@Column(name = "PAYER_SHARE_CURRENCY", length = 10)
	private String payerShareCurrency;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ProviderID", referencedColumnName = "SwitchAccountId", insertable = false, updatable = false)
	private SwitchAccount switchAccount;

	public SwitchAccount getSwitchAccount() {
		return switchAccount;
	}

	public void setSwitchAccount(SwitchAccount switchAccount) {
		this.switchAccount = switchAccount;
	}

	public boolean isCancelled() {
		return isCancelled;
	}

	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
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

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public PrescriptionRequest() {
		super();
	}

	public boolean getCanCancel() {
		return canCancel;
	}

	public void setCanCancel(boolean canCancel) {
		this.canCancel = canCancel;
	}

	public boolean getCanFollowUp() {
		return canFollowUp;
	}

	public void setCanFollowUp(boolean canFollowUp) {
		this.canFollowUp = canFollowUp;
	}

	public String getePrescriptionReferenceNumber() {
		return ePrescriptionReferenceNumber;
	}

	public void setePrescriptionReferenceNumber(String ePrescriptionReferenceNumber) {
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
	}

	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public BigDecimal getPatientShare() {
		return patientShare;
	}

	public BigDecimal getPayerShare() {
		return payerShare;
	}

	public void setPatientShare(BigDecimal patientShare) {
		this.patientShare = patientShare;
	}

	public void setPayerShare(BigDecimal payerShare) {
		this.payerShare = payerShare;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getPatientShareCurrency() {
		return patientShareCurrency;
	}

	public String getPayerShareCurrency() {
		return payerShareCurrency;
	}

	public void setPatientShareCurrency(String patientShareCurrency) {
		this.patientShareCurrency = patientShareCurrency;
	}

	public void setPayerShareCurrency(String payerShareCurrency) {
		this.payerShareCurrency = payerShareCurrency;
	}
}