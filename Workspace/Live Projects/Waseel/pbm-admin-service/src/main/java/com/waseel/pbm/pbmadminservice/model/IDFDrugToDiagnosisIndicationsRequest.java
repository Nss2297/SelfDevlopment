package com.waseel.pbm.pbmadminservice.model;

import com.waseel.pbm.pbmadminservice.persist.mdss.IdfDrugToDiagnosisIndications;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class IDFDrugToDiagnosisIndicationsRequest implements Specification<IdfDrugToDiagnosisIndications> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String icdDiagnosisCode;
	private String serviceCode;
	private Character isDeleted;
    private String oldServiceCode;

	public String getIcdDiagnosisCode() {
		return icdDiagnosisCode;
	}

    public void setIcdDiagnosisCode(String icdDiagnosisCode) {
        this.icdDiagnosisCode = icdDiagnosisCode;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public Character getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Character isDeleted) {
        this.isDeleted = isDeleted;
    }

    public IDFDrugToDiagnosisIndicationsRequest(String icdDiagnosisCode, String serviceCode) {
        this.icdDiagnosisCode = icdDiagnosisCode;
        this.serviceCode = serviceCode;
    }

    public String getOldServiceCode() {
        return oldServiceCode;
    }

    public void setOldServiceCode(String oldServiceCode) {
        this.oldServiceCode = oldServiceCode;
    }

    @Override
    public Predicate toPredicate(Root<IdfDrugToDiagnosisIndications> root, CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        Character isDeletedData = '0';
        if (!StringUtils.isBlank(icdDiagnosisCode)) {
            predicates.add(criteriaBuilder.like(root.get("icdDiagnosisCode"), "%" + icdDiagnosisCode.trim() + "%"));
        }
        if (!StringUtils.isBlank(serviceCode)) {
            predicates.add(criteriaBuilder.like(root.get("serviceCode"), "%" + serviceCode.trim() + "%"));
        }
        predicates.add(criteriaBuilder.equal(root.get("isDeleted"), isDeletedData));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
