package com.waseel.pbm.pbmadminservice.model.drugexclusion.provider;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;

import com.waseel.pbm.pbmadminservice.validator.customannotation.IsNumber;

public class ProviderExclusionRequestModel {

	@NotNull(message = "providerId {notNullOrEmpty}")
	@IsNumber(message = "providerId {onlyAllowDigits}")
	private String providerId;

	@NotBlank(message = "providerName {notNullOrEmpty}")
	private String providerName;

	public ProviderExclusionRequestModel() {
	}

	public ProviderExclusionRequestModel(String providerId, String providerName) {
		this.providerId = providerId;
		this.providerName = providerName;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = !StringUtils.isBlank(providerId) ? providerId.trim() : providerId;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = !StringUtils.isBlank(providerName) ? providerName.trim() : providerName;
	}
}
