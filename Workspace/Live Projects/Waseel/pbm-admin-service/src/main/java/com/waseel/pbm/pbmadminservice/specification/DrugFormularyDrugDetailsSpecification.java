package com.waseel.pbm.pbmadminservice.specification;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import com.waseel.pbm.pbmadminservice.model.drugformulary.DrugFormularyDrugDetailsModel;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugFormularyDetails;

@Component
public class DrugFormularyDrugDetailsSpecification {

	@PersistenceContext(unitName = "businessrules")
	private EntityManager entityManager;

	public Page<DrugFormularyDrugDetailsModel> findDrugFormularyDrugDetailsWithPagination(
			DrugFormularyDrugDetailsModel dfddModel) {
		int pageNumber = dfddModel.getPageNumber();
		int recordSize = dfddModel.getRecordSize();
		PageRequest pageRequest = PageRequest.of(pageNumber, recordSize);
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DrugFormularyDrugDetailsModel> query = criteriaBuilder
				.createQuery(DrugFormularyDrugDetailsModel.class);
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<DrugFormularyDetails> root = query.from(DrugFormularyDetails.class);
		root.join("drugFormularyMetadata", JoinType.INNER);
		Root<DrugFormularyDetails> countQueryRoot = countQuery.from(DrugFormularyDetails.class);
		query.where(dfddModel.toPredicate(root, query, criteriaBuilder));
		countQuery.where(query.getRestriction());
		query.multiselect(root.get("registrationNumber"), root.get("tradeName"), root.get("scientificName"),
				root.get("price"), root.get("isOverride"), root.get("drugFormularyDetailsId"),
				root.get("patientShare"));
		countQuery.select(criteriaBuilder.count(countQueryRoot));
		TypedQuery<DrugFormularyDrugDetailsModel> typedQuery = entityManager.createQuery(query);
		typedQuery.setFirstResult(pageNumber * recordSize);
		typedQuery.setMaxResults(recordSize);
		Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
		List<DrugFormularyDrugDetailsModel> result = typedQuery.getResultList();
		return PageableExecutionUtils.getPage(result, pageRequest, () -> totalCount);
	}
}
