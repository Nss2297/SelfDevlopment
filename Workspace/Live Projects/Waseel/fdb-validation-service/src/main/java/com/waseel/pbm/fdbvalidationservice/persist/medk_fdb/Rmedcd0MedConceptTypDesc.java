package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rmedcd0MedConceptTypDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMEDCD0_MED_CONCEPT_TYP_DESC"
    ,schema="MEDK_FDB"
)

public class Rmedcd0MedConceptTypDesc  implements java.io.Serializable {


    // Fields    

     private Boolean medConceptIdTyp;
     private String medConceptIdTypDesc;


    // Constructors

    /** default constructor */
    public Rmedcd0MedConceptTypDesc() {
    }

	/** minimal constructor */
    public Rmedcd0MedConceptTypDesc(Boolean medConceptIdTyp) {
        this.medConceptIdTyp = medConceptIdTyp;
    }
    
    /** full constructor */
    public Rmedcd0MedConceptTypDesc(Boolean medConceptIdTyp, String medConceptIdTypDesc) {
        this.medConceptIdTyp = medConceptIdTyp;
        this.medConceptIdTypDesc = medConceptIdTypDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="MED_CONCEPT_ID_TYP", unique=true, nullable=false, precision=1, scale=0)

    public Boolean getMedConceptIdTyp() {
        return this.medConceptIdTyp;
    }
    
    public void setMedConceptIdTyp(Boolean medConceptIdTyp) {
        this.medConceptIdTyp = medConceptIdTyp;
    }
    
    @Column(name="MED_CONCEPT_ID_TYP_DESC", length=50)

    public String getMedConceptIdTypDesc() {
        return this.medConceptIdTypDesc;
    }
    
    public void setMedConceptIdTypDesc(String medConceptIdTypDesc) {
        this.medConceptIdTypDesc = medConceptIdTypDesc;
    }
   








}