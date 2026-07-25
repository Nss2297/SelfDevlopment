package com.waseel.prescription.model.prescription;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.jpa.domain.Specification;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.waseel.prescription.persist.prescriptionservice.Diagnosis;
import com.waseel.prescription.validator.customannotation.IsValidDiagnosisType;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DiagnosisCodes implements Specification<Diagnosis> {

	@NotEmpty(message = "diagnosisCode {notEmptyValidation}")
	private String diagnosisCode;

	@NotEmpty(message = "diagnosisType {notEmptyValidation}")
	@IsValidDiagnosisType(message = "{diagnosisTypeValidation}")
	private String diagnosisType;

	@Schema(hidden = true)
	private String requestId;

	private String diagnosisCodeDescription;

	public String getDiagnosisCode() {
		return diagnosisCode;
	}

	public void setDiagnosisCode(String diagnosisCode) {
		this.diagnosisCode = diagnosisCode;
	}

	public String getDiagnosisType() {
		return diagnosisType;
	}

	public void setDiagnosisType(String diagnosisType) {
		this.diagnosisType = diagnosisType;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public DiagnosisCodes(String requestId) {
		this.requestId = requestId;
	}

	public String getDiagnosisCodeDescription() {
		return diagnosisCodeDescription;
	}

	public void setDiagnosisCodeDescription(String diagnosisCodeDescription) {
		this.diagnosisCodeDescription = diagnosisCodeDescription;
	}

	public DiagnosisCodes() {
		super();
	}

	public DiagnosisCodes(String diagnosisCode, String diagnosisType) {
		super();
		this.diagnosisCode = diagnosisCode;
		this.diagnosisType = diagnosisType;
	}

	public DiagnosisCodes(String diagnosisCode, String diagnosisType, String diagnosisCodeDescription) {
		this.diagnosisCode = diagnosisCode;
		this.diagnosisType = diagnosisType;
		this.diagnosisCodeDescription = diagnosisCodeDescription;
	}

	@Override
	public Predicate toPredicate(Root<Diagnosis> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();
		predicates
				.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("diagnosisId").get("requestId")), requestId));
		predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("isDeleted")), false));
		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}
}
