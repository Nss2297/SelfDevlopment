package com.waseel.pbm.pbmadminservice.model;

import com.waseel.pbm.pbmadminservice.persist.mdss.ServiceCodeGCNSeqNoMapping;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class ServiceCodeGCNSeqNoMappingRequest implements Specification<ServiceCodeGCNSeqNoMapping> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String serviceCode;
    private Integer gcnSeqNo;
    private String productPackageUnit;
    private Integer productPackageSize;
    private Long id;

    public ServiceCodeGCNSeqNoMappingRequest() {

    }

    public ServiceCodeGCNSeqNoMappingRequest(String serviceCode, Integer gcnSeqNo) {
        this.serviceCode = serviceCode;
        this.gcnSeqNo = gcnSeqNo;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public Integer getGcnSeqNo() {
        return gcnSeqNo;
    }

    public void setGcnSeqNo(Integer gcnSeqNo) {
        this.gcnSeqNo = gcnSeqNo;
    }

    public String getProductPackageUnit() {
        return productPackageUnit;
    }

    public void setProductPackageUnit(String productPackageUnit) {
        this.productPackageUnit = productPackageUnit;
    }

    public Integer getProductPackageSize() {
        return productPackageSize;
    }

    public void setProductPackageSize(Integer productPackageSize) {
        this.productPackageSize = productPackageSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Predicate toPredicate(Root<ServiceCodeGCNSeqNoMapping> root, CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        Character isDelete = '0';
        if (gcnSeqNo > 0) {
            predicates.add(criteriaBuilder.equal(root.get("gcnSeqNo"), gcnSeqNo));
        }
        if (!StringUtils.isBlank(serviceCode)) {
            predicates.add(criteriaBuilder.like(root.get("serviceCode"), "%" + serviceCode + "%"));
        }
        predicates.add(criteriaBuilder.equal(root.get("isDeleted"), isDelete));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
