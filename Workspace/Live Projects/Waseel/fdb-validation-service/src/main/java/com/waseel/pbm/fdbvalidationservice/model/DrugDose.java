package com.waseel.pbm.fdbvalidationservice.model;

import java.math.BigDecimal;

public class DrugDose {

	private FdbDrugList drug;
	private BigDecimal requestedDose;

	public DrugDose() {
		super();
	}

	public DrugDose(FdbDrugList drug, BigDecimal requestedDose) {
		super();
		this.drug = drug;
		this.requestedDose = requestedDose;
	}

	public FdbDrugList getDrug() {
		return drug;
	}

	public void setDrug(FdbDrugList drug) {
		this.drug = drug;
	}

	public BigDecimal getRequestedDose() {
		return requestedDose;
	}

	public void setRequestedDose(BigDecimal requestedDose) {
		this.requestedDose = requestedDose;
	}

}
