package com.waseel.dssadminservice.model.sfdamanagement;

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

import com.waseel.dssadminservice.persist.mdss.DrugServiceMetaData;

public class SFDAMetaDataSearchModel implements Specification<DrugServiceMetaData> {

	private static final long serialVersionUID = 1L;
	private Integer pageNumber = 0;
	private Integer recordSize = 10;
	private String drugListId;
	private String effectiveDateFrom;
	private String effectiveDateTo;
	private String uploadDateFrom;
	private String uploadDateTo;
	private String fileName;
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

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

	public String getDrugListId() {
		return drugListId;
	}

	public void setDrugListId(String drugListId) {
		this.drugListId = drugListId;
	}

	public String getEffectiveDateFrom() {
		return effectiveDateFrom;
	}

	public void setEffectiveDateFrom(String effectiveDateFrom) {
		this.effectiveDateFrom = effectiveDateFrom;
	}

	public String getEffectiveDateTo() {
		return effectiveDateTo;
	}

	public void setEffectiveDateTo(String effectiveDateTo) {
		this.effectiveDateTo = effectiveDateTo;
	}

	public String getUploadDateFrom() {
		return uploadDateFrom;
	}

	public void setUploadDateFrom(String uploadDateFrom) {
		this.uploadDateFrom = uploadDateFrom;
	}

	public String getUploadDateTo() {
		return uploadDateTo;
	}

	public void setUploadDateTo(String uploadDateTo) {
		this.uploadDateTo = uploadDateTo;
	}

	@Override
	public Predicate toPredicate(Root<DrugServiceMetaData> root, CriteriaQuery<?> query,
			CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();
		if (StringUtils.isNotBlank(drugListId)) {
			predicates
					.add(criteriaBuilder.like(root.get("drugListId").as(String.class), "%" + drugListId.trim() + "%"));
		}
		
		if (StringUtils.isNotBlank(fileName)) {
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("fileName")),
					"%" + fileName.trim().toLowerCase() + "%"));
		}
		
		if (StringUtils.isNotBlank(effectiveDateFrom)) {
			LocalDate date = getLocalDate(effectiveDateFrom.trim());
			if (date != null) {
				predicates
						.add(criteriaBuilder.greaterThanOrEqualTo(root.get("effectiveDate").as(LocalDate.class), date));
			}
		}
		if (StringUtils.isNotBlank(effectiveDateTo)) {
			LocalDate date = getLocalDate(effectiveDateTo.trim());
			if (date != null) {
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("effectiveDate").as(LocalDate.class),
						date.plusDays(1)));
			}
		}
		if (StringUtils.isNotBlank(uploadDateFrom)) {
			LocalDate date = getLocalDate(uploadDateFrom.trim());
			if (date != null) {
				predicates.add(
						criteriaBuilder.greaterThanOrEqualTo(root.get("uploadDateTime").as(LocalDate.class), date));
			}
		}
		if (StringUtils.isNotBlank(uploadDateTo)) {
			LocalDate date = getLocalDate(uploadDateTo.trim());
			if (date != null) {
				predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("uploadDateTime").as(LocalDate.class),
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
