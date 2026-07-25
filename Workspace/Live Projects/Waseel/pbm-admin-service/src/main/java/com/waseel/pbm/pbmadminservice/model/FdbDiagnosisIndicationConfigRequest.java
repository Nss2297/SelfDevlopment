package com.waseel.pbm.pbmadminservice.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.waseel.pbm.pbmadminservice.persist.mdss.FdbDiagnosisIndicationConfig;

public class FdbDiagnosisIndicationConfigRequest implements Specification<FdbDiagnosisIndicationConfig> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String icdCode;
	private String validateSubChapters;
	private String isEnabled = "0";
	private Long id;

	public FdbDiagnosisIndicationConfigRequest() {
	}

	public FdbDiagnosisIndicationConfigRequest(String icdCode) {
		this.icdCode = icdCode;
	}

	public String getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(String isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getIcdCode() {
		return icdCode;
	}

	public String getValidateSubChapters() {
		return validateSubChapters;
	}

	public void setIcdCode(String icdCode) {
		this.icdCode = icdCode;
	}

	public void setValidateSubChapters(String validateSubChapters) {
		this.validateSubChapters = validateSubChapters;
	}

	@Override
	public Predicate toPredicate(Root<FdbDiagnosisIndicationConfig> root, CriteriaQuery<?> query,
			CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();
		Character isDelete = '0';
		if (!StringUtils.isBlank(icdCode)) {
			predicates.add(criteriaBuilder.like(root.get("icdCode"), "%" + icdCode + "%"));
		}
		predicates.add(criteriaBuilder.equal(root.get("isDeleted"), isDelete));
		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
