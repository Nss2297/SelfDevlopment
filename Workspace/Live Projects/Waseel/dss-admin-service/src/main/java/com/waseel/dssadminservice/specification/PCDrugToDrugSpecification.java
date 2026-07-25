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

import com.waseel.dssadminservice.model.customization.pcdrugtodrug.DrugToDrugResponseModel;
import com.waseel.dssadminservice.model.customization.pcdrugtodrug.DrugToDrugSearchModel;
import com.waseel.dssadminservice.persist.mdss.PcDrugToDrug;

@Repository
public class PCDrugToDrugSpecification {

	@PersistenceContext
	private EntityManager entityManager;

	public Page<DrugToDrugResponseModel> getPCDrugToDrug(
			DrugToDrugSearchModel drugToDrugSearchModel) {
		int pageNumber = drugToDrugSearchModel.getPageNumber();
		int recordSize = drugToDrugSearchModel.getRecordSize();
		Pageable pageable = PageRequest.of(pageNumber, recordSize);
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DrugToDrugResponseModel> query = criteriaBuilder.createQuery(DrugToDrugResponseModel.class);
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<PcDrugToDrug> root = query.from(PcDrugToDrug.class);
		Root<PcDrugToDrug> countQueryRoot = countQuery.from(PcDrugToDrug.class);
		query.where(drugToDrugSearchModel.toPredicate(root, query, criteriaBuilder));
		query.orderBy(criteriaBuilder.desc(root.get("lastUpdatedDateTime")));
		countQuery.where(query.getRestriction());
		query.multiselect(root.get("seqId"), root.get("id").get("serviceCode"), root.get("id").get("interactedServiceCode"),
				root.get("id").get("payerId"), root.get("serviceStatus"), root.get("id").get("moduleName"),
				root.get("additionalRejectionReason"), root.get("lastUpdatedDateTime"));
		countQuery.select(criteriaBuilder.count(countQueryRoot));
		TypedQuery<DrugToDrugResponseModel> typedQuery = entityManager.createQuery(query);
		typedQuery.setFirstResult(pageNumber * recordSize);
		typedQuery.setMaxResults(recordSize);
		Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
		List<DrugToDrugResponseModel> result = typedQuery.getResultList();
		return PageableExecutionUtils.getPage(result, pageable, () -> totalCount);
	}
}
