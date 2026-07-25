package com.waseel.prescription.specification;

import java.util.ArrayList;
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

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

import com.waseel.prescription.model.prescription.PrescriptionsSearchModel;
import com.waseel.prescription.model.prescription.ProviderPrescriptionDTO;
import com.waseel.prescription.model.prescription.ProviderPrescriptionResponseModel;
import com.waseel.prescription.persist.businessrules.PayerConfiguration;
import com.waseel.prescription.persist.prescriptionservice.MemberInfo;
import com.waseel.prescription.persist.prescriptionservice.PrescriptionRequest;
import com.waseel.prescription.repository.prescriptionservice.PrescriptionRequestRepository;

@Component
public class ProviderPrescriptionSpecification {
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private PrescriptionRequestRepository prescriptionRequestRepository;

	public Page<ProviderPrescriptionResponseModel> getProviderPrescriptionsWithPagination(
			PrescriptionsSearchModel request, String providerId) {
		Pageable pageable = PageRequest.of(request.getPageNumber(), request.getRecordSize());
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<ProviderPrescriptionResponseModel> query = criteriaBuilder
				.createQuery(ProviderPrescriptionResponseModel.class);
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);

		Root<PrescriptionRequest> root = query.from(PrescriptionRequest.class);
		Join<PrescriptionRequest, MemberInfo> memberInfo = root.join("memberInfo", JoinType.INNER);
		Join<PrescriptionRequest, PayerConfiguration> payerConfig = root.join("payerConfiguration", JoinType.INNER);

		query.orderBy(criteriaBuilder.desc(root.get("sendDateTime")));
		Root<PrescriptionRequest> countQueryRoot = countQuery.from(PrescriptionRequest.class);
		Predicate predicate = request.toPredicate(root, query, criteriaBuilder);
		query.where(predicate);

		countQuery.where(query.getRestriction());
		query.multiselect(root.get("ePrescriptionReferenceNumber"), root.get("statusCode"), root.get("sendDateTime"),
				memberInfo.get("memberId"), memberInfo.get("idNumber"), memberInfo.get("policyNumber"),
				memberInfo.get("memberName"), payerConfig.get("payerName"), payerConfig.get("payerId"));
		countQuery.select(criteriaBuilder.count(countQueryRoot));

