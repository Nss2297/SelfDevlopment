
package com.waseel.dssadminservice.model.sfdamanagement;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SFDADrugListResponseModel {

	@JsonProperty("id")
	private String id;
	@JsonProperty("effectiveDate")
	private String effectiveDate;
	@JsonProperty("uploadDate")
	private String uploadDate;
	@JsonProperty("drugs")
	private Page<Drug> drugs;

	@JsonProperty("id")
	public String getId() {
		return id;
	}

	@JsonProperty("id")
	public void setId(String id) {
		this.id = id;
	}

	@JsonProperty("effectiveDate")
	public String getEffectiveDate() {
		return effectiveDate;
	}

	@JsonProperty("effectiveDate")
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	@JsonProperty("uploadDate")
	public String getUploadDate() {
		return uploadDate;
	}

	@JsonProperty("uploadDate")
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	public Page<Drug> getDrugs() {
		return drugs;
	}

	public void setDrugs(Page<Drug> drugs) {
		this.drugs = drugs;
	}

}
