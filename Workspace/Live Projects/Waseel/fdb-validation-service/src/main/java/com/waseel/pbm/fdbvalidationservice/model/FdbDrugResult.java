package com.waseel.pbm.fdbvalidationservice.model;

import java.util.List;

public class FdbDrugResult {

	private FdbDrugList drugInfo;
	private String status;
	private List<Error> rejectionReason;

	public FdbDrugList getDrugInfo() {
		return drugInfo;
	}

	public void setDrugInfo(FdbDrugList drugInfo) {
		this.drugInfo = drugInfo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Error> getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(List<Error> rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

}
