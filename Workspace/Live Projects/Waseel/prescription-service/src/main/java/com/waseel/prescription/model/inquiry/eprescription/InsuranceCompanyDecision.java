package com.waseel.prescription.model.inquiry.eprescription;

import java.math.BigDecimal;
import java.util.List;

public class InsuranceCompanyDecision extends CommonInquiryModel {

	public InsuranceCompanyDecision() {
		super();
	}

	public InsuranceCompanyDecision(BigDecimal requestedAmount, BigDecimal approvedAmount, String status,
			List<EPrescriptionInquiryError> errors) {
		super(requestedAmount, approvedAmount, status, errors);
	}
}
