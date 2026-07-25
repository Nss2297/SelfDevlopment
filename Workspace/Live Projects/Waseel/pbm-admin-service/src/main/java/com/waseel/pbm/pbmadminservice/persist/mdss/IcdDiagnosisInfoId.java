package com.waseel.pbm.pbmadminservice.persist.mdss;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Icd10infoId entity. @author MyEclipse Persistence Tools
 */
@Embeddable
public class IcdDiagnosisInfoId implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6695389992200237375L;
    // Fields
    private String requestId;
    private String icdDiagnosisCode;

    // Constructors

    /**
     * default constructor
     */
    public IcdDiagnosisInfoId() {
    }

    /**
     * full constructor
     */
    public IcdDiagnosisInfoId(String requestId, String icdDiagnosisCode) {
        this.requestId = requestId;
        this.icdDiagnosisCode = icdDiagnosisCode;
    }

    // Property accessors
    @Column(name = "RequestId", precision = 0)
    public String getRequestId() {
        return this.requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    @Column(name = "IcdDiagnosisCode", length = 10)
    public String getIcdDiagnosisCode() {
        return icdDiagnosisCode;
    }

    public void setIcdDiagnosisCode(String icdDiagnosisCode) {
        this.icdDiagnosisCode = icdDiagnosisCode;
    }

    public boolean equals(Object other) {
        if ((this == other))
            return true;
        if ((other == null))
            return false;
        if (!(other instanceof IcdDiagnosisInfoId))
            return false;
        IcdDiagnosisInfoId castOther = (IcdDiagnosisInfoId) other;
        return ((this.getRequestId() == castOther.getRequestId()) || (this.getRequestId() != null
                && castOther.getRequestId() != null && this.getRequestId().equals(castOther.getRequestId())))
                && ((this.getIcdDiagnosisCode() == castOther.getIcdDiagnosisCode()) || (this.getIcdDiagnosisCode() != null
                && castOther.getIcdDiagnosisCode() != null && this.getIcdDiagnosisCode().equals(castOther.getIcdDiagnosisCode())));
    }

    public int hashCode() {
        int result = 17;
        result = 37 * result + (getRequestId() == null ? 0 : this.getRequestId().hashCode());
        result = 37 * result + (getIcdDiagnosisCode() == null ? 0 : this.getIcdDiagnosisCode().hashCode());
        return result;
    }

}