package com.waseel.dssadminservice.model.customization.pcdrugtodrug;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.waseel.dssadminservice.persist.mdss.PcDrugToDrug;

public class DrugToDrugSearchModel implements Specification<PcDrugToDrug> {

	private static final long serialVersionUID = 1L;
	private int pageNumber = 0;
	private int recordSize = 10;
	private String serviceCode;
	private String interactedServiceCode;
	private String payerId;
	private String moduleName;
	private String serviceStatus;

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getRecordSize() {
		return recordSize;
	}

	public void setRecordSize(int recordSize) {
		this.recordSize = recordSize;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getInteractedServiceCode() {
		return interactedServiceCode;
	}

	public void setInteractedServiceCode(String interactedServiceCode) {
		this.interactedServiceCode = interactedServiceCode;
	}

	public String getPayerId() {
		return payerId;
	}

	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	@Override
	public Predicate toPredicate(Root<PcDrugToDrug> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();
		if (!StringUtils.isBlank(interactedServiceCode)) {
			predicates.add(criteriaBuilder.like(root.get("id").get("interactedServiceCode"),
					"%" + interactedServiceCode + "%"));
		}
		if (!StringUtils.isBlank(serviceCode)) {
			predicates.add(criteriaBuilder.like(root.get("id").get("serviceCode"), "%" + serviceCode + "%"));
		}
		if (!StringUtils.isBlank(payerId)) {
			predicates.add(criteriaBuilder.equal(root.get("id").get("payerId"), payerId));
		}
		if (!StringUtils.isBlank(moduleName)) {
			predicates.add(criteriaBuilder.equal(root.get("id").get("moduleName"), moduleName));
		}
		if (!StringUtils.isBlank(serviceStatus)) {
			predicates.add(criteriaBuilder.equal(root.get("serviceStatus"), serviceStatus));
		}
		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}
}
