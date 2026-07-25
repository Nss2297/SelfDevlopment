package com.waseel.dssadminservice.specification;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.waseel.dssadminservice.model.customization.pcduplicatetherapy.DuplicateTherapyResponseModel;
import com.waseel.dssadminservice.model.customization.pcduplicatetherapy.DuplicateTherapySearchModel;
import com.waseel.dssadminservice.persist.mdss.PCDuplicateTherapy;

@Repository
public class PCDuplicateTherapySpecification {

    @PersistenceContext
    private EntityManager entityManager;

    public Page<DuplicateTherapyResponseModel> getPCDuplicateTherapy(
            DuplicateTherapySearchModel duplicateTherapySearchModel) {
        int pageNumber = duplicateTherapySearchModel.getPageNumber();
        int recordSize = duplicateTherapySearchModel.getRecordSize();
        Pageable pageable = PageRequest.of(pageNumber, recordSize);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DuplicateTherapyResponseModel> query = criteriaBuilder.createQuery(DuplicateTherapyResponseModel.class);
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<PCDuplicateTherapy> root = query.from(PCDuplicateTherapy.class);
        Root<PCDuplicateTherapy> countQueryRoot = countQuery.from(PCDuplicateTherapy.class);
        query.where(duplicateTherapySearchModel.toPredicate(root, query, criteriaBuilder));
        query.orderBy(criteriaBuilder.desc(root.get("lastUpdatedDateTime")));
        countQuery.where(query.getRestriction());
        query.multiselect(root.get("seqId"), root.get("id").get("serviceCode"), root.get("id").get("interactedServiceCode"),
                          root.get("id").get("payerId"), root.get("serviceStatus"), root.get("id").get("moduleName"),
                          root.get("additionalRejectionReason"), root.get("lastUpdatedDateTime"), root.get("scientificCode"));
        countQuery.select(criteriaBuilder.count(countQueryRoot));
        TypedQuery<DuplicateTherapyResponseModel> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(pageNumber * recordSize);
        typedQuery.setMaxResults(recordSize);
        Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
        List<DuplicateTherapyResponseModel> result = typedQuery.getResultList();
        return PageableExecutionUtils.getPage(result, pageable, () -> totalCount);
    }
}