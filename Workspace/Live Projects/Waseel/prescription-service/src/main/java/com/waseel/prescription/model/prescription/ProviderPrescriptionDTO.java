package com.waseel.prescription.model.prescription;

import java.io.Serializable;
import java.util.Date;

public interface ProviderPrescriptionDTO extends Serializable {

	String getReferenceNo();

	String getStatus();

	Date getDateAndTime();

	String getMemberId();

	String getIdNumber();

	String getPolicyNumber();

	String getMemberName();

	String getInsurance();

	String getPayerId();
}
