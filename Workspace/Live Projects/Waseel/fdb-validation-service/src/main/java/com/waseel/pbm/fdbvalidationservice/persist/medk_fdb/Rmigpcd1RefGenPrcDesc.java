package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rmigpcd1RefGenPrcDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMIGPCD1_REF_GEN_PRC_DESC"
    ,schema="MEDK_FDB"
)

public class Rmigpcd1RefGenPrcDesc  implements java.io.Serializable {


    // Fields    

     private String medRefGenCompPriceCd;
     private String medRefGenCompPriceCdDesc;


    // Constructors

    /** default constructor */
    public Rmigpcd1RefGenPrcDesc() {
    }

    
    /** full constructor */
    public Rmigpcd1RefGenPrcDesc(String medRefGenCompPriceCd, String medRefGenCompPriceCdDesc) {
        this.medRefGenCompPriceCd = medRefGenCompPriceCd;
        this.medRefGenCompPriceCdDesc = medRefGenCompPriceCdDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="MED_REF_GEN_COMP_PRICE_CD", unique=true, nullable=false, length=1)

    public String getMedRefGenCompPriceCd() {
        return this.medRefGenCompPriceCd;
    }
    
    public void setMedRefGenCompPriceCd(String medRefGenCompPriceCd) {
        this.medRefGenCompPriceCd = medRefGenCompPriceCd;
    }
    
    @Column(name="MED_REF_GEN_COMP_PRICE_CD_DESC", nullable=false, length=90)

    public String getMedRefGenCompPriceCdDesc() {
        return this.medRefGenCompPriceCdDesc;
    }
    
    public void setMedRefGenCompPriceCdDesc(String medRefGenCompPriceCdDesc) {
        this.medRefGenCompPriceCdDesc = medRefGenCompPriceCdDesc;
    }
   








}