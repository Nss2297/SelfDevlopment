package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rrouted3RouteDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RROUTED3_ROUTE_DESC"
    ,schema="MEDK_FDB"
)

public class Rrouted3RouteDesc  implements java.io.Serializable {


    // Fields    

     private String gcrt;
     private String rt;
     private String gcrt2;
     private String gcrtDesc;
     private String systemic;


    // Constructors

    /** default constructor */
    public Rrouted3RouteDesc() {
    }

	/** minimal constructor */
    public Rrouted3RouteDesc(String gcrt) {
        this.gcrt = gcrt;
    }
    
    /** full constructor */
    public Rrouted3RouteDesc(String gcrt, String rt, String gcrt2, String gcrtDesc, String systemic) {
        this.gcrt = gcrt;
        this.rt = rt;
        this.gcrt2 = gcrt2;
        this.gcrtDesc = gcrtDesc;
        this.systemic = systemic;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="GCRT", unique=true, nullable=false, length=1)

    public String getGcrt() {
        return this.gcrt;
    }
    
    public void setGcrt(String gcrt) {
        this.gcrt = gcrt;
    }
    
    @Column(name="RT", length=10)

    public String getRt() {
        return this.rt;
    }
    
    public void setRt(String rt) {
        this.rt = rt;
    }
    
    @Column(name="GCRT2", length=2)

    public String getGcrt2() {
        return this.gcrt2;
    }
    
    public void setGcrt2(String gcrt2) {
        this.gcrt2 = gcrt2;
    }
    
    @Column(name="GCRT_DESC", length=40)

    public String getGcrtDesc() {
        return this.gcrtDesc;
    }
    
    public void setGcrtDesc(String gcrtDesc) {
        this.gcrtDesc = gcrtDesc;
    }
    
    @Column(name="SYSTEMIC", length=1)

    public String getSystemic() {
        return this.systemic;
    }
    
    public void setSystemic(String systemic) {
        this.systemic = systemic;
    }
   








}