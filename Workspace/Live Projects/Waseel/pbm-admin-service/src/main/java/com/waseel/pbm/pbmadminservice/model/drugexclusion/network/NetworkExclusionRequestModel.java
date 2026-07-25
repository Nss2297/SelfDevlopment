package com.waseel.pbm.pbmadminservice.model.drugexclusion.network;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;

import com.waseel.pbm.pbmadminservice.validator.customannotation.IsNumber;

public class NetworkExclusionRequestModel {

	@NotNull(message = "networkId {notNullOrEmpty}")
	@IsNumber(message = "networkId {onlyAllowDigits}")
	private String networkId;

	public NetworkExclusionRequestModel() {
	}

	public NetworkExclusionRequestModel(String networkId) {
		this.networkId = networkId;
	}

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = !StringUtils.isBlank(networkId) ? networkId.trim() : networkId;
	}
}
