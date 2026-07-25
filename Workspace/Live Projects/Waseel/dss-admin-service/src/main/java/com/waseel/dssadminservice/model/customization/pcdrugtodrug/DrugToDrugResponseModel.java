package com.waseel.dssadminservice.model.customization.pcdrugtodrug;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class DrugToDrugResponseModel {

	private Long id;
	private String serviceCode;
	private String interactedServiceCode;
	private String payerId;
	private String serviceStatus;
	private String moduleName;
	private String additionalRejectionReason;
	private Date lastUpdateDateAndTime;
	private String scientificCode;

	public DrugToDrugResponseModel() {
	}

	public DrugToDrugResponseModel(Long id, String serviceCode, String interactedServiceCode, String payerId,
			String serviceStatus, String moduleName, String additionalRejectionReason, Date lastUpdateDateAndTime) {
		super();
		this.id = id;
		this.serviceCode = serviceCode;
		this.interactedServiceCode = interactedServiceCode;
		this.payerId = payerId;
		this.serviceStatus = serviceStatus;
		this.moduleName = moduleName;
		this.additionalRejectionReason = additionalRejectionReason;
		this.lastUpdateDateAndTime = lastUpdateDateAndTime;
	}

	public String getScientificCode() {
		return scientificCode;
	}

	public void setScientificCode(String scientificCode) {
		this.scientificCode = scientificCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getInteractedServiceCode() {
		return interactedServiceCode;
	}

	public void setInteractedServiceCode(String interactedServiceCode) {
		this.interactedServiceCode = interactedServiceCode;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getAdditionalRejectionReason() {
		return additionalRejectionReason;
	}

	public void setAdditionalRejectionReason(String additionalRejectionReason) {
		this.additionalRejectionReason = additionalRejectionReason;
	}

	public Date getLastUpdateDateAndTime() {
		return lastUpdateDateAndTime;
	}

	public void setLastUpdateDateAndTime(Date lastUpdateDateAndTime) {
		this.lastUpdateDateAndTime = lastUpdateDateAndTime;
	}
}
