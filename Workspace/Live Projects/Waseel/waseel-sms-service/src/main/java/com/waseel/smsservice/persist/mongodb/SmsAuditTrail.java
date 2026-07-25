package com.waseel.smsservice.persist.mongodb;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.waseel.smsservice.model.SmsRequestModel;
import com.waseel.smsservice.model.UnifonicResponseModel;

@Document(value = "SmsAuditTrail")
public class SmsAuditTrail {

	@Id
	@Field(name = "DocumentId")
	private String documentId = UUID.randomUUID().toString();

	@Field(name = "DateTime")
	public Date dateTime;

	@Field(name = "SmsRequestModel")
	public SmsRequestModel smsRequestModel;

	@Field(name = "UnifonicResponseModel")
	public UnifonicResponseModel unifonicResponseModel;

	public String getDocumentId() {
		return documentId;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public SmsRequestModel getSmsRequestModel() {
		return smsRequestModel;
	}

	public UnifonicResponseModel getUnifonicResponseModel() {
		return unifonicResponseModel;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public void setSmsRequestModel(SmsRequestModel smsRequestModel) {
		this.smsRequestModel = smsRequestModel;
	}

	public void setUnifonicResponseModel(UnifonicResponseModel unifonicResponseModel) {
		this.unifonicResponseModel = unifonicResponseModel;
	}

	public SmsAuditTrail() {
		super();
	}

}
