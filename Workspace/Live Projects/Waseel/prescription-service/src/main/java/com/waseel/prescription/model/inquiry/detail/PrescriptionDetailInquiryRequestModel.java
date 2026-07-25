package com.waseel.prescription.model.inquiry.detail;

import com.waseel.prescription.model.common.CommonRequestModel;

public class PrescriptionDetailInquiryRequestModel extends CommonRequestModel {
	
	public PrescriptionDetailInquiryRequestModel() {
		super();
	}

	public PrescriptionDetailInquiryRequestModel(String payerId, String ePrescriptionReferenceNumber) {
		super(payerId, ePrescriptionReferenceNumber);
	}
}
