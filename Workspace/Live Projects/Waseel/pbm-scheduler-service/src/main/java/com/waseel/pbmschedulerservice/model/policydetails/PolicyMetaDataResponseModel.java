package com.waseel.pbmschedulerservice.model.policydetails;

import java.util.List;

public class PolicyMetaDataResponseModel {

	private List<PolicyMetadataModel> policyMetadata;
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

	public List<PolicyMetadataModel> getPolicyMetadata() {
		return policyMetadata;
	}

	public void setPolicyMetadata(List<PolicyMetadataModel> policyMetadata) {
		this.policyMetadata = policyMetadata;
	}
}
