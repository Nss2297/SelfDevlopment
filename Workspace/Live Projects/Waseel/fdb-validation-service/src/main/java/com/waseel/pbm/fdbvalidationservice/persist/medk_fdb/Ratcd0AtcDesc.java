package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Ratcd0AtcDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RATCD0_ATC_DESC"
    ,schema="MEDK_FDB"
)

public class Ratcd0AtcDesc  implements java.io.Serializable {


    // Fields    

     private String atc;
     private String atcDesc;


    // Constructors

    /** default constructor */
    public Ratcd0AtcDesc() {
    }

	/** minimal constructor */
    public Ratcd0AtcDesc(String atc) {
        this.atc = atc;
    }
    
    /** full constructor */
    public Ratcd0AtcDesc(String atc, String atcDesc) {
        this.atc = atc;
        this.atcDesc = atcDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="ATC", unique=true, nullable=false, length=7)

    public String getAtc() {
        return this.atc;
    }
    
    public void setAtc(String atc) {
        this.atc = atc;
    }
    
    @Column(name="ATC_DESC", length=50)

    public String getAtcDesc() {
        return this.atcDesc;
    }
    
    public void setAtcDesc(String atcDesc) {
        this.atcDesc = atcDesc;
    }
   








}