package com.waseel.dssadminservice.model.customization.pcduplicatetherapy;

import java.util.Date;
import java.util.Objects;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import com.waseel.dssadminservice.util.UserInfoUtil;
import com.waseel.dssadminservice.validator.customannotation.IsValidModuleName;
import com.waseel.dssadminservice.validator.customannotation.IsValidServiceStatus;
import com.waseel.dssadminservice.validator.customannotation.NoMoreThan20Length;
import com.waseel.dssadminservice.validator.customannotation.NoMoreThan250Length;
import com.waseel.dssadminservice.validator.customannotation.NoMoreThan500Length;

public class PcDuplicateTherapyRequestModel {

    @NotEmpty(message = "ServiceCode {emptyDataValidation}")
    @NoMoreThan250Length(message = "ServiceCode {noMoreThan250LengthValidation}")
    private String serviceCode;

    @NotEmpty(message = "InteractedServiceCode {emptyDataValidation}")
    @NoMoreThan250Length(message = "InteractedServiceCode {noMoreThan250LengthValidation}")
    private String interactedServiceCode;

    @NoMoreThan20Length(message = "PayerId {noMoreThan20LengthValidation}")
    private String payerId;

    @IsValidModuleName(message = "{moduleNameValidation}")
    private String moduleName;

    @IsValidServiceStatus(message = "{serviceStatusValidation}")
    private String serviceStatus;

    @NoMoreThan500Length(message = "AdditionalRejectionReason {noMoreThan500LengthValidation}")
    private String additionalRejectionReason;

    private Boolean inReverseAddition = false;

    private Date updateDateAndTime;
    private String scientificCode;
    private Integer rowNumber;

    public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = StringUtils.isNotBlank(serviceCode) ? serviceCode.trim() : serviceCode;
	}

	public String getInteractedServiceCode() {
		return interactedServiceCode;
	}

	public void setInteractedServiceCode(String interactedServiceCode) {
		this.interactedServiceCode = StringUtils.isNotBlank(interactedServiceCode) ? interactedServiceCode.trim()
				: interactedServiceCode;
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
		this.scientificCode = scientificCode;
	}

	public Integer getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}

	public Boolean getInReverseAddition() {
		return inReverseAddition;
	}

	public void setInReverseAddition(Boolean inReverseAddition) {
		this.inReverseAddition = inReverseAddition;
	}

    public PcDuplicateTherapyRequestModel() {}

	public PcDuplicateTherapyRequestModel(String serviceCode, String interactedServiceCode, String payerId,
			String serviceStatus, String additionalRejectionReason, String moduleName, Integer rowNumber) {
		super();
		this.serviceCode = serviceCode;
		this.interactedServiceCode = interactedServiceCode;
		this.payerId = payerId;
		this.serviceStatus = serviceStatus;
		this.additionalRejectionReason = additionalRejectionReason;
		this.moduleName = moduleName;
		this.rowNumber = rowNumber;
	}

    public PcDuplicateTherapyRequestModel(String serviceCode, String interactedServiceCode, String payerId,
			String moduleName, String serviceStatus, String additionalRejectionReason) {
    	this.serviceCode = serviceCode;
		this.interactedServiceCode = interactedServiceCode;
		this.payerId = payerId;
		this.serviceStatus = serviceStatus;
		this.additionalRejectionReason = additionalRejectionReason;
		this.moduleName = moduleName;
	}

	@AssertTrue(message = "PayerId {emptyDataValidation}")
	public boolean isPayerIdValid() {
		String category = UserInfoUtil.getAccCategory(SecurityContextHolder.getContext().getAuthentication());
		if (category != null && StringUtils.isNotBlank(category) && category.equalsIgnoreCase("payer")) {
			return true;
		}
		return StringUtils.isNotBlank(payerId);
	}
	@Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        PcDuplicateTherapyRequestModel other = (PcDuplicateTherapyRequestModel) obj;
        return Objects.equals(serviceCode, other.serviceCode) &&
                Objects.equals(interactedServiceCode, other.interactedServiceCode) &&
                Objects.equals(payerId, other.payerId) &&
                Objects.equals(moduleName, other.moduleName) &&
				Objects.equals(serviceStatus, other.serviceStatus);
	}
}
