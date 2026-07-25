package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rmidead1RefFedDeaDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMIDEAD1_REF_FED_DEA_DESC"
    ,schema="MEDK_FDB"
)

public class Rmidead1RefFedDeaDesc  implements java.io.Serializable {


    // Fields    

     private String medRefDeaCd;
     private String medRefDeaCdDesc;


    // Constructors

    /** default constructor */
    public Rmidead1RefFedDeaDesc() {
    }

    
    /** full constructor */
    public Rmidead1RefFedDeaDesc(String medRefDeaCd, String medRefDeaCdDesc) {
        this.medRefDeaCd = medRefDeaCd;
        this.medRefDeaCdDesc = medRefDeaCdDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="MED_REF_DEA_CD", unique=true, nullable=false, length=1)

    public String getMedRefDeaCd() {
        return this.medRefDeaCd;
    }
    
    public void setMedRefDeaCd(String medRefDeaCd) {
        this.medRefDeaCd = medRefDeaCd;
    }
    
    @Column(name="MED_REF_DEA_CD_DESC", nullable=false, length=60)

    public String getMedRefDeaCdDesc() {
        return this.medRefDeaCdDesc;
    }
    
    public void setMedRefDeaCdDesc(String medRefDeaCdDesc) {
        this.medRefDeaCdDesc = medRefDeaCdDesc;
    }
   








}