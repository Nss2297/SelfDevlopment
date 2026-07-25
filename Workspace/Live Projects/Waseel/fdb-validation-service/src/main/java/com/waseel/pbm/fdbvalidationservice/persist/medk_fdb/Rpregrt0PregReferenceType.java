package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rpregrt0PregReferenceType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RPREGRT0_PREG_REFERENCE_TYPE"
    ,schema="MEDK_FDB"
)

public class Rpregrt0PregReferenceType  implements java.io.Serializable {


    // Fields    

     private Integer pregReferenceTypeId;
     private String pregReferenceTypeDesc;


    // Constructors

    /** default constructor */
    public Rpregrt0PregReferenceType() {
    }

    
    /** full constructor */
    public Rpregrt0PregReferenceType(Integer pregReferenceTypeId, String pregReferenceTypeDesc) {
        this.pregReferenceTypeId = pregReferenceTypeId;
        this.pregReferenceTypeDesc = pregReferenceTypeDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="PREG_REFERENCE_TYPE_ID", unique=true, nullable=false, precision=8, scale=0)

    public Integer getPregReferenceTypeId() {
        return this.pregReferenceTypeId;
    }
    
    public void setPregReferenceTypeId(Integer pregReferenceTypeId) {
        this.pregReferenceTypeId = pregReferenceTypeId;
    }
    
    @Column(name="PREG_REFERENCE_TYPE_DESC", nullable=false)

    public String getPregReferenceTypeDesc() {
        return this.pregReferenceTypeDesc;
    }
    
    public void setPregReferenceTypeDesc(String pregReferenceTypeDesc) {
        this.pregReferenceTypeDesc = pregReferenceTypeDesc;
    }
   








}