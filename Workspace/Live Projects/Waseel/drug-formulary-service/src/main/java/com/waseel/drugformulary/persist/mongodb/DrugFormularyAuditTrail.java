package com.waseel.drugformulary.persist.mongodb;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.waseel.drugformulary.model.DrugFormularyRequestModel;
import com.waseel.drugformulary.model.DrugFormularyResponseModel;

@Document(value = "DrugFormularyAuditTrail")
public class DrugFormularyAuditTrail {

	@Id
	@Field(name = "DocumentId")
	private String documentId = UUID.randomUUID().toString();

	@Field(name = "RequestId")
	public String requestId;

	@Field(name = "TransactionLogId")
	public Long transactionLogId;

	@Field(name = "DateTime")
	public Date dateTime;

	@Field(name = "DrugFormularyRequest")
	public DrugFormularyRequestModel drugFormularyRequestModel;

	@Field(name = "DrugFormularyResponse")
	public DrugFormularyResponseModel drugFormularyResponseModel;

	@Field(name = "DrugFormularyResponseList")
	public List<DrugFormularyResponseModel> drugFormularyResponseModelList;

	public List<DrugFormularyResponseModel> getDrugFormularyResponseModelList() {
		return drugFormularyResponseModelList;
	}

	public void setDrugFormularyResponseModelList(List<DrugFormularyResponseModel> drugFormularyResponseModelList) {
		this.drugFormularyResponseModelList = drugFormularyResponseModelList;
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

	public DrugFormularyRequestModel getDrugFormularyRequestModel() {
		return drugFormularyRequestModel;
	}

	public void setDrugFormularyRequestModel(DrugFormularyRequestModel drugFormularyRequestModel) {
		this.drugFormularyRequestModel = drugFormularyRequestModel;
	}

	public DrugFormularyResponseModel getDrugFormularyResponseModel() {
		return drugFormularyResponseModel;
	}

	public void setDrugFormularyResponseModel(DrugFormularyResponseModel drugFormularyResponseModel) {
		this.drugFormularyResponseModel = drugFormularyResponseModel;
	}
}
