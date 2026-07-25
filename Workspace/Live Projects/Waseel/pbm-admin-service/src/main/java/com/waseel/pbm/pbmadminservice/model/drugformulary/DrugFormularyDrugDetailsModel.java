package com.waseel.pbm.pbmadminservice.model.drugformulary;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.waseel.pbm.pbmadminservice.persist.businessrules.DrugFormularyDetails;
import com.waseel.pbm.pbmadminservice.util.UserInfoUtil;

@JsonInclude(Include.NON_NULL)
public class DrugFormularyDrugDetailsModel implements Specification<DrugFormularyDetails> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String drugCode;
	private String drugName;
	private String genericName;
	private BigDecimal price;
	private boolean isOverride;
	private Long formularyId;
	private Integer pageNumber = 0;
	private Integer recordSize = 10;
	private Long drugFormularyDetailsId;
	private BigDecimal patientShare;
	
	public DrugFormularyDrugDetailsModel() {
	}

	public DrugFormularyDrugDetailsModel(Long formularyId) {
		this.formularyId = formularyId;
	}

	public DrugFormularyDrugDetailsModel(String drugCode, String drugName, String genericName, Long formularyId) {
		this.drugCode = drugCode;
		this.drugName = drugName;
		this.genericName = genericName;
		this.formularyId = formularyId;
	}

	public DrugFormularyDrugDetailsModel(String drugCode, String drugName, String genericName, BigDecimal price,
			boolean isOverride) {
		this.drugCode = drugCode;
		this.drugName = drugName;
		this.genericName = genericName;
		this.price = price;
		this.isOverride = isOverride;
	}
	
	public DrugFormularyDrugDetailsModel(String drugCode, String drugName, String genericName, BigDecimal price,
			boolean isOverride, Long drugFormularyDetailsId, BigDecimal patientShare) {
		this.drugCode = drugCode;
		this.drugName = drugName;
		this.genericName = genericName;
		this.price = price;
		this.isOverride = isOverride;
		this.drugFormularyDetailsId = drugFormularyDetailsId;
		this.patientShare = patientShare;
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

	public Long getFormularyId() {
		return formularyId;
	}

	public void setFormularyId(Long formularyId) {
		this.formularyId = formularyId;
	}

	public String getDrugCode() {
		return drugCode;
	}

	public void setDrugCode(String drugCode) {
		this.drugCode = drugCode;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public String getGenericName() {
		return genericName;
	}

	public void setGenericName(String genericName) {
		this.genericName = genericName;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public boolean isOverride() {
		return isOverride;
	}

	public void setOverride(boolean isOverride) {
		this.isOverride = isOverride;
	}

	public Long getDrugFormularyDetailsId() {
		return drugFormularyDetailsId;
	}

	public void setDrugFormularyDetailsId(Long drugFormularyDetailsId) {
		this.drugFormularyDetailsId = drugFormularyDetailsId;
	}

	public BigDecimal getPatientShare() {
		return patientShare;
	}

	public void setPatientShare(BigDecimal patientShare) {
		this.patientShare = patientShare;
	}


	@Override
	public Predicate toPredicate(Root<DrugFormularyDetails> root, CriteriaQuery<?> query,
			CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();
		String payerId = UserInfoUtil.getAccId(SecurityContextHolder.getContext().getAuthentication());
		if (formularyId != null) {
			predicates.add(criteriaBuilder.equal(root.get("formularyId"), formularyId));
		}

		if (!StringUtils.isBlank(drugCode)) {
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("registrationNumber")),
					"%" + drugCode.toLowerCase().trim() + "%"));
		}
		if (!StringUtils.isBlank(drugName)) {
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("tradeName")),
					"%" + drugName.toLowerCase().trim() + "%"));
		}
		if (!StringUtils.isBlank(genericName)) {
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("scientificName")),
					"%" + genericName.toLowerCase().trim() + "%"));
		}
		predicates.add(criteriaBuilder.equal(root.get("drugFormularyMetadata").get("payerId"), payerId));
		predicates.add(criteriaBuilder.equal(root.get("drugFormularyMetadata").get("isDeleted"), false));
		predicates.add(criteriaBuilder.equal(root.get("isDeleted"), false));
		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}
}
