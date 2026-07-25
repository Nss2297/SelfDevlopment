package com.waseel.pbm.pbmadminservice.specification;

import com.waseel.pbm.pbmadminservice.model.DrugServiceModel;
import com.waseel.pbm.pbmadminservice.persist.mdss.DrugService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
public class DrugServiceSpecification {

    @PersistenceContext
    private EntityManager entityManager;

    public Page<DrugServiceModel> findByServiceCodeAndDescWithPagination(int pageNumber, int recordSize,
                                                                         DrugServiceModel drugServiceModel) {
        PageRequest pageRequest = PageRequest.of(pageNumber, recordSize);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DrugServiceModel> query =
                criteriaBuilder.createQuery(DrugServiceModel.class);
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<DrugService> root = query.from(DrugService.class);
        Root<DrugService> countQueryRoot = countQuery.from(DrugService.class);
        query.where(drugServiceModel.toPredicate(root, query, criteriaBuilder));
        countQuery.where(query.getRestriction());
        query.multiselect(root.get("otherCodesValue"), root.get("display"));
        countQuery.select(criteriaBuilder.count(countQueryRoot));
        TypedQuery<DrugServiceModel> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(pageNumber * recordSize);
        typedQuery.setMaxResults(recordSize);
        Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
        List<DrugServiceModel> result = typedQuery.getResultList();
        return PageableExecutionUtils.getPage(result, pageRequest, () -> totalCount);
    }
}
