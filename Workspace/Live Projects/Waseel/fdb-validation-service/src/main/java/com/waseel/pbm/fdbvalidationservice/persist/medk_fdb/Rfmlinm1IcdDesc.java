package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rfmlinm1IcdDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RFMLINM1_ICD_DESC"
    ,schema="MEDK_FDB"
)

public class Rfmlinm1IcdDesc  implements java.io.Serializable {


    // Fields    

     private Rfmlinm1IcdDescId id;
     private String icdDesc;
     private String icdDescSourceCd;
     private String icdStatusCd;
     private Timestamp icdFirstDt;
     private Timestamp icdLastDt;
     private Boolean icdBillableInd;


    // Constructors

    /** default constructor */
    public Rfmlinm1IcdDesc() {
    }

	/** minimal constructor */
    public Rfmlinm1IcdDesc(Rfmlinm1IcdDescId id, String icdDesc, String icdDescSourceCd, String icdStatusCd, Boolean icdBillableInd) {
        this.id = id;
        this.icdDesc = icdDesc;
        this.icdDescSourceCd = icdDescSourceCd;
        this.icdStatusCd = icdStatusCd;
        this.icdBillableInd = icdBillableInd;
    }
    
    /** full constructor */
    public Rfmlinm1IcdDesc(Rfmlinm1IcdDescId id, String icdDesc, String icdDescSourceCd, String icdStatusCd, Timestamp icdFirstDt, Timestamp icdLastDt, Boolean icdBillableInd) {
        this.id = id;
        this.icdDesc = icdDesc;
        this.icdDescSourceCd = icdDescSourceCd;
        this.icdStatusCd = icdStatusCd;
        this.icdFirstDt = icdFirstDt;
        this.icdLastDt = icdLastDt;
        this.icdBillableInd = icdBillableInd;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="icdCd", column=@Column(name="ICD_CD", nullable=false, length=10) ), 
        @AttributeOverride(name="icdCdType", column=@Column(name="ICD_CD_TYPE", nullable=false, length=2) ) } )

    public Rfmlinm1IcdDescId getId() {
        return this.id;
    }
    
    public void setId(Rfmlinm1IcdDescId id) {
        this.id = id;
    }
    
    @Column(name="ICD_DESC", nullable=false, length=500)

    public String getIcdDesc() {
        return this.icdDesc;
    }
    
    public void setIcdDesc(String icdDesc) {
        this.icdDesc = icdDesc;
    }
    
    @Column(name="ICD_DESC_SOURCE_CD", nullable=false, length=2)

    public String getIcdDescSourceCd() {
        return this.icdDescSourceCd;
    }
    
    public void setIcdDescSourceCd(String icdDescSourceCd) {
        this.icdDescSourceCd = icdDescSourceCd;
    }
    
    @Column(name="ICD_STATUS_CD", nullable=false, length=1)

    public String getIcdStatusCd() {
        return this.icdStatusCd;
    }
    
    public void setIcdStatusCd(String icdStatusCd) {
        this.icdStatusCd = icdStatusCd;
    }
    
    @Column(name="ICD_FIRST_DT", length=7)

    public Timestamp getIcdFirstDt() {
        return this.icdFirstDt;
    }
    
    public void setIcdFirstDt(Timestamp icdFirstDt) {
        this.icdFirstDt = icdFirstDt;
    }
    
    @Column(name="ICD_LAST_DT", length=7)

    public Timestamp getIcdLastDt() {
        return this.icdLastDt;
    }
    
    public void setIcdLastDt(Timestamp icdLastDt) {
        this.icdLastDt = icdLastDt;
    }
    
    @Column(name="ICD_BILLABLE_IND", nullable=false, precision=1, scale=0)

    public Boolean getIcdBillableInd() {
        return this.icdBillableInd;
    }
    
    public void setIcdBillableInd(Boolean icdBillableInd) {
        this.icdBillableInd = icdBillableInd;
    }
   








}