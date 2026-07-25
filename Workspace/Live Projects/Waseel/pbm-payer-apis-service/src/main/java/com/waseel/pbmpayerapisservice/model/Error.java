package com.waseel.pbmpayerapisservice.model;

import com.waseel.pbmpayerapisservice.validator.customannotation.NoMoreThan2500Length;
import com.waseel.pbmpayerapisservice.validator.customannotation.NoMoreThan30Length;

public class Error {

    @NoMoreThan30Length(message = "denialCode {noMoreThan30LengthValidation}")
	private String denialCode;
    
    @NoMoreThan2500Length(message = "rejectionReason {noMoreThan2500LengthValidation}")
	private String rejectionReason;

	public String getDenialCode() {
		return denialCode;
	}

	public void setDenialCode(String denialCode) {
		this.denialCode = denialCode;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

}
