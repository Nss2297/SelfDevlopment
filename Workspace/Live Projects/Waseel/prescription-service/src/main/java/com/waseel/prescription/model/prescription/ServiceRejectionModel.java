package com.waseel.prescription.model.prescription;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waseel.prescription.persist.prescriptionservice.ServiceRejection;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceRejectionModel implements Specification<ServiceRejection> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String drugCode;
	private String denialCode;
	private String requestId;
	private String drugName;
	private String rejectionReason;
	private boolean isDeleted;
	private String payerId;
	private Boolean isCustomizable;
	private String scientificCode;
	private String scientificName;

	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public String getDenialCode() {
		return denialCode;
	}

	public void setDenialCode(String denialCode) {
		this.denialCode = denialCode;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

	public ServiceRejectionModel(String requestId) {
		this.requestId = requestId;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public Boolean getIsCustomizable() {
		return isCustomizable;
	}

	public void setIsCustomizable(Boolean isCustomizable) {
		this.isCustomizable = isCustomizable;
	}

	public String getScientificCode() {
		return scientificCode;
	}

	public String getScientificName() {
		return scientificName;
	}

	public void setScientificCode(String scientificCode) {
		this.scientificCode = scientificCode;
	}

	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}

	public ServiceRejectionModel() {
	}

	public ServiceRejectionModel(String drugCode, String denialCode, String rejectionReason) {
		this.drugCode = drugCode;
		this.denialCode = denialCode;
		this.rejectionReason = rejectionReason;
	}

	@Override
	public Predicate toPredicate(Root<ServiceRejection> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("requestId")), requestId));
		predicates.add(criteriaBuilder.equal(root.get("isModifiedByPayer"), false));
		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}

	public ServiceRejectionModel(String requestId, String payerId) {
		super();
		this.requestId = requestId;
		this.payerId = payerId;
	}

	public ServiceRejectionModel(String drugCode, String denialCode, String drugName, String rejectionReason,
			Boolean isCustomizable) {
		this.drugCode = drugCode;
		this.denialCode = denialCode;
		this.drugName = drugName;
		this.rejectionReason = rejectionReason;
		this.isCustomizable = isCustomizable;
	}

	public <T> Predicate toPredicate(Root<ServiceRejection> root, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("requestId")), requestId));
		predicates.add(criteriaBuilder.equal(root.get("isModifiedByPayer"), false));
		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}
}
