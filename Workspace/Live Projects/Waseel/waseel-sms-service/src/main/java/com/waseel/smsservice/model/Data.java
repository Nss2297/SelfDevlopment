
package com.waseel.smsservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Data {

	@JsonProperty("MessageID")
	private Long messageID;
	@JsonProperty("CorrelationID")
	private String correlationID;
	@JsonProperty("Status")
	private String status;
	@JsonProperty("NumberOfUnits")
	private Integer numberOfUnits;
	@JsonProperty("Cost")
	private Integer cost;
	@JsonProperty("Balance")
	private Integer balance;
	@JsonProperty("Recipient")
	private String recipient;
	@JsonProperty("TimeCreated")
	private String timeCreated;
	@JsonProperty("CurrencyCode")
	private String currencyCode;

	@JsonProperty("MessageID")
	public Long getMessageID() {
		return messageID;
	}

	@JsonProperty("MessageID")
	public void setMessageID(Long messageID) {
		this.messageID = messageID;
	}

	@JsonProperty("CorrelationID")
	public String getCorrelationID() {
		return correlationID;
	}

	@JsonProperty("CorrelationID")
	public void setCorrelationID(String correlationID) {
		this.correlationID = correlationID;
	}

	@JsonProperty("Status")
	public String getStatus() {
		return status;
	}

	@JsonProperty("Status")
	public void setStatus(String status) {
		this.status = status;
	}

	@JsonProperty("NumberOfUnits")
	public Integer getNumberOfUnits() {
		return numberOfUnits;
	}

	@JsonProperty("NumberOfUnits")
	public void setNumberOfUnits(Integer numberOfUnits) {
		this.numberOfUnits = numberOfUnits;
	}

	@JsonProperty("Cost")
	public Integer getCost() {
		return cost;
	}

	@JsonProperty("Cost")
	public void setCost(Integer cost) {
		this.cost = cost;
	}

	@JsonProperty("Balance")
	public Integer getBalance() {
		return balance;
	}

	@JsonProperty("Balance")
	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	@JsonProperty("Recipient")
	public String getRecipient() {
		return recipient;
	}

	@JsonProperty("Recipient")
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	@JsonProperty("TimeCreated")
	public String getTimeCreated() {
		return timeCreated;
	}

	@JsonProperty("TimeCreated")
	public void setTimeCreated(String timeCreated) {
		this.timeCreated = timeCreated;
	}

	@JsonProperty("CurrencyCode")
	public String getCurrencyCode() {
		return currencyCode;
	}

	@JsonProperty("CurrencyCode")
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

}
