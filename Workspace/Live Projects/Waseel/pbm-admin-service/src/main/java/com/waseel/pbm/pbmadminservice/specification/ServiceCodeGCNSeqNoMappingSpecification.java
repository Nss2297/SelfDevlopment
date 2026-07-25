package com.waseel.pbm.pbmadminservice.specification;

import com.waseel.pbm.pbmadminservice.model.ServiceCodeGCNSeqNoMappingModel;
import com.waseel.pbm.pbmadminservice.model.ServiceCodeGCNSeqNoMappingRequest;
import com.waseel.pbm.pbmadminservice.persist.mdss.ServiceCodeGCNSeqNoMapping;
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
public class ServiceCodeGCNSeqNoMappingSpecification {

    @PersistenceContext
    private EntityManager entityManager;

    public Page<ServiceCodeGCNSeqNoMappingModel> findByServiceCodeGCNSeqNumberWithPagination(
            int pageNumber, int recordSize, Integer gcnSeqNumber, String serviceCode) {
        ServiceCodeGCNSeqNoMappingRequest request = new ServiceCodeGCNSeqNoMappingRequest(serviceCode, gcnSeqNumber);
        PageRequest pageRequest = PageRequest.of(pageNumber, recordSize);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ServiceCodeGCNSeqNoMappingModel> query =
                criteriaBuilder.createQuery(ServiceCodeGCNSeqNoMappingModel.class);
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<ServiceCodeGCNSeqNoMapping> root = query.from(ServiceCodeGCNSeqNoMapping.class);
        Root<ServiceCodeGCNSeqNoMapping> countQueryRoot = countQuery.from(ServiceCodeGCNSeqNoMapping.class);
        query.where(request.toPredicate(root, query, criteriaBuilder));
        countQuery.where(query.getRestriction());
        query.multiselect(root.get("serviceCode"), root.get("gcnSeqNo"),
                root.get("productPackageUnit"), root.get("productPackageSize"), root.get("id"));
        countQuery.select(criteriaBuilder.count(countQueryRoot));
        TypedQuery<ServiceCodeGCNSeqNoMappingModel> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(pageNumber * recordSize);
        typedQuery.setMaxResults(recordSize);
        Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
        List<ServiceCodeGCNSeqNoMappingModel> result = typedQuery.getResultList();
        return PageableExecutionUtils.getPage(result, pageRequest, () -> totalCount);
    }
}
