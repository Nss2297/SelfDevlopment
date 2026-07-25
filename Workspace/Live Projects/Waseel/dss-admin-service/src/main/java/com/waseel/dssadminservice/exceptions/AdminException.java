package com.waseel.dssadminservice.exceptions;

public class AdminException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public AdminException() {
		super();
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
