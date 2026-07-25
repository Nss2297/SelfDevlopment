package com.waseel.pbm.pbmadminservice.exceptions;

public class AdminException extends Exception {

	private static final long serialVersionUID = 1L;

	public AdminException() {
		super();
	}
	
	public AdminException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AdminException(String message, Throwable cause) {
		super(message, cause);
	}

	public AdminException(String message) {
		super(message);
	}

	public AdminException(Throwable cause) {
		super(cause);
	}
}
