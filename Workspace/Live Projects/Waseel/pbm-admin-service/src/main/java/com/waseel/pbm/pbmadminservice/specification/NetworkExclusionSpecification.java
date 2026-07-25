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

import com.waseel.pbm.pbmadminservice.model.drugexclusion.network.NetworkExclusionModel;
import com.waseel.pbm.pbmadminservice.persist.businessrules.ProviderNetwork;

@Component
public class NetworkExclusionSpecification {

	@PersistenceContext(unitName = "businessrules")
	private EntityManager entityManager;

	public Page<NetworkExclusionModel> findNetworksWithPagination(int pageNumber, int recordSize,
			NetworkExclusionModel networkExclusionModel) {
		PageRequest pageRequest = PageRequest.of(pageNumber, recordSize);
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<NetworkExclusionModel> query = criteriaBuilder.createQuery(NetworkExclusionModel.class);
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<ProviderNetwork> root = query.from(ProviderNetwork.class);
		Root<ProviderNetwork> countQueryRoot = countQuery.from(ProviderNetwork.class);
		query.orderBy(criteriaBuilder.desc(root.get("lastUpdateDate")));
		query.where(networkExclusionModel.toPredicate(root, query, criteriaBuilder));
		countQuery.where(query.getRestriction());
		query.multiselect(root.get("networkId"), root.get("networkName"));
		countQuery.select(criteriaBuilder.count(countQueryRoot));
		TypedQuery<NetworkExclusionModel> typedQuery = entityManager.createQuery(query);
		typedQuery.setFirstResult(pageNumber * recordSize);
		typedQuery.setMaxResults(recordSize);
		Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
		List<NetworkExclusionModel> result = typedQuery.getResultList();
		return PageableExecutionUtils.getPage(result, pageRequest, () -> totalCount);
	}
}
