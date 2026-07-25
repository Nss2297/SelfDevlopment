package com.waseel.pbm.pbmadminservice.specification;

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
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.waseel.pbm.pbmadminservice.model.PCDrugToDiagnosisRequest;
import com.waseel.pbm.pbmadminservice.model.customization.DrugToDiagnosisModel;
import com.waseel.pbm.pbmadminservice.persist.mdss.PCDrugToDiagnosis;

@Repository
public class PCDrugToDiagnosisSpecification {

	@PersistenceContext
	private EntityManager entityManager;

	public Page<DrugToDiagnosisModel> getPCDrugToDiagnosisConfigurationDetailsWithPagination(int pageNumber,
			int recordSize, PCDrugToDiagnosisRequest request) {
		Pageable pageable = PageRequest.of(pageNumber, recordSize);
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DrugToDiagnosisModel> query = criteriaBuilder.createQuery(DrugToDiagnosisModel.class);
		Root<PCDrugToDiagnosis> root = query.from(PCDrugToDiagnosis.class);
		query.where(request.toPredicate(root, query, criteriaBuilder));
		/*
		 * Changed the orderBy logic because it is causing issue if we have same
		 * lastUpdatedDateTime for all related records
		 */
		query.orderBy(criteriaBuilder.desc(root.get("lastUpdatedDateTime")), criteriaBuilder.asc(root.get("id")));
		query.multiselect(root.get("id"), root.get("serviceCode"),
				root.get("icdCode"), root.get("payerId"),
				root.get("categoryOfApproval"), root.get("rejectionCategory"),
				root.get("serviceStatus"), root.get("moduleName"),
				root.get("additionalRejectionReason"), root.get("lastUpdatedDateTime"));
		
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<PCDrugToDiagnosis> countQueryRoot = countQuery.from(PCDrugToDiagnosis.class);
		countQuery.select(criteriaBuilder.count(countQueryRoot));
		countQuery.where(query.getRestriction());
		Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
		
		TypedQuery<DrugToDiagnosisModel> typedQuery = entityManager.createQuery(query);
		typedQuery.setFirstResult(pageNumber * recordSize);
		typedQuery.setMaxResults(recordSize);

		List<DrugToDiagnosisModel> result = typedQuery.getResultList();
        return PageableExecutionUtils.getPage(result, pageable, () -> totalCount);
	}
	
}
