package com.waseel.prescription.model.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.waseel.prescription.persist.businessrules.PayerMemberInfo;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PayerMemberInfoModel implements Specification<PayerMemberInfo> {

    private String memberName;
    private String age;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dob;
    private String gender;
    private String nationality;
    private String payerId;
    private String idNumber;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public PayerMemberInfoModel(String payerId, String idNumber) {
        this.payerId = payerId;
        this.idNumber = idNumber;
    }

    public PayerMemberInfoModel(String memberName, Date dob, String gender, String nationality, String idNumber) {
        this.memberName = memberName;
        this.dob = dob;
        this.gender = gender;
        this.nationality = nationality;
        this.idNumber = idNumber;
    }

    @Override
    public Predicate toPredicate(Root<PayerMemberInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.like(root.get("idNumber"), "%" + idNumber + "%"));
        predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("memberName")), "%" + idNumber.toLowerCase() + "%"));
        List<Predicate> predicatesNew = new ArrayList<>();
        predicatesNew.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("payerId"))
                , "%" + payerId.toLowerCase() + "%"));
        predicatesNew.add(criteriaBuilder.isFalse(root.get("isCancelled")));
        predicatesNew.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get("status")), "active"));
        Predicate combinedPredicate = criteriaBuilder.or(predicates.get(0), predicates.get(1));
        Predicate combinedPredicateNew = criteriaBuilder.and(predicatesNew.toArray(new Predicate[0]));
        return criteriaBuilder.and(combinedPredicate, combinedPredicateNew);
    }
}
