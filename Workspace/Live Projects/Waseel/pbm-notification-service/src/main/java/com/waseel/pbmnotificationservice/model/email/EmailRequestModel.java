package com.waseel.pbmnotificationservice.model.email;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmailRequestModel {

	private List<String> recipients;
	private String msgBody;
	private String subject;
    @JsonProperty("isHtml")
	private boolean html = false;
	private String senderName;

	public EmailRequestModel() {
		super();
	}

	public EmailRequestModel(List<String> recipients, String msgBody, String subject, boolean isHtml,
			String senderName) {
		this.recipients = recipients;
		this.msgBody = msgBody;
		this.subject = subject;
		this.html = isHtml;
		this.senderName = senderName;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public List<String> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<String> recipients) {
		this.recipients = recipients;
	}

	public String getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public boolean isHtml() {
		return html;
	}

	public void setHtml(boolean html) {
		this.html = html;
	}
}
