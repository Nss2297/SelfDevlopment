package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rmigcnd1GcnseqnoAssgnDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMIGCND1_GCNSEQNO_ASSGN_DESC"
    ,schema="MEDK_FDB"
)

public class Rmigcnd1GcnseqnoAssgnDesc  implements java.io.Serializable {


    // Fields    

     private String medGcnseqnoAssignCd;
     private String medGcnseqnoAssignCdDesc;


    // Constructors

    /** default constructor */
    public Rmigcnd1GcnseqnoAssgnDesc() {
    }

    
    /** full constructor */
    public Rmigcnd1GcnseqnoAssgnDesc(String medGcnseqnoAssignCd, String medGcnseqnoAssignCdDesc) {
        this.medGcnseqnoAssignCd = medGcnseqnoAssignCd;
        this.medGcnseqnoAssignCdDesc = medGcnseqnoAssignCdDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="MED_GCNSEQNO_ASSIGN_CD", unique=true, nullable=false, length=1)

    public String getMedGcnseqnoAssignCd() {
        return this.medGcnseqnoAssignCd;
    }
    
    public void setMedGcnseqnoAssignCd(String medGcnseqnoAssignCd) {
        this.medGcnseqnoAssignCd = medGcnseqnoAssignCd;
    }
    
    @Column(name="MED_GCNSEQNO_ASSIGN_CD_DESC", nullable=false, length=60)

    public String getMedGcnseqnoAssignCdDesc() {
        return this.medGcnseqnoAssignCdDesc;
    }
    
    public void setMedGcnseqnoAssignCdDesc(String medGcnseqnoAssignCdDesc) {
        this.medGcnseqnoAssignCdDesc = medGcnseqnoAssignCdDesc;
    }
   








}