package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rfmlcsd0IcdStatusDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RFMLCSD0_ICD_STATUS_DESC"
    ,schema="MEDK_FDB"
)

public class Rfmlcsd0IcdStatusDesc  implements java.io.Serializable {


    // Fields    

     private String icdStatusCd;
     private String icdStatusDesc;


    // Constructors

    /** default constructor */
    public Rfmlcsd0IcdStatusDesc() {
    }

    
    /** full constructor */
    public Rfmlcsd0IcdStatusDesc(String icdStatusCd, String icdStatusDesc) {
        this.icdStatusCd = icdStatusCd;
        this.icdStatusDesc = icdStatusDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="ICD_STATUS_CD", unique=true, nullable=false, length=1)

    public String getIcdStatusCd() {
        return this.icdStatusCd;
    }
    
    public void setIcdStatusCd(String icdStatusCd) {
        this.icdStatusCd = icdStatusCd;
    }
    
    @Column(name="ICD_STATUS_DESC", nullable=false, length=50)

    public String getIcdStatusDesc() {
        return this.icdStatusDesc;
    }
    
    public void setIcdStatusDesc(String icdStatusDesc) {
        this.icdStatusDesc = icdStatusDesc;
    }
   








}