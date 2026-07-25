package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rmigscd1RefGenPrcSprdDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMIGSCD1_REF_GEN_PRC_SPRD_DESC"
    ,schema="MEDK_FDB"
)

public class Rmigscd1RefGenPrcSprdDesc  implements java.io.Serializable {


    // Fields    

     private String medRefGenSpreadCd;
     private String medRefGenSpreadCdDesc;


    // Constructors

    /** default constructor */
    public Rmigscd1RefGenPrcSprdDesc() {
    }

    
    /** full constructor */
    public Rmigscd1RefGenPrcSprdDesc(String medRefGenSpreadCd, String medRefGenSpreadCdDesc) {
        this.medRefGenSpreadCd = medRefGenSpreadCd;
        this.medRefGenSpreadCdDesc = medRefGenSpreadCdDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="MED_REF_GEN_SPREAD_CD", unique=true, nullable=false, length=1)

    public String getMedRefGenSpreadCd() {
        return this.medRefGenSpreadCd;
    }
    
    public void setMedRefGenSpreadCd(String medRefGenSpreadCd) {
        this.medRefGenSpreadCd = medRefGenSpreadCd;
    }
    
    @Column(name="MED_REF_GEN_SPREAD_CD_DESC", nullable=false, length=90)

    public String getMedRefGenSpreadCdDesc() {
        return this.medRefGenSpreadCdDesc;
    }
    
    public void setMedRefGenSpreadCdDesc(String medRefGenSpreadCdDesc) {
        this.medRefGenSpreadCdDesc = medRefGenSpreadCdDesc;
    }
   








}