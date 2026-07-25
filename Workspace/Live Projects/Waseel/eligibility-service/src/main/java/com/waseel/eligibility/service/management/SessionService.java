package com.waseel.eligibility.service.management;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Service
public class SessionService {

	private static final String TRANSACTION_REFERENCE_NUMBER = "transactionReferenceNumber";

	public void setTransactionLogIdInSession(ContentCachingRequestWrapper requestWrapper, Long transactionLogId) {
		HttpSession session = requestWrapper.getSession();
		session.setAttribute(TRANSACTION_REFERENCE_NUMBER, transactionLogId);
	}

	public void removeTransactionLogIdFromSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(TRANSACTION_REFERENCE_NUMBER);
	}

	public String getTransactionLogIdFromSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute(TRANSACTION_REFERENCE_NUMBER) != null) {
			return session.getAttribute(TRANSACTION_REFERENCE_NUMBER).toString();
		}
		return null;
	}
}
