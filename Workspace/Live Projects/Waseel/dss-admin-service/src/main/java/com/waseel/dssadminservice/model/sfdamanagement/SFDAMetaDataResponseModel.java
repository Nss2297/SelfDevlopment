package com.waseel.dssadminservice.model.sfdamanagement;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class SFDAMetaDataResponseModel {

	private Long drugListId;
	private String effectiveDate;
	private String uploadDate;
	private String fileName;

	public SFDAMetaDataResponseModel() {
	}

	public SFDAMetaDataResponseModel(Long drugListId, Date effectiveDate, Date uploadDate, String fileName) {
		this.drugListId = drugListId;
		this.effectiveDate = convertDateToString(effectiveDate);
		this.uploadDate = convertDateToString(uploadDate);
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getDrugListId() {
		return drugListId;
	}

	public void setDrugListId(Long drugListId) {
		this.drugListId = drugListId;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	private String convertDateToString(Date date) {
		if (date != null) {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(date);
		}
		return null;
	}
}
