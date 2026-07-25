package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rmidfd1DoseForm entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMIDFD1_DOSE_FORM"
    ,schema="MEDK_FDB"
)

public class Rmidfd1DoseForm  implements java.io.Serializable {


    // Fields    

     private Integer medDosageFormId;
     private String medDosageFormAbbr;
     private String medDosageFormDesc;


    // Constructors

    /** default constructor */
    public Rmidfd1DoseForm() {
    }

    
    /** full constructor */
    public Rmidfd1DoseForm(Integer medDosageFormId, String medDosageFormAbbr, String medDosageFormDesc) {
        this.medDosageFormId = medDosageFormId;
        this.medDosageFormAbbr = medDosageFormAbbr;
        this.medDosageFormDesc = medDosageFormDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="MED_DOSAGE_FORM_ID", unique=true, nullable=false, precision=5, scale=0)

    public Integer getMedDosageFormId() {
        return this.medDosageFormId;
    }
    
    public void setMedDosageFormId(Integer medDosageFormId) {
        this.medDosageFormId = medDosageFormId;
    }
    
    @Column(name="MED_DOSAGE_FORM_ABBR", nullable=false, length=4)

    public String getMedDosageFormAbbr() {
        return this.medDosageFormAbbr;
    }
    
    public void setMedDosageFormAbbr(String medDosageFormAbbr) {
        this.medDosageFormAbbr = medDosageFormAbbr;
    }
    
    @Column(name="MED_DOSAGE_FORM_DESC", nullable=false, length=30)

    public String getMedDosageFormDesc() {
        return this.medDosageFormDesc;
    }
    
    public void setMedDosageFormDesc(String medDosageFormDesc) {
        this.medDosageFormDesc = medDosageFormDesc;
    }
   








}