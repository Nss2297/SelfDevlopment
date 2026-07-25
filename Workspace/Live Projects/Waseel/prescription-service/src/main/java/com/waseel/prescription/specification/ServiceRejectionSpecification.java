package com.waseel.prescription.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import com.waseel.prescription.model.prescription.EligibilityValidationModel;
import com.waseel.prescription.model.prescription.ServiceRejectionModel;
import com.waseel.prescription.persist.mdss.CustomizationRequestMetadata;
import com.waseel.prescription.persist.prescriptionservice.ServiceRejection;

@Component
public class ServiceRejectionSpecification {

	@PersistenceContext
	private EntityManager entityManager;

	public List<ServiceRejectionModel> findByRequestId(ServiceRejectionModel serviceRejectionModel) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ServiceRejectionModel> query = criteriaBuilder.createQuery(ServiceRejectionModel.class);
		Root<ServiceRejection> root = query.from(ServiceRejection.class);
		// Root<CustomizationRequestMetadata> customizationRequestMetadataRoot = query
		// .from(CustomizationRequestMetadata.class);
		Join<ServiceRejection, CustomizationRequestMetadata> customizationRequestMetadataLeftJoin = root
				.join("requestMetadata", JoinType.LEFT);
		customizationRequestMetadataLeftJoin.on(
				criteriaBuilder.equal(customizationRequestMetadataLeftJoin.get("isDeleted"), false),
				criteriaBuilder.equal(customizationRequestMetadataLeftJoin.get("payerId"),
						serviceRejectionModel.getPayerId()));
		Predicate predicate = serviceRejectionModel.toPredicate(root, criteriaBuilder);
		query.where(predicate);
		query.multiselect(root.get("drugCode"), root.get("denialCode"),
				root.get("rejectionReason"), customizationRequestMetadataLeftJoin.get("isCustomizable"));
		query.groupBy(root.get("drugCode"), root.get("denialCode"),
				root.get("rejectionReason"), customizationRequestMetadataLeftJoin.get("isCustomizable"));
		TypedQuery<ServiceRejectionModel> typedQuery = entityManager.createQuery(query);
		return typedQuery.getResultList();
	}

	public Page<EligibilityValidationModel> findEligiblityValidaions(
			EligibilityValidationModel eligibilityValidationModel, int pageNumber, int recordSize) {
		Pageable pageable = PageRequest.of(pageNumber, recordSize);
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<EligibilityValidationModel> query = criteriaBuilder.createQuery(EligibilityValidationModel.class);
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<ServiceRejection> root = query.from(ServiceRejection.class);
		Root<ServiceRejection> countQueryRoot = countQuery.from(ServiceRejection.class);
		List<Expression<?>> groupByList = new ArrayList<Expression<?>>();
		Expression<?> eligibilityReferenceNumberExp = root.get("eligibilityReferenceNumber");
		Expression<?> denialCodeExp = root.get("denialCode");
		Expression<?> rejectionReasonExp = root.get("rejectionReason");
		groupByList.add(eligibilityReferenceNumberExp);
		groupByList.add(denialCodeExp);
		groupByList.add(rejectionReasonExp);
		Predicate predicate = eligibilityValidationModel.toPredicate(root, query, criteriaBuilder);
		query.where(predicate);
		countQuery.where(query.getRestriction());
		query.multiselect(root.get("eligibilityReferenceNumber"), root.get("denialCode"), root.get("rejectionReason"))
				.groupBy(groupByList);
		countQuery.select(criteriaBuilder.count(countQueryRoot));
		TypedQuery<EligibilityValidationModel> typedQuery = entityManager.createQuery(query);
		typedQuery.setFirstResult(pageNumber * recordSize);
		typedQuery.setMaxResults(recordSize);
		Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
		List<EligibilityValidationModel> result = typedQuery.getResultList();
		return PageableExecutionUtils.getPage(result, pageable, () -> totalCount);
	}
}
