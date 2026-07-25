package com.waseel.pbm.pbmadminservice.model.drugexclusion;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugExclusionDetails;
import com.waseel.pbm.pbmadminservice.util.UserInfoUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DrugExclusionDrugDetailsModel implements Specification<DrugExclusionDetails> {

    private static final long serialVersionUID = 1L;
    private Integer pageNumber;
    private Integer recordSize;
    private Long exclusionId;
    private String drugCode;
    private String drugName;
    private String scientificName;
    private String scientificCode;
    private String lastUpdateDate;
    private String updatedDateFrom;
    private String updatedDateTo;
    private BigDecimal price;
    private Long drugExclusionDetailsId;

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

    public Long getExclusionId() {
        return exclusionId;
    }

    public void setExclusionId(Long exclusionId) {
        this.exclusionId = exclusionId;
    }

    public String getDrugCode() {
        return drugCode;
    }

    public void setDrugCode(String drugCode) {
        this.drugCode = drugCode;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getScientificCode() {
        return scientificCode;
    }

    public void setScientificCode(String scientificCode) {
        this.scientificCode = scientificCode;
    }

    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public String getUpdatedDateFrom() {
        return updatedDateFrom;
    }

    public void setUpdatedDateFrom(String updatedDateFrom) {
        this.updatedDateFrom = updatedDateFrom;
    }

    public String getUpdatedDateTo() {
        return updatedDateTo;
    }

    public void setUpdatedDateTo(String updatedDateTo) {
        this.updatedDateTo = updatedDateTo;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getDrugExclusionDetailsId() {
        return drugExclusionDetailsId;
    }

    public void setDrugExclusionDetailsId(Long drugExclusionDetailsId) {
        this.drugExclusionDetailsId = drugExclusionDetailsId;
    }

    public DrugExclusionDrugDetailsModel(String drugCode, String drugName, String scientificName,
                                         String scientificCode, BigDecimal price, Date lastUpdateDate,
                                         Long drugExclusionDetailsId) {
        this.drugCode = drugCode;
        this.drugName = drugName;
        this.scientificName = scientificName;
        this.scientificCode = scientificCode;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        if (lastUpdateDate != null) {
            this.lastUpdateDate = simpleDateFormat.format(lastUpdateDate);
        }
        this.price = price;
        this.drugExclusionDetailsId = drugExclusionDetailsId;
    }

    @Override
    public Predicate toPredicate(Root<DrugExclusionDetails> root, CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        String payerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
        if (exclusionId != null) {
            predicates.add(criteriaBuilder.equal(root.get("exclusionId"), exclusionId));
        }
        if (!StringUtils.isBlank(drugCode)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("registrationNumber")),
                    "%" + drugCode.toLowerCase().trim() + "%"));
        }
        if (!StringUtils.isBlank(drugName)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("tradeName")),
                    "%" + drugName.toLowerCase().trim() + "%"));
        }
        if (!StringUtils.isBlank(scientificName)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("scientificName")),
                    "%" + scientificName.toLowerCase().trim() + "%"));
        }
        if (!StringUtils.isBlank(scientificCode)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("scientificCode")),
                    "%" + scientificCode.toLowerCase().trim() + "%"));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        if (!StringUtils.isBlank(updatedDateFrom)) {
            try {
                Date date = simpleDateFormat.parse(updatedDateFrom);
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("lastUpdateDate"), date));
            } catch (Exception e) {

            }
        }
        if (!StringUtils.isBlank(updatedDateTo)) {
            try {
                Date date = simpleDateFormat.parse(updatedDateTo);
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                c.add(Calendar.DATE, 1);
                date = c.getTime();
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("lastUpdateDate"), date));
            } catch (Exception e) {

            }
        }
        predicates.add(criteriaBuilder.equal(root.get("isDeleted"), false));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
