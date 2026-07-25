package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rmirtd1Route entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMIRTD1_ROUTE"
    ,schema="MEDK_FDB"
)

public class Rmirtd1Route  implements java.io.Serializable {


    // Fields    

     private Integer medRouteId;
     private String medRouteAbbr;
     private String medRouteDesc;


    // Constructors

    /** default constructor */
    public Rmirtd1Route() {
    }

    
    /** full constructor */
    public Rmirtd1Route(Integer medRouteId, String medRouteAbbr, String medRouteDesc) {
        this.medRouteId = medRouteId;
        this.medRouteAbbr = medRouteAbbr;
        this.medRouteDesc = medRouteDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="MED_ROUTE_ID", unique=true, nullable=false, precision=5, scale=0)

    public Integer getMedRouteId() {
        return this.medRouteId;
    }
    
    public void setMedRouteId(Integer medRouteId) {
        this.medRouteId = medRouteId;
    }
    
    @Column(name="MED_ROUTE_ABBR", nullable=false, length=4)

    public String getMedRouteAbbr() {
        return this.medRouteAbbr;
    }
    
    public void setMedRouteAbbr(String medRouteAbbr) {
        this.medRouteAbbr = medRouteAbbr;
    }
    
    @Column(name="MED_ROUTE_DESC", nullable=false, length=30)

    public String getMedRouteDesc() {
        return this.medRouteDesc;
    }
    
    public void setMedRouteDesc(String medRouteDesc) {
        this.medRouteDesc = medRouteDesc;
    }
   








}