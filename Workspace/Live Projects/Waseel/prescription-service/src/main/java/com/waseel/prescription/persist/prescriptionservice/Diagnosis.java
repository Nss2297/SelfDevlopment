package com.waseel.prescription.persist.prescriptionservice;

import com.waseel.prescription.persist.hira.ICDDiagnosis;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Diagnosis", schema = "PRESCRIPTION_SERVICE")
public class Diagnosis implements Serializable {

	private static final long serialVersionUID = -6212819971639037293L;

	@EmbeddedId
	@AttributeOverride(name = "requestId", column = @Column(name = "RequestID", length = 100, nullable = false))
	@AttributeOverride(name = "diagnosisCode", column = @Column(name = "DiagnosisCode", length = 10, nullable = false))
	private DiagnosisId diagnosisId;

	@Column(name = "DiagnosisType", length = 30, nullable = false)
	private String diagnosisType;

	@Column(name = "IsDeleted", length = 1, nullable = false)
	private boolean isDeleted;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DiagnosisCode", referencedColumnName = "ICDDiagnosisCode", insertable = false, updatable = false)
	private ICDDiagnosis icdDiagnosis;

	public ICDDiagnosis getIcdDiagnosis() {
		return icdDiagnosis;
	}

	public void setIcdDiagnosis(ICDDiagnosis icdDiagnosis) {
		this.icdDiagnosis = icdDiagnosis;
	}

	public DiagnosisId getDiagnosisId() {
		return diagnosisId;
	}

	public void setDiagnosisId(DiagnosisId diagnosisId) {
		this.diagnosisId = diagnosisId;
	}

	public String getDiagnosisType() {
		return diagnosisType;
	}

	public void setDiagnosisType(String diagnosisType) {
		this.diagnosisType = diagnosisType;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

}
