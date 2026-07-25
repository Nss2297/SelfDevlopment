package com.waseel.pbmschedulerservice.model.policydetails;

import java.math.BigDecimal;

public class PolicyClassesModel {

	private String classCode;
	private BigDecimal classLimitValue;
	private String classLimitCurrency;
	private String coverage;
	private String exclusions;
	private String comments;

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public BigDecimal getClassLimitValue() {
		return classLimitValue;
	}

	public void setClassLimitValue(BigDecimal classLimitValue) {
		this.classLimitValue = classLimitValue;
	}

	public String getClassLimitCurrency() {
		return classLimitCurrency;
	}

	public void setClassLimitCurrency(String classLimitCurrency) {
		this.classLimitCurrency = classLimitCurrency;
	}

	public String getCoverage() {
		return coverage;
	}

	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}

	public String getExclusions() {
		return exclusions;
	}

	public void setExclusions(String exclusions) {
		this.exclusions = exclusions;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
}
