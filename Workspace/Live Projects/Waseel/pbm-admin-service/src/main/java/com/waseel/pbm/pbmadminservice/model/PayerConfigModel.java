package com.waseel.pbm.pbmadminservice.model;

import com.waseel.pbm.pbmadminservice.persist.mdss.PayerConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class PayerConfigModel implements Specification<PayerConfig> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String payerId;
    private String pbmPayerType;

    public PayerConfigModel(String payerId) {
        this.payerId = payerId;
    }

    public PayerConfigModel(String payerId, String pbmPayerType) {
        this.payerId = payerId;
        this.pbmPayerType = pbmPayerType;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getPbmPayerType() {
        return pbmPayerType;
    }

    public void setPbmPayerType(String pbmPayerType) {
        this.pbmPayerType = pbmPayerType;
    }

    @Override
    public Predicate toPredicate(Root<PayerConfig> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        Character isEnabledPayer = '1';
        if (!StringUtils.isBlank(payerId)) {
            predicates.add(criteriaBuilder.like(root.get("id").get("payerId"), "%" + payerId + "%"));
        }
        predicates.add(criteriaBuilder.equal(root.get("id").get("isEnabled"), isEnabledPayer));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
