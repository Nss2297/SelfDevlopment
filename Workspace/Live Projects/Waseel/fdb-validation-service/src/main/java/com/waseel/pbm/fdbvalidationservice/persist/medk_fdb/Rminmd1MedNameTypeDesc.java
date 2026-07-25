package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rminmd1MedNameTypeDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMINMD1_MED_NAME_TYPE_DESC"
    ,schema="MEDK_FDB"
)

public class Rminmd1MedNameTypeDesc  implements java.io.Serializable {


    // Fields    

     private String medNameTypeCd;
     private String medNameTypeCdDesc;


    // Constructors

    /** default constructor */
    public Rminmd1MedNameTypeDesc() {
    }

    
    /** full constructor */
    public Rminmd1MedNameTypeDesc(String medNameTypeCd, String medNameTypeCdDesc) {
        this.medNameTypeCd = medNameTypeCd;
        this.medNameTypeCdDesc = medNameTypeCdDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="MED_NAME_TYPE_CD", unique=true, nullable=false, length=1)

    public String getMedNameTypeCd() {
        return this.medNameTypeCd;
    }
    
    public void setMedNameTypeCd(String medNameTypeCd) {
        this.medNameTypeCd = medNameTypeCd;
    }
    
    @Column(name="MED_NAME_TYPE_CD_DESC", nullable=false, length=30)

    public String getMedNameTypeCdDesc() {
        return this.medNameTypeCdDesc;
    }
    
    public void setMedNameTypeCdDesc(String medNameTypeCdDesc) {
        this.medNameTypeCdDesc = medNameTypeCdDesc;
    }
   








}