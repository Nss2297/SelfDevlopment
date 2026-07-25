package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rfmlibd0IcdBillableDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RFMLIBD0_ICD_BILLABLE_DESC"
    ,schema="MEDK_FDB"
)

public class Rfmlibd0IcdBillableDesc  implements java.io.Serializable {


    // Fields    

     private Boolean icdBillableInd;
     private String icdBillableIndDesc;


    // Constructors

    /** default constructor */
    public Rfmlibd0IcdBillableDesc() {
    }

    
    /** full constructor */
    public Rfmlibd0IcdBillableDesc(Boolean icdBillableInd, String icdBillableIndDesc) {
        this.icdBillableInd = icdBillableInd;
        this.icdBillableIndDesc = icdBillableIndDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="ICD_BILLABLE_IND", unique=true, nullable=false, precision=1, scale=0)

    public Boolean getIcdBillableInd() {
        return this.icdBillableInd;
    }
    
    public void setIcdBillableInd(Boolean icdBillableInd) {
        this.icdBillableInd = icdBillableInd;
    }
    
    @Column(name="ICD_BILLABLE_IND_DESC", nullable=false, length=50)

    public String getIcdBillableIndDesc() {
        return this.icdBillableIndDesc;
    }
    
    public void setIcdBillableIndDesc(String icdBillableIndDesc) {
        this.icdBillableIndDesc = icdBillableIndDesc;
    }
   








}