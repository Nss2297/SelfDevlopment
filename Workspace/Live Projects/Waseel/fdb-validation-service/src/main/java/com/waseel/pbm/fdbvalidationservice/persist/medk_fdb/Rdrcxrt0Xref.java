package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rdrcxrt0Xref entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDRCXRT0_XREF"
    ,schema="MEDK_FDB"
)

public class Rdrcxrt0Xref  implements java.io.Serializable {


    // Fields    

     private String gcrt2;
     private String dr2Rt;


    // Constructors

    /** default constructor */
    public Rdrcxrt0Xref() {
    }

    
    /** full constructor */
    public Rdrcxrt0Xref(String gcrt2, String dr2Rt) {
        this.gcrt2 = gcrt2;
        this.dr2Rt = dr2Rt;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="GCRT2", unique=true, nullable=false, length=2)

    public String getGcrt2() {
        return this.gcrt2;
    }
    
    public void setGcrt2(String gcrt2) {
        this.gcrt2 = gcrt2;
    }
    
    @Column(name="DR2_RT", nullable=false, length=3)

    public String getDr2Rt() {
        return this.dr2Rt;
    }
    
    public void setDr2Rt(String dr2Rt) {
        this.dr2Rt = dr2Rt;
    }
   








}