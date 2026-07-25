package com.waseel.pbm.fdbvalidationservice.persist.mdss;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "FDB_NOT_EXIST_DIAGNOSIS", schema = "MDSS")

public class FdbNotExistDiagnosis implements java.io.Serializable {

	// Fields

	private FdbNotExistDiagnosisId id;

	// Constructors

	/** default constructor */
	public FdbNotExistDiagnosis() {
	}

	/** full constructor */
	public FdbNotExistDiagnosis(FdbNotExistDiagnosisId id) {
		this.id = id;
	}

	// Property accessors
	@EmbeddedId

	@AttributeOverrides({
			@AttributeOverride(name = "icdCode", column = @Column(name = "ICD_CODE", nullable = false, length = 10)),
			@AttributeOverride(name = "requestId", column = @Column(name = "REQUEST_ID", nullable = false, length = 100)) })

	public FdbNotExistDiagnosisId getId() {
		return this.id;
	}

	public void setId(FdbNotExistDiagnosisId id) {
		this.id = id;
	}

}