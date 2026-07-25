package com.waseel.pbm.pbmadminservice.model.drugexclusion;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExclusionDrugListUploadResponseModel {

	private Integer duplicateRecordCount;

	private List<String> duplicateRecords;

	private List<String> errors;

	private List<ExclusionListDrugDetails> exclusionListDrugDetailsRequestModel;

	public Integer getDuplicateRecordCount() {
		return duplicateRecordCount;
	}

	public List<String> getDuplicateRecords() {
		return duplicateRecords;
	}

	public void setDuplicateRecordCount(Integer duplicateRecordCount) {
		this.duplicateRecordCount = duplicateRecordCount;
	}

	public void setDuplicateRecords(List<String> duplicateRecords) {
		this.duplicateRecords = duplicateRecords;
	}

	public List<ExclusionListDrugDetails> getExclusionListDrugDetailsRequestModel() {
		return exclusionListDrugDetailsRequestModel;
	}

	public void setExclusionListDrugDetailsRequestModel(
			List<ExclusionListDrugDetails> exclusionListDrugDetailsRequestModel) {
		this.exclusionListDrugDetailsRequestModel = exclusionListDrugDetailsRequestModel;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public ExclusionDrugListUploadResponseModel() {
		super();
	}

	public ExclusionDrugListUploadResponseModel(Integer duplicateRecordCount, List<String> duplicateRecords,
			List<ExclusionListDrugDetails> exclusionListDrugDetailsRequestModel, List<String> errors) {
		super();
		this.duplicateRecordCount = duplicateRecordCount;
		this.duplicateRecords = duplicateRecords;
		this.errors = errors;
		this.exclusionListDrugDetailsRequestModel = exclusionListDrugDetailsRequestModel;
	}

}
