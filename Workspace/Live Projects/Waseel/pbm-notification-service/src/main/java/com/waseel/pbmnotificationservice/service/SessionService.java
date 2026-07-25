package com.waseel.pbmnotificationservice.service;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Service
public class SessionService {

	private static final String EMAIL_SMS_AUDIT_LOG_ID = "EmailSmsAuditlogId";

	public void setTransactionLogIdInSession(ContentCachingRequestWrapper requestWrapper, Long transactionLogId) {
		HttpSession session = requestWrapper.getSession();
		session.setAttribute(EMAIL_SMS_AUDIT_LOG_ID, transactionLogId);
	}

	public void removeTransactionLogIdFromSession(ContentCachingRequestWrapper requestWrapper) {
		HttpSession session = requestWrapper.getSession();
		session.removeAttribute(EMAIL_SMS_AUDIT_LOG_ID);
	}

	public String getTransactionLogIdFromSession(ContentCachingRequestWrapper requestWrapper) {
		HttpSession session = requestWrapper.getSession();
		if (session.getAttribute(EMAIL_SMS_AUDIT_LOG_ID) != null) {
			return session.getAttribute(EMAIL_SMS_AUDIT_LOG_ID).toString();
		}
		return null;
	}
}
