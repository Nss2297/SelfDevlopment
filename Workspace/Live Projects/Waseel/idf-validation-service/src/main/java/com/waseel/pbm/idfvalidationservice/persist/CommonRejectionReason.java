package com.waseel.pbm.idfvalidationservice.persist;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CommonRejectionReason entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "CommonRejectionReason", schema = "MDSS")

public class CommonRejectionReason implements java.io.Serializable {

    // Fields

    /**
     *
     */
    private static final long serialVersionUID = -2562943180969365292L;
    private String rejectionCode;
    private String rejectionReason;

    // Constructors

    /**
     * default constructor
     */
    public CommonRejectionReason() {
    }

    /**
     * minimal constructor
     */
    public CommonRejectionReason(String rejectionCode) {
        this.rejectionCode = rejectionCode;
    }

    /**
     * full constructor
     */
    public CommonRejectionReason(String rejectionCode, String rejectionReason) {
        this.rejectionCode = rejectionCode;
        this.rejectionReason = rejectionReason;
    }

    // Property accessors
    @Id

    @Column(name = "RejectionCode", unique = true, nullable = false, length = 30)

    public String getRejectionCode() {
        return this.rejectionCode;
    }

    public void setRejectionCode(String rejectionCode) {
        this.rejectionCode = rejectionCode;
    }

    @Column(name = "RejectionReason", length = 200)

    public String getRejectionReason() {
        return this.rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

}