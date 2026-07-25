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
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import com.waseel.pbm.pbmadminservice.model.drugexclusion.ExclusionTypeSearchModel;
import com.waseel.pbm.pbmadminservice.model.drugexclusion.ExclusionTypeSearchResponseModel;
import com.waseel.pbm.pbmadminservice.persist.businessrules.ExclusionAsscTypeList;

@Component
public class DrugExclusionTypesSpecification {

	@PersistenceContext(unitName = "businessrules")
	private EntityManager entityManager;

	public Page<ExclusionTypeSearchResponseModel> findDrugExclusionTypes(
			ExclusionTypeSearchModel exclusionTypeSearchModel, Long exclusionId) {
		exclusionTypeSearchModel.setExclusionId(exclusionId);
		int pageNumber = exclusionTypeSearchModel.getPageNumber();
		int recordSize = exclusionTypeSearchModel.getRecordSize();
		PageRequest pageRequest = PageRequest.of(pageNumber, recordSize);
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ExclusionTypeSearchResponseModel> query = criteriaBuilder
				.createQuery(ExclusionTypeSearchResponseModel.class);
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<ExclusionAsscTypeList> countQueryRoot = countQuery.from(ExclusionAsscTypeList.class);
		Root<ExclusionAsscTypeList> root = query.from(ExclusionAsscTypeList.class);
		query.where(exclusionTypeSearchModel.toPredicate(root, query, criteriaBuilder));
		countQuery.where(query.getRestriction());
		query.multiselect(root.get("exclusionType"), root.get("exclusionTypeName"), root.get("exclusionAsscId"));
		countQuery.select(criteriaBuilder.count(countQueryRoot));
		TypedQuery<ExclusionTypeSearchResponseModel> typedQuery = entityManager.createQuery(query);
		typedQuery.setFirstResult(pageNumber * recordSize);
		typedQuery.setMaxResults(recordSize);
		Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
		List<ExclusionTypeSearchResponseModel> result = typedQuery.getResultList();
		return PageableExecutionUtils.getPage(result, pageRequest, () -> totalCount);
	}
}
