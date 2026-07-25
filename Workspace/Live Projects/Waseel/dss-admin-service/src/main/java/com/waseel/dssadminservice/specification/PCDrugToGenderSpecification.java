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

import com.waseel.dssadminservice.model.customization.pcdrugtogender.DrugToGenderCustomizationRequestModel;
import com.waseel.dssadminservice.model.customization.pcdrugtogender.DrugToGenderResponseModel;
import com.waseel.dssadminservice.persist.mdss.PCGender;

@Repository
public class PCDrugToGenderSpecification {

	@PersistenceContext
	private EntityManager entityManager;

	public Page<DrugToGenderResponseModel> getPCDrugToGender(
			DrugToGenderCustomizationRequestModel drugToGenderCustomizationRequestModel) {
		int pageNumber = drugToGenderCustomizationRequestModel.getPageNumber();
		int recordSize = drugToGenderCustomizationRequestModel.getRecordSize();
		Pageable pageable = PageRequest.of(pageNumber, recordSize);
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DrugToGenderResponseModel> query = criteriaBuilder.createQuery(DrugToGenderResponseModel.class);
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<PCGender> root = query.from(PCGender.class);
		Root<PCGender> countQueryRoot = countQuery.from(PCGender.class);
		query.where(drugToGenderCustomizationRequestModel.toPredicate(root, query, criteriaBuilder));
		query.orderBy(criteriaBuilder.desc(root.get("lastUpdatedDateTime")));
		countQuery.where(query.getRestriction());
		query.multiselect(root.get("seqId"), root.get("id").get("serviceCode"), root.get("gender"),
				root.get("id").get("payerId"), root.get("serviceStatus"), root.get("id").get("moduleName"),
				root.get("additionalRejectionReason"), root.get("lastUpdatedDateTime"));
		countQuery.select(criteriaBuilder.count(countQueryRoot));
		TypedQuery<DrugToGenderResponseModel> typedQuery = entityManager.createQuery(query);
		typedQuery.setFirstResult(pageNumber * recordSize);
		typedQuery.setMaxResults(recordSize);
		Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
		List<DrugToGenderResponseModel> result = typedQuery.getResultList();
		return PageableExecutionUtils.getPage(result, pageable, () -> totalCount);
	}
}
