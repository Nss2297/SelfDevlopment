package com.waseel.prescription.model.prescription;

import java.io.Serializable;

public interface RejectionReasons extends Serializable {

	String getDrugCode();

	String getDenialCode();

	String getDrugName();

	String getRejectionReason();

	String getScientificCode();

	String getScientificName();
}
