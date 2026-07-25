package com.waseel.dssadminservice.specification;

import java.util.List;
import java.util.Optional;

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

import com.waseel.dssadminservice.model.sfdamanagement.SfdaDrugSearchModel;
import com.waseel.dssadminservice.persist.mdss.DrugService;

@Component
public class SFDADrugListSpecification {

	@PersistenceContext
	private EntityManager entityManager;

	public Optional<Page<DrugService>> getSfdaDrugsPaginated(SfdaDrugSearchModel sfdaDrugSearchModel, Long drugListId) {
		int pageNumber = sfdaDrugSearchModel.getPage();
		int recordSize = sfdaDrugSearchModel.getPageSize();
		sfdaDrugSearchModel.setDrugListId(drugListId);
		PageRequest pageRequest = PageRequest.of(pageNumber, recordSize);
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<DrugService> query = criteriaBuilder.createQuery(DrugService.class);
		Root<DrugService> root = query.from(DrugService.class);
		query.where(sfdaDrugSearchModel.toPredicate(root, query, criteriaBuilder));
		query.orderBy(criteriaBuilder.desc(root.get("lastUpdatedDate")));
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);

		Root<DrugService> countQueryRoot = countQuery.from(DrugService.class);
		Predicate countPredicate = sfdaDrugSearchModel.toPredicate(countQueryRoot, countQuery, criteriaBuilder);
		countQuery.select(criteriaBuilder.count(countQueryRoot)).where(countPredicate);
		countQuery.select(criteriaBuilder.count(countQueryRoot));
		Long totalCount = entityManager.createQuery(countQuery).getSingleResult();

		TypedQuery<DrugService> typedQuery = entityManager.createQuery(query);
		typedQuery.setFirstResult(pageNumber * recordSize);
		typedQuery.setMaxResults(recordSize);
		List<DrugService> result = typedQuery.getResultList();
		return Optional.ofNullable(PageableExecutionUtils.getPage(result, pageRequest, () -> totalCount));
	}
}
