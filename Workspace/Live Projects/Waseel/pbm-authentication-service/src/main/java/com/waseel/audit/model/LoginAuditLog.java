package com.waseel.audit.model;

public class LoginAuditLog extends AuditLog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7682863361182377808L;
	
	public static final String Login_AUDIT_LOG_TYPE = "LoginAuditLogType";
	
	public LoginAuditLog() {
		this.setEventType(Login_AUDIT_LOG_TYPE);
	}

}
