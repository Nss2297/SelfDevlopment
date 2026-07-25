package com.waseel.pbm.dssservice.persist.mongodb;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.waseel.pbm.dssservice.model.CancelOverrideResponse;
import com.waseel.pbm.dssservice.model.CancellationOverrideRequest;
import com.waseel.pbm.dssservice.model.DssRequest;
import com.waseel.pbm.dssservice.model.DssResponse;

@Document(value = "DSSAuditTrail")
public class DSSAuditTrail {

	@Id
	@Field(name = "DocumentId")
	private String documentId = UUID.randomUUID().toString();
	
	@Field(name = "RequestId")
	public String requestId;

	@Field(name = "DssTransactionLogId")
	public Long dssTransactionLogId;

	@Field(name = "DateTime")
	public Date dateTime;

	@Field(name = "DssRequest")
	public DssRequest dssRequest;

	@Field(name = "CancelOverrideRequest")
	public CancellationOverrideRequest cancellationOverrideRequest;
	
	@Field(name = "DssResponse")
	public DssResponse dssResponse;
	
	@Field(name = "CancelOverrideResponse")
	public CancelOverrideResponse cancellationOverrideResponse;
	
	@Field(name = "requestType")
	public String requestType;

	public CancellationOverrideRequest getCancellationOverrideRequest() {
		return cancellationOverrideRequest;
	}

	public void setCancellationOverrideRequest(CancellationOverrideRequest cancellationOverrideRequest) {
		this.cancellationOverrideRequest = cancellationOverrideRequest;
	}

	public CancelOverrideResponse getCancellationOverrideResponse() {
		return cancellationOverrideResponse;
	}

	public void setCancellationOverrideResponse(CancelOverrideResponse cancellationOverrideResponse) {
		this.cancellationOverrideResponse = cancellationOverrideResponse;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Long getDssTransactionLogId() {
		return dssTransactionLogId;
	}

	public void setDssTransactionLogId(Long dssTransactionLogId) {
		this.dssTransactionLogId = dssTransactionLogId;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public DssRequest getDssRequest() {
		return dssRequest;
	}

	public void setDssRequest(DssRequest dssRequest) {
		this.dssRequest = dssRequest;
	}

	public DssResponse getDssResponse() {
		return dssResponse;
	}

	public void setDssResponse(DssResponse dssResponse) {
		this.dssResponse = dssResponse;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

}
