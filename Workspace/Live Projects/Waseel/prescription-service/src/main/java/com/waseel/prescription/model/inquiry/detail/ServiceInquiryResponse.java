package com.waseel.prescription.model.inquiry.detail;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waseel.prescription.model.enums.CommonWords;
import com.waseel.prescription.model.prescription.ServiceResponse;

public class ServiceInquiryResponse extends ServiceResponse {

	@JsonProperty("suggestedDrugs")
	private List<SuggestedDrugInquiry> suggestedDrugs;

	public ServiceInquiryResponse() {
		super();
	}

	public ServiceInquiryResponse(String scientificCode, String drugCode, String unitType, Double unitPrice,
			BigDecimal quantity, BigDecimal requestedAmount, BigDecimal approvedAmount, Double discount,
			BigDecimal patientShare, BigDecimal net, String status, String statusDescription) {
		super(scientificCode, drugCode, unitType, unitPrice, quantity, requestedAmount, approvedAmount, discount,
				patientShare, net, status, statusDescription);
		if (this.getDrugCode().equalsIgnoreCase(CommonWords.UNDEFINED.value())) {
			this.setDrugCode(null);
		}
	}

	public List<SuggestedDrugInquiry> getSuggestedDrugs() {
		return suggestedDrugs;
	}

	public void setSuggestedDrugs(List<SuggestedDrugInquiry> suggestedDrugs) {
		this.suggestedDrugs = suggestedDrugs;
	}
}
