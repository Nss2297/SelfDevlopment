package com.waseel.prescription.model.dispense;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.waseel.prescription.validator.customannotation.NoMoreThanTenLength;

public class DispenseDrugsRequestModel {

	@NotNull(message = "totalPatientShare {notEmptyValidation}")
	@Digits(integer = 12, fraction = 2, message = "totalPatientShare {noMoreThanTwelveWithTwoDecimalPrecisionValidation}")
	private BigDecimal totalPatientShare;
	
	@NotNull(message = "totalNet {notEmptyValidation}")
	@Digits(integer = 12, fraction = 2, message = "totalNet {noMoreThanTwelveWithTwoDecimalPrecisionValidation}")
	private BigDecimal totalNet;
	
	@NotBlank(message = "totalPatientShareCurrency {emptyDateValidation}")
	@NoMoreThanTenLength(message = "totalPatientShareCurrency {noMoreThanTenLengthValidation}")
	private String totalPatientShareCurrency;
	
	@NotBlank(message = "totalNetCurrency {emptyDateValidation}")
	@NoMoreThanTenLength(message = "totalNetCurrency {noMoreThanTenLengthValidation}")
	private String totalNetCurrency;
	
	@NotEmpty(message = "drugList {notEmptyValidation}")
	@Valid
	private List<DispensableDrugs> drugList;
	
	public DispenseDrugsRequestModel() {
	}

	public DispenseDrugsRequestModel(BigDecimal totalPatientShare, BigDecimal totalNet,
			String totalPatientShareCurrency, String totalNetCurrency, List<DispensableDrugs> drugList) {
		this.totalPatientShare = totalPatientShare;
		this.totalNet = totalNet;
		this.totalPatientShareCurrency = totalPatientShareCurrency;
		this.totalNetCurrency = totalNetCurrency;
		this.drugList = drugList;
	}

	public BigDecimal getTotalPatientShare() {
		return totalPatientShare;
	}

	public void setTotalPatientShare(BigDecimal totalPatientShare) {
		this.totalPatientShare = totalPatientShare;
	}

	public BigDecimal getTotalNet() {
		return totalNet;
	}

	public void setTotalNet(BigDecimal totalNet) {
		this.totalNet = totalNet;
	}

	public List<DispensableDrugs> getDrugList() {
		return drugList;
	}

	public void setDrugList(List<DispensableDrugs> drugList) {
		this.drugList = drugList;
	}

	public String getTotalPatientShareCurrency() {
		return totalPatientShareCurrency;
	}

	public void setTotalPatientShareCurrency(String totalPatientShareCurrency) {
		this.totalPatientShareCurrency = totalPatientShareCurrency;
	}

	public String getTotalNetCurrency() {
		return totalNetCurrency;
	}

	public void setTotalNetCurrency(String totalNetCurrency) {
		this.totalNetCurrency = totalNetCurrency;
	}
}
