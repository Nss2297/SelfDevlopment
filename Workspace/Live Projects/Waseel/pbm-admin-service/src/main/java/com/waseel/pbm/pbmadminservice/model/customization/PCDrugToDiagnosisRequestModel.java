package com.waseel.pbm.pbmadminservice.model.customization;

import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang.StringUtils;

import com.waseel.pbm.pbmadminservice.validator.customannotation.IsValidModuleName;
import com.waseel.pbm.pbmadminservice.validator.customannotation.IsValidRejectionCategory;
import com.waseel.pbm.pbmadminservice.validator.customannotation.IsValidServiceStatus;
import com.waseel.pbm.pbmadminservice.validator.customannotation.NoMoreThan500Length;

public class PCDrugToDiagnosisRequestModel {

	@NotEmpty(message = "ServiceCode {notNullOrEmpty}")
	private String serviceCode;

	@NotEmpty(message = "IcdCode {notNullOrEmpty}")
	private String icdCode;

	@NotEmpty(message = "PayerId {notNullOrEmpty}")
	private String payerId;

	@NotEmpty(message = "ModuleName {notNullOrEmpty}")
	@IsValidModuleName(message = "{moduleNameValidation}")
	private String moduleName;

	@NotEmpty(message = "RejectionCategory {notNullOrEmpty}")
	@IsValidRejectionCategory(message = "{rejectionCategoryValidation}")
	private String rejectionCategory;

	@NotEmpty(message = "CategoryOfApproval {notNullOrEmpty}")
	private String categoryOfApproval;

	@NotEmpty(message = "ServiceStatus {notNullOrEmpty}")
	@IsValidServiceStatus(message = "{serviceStatusValidation}")
	private String serviceStatus;

	@NoMoreThan500Length(message = "AdditionalRejectionReason {noMoreThan500LengthValidation}")
	private String additionalRejectionReason;

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

	public String getIcdCode() {
		return icdCode;
	}

	public String getPayerId() {
		return payerId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public String getCategoryOfApproval() {
		return categoryOfApproval;
	}

	public String getRejectionCategory() {
		return rejectionCategory;
	}

	public String getServiceStatus() {
		return serviceStatus;
	}

	public String getAdditionalRejectionReason() {
		return additionalRejectionReason;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = !StringUtils.isBlank(serviceCode) ? serviceCode.trim() : serviceCode;
	}

	public void setIcdCode(String icdCode) {
		this.icdCode = !StringUtils.isBlank(icdCode) ? icdCode.trim().toUpperCase() : icdCode;
	}

	public void setPayerId(String payerId) {
		this.payerId = !StringUtils.isBlank(payerId) ? payerId.trim() : payerId;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = !StringUtils.isBlank(moduleName) ? moduleName.trim().toUpperCase() : moduleName;
	}

	public void setCategoryOfApproval(String categoryOfApproval) {
		this.categoryOfApproval = !StringUtils.isBlank(categoryOfApproval) ? categoryOfApproval.trim()
				: categoryOfApproval;
	}

	public void setRejectionCategory(String rejectionCategory) {
		this.rejectionCategory = !StringUtils.isBlank(rejectionCategory) ? rejectionCategory.trim() : rejectionCategory;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = !StringUtils.isBlank(serviceStatus) ? serviceStatus.trim().toUpperCase() : serviceStatus;
	}

	public void setAdditionalRejectionReason(String additionalRejectionReason) {
		this.additionalRejectionReason = !StringUtils.isBlank(additionalRejectionReason)
				? additionalRejectionReason.trim()
				: additionalRejectionReason;
	}
}
