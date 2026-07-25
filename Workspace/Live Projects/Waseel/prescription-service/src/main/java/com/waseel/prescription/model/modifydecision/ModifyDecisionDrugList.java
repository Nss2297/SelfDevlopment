package com.waseel.prescription.model.modifydecision;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

import org.apache.commons.lang3.StringUtils;

import com.waseel.prescription.model.prescription.CommonDrugList;
import com.waseel.prescription.validator.customannotation.IsValidServiceStatus;
import com.waseel.prescription.validator.customannotation.NoMoreThan3000Length;

public class ModifyDecisionDrugList extends CommonDrugList {

	@NotBlank(message = "status {notEmptyValidation}")
	@IsValidServiceStatus(message = "{serviceStatusValidation}")
	private String status;

	@NoMoreThan3000Length(message = "decisionDescription {noMoreThan3000LengthValidation}")
	private String decisionDescription;

	private BigDecimal recalculatedPatientShare;

	private BigDecimal recalculatedNet;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = !StringUtils.isBlank(status) ? status.trim() : status;
	}

	public String getDecisionDescription() {
		return decisionDescription;
	}

	public void setDecisionDescription(String decisionDescription) {
		this.decisionDescription = !StringUtils.isBlank(decisionDescription) ? decisionDescription.trim()
				: decisionDescription;
	}

	public ModifyDecisionDrugList() {
		super();
	}

	public ModifyDecisionDrugList(String drugCode, String unitType, BigDecimal quantity, Double unitPrice,
			Double useUnitValue, String frequency, String frequencyOthersDescription, Long duration, BigDecimal net,
			BigDecimal patientShare, String status, String decisionDescription) {
		super(drugCode, unitType, quantity, unitPrice, useUnitValue, frequency, frequencyOthersDescription, duration,
				net, patientShare);
		this.status = status;
		this.decisionDescription = decisionDescription;
	}

	public BigDecimal getRecalculatedPatientShare() {
		return recalculatedPatientShare;
	}

	public BigDecimal getRecalculatedNet() {
		return recalculatedNet;
	}

	public void setRecalculatedPatientShare(BigDecimal recalculatedPatientShare) {
		this.recalculatedPatientShare = recalculatedPatientShare;
	}

	public void setRecalculatedNet(BigDecimal recalculatedNet) {
		this.recalculatedNet = recalculatedNet;
	}

}
