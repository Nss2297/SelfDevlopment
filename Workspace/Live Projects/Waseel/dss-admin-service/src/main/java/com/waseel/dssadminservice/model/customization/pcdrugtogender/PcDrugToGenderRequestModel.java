package com.waseel.dssadminservice.model.customization.pcdrugtogender;

import java.util.Date;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import com.waseel.dssadminservice.util.UserInfoUtil;
import com.waseel.dssadminservice.validator.customannotation.IsValidGender;
import com.waseel.dssadminservice.validator.customannotation.IsValidModuleName;
import com.waseel.dssadminservice.validator.customannotation.IsValidServiceStatus;
import com.waseel.dssadminservice.validator.customannotation.NoMoreThan20Length;
import com.waseel.dssadminservice.validator.customannotation.NoMoreThan250Length;
import com.waseel.dssadminservice.validator.customannotation.NoMoreThan500Length;

public class PcDrugToGenderRequestModel {

	@NotEmpty(message = "ServiceCode {emptyDataValidation}")
	@NoMoreThan250Length(message = "ServiceCode {noMoreThan250LengthValidation}")
	private String serviceCode;

	@IsValidGender(message = "{invalidGenderValidation}")
	private String gender;

	@NoMoreThan20Length(message = "PayerId {noMoreThan20LengthValidation}")
	private String payerId;

	@IsValidModuleName(message = "{moduleNameValidation}")
	private String moduleName;

	@IsValidServiceStatus(message = "{serviceStatusValidation}")
	private String serviceStatus;

	@NoMoreThan500Length(message = "AdditionalRejectionReason {noMoreThan500LengthValidation}")
	private String additionalRejectionReason;

	private Date updateDateAndTime;

	private String scientificCode;
	private Integer rowNumber;

	public Integer getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = StringUtils.isNotBlank(serviceCode) ? serviceCode.trim() : serviceCode;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = StringUtils.isNotBlank(gender) ? gender.trim().toUpperCase() : gender;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = StringUtils.isNotBlank(payerId) ? payerId.trim() : payerId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = StringUtils.isNotBlank(moduleName) ? moduleName.trim().toUpperCase() : moduleName;
	}

	public String getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = StringUtils.isNotBlank(serviceStatus) ? serviceStatus.trim().toUpperCase() : serviceStatus;
	}

	public String getAdditionalRejectionReason() {
		return additionalRejectionReason;
	}

	public void setAdditionalRejectionReason(String additionalRejectionReason) {
		this.additionalRejectionReason = StringUtils.isNotBlank(additionalRejectionReason)
				? additionalRejectionReason.trim()
				: additionalRejectionReason;
	}

	public Date getUpdateDateAndTime() {
		return updateDateAndTime;
	}

	public void setUpdateDateAndTime(Date updateDateAndTime) {
		this.updateDateAndTime = updateDateAndTime;
	}

	public String getScientificCode() {
		return scientificCode;
	}

	public void setScientificCode(String scientificCode) {
		this.scientificCode = StringUtils.isNotBlank(scientificCode) ? scientificCode.trim() : scientificCode;
	}

	public PcDrugToGenderRequestModel() {
	}

	public PcDrugToGenderRequestModel(String serviceCode, String gender, String payerId,
			String serviceStatus, String additionalRejectionReason, String moduleName,
			Integer rowNumber) {
		super();
		this.serviceCode = serviceCode;
		this.gender = gender;
		this.payerId = payerId;
		this.serviceStatus = serviceStatus;
		this.additionalRejectionReason = additionalRejectionReason;
		this.moduleName = moduleName;
		this.rowNumber = rowNumber;
	}

	@AssertTrue(message = "PayerId {emptyDataValidation}")
	public boolean isPayerIdValid() {
		String category = UserInfoUtil.getAccCategory(SecurityContextHolder.getContext().getAuthentication());
		if (category != null && StringUtils.isNotBlank(category) && category.equalsIgnoreCase("payer")) {
			return true;
		}
		return StringUtils.isNotBlank(payerId);
	}
}
