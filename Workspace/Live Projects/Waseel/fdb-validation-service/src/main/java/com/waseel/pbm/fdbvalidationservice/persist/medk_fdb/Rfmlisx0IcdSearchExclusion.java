package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rfmlisx0IcdSearchExclusion entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RFMLISX0_ICD_SEARCH_EXCLUSION"
    ,schema="MEDK_FDB"
)

public class Rfmlisx0IcdSearchExclusion  implements java.io.Serializable {


    // Fields    

     private Rfmlisx0IcdSearchExclusionId id;


    // Constructors

    /** default constructor */
    public Rfmlisx0IcdSearchExclusion() {
    }

    
    /** full constructor */
    public Rfmlisx0IcdSearchExclusion(Rfmlisx0IcdSearchExclusionId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="searchIcdCd", column=@Column(name="SEARCH_ICD_CD", nullable=false, length=10) ), 
        @AttributeOverride(name="icdCdType", column=@Column(name="ICD_CD_TYPE", nullable=false, length=2) ), 
        @AttributeOverride(name="relatedDxid", column=@Column(name="RELATED_DXID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="fmlClinCode", column=@Column(name="FML_CLIN_CODE", nullable=false, length=2) ), 
        @AttributeOverride(name="clinDrugGroup", column=@Column(name="CLIN_DRUG_GROUP", nullable=false, precision=5, scale=0) ) } )

    public Rfmlisx0IcdSearchExclusionId getId() {
        return this.id;
    }
    
    public void setId(Rfmlisx0IcdSearchExclusionId id) {
        this.id = id;
    }
   








}