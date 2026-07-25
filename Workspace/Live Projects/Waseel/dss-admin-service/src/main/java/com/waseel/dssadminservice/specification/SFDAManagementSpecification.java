package com.waseel.dssadminservice.specification;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import com.waseel.dssadminservice.model.sfdamanagement.SFDAMetaDataResponseModel;
import com.waseel.dssadminservice.model.sfdamanagement.SFDAMetaDataSearchModel;
import com.waseel.dssadminservice.persist.mdss.DrugServiceMetaData;

@Component
public class SFDAManagementSpecification {

	@PersistenceContext
	private EntityManager entityManager;

	public Page<SFDAMetaDataResponseModel> findSFDAListWithPagination(SFDAMetaDataSearchModel sfdaMetaDataModel) {
		int pageNumber = sfdaMetaDataModel.getPageNumber();
		int recordSize = sfdaMetaDataModel.getRecordSize();
		PageRequest pageRequest = PageRequest.of(pageNumber, recordSize);
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<SFDAMetaDataResponseModel> query = criteriaBuilder.createQuery(SFDAMetaDataResponseModel.class);
		Root<DrugServiceMetaData> root = query.from(DrugServiceMetaData.class);
		query.multiselect(root.get("drugListId"),root.get("effectiveDate"),
				root.get("uploadDateTime"),root.get("fileName"));
		query.where(sfdaMetaDataModel.toPredicate(root, query, criteriaBuilder));
		query.orderBy(criteriaBuilder.desc(root.get("uploadDateTime")));

		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<DrugServiceMetaData> countQueryRoot = countQuery.from(DrugServiceMetaData.class);
		Predicate countPredicate = sfdaMetaDataModel.toPredicate(countQueryRoot, countQuery, criteriaBuilder);
		countQuery.select(criteriaBuilder.count(countQueryRoot)).where(countPredicate);
		countQuery.select(criteriaBuilder.count(countQueryRoot));
		Long totalCount = entityManager.createQuery(countQuery).getSingleResult();

		TypedQuery<SFDAMetaDataResponseModel> typedQuery = entityManager.createQuery(query);
		typedQuery.setFirstResult(pageNumber * recordSize);
		typedQuery.setMaxResults(recordSize);
		List<SFDAMetaDataResponseModel> result = typedQuery.getResultList();
		return PageableExecutionUtils.getPage(result, pageRequest, () -> totalCount);
	}
}
