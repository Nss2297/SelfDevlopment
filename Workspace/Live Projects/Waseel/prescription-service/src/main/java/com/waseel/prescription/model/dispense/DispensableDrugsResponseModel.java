package com.waseel.prescription.model.dispense;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waseel.prescription.model.enums.ServiceStatus;
import com.waseel.prescription.persist.prescriptionservice.ServiceInfo;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DispensableDrugsResponseModel implements Specification<ServiceInfo>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String drugCode;
	private String drugDescription;
	private BigDecimal quantity;
	private Double unitPrice;
	private BigDecimal totalPrice;
	private String requestId;

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public String getDrugDescription() {
		return drugDescription;
	}

	public void setDrugDescription(String drugDescription) {
		this.drugDescription = drugDescription;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	public DispensableDrugsResponseModel(String requestId) {
		this.requestId = requestId;
	}
	
	public DispensableDrugsResponseModel(String drugCode, BigDecimal quantity, Double unitPrice) {
		this.drugCode = drugCode;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.totalPrice = quantity.multiply(BigDecimal.valueOf(unitPrice)).setScale(2, RoundingMode.HALF_UP);
	}

	public DispensableDrugsResponseModel() {
		super();
	}

	@Override
	public Predicate toPredicate(Root<ServiceInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();
		List<Predicate> serviceResponseInfoPredicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("requestId")), requestId));
		predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("isDeleted")), false));
		serviceResponseInfoPredicates.add(
				criteriaBuilder.equal(root.get("serviceResponseInfo").get("status"), ServiceStatus.APPROVED.name()));
		serviceResponseInfoPredicates.add(criteriaBuilder.equal(root.get("serviceResponseInfo").get("status"),
				ServiceStatus.PARTIAL_DISPENSED.name()));
		Predicate combinedPredicateForServiceResponseInfo = criteriaBuilder.or(serviceResponseInfoPredicates.get(0),
				serviceResponseInfoPredicates.get(1));
		Predicate combinedPredicateForServiceInfo = criteriaBuilder.and(predicates.get(0), predicates.get(1));
		return criteriaBuilder.and(combinedPredicateForServiceInfo, combinedPredicateForServiceResponseInfo);
	}
}
