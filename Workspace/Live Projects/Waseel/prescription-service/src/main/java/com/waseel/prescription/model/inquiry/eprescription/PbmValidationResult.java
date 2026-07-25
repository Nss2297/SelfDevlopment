package com.waseel.prescription.model.inquiry.eprescription;

import java.math.BigDecimal;
import java.util.List;

public class PbmValidationResult extends CommonInquiryModel {

	public PbmValidationResult() {
		super();
	}

	public PbmValidationResult(BigDecimal requestedAmount, BigDecimal approvedAmount, String status,
			List<EPrescriptionInquiryError> errors) {
		super(requestedAmount, approvedAmount, status, errors);
	}
}
