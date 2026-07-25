package com.waseel.pbm.pbmadminservice.specification.membermanagement;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import com.waseel.pbm.pbmadminservice.enums.SpecificationDetails;
import com.waseel.pbm.pbmadminservice.model.membermanagement.MemberHistoryModel;
import com.waseel.pbm.pbmadminservice.model.membermanagement.MemberHistoryResponseModel;
import com.waseel.pbm.pbmadminservice.model.membermanagement.MembersRequestModel;
import com.waseel.pbm.pbmadminservice.model.membermanagement.MembersResponseModel;
import com.waseel.pbm.pbmadminservice.persist.hira.SwitchAccount;
import com.waseel.pbm.pbmadminservice.persist.prescriptionservice.MemberInfo;
import com.waseel.pbm.pbmadminservice.persist.prescriptionservice.PrescriptionRequest;

@Component
public class MemberManagementSpecification {

	@PersistenceContext(unitName = "prescriptionservice")
	private EntityManager entityManager;

	public Page<MembersResponseModel> paginatedMembersListWithFilter(MembersRequestModel searchFilters) {
		int pageNumber = searchFilters.getPageNumber();
		int recordSize = searchFilters.getRecordSize();
		PageRequest pageRequest = PageRequest.of(pageNumber, recordSize);
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<MembersResponseModel> query = criteriaBuilder.createQuery(MembersResponseModel.class);
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<MemberInfo> root = query.from(MemberInfo.class);
		root.join(SpecificationDetails.PRESCRIPTION_REQUEST_PROPERTY.value(), JoinType.INNER);
		query.where(searchFilters.toPredicate(root, query, criteriaBuilder));
		query.multiselect(criteriaBuilder.max(root.get("memberName")),
				root.get(SpecificationDetails.ID_NUMBER_PROPERTY.value()), criteriaBuilder.max(root.get("gender")),
				criteriaBuilder.max(root.get("dob")), criteriaBuilder.max(root.get("nationality")));
		query.groupBy(root.get("idNumber"));
		TypedQuery<MembersResponseModel> typedQuery = entityManager.createQuery(query);
		typedQuery.setFirstResult(pageNumber * recordSize);
		typedQuery.setMaxResults(recordSize);
		Root<MemberInfo> countQueryRoot = countQuery.from(MemberInfo.class);
		countQueryRoot.join(SpecificationDetails.PRESCRIPTION_REQUEST_PROPERTY.value(), JoinType.INNER);
		countQuery.where(query.getRestriction());
		countQuery.select(
				criteriaBuilder.countDistinct(countQueryRoot.get(SpecificationDetails.ID_NUMBER_PROPERTY.value())));
		Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
		List<MembersResponseModel> result = typedQuery.getResultList();
		return PageableExecutionUtils.getPage(result, pageRequest, () -> totalCount);
	}

	public Page<MemberHistoryResponseModel> findMemberHistoryWithPagination(MemberHistoryModel memberHistoryModel) {
		int pageNumber = memberHistoryModel.getPageNumber();
		int recordSize = memberHistoryModel.getRecordSize();
		String strLastUpdateDate = "lastUpdateDate";
		PageRequest pageRequest = PageRequest.of(pageNumber, recordSize);
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<MemberHistoryResponseModel> query = criteriaBuilder.createQuery(MemberHistoryResponseModel.class);
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<MemberInfo> root = query.from(MemberInfo.class);
		Join<MemberInfo, PrescriptionRequest> prescriptionRequest = root.join("prescriptionRequest", JoinType.INNER);
		Join<PrescriptionRequest, SwitchAccount> switchAccount = prescriptionRequest.join("switchAccount",
				JoinType.INNER);
		Predicate predicate = memberHistoryModel.toPredicate(root, query, criteriaBuilder);
		query.where(predicate);
		query.orderBy(
				criteriaBuilder.desc(criteriaBuilder.selectCase()
						.when(criteriaBuilder.isNotNull(prescriptionRequest.get(strLastUpdateDate)),
								prescriptionRequest.get(strLastUpdateDate))
						.otherwise(criteriaBuilder.literal(new Date(0)))));
		query.multiselect(prescriptionRequest.get("ePrescriptionReferenceNumber"),
				prescriptionRequest.get(strLastUpdateDate), prescriptionRequest.get("providerId"),
				prescriptionRequest.get("statusCode"), prescriptionRequest.get("sendDateTime"),
				switchAccount.get("name"));
		Root<MemberInfo> countRoot = countQuery.from(MemberInfo.class);
		Join<MemberInfo, PrescriptionRequest> countPrescriptionRequest = countRoot.join("prescriptionRequest",
				JoinType.INNER);
		countPrescriptionRequest.join("switchAccount", JoinType.INNER);
		Predicate countPredicate = memberHistoryModel.toPredicate(countRoot, countQuery, criteriaBuilder);
		countQuery.select(criteriaBuilder.count(countRoot)).where(countPredicate);
		TypedQuery<MemberHistoryResponseModel> typedQuery = entityManager.createQuery(query);
		typedQuery.setFirstResult(pageNumber * recordSize);
		typedQuery.setMaxResults(recordSize);
		Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
		List<MemberHistoryResponseModel> result = typedQuery.getResultList();
		return PageableExecutionUtils.getPage(result, pageRequest, () -> totalCount);
	}
}
