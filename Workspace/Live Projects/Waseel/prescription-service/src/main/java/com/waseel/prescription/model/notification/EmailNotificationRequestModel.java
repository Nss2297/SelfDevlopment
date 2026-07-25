package com.waseel.prescription.model.notification;

import java.util.List;

public class EmailNotificationRequestModel extends CommonNotificationModel {

	private List<String> emails;
	private String memberName;
	private String providerName;

	public EmailNotificationRequestModel() {
		super();
	}

	public EmailNotificationRequestModel(List<String> emails, String url, String requestType, String requestId,
			String ePrescriptionReferenceNumber) {
		super(url, requestType, requestId, ePrescriptionReferenceNumber);
		this.emails = emails;
	}

	public EmailNotificationRequestModel(String url, String requestType, String requestId,
			String ePrescriptionReferenceNumber, List<String> emails, String memberName, String providerName) {
		super(url, requestType, requestId, ePrescriptionReferenceNumber);
		this.emails = emails;
		this.memberName = memberName;
		this.providerName = providerName;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public List<String> getEmails() {
		return emails;
	}

	public void setEmails(List<String> emails) {
		this.emails = emails;
	}
}
