package com.waseel.dssadminservice.model.sfdamanagement;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.waseel.dssadminservice.persist.mdss.DrugService;

public class SfdaDrugSearchModel implements Specification<DrugService> {

	private static final long serialVersionUID = 1L;
	private Integer page = 0;
	private Integer pageSize = 10;
	private String searchValue;
	private Long drugListId;

	public SfdaDrugSearchModel(Long drugListId) {
		super();
		this.drugListId = drugListId;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public Long getDrugListId() {
		return drugListId;
	}

	public void setDrugListId(Long drugListId) {
		this.drugListId = drugListId;
	}

	@Override
	public Predicate toPredicate(Root<DrugService> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();
		if (searchValue != null && !searchValue.isBlank()) {
			String searchValued = searchValue.trim().toLowerCase();
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("otherCodesValue")), "%" + searchValued + "%"));
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("code")), "%" + searchValued + "%"));
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("display")), "%" + searchValued + "%"));
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("ingredients")), "%" + searchValued+ "%"));
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("scientificCode")), "%" + searchValued + "%"));
		}
		Predicate predicate = criteriaBuilder.equal(root.get("drugListId"), drugListId);
		Predicate orConditioncombinedPredicate;
		if (predicates.isEmpty()) {
			orConditioncombinedPredicate = criteriaBuilder.conjunction();
		} else {
			orConditioncombinedPredicate = criteriaBuilder.or(predicates.get(0), predicates.get(1), predicates.get(2),
					predicates.get(3),predicates.get(4));
		}
		return criteriaBuilder.and(orConditioncombinedPredicate, predicate);
	}
}
