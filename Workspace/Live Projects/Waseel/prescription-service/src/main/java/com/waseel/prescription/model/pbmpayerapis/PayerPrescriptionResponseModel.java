package com.waseel.prescription.model.pbmpayerapis;

import java.math.BigDecimal;
import java.util.Date;

public class PayerPrescriptionResponseModel {

    private String referenceNo;
    private Long idNumber;
    private Date dateAndTime;
    private String providerId;
    private String providerName;
    private String status;
    private String memberName;

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public Long getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(Long idNumber) {
        this.idNumber = idNumber;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Date dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public PayerPrescriptionResponseModel(String referenceNo, BigDecimal idNumber,
                                          Date dateAndTime, String providerId,
                                          String providerName, String status,
                                          String memberName) {
        this.referenceNo = referenceNo;
        this.idNumber = idNumber.longValue();
        this.memberName = memberName;
        this.dateAndTime = dateAndTime;
        this.providerId = providerId;
        this.providerName = providerName;
        this.status = status;
    }
}
