package com.waseel.pbm.payercustomizationservice.persist;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The persistent class for the CUSTOMIZATION_REQUEST_DETAILS database table.
 * 
 */
@Entity
@Table(name = "CUSTOMIZATION_REQUEST_DETAILS", schema = "MDSS")
@NamedQuery(name = "CustomizationRequestDetail.findAll", query = "SELECT c FROM CustomizationRequestDetail c")
public class CustomizationRequestDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "CustomizationRequestDetailSeq")
	@SequenceGenerator(name = "CustomizationRequestDetailSeq", sequenceName = "PC_REQUESTS_DETAILS_SEQ", allocationSize = 0, initialValue = 1)
	@Column(name = "CUSTOMIZATION_DETAILS_ID")
	private long customizationDetailsId;

	@Column(name = "CUSTOMIZATION_REQUEST_ID")
	private long customizationRequestsId;

	@Column(name = "CUSTOMIZATION_KEY")
	private String customizationKey;

	@Column(name = "CUSTOMIZATION_VALUE")
	private String customizationValue;

	@Column(name = "CUSTOMIZATION_LABEL")
	private String customizationLabel;

	// bi-directional many-to-one association to CustomizationRequestMetadata
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMIZATION_REQUEST_ID", referencedColumnName = "CUSTOMIZATION_REQUESTS_ID", insertable = false, updatable = false)
	private CustomizationRequestMetadata customizationRequestMetadata;

	public CustomizationRequestDetail() {
	}

	public long getCustomizationRequestsId() {
		return this.customizationRequestsId;
	}

	public void setCustomizationRequestsId(long customizationRequestsId) {
		this.customizationRequestsId = customizationRequestsId;
	}

	public long getCustomizationDetailsId() {
		return customizationDetailsId;
	}

	public void setCustomizationDetailsId(long customizationDetailsId) {
		this.customizationDetailsId = customizationDetailsId;
	}

	public String getCustomizationKey() {
		return this.customizationKey;
	}

	public void setCustomizationKey(String customizationKey) {
		this.customizationKey = customizationKey;
	}

	public String getCustomizationValue() {
		return this.customizationValue;
	}

	public void setCustomizationValue(String customizationValue) {
		this.customizationValue = customizationValue;
	}

	public CustomizationRequestMetadata getCustomizationRequestMetadata() {
		return this.customizationRequestMetadata;
	}

	public void setCustomizationRequestMetadata(CustomizationRequestMetadata customizationRequestMetadata) {
		this.customizationRequestMetadata = customizationRequestMetadata;
	}

	public String getCustomizationLabel() {
		return customizationLabel;
	}

	public void setCustomizationLabel(String customizationLabel) {
		this.customizationLabel = customizationLabel;
	}

	public CustomizationRequestDetail(long customizationDetailsId, long customizationRequestsId,
			String customizationKey, String customizationValue, String customizationLabel) {
		super();
		this.customizationDetailsId = customizationDetailsId;
		this.customizationRequestsId = customizationRequestsId;
		this.customizationKey = customizationKey;
		this.customizationValue = customizationValue;
		this.customizationLabel = customizationLabel;
	}

	public CustomizationRequestDetail(long customizationRequestsId, String customizationKey, String customizationValue,
			String customizationLabel) {
		super();
		this.customizationRequestsId = customizationRequestsId;
		this.customizationKey = customizationKey;
		this.customizationValue = customizationValue;
		this.customizationLabel = customizationLabel;
	}
}