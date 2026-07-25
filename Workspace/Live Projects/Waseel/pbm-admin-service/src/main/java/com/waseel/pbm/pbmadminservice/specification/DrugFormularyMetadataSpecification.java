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

import com.waseel.pbm.pbmadminservice.model.drugformulary.DrugFormularyMetaDataResponseModel;
import com.waseel.pbm.pbmadminservice.model.drugformulary.DrugFormularyMetaDataSearchModel;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugFormularyMetadata;

@Component
public class DrugFormularyMetadataSpecification {

	@PersistenceContext(unitName = "businessrules")
	private EntityManager entityManager;

	public Page<DrugFormularyMetaDataResponseModel> findFormulariesWithPagination(
			DrugFormularyMetaDataSearchModel dfmdsearchModel) {
		int pageNumber = dfmdsearchModel.getPageNumber();
		int recordSize = dfmdsearchModel.getRecordSize();
		PageRequest pageRequest = PageRequest.of(pageNumber, recordSize);
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DrugFormularyMetaDataResponseModel> query = criteriaBuilder
				.createQuery(DrugFormularyMetaDataResponseModel.class);
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<DrugFormularyMetadata> root = query.from(DrugFormularyMetadata.class);
		Root<DrugFormularyMetadata> countQueryRoot = countQuery.from(DrugFormularyMetadata.class);
		query.orderBy(criteriaBuilder.desc(root.get("lastUpdateDate")));
		query.where(dfmdsearchModel.toPredicate(root, query, criteriaBuilder));
		countQuery.where(query.getRestriction());
		query.multiselect(root.get("formularyId"), root.get("formularyName"), root.get("createdDate"),
				root.get("lastUpdateDate"));
		countQuery.select(criteriaBuilder.count(countQueryRoot));
		TypedQuery<DrugFormularyMetaDataResponseModel> typedQuery = entityManager.createQuery(query);
		typedQuery.setFirstResult(pageNumber * recordSize);
		typedQuery.setMaxResults(recordSize);
		Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
		List<DrugFormularyMetaDataResponseModel> result = typedQuery.getResultList();
		return PageableExecutionUtils.getPage(result, pageRequest, () -> totalCount);
	}

}
