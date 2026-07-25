package com.waseel.pbm.pbmadminservice.model;

public class ServiceCodeGCNSeqNoMappingModel {

    private String serviceCode;
    private Integer gcnSeqNo;
    private String productPackageUnit;
    private Integer productPackageSize;
    private Long id;

    public ServiceCodeGCNSeqNoMappingModel(String serviceCode, Integer gcnSeqNo,
                                           String productPackageUnit, Integer productPackageSize, Long id) {
        this.serviceCode = serviceCode;
        this.gcnSeqNo = gcnSeqNo;
        this.productPackageUnit = productPackageUnit;
        this.productPackageSize = productPackageSize;
        this.id = id;
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
}
