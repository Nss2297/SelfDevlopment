package com.waseel.smsservice.persist;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "`WaseelSmsConfiguration`")
public class WaseelSmsConfiguration implements Serializable {

	private static final long serialVersionUID = -8682465281176692895L;

	@Id
	@Column(name = "`SmsConfigurationId`")
	private Long smsConfigurationId;
	@Column(name = "`AppName`")
	private String appName;
	@Column(name = "`UnifonicAppId`")
	private String unifonicAppId;
	@Column(name = "`SenderID`")
	private String senderId;
	@Column(name = "`IsEnabled`")
	private String isEnabled;

	public String getAppName() {
		return appName;
	}

	public String getUnifonicAppId() {
		return unifonicAppId;
	}

	public String getSenderId() {
		return senderId;
	}

	public String getIsEnabled() {
		return isEnabled;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public void setUnifonicAppId(String unifonicAppId) {
		this.unifonicAppId = unifonicAppId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Long getSmsConfigurationId() {
		return smsConfigurationId;
	}

	public void setSmsConfigurationId(Long smsConfigurationId) {
		this.smsConfigurationId = smsConfigurationId;
	}

}
