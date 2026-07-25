package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rdamcd0PicklistConTypDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDAMCD0_PICKLIST_CON_TYP_DESC"
    ,schema="MEDK_FDB"
)

public class Rdamcd0PicklistConTypDesc  implements java.io.Serializable {


    // Fields    

     private Short damConceptIdTyp;
     private String damConceptIdTypDesc;


    // Constructors

    /** default constructor */
    public Rdamcd0PicklistConTypDesc() {
    }

	/** minimal constructor */
    public Rdamcd0PicklistConTypDesc(Short damConceptIdTyp) {
        this.damConceptIdTyp = damConceptIdTyp;
    }
    
    /** full constructor */
    public Rdamcd0PicklistConTypDesc(Short damConceptIdTyp, String damConceptIdTypDesc) {
        this.damConceptIdTyp = damConceptIdTyp;
        this.damConceptIdTypDesc = damConceptIdTypDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="DAM_CONCEPT_ID_TYP", unique=true, nullable=false, precision=3, scale=0)

    public Short getDamConceptIdTyp() {
        return this.damConceptIdTyp;
    }
    
    public void setDamConceptIdTyp(Short damConceptIdTyp) {
        this.damConceptIdTyp = damConceptIdTyp;
    }
    
    @Column(name="DAM_CONCEPT_ID_TYP_DESC", length=50)

    public String getDamConceptIdTypDesc() {
        return this.damConceptIdTypDesc;
    }
    
    public void setDamConceptIdTypDesc(String damConceptIdTypDesc) {
        this.damConceptIdTypDesc = damConceptIdTypDesc;
    }
   








}