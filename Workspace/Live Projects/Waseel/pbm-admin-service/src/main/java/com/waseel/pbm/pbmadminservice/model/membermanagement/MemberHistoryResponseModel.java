package com.waseel.pbm.pbmadminservice.model.membermanagement;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class MemberHistoryResponseModel {

	private String ePrescriptionReferenceNumber;
	private String lastUpdateDateTime;
	private String sendDateTime;
	private String providerId;
	private String providerName;
	private String ePrescriptionStatus;

	public MemberHistoryResponseModel() {
	}

	public MemberHistoryResponseModel(String ePrescriptionReferenceNumber, Date lastUpdateDateTime, String providerId,
			String ePrescriptionStatus, Date sendDateTime, String providerName) {
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
		this.lastUpdateDateTime = convertDateToString(lastUpdateDateTime);
		this.sendDateTime = convertDateToString(sendDateTime);
		this.providerId = providerId;
		this.ePrescriptionStatus = ePrescriptionStatus;
		this.providerName = providerName;
	}

	public String getSendDateTime() {
		return sendDateTime;
	}

	public void setSendDateTime(String sendDateTime) {
		this.sendDateTime = sendDateTime;
	}

	public String getePrescriptionReferenceNumber() {
		return ePrescriptionReferenceNumber;
	}

	public void setePrescriptionReferenceNumber(String ePrescriptionReferenceNumber) {
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
	}

	public String getLastUpdateDateTime() {
		return lastUpdateDateTime;
	}

	public void setLastUpdateDateTime(String lastUpdateDateTime) {
		this.lastUpdateDateTime = lastUpdateDateTime;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getePrescriptionStatus() {
		return ePrescriptionStatus;
	}

	public void setePrescriptionStatus(String ePrescriptionStatus) {
		this.ePrescriptionStatus = ePrescriptionStatus;
	}

	private String convertDateToString(Date date) {
		if (date != null) {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		}
		return null;
	}
}
