package com.waseel.emailservice.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.waseel.emailservice.validator.customannotation.ValidateWaseelEmail;

public class EmailDetails {

	@Valid
	@NotEmpty(message = "Recipients should not be null or empty")
	private List<@NotBlank(message = "Recipients should not be null or empty") 
		@Email @ValidateWaseelEmail(message = "Only accept email addresses that belong to Waseel") String> recipients;
	@NotEmpty(message = "Message body should not be null or empty")
	private String msgBody;
	@NotEmpty(message = "Subject should not be null or empty")
	private String subject;
    @JsonProperty("isHtml")
	private boolean html;
	@NotEmpty(message = "SenderName should not be null or empty")
	private String senderName;

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

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
}
