package com.waseel.pbm.fdbvalidationservice.persist.mdss;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable

public class FdbNotExistDiagnosisId implements java.io.Serializable {

	// Fields

	private String icdCode;
	private String requestId;

	// Constructors

	/** default constructor */
	public FdbNotExistDiagnosisId() {
	}

	/** full constructor */
	public FdbNotExistDiagnosisId(String icdCode, String requestId) {
		this.icdCode = icdCode;
		this.requestId = requestId;
	}

	// Property accessors

	@Column(name = "ICD_CODE", nullable = false, length = 10)

	public String getIcdCode() {
		return this.icdCode;
	}

	public void setIcdCode(String icdCode) {
		this.icdCode = icdCode;
	}

	@Column(name = "REQUEST_ID", nullable = false, length = 100)

	public String getRequestId() {
		return this.requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof FdbNotExistDiagnosisId))
			return false;
		FdbNotExistDiagnosisId castOther = (FdbNotExistDiagnosisId) other;

		return ((this.getIcdCode() == castOther.getIcdCode()) || (this.getIcdCode() != null
				&& castOther.getIcdCode() != null && this.getIcdCode().equals(castOther.getIcdCode())))
				&& ((this.getRequestId() == castOther.getRequestId()) || (this.getRequestId() != null
						&& castOther.getRequestId() != null && this.getRequestId().equals(castOther.getRequestId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getIcdCode() == null ? 0 : this.getIcdCode().hashCode());
		result = 37 * result + (getRequestId() == null ? 0 : this.getRequestId().hashCode());
		return result;
	}

}