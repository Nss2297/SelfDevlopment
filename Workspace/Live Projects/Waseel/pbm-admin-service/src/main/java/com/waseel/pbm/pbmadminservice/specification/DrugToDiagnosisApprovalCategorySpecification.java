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
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import com.waseel.pbm.pbmadminservice.model.DrugToDiagnosisApprovalCategoryModel;
import com.waseel.pbm.pbmadminservice.persist.mdss.DrugToDiagnosisApprovalCategory;

@Component
public class DrugToDiagnosisApprovalCategorySpecification {

	@PersistenceContext
	private EntityManager entityManager;

	public Page<DrugToDiagnosisApprovalCategoryModel> findByApprovalCategoryWithPagination(int pageNumber,
			int recordSize, String name, String category) {
		DrugToDiagnosisApprovalCategoryModel model = new DrugToDiagnosisApprovalCategoryModel(name, category);
		PageRequest pageRequest = PageRequest.of(pageNumber, recordSize);
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DrugToDiagnosisApprovalCategoryModel> query = criteriaBuilder
				.createQuery(DrugToDiagnosisApprovalCategoryModel.class);
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<DrugToDiagnosisApprovalCategory> root = query.from(DrugToDiagnosisApprovalCategory.class);
		Root<DrugToDiagnosisApprovalCategory> countQueryRoot = countQuery.from(DrugToDiagnosisApprovalCategory.class);
		query.where(model.toPredicate(root, query, criteriaBuilder));
		countQuery.where(query.getRestriction());
		query.multiselect(root.get("id"), root.get("name"));
		countQuery.select(criteriaBuilder.count(countQueryRoot));
		TypedQuery<DrugToDiagnosisApprovalCategoryModel> typedQuery = entityManager.createQuery(query);
		typedQuery.setFirstResult(pageNumber * recordSize);
		typedQuery.setMaxResults(recordSize);
		Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
		List<DrugToDiagnosisApprovalCategoryModel> result = typedQuery.getResultList();
		return PageableExecutionUtils.getPage(result, pageRequest, () -> totalCount);
	}
}
