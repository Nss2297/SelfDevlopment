package com.waseel.pbm.fdbvalidationservice.exceptions;

import com.waseel.pbm.fdbvalidationservice.model.DssResponse;

public class FdbException extends Exception {

	private static final long serialVersionUID = 1L;
	private DssResponse dssInvalidResponse;

	public FdbException() {
		super();
	}

	public FdbException(DssResponse dssInvalidResponse) {
		this.dssInvalidResponse = dssInvalidResponse;
	}

	public FdbException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public FdbException(String message, Throwable cause) {
		super(message, cause);
	}

	public FdbException(String message) {
		super(message);
	}

	public FdbException(Throwable cause) {
		super(cause);
	}

	public DssResponse getDssInvalidResponse() {
		return dssInvalidResponse;
	}

	public void setDssInvalidResponse(DssResponse dssInvalidResponse) {
		this.dssInvalidResponse = dssInvalidResponse;
	}

}
