package com.waseel.pbm.payercustomizationservice.model;

public class CustomizationRequestModel {

	private String ePrescriptionReferenceNo;
	private String drugCode;
	private String drugName;
	private String gender;
	private String rejectionCategory;
	private String moduleName;
	private String status;
	private String rejectionReason;

	public String getePrescriptionReferenceNo() {
		return ePrescriptionReferenceNo;
	}

	public String getDrugCode() {
		return drugCode;
	}

	public String getDrugName() {
		return drugName;
	}

	public String getGender() {
		return gender;
	}

	public String getRejectionCategory() {
		return rejectionCategory;
	}

	public String getModuleName() {
		return moduleName;
	}

	public String getStatus() {
		return status;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setePrescriptionReferenceNo(String ePrescriptionReferenceNo) {
		this.ePrescriptionReferenceNo = ePrescriptionReferenceNo;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setRejectionCategory(String rejectionCategory) {
		this.rejectionCategory = rejectionCategory;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public CustomizationRequestModel() {
		super();
	}

	@Override
	public String toString() {
		return "CustomizationRequestModel [ePrescriptionReferenceNo=" + ePrescriptionReferenceNo + ", drugCode="
				+ drugCode + ", drugName=" + drugName + ", gender=" + gender + ", rejectionCategory="
				+ rejectionCategory + ", moduleName=" + moduleName + ", status=" + status + ", rejectionReason="
				+ rejectionReason + ", getePrescriptionReferenceNo()=" + getePrescriptionReferenceNo()
				+ ", getDrugCode()=" + getDrugCode() + ", getDrugName()=" + getDrugName() + ", getGender()="
				+ getGender() + ", getRejectionCategory()=" + getRejectionCategory() + ", getModuleName()="
				+ getModuleName() + ", getStatus()=" + getStatus() + ", getRejectionReason()=" + getRejectionReason()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}

	public CustomizationRequestModel(String ePrescriptionReferenceNo, String drugCode, String drugName,
			String moduleName, String rejectionReason) {
		super();
		this.ePrescriptionReferenceNo = ePrescriptionReferenceNo;
		this.drugCode = drugCode;
		this.drugName = drugName;
		this.moduleName = moduleName;
		this.rejectionReason = rejectionReason;
	}

}
