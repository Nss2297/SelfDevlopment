package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rmigncd1RefGenNameDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMIGNCD1_REF_GEN_NAME_DESC"
    ,schema="MEDK_FDB"
)

public class Rmigncd1RefGenNameDesc  implements java.io.Serializable {


    // Fields    

     private String medRefGenDrugNameCd;
     private String medRefGenDrugNameCdDesc;


    // Constructors

    /** default constructor */
    public Rmigncd1RefGenNameDesc() {
    }

    
    /** full constructor */
    public Rmigncd1RefGenNameDesc(String medRefGenDrugNameCd, String medRefGenDrugNameCdDesc) {
        this.medRefGenDrugNameCd = medRefGenDrugNameCd;
        this.medRefGenDrugNameCdDesc = medRefGenDrugNameCdDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="MED_REF_GEN_DRUG_NAME_CD", unique=true, nullable=false, length=1)

    public String getMedRefGenDrugNameCd() {
        return this.medRefGenDrugNameCd;
    }
    
    public void setMedRefGenDrugNameCd(String medRefGenDrugNameCd) {
        this.medRefGenDrugNameCd = medRefGenDrugNameCd;
    }
    
    @Column(name="MED_REF_GEN_DRUG_NAME_CD_DESC", nullable=false, length=90)

    public String getMedRefGenDrugNameCdDesc() {
        return this.medRefGenDrugNameCdDesc;
    }
    
    public void setMedRefGenDrugNameCdDesc(String medRefGenDrugNameCdDesc) {
        this.medRefGenDrugNameCdDesc = medRefGenDrugNameCdDesc;
    }
   








}