package com.waseel.pbm.pbmadminservice.model;

import com.waseel.pbm.pbmadminservice.persist.mdss.PCDrugToDiagnosis;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class PCDrugToDiagnosisRequest implements Specification<PCDrugToDiagnosis> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String serviceCode;
    private String icdCode;
    private String payerId;
    private String moduleName;
    private String categoryOfApproval;
    private String rejectionCategory;
    private String serviceStatus;
    private String additionalRejectionReason;

    public String getServiceCode() {
        return serviceCode;
    }

    public String getIcdCode() {
        return icdCode;
    }

    public String getPayerId() {
        return payerId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getCategoryOfApproval() {
        return categoryOfApproval;
    }

    public String getRejectionCategory() {
        return rejectionCategory;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public String getAdditionalRejectionReason() {
        return additionalRejectionReason;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public void setIcdCode(String icdCode) {
        this.icdCode = icdCode;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public void setCategoryOfApproval(String categoryOfApproval) {
        this.categoryOfApproval = categoryOfApproval;
    }

    public void setRejectionCategory(String rejectionCategory) {
        this.rejectionCategory = rejectionCategory;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public void setAdditionalRejectionReason(String additionalRejectionReason) {
        this.additionalRejectionReason = additionalRejectionReason;
    }

    public PCDrugToDiagnosisRequest(String serviceCode, String icdCode, String payerId, String moduleName,
                                    String categoryOfApproval, String rejectionCategory, String serviceStatus) {
        this.serviceCode = serviceCode;
        this.icdCode = icdCode;
        this.payerId = payerId;
        this.moduleName = moduleName;
        this.categoryOfApproval = categoryOfApproval;
        this.rejectionCategory = rejectionCategory;
        this.serviceStatus = serviceStatus;
    }

    public PCDrugToDiagnosisRequest() {
    }

    @Override
    public Predicate toPredicate(Root<PCDrugToDiagnosis> root, CriteriaQuery<?> criteriaQuery,
                                 CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (!StringUtils.isBlank(icdCode)) {
            predicates.add(criteriaBuilder.equal(root.get("icdCode"), icdCode));
        }
        if (!StringUtils.isBlank(serviceCode)) {
            predicates.add(
                    criteriaBuilder.like(root.get("serviceCode"), "%" + serviceCode + "%"));
        }
        if (!StringUtils.isBlank(payerId)) {
            predicates.add(criteriaBuilder.equal(root.get("payerId"), payerId));
        }
        if (!StringUtils.isBlank(moduleName)) {
            predicates.add(criteriaBuilder.equal(root.get("moduleName"), moduleName));
        }
        if (!StringUtils.isBlank(categoryOfApproval)) {
            predicates.add(criteriaBuilder.equal(root.get("categoryOfApproval"), categoryOfApproval));
        }
        if (!StringUtils.isBlank(rejectionCategory)) {
            predicates.add(criteriaBuilder.equal(root.get("rejectionCategory"), rejectionCategory));
        }
        if (!StringUtils.isBlank(serviceStatus)) {
            predicates.add(criteriaBuilder.equal(root.get("serviceStatus"), serviceStatus));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
