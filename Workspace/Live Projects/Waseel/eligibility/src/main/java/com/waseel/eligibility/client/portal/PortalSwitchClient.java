package com.waseel.eligibility.client.portal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.waseel.eligibility.client.portal.model.TransactionWrapper;


@Service
public class PortalSwitchClient {

	private static final Logger logger = LoggerFactory.getLogger(PortalSwitchClient.class);
	
	@Value("${portal.url}")
	public String URL;

	@Autowired
	SOAPConnector soapConnector;

	public TransactionWrapper send(TransactionWrapper request) {
		logger.info("Sending Request to Portal - " + URL);
		TransactionWrapper response = (TransactionWrapper) soapConnector.callWebService(URL, request);
		logger.info("Received Response from Portal - " + URL);
		return response;
	}
}