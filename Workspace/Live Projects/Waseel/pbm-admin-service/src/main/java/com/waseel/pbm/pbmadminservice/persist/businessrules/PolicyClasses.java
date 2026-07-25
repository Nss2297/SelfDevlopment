package com.waseel.pbm.pbmadminservice.persist.businessrules;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "POLICY_CLASSES", schema = "PBM_BUSINESS_RULES")
@Where(clause = "IS_ENABLED='1'")
public class PolicyClasses implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "POLICY_CLASS_ID", nullable = false, updatable = false)
	private Long policyClassId;

	@Column(name = "POLICY_INFORMATION_ID", nullable = false, unique = true)
	private Long policyInformationId;

	@Column(name = "CLASS_CODE", nullable = false, length = 50, unique = true)
	private String classCode;

	@Column(name = "CLASS_LIMIT_VALUE")
	private Long classLimitValue;

	@Column(name = "CLASS_LIMIT_CURRENCY", length = 30)
	private String classLimitCurrency;

	@Column(name = "COVERAGE", length = 2500)
	private String coverage;

	@Column(name = "EXCLUSION", length = 2500)
	private String exclusion;

	@Column(name = "COMMENTS", length = 3000)
	private String comments;

	@Column(name = "IS_ENABLED", columnDefinition = "CHAR(1) default ('1')", nullable = false)
	private Boolean isEnabled = true;

	public Long getPolicyClassId() {
		return policyClassId;
	}

	public void setPolicyClassId(Long policyClassId) {
		this.policyClassId = policyClassId;
	}

	public Long getPolicyInformationId() {
		return policyInformationId;
	}

	public void setPolicyInformationId(Long policyInformationId) {
		this.policyInformationId = policyInformationId;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public Long getClassLimitValue() {
		return classLimitValue;
	}

	public void setClassLimitValue(Long classLimitValue) {
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

	public String getExclusion() {
		return exclusion;
	}

	public void setExclusion(String exclusion) {
		this.exclusion = exclusion;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Boolean getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

}
