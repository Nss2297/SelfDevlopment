package com.waseel.pbm.pbmadminservice.model.drugexclusion;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExclusionTypeSearchResponseModel {

	private String exclusionType;
	private String exclusionName;
	private Long exclusionAsscId;

	public ExclusionTypeSearchResponseModel() {
		super();
	}

	public ExclusionTypeSearchResponseModel(String exclusionType, String exclusionName) {
		super();
		this.exclusionType = exclusionType;
		this.exclusionName = exclusionName;
	}

	public ExclusionTypeSearchResponseModel(String exclusionType, String exclusionName, Long exclusionAsscId) {
		super();
		this.exclusionType = exclusionType;
		this.exclusionName = exclusionName;
		this.exclusionAsscId = exclusionAsscId;
	}

	public Long getExclusionAsscId() {
		return exclusionAsscId;
	}

	public void setExclusionAsscId(Long exclusionAsscId) {
		this.exclusionAsscId = exclusionAsscId;
	}

	public String getExclusionName() {
		return exclusionName;
	}

	public void setExclusionName(String exclusionName) {
		this.exclusionName = exclusionName;
	}

	public String getExclusionType() {
		return exclusionType;
	}

	public void setExclusionType(String exclusionType) {
		this.exclusionType = exclusionType;
	}

}
