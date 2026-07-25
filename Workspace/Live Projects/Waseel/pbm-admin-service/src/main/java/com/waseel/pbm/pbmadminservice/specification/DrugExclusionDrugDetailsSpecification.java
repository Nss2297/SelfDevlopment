package com.waseel.pbm.pbmadminservice.specification;

import com.waseel.pbm.pbmadminservice.model.drugexclusion.DrugExclusionDrugDetailsModel;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugExclusionDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class DrugExclusionDrugDetailsSpecification {

    @PersistenceContext(unitName = "businessrules")
    private EntityManager entityManager;

    public Page<DrugExclusionDrugDetailsModel> findDrugExclusionDrugDetailsWithPagination(
            DrugExclusionDrugDetailsModel drugDetailsModel) {
        int pageNumber = drugDetailsModel.getPageNumber();
        int recordSize = drugDetailsModel.getRecordSize();
        PageRequest pageRequest = PageRequest.of(pageNumber, recordSize);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DrugExclusionDrugDetailsModel> query = criteriaBuilder
                .createQuery(DrugExclusionDrugDetailsModel.class);
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<DrugExclusionDetails> root = query.from(DrugExclusionDetails.class);
        Root<DrugExclusionDetails> countQueryRoot = countQuery.from(DrugExclusionDetails.class);
        query.where(drugDetailsModel.toPredicate(root, query, criteriaBuilder));
        countQuery.where(query.getRestriction());
        query.multiselect(root.get("registrationNumber"), root.get("tradeName"), root.get("scientificName"),
                root.get("scientificCode"), root.get("price"), root.get("lastUpdateDate"),
                root.get("drugExclusionDetailsId"));
        countQuery.select(criteriaBuilder.count(countQueryRoot));
        TypedQuery<DrugExclusionDrugDetailsModel> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(pageNumber * recordSize);
        typedQuery.setMaxResults(recordSize);
        Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
        List<DrugExclusionDrugDetailsModel> result = typedQuery.getResultList();
        return PageableExecutionUtils.getPage(result, pageRequest, () -> totalCount);
    }
}
