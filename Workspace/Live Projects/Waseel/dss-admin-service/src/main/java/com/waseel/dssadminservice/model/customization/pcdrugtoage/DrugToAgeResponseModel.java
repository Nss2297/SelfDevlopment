package com.waseel.dssadminservice.model.customization.pcdrugtoage;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class DrugToAgeResponseModel {

	private Long id;
	private String serviceCode;
	private Long fromAgeInDays;
	private Long toAgeInDays;
	private String payerId;
	private String serviceStatus;
	private String moduleName;
	private String rejectionReason;
	private Date updateDateAndTime;

	public DrugToAgeResponseModel(Long id, String serviceCode, Long fromAgeInDays, Long toAgeInDays, String payerId,
			String serviceStatus, String moduleName, String rejectionReason, Date updateDateAndTime) {
		super();
		this.id = id;
		this.serviceCode = serviceCode;
		this.fromAgeInDays = fromAgeInDays;
		this.toAgeInDays = toAgeInDays;
		this.payerId = payerId;
		this.serviceStatus = serviceStatus;
		this.moduleName = moduleName;
		this.rejectionReason = rejectionReason;
		this.updateDateAndTime = updateDateAndTime;
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

	public Long getFromAgeInDays() {
		return fromAgeInDays;
	}

	public void setFromAgeInDays(Long fromAgeInDays) {
		this.fromAgeInDays = fromAgeInDays;
	}

	public Long getToAgeInDays() {
		return toAgeInDays;
	}

	public void setToAgeInDays(Long toAgeInDays) {
		this.toAgeInDays = toAgeInDays;
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

	public Date getUpdateDateAndTime() {
		return updateDateAndTime;
	}

	public void setUpdateDateAndTime(Date updateDateAndTime) {
		this.updateDateAndTime = updateDateAndTime;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}
}
