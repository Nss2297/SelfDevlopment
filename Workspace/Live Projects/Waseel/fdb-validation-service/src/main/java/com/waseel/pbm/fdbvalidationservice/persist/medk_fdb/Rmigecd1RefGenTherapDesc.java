package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rmigecd1RefGenTherapDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMIGECD1_REF_GEN_THERAP_DESC"
    ,schema="MEDK_FDB"
)

public class Rmigecd1RefGenTherapDesc  implements java.io.Serializable {


    // Fields    

     private String medRefGenTheraEquCd;
     private String medRefGenTheraEquCdDesc;


    // Constructors

    /** default constructor */
    public Rmigecd1RefGenTherapDesc() {
    }

    
    /** full constructor */
    public Rmigecd1RefGenTherapDesc(String medRefGenTheraEquCd, String medRefGenTheraEquCdDesc) {
        this.medRefGenTheraEquCd = medRefGenTheraEquCd;
        this.medRefGenTheraEquCdDesc = medRefGenTheraEquCdDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="MED_REF_GEN_THERA_EQU_CD", unique=true, nullable=false, length=1)

    public String getMedRefGenTheraEquCd() {
        return this.medRefGenTheraEquCd;
    }
    
    public void setMedRefGenTheraEquCd(String medRefGenTheraEquCd) {
        this.medRefGenTheraEquCd = medRefGenTheraEquCd;
    }
    
    @Column(name="MED_REF_GEN_THERA_EQU_CD_DESC", nullable=false, length=90)

    public String getMedRefGenTheraEquCdDesc() {
        return this.medRefGenTheraEquCdDesc;
    }
    
    public void setMedRefGenTheraEquCdDesc(String medRefGenTheraEquCdDesc) {
        this.medRefGenTheraEquCdDesc = medRefGenTheraEquCdDesc;
    }
   








}