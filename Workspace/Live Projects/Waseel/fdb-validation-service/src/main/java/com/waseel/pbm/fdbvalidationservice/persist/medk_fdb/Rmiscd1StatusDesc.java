package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rmiscd1StatusDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMISCD1_STATUS_DESC"
    ,schema="MEDK_FDB"
)

public class Rmiscd1StatusDesc  implements java.io.Serializable {


    // Fields    

     private String medStatusCd;
     private String medStatusCdDesc;


    // Constructors

    /** default constructor */
    public Rmiscd1StatusDesc() {
    }

    
    /** full constructor */
    public Rmiscd1StatusDesc(String medStatusCd, String medStatusCdDesc) {
        this.medStatusCd = medStatusCd;
        this.medStatusCdDesc = medStatusCdDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="MED_STATUS_CD", unique=true, nullable=false, length=1)

    public String getMedStatusCd() {
        return this.medStatusCd;
    }
    
    public void setMedStatusCd(String medStatusCd) {
        this.medStatusCd = medStatusCd;
    }
    
    @Column(name="MED_STATUS_CD_DESC", nullable=false, length=30)

    public String getMedStatusCdDesc() {
        return this.medStatusCdDesc;
    }
    
    public void setMedStatusCdDesc(String medStatusCdDesc) {
        this.medStatusCdDesc = medStatusCdDesc;
    }
   








}