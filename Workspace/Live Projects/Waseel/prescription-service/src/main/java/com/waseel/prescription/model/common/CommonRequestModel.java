package com.waseel.prescription.model.common;

import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.waseel.prescription.validator.customannotation.IsNumber;
import com.waseel.prescription.validator.customannotation.NoMoreThanTwentyLength;
import com.waseel.prescription.validator.customannotation.NoWhiteSpaceCharacter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonRequestModel {

	@NotEmpty(message = "payerId should not be null or empty")
	@IsNumber(message = "payerId {notAnumberValidation}")
	@NoWhiteSpaceCharacter(message = "payerId {noWhiteSpaceCharacterValidation}")
	@NoMoreThanTwentyLength(message = "PayerId {noMoreThanTwentyLengthValidation}")
	private String payerId;

	@NotEmpty(message = "ePrescriptionReferenceNumber should not be null or empty")
	@JsonProperty("ePrescriptionReferenceNumber")
	private String ePrescriptionReferenceNumber;

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = !StringUtils.isBlank(payerId) ? payerId.trim() : payerId;
	}

	public String getePrescriptionReferenceNumber() {
		return ePrescriptionReferenceNumber;
	}

	public void setePrescriptionReferenceNumber(String ePrescriptionReferenceNumber) {
		this.ePrescriptionReferenceNumber = !StringUtils.isBlank(ePrescriptionReferenceNumber)
				? ePrescriptionReferenceNumber.trim()
				: ePrescriptionReferenceNumber;
	}

	public CommonRequestModel(
			@NotEmpty(message = "payerId should not be null or empty") @NoMoreThanTwentyLength(message = "PayerId {noMoreThanTwentyLengthValidation}") String payerId,
			@NotEmpty(message = "ePrescriptionReferenceNumber should not be null or empty") String ePrescriptionReferenceNumber) {
		super();
		this.payerId = payerId;
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
	}

	public CommonRequestModel() {
	}

}
