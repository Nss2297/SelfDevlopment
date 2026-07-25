package com.waseel.policy.model.client;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PbmPayerApiResponseModel {

	private MemberDetailsResponseModel memberDetailsResponseModel;
	private InvalidResponseModel invalidResponseModel;

	public MemberDetailsResponseModel getMemberDetailsResponseModel() {
		return memberDetailsResponseModel;
	}

	public void setMemberDetailsResponseModel(MemberDetailsResponseModel memberDetailsResponseModel) {
		this.memberDetailsResponseModel = memberDetailsResponseModel;
	}

	public InvalidResponseModel getInvalidResponseModel() {
		return invalidResponseModel;
	}

	public void setInvalidResponseModel(InvalidResponseModel invalidResponseModel) {
		this.invalidResponseModel = invalidResponseModel;
	}

	public PbmPayerApiResponseModel() {
		super();
	}

	public PbmPayerApiResponseModel(MemberDetailsResponseModel memberDetailsResponseModel,
			InvalidResponseModel invalidResponseModel) {
		super();
		this.memberDetailsResponseModel = memberDetailsResponseModel;
		this.invalidResponseModel = invalidResponseModel;
	}

}
