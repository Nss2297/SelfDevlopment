package com.waseel.pbmnotificationservice.model.email;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.waseel.pbmnotificationservice.model.common.CommonRequestModel;

public class EmailNotificationRequestModel extends CommonRequestModel {

	@Valid
	@NotEmpty(message = "email {notEmptyValidation}")
	private List<@Email(message = "{invalidEmailFormat}")
		@NotBlank(message = "email {notEmptyValidation}") String> emails;

	@NotBlank(message = "memberName {notEmptyValidation}")
	private String memberName;

	@NotBlank(message = "providerName {notEmptyValidation}")
	private String providerName;

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

	public EmailNotificationRequestModel() {
		super();
	}

	public EmailNotificationRequestModel(List<String> emails) {
		super();
		this.emails = emails;
	}

}
