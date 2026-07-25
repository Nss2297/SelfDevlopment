package com.waseel.prescription.model.dispense;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.waseel.prescription.model.policyconsumption.MaxPatientShareValueModel;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberPolicyConsumption {

	@JsonProperty("REPLACEABLE_BRAND")
	private MaxPatientShareValueModel replaceableBrandCase;

	@JsonProperty("IRREPLACEABLE_BRAND")
	private MaxPatientShareValueModel irreplaceableBrandCase;

	@JsonProperty("OUTPATIENT")
	private MaxPatientShareValueModel outpatientCase;

	public MaxPatientShareValueModel getReplaceableBrandCase() {
		return replaceableBrandCase;
	}

	public MaxPatientShareValueModel getIrreplaceableBrandCase() {
		return irreplaceableBrandCase;
	}

	public void setReplaceableBrandCase(MaxPatientShareValueModel replaceableBrandCase) {
		this.replaceableBrandCase = replaceableBrandCase;
	}

	public void setIrreplaceableBrandCase(MaxPatientShareValueModel irreplaceableBrandCase) {
		this.irreplaceableBrandCase = irreplaceableBrandCase;
	}
	
	public MaxPatientShareValueModel getOutpatientCase() {
		return outpatientCase;
	}

	public void setOutpatientCase(MaxPatientShareValueModel outpatientCase) {
		this.outpatientCase = outpatientCase;
	}

	public MemberPolicyConsumption() {
		super();
	}

	public MemberPolicyConsumption(MaxPatientShareValueModel replaceableBrandCase,
			MaxPatientShareValueModel irreplaceableBrandCase) {
		super();
		this.replaceableBrandCase = replaceableBrandCase;
		this.irreplaceableBrandCase = irreplaceableBrandCase;
	}

}
