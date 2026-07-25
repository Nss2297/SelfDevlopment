package com.waseel.pbm.pbmadminservice.model.drugexclusion;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waseel.pbm.pbmadminservice.validator.customannotation.NoMoreThan100Length;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DrugExclusionRequestModel {

	@NotEmpty(message = "exclusionListName {notNullOrEmpty}")
	@NoMoreThan100Length(message = "exclusionListName {noMoreThan100LengthValidation}")
	private String exclusionListName;

	@NotEmpty(message = "exclusionTypeDetails {notNullOrEmpty}")
	@Valid
	private List<ExclusionTypeRequestModel> exclusionTypeDetails;

	@NotEmpty(message = "exclusionDrugDetails {notNullOrEmpty}")
	@Valid
	private List<ExclusionListDrugDetailsRequestModel> exclusionDrugDetails;

	public String getExclusionListName() {
		return exclusionListName;
	}

	public List<ExclusionTypeRequestModel> getExclusionTypeDetails() {
		return exclusionTypeDetails;
	}

	public List<ExclusionListDrugDetailsRequestModel> getExclusionDrugDetails() {
		return exclusionDrugDetails;
	}

	public void setExclusionListName(String exclusionListName) {
		this.exclusionListName = exclusionListName;
	}

	public void setExclusionTypeDetails(List<ExclusionTypeRequestModel> exclusionTypeDetails) {
		this.exclusionTypeDetails = exclusionTypeDetails;
	}

	public void setExclusionDrugDetails(List<ExclusionListDrugDetailsRequestModel> exclusionDrugDetails) {
		this.exclusionDrugDetails = exclusionDrugDetails;
	}

	public DrugExclusionRequestModel() {
		super();
	}

	public DrugExclusionRequestModel(String exclusionListName, List<ExclusionTypeRequestModel> exclusionTypeDetails,
			List<ExclusionListDrugDetailsRequestModel> exclusionDrugDetails) {
		super();
		this.exclusionListName = exclusionListName;
		this.exclusionTypeDetails = exclusionTypeDetails;
		this.exclusionDrugDetails = exclusionDrugDetails;
	}

}
