package com.waseel.prescription.service.management;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Service
public class SessionService {

	private static final String TRANSACTIONLOGID = "TransactionLogId";
	private static final String INVALID_REQUEST_ID = "InvalidPrescriptionRequestId";

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

	public void setInvalidPresRequestIdInSession(ContentCachingRequestWrapper requestWrapper,
			Long invalidPrescriptionRequestId) {
		HttpSession session = requestWrapper.getSession();
		session.setAttribute(INVALID_REQUEST_ID, invalidPrescriptionRequestId);
	}

	public String getInvalidPresRequestIdFromSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute(INVALID_REQUEST_ID) != null) {
			return session.getAttribute(INVALID_REQUEST_ID).toString();
		}
		return null;
	}

	public void removeInvalidPresRequestIdFromSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(INVALID_REQUEST_ID);
	}
}
