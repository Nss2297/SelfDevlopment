package com.waseel.prescription.specification;

import com.waseel.prescription.model.dispense.DispensedPrescriptionModel;
import com.waseel.prescription.persist.hira.SwitchAccount;
import com.waseel.prescription.persist.prescriptionservice.DispensedPrescription;
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
public class DispensedPrescriptionSpecification {

    @PersistenceContext
    private EntityManager entityManager;

    public Page<DispensedPrescriptionModel> findDispensedPrescriptionWithPagination(
            int pageNumber, int recordSize, DispensedPrescriptionModel model) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DispensedPrescriptionModel> query = criteriaBuilder
                .createQuery(DispensedPrescriptionModel.class);
        Root<DispensedPrescription> root = query.from(DispensedPrescription.class);
        Join<DispensedPrescription, SwitchAccount> drugServiceJoin = root.join("switchAccount", JoinType.INNER);
        query.where(model.toPredicate(root, query, criteriaBuilder));
        query.multiselect(drugServiceJoin.get("name"), root.get("dispenseDate"));
        TypedQuery<DispensedPrescriptionModel> typedQuery = entityManager.createQuery(query);
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<DispensedPrescription> countQueryRoot = countQuery.from(DispensedPrescription.class);
        countQuery.select(criteriaBuilder.count(countQueryRoot));
        countQuery.where(query.getRestriction());
        Pageable pageable = PageRequest.of(pageNumber, recordSize);
        typedQuery.setFirstResult(pageNumber * recordSize);
        typedQuery.setMaxResults(recordSize);
        List<DispensedPrescriptionModel> result = typedQuery.getResultList();
        return PageableExecutionUtils.getPage(result, pageable, () -> result.size());
    }
}
