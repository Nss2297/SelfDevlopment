package com.waseel.prescription.model.prescription;

import java.io.Serializable;

public interface ServiceRejections extends Serializable {

	String getDrugCode();

	String getDenialCode();

	String getRequestId();

	String getDrugName();

	String getRejectionReason();

	String getIsDeleted();

	String getPayerId();

	String getIsCustomizable();
	
	String getKeyValue();
}
