package com.waseel.pbm.pbmadminservice.model.drugexclusion;

import com.waseel.pbm.pbmadminservice.persist.businessrules.ExclusionAsscTypeList;
import com.waseel.pbm.pbmadminservice.util.UserInfoUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ExclusionTypeSearchModel implements Specification<ExclusionAsscTypeList> {

    private static final long serialVersionUID = 1L;
    private Integer pageNumber = 0;
    private Integer recordSize = 10;
    private String exclusionType;
    private String exclusionName;
    private Long exclusionId;

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

    public String getExclusionType() {
        return exclusionType;
    }

    public void setExclusionType(String exclusionType) {
        this.exclusionType = exclusionType;
    }

    public String getExclusionName() {
        return exclusionName;
    }

    public void setExclusionName(String exclusionName) {
        this.exclusionName = exclusionName;
    }

    public Long getExclusionId() {
        return exclusionId;
    }

    public void setExclusionId(Long exclusionId) {
        this.exclusionId = exclusionId;
    }

    @Override
    public Predicate toPredicate(Root<ExclusionAsscTypeList> root, CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        String payerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
        if (!StringUtils.isBlank(payerId) && !payerId.equals("101")) {
            predicates.add(criteriaBuilder.equal(root.get("payerId"), payerId));
        }
        if (!StringUtils.isBlank(exclusionType)) {
            predicates.add(criteriaBuilder.equal(root.get("exclusionType"), exclusionType));
        }
        if (!StringUtils.isBlank(exclusionName)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("exclusionTypeName")),
                    "%" + exclusionName.trim().toLowerCase() + "%"));
        }
        predicates.add(criteriaBuilder.equal(root.get("exclusionId"), exclusionId));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
