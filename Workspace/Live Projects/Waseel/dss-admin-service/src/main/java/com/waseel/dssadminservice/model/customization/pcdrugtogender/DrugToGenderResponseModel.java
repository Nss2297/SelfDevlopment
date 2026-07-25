package com.waseel.dssadminservice.model.customization.pcdrugtogender;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class DrugToGenderResponseModel {

	private Long id;
	private String serviceCode;
	private String gender;
	private String payerId;
	private String serviceStatus;
	private String moduleName;
	private String rejectionReason;
	private Date updateDateAndTime;
	private String ScientificCode;

	public DrugToGenderResponseModel() {
	}

	public DrugToGenderResponseModel(Long id, String serviceCode, String gender, String payerId, String serviceStatus,
			String moduleName, String rejectionReason, Date updateDateAndTime) {
		super();
		this.id = id;
		this.serviceCode = serviceCode;
		this.gender = gender;
		this.payerId = payerId;
		this.serviceStatus = serviceStatus;
		this.moduleName = moduleName;
		this.rejectionReason = rejectionReason;
		this.updateDateAndTime = updateDateAndTime;
	}

	public String getScientificCode() {
		return ScientificCode;
	}

	public void setScientificCode(String scientificCode) {
		ScientificCode = scientificCode;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public Date getUpdateDateAndTime() {
		return updateDateAndTime;
	}

	public void setUpdateDateAndTime(Date updateDateAndTime) {
		this.updateDateAndTime = updateDateAndTime;
	}
}
