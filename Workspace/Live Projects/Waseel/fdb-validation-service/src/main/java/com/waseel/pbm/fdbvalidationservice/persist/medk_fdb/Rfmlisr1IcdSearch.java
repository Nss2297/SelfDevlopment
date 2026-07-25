package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rfmlisr1IcdSearch entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RFMLISR1_ICD_SEARCH"
    ,schema="MEDK_FDB"
)

public class Rfmlisr1IcdSearch  implements java.io.Serializable {


    // Fields    

     private Rfmlisr1IcdSearchId id;
     private String fmlNavCode;


    // Constructors

    /** default constructor */
    public Rfmlisr1IcdSearch() {
    }

    
    /** full constructor */
    public Rfmlisr1IcdSearch(Rfmlisr1IcdSearchId id, String fmlNavCode) {
        this.id = id;
        this.fmlNavCode = fmlNavCode;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="searchIcdCd", column=@Column(name="SEARCH_ICD_CD", nullable=false, length=10) ), 
        @AttributeOverride(name="icdCdType", column=@Column(name="ICD_CD_TYPE", nullable=false, length=2) ), 
        @AttributeOverride(name="relatedDxid", column=@Column(name="RELATED_DXID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="fmlClinCode", column=@Column(name="FML_CLIN_CODE", nullable=false, length=2) ) } )

    public Rfmlisr1IcdSearchId getId() {
        return this.id;
    }
    
    public void setId(Rfmlisr1IcdSearchId id) {
        this.id = id;
    }
    
    @Column(name="FML_NAV_CODE", nullable=false, length=2)

    public String getFmlNavCode() {
        return this.fmlNavCode;
    }
    
    public void setFmlNavCode(String fmlNavCode) {
        this.fmlNavCode = fmlNavCode;
    }
   








}