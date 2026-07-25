package com.waseel.prescription.model.dispense;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DispensableDrugsSummaryModel {

	private BigDecimal totalQuantity;
	private BigDecimal grandTotal;
	private Integer totalDrugs;

	public BigDecimal getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(BigDecimal totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public BigDecimal getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(BigDecimal grandTotal) {
		this.grandTotal = grandTotal;
	}

	public Integer getTotalDrugs() {
		return totalDrugs;
	}

	public void setTotalDrugs(Integer totalDrugs) {
		this.totalDrugs = totalDrugs;
	}

	public DispensableDrugsSummaryModel(BigDecimal totalQuantity, BigDecimal grandTotal, Integer totalDrugs) {
		super();
		this.totalQuantity = totalQuantity;
		this.grandTotal = grandTotal;
		this.totalDrugs = totalDrugs;
	}

	@Override
	public String toString() {
		return "DispensableDrugsSummaryModel [totalQuantity=" + totalQuantity + ", grandTotal=" + grandTotal
				+ ", totalDrugs=" + totalDrugs + ", getTotalQuantity()=" + getTotalQuantity() + ", getGrandTotal()="
				+ getGrandTotal() + ", getTotalDrugs()=" + getTotalDrugs() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

}
