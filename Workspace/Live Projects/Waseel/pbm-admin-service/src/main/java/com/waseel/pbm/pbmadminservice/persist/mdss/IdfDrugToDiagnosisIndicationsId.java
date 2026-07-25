package com.waseel.pbm.pbmadminservice.persist.mdss;

import java.io.Serializable;

public class IdfDrugToDiagnosisIndicationsId implements Serializable {

	private static final long serialVersionUID = -1131625151931778341L;
	private String icdDiagnosisCode;
	private String serviceCode;

	public IdfDrugToDiagnosisIndicationsId() {
	}

	public IdfDrugToDiagnosisIndicationsId(Long id, String icdDiagnosisCode, String serviceCode) {
		super();
		this.icdDiagnosisCode = icdDiagnosisCode;
		this.serviceCode = serviceCode;
	}

	public String getIcdDiagnosisCode() {
		return icdDiagnosisCode;
	}

	public void setIcdDiagnosisCode(String iCDDiagnosisCode) {
		icdDiagnosisCode = iCDDiagnosisCode;
	}

	public String getServiceCode() {
		return this.serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof IdfDrugToDiagnosisIndicationsId))
			return false;
		IdfDrugToDiagnosisIndicationsId castOther = (IdfDrugToDiagnosisIndicationsId) other;

		return ((this.getIcdDiagnosisCode() == castOther.getIcdDiagnosisCode())
				|| (this.getIcdDiagnosisCode() != null && castOther.getIcdDiagnosisCode() != null
						&& this.getIcdDiagnosisCode().equals(castOther.getIcdDiagnosisCode())))
				&& ((this.getServiceCode() == castOther.getServiceCode())
						|| (this.getServiceCode() != null && castOther.getServiceCode() != null
								&& this.getServiceCode().equals(castOther.getServiceCode())));
	}

	public int hashCode() {
		int result = 17;
		result = 37 * result + (getIcdDiagnosisCode() == null ? 0 : this.getIcdDiagnosisCode().hashCode());
		result = 37 * result + (getServiceCode() == null ? 0 : this.getServiceCode().hashCode());
		return result;
	}

}