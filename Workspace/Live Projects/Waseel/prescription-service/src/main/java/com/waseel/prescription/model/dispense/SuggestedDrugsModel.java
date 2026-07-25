package com.waseel.prescription.model.dispense;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuggestedDrugsModel {

	@JsonProperty("memberPolicyConsumption")
	private MemberPolicyConsumption memberPolicyConsumption;

	@JsonProperty("prescriptionDrugs")
	private List<PrescriptionDrug> prescriptionDrugs;

	public MemberPolicyConsumption getMemberPolicyConsumption() {
		return memberPolicyConsumption;
	}

	public void setMemberPolicyConsumption(MemberPolicyConsumption memberPolicyConsumption) {
		this.memberPolicyConsumption = memberPolicyConsumption;
	}

	public List<PrescriptionDrug> getPrescriptionDrugs() {
		return prescriptionDrugs;
	}

	public void setPrescriptionDrugs(List<PrescriptionDrug> prescriptionDrugs) {
		this.prescriptionDrugs = prescriptionDrugs;
	}
}
