package com.waseel.dssadminservice.model.customization.pcdrugtoage;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waseel.dssadminservice.enums.AccountCategory;
import com.waseel.dssadminservice.util.UserInfoUtil;
import com.waseel.dssadminservice.validator.customannotation.IsDecimal;
import com.waseel.dssadminservice.validator.customannotation.IsNumber;
import com.waseel.dssadminservice.validator.customannotation.IsValidModuleName;
import com.waseel.dssadminservice.validator.customannotation.IsValidServiceStatus;
import com.waseel.dssadminservice.validator.customannotation.NoMoreThan20Length;
import com.waseel.dssadminservice.validator.customannotation.NoMoreThan250Length;
import com.waseel.dssadminservice.validator.customannotation.NoMoreThan500Length;
import com.waseel.dssadminservice.validator.customannotation.NoMoreThan50Length;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PcDrugToAgeRequestModel implements Serializable {

	private static final long serialVersionUID = 3632971278894616803L;

	@NotEmpty(message = "ServiceCode {emptyDataValidation}")
	@NoMoreThan250Length(message = "ServiceCode {noMoreThan250LengthValidation}")
	private String serviceCode;

	@NotEmpty(message = "From Age(in days) {emptyDataValidation}")
	@IsNumber(message = "From Age(in days) {notANumberValidation}")
	@IsDecimal(message = "From Age(in days) {noDecimalValidation}")
	@NoMoreThan20Length(message = "From Age(in days) {noMoreThan20DigitsValidation}")
	@DecimalMin(value = "0", inclusive = true, message = "From Age(in days) {valueNotLessThanZero}")
	private String fromAgeInDays;

	@NotEmpty(message = "To Age(in days) {emptyDataValidation}")
	@IsNumber(message = "To Age(in days) {notANumberValidation}")
	@IsDecimal(message = "To Age(in days) {noDecimalValidation}")
	@NoMoreThan20Length(message = "To Age(in days) {noMoreThan20DigitsValidation}")
	@DecimalMin(value = "0", inclusive = true, message = "To Age(in days) {valueNotLessThanZero}")
	private String toAgeInDays;

	@NoMoreThan20Length(message = "Payer Id {noMoreThan20LengthValidation}")
	private String payerId;

	@NotEmpty(message = "Service Status {emptyDataValidation}")
	@NoMoreThan50Length(message = "Service Status {noMoreThan50LengthValidation}")
	@IsValidServiceStatus(message = "{serviceStatusValidation}")
	private String serviceStatus;

	@NoMoreThan500Length(message = "AdditionalRejectionReason {noMoreThan500LengthValidation}")
	private String additionalRejectionReason;

	@NotEmpty(message = "Module Name {emptyDataValidation}")
	@NoMoreThan20Length(message = "Module Name  {noMoreThan20LengthValidation}")
	@IsValidModuleName(message = "{moduleNameValidation}")
	private String moduleName;

	private Integer rowNumber;
	private String scientificCode;

	public String getScientificCode() {
		return scientificCode;
	}

	public void setScientificCode(String scientificCode) {
		this.scientificCode = scientificCode;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public String getFromAgeInDays() {
		return fromAgeInDays;
	}

	public String getToAgeInDays() {
		return toAgeInDays;
	}

	public String getPayerId() {
		return payerId;
	}

	public String getServiceStatus() {
		return serviceStatus;
	}

	public String getAdditionalRejectionReason() {
		return additionalRejectionReason;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = StringUtils.isNotBlank(serviceCode) ? serviceCode.trim() : serviceCode;
	}

	public void setFromAgeInDays(String fromAgeInDays) {
		this.fromAgeInDays = StringUtils.isNotBlank(fromAgeInDays) ? fromAgeInDays.trim() : fromAgeInDays;
	}

	public void setToAgeInDays(String toAgeInDays) {
		this.toAgeInDays = StringUtils.isNotBlank(toAgeInDays) ? toAgeInDays.trim() : toAgeInDays;
	}

	public void setPayerId(String payerId) {
		this.payerId = StringUtils.isNotBlank(payerId) ? payerId.trim() : payerId;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = StringUtils.isNotBlank(serviceStatus) ? serviceStatus.trim().toUpperCase() : serviceStatus;
	}

	public void setAdditionalRejectionReason(String additionalRejectionReason) {
		this.additionalRejectionReason = StringUtils.isNotBlank(additionalRejectionReason)
				? additionalRejectionReason.trim()
				: additionalRejectionReason;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = StringUtils.isNotBlank(moduleName) ? moduleName.trim().toUpperCase() : moduleName;
	}

	public Integer getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}

	public PcDrugToAgeRequestModel() {
		super();
	}

	public PcDrugToAgeRequestModel(String serviceCode, String fromAgeInDays, String toAgeInDays, String payerId,
			String serviceStatus, String additionalRejectionReason, String moduleName, Integer rowNumber) {
		super();
		this.serviceCode = serviceCode;
		this.fromAgeInDays = fromAgeInDays;
		this.toAgeInDays = toAgeInDays;
		this.payerId = payerId;
		this.serviceStatus = serviceStatus;
		this.additionalRejectionReason = additionalRejectionReason;
		this.moduleName = moduleName;
		this.rowNumber = rowNumber;
	}

	@AssertTrue(message = "PayerId {emptyDataValidation}")
	public boolean isPayerIdValid() {
		String category = UserInfoUtil.getAccCategory(SecurityContextHolder.getContext().getAuthentication());
		if (category != null && StringUtils.isNotBlank(category)
				&& category.equalsIgnoreCase(AccountCategory.PAYER.name())) {
			return true;
		}
		return StringUtils.isNotBlank(payerId);
	}
}
