package com.waseel.pbm.pbmadminservice.specification;

import com.waseel.pbm.pbmadminservice.model.FdbDiagnosisIndicationConfigModel;
import com.waseel.pbm.pbmadminservice.model.FdbDiagnosisIndicationConfigRequest;
import com.waseel.pbm.pbmadminservice.persist.mdss.FdbDiagnosisIndicationConfig;
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
public class FDBDiagnosisIndicationConfigSpecification {

    @PersistenceContext
    private EntityManager entityManager;

    public Page<FdbDiagnosisIndicationConfigModel> findByIcdCodeWithPagination(int pageNumber, int recordSize,
                                                                                 String icdCode) {
        FdbDiagnosisIndicationConfigRequest request = new FdbDiagnosisIndicationConfigRequest(icdCode);
        PageRequest pageRequest = PageRequest.of(pageNumber, recordSize);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<FdbDiagnosisIndicationConfigModel> query =
                criteriaBuilder.createQuery(FdbDiagnosisIndicationConfigModel.class);
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<FdbDiagnosisIndicationConfig> root = query.from(FdbDiagnosisIndicationConfig.class);
        Root<FdbDiagnosisIndicationConfig> countQueryRoot = countQuery.from(FdbDiagnosisIndicationConfig.class);
        query.where(request.toPredicate(root, query, criteriaBuilder));
        countQuery.where(query.getRestriction());
        query.multiselect(root.get("icdCode"), root.get("validateSubChapters"),
                root.get("isEnabled"), root.get("id"));
        countQuery.select(criteriaBuilder.count(countQueryRoot));
        TypedQuery<FdbDiagnosisIndicationConfigModel> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(pageNumber * recordSize);
        typedQuery.setMaxResults(recordSize);
        Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
        List<FdbDiagnosisIndicationConfigModel> result = typedQuery.getResultList();
        return PageableExecutionUtils.getPage(result, pageRequest, () -> totalCount);
    }
}
