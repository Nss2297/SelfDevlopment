package com.waseel.prescription.persist.prescriptionservice;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ServiceResponseInfo", schema = "PRESCRIPTION_SERVICE")
public class ServiceResponseInfo implements Serializable {

	private static final long serialVersionUID = -2485366013526837696L;

	@Id
	@GeneratedValue(generator = "PsServiceResponseInfoSeq")
	@SequenceGenerator(name = "PsServiceResponseInfoSeq", sequenceName = "PS_ServiceResponseInfo_SEQ", allocationSize = 0, initialValue = 1)
	@Column(name = "ID", unique = true, nullable = false, precision = 0)
	private Long id;

	@Column(name = "RequestID", length = 100, nullable = false, updatable = false)
	private String requestId;

	@Column(name = "RequestedAmount")
	private BigDecimal requestedAmount;

	@Column(name = "ApprovedAmount")
	private BigDecimal approvedAmount;

	@Column(name = "Discount")
	private Double discount;

	@Column(name = "PatientShare")
	private BigDecimal patientShare;

	@Column(name = "Net")
	private BigDecimal net;

	@Column(name = "Status", length = 60)
	private String status;

	@Column(name = "StatusDescription", length = 500)
	private String statusDescription;

	@Column(name = "ServiceID", nullable = false)
	private long serviceID;
	
	@Column(name = "IS_OVERRIDE_DECISION",columnDefinition = "CHAR(1) default ('0')")
	private boolean isOverrideDecision = false;

	@Column(name = "OVERRIDE_DESCRIPTION",length = 3000)
	private String overrideDescription;
	
	@Column(name = "PATIENT_SHARE_CURRENCY", nullable = true, length = 10)
	private String patientShareCurrency;

	@Column(name = "NET_CURRENCY", nullable = true, length = 10)
	private String netCurrency;
	
	public boolean isOverrideDecision() {
		return isOverrideDecision;
	}

	public void setOverrideDecision(boolean isOverrideDecision) {
		this.isOverrideDecision = isOverrideDecision;
	}

	public String getOverrideDescription() {
		return overrideDescription;
	}

	public void setOverrideDescription(String overrideDescription) {
		this.overrideDescription = overrideDescription;
	}

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

	public BigDecimal getRequestedAmount() {
		return requestedAmount;
	}

	public void setRequestedAmount(BigDecimal requestedAmount) {
		this.requestedAmount = requestedAmount;
	}

	public BigDecimal getApprovedAmount() {
		return approvedAmount;
	}

	public void setApprovedAmount(BigDecimal approvedAmount) {
		this.approvedAmount = approvedAmount;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
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

	public long getServiceID() {
		return serviceID;
	}

	public void setServiceID(long serviceID) {
		this.serviceID = serviceID;
	}

	public BigDecimal getPatientShare() {
		return patientShare;
	}

	public void setPatientShare(BigDecimal patientShare) {
		this.patientShare = patientShare;
	}

	public BigDecimal getNet() {
		return net;
	}

	public void setNet(BigDecimal net) {
		this.net = net;
	}

	
	
	public String getPatientShareCurrency() {
		return patientShareCurrency;
	}

	public String getNetCurrency() {
		return netCurrency;
	}

	public void setPatientShareCurrency(String patientShareCurrency) {
		this.patientShareCurrency = patientShareCurrency;
	}

	public void setNetCurrency(String netCurrency) {
		this.netCurrency = netCurrency;
	}

	public ServiceResponseInfo() {
		super();
	}

	public ServiceResponseInfo(String requestId, BigDecimal requestedAmount, BigDecimal approvedAmount, Double discount,
			BigDecimal patientShare, BigDecimal net, String status, String statusDescription, long serviceID) {
		super();
		this.requestId = requestId;
		this.requestedAmount = requestedAmount;
		this.approvedAmount = approvedAmount;
		this.discount = discount;
		this.patientShare = patientShare;
		this.net = net;
		this.status = status;
		this.statusDescription = statusDescription;
		this.serviceID = serviceID;
	}

	public ServiceResponseInfo(Long id, String requestId, BigDecimal requestedAmount, BigDecimal approvedAmount,
			Double discount, BigDecimal patientShare, BigDecimal net, String status, String statusDescription,
			long serviceID) {
		super();
		this.id = id;
		this.requestId = requestId;
		this.requestedAmount = requestedAmount;
		this.approvedAmount = approvedAmount;
		this.discount = discount;
		this.patientShare = patientShare;
		this.net = net;
		this.status = status;
		this.statusDescription = statusDescription;
		this.serviceID = serviceID;
	}

	public ServiceResponseInfo(String requestId, BigDecimal requestedAmount, BigDecimal approvedAmount, Double discount,
			BigDecimal patientShare, BigDecimal net, String status, String statusDescription, long serviceID,
			String patientShareCurrency, String netCurrency) {
		super();
		this.requestId = requestId;
		this.requestedAmount = requestedAmount;
		this.approvedAmount = approvedAmount;
		this.discount = discount;
		this.patientShare = patientShare;
		this.net = net;
		this.status = status;
		this.statusDescription = statusDescription;
		this.serviceID = serviceID;
		this.patientShareCurrency = patientShareCurrency;
		this.netCurrency = netCurrency;
	}
	
}
