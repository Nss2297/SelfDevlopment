package com.waseel.pbmnotificationservice.model.eprescription.notification;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class NotificationResponseModel {
    private String ePrescriptionReferenceNumber;
    private String approvalReferenceNumber;
    private String status;
    private String acknowledgementDateAndTime;
    private String statusDescription;
    
    public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAcknowledgementDateAndTime() {
        return acknowledgementDateAndTime;
    }

    public void setAcknowledgementDateAndTime(String acknowledgementDateAndTime) {
        this.acknowledgementDateAndTime = acknowledgementDateAndTime;
    }
}
