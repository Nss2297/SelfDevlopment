package com.waseel.pbm.authentication.model.portal.enity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "`SwitchAccount`")
@Transactional
public class SwitchAccount implements java.io.Serializable {

	private static final long serialVersionUID = -364486428821096329L;
	
	private BigDecimal switchAccountId;
	private String name;
	private String arabicName;
	private String category;
	private String code;
	private Double payerCategory;
	private String isEnabled;
	private String buildingImage;
	private String logo;
	private String websiteUrl;
	private String vATRegistrationNumber;
	private Date lastUpdatedDate;
	private String providerPaymentModel;
	private BigDecimal subscriptionAmount;
	private String IsNetsuiteInvoiceDisabled;
	private String StartBillingNextMonth;
	
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
	
	@Column(name = "`BuildingImage`")
	public String getBuildingImage() {
		return buildingImage;
	}
	
	public void setBuildingImage(String buildingImage) {
		this.buildingImage = buildingImage;
	}
	
	@Column(name = "`IsEnabled`", precision = 0)
	public String getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	@Column(name = "`Logo`")
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
	@Column(name = "`WebsiteUrl`")
	public String getWebsiteUrl() {
		return websiteUrl;
	}
	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}
	
	@Column(name = "`VATRegistrationNumber`")
	public String getvATRegistrationNumber() {
		return vATRegistrationNumber;
	}
	public void setvATRegistrationNumber(String vATRegistrationNumber) {
		this.vATRegistrationNumber = vATRegistrationNumber;
	}
	
	@Column(name = "`LastUpdatedDate`")
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}
	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
	@Column(name = "`ProviderPaymentModel`")
	public String getProviderPaymentModel() {
		return providerPaymentModel;
	}
	public void setProviderPaymentModel(String providerPaymentModel) {
		this.providerPaymentModel = providerPaymentModel;
	}
	
	@Column(name = "`SubscriptionAmount`")
	public BigDecimal getSubscriptionAmount() {
		return subscriptionAmount;
	}
	public void setSubscriptionAmount(BigDecimal subscriptionAmount) {
		this.subscriptionAmount = subscriptionAmount;
	}
	
	@Column(name = "`IsNetsuiteInvoiceDisabled`")
	public String getIsNetsuiteInvoiceDisabled() {
		return IsNetsuiteInvoiceDisabled;
	}
	public void setIsNetsuiteInvoiceDisabled(String isNetsuiteInvoiceDisabled) {
		IsNetsuiteInvoiceDisabled = isNetsuiteInvoiceDisabled;
	}
	
	@Column(name = "`StartBillingNextMonth`")
	public String getStartBillingNextMonth() {
		return StartBillingNextMonth;
	}
	public void setStartBillingNextMonth(String startBillingNextMonth) {
		StartBillingNextMonth = startBillingNextMonth;
	}
	
	
}
