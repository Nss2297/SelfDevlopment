package com.waseel.pbm.pbmadminservice.model;

import com.waseel.pbm.pbmadminservice.persist.hira.ICDDiagnosis;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ICDDiagnosisModel implements Specification<ICDDiagnosis> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String icdDiagnosisCode;
    private String description;

    public ICDDiagnosisModel() {
    }

    public ICDDiagnosisModel(String icdDiagnosisCode, String description) {
        super();
        this.icdDiagnosisCode = icdDiagnosisCode;
        this.description = description;
    }

    public String getIcdDiagnosisCode() {
        return icdDiagnosisCode;
    }

    public void setIcdDiagnosisCode(String icdDiagnosisCode) {
        this.icdDiagnosisCode = icdDiagnosisCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Predicate toPredicate(Root<ICDDiagnosis> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (!StringUtils.isBlank(icdDiagnosisCode)) {
            String code = icdDiagnosisCode.toLowerCase().trim();
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("icdDiagnosisCode"))
                    , "%" + code + "%"));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description"))
                    , "%" + code + "%"));
            return criteriaBuilder.or(predicates.get(0), predicates.get(1));
        }
        if (!StringUtils.isBlank(description)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description"))
                    , "%" + description.toLowerCase().trim() + "%"));
        }
        query.orderBy(criteriaBuilder.asc(root.get("icdDiagnosisCode")));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
