package com.waseel.pbm.pbmadminservice.model.membermanagement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.waseel.pbm.pbmadminservice.persist.prescriptionservice.MemberInfo;

public class MemberHistoryModel implements Serializable, Specification<MemberInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer pageNumber = 0;

	private Integer recordSize = 10;

	private Long idNumber;

	private List<String> payerIds;

	public MemberHistoryModel() {
	}

	public MemberHistoryModel(Long idNumber, Integer pageNumber, Integer recordSize, List<String> payerIds) {
		this.idNumber = idNumber;
		this.pageNumber = pageNumber;
		this.recordSize = recordSize;
		this.payerIds = payerIds;
	}

	public List<String> getPayerIds() {
		return payerIds;
	}

	public void setPayerIds(List<String> payerIds) {
		this.payerIds = payerIds;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getRecordSize() {
		return recordSize;
	}

	public void setRecordSize(Integer recordSize) {
		this.recordSize = recordSize;
	}

	public Long getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(Long idNumber) {
		this.idNumber = idNumber;
	}

	@Override
	public Predicate toPredicate(Root<MemberInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(root.get("prescriptionRequest").get("payerId").in(payerIds));
		if (idNumber != null) {
			predicates.add(criteriaBuilder.equal(root.get("idNumber"), idNumber));
			predicates.add(criteriaBuilder.equal(
					criteriaBuilder.lower(root.get("prescriptionRequest").get("switchAccount").get("category")),
					"provider"));
		}
		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}
}
