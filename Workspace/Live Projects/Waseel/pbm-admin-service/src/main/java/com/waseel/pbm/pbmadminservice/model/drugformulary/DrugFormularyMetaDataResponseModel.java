package com.waseel.pbm.pbmadminservice.model.drugformulary;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class DrugFormularyMetaDataResponseModel {

	private Long formularyId;
	private String formularyName;
	private String createdDate;
	private String createdBy;
	private String updatedDate;
	private String payerId;
	private String status;

	public DrugFormularyMetaDataResponseModel() {
	}

	public DrugFormularyMetaDataResponseModel(Long formularyId) {
		this.formularyId = formularyId;
	}
	
	public DrugFormularyMetaDataResponseModel(String status) {
		this.status = status;
	}

	public DrugFormularyMetaDataResponseModel(Long formularyId, String formularyName, Date createdDate,
			Date updatedDate) {
		this.formularyId = formularyId;
		this.formularyName = formularyName;
		this.createdDate = convertDateToString(createdDate);
		this.updatedDate = convertDateToString(updatedDate);
	}

	public DrugFormularyMetaDataResponseModel(Long formularyId, String formularyName, Date createdDate,
			Date updatedDate, String payerId, String createdBy) {
		this.formularyId = formularyId;
		this.formularyName = formularyName;
		this.createdDate = convertDateToString(createdDate);
		this.updatedDate = convertDateToString(updatedDate);
		this.payerId = payerId;
		this.createdBy = createdBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Long getFormularyId() {
		return formularyId;
	}

	public void setFormularyId(Long formularyId) {
		this.formularyId = formularyId;
	}

	public String getFormularyName() {
		return formularyName;
	}

	public void setFormularyName(String formularyName) {
		this.formularyName = formularyName;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	private String convertDateToString(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(date);
	}
}
