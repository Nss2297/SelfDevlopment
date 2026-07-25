package com.waseel.prescription.model.prescription;

import java.io.Serializable;

public interface CustomizationRequests extends Serializable {

	String getDrugCode();

	String getIsCustomizable();

	String getKeyValue();
}
