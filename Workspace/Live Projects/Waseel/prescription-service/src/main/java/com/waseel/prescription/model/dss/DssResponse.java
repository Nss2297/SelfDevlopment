package com.waseel.prescription.model.dss;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "requestId", "status", "errors", "results" })
public class DssResponse {

	@JsonProperty("requestId")
	private String requestId;
	@JsonProperty("status")
	private String status;
	@JsonProperty("errors")
	private List<String> errors = null;
	@JsonProperty("results")
	private List<Result> results = null;

	private int httpStatusCode;
	private String httpStatusDescription;

	private Long transactionLogId;

	private Integer code;

	private String message;

	public DssResponse() {
		super();
	}

	public DssResponse(int httpStatusCode, String httpStatusDescription) {
		this.httpStatusCode = httpStatusCode;
		this.httpStatusDescription = httpStatusDescription;
	}

	public DssResponse(int httpStatusCode, Integer code) {
		super();
		this.httpStatusCode = httpStatusCode;
		this.code = code;
	}

	public DssResponse(String requestId, String status, List<String> errors, List<Result> results, int httpStatusCode,
			String httpStatusDescription) {
		super();
		this.requestId = requestId;
		this.status = status;
		this.errors = errors;
		this.results = results;
		this.httpStatusCode = httpStatusCode;
		this.httpStatusDescription = httpStatusDescription;
	}

	public Long getTransactionLogId() {
		return transactionLogId;
	}

	public void setTransactionLogId(Long transactionLogId) {
		this.transactionLogId = transactionLogId;
	}

	@JsonProperty("requestId")
	public String getRequestId() {
		return requestId;
	}

	@JsonProperty("requestId")
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	@JsonProperty("status")
	public String getStatus() {
		return status;
	}

	@JsonProperty("status")
	public void setStatus(String status) {
		this.status = status;
	}

	@JsonProperty("errors")
	public List<String> getErrors() {
		return errors;
	}

	@JsonProperty("errors")
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	@JsonProperty("results")
	public List<Result> getResults() {
		return results;
	}

	@JsonProperty("results")
	public void setResults(List<Result> results) {
		this.results = results;
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public String getHttpStatusDescription() {
		return httpStatusDescription;
	}

	public void setHttpStatusDescription(String httpStatusDescription) {
		this.httpStatusDescription = httpStatusDescription;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "{requestId=" + requestId + ", status=" + status + ", errors=" + errors + ", results=" + results
				+ ", httpStatusCode=" + httpStatusCode + ", httpStatusDescription=" + httpStatusDescription
				+ ", transactionLogId=" + transactionLogId + "}";
	}

	public DssResponse(String requestId, String status, List<String> errors, List<Result> results, int httpStatusCode,
			String httpStatusDescription, Long transactionLogId) {
		super();
		this.requestId = requestId;
		this.status = status;
		this.errors = errors;
		this.results = results;
		this.httpStatusCode = httpStatusCode;
		this.httpStatusDescription = httpStatusDescription;
		this.transactionLogId = transactionLogId;
	}

	public DssResponse(Integer code, String message) {
		super();
		this.code = code;
		this.message = message;
	}

}