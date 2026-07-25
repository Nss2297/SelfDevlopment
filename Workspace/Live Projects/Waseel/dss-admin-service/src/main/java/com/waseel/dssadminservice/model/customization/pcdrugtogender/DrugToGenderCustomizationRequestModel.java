package com.waseel.dssadminservice.model.customization.pcdrugtogender;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.waseel.dssadminservice.persist.mdss.PCGender;

public class DrugToGenderCustomizationRequestModel implements Specification<PCGender> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int pageNumber = 0;
	private int recordSize = 10;
	private String serviceCode;
	private String gender;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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
	public Predicate toPredicate(Root<PCGender> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

		List<Predicate> predicates = new ArrayList<>();
		if (!StringUtils.isBlank(gender)) {
			predicates.add(criteriaBuilder.equal(root.get("gender"), gender));
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
