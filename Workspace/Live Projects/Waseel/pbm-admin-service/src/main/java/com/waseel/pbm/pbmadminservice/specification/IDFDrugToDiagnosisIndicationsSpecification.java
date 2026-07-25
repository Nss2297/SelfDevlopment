package com.waseel.pbm.pbmadminservice.specification;

import com.waseel.pbm.pbmadminservice.model.IDFDrugToDiagnosisIndicationsRequest;
import com.waseel.pbm.pbmadminservice.model.IDFDrugToDiagnosisModel;
import com.waseel.pbm.pbmadminservice.persist.mdss.IdfDrugToDiagnosisIndications;
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
public class IDFDrugToDiagnosisIndicationsSpecification {

    @PersistenceContext
    private EntityManager entityManager;

    public Page<IDFDrugToDiagnosisModel> findByIcdCodeAndServiceCodeWithPagination(int pageNumber, int recordSize,
                                                                                   String serviceCode, String icdCode) {
        IDFDrugToDiagnosisIndicationsRequest request = new IDFDrugToDiagnosisIndicationsRequest(icdCode, serviceCode);
        PageRequest pageRequest = PageRequest.of(pageNumber, recordSize);
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<IDFDrugToDiagnosisModel> query =
                criteriaBuilder.createQuery(IDFDrugToDiagnosisModel.class);
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<IdfDrugToDiagnosisIndications> root = query.from(IdfDrugToDiagnosisIndications.class);
        Root<IdfDrugToDiagnosisIndications> countQueryRoot = countQuery.from(IdfDrugToDiagnosisIndications.class);
        query.where(request.toPredicate(root, query, criteriaBuilder));
        countQuery.where(query.getRestriction());
        query.multiselect(root.get("id"), root.get("icdDiagnosisCode"), root.get("serviceCode"),
                root.get("oldServiceCode"));
        countQuery.select(criteriaBuilder.count(countQueryRoot));
        TypedQuery<IDFDrugToDiagnosisModel> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(pageNumber * recordSize);
        typedQuery.setMaxResults(recordSize);
        Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
        List<IDFDrugToDiagnosisModel> result = typedQuery.getResultList();
        return PageableExecutionUtils.getPage(result, pageRequest, () -> totalCount);
    }
}