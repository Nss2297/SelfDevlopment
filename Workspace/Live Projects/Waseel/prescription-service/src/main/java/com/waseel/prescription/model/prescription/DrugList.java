package com.waseel.prescription.model.prescription;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotEmpty;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.waseel.prescription.persist.prescriptionservice.ServiceInfo;
import com.waseel.prescription.util.UserInfoUtil;
import com.waseel.prescription.validator.customannotation.IsNumber;
import com.waseel.prescription.validator.customannotation.IsValidDateFormat;
import com.waseel.prescription.validator.customannotation.NoSpecialCharacter;
import com.waseel.prescription.validator.customannotation.NoWhiteSpaceCharacter;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(Include.NON_NULL)
public class DrugList extends CommonDrugList implements Specification<ServiceInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String orderingClinician;

	@NotEmpty(message = "serviceStartDate {emptyDateValidation}")
	@IsValidDateFormat(message = "serviceStartDate {dateFormatValidation}")
	private String serviceStartDate;

	@IsValidDateFormat(message = "serviceEndDate {dateFormatValidation}")
	private String serviceEndDate;

	@Schema(hidden = true)
	private String useUnitType;

	@Schema(hidden = true)
	private String requestId;

	@Schema(hidden = true)
	private String drugName;

	@Schema(hidden = true)
	private String status;

	@Schema(hidden = true)
	private Boolean isOverridableByProvider = false;

	private String scientificName;
	private String dosageForm;
	private String strengthUnit;
	private String strength;
	private String roaSuggested;

	@NotEmpty(message = "drugListId {emptyDateValidation}")
	@NoWhiteSpaceCharacter(message = "drugListId {noWhiteSpaceCharacterValidation}")
	@NoSpecialCharacter(message = "drugListId {noSpecialCharactersValidation}")
	@IsNumber(message = "drugListId {notAnumberValidation}")
	private String drugListId;
	
	@Schema(hidden = true)
	private BigDecimal patientShareVatAmount = BigDecimal.ZERO;
	@Schema(hidden = true)
	private String patientShareVatCurrency = "SAR";

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

	public String getScientificName() {
		return scientificName;
	}

	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUseUnitType() {
		return useUnitType;
	}

	public void setUseUnitType(String useUnitType) {
		this.useUnitType = useUnitType;
	}

	public String getDrugName() {
		return drugName;
	}

	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getOrderingClinician() {
		return orderingClinician;
	}

	public void setOrderingClinician(String orderingClinician) {
		this.orderingClinician = orderingClinician;
	}

	public String getServiceStartDate() {
		return serviceStartDate;
	}

	public void setServiceStartDate(String serviceStartDate) {
		this.serviceStartDate = serviceStartDate;
	}

	public String getServiceEndDate() {
		return serviceEndDate;
	}

	public void setServiceEndDate(String serviceEndDate) {
		this.serviceEndDate = serviceEndDate;
	}

	public String getDrugListId() {
		return drugListId;
	}

	public void setDrugListId(String drugListId) {
		this.drugListId = drugListId;
	}

	public Boolean getIsOverridableByProvider() {
		return isOverridableByProvider;
	}

	public void setIsOverridableByProvider(Boolean isOverridableByProvider) {
		this.isOverridableByProvider = isOverridableByProvider;
	}

	public BigDecimal getPatientShareVatAmount() {
		return patientShareVatAmount;
	}

	public void setPatientShareVatAmount(BigDecimal patientShareVatAmount) {
		this.patientShareVatAmount = patientShareVatAmount;
	}

	public String getPatientShareVatCurrency() {
		return patientShareVatCurrency;
	}

	public void setPatientShareVatCurrency(String patientShareVatCurrency) {
		this.patientShareVatCurrency = patientShareVatCurrency;
	}

	public DrugList() {
		super();
	}

	public DrugList(String drugCode, String unitType, BigDecimal quantity, Double unitPrice, String orderingClinician,
			String duration, String frequency, String frequencyOthersDescription, String serviceStartDate,
			String serviceEndDate, String drugListId) {
		super(drugCode, unitType, quantity, unitPrice, frequency, frequencyOthersDescription, duration);
		this.orderingClinician = orderingClinician;
		this.serviceStartDate = serviceStartDate;
		this.serviceEndDate = serviceEndDate;
		this.drugListId = drugListId;
	}

	public DrugList(String drugCode, String unitType, BigDecimal quantity, Double unitPrice, Long duration,
			String frequency, String status, String frequencyOthersDescription, String useUnitType, Double useUnitValue,
			Date serviceStartDate, BigDecimal net, BigDecimal patientShare, String scientificCode, Long drugListId) {
		super(drugCode, unitType, quantity, unitPrice, useUnitValue, frequency, frequencyOthersDescription, duration,
				net, patientShare, scientificCode);
		this.status = status;
		this.useUnitType = useUnitType;
		try {
			this.serviceStartDate = new SimpleDateFormat("dd-MM-yyyy").format(serviceStartDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.drugListId = String.valueOf(drugListId);
	}

	public DrugList(String requestId) {
		this.requestId = requestId;
	}

	@Override
	public Predicate toPredicate(Root<ServiceInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("requestId")), requestId));
		predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("isDeleted")), false));
		if (!StringUtils.isBlank(UserInfoUtil.getPatientId(SecurityContextHolder.getContext().getAuthentication()))) {
			predicates.add(criteriaBuilder.notEqual(root.get("serviceResponseInfo").get("status"), "REJECTED"));
		}
		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}
}