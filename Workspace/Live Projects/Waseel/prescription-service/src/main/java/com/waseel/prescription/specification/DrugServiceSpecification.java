package com.waseel.prescription.specification;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import com.waseel.prescription.model.common.DrugServiceModel;
import com.waseel.prescription.model.enums.DrugServiceLiterals;
import com.waseel.prescription.persist.businessrules.DrugFormularyDetails;
import com.waseel.prescription.persist.mdss.DrugService;

@Component
public class DrugServiceSpecification {

	@PersistenceContext(unitName = "mdss")
	private EntityManager entityManager;

	public Page<DrugServiceModel> findByServiceCodeAndDescriptionWithPagination(int pageNumber, int recordSize,
			DrugServiceModel drugServiceModel) {
		PageRequest pageRequest = PageRequest.of(pageNumber, recordSize);
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DrugServiceModel> query = criteriaBuilder.createQuery(DrugServiceModel.class);
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<DrugService> root = query.from(DrugService.class);
		Root<DrugService> countQueryRoot = countQuery.from(DrugService.class);
		Join<DrugService, DrugFormularyDetails> drugFormularyDetailsJoin = root.join("drugFormularyDetailsList",
				JoinType.LEFT);
		drugFormularyDetailsJoin.on(criteriaBuilder.and(
				criteriaBuilder.equal(drugFormularyDetailsJoin.get("formularyId"),
						drugServiceModel.getDrugFormularyId()),
				criteriaBuilder.equal(drugFormularyDetailsJoin.get("isDeleted"), false)));
		query.where(drugServiceModel.toPredicate(root, query, criteriaBuilder));
		countQuery.where(query.getRestriction());
		if (drugServiceModel.getSearchBy().equalsIgnoreCase("tradeName")) {
			query.multiselect(root.get("price"), root.get("otherCodesValue"), root.get("display"),
					root.get("ingredients"), root.get("scientificCode"), root.get("dosageForm"),
					root.get("strengthUnit"), root.get("waseelDrugId"), root.get("lastUpdatedDate"),
					drugFormularyDetailsJoin.get("formularyId"), drugFormularyDetailsJoin.get("isDeleted"),
					root.get("strength"), root.get("roaSuggested"), root.get(DrugServiceLiterals.DRUG_LIST_ID.value()));
		} else {
			query.multiselect(criteriaBuilder.max(root.get("price")), criteriaBuilder.max(root.get("otherCodesValue")),
					criteriaBuilder.max(root.get("display")), root.get("ingredients"),
					criteriaBuilder.max(root.get("scientificCode")), root.get("dosageForm"), root.get("strengthUnit"),
					criteriaBuilder.max(root.get("waseelDrugId")), criteriaBuilder.max(root.get("lastUpdatedDate")),
					criteriaBuilder.max(drugFormularyDetailsJoin.get("formularyId")),
					criteriaBuilder.max(drugFormularyDetailsJoin.get("isDeleted")), root.get("strength"),
					root.get("roaSuggested"), root.get(DrugServiceLiterals.DRUG_LIST_ID.value()));
			query.groupBy(root.get("ingredients"), root.get("strength"), root.get("strengthUnit"),
					root.get("dosageForm"), root.get("roaSuggested"),
					root.get(DrugServiceLiterals.DRUG_LIST_ID.value()));
		}
		countQuery.select(criteriaBuilder.count(countQueryRoot));
		TypedQuery<DrugServiceModel> typedQuery = entityManager.createQuery(query);
		typedQuery.setFirstResult(pageNumber * recordSize);
		typedQuery.setMaxResults(recordSize);
		Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
		List<DrugServiceModel> result = typedQuery.getResultList();
		return PageableExecutionUtils.getPage(result, pageRequest, () -> totalCount);
	}
}
