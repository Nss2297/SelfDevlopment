package com.waseel.pbmnotificationservice.exceptions;

import com.waseel.pbmnotificationservice.model.eprescription.notification.NotificationResponseModel;

public class NotificationException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private NotificationResponseModel notificationResponseModel;

	public NotificationException() {
		super();
	}

	public NotificationException(NotificationResponseModel notificationResponseModel) {
		this.notificationResponseModel = notificationResponseModel;
	}

	public NotificationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NotificationException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotificationException(String message) {
		super(message);
	}

	public NotificationException(Throwable cause) {
		super(cause);
	}

	public NotificationResponseModel getNotificationResponseModel() {
		return notificationResponseModel;
	}

	public void setNotificationResponseModel(NotificationResponseModel notificationResponseModel) {
		this.notificationResponseModel = notificationResponseModel;
	}
}
