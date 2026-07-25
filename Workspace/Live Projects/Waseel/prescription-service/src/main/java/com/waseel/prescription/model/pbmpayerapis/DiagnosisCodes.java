package com.waseel.prescription.model.pbmpayerapis;

public class DiagnosisCodes {

	private String diagnosisCode;
	private String diagnosisType;

	public String getDiagnosisCode() {
		return diagnosisCode;
	}

	public void setDiagnosisCode(String diagnosisCode) {
		this.diagnosisCode = diagnosisCode;
	}

	public String getDiagnosisType() {
		return diagnosisType;
	}

	public void setDiagnosisType(String diagnosisType) {
		this.diagnosisType = diagnosisType;
	}

	public DiagnosisCodes() {
		super();
	}

	public DiagnosisCodes(String diagnosisCode, String diagnosisType) {
		super();
		this.diagnosisCode = diagnosisCode;
		this.diagnosisType = diagnosisType;
	}

}
