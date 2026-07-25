package com.waseel.pbm.payercustomizationservice.model;

import java.util.Date;
import java.util.List;

public class CustomizationListingResponse {

	private Date lastUpdatedDate;
	private String ePrescriptionReferenceNo;
	private String drugCode;
	private String drugName;
	private String moduleName;
	private String status;
	private Long customizationRequestId;
	private List<CustomizationDetails> customizationDetails;

	public CustomizationListingResponse(Date lastUpdatedDate, String ePrescriptionReferenceNo, String drugCode,
			String drugName, String moduleName, String status, Long customizationRequestId,
			List<CustomizationDetails> customizationDetails) {
		super();
		this.lastUpdatedDate = lastUpdatedDate;
		this.ePrescriptionReferenceNo = ePrescriptionReferenceNo;
		this.drugCode = drugCode;
		this.drugName = drugName;
		this.moduleName = moduleName;
		this.status = status;
		this.customizationRequestId = customizationRequestId;
		this.customizationDetails = customizationDetails;
	}

	public String getePrescriptionReferenceNo() {
		return ePrescriptionReferenceNo;
	}

	public void setePrescriptionReferenceNo(String ePrescriptionReferenceNo) {
		this.ePrescriptionReferenceNo = ePrescriptionReferenceNo;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public CustomizationListingResponse() {
		super();
	}

	public List<CustomizationDetails> getCustomizationDetails() {
		return customizationDetails;
	}

	public void setCustomizationDetails(List<CustomizationDetails> customizationDetails) {
		this.customizationDetails = customizationDetails;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getCustomizationRequestId() {
		return customizationRequestId;
	}

	public void setCustomizationRequestId(Long customizationRequestId) {
		this.customizationRequestId = customizationRequestId;
	}

	public class CustomizationDetails {

		public CustomizationDetails() {
			super();
		}

		public CustomizationDetails(String lable, String value) {
			super();
			this.lable = lable;
			this.value = value;
		}

		private String lable;
		private String value;

		public String getLable() {
			return lable;
		}

		public void setLable(String lable) {
			this.lable = lable;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

	}
}
