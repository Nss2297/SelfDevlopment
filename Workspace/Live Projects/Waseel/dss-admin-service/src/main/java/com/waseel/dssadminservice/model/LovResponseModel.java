package com.waseel.dssadminservice.model;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waseel.dssadminservice.persist.mdss.LOV;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LovResponseModel implements Serializable {

	private static final long serialVersionUID = -4630019695775755460L;

	private List<LOV> lovList;

	private List<String> errors;

	public List<LOV> getLovs() {
		return lovList;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setLovs(List<LOV> lovList) {
		this.lovList = lovList;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public LovResponseModel() {
		super();
	}

	public LovResponseModel(List<LOV> lovList, List<String> errors) {
		super();
		this.lovList = lovList;
		this.errors = errors;
	}

}
