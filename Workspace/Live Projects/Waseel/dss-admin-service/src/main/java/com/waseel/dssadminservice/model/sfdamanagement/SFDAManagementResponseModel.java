package com.waseel.dssadminservice.model.sfdamanagement;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SFDAManagementResponseModel implements Serializable {

	private static final long serialVersionUID = -8415838754719129830L;

	private Integer duplicateRecordCount;

	private List<String> errors;

	private Integer rowNumber;

	public SFDAManagementResponseModel() {
	}

	public SFDAManagementResponseModel(Integer duplicateRecordCount, List<String> errors, Integer rowNumber) {
		super();
		this.duplicateRecordCount = duplicateRecordCount;
		this.errors = errors;
		this.rowNumber = rowNumber;
	}

	public SFDAManagementResponseModel(List<String> errors) {
		super();
		this.errors = errors;
	}

	public Integer getDuplicateRecordCount() {
		return duplicateRecordCount;
	}

	public List<String> getErrors() {
		return errors;
	}

	public Integer getRowNumber() {
		return rowNumber;
	}

	public void setDuplicateRecordCount(Integer duplicateRecordCount) {
		this.duplicateRecordCount = duplicateRecordCount;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}

}
