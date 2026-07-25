package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rtmdfid1TmRoutedDfMed entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RTMDFID1_TM_ROUTED_DF_MED"
    ,schema="MEDK_FDB"
)

public class Rtmdfid1TmRoutedDfMed  implements java.io.Serializable {


    // Fields    

     private Integer routedDosageFormMedId;
     private Integer tmSourceId;
     private Boolean tmInd;
     private String tmAltRoutedDfMedIdDesc;


    // Constructors

    /** default constructor */
    public Rtmdfid1TmRoutedDfMed() {
    }

	/** minimal constructor */
    public Rtmdfid1TmRoutedDfMed(Integer routedDosageFormMedId) {
        this.routedDosageFormMedId = routedDosageFormMedId;
    }
    
    /** full constructor */
    public Rtmdfid1TmRoutedDfMed(Integer routedDosageFormMedId, Integer tmSourceId, Boolean tmInd, String tmAltRoutedDfMedIdDesc) {
        this.routedDosageFormMedId = routedDosageFormMedId;
        this.tmSourceId = tmSourceId;
        this.tmInd = tmInd;
        this.tmAltRoutedDfMedIdDesc = tmAltRoutedDfMedIdDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="ROUTED_DOSAGE_FORM_MED_ID", unique=true, nullable=false, precision=8, scale=0)

    public Integer getRoutedDosageFormMedId() {
        return this.routedDosageFormMedId;
    }
    
    public void setRoutedDosageFormMedId(Integer routedDosageFormMedId) {
        this.routedDosageFormMedId = routedDosageFormMedId;
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
    
    @Column(name="TM_ALT_ROUTED_DF_MED_ID_DESC", length=70)

    public String getTmAltRoutedDfMedIdDesc() {
        return this.tmAltRoutedDfMedIdDesc;
    }
    
    public void setTmAltRoutedDfMedIdDesc(String tmAltRoutedDfMedIdDesc) {
        this.tmAltRoutedDfMedIdDesc = tmAltRoutedDfMedIdDesc;
    }
   








}