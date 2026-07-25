package com.waseel.pbm.pbmadminservice.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import com.waseel.pbm.pbmadminservice.persist.mdss.DrugService;

public class DrugServiceModel implements Specification<DrugService> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String serviceCode;
    private String description;
    private Long drugListId;

    public DrugServiceModel() {
    }

    public DrugServiceModel(String serviceCode, String description) {
        super();
        this.serviceCode = serviceCode;
        this.description = description;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public String getDescription() {
        return description;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public Long getDrugListId() {
		return drugListId;
	}

	public void setDrugListId(Long drugListId) {
		this.drugListId = drugListId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
    public Predicate toPredicate(Root<DrugService> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(criteriaBuilder.equal(root.get("drugListId"), drugListId));
        if (!StringUtils.isBlank(serviceCode)) {
            String code = serviceCode.toLowerCase().trim();
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("otherCodesValue"))
                    , "%" + code + "%"));
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("display"))
                    , "%" + code + "%"));
            return criteriaBuilder.and(predicates.get(0), criteriaBuilder.or(predicates.get(1), predicates.get(2)));
        }
        if (!StringUtils.isBlank(description)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("display"))
                    , "%" + description.toLowerCase().trim() + "%"));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
