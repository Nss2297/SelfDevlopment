package com.waseel.pbm.payercustomizationservice.persist.mongodb;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.waseel.pbm.payercustomizationservice.model.CustomizationRequestModel;
import com.waseel.pbm.payercustomizationservice.model.CustomizationResponseModel;

@Document(value = "CustomizationRequestAuditTrail")
public class CustomizationRequestAuditTrail {

	@Id
	@Field(name = "DocumentId")
	private String documentId = UUID.randomUUID().toString();

	@Field(name = "CustomizationRequestModel")
	private CustomizationRequestModel customizationRequestModel;

	@Field(name = "CustomizationRequestId")
	private Long customizationRequestId;

	@Field(name = "DateTime")
	private Date dateTime;

	@Field(name = "CustomizationResponseModel")
	private CustomizationResponseModel customizationResponseModel;

	public String getDocumentId() {
		return documentId;
	}

	public CustomizationRequestModel getCustomizationRequestModel() {
		return customizationRequestModel;
	}

	public Long getCustomizationRequestId() {
		return customizationRequestId;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public CustomizationResponseModel getCustomizationResponseModel() {
		return customizationResponseModel;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public void setCustomizationRequestModel(CustomizationRequestModel customizationRequestModel) {
		this.customizationRequestModel = customizationRequestModel;
	}

	public void setCustomizationRequestId(Long customizationRequestId) {
		this.customizationRequestId = customizationRequestId;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public void setCustomizationResponseModel(CustomizationResponseModel customizationResponseModel) {
		this.customizationResponseModel = customizationResponseModel;
	}

}
