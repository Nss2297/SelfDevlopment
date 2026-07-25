package com.waseel.dssadminservice.model.excelupload;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ErrorList {

	private Long rowNumber;
	private List<String> errorDescriptions;
	@JsonInclude(Include.NON_DEFAULT)
	@JsonProperty("isDuplicateRecord")
	private boolean duplicateRecord;

	public Long getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Long rowNumber) {
		this.rowNumber = rowNumber;
	}

	public List<String> getErrorDescriptions() {
		return errorDescriptions;
	}

	public void setErrorDescriptions(List<String> errorDescriptions) {
		this.errorDescriptions = errorDescriptions;
	}

	public boolean isDuplicateRecord() {
		return duplicateRecord;
	}

	public void setDuplicateRecord(boolean duplicateRecord) {
		this.duplicateRecord = duplicateRecord;
	}
}
