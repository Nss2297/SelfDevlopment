package com.waseel.dssadminservice.specification;

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
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.waseel.dssadminservice.model.customization.pcdrugtoage.DrugToAgeResponseModel;
import com.waseel.dssadminservice.model.customization.pcdrugtoage.DrugToAgeSearchModel;
import com.waseel.dssadminservice.persist.mdss.PCAge;

@Repository
public class PCDrugToAgeSpecification {

	@PersistenceContext
	private EntityManager entityManager;

	public Page<DrugToAgeResponseModel> getPCDrugToAge(DrugToAgeSearchModel searchCriteria) {
		int pageNumber = searchCriteria.getPageNumber();
		int recordSize = searchCriteria.getRecordSize();
		Pageable pageable = PageRequest.of(pageNumber, recordSize);
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DrugToAgeResponseModel> query = criteriaBuilder.createQuery(DrugToAgeResponseModel.class);
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<PCAge> root = query.from(PCAge.class);
		Root<PCAge> countQueryRoot = countQuery.from(PCAge.class);
		query.where(searchCriteria.toPredicate(root, query, criteriaBuilder));
		query.orderBy(criteriaBuilder.desc(root.get("lastUpdatedDateTime")), criteriaBuilder.asc(root.get("seqId")));
		countQuery.where(query.getRestriction());
		query.multiselect(root.get("seqId"), root.get("id").get("serviceCode"), root.get("fromAgeInDays"),
				root.get("toAgeInDays"), root.get("id").get("payerId"), root.get("serviceStatus"),
				root.get("id").get("moduleName"), root.get("additionalRejectionReason"),
				root.get("lastUpdatedDateTime"));
		countQuery.select(criteriaBuilder.count(countQueryRoot));
		TypedQuery<DrugToAgeResponseModel> typedQuery = entityManager.createQuery(query);
		typedQuery.setFirstResult(pageNumber * recordSize);
		typedQuery.setMaxResults(recordSize);
		Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
		List<DrugToAgeResponseModel> result = typedQuery.getResultList();
		return PageableExecutionUtils.getPage(result, pageable, () -> totalCount);
	}
}
