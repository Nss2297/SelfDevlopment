package com.waseel.pbm.fdbvalidationservice.dto;

public class FdbDrugInfo {
	
	private Integer gcnSeqNo;
	private String productPackageUnit;
	private Double productPackageSize;
	
	
	public FdbDrugInfo() {
		super();
	}

	public FdbDrugInfo(Integer gcnSeqNo, String productPackageUnit , Double productPackageSize) {
		super();
		this.gcnSeqNo = gcnSeqNo;
		this.productPackageUnit = productPackageUnit;
		this.productPackageSize = productPackageSize;
	}
	
	public Integer getGcnSeqNo() {
		return gcnSeqNo;
	}

	public void setGcnSeqNo(Integer gcnSeqNo) {
		this.gcnSeqNo = gcnSeqNo;
	}

	public String getProductPackageUnit() {
		return productPackageUnit;
	}

	public void setProductPackageUnit(String productPackageUnit) {
		this.productPackageUnit = productPackageUnit;
	}

	public Double getProductPackageSize() {
		return productPackageSize;
	}

	public void setProductPackageSize(Double productPackageSize) {
		this.productPackageSize = productPackageSize;
	}
	
	
	
}
