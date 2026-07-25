package com.waseel.brservice.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Service
public class SessionService {
	private static final String TRANSACTIONLOGID = "TransactionLogId";

	public void setTransactionLogIdInSession(ContentCachingRequestWrapper requestWrapper, Long transactionLogId) {
		HttpSession session = requestWrapper.getSession();
		session.setAttribute(TRANSACTIONLOGID, transactionLogId);
	}

	public void removeTransactionLogIdFromSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(TRANSACTIONLOGID);
	}

	public String getTransactionLogIdFromSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute(TRANSACTIONLOGID) != null) {
			return session.getAttribute(TRANSACTIONLOGID).toString();
		}
		return null;
	}
}
