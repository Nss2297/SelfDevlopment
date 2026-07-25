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
public class EligibilityValidationModel implements Specification<ServiceRejection> {

	private String eligibilityReferenceNumber;
	private String denialCode;
	private String message;
	private String requestId;

	public String getEligibilityReferenceNumber() {
		return eligibilityReferenceNumber;
	}

	public String getDenialCode() {
		return denialCode;
	}

	public String getMessage() {
		return message;
	}

	public void setEligibilityReferenceNumber(String eligibilityReferenceNumber) {
		this.eligibilityReferenceNumber = eligibilityReferenceNumber;
	}

	public void setDenialCode(String denialCode) {
		this.denialCode = denialCode;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public EligibilityValidationModel() {
		super();
	}

	public EligibilityValidationModel(String eligibilityReferenceNumber, String denialCode, String message) {
		super();
		this.eligibilityReferenceNumber = eligibilityReferenceNumber;
		this.denialCode = denialCode;
		this.message = message;
	}

	public EligibilityValidationModel(String requestId) {
		super();
		this.requestId = requestId;
	}

	@Override
	public Predicate toPredicate(Root<ServiceRejection> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("requestId")), requestId));
		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}
}
