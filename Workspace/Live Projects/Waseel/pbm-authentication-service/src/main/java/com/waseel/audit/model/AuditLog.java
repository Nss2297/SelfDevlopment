package com.waseel.audit.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Transient;

public class AuditLog implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9162171314712466216L;

	@Transient
    public static final String SEQUENCE_NAME = "audit_sequence";
	
	public static final String GENERIC_AUDIT_LOG_TYPE = "GenericAuditLogType";


	private Long id;
	
	protected String userId;
	protected String providerId;
	protected String objectId;
	protected Date eventTimeStamp;
	protected String eventType;
	protected String eventPath;
	protected String eventDescription;
	
	
	public AuditLog() {
		setEventType(GENERIC_AUDIT_LOG_TYPE);
	}
	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getProviderId() {
		return providerId;
	}
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	public Date getEventTimeStamp() {
		return eventTimeStamp;
	}
	public void setEventTimeStamp(Date eventTimeStamp) {
		this.eventTimeStamp = eventTimeStamp;
	}


	public String getObjectId() {
		return objectId;
	}


	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}


	public String getEventType() {
		return eventType;
	}


	public void setEventType(String eventType) {
		this.eventType = eventType;
	}


	public String getEventPath() {
		return eventPath;
	}


	public void setEventPath(String eventPath) {
		this.eventPath = eventPath;
	}


	public String getEventDescription() {
		return eventDescription;
	}


	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}
	
}
