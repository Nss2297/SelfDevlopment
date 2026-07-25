package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rstrtd0StrengthTypDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RSTRTD0_STRENGTH_TYP_DESC"
    ,schema="MEDK_FDB"
)

public class Rstrtd0StrengthTypDesc  implements java.io.Serializable {


    // Fields    

     private Boolean strengthTypCode;
     private String strengthTypDesc;


    // Constructors

    /** default constructor */
    public Rstrtd0StrengthTypDesc() {
    }

	/** minimal constructor */
    public Rstrtd0StrengthTypDesc(Boolean strengthTypCode) {
        this.strengthTypCode = strengthTypCode;
    }
    
    /** full constructor */
    public Rstrtd0StrengthTypDesc(Boolean strengthTypCode, String strengthTypDesc) {
        this.strengthTypCode = strengthTypCode;
        this.strengthTypDesc = strengthTypDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="STRENGTH_TYP_CODE", unique=true, nullable=false, precision=1, scale=0)

    public Boolean getStrengthTypCode() {
        return this.strengthTypCode;
    }
    
    public void setStrengthTypCode(Boolean strengthTypCode) {
        this.strengthTypCode = strengthTypCode;
    }
    
    @Column(name="STRENGTH_TYP_DESC", length=100)

    public String getStrengthTypDesc() {
        return this.strengthTypDesc;
    }
    
    public void setStrengthTypDesc(String strengthTypDesc) {
        this.strengthTypDesc = strengthTypDesc;
    }
   








}