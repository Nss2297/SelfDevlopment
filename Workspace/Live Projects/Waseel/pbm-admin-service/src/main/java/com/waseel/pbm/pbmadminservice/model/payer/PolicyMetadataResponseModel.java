package com.waseel.pbm.pbmadminservice.model.payer;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PolicyMetadataResponseModel {

	private PolicyMetadata policyMetadata;

	public PolicyMetadata getPolicyMetadata() {
		return policyMetadata;
	}

	public void setPolicyMetadata(PolicyMetadata policyMetadata) {
		this.policyMetadata = policyMetadata;
	}

}
