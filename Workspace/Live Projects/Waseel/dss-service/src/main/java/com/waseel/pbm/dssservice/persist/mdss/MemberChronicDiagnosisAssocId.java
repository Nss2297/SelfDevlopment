package com.waseel.pbm.dssservice.persist.mdss;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * MemberChronicDiagnosisAssocId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class MemberChronicDiagnosisAssocId implements java.io.Serializable {

	// Fields

	private Integer memberChronicDzAssocId;
	private String diagnosisCode;

	// Constructors

	/** default constructor */
	public MemberChronicDiagnosisAssocId() {
	}

	/** full constructor */
	public MemberChronicDiagnosisAssocId(Integer memberChronicDzAssocId, String diagnosisCode) {
		this.memberChronicDzAssocId = memberChronicDzAssocId;
		this.diagnosisCode = diagnosisCode;
	}

	// Property accessors

	@Column(name = "MEMBER_CHRONIC_DZ_ASSOC_ID", nullable = false, precision = 0)

	public Integer getMemberChronicDzAssocId() {
		return this.memberChronicDzAssocId;
	}

	public void setMemberChronicDzAssocId(Integer memberChronicDzAssocId) {
		this.memberChronicDzAssocId = memberChronicDzAssocId;
	}

	@Column(name = "DIAGNOSIS_CODE", nullable = false, length = 10)

	public String getDiagnosisCode() {
		return this.diagnosisCode;
	}

	public void setDiagnosisCode(String diagnosisCode) {
		this.diagnosisCode = diagnosisCode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof MemberChronicDiagnosisAssocId))
			return false;
		MemberChronicDiagnosisAssocId castOther = (MemberChronicDiagnosisAssocId) other;

		return ((this.getMemberChronicDzAssocId() == castOther.getMemberChronicDzAssocId())
				|| (this.getMemberChronicDzAssocId() != null && castOther.getMemberChronicDzAssocId() != null
						&& this.getMemberChronicDzAssocId().equals(castOther.getMemberChronicDzAssocId())))
				&& ((this.getDiagnosisCode() == castOther.getDiagnosisCode())
						|| (this.getDiagnosisCode() != null && castOther.getDiagnosisCode() != null
								&& this.getDiagnosisCode().equals(castOther.getDiagnosisCode())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getMemberChronicDzAssocId() == null ? 0 : this.getMemberChronicDzAssocId().hashCode());
		result = 37 * result + (getDiagnosisCode() == null ? 0 : this.getDiagnosisCode().hashCode());
		return result;
	}

}