package com.waseel.pbmpayerapisservice.model;

import javax.validation.constraints.NotEmpty;

import com.waseel.pbmpayerapisservice.validator.customannotation.IsValidDiagnosisType;
import com.waseel.pbmpayerapisservice.validator.customannotation.NoMoreThan10Length;
import com.waseel.pbmpayerapisservice.validator.customannotation.NoMoreThan30Length;

public class DiagnosisCodes {

    @NotEmpty(message = "diagnosisCode {notEmptyValidation}")
    @NoMoreThan10Length(message = "diagnosisCode {noMoreThan10LengthValidation}")
	private String diagnosisCode;

    @NotEmpty(message = "diagnosisType {notEmptyValidation}")
    @NoMoreThan30Length(message = "diagnosisType {noMoreThan30LengthValidation}")
    @IsValidDiagnosisType(message = "{diagnosisTypeValidation}")
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
}
