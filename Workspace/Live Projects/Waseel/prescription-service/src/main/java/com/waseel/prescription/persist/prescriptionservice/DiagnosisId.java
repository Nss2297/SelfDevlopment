package com.waseel.prescription.persist.prescriptionservice;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class DiagnosisId implements Serializable {

	private static final long serialVersionUID = 3577365283160427454L;

	private String diagnosisCode;

	private String requestId;

	public String getDiagnosisCode() {
		return diagnosisCode;
	}

	public void setDiagnosisCode(String diagnosisCode) {
		this.diagnosisCode = diagnosisCode;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

}
