package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rstrscd0StrengthStatusDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RSTRSCD0_STRENGTH_STATUS_DESC"
    ,schema="MEDK_FDB"
)

public class Rstrscd0StrengthStatusDesc  implements java.io.Serializable {


    // Fields    

     private Boolean strengthStatusCode;
     private String strengthStatusDesc;


    // Constructors

    /** default constructor */
    public Rstrscd0StrengthStatusDesc() {
    }

	/** minimal constructor */
    public Rstrscd0StrengthStatusDesc(Boolean strengthStatusCode) {
        this.strengthStatusCode = strengthStatusCode;
    }
    
    /** full constructor */
    public Rstrscd0StrengthStatusDesc(Boolean strengthStatusCode, String strengthStatusDesc) {
        this.strengthStatusCode = strengthStatusCode;
        this.strengthStatusDesc = strengthStatusDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="STRENGTH_STATUS_CODE", unique=true, nullable=false, precision=1, scale=0)

    public Boolean getStrengthStatusCode() {
        return this.strengthStatusCode;
    }
    
    public void setStrengthStatusCode(Boolean strengthStatusCode) {
        this.strengthStatusCode = strengthStatusCode;
    }
    
    @Column(name="STRENGTH_STATUS_DESC", length=100)

    public String getStrengthStatusDesc() {
        return this.strengthStatusDesc;
    }
    
    public void setStrengthStatusDesc(String strengthStatusDesc) {
        this.strengthStatusDesc = strengthStatusDesc;
    }
   








}