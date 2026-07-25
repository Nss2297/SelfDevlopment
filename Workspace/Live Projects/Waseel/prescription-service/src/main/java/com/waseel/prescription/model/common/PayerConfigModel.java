package com.waseel.prescription.model.common;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.waseel.prescription.persist.businessrules.PayerConfiguration;

//@JsonInclude(Include.NON_NULL)
public class PayerConfigModel implements Specification<PayerConfiguration> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String payerId;
	private String payerName;

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public PayerConfigModel() {
	}

	public PayerConfigModel(String payerId, String payerName) {
		this.payerId = payerId;
		this.payerName = payerName;
	}

	@Override
	public Predicate toPredicate(Root<PayerConfiguration> root, CriteriaQuery<?> query,
			CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();
		Predicate predicate = criteriaBuilder.isTrue(root.get("isEnabled"));
		predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("payerId")), "%" + payerId.toLowerCase() + "%"));
		predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("payerName")), "%" + payerName.toLowerCase() + "%"));
		Predicate combinedPredicate = criteriaBuilder.or(predicates.get(0), predicates.get(1));
		return criteriaBuilder.and(combinedPredicate, predicate);
	}
}
