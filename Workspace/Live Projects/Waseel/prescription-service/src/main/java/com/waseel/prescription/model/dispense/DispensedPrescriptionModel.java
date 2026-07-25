package com.waseel.prescription.model.dispense;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.waseel.prescription.persist.prescriptionservice.DispensedPrescription;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DispensedPrescriptionModel implements Specification<DispensedPrescription> {

	private String pharmacy;
	private Date dispenseDate;
	@JsonProperty("ePrescriptionReferenceNumber")
	private String ePrescriptionReferenceNumber;

	public DispensedPrescriptionModel(String ePrescriptionReferenceNumber) {
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
	}

	public DispensedPrescriptionModel(String pharmacy, Date dispenseDate) {
		this.pharmacy = pharmacy;
		this.dispenseDate = dispenseDate;
	}

	public DispensedPrescriptionModel() {
	}

	public String getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(String pharmacy) {
		this.pharmacy = pharmacy;
	}

	public Date getDispenseDate() {
		return dispenseDate;
	}

	public void setDispenseDate(Date dispenseDate) {
		this.dispenseDate = dispenseDate;
	}

	public String getePrescriptionReferenceNumber() {
		return ePrescriptionReferenceNumber;
	}

	public void setePrescriptionReferenceNumber(String ePrescriptionReferenceNumber) {
		this.ePrescriptionReferenceNumber = ePrescriptionReferenceNumber;
	}

	@Override
	public Predicate toPredicate(Root<DispensedPrescription> root, CriteriaQuery<?> query,
			CriteriaBuilder criteriaBuilder) {
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get("ePrescriptionReferenceNumber"), ePrescriptionReferenceNumber));
		predicates.add(
				criteriaBuilder.equal(criteriaBuilder.lower(root.get("switchAccount").get("category")), "provider"));
		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}
}
