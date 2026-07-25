package com.waseel.pbm.fdbvalidationservice.persist.mdss;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class FDBPediatricAgeSeverityLevelId implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Column(name = "ServiceCode")
    private String serviceCode;
    @Column(name = "PayerId")
    private String payerId;

    public String getServiceCode() {
        return this.serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getPayerId() {
        return this.payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }
}
