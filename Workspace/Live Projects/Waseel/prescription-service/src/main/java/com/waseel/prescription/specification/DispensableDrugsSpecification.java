package com.waseel.prescription.specification;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import com.waseel.prescription.model.dispense.DispensableDrugsResponseModel;
import com.waseel.prescription.persist.mdss.DrugService;
import com.waseel.prescription.persist.prescriptionservice.ServiceInfo;

@Component
public class DispensableDrugsSpecification {

	@PersistenceContext
	private EntityManager entityManager;

	public Page<DispensableDrugsResponseModel> findDispensableDrugsWithPagination(int pageNumber, int recordSize,
			DispensableDrugsResponseModel dispensableDrugsResponseModel) {
		Pageable pageable = PageRequest.of(pageNumber, recordSize);
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DispensableDrugsResponseModel> query = criteriaBuilder
				.createQuery(DispensableDrugsResponseModel.class);
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<ServiceInfo> root = query.from(ServiceInfo.class);
		Root<ServiceInfo> countQueryRoot = countQuery.from(ServiceInfo.class);
		Predicate predicate = dispensableDrugsResponseModel.toPredicate(root, query, criteriaBuilder);
		query.where(predicate);
		countQuery.where(query.getRestriction());
		query.multiselect(root.get("drugCode"), root.get("quantity"), root.get("unitPrice"));
		countQuery.select(criteriaBuilder.count(countQueryRoot));
		TypedQuery<DispensableDrugsResponseModel> typedQuery = entityManager.createQuery(query);
		typedQuery.setFirstResult(pageNumber * recordSize);
		typedQuery.setMaxResults(recordSize);
		Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
		List<DispensableDrugsResponseModel> result = typedQuery.getResultList();
		return PageableExecutionUtils.getPage(result, pageable, () -> totalCount);
	}
}
