package com.waseel.pbmnotificationservice.model.eprescription.notification;

import javax.validation.constraints.NotEmpty;

import com.waseel.pbmnotificationservice.validator.customannotation.IsValidEPrescriptionStatusType;
import com.waseel.pbmnotificationservice.validator.customannotation.NoMoreThan100Length;
import com.waseel.pbmnotificationservice.validator.customannotation.NoMoreThan60Length;

import liquibase.repackaged.org.apache.commons.lang3.StringUtils;

public class NotificationRequestModel {

	@NotEmpty(message = "ePrescriptionReferenceNumber {notEmptyValidation}")
	@NoMoreThan100Length(message = "ePrescriptionReferenceNumber {noMoreThan100LengthValidation}")
	private String ePrescriptionReferenceNumber;

	@NotEmpty(message = "approvalReferenceNumber {notEmptyValidation}")
	@NoMoreThan100Length(message = "approvalReferenceNumber {noMoreThan100LengthValidation}")
	private String approvalReferenceNumber;

	@NotEmpty(message = "ePrescriptionStatus {notEmptyValidation}")
	@NoMoreThan60Length(message = "ePrescriptionStatus {noMoreThan60LengthValidation}")
	@IsValidEPrescriptionStatusType(message = "{ePrescriptionStatusTypeValidation}")
	private String ePrescriptionStatus;

	public NotificationRequestModel() {
		super();
	}

	public NotificationRequestModel(String ePrescriptionReferenceNumber, String approvalReferenceNumber,
			String ePrescriptionStatus) {
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
		this.approvalReferenceNumber = approvalReferenceNumber;
		this.ePrescriptionStatus = ePrescriptionStatus;
	}

	public String getePrescriptionReferenceNumber() {
		return ePrescriptionReferenceNumber;
	}

	public void setePrescriptionReferenceNumber(String ePrescriptionReferenceNumber) {
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
	}

	public String getApprovalReferenceNumber() {
		return approvalReferenceNumber;
	}

	public void setApprovalReferenceNumber(String approvalReferenceNumber) {
		this.approvalReferenceNumber = approvalReferenceNumber;
	}

	public String getePrescriptionStatus() {
		return ePrescriptionStatus;
	}

	public void setePrescriptionStatus(String ePrescriptionStatus) {
		this.ePrescriptionStatus = StringUtils.isBlank(ePrescriptionStatus) ? ePrescriptionStatus
				: ePrescriptionStatus.trim();
	}
}
