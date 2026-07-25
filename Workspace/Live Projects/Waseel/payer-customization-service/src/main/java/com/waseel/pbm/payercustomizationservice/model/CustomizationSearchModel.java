package com.waseel.pbm.payercustomizationservice.model;

import com.waseel.pbm.payercustomizationservice.persist.CustomizationRequestMetadata;
import com.waseel.pbm.payercustomizationservice.util.UserInfoUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class CustomizationSearchModel implements Specification<CustomizationRequestMetadata> {
    private static final long serialVersionUID = 1L;
    private String status;
    private Integer pageNumber = 0;
    private Integer recordSize = 10;
    private String fromDate;
    private String endDate;
    private String moduleName;
    private String drugCode;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getRecordSize() {
        return recordSize;
    }

    public void setRecordSize(Integer recordSize) {
        this.recordSize = recordSize;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    @Override
    public Predicate toPredicate(Root<CustomizationRequestMetadata> root, CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (!StringUtils.isBlank(drugCode)) {
            predicates.add(criteriaBuilder.like(root.get("drugCode"), "%" + drugCode.trim() + "%"));
        }
        if (!StringUtils.isBlank(moduleName)) {
            predicates.add(criteriaBuilder.equal(root.get("moduleName"), moduleName.trim()));
        }
        if (!StringUtils.isBlank(status)) {
            predicates.add(criteriaBuilder.equal(root.get("status"), status.trim()));
        }
        if (!StringUtils.isBlank(fromDate)) {
            LocalDate date = null;
            try {
                date = LocalDate.parse(fromDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            } catch (DateTimeParseException e) {
                e.printStackTrace();
            }
            if (date != null) {
                predicates.add(
                        criteriaBuilder.greaterThanOrEqualTo(root.get("lastUpdatedDate").as(LocalDate.class), date));
            }
        }
        if (!StringUtils.isBlank(endDate)) {
            LocalDate date = null;
            try {
                date = LocalDate.parse(endDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

            } catch (DateTimeParseException e) {
                e.printStackTrace();
            }
            if (date != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("lastUpdatedDate").as(LocalDate.class),
                        date.plusDays(1)));
            }
        }

        String payerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
        if (!StringUtils.isBlank(payerId) && !payerId.equals("101")) {
            predicates.add(criteriaBuilder.equal(root.get("payerId"), payerId));
        }

        predicates.add(criteriaBuilder.equal(root.get("isDeleted"), 0));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
