package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rdrcrtd0RouteDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDRCRTD0_ROUTE_DESC"
    ,schema="MEDK_FDB"
)

public class Rdrcrtd0RouteDesc  implements java.io.Serializable {


    // Fields    

     private String dr2Rt;
     private String routesDes;


    // Constructors

    /** default constructor */
    public Rdrcrtd0RouteDesc() {
    }

	/** minimal constructor */
    public Rdrcrtd0RouteDesc(String dr2Rt) {
        this.dr2Rt = dr2Rt;
    }
    
    /** full constructor */
    public Rdrcrtd0RouteDesc(String dr2Rt, String routesDes) {
        this.dr2Rt = dr2Rt;
        this.routesDes = routesDes;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="DR2_RT", unique=true, nullable=false, length=3)

    public String getDr2Rt() {
        return this.dr2Rt;
    }
    
    public void setDr2Rt(String dr2Rt) {
        this.dr2Rt = dr2Rt;
    }
    
    @Column(name="ROUTES_DES", length=22)

    public String getRoutesDes() {
        return this.routesDes;
    }
    
    public void setRoutesDes(String routesDes) {
        this.routesDes = routesDes;
    }
   








}