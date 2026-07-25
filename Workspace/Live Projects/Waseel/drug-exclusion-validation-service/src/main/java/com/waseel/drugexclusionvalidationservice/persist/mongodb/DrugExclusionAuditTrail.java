package com.waseel.drugexclusionvalidationservice.persist.mongodb;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.waseel.drugexclusionvalidationservice.model.DrugExclusionRequestModel;
import com.waseel.drugexclusionvalidationservice.model.DrugExclusionResponseModel;

@Document(value = "DrugExclusionAuditTrail")
public class DrugExclusionAuditTrail {

	@Id
	@Field(name = "DocumentId")
	private String documentId = UUID.randomUUID().toString();

	@Field(name = "RequestId")
	public String requestId;

	@Field(name = "TransactionLogId")
	public Long transactionLogId;

	@Field(name = "DateTime")
	public Date dateTime;

	@Field(name = "DrugExclusionRequest")
	public DrugExclusionRequestModel drugExclusionRequestModel;

	@Field(name = "DrugExclusionResponse")
	public DrugExclusionResponseModel drugExclusionResponseModel;

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

	public DrugExclusionRequestModel getSpecialityExclusionRequestModel() {
		return drugExclusionRequestModel;
	}

	public void setSpecialityExclusionRequestModel(DrugExclusionRequestModel drugExclusionRequestModel) {
		this.drugExclusionRequestModel = drugExclusionRequestModel;
	}

	public DrugExclusionResponseModel getSpecialityExclusionResponseModel() {
		return drugExclusionResponseModel;
	}

	public void setSpecialityExclusionResponseModel(DrugExclusionResponseModel drugExclusionResponseModel) {
		this.drugExclusionResponseModel = drugExclusionResponseModel;
	}

	public DrugExclusionRequestModel getDrugExclusionRequestModel() {
		return drugExclusionRequestModel;
	}

	public void setDrugExclusionRequestModel(DrugExclusionRequestModel drugExclusionRequestModel) {
		this.drugExclusionRequestModel = drugExclusionRequestModel;
	}

	public DrugExclusionResponseModel getDrugExclusionResponseModel() {
		return drugExclusionResponseModel;
	}

	public void setDrugExclusionResponseModel(DrugExclusionResponseModel drugExclusionResponseModel) {
		this.drugExclusionResponseModel = drugExclusionResponseModel;
	}
}
