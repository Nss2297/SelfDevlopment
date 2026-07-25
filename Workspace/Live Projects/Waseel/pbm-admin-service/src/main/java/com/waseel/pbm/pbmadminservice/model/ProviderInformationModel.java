package com.waseel.pbm.pbmadminservice.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.waseel.pbm.pbmadminservice.persist.hira.AccountToAccountAssociation;

@JsonInclude(Include.NON_NULL)
public class ProviderInformationModel implements Specification<AccountToAccountAssociation> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal providerId;
	private String providerName;
	private String code;
	private BigDecimal source;
	private BigDecimal destination;

	public BigDecimal getDestination() {
		return destination;
	}

	public void setDestination(BigDecimal destination) {
		this.destination = destination;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getSource() {
		return source;
	}

	public void setSource(BigDecimal source) {
		this.source = source;
	}

	public BigDecimal getProviderId() {
		return providerId;
	}

	public void setProviderId(BigDecimal providerId) {
		this.providerId = providerId;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public ProviderInformationModel() {
		super();
	}

	public ProviderInformationModel(BigDecimal providerId, String providerName, String code) {
		this.providerId = providerId;
		this.providerName = providerName;
		this.code = code;
	}

	public ProviderInformationModel(String value, BigDecimal payerId) {
		this.code = value;
		this.source = isValidBigDecimal(value) ? new BigDecimal(value) : BigDecimal.ZERO;
		this.destination = payerId;
		this.providerName = value;
	}

	private boolean isValidBigDecimal(String value) {
		try {
			new BigDecimal(value);
			return true;
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Predicate toPredicate(Root<AccountToAccountAssociation> root, CriteriaQuery<?> query,
			CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();
		String strSwitchAccount = "switchAccount";
		Predicate predicate = criteriaBuilder.isTrue(root.get("isEnabled"));

		predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("code")), "%" + code.toLowerCase() + "%"));
		predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("id").get("source")), source));
		predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(strSwitchAccount).get("name")),
				"%" + providerName.toLowerCase() + "%"));
		Predicate orConditioncombinedPredicate = criteriaBuilder.or(predicates.get(0), predicates.get(1),
				predicates.get(2));

		predicates.add(
				criteriaBuilder.equal(criteriaBuilder.lower(root.get(strSwitchAccount).get("category")), "provider"));
		predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get(strSwitchAccount).get("isEnabled")), "1"));
		predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("id").get("destination")), destination));
		Predicate andConditioncombinedPredicate = criteriaBuilder.and(predicates.get(3), predicates.get(4),
				predicates.get(5));
		return criteriaBuilder.and(orConditioncombinedPredicate, andConditioncombinedPredicate, predicate);
	}
}
