package com.waseel.prescription.specification;

import com.waseel.prescription.model.prescription.DiagnosisCodes;
import com.waseel.prescription.persist.hira.ICDDiagnosis;
import com.waseel.prescription.persist.prescriptionservice.Diagnosis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Component
public class DiagnosisSpecification {

    @PersistenceContext
    private EntityManager entityManager;

    public Page<DiagnosisCodes> findByRequestIdWithPagination(int pageNumber, int recordSize,
                                                              DiagnosisCodes diagnosisCodes) {
        Pageable pageable = PageRequest.of(pageNumber, recordSize);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DiagnosisCodes> query = criteriaBuilder
                .createQuery(DiagnosisCodes.class);
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Diagnosis> root = query.from(Diagnosis.class);
        Join<Diagnosis, ICDDiagnosis> icdDiagnosisJoin = root.join("icdDiagnosis", JoinType.INNER);
        Root<Diagnosis> countQueryRoot = countQuery.from(Diagnosis.class);
        Predicate predicate = diagnosisCodes.toPredicate(root, query, criteriaBuilder);
        query.where(predicate);
        countQuery.where(query.getRestriction());
        query.multiselect(root.get("diagnosisId").get("diagnosisCode"), root.get("diagnosisType"),
                icdDiagnosisJoin.get("description"));
        countQuery.select(criteriaBuilder.count(countQueryRoot));
        TypedQuery<DiagnosisCodes> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(pageNumber * recordSize);
        typedQuery.setMaxResults(recordSize);
        Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
        List<DiagnosisCodes> result = typedQuery.getResultList();
        return PageableExecutionUtils.getPage(result, pageable, () -> totalCount);
    }
}
