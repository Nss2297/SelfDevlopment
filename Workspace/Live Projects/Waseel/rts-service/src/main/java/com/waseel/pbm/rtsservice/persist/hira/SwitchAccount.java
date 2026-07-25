package com.waseel.pbm.rtsservice.persist.hira;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "`SwitchAccount`",schema = "HIRA")
@Transactional
public class SwitchAccount implements java.io.Serializable {

	private static final long serialVersionUID = -364486428821096329L;
	
	private BigDecimal switchAccountId;
	private String name;
	private String arabicName;
	private String category;
	private String code;
	private Double payerCategory;
	private String isEabled;
	
	private Organization organization;
	
	@Id
	@Column(name = "`SwitchAccountId`")
	public BigDecimal getSwitchAccountId() {
		return switchAccountId;
	}
	public void setSwitchAccountId(BigDecimal switchAccountId) {
		this.switchAccountId = switchAccountId;
	}
	
	@Column(name = "`Name`")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "`ArabicName`")
	public String getArabicName() {
		return arabicName;
	}
	public void setArabicName(String arabicName) {
		this.arabicName = arabicName;
	}
	
	@Column(name = "`Category`")
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	@Column(name = "`Code`")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name = "`PayerCategory`", precision = 0)
	public Double getPayerCategory() {
		return this.payerCategory;
	}

	public void setPayerCategory(Double payerCategory) {
		this.payerCategory = payerCategory;
	}
	
	@ManyToOne()
	@JoinColumn(name="`OrganizationId`", referencedColumnName = "`OrganizationId`")
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	
	@Column(name = "`IsEnabled`", precision = 0)
	public String getIsEabled() {
		return isEabled;
	}
	public void setIsEabled(String isEabled) {
		this.isEabled = isEabled;
	}
	
	
}
