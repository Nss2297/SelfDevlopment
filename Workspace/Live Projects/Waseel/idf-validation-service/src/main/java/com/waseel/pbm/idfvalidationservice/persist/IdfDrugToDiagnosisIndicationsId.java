package com.waseel.pbm.idfvalidationservice.persist;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * IdfindicationsId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class IdfDrugToDiagnosisIndicationsId implements Serializable {

    // Fields

    /**
     *
     */
    private static final long serialVersionUID = -1131625151931778341L;
    @Column(name = "ICDDiagnosisCode", length = 100)
    private String icdDiagnosisCode;

    @Column(name = "OldServiceCode", length = 100)
    private String oldServiceCode;
    
    @Column(name = "ServiceCode", length = 100)
    private String serviceCode;

    // Constructors

    /**
     * default constructor
     */
    public IdfDrugToDiagnosisIndicationsId() {
    }

    /**
     * full constructor
     */
    public IdfDrugToDiagnosisIndicationsId(String icdDiagnosisCode, String oldServiceCode, String serviceCode) {
        this.icdDiagnosisCode = icdDiagnosisCode;
        this.oldServiceCode = oldServiceCode;
        this.serviceCode = serviceCode;
    }

    // Property accessors

    public String getIcdDiagnosisCode() {
        return icdDiagnosisCode;
    }

    public void setIcdDiagnosisCode(String iCDDiagnosisCode) {
        icdDiagnosisCode = iCDDiagnosisCode;
    }

    public String getOldServiceCode() {
        return this.oldServiceCode;
    }

    public void setOldServiceCode(String oldServiceCode) {
        this.oldServiceCode = oldServiceCode;
    }

    public String getServiceCode() {
        return this.serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof IdfDrugToDiagnosisIndicationsId))
            return false;
        IdfDrugToDiagnosisIndicationsId castOther = (IdfDrugToDiagnosisIndicationsId) other;

        return ((this.getIcdDiagnosisCode() == castOther.getIcdDiagnosisCode()) || (this.getIcdDiagnosisCode() != null
                && castOther.getIcdDiagnosisCode() != null && this.getIcdDiagnosisCode().equals(castOther.getIcdDiagnosisCode())))
                && ((this.getOldServiceCode() == castOther.getOldServiceCode())
                || (this.getOldServiceCode() != null && castOther.getOldServiceCode() != null
                && this.getOldServiceCode().equals(castOther.getOldServiceCode())))
                && ((this.getServiceCode() == castOther.getServiceCode())
                || (this.getServiceCode() != null && castOther.getServiceCode() != null
                && this.getServiceCode().equals(castOther.getServiceCode())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + (getIcdDiagnosisCode() == null ? 0 : this.getIcdDiagnosisCode().hashCode());
        result = 37 * result + (getOldServiceCode() == null ? 0 : this.getOldServiceCode().hashCode());
        result = 37 * result + (getServiceCode() == null ? 0 : this.getServiceCode().hashCode());
        return result;
    }

}