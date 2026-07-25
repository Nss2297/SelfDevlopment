package com.waseel.dssadminservice.model.excelupload;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class BulkUploadResponseModel {

	private Integer duplicateRecordCount;
	private List<String> duplicateRecords;
	private List<ErrorList> errorList;
	private String message;

	public Integer getDuplicateRecordCount() {
		return duplicateRecordCount;
	}

	public void setDuplicateRecordCount(Integer duplicateRecordCount) {
		this.duplicateRecordCount = duplicateRecordCount;
	}

	public List<String> getDuplicateRecords() {
		return duplicateRecords;
	}

	public void setDuplicateRecords(List<String> duplicateRecords) {
		this.duplicateRecords = duplicateRecords;
	}

	public List<ErrorList> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<ErrorList> errorList) {
		this.errorList = errorList;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
