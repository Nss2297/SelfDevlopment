package com.waseel.prescription.persist.mdss;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "`CUSTOMIZATION_REQUEST_METADATA`", schema = "MDSS")
public class CustomizationRequestMetadata implements Serializable {

	private static final long serialVersionUID = 1087902552019883462L;

	@Id
	@GeneratedValue(generator = "CustomizationRequestMetadataSeq")
	@SequenceGenerator(name = "CustomizationRequestMetadataSeq", sequenceName = "PC_REQUESTS_METADATA_SEQ", allocationSize = 0, initialValue = 1)
	@Column(name = "CUSTOMIZATION_REQUESTS_ID")
	private long customizationRequestsId;

	@Column(name = "DRUG_CODE")
	private String drugCode;

	@Column(name = "DRUG_NAME")
	private String drugName;

	@Column(name = "IS_DELETED")
	private boolean isDeleted;

	@Temporal(TemporalType.DATE)
	@Column(name = "LAST_UPDATED_DATE")
	private Date lastUpdatedDate;

	@Column(name = "MODULE_NAME")
	private String moduleName;

	@Column(name = "PAYER_ID")
	private String payerId;

	@Column(name = "REJECTION_REASON")
	private String rejectionReason;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "IS_CUSTOMIZABLE")
	private boolean isCustomizable;

	@Column(name = "E_PRESCRIPTION_REF_NO")
	private String ePrescriptionRefNo;

	public long getCustomizationRequestsId() {
		return customizationRequestsId;
	}

	public String getDrugCode() {
		return drugCode;
	}

	public String getDrugName() {
		return drugName;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public String getModuleName() {
		return moduleName;
	}

	public String getPayerId() {
		return payerId;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public String getStatus() {
		return status;
	}

	public boolean isCustomizable() {
		return isCustomizable;
	}

	public void setCustomizationRequestsId(long customizationRequestsId) {
		this.customizationRequestsId = customizationRequestsId;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCustomizable(boolean isCustomizable) {
		this.isCustomizable = isCustomizable;
	}

	public String getePrescriptionRefNo() {
		return ePrescriptionRefNo;
	}

	public void setePrescriptionRefNo(String ePrescriptionRefNo) {
		this.ePrescriptionRefNo = ePrescriptionRefNo;
	}

	public CustomizationRequestMetadata() {
		super();
	}

	public CustomizationRequestMetadata(long customizationRequestsId, String drugCode, String drugName,
			boolean isDeleted, Date lastUpdatedDate, String moduleName, String payerId, String rejectionReason,
			String status, boolean isCustomizable) {
		super();
		this.customizationRequestsId = customizationRequestsId;
		this.drugCode = drugCode;
		this.drugName = drugName;
		this.isDeleted = isDeleted;
		this.lastUpdatedDate = lastUpdatedDate;
		this.moduleName = moduleName;
		this.payerId = payerId;
		this.rejectionReason = rejectionReason;
		this.status = status;
		this.isCustomizable = isCustomizable;
	}

}