		TypedQuery<ProviderPrescriptionResponseModel> typedQuery = entityManager.createQuery(query);
		typedQuery.setFirstResult(request.getPageNumber() * request.getRecordSize());
		typedQuery.setMaxResults(request.getRecordSize());
		Long totalCount = entityManager.createQuery(countQuery).getSingleResult();
		List<ProviderPrescriptionResponseModel> result = typedQuery.getResultList();
		return PageableExecutionUtils.getPage(result, pageable, () -> totalCount);
	}

	public Page<ProviderPrescriptionResponseModel> getProviderPrescriptionsPaginated(
			PrescriptionsSearchModel searchModel) {
		Boolean isActivePrescription = searchModel.getActivePrescription();
		Boolean isPharmacyUser = searchModel.getPharmacyUser();
		populateRequestModel(searchModel);
		if (isActivePrescription.equals(Boolean.TRUE)) {
			return fetchForActivePrescription(searchModel);
		} else if (isPharmacyUser.equals(Boolean.TRUE)) {
			return fetchPrescriptionsForPharmacyUser(searchModel);
		} else {
			return fetchAllPrescriptions(searchModel);
		}
	}

	private void populateRequestModel(PrescriptionsSearchModel searchModel) {
		String referenceNo = searchModel.getReferenceNo();
		String memberId = searchModel.getMemberId();
		String idNumber = searchModel.getIdNumber();
		String memberName = searchModel.getMemberName();
		String policyNumber = searchModel.getPolicyNumber();
		String status = searchModel.getStatus();
		String fromDate = searchModel.getFromDate();
		String endDate = searchModel.getEndDate();
		String providerId = searchModel.getProviderId();
		searchModel.setEndDate(StringUtils.isNotBlank(endDate) ? endDate.trim() : "");
		searchModel.setFromDate(StringUtils.isNotBlank(fromDate) ? fromDate.trim() : "");
		searchModel.setIdNumber(StringUtils.isNotBlank(idNumber) ? idNumber.trim() : "");
		searchModel.setMemberId(StringUtils.isNotBlank(memberId) ? memberId.trim() : "");
		searchModel.setMemberName(StringUtils.isNotBlank(memberName) ? memberName.trim() : "");
		searchModel.setPolicyNumber(StringUtils.isNotBlank(policyNumber) ? policyNumber.trim() : "");
		searchModel.setProviderId(StringUtils.isNotBlank(providerId) ? providerId.trim() : "");
		searchModel.setReferenceNo(StringUtils.isNotBlank(referenceNo) ? referenceNo.trim() : "");
		searchModel.setStatus(StringUtils.isNotBlank(status) ? status.trim() : "");
	}

	private Page<ProviderPrescriptionResponseModel> fetchForActivePrescription(PrescriptionsSearchModel searchModel) {
		Integer pageNumber = searchModel.getPageNumber();
		Integer recordSize = searchModel.getRecordSize();
		Integer offset = pageNumber * recordSize;
		Pageable pageable = PageRequest.of(pageNumber, recordSize);
		List<ProviderPrescriptionDTO> prescriptions = prescriptionRequestRepository
				.getProviderPrescriptionPaginatedAPIForActivePrescription(searchModel.getReferenceNo(),
						searchModel.getMemberId(), searchModel.getIdNumber(), searchModel.getMemberName(),
						searchModel.getPolicyNumber(), searchModel.getFromDate(), searchModel.getEndDate(),
						searchModel.getProviderId(), offset, recordSize);
		Long totalCount = prescriptionRequestRepository.getProviderPrescriptionPaginatedAPICountForActivePrescription(
				searchModel.getReferenceNo(), searchModel.getMemberId(), searchModel.getIdNumber(),
				searchModel.getMemberName(), searchModel.getPolicyNumber(), searchModel.getFromDate(),
				searchModel.getEndDate(), searchModel.getProviderId());
		return preparePrescriptionList(prescriptions, totalCount, pageable);
	}

	private Page<ProviderPrescriptionResponseModel> fetchPrescriptionsForPharmacyUser(
			PrescriptionsSearchModel searchModel) {
		Integer pageNumber = searchModel.getPageNumber();
		Integer recordSize = searchModel.getRecordSize();
		Integer offset = pageNumber * recordSize;
		Pageable pageable = PageRequest.of(pageNumber, recordSize);
		List<ProviderPrescriptionDTO> prescriptions = prescriptionRequestRepository
				.getProviderPrescriptionPaginatedAPIForPharmacyUser(searchModel.getReferenceNo(),
						searchModel.getMemberId(), searchModel.getIdNumber(), searchModel.getMemberName(),
						searchModel.getPolicyNumber(), searchModel.getFromDate(), searchModel.getEndDate(),
						searchModel.getProviderId(), offset, recordSize);
		Long totalCount = prescriptionRequestRepository.getProviderPrescriptionPaginatedAPICountForPharmacyUser(
				searchModel.getReferenceNo(), searchModel.getMemberId(), searchModel.getIdNumber(),
				searchModel.getMemberName(), searchModel.getPolicyNumber(), searchModel.getFromDate(),
				searchModel.getEndDate(), searchModel.getProviderId());
		return preparePrescriptionList(prescriptions, totalCount, pageable);
	}

	private Page<ProviderPrescriptionResponseModel> fetchAllPrescriptions(PrescriptionsSearchModel searchModel) {
		Integer pageNumber = searchModel.getPageNumber();
		Integer recordSize = searchModel.getRecordSize();
		Integer offset = pageNumber * recordSize;
		Pageable pageable = PageRequest.of(pageNumber, recordSize);
		List<ProviderPrescriptionDTO> prescriptions = prescriptionRequestRepository.getProviderPrescriptionPaginatedAPI(
				searchModel.getReferenceNo(), searchModel.getMemberId(), searchModel.getIdNumber(),
				searchModel.getMemberName(), searchModel.getPolicyNumber(), searchModel.getStatus(),
				searchModel.getFromDate(), searchModel.getEndDate(), searchModel.getProviderId(), offset, recordSize);
		Long totalCount = prescriptionRequestRepository.getProviderPrescriptionPaginatedAPICount(
				searchModel.getReferenceNo(), searchModel.getMemberId(), searchModel.getIdNumber(),
				searchModel.getMemberName(), searchModel.getPolicyNumber(), searchModel.getStatus(),
				searchModel.getFromDate(), searchModel.getEndDate(), searchModel.getProviderId());
		return preparePrescriptionList(prescriptions, totalCount, pageable);
	}

	private Page<ProviderPrescriptionResponseModel> preparePrescriptionList(List<ProviderPrescriptionDTO> prescriptions,
			Long totalCount, Pageable pageable) {
		List<ProviderPrescriptionResponseModel> result = new ArrayList<>();
		if (null != prescriptions && !prescriptions.isEmpty()) {
			prescriptions.stream().forEach(prescription -> {
				ProviderPrescriptionResponseModel responseModel = new ProviderPrescriptionResponseModel();
				responseModel.setDateAndTime(prescription.getDateAndTime());
				responseModel.setId(prescription.getReferenceNo());
				responseModel.setIdNumber(Long.valueOf(prescription.getIdNumber()));
				responseModel.setInsurance(prescription.getInsurance());
				responseModel.setMemberId(prescription.getMemberId());
				responseModel.setMemberName(prescription.getMemberName());
				responseModel.setPayerId(prescription.getPayerId());
				responseModel.setPolicyNumber(prescription.getPolicyNumber());
				responseModel.setReferenceNo(prescription.getReferenceNo());
				responseModel.setStatus(prescription.getStatus());
				result.add(responseModel);
			});
		}
		return PageableExecutionUtils.getPage(result, pageable, () -> totalCount);
	}
}
