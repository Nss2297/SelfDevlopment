package com.waseel.prescription.model.prescription;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.waseel.prescription.validator.customannotation.IsNumber;
import com.waseel.prescription.validator.customannotation.IsValidFrequencyType;
import com.waseel.prescription.validator.customannotation.IsValidUnitType;
import com.waseel.prescription.validator.customannotation.NoMoreThan250Length;
import com.waseel.prescription.validator.customannotation.NoMoreThanTenLength;
import com.waseel.prescription.validator.customannotation.NoSpecialCharacter;
import com.waseel.prescription.validator.customannotation.NoWhiteSpaceCharacter;

import io.swagger.v3.oas.annotations.media.Schema;

public class CommonDrugList {

//	@NotEmpty(message = "drugCode {notEmptyValidation}")
	private String drugCode;

	@NotEmpty(message = "unitType {notEmptyValidation}")
	@IsValidUnitType(message = "{unitTypeValidation}")
	private String unitType;

	@NotNull(message = "quantity {notEmptyValidation}")
	@DecimalMin(value = "1", message = "Quantity value should be more than or equal to 1")
	@DecimalMax(value = "999", message = "Quantity value should be less than or equal to 999")
	private BigDecimal quantity;

//	@NotNull(message = "unitPrice {notEmptyValidation}")
	@Digits(integer = 12, fraction = 2, message = "unitPrice {noMoreThanTwelveWithTwoDecimalPrecisionValidation}")
	private Double unitPrice;

	@NotEmpty(message = "UseUnit {notEmptyValidation}")
	@IsNumber(message = "useUnitValue {notAnumberValidation}")
	@DecimalMin(value = "1", message = "UseUnitValue value should be more than or equal to 1")
	@DecimalMax(value = "99", message = "UseUnitValue value should be less than or equal to 99")
	private String useUnitValue;

	@NotEmpty(message = "frequency {notEmptyValidation}")
	@IsValidFrequencyType(message = "{frequencyTypeValidation}")
	private String frequency;

	@NoMoreThan250Length(message = "frequencyOthersDescription {noMoreThan250LengthValidation}")
	private String frequencyOthersDescription;

	@NoMoreThanTenLength(message = "duration {noMoreThanTenLengthValidation}")
	@NoWhiteSpaceCharacter(message = "duration {noWhiteSpaceCharacterValidation}")
	@NoSpecialCharacter(message = "duration {noSpecialCharactersValidation}")
	@IsNumber(message = "duration {notAnumberValidation}")
	@Min(value = 1, message = "duration value should be more than or equal to 1")
	private String duration;

	@Schema(hidden = true)
	private BigDecimal net;

	@Schema(hidden = true)
	private BigDecimal patientShare;

	@Schema(hidden = true)
	private BigDecimal totalOfNetAndPatientShare;

	private String scientificCode;

	public CommonDrugList() {
		super();
	}

	public CommonDrugList(String drugCode, String unitType, BigDecimal quantity, Double unitPrice, String frequency,
			String frequencyOthersDescription, String duration) {
		this.drugCode = drugCode;
		this.unitType = unitType;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.frequency = frequency;
		this.frequencyOthersDescription = frequencyOthersDescription;
		this.duration = duration;
	}

	public CommonDrugList(String drugCode, String unitType, BigDecimal quantity, Double unitPrice, Double useUnitValue,
			String frequency, String frequencyOthersDescription, Long duration, BigDecimal net,
			BigDecimal patientShare) {
		this.drugCode = drugCode;
		this.unitType = unitType;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.duration = String.valueOf(duration);
		this.frequency = frequency;
		this.frequencyOthersDescription = frequencyOthersDescription;
		if (useUnitValue != null)
			this.useUnitValue = useUnitValue + "";
		this.net = net != null ? net.setScale(2, RoundingMode.HALF_UP) : new BigDecimal(0);
		this.patientShare = patientShare != null ? patientShare.setScale(2, RoundingMode.HALF_UP) : new BigDecimal(0);
		this.totalOfNetAndPatientShare = this.net.add(this.patientShare);
	}

	public CommonDrugList(String drugCode, String unitType, BigDecimal quantity, Double unitPrice, Double useUnitValue,
			String frequency, String frequencyOthersDescription, Long duration, BigDecimal net,
			BigDecimal patientShare,String scientificCode) {
		this.drugCode = drugCode;
		this.unitType = unitType;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.duration = String.valueOf(duration);
		this.frequency = frequency;
		this.frequencyOthersDescription = frequencyOthersDescription;
		if (useUnitValue != null)
			this.useUnitValue = useUnitValue + "";
		this.net = net != null ? net.setScale(2, RoundingMode.HALF_UP) : new BigDecimal(0);
		this.patientShare = patientShare != null ? patientShare.setScale(2, RoundingMode.HALF_UP) : new BigDecimal(0);
		this.totalOfNetAndPatientShare = this.net.add(this.patientShare);
		this.scientificCode = scientificCode;
	}
	
	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getUseUnitValue() {
		return useUnitValue;
	}

	public void setUseUnitValue(String useUnitValue) {
		this.useUnitValue = useUnitValue;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getFrequencyOthersDescription() {
		return frequencyOthersDescription;
	}

	public void setFrequencyOthersDescription(String frequencyOthersDescription) {
		this.frequencyOthersDescription = frequencyOthersDescription;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public BigDecimal getNet() {
		return net;
	}

	public void setNet(BigDecimal net) {
		this.net = net;
	}

	public BigDecimal getPatientShare() {
		return patientShare;
	}

	public void setPatientShare(BigDecimal patientShare) {
		this.patientShare = patientShare;
	}

	public BigDecimal getTotalOfNetAndPatientShare() {
		return totalOfNetAndPatientShare;
	}

	public void setTotalOfNetAndPatientShare(BigDecimal totalOfNetAndPatientShare) {
		this.totalOfNetAndPatientShare = totalOfNetAndPatientShare;
	}

	public String getScientificCode() {
		return scientificCode;
	}

	public void setScientificCode(String scientificCode) {
		this.scientificCode = scientificCode;
	}
}
