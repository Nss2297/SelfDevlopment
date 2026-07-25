package com.waseel.prescription.model.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waseel.prescription.persist.mdss.DrugService;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DrugServiceModel implements Specification<DrugService> {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String unitPrice;
	private String sfdaCode;
	private String sfdaDescription;
	private String scientificName;
	private String scientificCode;
	private Long drugFormularyId;
	private Long activeDrugListId;
	private boolean isDrugFormulary;
	private String dosageForm;
	private String strengthUnit;
	private Long waseelDrugId;
	private Date lastUpdatedDate;
	private String searchBy;
	private String strength;
	private String roaSuggested;

	public Long getActiveDrugListId() {
		return activeDrugListId;
	}

	public void setActiveDrugListId(Long activeDrugListId) {
		this.activeDrugListId = activeDrugListId;
	}

	public DrugServiceModel(String unitPrice, String sfdaCode, String sfdaDescription, String scientificName,
			String scientificCode, String dosageForm, String strengthUnit, Long waseelDrugId, Date lastUpdatedDate,
			Long drugFormularyId, Boolean isDeletedDrugForFormulary, String strength, String roaSuggested) {
		this.unitPrice = unitPrice;
		this.sfdaCode = sfdaCode;
		this.sfdaDescription = sfdaDescription;
		this.scientificName = addWhiteSpaceAfterComma(scientificName);
		this.scientificCode = scientificCode;
		this.dosageForm = dosageForm;
		this.strengthUnit = strengthUnit;
		this.waseelDrugId = waseelDrugId;
		this.lastUpdatedDate = lastUpdatedDate;
		isDrugFormulary = drugFormularyId != null && !isDeletedDrugForFormulary;
		this.strength = strength;
		this.roaSuggested = roaSuggested;
	}

	public DrugServiceModel(String unitPrice, String sfdaCode, String sfdaDescription, String scientificName) {
		this.unitPrice = unitPrice;
		this.sfdaCode = sfdaCode;
		this.sfdaDescription = sfdaDescription;
		this.scientificName = addWhiteSpaceAfterComma(scientificName);
	}

	public boolean isDrugFormulary() {
		return isDrugFormulary;
	}

	public void setDrugFormulary(boolean drugFormulary) {
		isDrugFormulary = drugFormulary;
	}

	public Long getDrugFormularyId() {
		return drugFormularyId;
	}

	public void setDrugFormularyId(Long drugFormularyId) {
		this.drugFormularyId = drugFormularyId;
	}

	public String getScientificCode() {
		return scientificCode;
	}

	public void setScientificCode(String scientificCode) {
		this.scientificCode = scientificCode;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getSfdaCode() {
		return sfdaCode;
	}

	public void setSfdaCode(String sfdaCode) {
		this.sfdaCode = sfdaCode;
	}

	public String getSfdaDescription() {
		return sfdaDescription;
	}

	public void setSfdaDescription(String sfdaDescription) {
		this.sfdaDescription = sfdaDescription;
	}

	public String getScientificName() {
		return scientificName;
	}

	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}

	public String getDosageForm() {
		return dosageForm;
	}

	public void setDosageForm(String dosageForm) {
		this.dosageForm = dosageForm;
	}

	public String getStrengthUnit() {
		return strengthUnit;
	}

	public void setStrengthUnit(String strengthUnit) {
		this.strengthUnit = strengthUnit;
	}

	public Long getWaseelDrugId() {
		return waseelDrugId;
	}

	public void setWaseelDrugId(Long waseelDrugId) {
		this.waseelDrugId = waseelDrugId;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getSearchBy() {
		return searchBy;
	}

	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}

	public String getStrength() {
		return strength;
	}

	public void setStrength(String strength) {
		this.strength = strength;
	}

	public String getRoaSuggested() {
		return roaSuggested;
	}

	public void setRoaSuggested(String roaSuggested) {
		this.roaSuggested = roaSuggested;
	}

	public DrugServiceModel() {
	}

	public DrugServiceModel(String value, Long drugFormularyId, Long activeDrugListId, String searchBy) {
		super();
		this.sfdaCode = value;
		this.drugFormularyId = drugFormularyId;
		this.activeDrugListId = activeDrugListId;
		this.searchBy = searchBy;
	}

	private String addWhiteSpaceAfterComma(String inputString) {
		if (!StringUtils.isBlank(inputString)) {
			return inputString.replaceAll(",(?!\\s)", ", ");
		}
		return inputString;
	}

	@Override
	public Predicate toPredicate(Root<DrugService> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		String code = sfdaCode.toLowerCase().trim();
		Predicate activeDrugListPredicate = criteriaBuilder.equal(root.get("drugServiceMetaData").get("drugListId"),
				this.activeDrugListId);
		if (searchBy.equalsIgnoreCase("tradeName")) {
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("otherCodesValue")), "%" + code + "%"));
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("display")), "%" + code + "%"));
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("ingredients")), "%" + code + "%"));
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("scientificCode")), "%" + code + "%"));
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("strengthUnit")), "%" + code + "%"));
			predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("dosageForm")), "%" + code + "%"));
			Predicate p = criteriaBuilder.or(predicates.get(0), predicates.get(1), predicates.get(2), predicates.get(3),
					predicates.get(4), predicates.get(5));
			return criteriaBuilder.and(p, activeDrugListPredicate);
		}
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("strength")), "%" + code + "%"));
		predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("ingredients")), "%" + code + "%"));
		predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("strengthUnit")), "%" + code + "%"));
		predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("roaSuggested")), "%" + code + "%"));
		predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("dosageForm")), "%" + code + "%"));
		predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("scientificCode")), "%" + code + "%"));
		Predicate p = criteriaBuilder.or(predicates.get(0), predicates.get(1), predicates.get(2), predicates.get(3),
				predicates.get(4), predicates.get(5));
		return criteriaBuilder.and(p, activeDrugListPredicate);
	}

	public DrugServiceModel(String unitPrice, String sfdaCode, String sfdaDescription, String scientificName,
			String scientificCode, String dosageForm, String strengthUnit, Long waseelDrugId, Date lastUpdatedDate,
			Long drugFormularyId, Boolean isDeletedDrugForFormulary, String strength, String roaSuggested,
			Long activeDrugListId) {
		this.unitPrice = unitPrice;
		this.sfdaCode = sfdaCode;
		this.sfdaDescription = sfdaDescription;
		this.scientificName = addWhiteSpaceAfterComma(scientificName);
		this.scientificCode = scientificCode;
		this.dosageForm = dosageForm;
		this.strengthUnit = strengthUnit;
		this.waseelDrugId = waseelDrugId;
		this.lastUpdatedDate = lastUpdatedDate;
		isDrugFormulary = drugFormularyId != null && !isDeletedDrugForFormulary;
		this.strength = strength;
		this.roaSuggested = roaSuggested;
		this.activeDrugListId = activeDrugListId;
	}
}
