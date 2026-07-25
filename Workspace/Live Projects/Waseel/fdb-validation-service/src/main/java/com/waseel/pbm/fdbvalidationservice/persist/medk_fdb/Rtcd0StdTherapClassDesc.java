package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rtcd0StdTherapClassDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RTCD0_STD_THERAP_CLASS_DESC"
    ,schema="MEDK_FDB"
)

public class Rtcd0StdTherapClassDesc  implements java.io.Serializable {


    // Fields    

     private Byte tc;
     private String tcDesc;


    // Constructors

    /** default constructor */
    public Rtcd0StdTherapClassDesc() {
    }

	/** minimal constructor */
    public Rtcd0StdTherapClassDesc(Byte tc) {
        this.tc = tc;
    }
    
    /** full constructor */
    public Rtcd0StdTherapClassDesc(Byte tc, String tcDesc) {
        this.tc = tc;
        this.tcDesc = tcDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="TC", unique=true, nullable=false, precision=2, scale=0)

    public Byte getTc() {
        return this.tc;
    }
    
    public void setTc(Byte tc) {
        this.tc = tc;
    }
    
    @Column(name="TC_DESC", length=50)

    public String getTcDesc() {
        return this.tcDesc;
    }
    
    public void setTcDesc(String tcDesc) {
        this.tcDesc = tcDesc;
    }
   








}