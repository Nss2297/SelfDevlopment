package com.waseel.policy.model;

import java.math.BigDecimal;

public class BrandAndGenericModel {

	private BigDecimal genericDrugPatientShareValue;
	private String genericDrugPatientShareCurrency;
	private BigDecimal genericDrugMaxPatientShare;
	private String genericDrugMaxPatientShareCurrency;
	private BigDecimal brandDrugPatientShareValue;
	private String brandDrugPatientShareCurrency;
	private BigDecimal brandDrugMaxPatientShare;
	private String brandedDrugMaxPatientShareCurrency;

	public BigDecimal getGenericDrugPatientShareValue() {
		return genericDrugPatientShareValue;
	}

	public void setGenericDrugPatientShareValue(BigDecimal genericDrugPatientShareValue) {
		this.genericDrugPatientShareValue = genericDrugPatientShareValue;
	}

	public String getGenericDrugPatientShareCurrency() {
		return genericDrugPatientShareCurrency;
	}

	public void setGenericDrugPatientShareCurrency(String genericDrugPatientShareCurrency) {
		this.genericDrugPatientShareCurrency = genericDrugPatientShareCurrency;
	}

	public BigDecimal getGenericDrugMaxPatientShare() {
		return genericDrugMaxPatientShare;
	}

	public void setGenericDrugMaxPatientShare(BigDecimal genericDrugMaxPatientShare) {
		this.genericDrugMaxPatientShare = genericDrugMaxPatientShare;
	}

	public BigDecimal getBrandDrugPatientShareValue() {
		return brandDrugPatientShareValue;
	}

	public void setBrandDrugPatientShareValue(BigDecimal brandDrugPatientShareValue) {
		this.brandDrugPatientShareValue = brandDrugPatientShareValue;
	}

	public String getBrandDrugPatientShareCurrency() {
		return brandDrugPatientShareCurrency;
	}

	public void setBrandDrugPatientShareCurrency(String brandDrugPatientShareCurrency) {
		this.brandDrugPatientShareCurrency = brandDrugPatientShareCurrency;
	}

	public BigDecimal getBrandDrugMaxPatientShare() {
		return brandDrugMaxPatientShare;
	}

	public void setBrandDrugMaxPatientShare(BigDecimal brandDrugMaxPatientShare) {
		this.brandDrugMaxPatientShare = brandDrugMaxPatientShare;
	}

	
	public String getGenericDrugMaxPatientShareCurrency() {
		return genericDrugMaxPatientShareCurrency;
	}

	public String getBrandedDrugMaxPatientShareCurrency() {
		return brandedDrugMaxPatientShareCurrency;
	}

	public void setGenericDrugMaxPatientShareCurrency(String genericDrugMaxPatientShareCurrency) {
		this.genericDrugMaxPatientShareCurrency = genericDrugMaxPatientShareCurrency;
	}

	public void setBrandedDrugMaxPatientShareCurrency(String brandedDrugMaxPatientShareCurrency) {
		this.brandedDrugMaxPatientShareCurrency = brandedDrugMaxPatientShareCurrency;
	}

	public BrandAndGenericModel(BigDecimal genericDrugPatientShareValue, String genericDrugPatientShareCurrency,
			BigDecimal genericDrugMaxPatientShare, BigDecimal brandDrugPatientShareValue,
			String brandDrugPatientShareCurrency, BigDecimal brandDrugMaxPatientShare) {
		super();
		this.genericDrugPatientShareValue = genericDrugPatientShareValue;
		this.genericDrugPatientShareCurrency = genericDrugPatientShareCurrency;
		this.genericDrugMaxPatientShare = genericDrugMaxPatientShare;
		this.brandDrugPatientShareValue = brandDrugPatientShareValue;
		this.brandDrugPatientShareCurrency = brandDrugPatientShareCurrency;
		this.brandDrugMaxPatientShare = brandDrugMaxPatientShare;
	}
	
	public BrandAndGenericModel(BigDecimal genericDrugPatientShareValue, String genericDrugPatientShareCurrency,
			BigDecimal genericDrugMaxPatientShare, BigDecimal brandDrugPatientShareValue,
			String brandDrugPatientShareCurrency, BigDecimal brandDrugMaxPatientShare, String genericDrugMaxPatientShareCurrency, String brandedDrugMaxPatientShareCurrency) {
		super();
		this.genericDrugPatientShareValue = genericDrugPatientShareValue;
		this.genericDrugPatientShareCurrency = genericDrugPatientShareCurrency;
		this.genericDrugMaxPatientShare = genericDrugMaxPatientShare;
		this.brandDrugPatientShareValue = brandDrugPatientShareValue;
		this.brandDrugPatientShareCurrency = brandDrugPatientShareCurrency;
		this.brandDrugMaxPatientShare = brandDrugMaxPatientShare;
		this.genericDrugMaxPatientShareCurrency = genericDrugMaxPatientShareCurrency;
		this.brandedDrugMaxPatientShareCurrency = brandedDrugMaxPatientShareCurrency;
	}
}
