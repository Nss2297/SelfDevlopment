package com.waseel.pbm.dssservice.persist.mdss;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * TransactionLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "TransactionLog", schema = "MDSS")

public class TransactionLog implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4744917492049854771L;
	// Fields
	private Long transactionLogId;
	private String requestId;
	private String transactionType;
	private Double transactionId;
	private String payerId;
	private String providerId;
	private String transactionStatus;
	private String pbmstatus;
	private String httpStatus;
	private String httpStatusDescription;
	private Timestamp receivingRequestDateTime;
	private Timestamp sendingResponseDateTime;
	private String validationRequester;

	// Constructors
	/** default constructor */
	public TransactionLog() {
	}

	/** minimal constructor */
	public TransactionLog(Long transactionLogId) {
		this.transactionLogId = transactionLogId;
	}

	/** full constructor */
	public TransactionLog(Long transactionLogId, String requestId, String transactionType, Double transactionId,
			String payerId, String providerId, String transactionStatus, String pbmstatus, String httpStatus,
			String httpStatusDescription, Timestamp receivingRequestDateTime, Timestamp sendingResponseDateTime,
			String validationRequester) {
		this.transactionLogId = transactionLogId;
		this.requestId = requestId;
		this.transactionType = transactionType;
		this.transactionId = transactionId;
		this.payerId = payerId;
		this.providerId = providerId;
		this.transactionStatus = transactionStatus;
		this.pbmstatus = pbmstatus;
		this.httpStatus = httpStatus;
		this.httpStatusDescription = httpStatusDescription;
		this.receivingRequestDateTime = receivingRequestDateTime;
		this.sendingResponseDateTime = sendingResponseDateTime;
		this.validationRequester = validationRequester;
	}

	// Property accessors
	@Id
	@GeneratedValue(generator="DssTransactionLogSeq")
	@SequenceGenerator(name="DssTransactionLogSeq",sequenceName="DSSTransactionLog_SEQ", allocationSize=0,initialValue = 1)
	@Column(name = "TransactionLogId", unique = true, nullable = false, precision = 0)
	public Long getTransactionLogId() {
		return this.transactionLogId;
	}

	public void setTransactionLogId(Long transactionLogId) {
		this.transactionLogId = transactionLogId;
	}

	// @ManyToOne(fetch = FetchType.LAZY)
	// @JoinColumn(name = "RequestId")
	@Column(name = "RequestId", length = 100)
	public String getRequestId() {
		return this.requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
	@Column(name = "TransactionType", length = 20)
	public String getTransactionType() {
		return this.transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	@Column(name = "TransactionId", precision = 0)
	public Double getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId(Double transactionId) {
		this.transactionId = transactionId;
	}

	@Column(name = "PayerId", length = 20)
	public String getPayerId() {
		return this.payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	@Column(name = "ProviderId", length = 20)
	public String getProviderId() {
		return this.providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	@Column(name = "TransactionStatus", length = 40)
	public String getTransactionStatus() {
		return this.transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	@Column(name = "PBMStatus", length = 40)
	public String getPbmstatus() {
		return this.pbmstatus;
	}

	public void setPbmstatus(String pbmstatus) {
		this.pbmstatus = pbmstatus;
	}

	@Column(name = "HttpStatus", length = 30)
	public String getHttpStatus() {
		return this.httpStatus;
	}

	public void setHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
	}

	@Column(name = "HttpStatusDescription", length = 100)
	public String getHttpStatusDescription() {
		return this.httpStatusDescription;
	}

	public void setHttpStatusDescription(String httpStatusDescription) {
		this.httpStatusDescription = httpStatusDescription;
	}

	@Column(name = "ReceivingRequestDateTime", length = 11)
	public Timestamp getReceivingRequestDateTime() {
		return this.receivingRequestDateTime;
	}

	public void setReceivingRequestDateTime(Timestamp receivingRequestDateTime) {
		this.receivingRequestDateTime = receivingRequestDateTime;
	}

	@Column(name = "SendingResponseDateTime", length = 11)
	public Timestamp getSendingResponseDateTime() {
		return this.sendingResponseDateTime;
	}

	public void setSendingResponseDateTime(Timestamp sendingResponseDateTime) {
		this.sendingResponseDateTime = sendingResponseDateTime;
	}

	@Column(name = "ValidationRequester", length = 25)
	public String getValidationRequester() {
		return this.validationRequester;
	}

	public void setValidationRequester(String validationRequester) {
		this.validationRequester = validationRequester;
	}

}