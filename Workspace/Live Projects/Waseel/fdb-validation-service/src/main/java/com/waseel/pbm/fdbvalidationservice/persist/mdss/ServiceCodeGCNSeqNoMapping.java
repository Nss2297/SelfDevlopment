package com.waseel.pbm.fdbvalidationservice.persist.mdss;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "ServiceCodeGCNSeqNoMapping", schema = "MDSS")
public class ServiceCodeGCNSeqNoMapping implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ServiceCode")
    private String serviceCode;
    @Column(name = "GcnSeqNo")
    private Integer gcnSeqNo;
    @Column(name = "ProductPackageUnit")
    private String productPackageUnit;
    @Column(name = "ProductPackageSize")
    private Double productPackageSize;
    @Column(name = "IsDeleted")
    private Character isDeleted = '0';
    @Column(name = "LastUpdatedDateTime")
    private Timestamp lastUpdatedDateTime = Timestamp.from(Instant.now());

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

    public Double getProductPackageSize() {
        return productPackageSize;
    }

    public void setProductPackageSize(Double productPackageSize) {
        this.productPackageSize = productPackageSize;
    }

    public Character getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Character isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Timestamp getLastUpdatedDateTime() {
        return lastUpdatedDateTime;
    }

    public void setLastUpdatedDateTime(Timestamp lastUpdatedDateTime) {
        this.lastUpdatedDateTime = lastUpdatedDateTime;
    }
}