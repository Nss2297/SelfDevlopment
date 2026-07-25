package com.waseel.policy.service.management;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import com.waseel.policy.enums.SessionServiceDetails;

@Service
public class SessionService {

	public void setTransactionLogIdInSession(ContentCachingRequestWrapper requestWrapper, Long transactionLogId) {
		HttpSession session = requestWrapper.getSession();
		session.setAttribute(SessionServiceDetails.TRANSACTION_LOG_ID.value(), transactionLogId);
	}

	public void removeTransactionLogIdFromSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(SessionServiceDetails.TRANSACTION_LOG_ID.value());
	}

	public String getTransactionLogIdFromSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute(SessionServiceDetails.TRANSACTION_LOG_ID.value()) != null) {
			return session.getAttribute(SessionServiceDetails.TRANSACTION_LOG_ID.value()).toString();
		}
		return null;
	}
}
