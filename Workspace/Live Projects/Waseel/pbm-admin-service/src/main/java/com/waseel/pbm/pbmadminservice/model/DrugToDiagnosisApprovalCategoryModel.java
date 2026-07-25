package com.waseel.pbm.pbmadminservice.model;

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
import com.waseel.pbm.pbmadminservice.persist.mdss.DrugToDiagnosisApprovalCategory;

@JsonInclude(Include.NON_NULL)
public class DrugToDiagnosisApprovalCategoryModel implements Specification<DrugToDiagnosisApprovalCategory> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String category;

	public DrugToDiagnosisApprovalCategoryModel(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DrugToDiagnosisApprovalCategoryModel(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public DrugToDiagnosisApprovalCategoryModel(String name, String category) {
		super();
		this.name = name;
		this.category = category;
	}

	@Override
	public Predicate toPredicate(Root<DrugToDiagnosisApprovalCategory> root, CriteriaQuery<?> query,
			CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();
		Character isEnabledCategory = '1';
		if (!StringUtils.isBlank(name) && !category.equalsIgnoreCase("payer")) {
			predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
		}
		if (!StringUtils.isBlank(name) && category.equalsIgnoreCase("payer")) {
			predicates.add(criteriaBuilder.equal(root.get("name"), name));
		}
		predicates.add(criteriaBuilder.equal(root.get("isEnabled"), isEnabledCategory));
		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}

}
