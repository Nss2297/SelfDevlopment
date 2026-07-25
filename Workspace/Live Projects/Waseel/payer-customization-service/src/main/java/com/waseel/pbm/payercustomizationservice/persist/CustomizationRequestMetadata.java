package com.waseel.pbm.payercustomizationservice.persist;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.waseel.pbm.payercustomizationservice.enums.CustomizationRequestMetaDataStatus;

/**
 * The persistent class for the CUSTOMIZATION_REQUEST_METADATA database table.
 */
@Entity
@Table(name = "CUSTOMIZATION_REQUEST_METADATA", schema = "MDSS")
@NamedQuery(name = "CustomizationRequestMetadata.findAll", query = "SELECT c FROM CustomizationRequestMetadata c")
public class CustomizationRequestMetadata implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "CustomizationRequestMetadataSeq")
	@SequenceGenerator(name = "CustomizationRequestMetadataSeq", sequenceName = "PC_REQUESTS_METADATA_SEQ", allocationSize = 0, initialValue = 1)
	@Column(name = "CUSTOMIZATION_REQUESTS_ID")
	private long customizationRequestsId;

	@Column(name = "DRUG_CODE")
	private String drugCode;

	@Column(name = "DRUG_NAME")
	private String drugName;

	@Column(name = "IS_DELETED", nullable = false, length = 1)
	private boolean isDeleted = false;

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
	private String status = CustomizationRequestMetaDataStatus.PC_PENDING_REQUEST.value();

	@Column(name = "E_PRESCRIPTION_REF_NO")
	private String ePrescriptionReferenceNumber;

	// bi-directional one-to-one association to CustomizationRequestDetail
	@OneToMany(mappedBy = "customizationRequestMetadata", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<CustomizationRequestDetail> customizationRequestDetailList;

	public CustomizationRequestMetadata() {
	}

	public long getCustomizationRequestsId() {
		return this.customizationRequestsId;
	}

	public void setCustomizationRequestsId(long customizationRequestsId) {
		this.customizationRequestsId = customizationRequestsId;
	}

	public String getDrugCode() {
		return this.drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public String getDrugName() {
		return this.drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getPayerId() {
		return this.payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getRejectionReason() {
		return this.rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<CustomizationRequestDetail> getCustomizationRequestDetailList() {
		return customizationRequestDetailList;
	}

	public void setCustomizationRequestDetailList(List<CustomizationRequestDetail> customizationRequestDetailList) {
		this.customizationRequestDetailList = customizationRequestDetailList;
	}

	public String getePrescriptionReferenceNumber() {
		return ePrescriptionReferenceNumber;
	}

	public void setePrescriptionReferenceNumber(String ePrescriptionReferenceNumber) {
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
	}

	public CustomizationRequestMetadata(long customizationRequestsId, String drugCode, String drugName,
			boolean isDeleted, Date lastUpdatedDate, String moduleName, String payerId, String rejectionReason,
			String status, List<CustomizationRequestDetail> customizationRequestDetailList) {
		this.customizationRequestsId = customizationRequestsId;
		this.drugCode = drugCode;
		this.drugName = drugName;
		this.isDeleted = isDeleted;
		this.lastUpdatedDate = lastUpdatedDate;
		this.moduleName = moduleName;
		this.payerId = payerId;
		this.rejectionReason = rejectionReason;
		this.status = status;
		this.customizationRequestDetailList = customizationRequestDetailList;
	}

	public CustomizationRequestMetadata(long customizationRequestsId, String drugCode, String drugName,
			boolean isDeleted, Date lastUpdatedDate, String moduleName, String payerId, String rejectionReason,
			String status) {
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
	}
}