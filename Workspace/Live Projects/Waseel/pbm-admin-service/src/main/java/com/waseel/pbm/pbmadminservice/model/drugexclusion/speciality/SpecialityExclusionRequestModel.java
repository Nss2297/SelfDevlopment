package com.waseel.pbm.pbmadminservice.model.drugexclusion.speciality;

import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.StringUtils;

import com.waseel.pbm.pbmadminservice.validator.customannotation.IsDecimal;

public class SpecialityExclusionRequestModel {
    

	@NotBlank(message = "specialityId {notNullOrEmpty}")
	@IsDecimal(message = "specialityId {onlyAllowDigitsAndDecimals}")
	private String specialityId;

	public SpecialityExclusionRequestModel() {
	}

	public SpecialityExclusionRequestModel(String specialityId) {
		this.specialityId = specialityId;
	}

	public String getspecialityId() {
		return specialityId;
	}

	public void setspecialityId(String specialityId) {
		this.specialityId = !StringUtils.isBlank(specialityId) ? specialityId.trim() : specialityId;
	}
}
