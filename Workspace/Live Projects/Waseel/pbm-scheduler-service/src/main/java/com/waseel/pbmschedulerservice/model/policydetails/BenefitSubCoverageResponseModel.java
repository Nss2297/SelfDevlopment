package com.waseel.pbmschedulerservice.model.policydetails;

import java.util.List;

public class BenefitSubCoverageResponseModel {

	private String policyNumber;
	private String classCode;
	private String benefitCode;
	private List<BenefitSubCoverageModel> benefitSubCoverage;
	private Long pageNumber;
	private Long numberOffElements;
	private Long pageSize;
	private boolean firstPage;
	private boolean lastPage;
	private Long totalElements;
	private Long totalPages;

	public Long getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Long pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Long getNumberOffElements() {
		return numberOffElements;
	}

	public void setNumberOffElements(Long numberOffElements) {
		this.numberOffElements = numberOffElements;
	}

	public Long getPageSize() {
		return pageSize;
	}

	public void setPageSize(Long pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isFirstPage() {
		return firstPage;
	}

	public void setFirstPage(boolean firstPage) {
		this.firstPage = firstPage;
	}

	public boolean isLastPage() {
		return lastPage;
	}

	public void setLastPage(boolean lastPage) {
		this.lastPage = lastPage;
	}

	public Long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(Long totalElements) {
		this.totalElements = totalElements;
	}

	public Long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Long totalPages) {
		this.totalPages = totalPages;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getBenefitCode() {
		return benefitCode;
	}

	public void setBenefitCode(String benefitCode) {
		this.benefitCode = benefitCode;
	}

	public List<BenefitSubCoverageModel> getBenefitSubCoverage() {
		return benefitSubCoverage;
	}

	public void setBenefitSubCoverage(List<BenefitSubCoverageModel> benefitSubCoverage) {
		this.benefitSubCoverage = benefitSubCoverage;
	}
}
