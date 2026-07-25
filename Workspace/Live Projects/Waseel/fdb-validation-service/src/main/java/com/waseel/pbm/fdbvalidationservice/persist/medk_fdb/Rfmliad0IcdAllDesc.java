package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rfmliad0IcdAllDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RFMLIAD0_ICD_ALL_DESC"
    ,schema="MEDK_FDB"
)

public class Rfmliad0IcdAllDesc  implements java.io.Serializable {


    // Fields    

     private Rfmliad0IcdAllDescId id;
     private String icdDesc;


    // Constructors

    /** default constructor */
    public Rfmliad0IcdAllDesc() {
    }

    
    /** full constructor */
    public Rfmliad0IcdAllDesc(Rfmliad0IcdAllDescId id, String icdDesc) {
        this.id = id;
        this.icdDesc = icdDesc;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="icdCd", column=@Column(name="ICD_CD", nullable=false, length=10) ), 
        @AttributeOverride(name="icdCdType", column=@Column(name="ICD_CD_TYPE", nullable=false, length=2) ), 
        @AttributeOverride(name="icdDescSourceCd", column=@Column(name="ICD_DESC_SOURCE_CD", nullable=false, length=2) ) } )

    public Rfmliad0IcdAllDescId getId() {
        return this.id;
    }
    
    public void setId(Rfmliad0IcdAllDescId id) {
        this.id = id;
    }
    
    @Column(name="ICD_DESC", nullable=false, length=500)

    public String getIcdDesc() {
        return this.icdDesc;
    }
    
    public void setIcdDesc(String icdDesc) {
        this.icdDesc = icdDesc;
    }
   








}