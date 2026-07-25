package com.waseel.pbm.pbmadminservice.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestInfoModel {

	@JsonProperty("requestId")
	private String requestId;
	
	@JsonProperty("memberId")
	private String memberId;
	
	@JsonProperty("dateTime")
	private Date dateTime;
	
	@JsonProperty("payerId")
	private String payerId;
	
	@JsonProperty("providerName")
	private String providerName;
	
	@JsonProperty("statusOfRequest")
	private String statusOfRequest;
	
	public RequestInfoModel() {
	}

	public RequestInfoModel(String requestId, String memberId, Date dateTime, String payerId, String providerName,
							String statusOfRequest) {
		super();
		this.requestId = requestId;
		this.memberId = memberId;
		this.dateTime = dateTime;
		this.payerId = payerId;
		this.providerName = providerName;
		this.statusOfRequest = statusOfRequest;
	}

	public String getRequestId() {
		return requestId;
	}

	public String getMemberId() {
		return memberId;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public String getProviderName() {
		return providerName;
	}

	public String getStatusOfRequest() {
		return statusOfRequest;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public void setStatusOfRequest(String statusOfRequest) {
		this.statusOfRequest = statusOfRequest;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}
}
