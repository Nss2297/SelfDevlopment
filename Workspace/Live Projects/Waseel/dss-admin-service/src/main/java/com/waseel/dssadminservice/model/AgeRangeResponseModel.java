package com.waseel.dssadminservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AgeRangeResponseModel {

	private String key;
	private String value;
	private String fromValue;
	private String toValue;

	public AgeRangeResponseModel(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public AgeRangeResponseModel(String key, String fromValue, String toValue) {
		this.key = key;
		this.fromValue = fromValue;
		this.toValue = toValue;
	}

	public String getFromValue() {
		return fromValue;
	}

	public void setFromValue(String fromValue) {
		this.fromValue = fromValue;
	}

	public String getToValue() {
		return toValue;
	}

	public void setToValue(String toValue) {
		this.toValue = toValue;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
