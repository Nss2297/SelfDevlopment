package com.waseel.prescription.model.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.waseel.prescription.persist.businessrules.PhysicianInfo;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(Include.NON_NULL)
public class PhysicianConfigModel implements Specification<PhysicianInfo> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long providerId;
    private String registrationNumber;
    private String name;
    private String category;
    private String physicianSpeciality;

    public String getPhysicianSpeciality() {
        return physicianSpeciality;
    }

    public void setPhysicianSpeciality(String physicianSpeciality) {
        this.physicianSpeciality = physicianSpeciality;
    }

    public Long getProviderId() {
        return providerId;
    }

    public void setProviderId(Long providerId) {
        this.providerId = providerId;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public PhysicianConfigModel() {
    }

    public PhysicianConfigModel(Long providerId, String physician) {
        this.providerId = providerId;
        this.registrationNumber = physician;
        this.name = physician;
        this.category = physician;
    }

    public PhysicianConfigModel(String registrationNumber, String name,
                                String category, String physicianSpeciality) {
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.category = category;
        this.physicianSpeciality = physicianSpeciality;
    }

    @Override
    public Predicate toPredicate(Root<PhysicianInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        Predicate predicate = criteriaBuilder.equal(root.get("providerId"), providerId);
        predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
        predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("category").get("categoryDescription")),
                "%" + category.toLowerCase() + "%"));
        predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("registrationNumber")),
                "%" + registrationNumber.toLowerCase() + "%"));
        Predicate combinedPredicate = criteriaBuilder.or(predicates.get(0), predicates.get(1), predicates.get(2));
        return criteriaBuilder.and(combinedPredicate, predicate);
    }
}
