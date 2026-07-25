package com.waseel.pbm.rtsservice.persist.mongodb;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.waseel.pbm.rtsservice.model.RTSRequest;
import com.waseel.pbm.rtsservice.model.RTSResponse;

@Document(value = "RTSAuditTrail")
public class RTSAuditTrail {

	@Id
	@Field(name = "DocumentId")
	private String documentId = UUID.randomUUID().toString();
	
	@Field(name = "RequestId")
	public String requestId;

	@Field(name = "PayerId")
	public String payerId;

	@Field(name = "SubmissionDateTime")
	public Date submissionDateTime;

	@Field(name = "RtsRequest")
	public RTSRequest rtsRequest;

	@Field(name = "RtsResponse")
	public RTSResponse rtsResponse;

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

	public Date getSubmissionDateTime() {
		return submissionDateTime;
	}

	public void setSubmissionDateTime(Date submissionDateTime) {
		this.submissionDateTime = submissionDateTime;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public RTSRequest getRtsRequest() {
		return rtsRequest;
	}

	public void setRtsRequest(RTSRequest rtsRequest) {
		this.rtsRequest = rtsRequest;
	}

	public RTSResponse getRtsResponse() {
		return rtsResponse;
	}

	public void setRtsResponse(RTSResponse rtsResponse) {
		this.rtsResponse = rtsResponse;
	}

	

	
	
}
