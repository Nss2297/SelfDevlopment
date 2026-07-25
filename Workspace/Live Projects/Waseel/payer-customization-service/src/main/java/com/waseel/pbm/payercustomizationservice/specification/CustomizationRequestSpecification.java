package com.waseel.pbm.payercustomizationservice.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import com.waseel.pbm.payercustomizationservice.model.CustomizationListingResponse;
import com.waseel.pbm.payercustomizationservice.model.CustomizationListingResponse.CustomizationDetails;
import com.waseel.pbm.payercustomizationservice.model.CustomizationSearchModel;
import com.waseel.pbm.payercustomizationservice.persist.CustomizationRequestDetail;
import com.waseel.pbm.payercustomizationservice.persist.CustomizationRequestMetadata;

@Component
public class CustomizationRequestSpecification {
	@PersistenceContext
	private EntityManager entityManager;

	public Page<CustomizationListingResponse> getCustomizationResponsesPaginated(
			CustomizationSearchModel customizationSearchModel) {

		Pageable pageable = PageRequest.of(customizationSearchModel.getPageNumber(),
				customizationSearchModel.getRecordSize());
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<CustomizationRequestMetadata> query = criteriaBuilder
				.createQuery(CustomizationRequestMetadata.class);
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);

		Root<CustomizationRequestMetadata> root = query.from(CustomizationRequestMetadata.class);

		query.orderBy(criteriaBuilder.desc(root.get("lastUpdatedDate")));
		Root<CustomizationRequestMetadata> countQueryRoot = countQuery.from(CustomizationRequestMetadata.class);
		Predicate predicate = customizationSearchModel.toPredicate(root, query, criteriaBuilder);
		query.where(predicate);

		countQuery.where(query.getRestriction());
		countQuery.select(criteriaBuilder.count(countQueryRoot));

		TypedQuery<CustomizationRequestMetadata> typedQuery = entityManager.createQuery(query);
		typedQuery.setFirstResult(customizationSearchModel.getPageNumber() * customizationSearchModel.getRecordSize());
		typedQuery.setMaxResults(customizationSearchModel.getRecordSize());
		Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
		List<CustomizationRequestMetadata> result = typedQuery.getResultList();
		List<CustomizationListingResponse> listing = result.stream()
				.map(entry -> new CustomizationListingResponse(entry.getLastUpdatedDate(),
						entry.getePrescriptionReferenceNumber(), entry.getDrugCode(), entry.getDrugName(),
						entry.getModuleName(), entry.getStatus().toUpperCase(), entry.getCustomizationRequestsId(),
						getDetailsList(entry.getCustomizationRequestDetailList())))
				.collect(Collectors.toList());
		return PageableExecutionUtils.getPage(listing, pageable, () -> totalCount);
	}

	private List<CustomizationDetails> getDetailsList(List<CustomizationRequestDetail> customizationRequestDetail) {
		List<CustomizationDetails> customizationDetailsList = new ArrayList<>();
		for (CustomizationRequestDetail detail : customizationRequestDetail) {
			if (!detail.getCustomizationKey().equalsIgnoreCase("CUSTOMIZABLE")) {
				CustomizationListingResponse listing = new CustomizationListingResponse();
				customizationDetailsList.add(listing.new CustomizationDetails(detail.getCustomizationLabel(),
						detail.getCustomizationValue()));
			}
		}
		return customizationDetailsList;
	}

}
