package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rhicscd0IngStatCdDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RHICSCD0_ING_STAT_CD_DESC"
    ,schema="MEDK_FDB"
)

public class Rhicscd0IngStatCdDesc  implements java.io.Serializable {


    // Fields    

     private Boolean ingStatusCd;
     private String ingStatusCdDesc;


    // Constructors

    /** default constructor */
    public Rhicscd0IngStatCdDesc() {
    }

	/** minimal constructor */
    public Rhicscd0IngStatCdDesc(Boolean ingStatusCd) {
        this.ingStatusCd = ingStatusCd;
    }
    
    /** full constructor */
    public Rhicscd0IngStatCdDesc(Boolean ingStatusCd, String ingStatusCdDesc) {
        this.ingStatusCd = ingStatusCd;
        this.ingStatusCdDesc = ingStatusCdDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="ING_STATUS_CD", unique=true, nullable=false, precision=1, scale=0)

    public Boolean getIngStatusCd() {
        return this.ingStatusCd;
    }
    
    public void setIngStatusCd(Boolean ingStatusCd) {
        this.ingStatusCd = ingStatusCd;
    }
    
    @Column(name="ING_STATUS_CD_DESC", length=50)

    public String getIngStatusCdDesc() {
        return this.ingStatusCdDesc;
    }
    
    public void setIngStatusCdDesc(String ingStatusCdDesc) {
        this.ingStatusCdDesc = ingStatusCdDesc;
    }
   








}