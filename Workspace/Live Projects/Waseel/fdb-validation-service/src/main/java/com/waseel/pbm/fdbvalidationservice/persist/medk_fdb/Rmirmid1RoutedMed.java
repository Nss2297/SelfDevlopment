package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rmirmid1RoutedMed entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMIRMID1_ROUTED_MED"
    ,schema="MEDK_FDB"
)

public class Rmirmid1RoutedMed  implements java.io.Serializable {


    // Fields    

     private Integer routedMedId;
     private Integer medNameId;
     private Integer medRouteId;
     private String medRoutedMedIdDesc;
     private String medStatusCd;


    // Constructors

    /** default constructor */
    public Rmirmid1RoutedMed() {
    }

    
    /** full constructor */
    public Rmirmid1RoutedMed(Integer routedMedId, Integer medNameId, Integer medRouteId, String medRoutedMedIdDesc, String medStatusCd) {
        this.routedMedId = routedMedId;
        this.medNameId = medNameId;
        this.medRouteId = medRouteId;
        this.medRoutedMedIdDesc = medRoutedMedIdDesc;
        this.medStatusCd = medStatusCd;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="ROUTED_MED_ID", unique=true, nullable=false, precision=8, scale=0)

    public Integer getRoutedMedId() {
        return this.routedMedId;
    }
    
    public void setRoutedMedId(Integer routedMedId) {
        this.routedMedId = routedMedId;
    }
    
    @Column(name="MED_NAME_ID", nullable=false, precision=8, scale=0)

    public Integer getMedNameId() {
        return this.medNameId;
    }
    
    public void setMedNameId(Integer medNameId) {
        this.medNameId = medNameId;
    }
    
    @Column(name="MED_ROUTE_ID", nullable=false, precision=5, scale=0)

    public Integer getMedRouteId() {
        return this.medRouteId;
    }
    
    public void setMedRouteId(Integer medRouteId) {
        this.medRouteId = medRouteId;
    }
    
    @Column(name="MED_ROUTED_MED_ID_DESC", nullable=false, length=60)

    public String getMedRoutedMedIdDesc() {
        return this.medRoutedMedIdDesc;
    }
    
    public void setMedRoutedMedIdDesc(String medRoutedMedIdDesc) {
        this.medRoutedMedIdDesc = medRoutedMedIdDesc;
    }
    
    @Column(name="MED_STATUS_CD", nullable=false, length=1)

    public String getMedStatusCd() {
        return this.medStatusCd;
    }
    
    public void setMedStatusCd(String medStatusCd) {
        this.medStatusCd = medStatusCd;
    }
   








}