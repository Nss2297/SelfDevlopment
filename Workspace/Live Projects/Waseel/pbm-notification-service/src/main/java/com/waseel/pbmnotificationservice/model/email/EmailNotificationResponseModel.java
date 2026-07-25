package com.waseel.pbmnotificationservice.model.email;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.waseel.pbmnotificationservice.model.common.CommonResponseModel;

@JsonInclude(Include.NON_NULL)
public class EmailNotificationResponseModel extends CommonResponseModel {

	public EmailNotificationResponseModel() {
		super();
	}

	public EmailNotificationResponseModel(String status, String statusDescription) {
		super(status, statusDescription);
	}
}
