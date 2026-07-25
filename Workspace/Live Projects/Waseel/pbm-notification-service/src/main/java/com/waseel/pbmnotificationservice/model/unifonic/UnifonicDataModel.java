package com.waseel.pbmnotificationservice.model.unifonic;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonInclude(Include.NON_NULL)
@JsonTypeName("data")
public class UnifonicDataModel {

	@JsonProperty("MessageID")
	private String messageId;
	@JsonProperty("CorrelationID")
	private String correlationId;
	@JsonProperty("Status")
	private String status;
	@JsonProperty("NumberOfUnits")
	private String numberOfUnits;
	@JsonProperty("Cost")
	private String cost;
	@JsonProperty("Balance")
	private String balance;
	@JsonProperty("Recipient")
	private String recipient;
	@JsonProperty("TimeCreated")
	private String timeCreated;
	@JsonProperty("CurrencyCode")
	private String currencyCode;

	public String getMessageId() {
		return messageId;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public String getStatus() {
		return status;
	}

	public String getNumberOfUnits() {
		return numberOfUnits;
	}

	public String getCost() {
		return cost;
	}

	public String getBalance() {
		return balance;
	}

	public String getRecipient() {
		return recipient;
	}

	public String getTimeCreated() {
		return timeCreated;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setNumberOfUnits(String numberOfUnits) {
		this.numberOfUnits = numberOfUnits;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public void setTimeCreated(String timeCreated) {
		this.timeCreated = timeCreated;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public UnifonicDataModel() {
		super();
	}

	public UnifonicDataModel(String messageId, String correlationId, String status, String numberOfUnits, String cost,
			String balance, String recipient, String timeCreated, String currencyCode) {
		super();
		this.messageId = messageId;
		this.correlationId = correlationId;
		this.status = status;
		this.numberOfUnits = numberOfUnits;
		this.cost = cost;
		this.balance = balance;
		this.recipient = recipient;
		this.timeCreated = timeCreated;
		this.currencyCode = currencyCode;
	}

}
