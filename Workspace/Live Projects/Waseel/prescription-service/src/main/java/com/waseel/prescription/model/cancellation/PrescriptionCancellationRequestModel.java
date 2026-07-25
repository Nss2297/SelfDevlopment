package com.waseel.prescription.model.cancellation;

import com.waseel.prescription.model.common.CommonRequestModel;

public class PrescriptionCancellationRequestModel extends CommonRequestModel {

	public PrescriptionCancellationRequestModel(String payerId, String ePrescriptionReferenceNumber) {
		super(payerId, ePrescriptionReferenceNumber);
	}

	public PrescriptionCancellationRequestModel() {
		super();
	}
}
