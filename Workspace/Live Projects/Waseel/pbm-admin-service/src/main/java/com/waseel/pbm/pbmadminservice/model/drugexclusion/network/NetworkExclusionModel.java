package com.waseel.pbm.pbmadminservice.model.drugexclusion.network;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.waseel.pbm.pbmadminservice.persist.businessrules.ProviderNetwork;

@JsonInclude(Include.NON_NULL)
public class NetworkExclusionModel implements Specification<ProviderNetwork> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long networkId;
	private String networkName;
	private Long payerId;
	private Long networkExclusionAsscId;

	public NetworkExclusionModel() {
	}

	public NetworkExclusionModel(Long payerId) {
		this.payerId = payerId;
	}

	public NetworkExclusionModel(Long networkId, String networkName) {
		this.networkId = networkId;
		this.networkName = networkName;
	}

	public NetworkExclusionModel(Long networkId, String networkName, Long payerId) {
		this.networkId = networkId;
		this.networkName = networkName;
		this.payerId = payerId;
	}
	
	public NetworkExclusionModel(String value, Long payerId) {
		this.networkId = isValidLong(value) ? Long.parseLong(value) : null;
		this.networkName = value;
		this.payerId = payerId;
	}

	private boolean isValidLong(String value) {
		try {
			Long.parseLong(value);
			return true;
		} catch (NumberFormatException e) {
		}
		return false;
	}
	
	public Long getNetworkExclusionAsscId() {
		return networkExclusionAsscId;
	}

	public void setNetworkExclusionAsscId(Long networkExclusionAsscId) {
		this.networkExclusionAsscId = networkExclusionAsscId;
	}

	public Long getPayerId() {
		return payerId;
	}

	public void setPayerId(Long payerId) {
		this.payerId = payerId;
	}

	public Long getNetworkId() {
		return networkId;
	}

	public void setNetworkId(Long networkId) {
		this.networkId = networkId;
	}

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = !StringUtils.isBlank(networkName) ? networkName.trim() : networkName;
	}

	@Override
	public Predicate toPredicate(Root<ProviderNetwork> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();
		Predicate orConditioncombinedPredicate = null;
		predicates.add(criteriaBuilder.equal(root.get("payerId"), payerId));
		predicates.add(criteriaBuilder.equal(root.get("isDeleted"), false));
		if (networkId != null || !StringUtils.isBlank(networkName)) {
			List<Predicate> filterPredicates = new ArrayList<>();
			if (networkId != null) {
				filterPredicates.add(criteriaBuilder.equal(root.get("networkId"), networkId));
			}
			if (!StringUtils.isBlank(networkName)) {
				filterPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("networkName")),
						"%" + networkName.trim().toLowerCase() + "%"));
			}
			orConditioncombinedPredicate = criteriaBuilder.or(filterPredicates.toArray(new Predicate[0]));
		}
		if (orConditioncombinedPredicate != null) {
			Predicate andConditioncombinedPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
			return criteriaBuilder.and(orConditioncombinedPredicate, andConditioncombinedPredicate);
		}
		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}
}
