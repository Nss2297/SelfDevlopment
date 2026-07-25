package com.waseel.pbm.pbmadminservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waseel.pbm.pbmadminservice.persist.businessrules.Speciality;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SpecialityModel implements Specification<Speciality> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private BigDecimal specialityId;
    private String specialityName;

    public BigDecimal getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(BigDecimal specialityId) {
        this.specialityId = specialityId;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public SpecialityModel(BigDecimal specialityId, String specialityName) {
        this.specialityId = specialityId;
        this.specialityName = specialityName;
    }

    public SpecialityModel(String value) {
        this.specialityId = isValidBigDecimal(value) ? new BigDecimal(value) : null;
        this.specialityName = value;
    }

    private boolean isValidBigDecimal(String value) {
        try {
            new BigDecimal(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public Predicate toPredicate(Root<Speciality> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(root.get("isDeleted"), 0));
        if (specialityId != null) {
            predicates.add(criteriaBuilder.equal(root.get("specialityId"), specialityId));
        }
        if (!StringUtils.isBlank(specialityName)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("specialityName")),
                    "%" + specialityName.trim().toLowerCase() + "%"));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
