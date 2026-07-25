package com.waseel.brservice.persist.mongodb;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.waseel.brservice.model.InvalidResponseModel;
import com.waseel.brservice.model.SensitiveDrugRequestModel;
import com.waseel.brservice.model.SensitiveDrugResponseModel;

@Document(value = "BusinessRuleAuditTrail")
public class BusinessRuleAuditTrail {

	@Id
	@Field(name = "DocumentId")
	private String documentId = UUID.randomUUID().toString();

	@Field(name = "RequestId")
	public String requestId;

	@Field(name = "TransactionLogId")
	public Long transactionLogId;

	@Field(name = "DateTime")
	public Date dateTime;

	@Field(name = "SensitiveDrugRequest")
	public SensitiveDrugRequestModel sensitiveDrugRequestModel;

	@Field(name = "SensitiveDrugResponse")
	public SensitiveDrugResponseModel sensitiveDrugResponseModel;

	@Field(name = "InvalidResponse")
	public InvalidResponseModel invalidResponseModel;

	public InvalidResponseModel getInvalidResponseModel() {
		return invalidResponseModel;
	}

	public void setInvalidResponseModel(InvalidResponseModel invalidResponseModel) {
		this.invalidResponseModel = invalidResponseModel;
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

	public Long getTransactionLogId() {
		return transactionLogId;
	}

	public void setTransactionLogId(Long transactionLogId) {
		this.transactionLogId = transactionLogId;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public SensitiveDrugRequestModel getSensitiveDrugRequestModel() {
		return sensitiveDrugRequestModel;
	}

	public void setSensitiveDrugRequestModel(SensitiveDrugRequestModel sensitiveDrugRequestModel) {
		this.sensitiveDrugRequestModel = sensitiveDrugRequestModel;
	}

	public SensitiveDrugResponseModel getSensitiveDrugResponseModel() {
		return sensitiveDrugResponseModel;
	}

	public void setSensitiveDrugResponseModel(SensitiveDrugResponseModel sensitiveDrugResponseModel) {
		this.sensitiveDrugResponseModel = sensitiveDrugResponseModel;
	}
}
