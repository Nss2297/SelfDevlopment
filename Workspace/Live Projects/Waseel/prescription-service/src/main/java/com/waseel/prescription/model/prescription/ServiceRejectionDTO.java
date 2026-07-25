package com.waseel.prescription.model.prescription;

import java.io.Serializable;

public interface ServiceRejectionDTO extends Serializable {

	Long getId();

	String getDrugCode();

	String getDenialCode();

	String getRejectionReason();

	String getRequestId();

	Long getServiceResponseId();

	String getEligibilityReferenceNumber();

	String getIsModifiedByPayer();

	String getScientificCode();
}
