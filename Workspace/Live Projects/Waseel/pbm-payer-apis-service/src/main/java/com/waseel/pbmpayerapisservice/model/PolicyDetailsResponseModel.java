package com.waseel.pbmpayerapisservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PolicyDetailsResponseModel {

	private PolicyMetaDataModel policyMetadata;

	public PolicyMetaDataModel getPolicyMetadata() {
		return policyMetadata;
	}

	public void setPolicyMetadata(PolicyMetaDataModel policyMetadata) {
		this.policyMetadata = policyMetadata;
	}

}
