package com.waseel.eligibility.persist;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.waseel.eligibility.model.EligibilityRequestModel;
import com.waseel.eligibility.model.EligibilityResponseModel;

@Document(value = "EligibilityAuditTrail")
public class EligibilityAuditTrail {

	@Id
	@Field(name = "DocumentId")
	private String documentId = UUID.randomUUID().toString();

	@Field(name = "TransactionReferenceNumber")
	public String transactionReferenceNumber;

	@Field(name = "EligibilityTransactionLogId")
	public Long eligibilityTransactionLogId;

	@Field(name = "DateTime")
	public Date dateTime;

	@Field(name = "EligibilityRequest")
	public EligibilityRequestModel eligibilityRequestModel;

	@Field(name = "EligibilityResponse")
	public EligibilityResponseModel eligibilityResponseModel;

	@Field(name = "TransactionType")
	public String transactionType;

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getTransactionReferenceNumber() {
		return transactionReferenceNumber;
	}

	public void setTransactionReferenceNumber(String transactionReferenceNumber) {
		this.transactionReferenceNumber = transactionReferenceNumber;
	}

	public Long getEligibilityTransactionLogId() {
		return eligibilityTransactionLogId;
	}

	public void setEligibilityTransactionLogId(Long eligibilityTransactionLogId) {
		this.eligibilityTransactionLogId = eligibilityTransactionLogId;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public EligibilityRequestModel getEligibilityRequestModel() {
		return eligibilityRequestModel;
	}

	public void setEligibilityRequestModel(EligibilityRequestModel eligibilityRequestModel) {
		this.eligibilityRequestModel = eligibilityRequestModel;
	}

	public EligibilityResponseModel getEligibilityResponseModel() {
		return eligibilityResponseModel;
	}

	public void setEligibilityResponseModel(EligibilityResponseModel eligibilityResponseModel) {
		this.eligibilityResponseModel = eligibilityResponseModel;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
}
