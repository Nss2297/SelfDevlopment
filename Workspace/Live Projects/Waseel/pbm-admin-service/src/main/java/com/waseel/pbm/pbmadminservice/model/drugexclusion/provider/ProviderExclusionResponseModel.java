package com.waseel.pbm.pbmadminservice.model.drugexclusion.provider;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ProviderExclusionResponseModel {

	private Long providerExclusionAsscId;

	public Long getProviderExclusionAsscId() {
		return providerExclusionAsscId;
	}

	public void setProviderExclusionAsscId(Long providerExclusionAsscId) {
		this.providerExclusionAsscId = providerExclusionAsscId;
	}
}
