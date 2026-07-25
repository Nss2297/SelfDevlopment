package com.waseel.brservice.exceptions;

public class BRException extends Exception {

	private static final long serialVersionUID = 1L;

	public BRException() {
		super();
	}
	
	public BRException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BRException(String message, Throwable cause) {
		super(message, cause);
	}

	public BRException(String message) {
		super(message);
	}

	public BRException(Throwable cause) {
		super(cause);
	}
}
