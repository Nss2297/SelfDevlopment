package com.waseel.pbmschedulerservice.model.memberdetails;

import java.util.List;

public class MemberDetailsResponseModel {

    private List<MembersResponseModel> memberDetails;
    private Long pageNumber;
    private Long numberOffElements;
    private Long pageSize;
    private Long totalElements;
    private Long totalPages;
    private boolean firstPage;
    private boolean lastPage;

    public List<MembersResponseModel> getMemberDetails() {
        return memberDetails;
    }

    public void setMemberDetails(List<MembersResponseModel> memberDetails) {
        this.memberDetails = memberDetails;
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
}
