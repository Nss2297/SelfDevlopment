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

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import com.waseel.prescription.model.prescription.DrugList;
import com.waseel.prescription.persist.prescriptionservice.ServiceInfo;
import com.waseel.prescription.persist.prescriptionservice.ServiceResponseInfo;

@Component
public class ServiceInfoSpecification {

	@PersistenceContext
	private EntityManager entityManager;

	public Iterable<DrugList> findByRequestIdWithPagination(Integer pageNumber, Integer recordSize, DrugList drugList,
			boolean isPaginated) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DrugList> query = criteriaBuilder.createQuery(DrugList.class);
		Root<ServiceInfo> root = query.from(ServiceInfo.class);
		Join<ServiceInfo, ServiceResponseInfo> serviceResponseInfo = root.join("serviceResponseInfo", JoinType.INNER);

		Predicate predicate = drugList.toPredicate(root, query, criteriaBuilder);
		query.where(predicate);
		query.multiselect(root.get("drugCode"), root.get("unitType"), root.get("quantity"), root.get("unitPrice"),
				root.get("duration"), root.get("frequency"), serviceResponseInfo.get("status"),
				root.get("frequencyOthersDescription"), root.get("useUnitType"), root.get("useUnitValue"),
				root.get("serviceStartDate"), serviceResponseInfo.get("net"), serviceResponseInfo.get("patientShare"),
				root.get("scientificCode"), root.get("drugListId"));
		TypedQuery<DrugList> typedQuery = entityManager.createQuery(query);
		if (isPaginated) {
			CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
			Root<ServiceInfo> countQueryRoot = countQuery.from(ServiceInfo.class);
			countQueryRoot.join("serviceResponseInfo", JoinType.INNER);
			countQuery.select(criteriaBuilder.count(countQueryRoot));
			countQuery.where(query.getRestriction());
			Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
			Pageable pageable = PageRequest.of(pageNumber, recordSize);
			typedQuery.setFirstResult(pageNumber * recordSize);
			typedQuery.setMaxResults(recordSize);
			List<DrugList> result = typedQuery.getResultList();
			return PageableExecutionUtils.getPage(result, pageable, () -> totalCount);
		}
		return typedQuery.getResultList();
	}
}
