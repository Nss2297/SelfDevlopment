package com.waseel.pbm.pbmadminservice.specification;

import com.waseel.pbm.pbmadminservice.model.drugexclusion.DrugExclusionMetaDataRequestModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.DrugExclusionMetaDataResponseModel;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugExclusionMetadata;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class DrugExclusionSpecification {

    @PersistenceContext(unitName = "businessrules")
    private EntityManager entityManager;

    public Page<DrugExclusionMetaDataResponseModel> getDrugExclusionMetadataWithPagination(
            int pageNumber, int recordSize, DrugExclusionMetaDataRequestModel request) {
        Pageable pageable = PageRequest.of(pageNumber, recordSize);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DrugExclusionMetaDataResponseModel> query =
                criteriaBuilder.createQuery(DrugExclusionMetaDataResponseModel.class);
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<DrugExclusionMetadata> root = query.from(DrugExclusionMetadata.class);
        Root<DrugExclusionMetadata> countQueryRoot = countQuery.from(DrugExclusionMetadata.class);
        query.where(request.toPredicate(root, query, criteriaBuilder));
        query.orderBy(criteriaBuilder.desc(root.get("lastUpdateDate")));
        countQuery.where(query.getRestriction());
        query.multiselect(root.get("exclusionId"), root.get("exclusionName"),
                root.get("createdDate"), root.get("lastUpdateDate"));
        countQuery.select(criteriaBuilder.count(countQueryRoot));
        TypedQuery<DrugExclusionMetaDataResponseModel> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(pageNumber * recordSize);
        typedQuery.setMaxResults(recordSize);
        Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
        List<DrugExclusionMetaDataResponseModel> result = typedQuery.getResultList();
        return PageableExecutionUtils.getPage(result, pageable, () -> totalCount);
    }
}
