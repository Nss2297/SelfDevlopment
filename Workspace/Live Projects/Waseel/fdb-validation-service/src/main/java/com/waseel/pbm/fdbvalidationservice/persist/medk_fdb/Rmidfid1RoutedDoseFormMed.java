package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rmidfid1RoutedDoseFormMed entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMIDFID1_ROUTED_DOSE_FORM_MED"
    ,schema="MEDK_FDB"
)

public class Rmidfid1RoutedDoseFormMed  implements java.io.Serializable {


    // Fields    

     private Integer routedDosageFormMedId;
     private Integer routedMedId;
     private Integer medDosageFormId;
     private String medRoutedDfMedIdDesc;
     private String medStatusCd;


    // Constructors

    /** default constructor */
    public Rmidfid1RoutedDoseFormMed() {
    }

    
    /** full constructor */
    public Rmidfid1RoutedDoseFormMed(Integer routedDosageFormMedId, Integer routedMedId, Integer medDosageFormId, String medRoutedDfMedIdDesc, String medStatusCd) {
        this.routedDosageFormMedId = routedDosageFormMedId;
        this.routedMedId = routedMedId;
        this.medDosageFormId = medDosageFormId;
        this.medRoutedDfMedIdDesc = medRoutedDfMedIdDesc;
        this.medStatusCd = medStatusCd;
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
    
    @Column(name="ROUTED_MED_ID", nullable=false, precision=8, scale=0)

    public Integer getRoutedMedId() {
        return this.routedMedId;
    }
    
    public void setRoutedMedId(Integer routedMedId) {
        this.routedMedId = routedMedId;
    }
    
    @Column(name="MED_DOSAGE_FORM_ID", nullable=false, precision=5, scale=0)

    public Integer getMedDosageFormId() {
        return this.medDosageFormId;
    }
    
    public void setMedDosageFormId(Integer medDosageFormId) {
        this.medDosageFormId = medDosageFormId;
    }
    
    @Column(name="MED_ROUTED_DF_MED_ID_DESC", nullable=false, length=60)

    public String getMedRoutedDfMedIdDesc() {
        return this.medRoutedDfMedIdDesc;
    }
    
    public void setMedRoutedDfMedIdDesc(String medRoutedDfMedIdDesc) {
        this.medRoutedDfMedIdDesc = medRoutedDfMedIdDesc;
    }
    
    @Column(name="MED_STATUS_CD", nullable=false, length=1)

    public String getMedStatusCd() {
        return this.medStatusCd;
    }
    
    public void setMedStatusCd(String medStatusCd) {
        this.medStatusCd = medStatusCd;
    }
   








}