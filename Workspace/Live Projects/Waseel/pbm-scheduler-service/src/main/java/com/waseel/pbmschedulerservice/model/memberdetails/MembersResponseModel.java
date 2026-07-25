package com.waseel.pbmschedulerservice.model.memberdetails;

import java.util.List;

public class MembersResponseModel {

    private String policyNumber;
    private String policyHolderName;
    private String expiryDate;
    private List<MemberPolicyAssociationModel> members;
    private Long pageNumber;
    private Long numberOffElements;
    private Long pageSize;
    private Long totalElements;
    private Long totalPages;
    private boolean firstPage;
    private boolean lastPage;

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getPolicyHolderName() {
        return policyHolderName;
    }

    public void setPolicyHolderName(String policyHolderName) {
        this.policyHolderName = policyHolderName;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

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

    public List<MemberPolicyAssociationModel> getMembers() {
        return members;
    }

    public void setMembers(List<MemberPolicyAssociationModel> members) {
        this.members = members;
    }
}
