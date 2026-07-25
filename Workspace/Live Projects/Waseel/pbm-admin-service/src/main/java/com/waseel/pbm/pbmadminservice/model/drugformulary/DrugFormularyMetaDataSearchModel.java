package com.waseel.pbm.pbmadminservice.model.drugformulary;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;

import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugFormularyMetadata;
import com.waseel.pbm.pbmadminservice.util.UserInfoUtil;
import com.waseel.pbm.pbmadminservice.validator.customannotation.IsNumber;

public class DrugFormularyMetaDataSearchModel implements Specification<DrugFormularyMetadata> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer pageNumber = 0;
	private Integer recordSize = 10;
	@IsNumber(message = "formularyId {onlyAllowDigits}")
	private String formularyId;
	private String formularyName;
	private String createdDateFrom;
	private String createdDateTo;
	private String updatedDateFrom;
	private String updatedDateTo;

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getRecordSize() {
		return recordSize;
	}

	public void setRecordSize(Integer recordSize) {
		this.recordSize = recordSize;
	}

	public String getFormularyId() {
		return formularyId;
	}

	public void setFormularyId(String formularyId) {
		this.formularyId = formularyId;
	}

	public String getFormularyName() {
		return formularyName;
	}

	public void setFormularyName(String formularyName) {
		this.formularyName = formularyName;
	}

	public String getCreatedDateFrom() {
		return createdDateFrom;
	}

	public void setCreatedDateFrom(String createdDateFrom) {
		this.createdDateFrom = createdDateFrom;
	}

	public String getCreatedDateTo() {
		return createdDateTo;
	}

	public void setCreatedDateTo(String createdDateTo) {
		this.createdDateTo = createdDateTo;
	}

	public String getUpdatedDateFrom() {
		return updatedDateFrom;
	}

	public void setUpdatedDateFrom(String updatedDateFrom) {
		this.updatedDateFrom = updatedDateFrom;
	}

	public String getUpdatedDateTo() {
		return updatedDateTo;
	}

	public void setUpdatedDateTo(String updatedDateTo) {
		this.updatedDateTo = updatedDateTo;
	}

	@Override
	public Predicate toPredicate(Root<DrugFormularyMetadata> root, CriteriaQuery<?> query,
			CriteriaBuilder criteriaBuilder) {
		String payerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get("payerId"), payerId));
		predicates.add(criteriaBuilder.equal(root.get("isDeleted"), false));
		if (!StringUtils.isBlank(formularyName)) {
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("formularyName")),
					"%" + formularyName.toLowerCase().trim() + "%"));
		}
		if (!StringUtils.isBlank(formularyId)) {
			predicates.add(criteriaBuilder.equal(root.get("formularyId"), formularyId.trim()));
		}

		if (!StringUtils.isBlank(createdDateFrom)) {
			LocalDate date = getLocalDate(createdDateFrom);
			if (date != null) {
				predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdDate").as(LocalDate.class), date));
			}
		}

		if (!StringUtils.isBlank(createdDateTo)) {
			LocalDate date = getLocalDate(createdDateTo);
			if (date != null) {
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdDate").as(LocalDate.class),
						date.plusDays(1)));
			}
		}

		if (!StringUtils.isBlank(updatedDateFrom)) {
			LocalDate date = getLocalDate(updatedDateFrom);
			if (date != null) {
				predicates.add(
						criteriaBuilder.greaterThanOrEqualTo(root.get("lastUpdateDate").as(LocalDate.class), date));
			}
		}

		if (!StringUtils.isBlank(updatedDateTo)) {
			LocalDate date = getLocalDate(updatedDateTo);
			if (date != null) {
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("lastUpdateDate").as(LocalDate.class),
						date.plusDays(1)));
			}
		}
		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}

	private LocalDate getLocalDate(String date) {
		try {
			return LocalDate.parse(date.trim(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
		} catch (DateTimeParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
