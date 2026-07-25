package com.waseel.pbm.pbmadminservice.specification;

import com.waseel.pbm.pbmadminservice.model.PayerConfigModel;
import com.waseel.pbm.pbmadminservice.persist.mdss.PayerConfig;
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
public class PayerConfigSpecification {

    @PersistenceContext
    private EntityManager entityManager;

    public Page<PayerConfigModel> findByPayerIdWithPagination(int pageNumber, int recordSize,
                                                              String payerId) {
        PayerConfigModel payerConfigModel = new PayerConfigModel(payerId);
        PageRequest pageRequest = PageRequest.of(pageNumber, recordSize);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<PayerConfigModel> query = criteriaBuilder.createQuery(PayerConfigModel.class);
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<PayerConfig> root = query.from(PayerConfig.class);
        Root<PayerConfig> countQueryRoot = countQuery.from(PayerConfig.class);
        query.where(payerConfigModel.toPredicate(root, query, criteriaBuilder));
        countQuery.where(query.getRestriction());
        query.multiselect(root.get("id").get("payerId"), root.get("id").get("pbmPayerType"));
        countQuery.select(criteriaBuilder.count(countQueryRoot));
        TypedQuery<PayerConfigModel> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(pageNumber * recordSize);
        typedQuery.setMaxResults(recordSize);
        Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
        List<PayerConfigModel> result = typedQuery.getResultList();
        return PageableExecutionUtils.getPage(result, pageRequest, () -> totalCount);
    }

}
