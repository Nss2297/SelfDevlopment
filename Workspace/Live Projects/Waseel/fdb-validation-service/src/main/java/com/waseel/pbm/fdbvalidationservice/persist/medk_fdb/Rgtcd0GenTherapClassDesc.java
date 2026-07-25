package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rgtcd0GenTherapClassDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RGTCD0_GEN_THERAP_CLASS_DESC"
    ,schema="MEDK_FDB"
)

public class Rgtcd0GenTherapClassDesc  implements java.io.Serializable {


    // Fields    

     private Byte gtc;
     private String gtcDesc;


    // Constructors

    /** default constructor */
    public Rgtcd0GenTherapClassDesc() {
    }

	/** minimal constructor */
    public Rgtcd0GenTherapClassDesc(Byte gtc) {
        this.gtc = gtc;
    }
    
    /** full constructor */
    public Rgtcd0GenTherapClassDesc(Byte gtc, String gtcDesc) {
        this.gtc = gtc;
        this.gtcDesc = gtcDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="GTC", unique=true, nullable=false, precision=2, scale=0)

    public Byte getGtc() {
        return this.gtc;
    }
    
    public void setGtc(Byte gtc) {
        this.gtc = gtc;
    }
    
    @Column(name="GTC_DESC", length=50)

    public String getGtcDesc() {
        return this.gtcDesc;
    }
    
    public void setGtcDesc(String gtcDesc) {
        this.gtcDesc = gtcDesc;
    }
   








}