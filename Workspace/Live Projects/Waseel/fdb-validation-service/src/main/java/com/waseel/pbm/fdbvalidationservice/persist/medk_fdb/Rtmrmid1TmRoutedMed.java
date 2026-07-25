package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rtmrmid1TmRoutedMed entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RTMRMID1_TM_ROUTED_MED"
    ,schema="MEDK_FDB"
)

public class Rtmrmid1TmRoutedMed  implements java.io.Serializable {


    // Fields    

     private Integer routedMedId;
     private Integer tmSourceId;
     private Boolean tmInd;
     private String tmAltRoutedMedIdDesc;


    // Constructors

    /** default constructor */
    public Rtmrmid1TmRoutedMed() {
    }

	/** minimal constructor */
    public Rtmrmid1TmRoutedMed(Integer routedMedId) {
        this.routedMedId = routedMedId;
    }
    
    /** full constructor */
    public Rtmrmid1TmRoutedMed(Integer routedMedId, Integer tmSourceId, Boolean tmInd, String tmAltRoutedMedIdDesc) {
        this.routedMedId = routedMedId;
        this.tmSourceId = tmSourceId;
        this.tmInd = tmInd;
        this.tmAltRoutedMedIdDesc = tmAltRoutedMedIdDesc;
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
    
    @Column(name="TM_SOURCE_ID", precision=5, scale=0)

    public Integer getTmSourceId() {
        return this.tmSourceId;
    }
    
    public void setTmSourceId(Integer tmSourceId) {
        this.tmSourceId = tmSourceId;
    }
    
    @Column(name="TM_IND", precision=1, scale=0)

    public Boolean getTmInd() {
        return this.tmInd;
    }
    
    public void setTmInd(Boolean tmInd) {
        this.tmInd = tmInd;
    }
    
    @Column(name="TM_ALT_ROUTED_MED_ID_DESC", length=70)

    public String getTmAltRoutedMedIdDesc() {
        return this.tmAltRoutedMedIdDesc;
    }
    
    public void setTmAltRoutedMedIdDesc(String tmAltRoutedMedIdDesc) {
        this.tmAltRoutedMedIdDesc = tmAltRoutedMedIdDesc;
    }
   








}