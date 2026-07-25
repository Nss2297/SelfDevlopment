package com.waseel.prescription.model.prescription;

import com.waseel.prescription.persist.hira.ICDDiagnosis;
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

    public ICDDiagnosisModel(String icdDiagnosisCode) {
        this.icdDiagnosisCode = icdDiagnosisCode;
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
        predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("icdDiagnosisCode"))
                , icdDiagnosisCode.toLowerCase().trim()));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
