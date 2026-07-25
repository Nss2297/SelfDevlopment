package com.waseel.pbm.pbmadminservice.specification;

import com.waseel.pbm.pbmadminservice.model.drugformulary.PolicyDetailsModel;
import com.waseel.pbm.pbmadminservice.persist.businessrules.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Component
public class PolicyDetailsSpecification {

	@PersistenceContext(unitName = "businessrules")
	private EntityManager entityManager;

	public Page<PolicyDetailsModel> findByRequestIdWithPagination(PolicyDetailsModel policyDetailsModel) {
		int pageNumber = policyDetailsModel.getPageNumber();
		int recordSize = policyDetailsModel.getRecordSize();
		Pageable pageable = PageRequest.of(pageNumber, recordSize);
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<PolicyDetailsModel> query = criteriaBuilder.createQuery(PolicyDetailsModel.class);
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<DrugFormularyPolicyAssociation> root = query.from(DrugFormularyPolicyAssociation.class);
		root.join("drugFormularyMetadata", JoinType.INNER);
		Join<DrugFormularyPolicyAssociation, PolicyInformation> policyInformationJoin = root.join("policyInformation",
				JoinType.INNER);
		Join<DrugFormularyPolicyAssociation, PolicyClasses> policyClassesJoin = root.join("policyClasses",
				JoinType.LEFT);
		Join<DrugFormularyPolicyAssociation, MemberPolicyAssociation> memberPolicyAssociationJoin = root
				.join("memberPolicyAssociation", JoinType.LEFT);
		Join<MemberPolicyAssociation, MemberProfile> memberProfileJoin = memberPolicyAssociationJoin
				.join("memberProfile", JoinType.LEFT);
		Root<DrugFormularyPolicyAssociation> countQueryRoot = countQuery.from(DrugFormularyPolicyAssociation.class);
		Predicate predicate = policyDetailsModel.toPredicate(root, query, criteriaBuilder);
		query.where(predicate);
		countQuery.where(query.getRestriction());
		Predicate countPredicate = policyDetailsModel.toPredicate(countQueryRoot, countQuery, criteriaBuilder);
		countQuery.where(countPredicate);
		query.multiselect(root.get("drugFormularyAssociationId"), policyInformationJoin.get("policyHolderName"),
				policyInformationJoin.get("policyNumber"), policyClassesJoin.get("classCode"),
				memberProfileJoin.get("idNumber"));
		countQuery.select(criteriaBuilder.count(countQueryRoot));
		TypedQuery<PolicyDetailsModel> typedQuery = entityManager.createQuery(query);
		typedQuery.setFirstResult(pageNumber * recordSize);
		typedQuery.setMaxResults(recordSize);
		Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
		List<PolicyDetailsModel> result = typedQuery.getResultList();
		return PageableExecutionUtils.getPage(result, pageable, () -> totalCount);
	}
}
