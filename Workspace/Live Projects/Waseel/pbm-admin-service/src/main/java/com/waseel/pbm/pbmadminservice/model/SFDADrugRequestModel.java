package com.waseel.pbm.pbmadminservice.model;

import com.waseel.pbm.pbmadminservice.persist.mdss.DrugService;
import org.apache.commons.lang.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SFDADrugRequestModel implements Serializable {

    private String sfdaCode;

    private String tradeName;

    private String scientificCode;

    private String scientificName;

    private String gtinCode;

    private int pageNumber = 0;

    private int recordSize = 10;

    public String getSfdaCode() {
        return sfdaCode;
    }

    public void setSfdaCode(String sfdaCode) {
        this.sfdaCode = sfdaCode;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getScientificCode() {
        return scientificCode;
    }

    public void setScientificCode(String scientificCode) {
        this.scientificCode = scientificCode;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String scientificName) {
        this.scientificName = scientificName;
    }

    public String getGtinCode() {
        return gtinCode;
    }

    public void setGtinCode(String gtinCode) {
        this.gtinCode = gtinCode;
    }

    public SFDADrugRequestModel() {
        super();
    }


    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getRecordSize() {
        return recordSize;
    }

    public void setRecordSize(int recordSize) {
        this.recordSize = recordSize;
    }

    public SFDADrugRequestModel(String sfdaCode, String tradeName, String scientificCode, String scientificName,
                                String gtinCode, int pageNumber, int recordSize) {
        super();
        this.sfdaCode = sfdaCode;
        this.tradeName = tradeName;
        this.scientificCode = scientificCode;
        this.scientificName = scientificName;
        this.gtinCode = gtinCode;
        this.pageNumber = pageNumber;
        this.recordSize = recordSize;
    }

    public Predicate toPredicate(Root<DrugService> root, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (!StringUtils.isBlank(sfdaCode)) {
            predicates.add(criteriaBuilder.equal(root.get("otherCodesValue"), sfdaCode));
        }
        if (!StringUtils.isBlank(tradeName)) {
            predicates.add(criteriaBuilder.equal(root.get("display"), tradeName));
        }
        if (!StringUtils.isBlank(scientificCode)) {
            predicates.add(criteriaBuilder.equal(root.get("scientificCode"), scientificCode));
        }
        if (!StringUtils.isBlank(scientificName)) {
            predicates.add(criteriaBuilder.equal(root.get("ingredients"), scientificName));
        }
        if (!StringUtils.isBlank(gtinCode)) {
            predicates.add(criteriaBuilder.equal(root.get("code"), gtinCode));
        }
        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("drugServiceMetaData")
                .get("effectiveDate"), new Date()));
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

}
