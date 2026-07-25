package com.waseel.pbm.rtsservice.persist.hira;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
* PBMTransactionLog entity. @author MyEclipse Persistence Tools
*/
@Entity
@Table(name = "PBMTransactionLog", schema = "HIRA")

public class PBMTransactionLog implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal pbmtransactionLogId;
	private BigDecimal requestId;
	private String requestType;
	private BigDecimal source;
	private String destination;
	private String payerReferenceNumber;
	private String status;
	private String pbmstatus;
	private String httpStatus;
	private String httpResponseDescription;
	private Timestamp sendDateTime;
	private Timestamp receiveDateTime;
	private BigDecimal portalTransactionId;
	private Double transactionType;
	private String pbmValidationRequester;

	// Constructors

	/** default constructor */
	public PBMTransactionLog() {
	}

	/** minimal constructor */
	public PBMTransactionLog(BigDecimal pbmtransactionLogId, BigDecimal requestId, BigDecimal source,
			String destination) {
		this.pbmtransactionLogId = pbmtransactionLogId;
		this.requestId = requestId;
		this.source = source;
		this.destination = destination;
	}

	/** full constructor */
	public PBMTransactionLog(BigDecimal pbmtransactionLogId, BigDecimal requestId, String requestType,
			BigDecimal source, String destination, String payerReferenceNumber, String status, String pbmstatus,
			String httpStatus, String httpResponseDescription, Timestamp sendDateTime, Timestamp receiveDateTime,
			BigDecimal portalTransactionId, Double transactionType, String pbmValidationRequester) {
		this.pbmtransactionLogId = pbmtransactionLogId;
		this.requestId = requestId;
		this.requestType = requestType;
		this.source = source;
		this.destination = destination;
		this.payerReferenceNumber = payerReferenceNumber;
		this.status = status;
		this.pbmstatus = pbmstatus;
		this.httpStatus = httpStatus;
		this.httpResponseDescription = httpResponseDescription;
		this.sendDateTime = sendDateTime;
		this.receiveDateTime = receiveDateTime;
		this.portalTransactionId = portalTransactionId;
		this.transactionType = transactionType;
		this.pbmValidationRequester = pbmValidationRequester;

	}

	@Id
	@Column(name = "PBMTransactionLogId", unique = true, nullable = false, precision = 22, scale = 0)
	@GeneratedValue(generator = "PbmTransactionLogSeq")
	@SequenceGenerator(name = "PbmTransactionLogSeq", sequenceName = "PBMTransactionLog_SEQ", allocationSize = 1)
	public BigDecimal getPbmtransactionLogId() {
		return this.pbmtransactionLogId;
	}

	public void setPbmtransactionLogId(BigDecimal pbmtransactionLogId) {
		this.pbmtransactionLogId = pbmtransactionLogId;
	}

	@Column(name = "RequestId", nullable = false, precision = 22, scale = 0)

	public BigDecimal getRequestId() {
		return this.requestId;
	}

	public void setRequestId(BigDecimal requestId) {
		this.requestId = requestId;
	}

	@Column(name = "RequestType", length = 15)

	public String getRequestType() {
		return this.requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	@Column(name = "Source", nullable = false, precision = 22, scale = 0)

	public BigDecimal getSource() {
		return this.source;
	}

	public void setSource(BigDecimal source) {
		this.source = source;
	}

	@Column(name = "Destination", nullable = false, length = 40)

	public String getDestination() {
		return this.destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	@Column(name = "PayerReferenceNumber", length = 100)
	public String getPayerReferenceNumber() {
		return this.payerReferenceNumber;
	}

	public void setPayerReferenceNumber(String payerReferenceNumber) {
		this.payerReferenceNumber = payerReferenceNumber;
	}

	@Column(name = "Status", length = 40)

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	@Column(name = "HttpResponseDescription", length = 256)

	public String getHttpResponseDescription() {
		return this.httpResponseDescription;
	}

	public void setHttpResponseDescription(String httpResponseDescription) {
		this.httpResponseDescription = httpResponseDescription;
	}

	@Column(name = "SendDateTime", length = 11)

	public Timestamp getSendDateTime() {
		return this.sendDateTime;
	}

	public void setSendDateTime(Timestamp sendDateTime) {
		this.sendDateTime = sendDateTime;
	}

	@Column(name = "ReceiveDateTime", length = 11)

	public Timestamp getReceiveDateTime() {
		return this.receiveDateTime;
	}

	public void setReceiveDateTime(Timestamp receiveDateTime) {
		this.receiveDateTime = receiveDateTime;
	}

	@Column(name = "PortalTransactionId", precision = 22, scale = 0)

	public BigDecimal getPortalTransactionId() {
		return this.portalTransactionId;
	}

	public void setPortalTransactionId(BigDecimal portalTransactionId) {
		this.portalTransactionId = portalTransactionId;
	}
	
	@Column(name = "TransactionType", precision = 10, scale = 6)

	public Double getTransactionType() {
		return this.transactionType;
	}

	public void setTransactionType(Double transactionType) {
		this.transactionType = transactionType;
	}
	
	@Column(name = "PbmValidationRequester", length = 25)

	public String getPbmValidationRequester() {
		return this.pbmValidationRequester;
	}

	public void setPbmValidationRequester(String pbmValidationRequester) {
		this.pbmValidationRequester = pbmValidationRequester;
	}

}