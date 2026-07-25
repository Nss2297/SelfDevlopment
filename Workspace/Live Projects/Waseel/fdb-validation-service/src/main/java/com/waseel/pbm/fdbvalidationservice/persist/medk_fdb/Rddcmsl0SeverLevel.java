package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rddcmsl0SeverLevel entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDDCMSL0_SEVER_LEVEL"
    ,schema="MEDK_FDB"
)

public class Rddcmsl0SeverLevel  implements java.io.Serializable {


    // Fields    

     private String ddxcnSl;
     private String ddxcnSlDesc;


    // Constructors

    /** default constructor */
    public Rddcmsl0SeverLevel() {
    }

    
    /** full constructor */
    public Rddcmsl0SeverLevel(String ddxcnSl, String ddxcnSlDesc) {
        this.ddxcnSl = ddxcnSl;
        this.ddxcnSlDesc = ddxcnSlDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="DDXCN_SL", unique=true, nullable=false, length=1)

    public String getDdxcnSl() {
        return this.ddxcnSl;
    }
    
    public void setDdxcnSl(String ddxcnSl) {
        this.ddxcnSl = ddxcnSl;
    }
    
    @Column(name="DDXCN_SL_DESC", nullable=false)

    public String getDdxcnSlDesc() {
        return this.ddxcnSlDesc;
    }
    
    public void setDdxcnSlDesc(String ddxcnSlDesc) {
        this.ddxcnSlDesc = ddxcnSlDesc;
    }
   








}