package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rdrcex0Exclusions entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDRCEX0_EXCLUSIONS"
    ,schema="MEDK_FDB"
)

public class Rdrcex0Exclusions  implements java.io.Serializable {


    // Fields    

     private Integer gcnSeqno;
     private Boolean exclusionCode;


    // Constructors

    /** default constructor */
    public Rdrcex0Exclusions() {
    }

    
    /** full constructor */
    public Rdrcex0Exclusions(Integer gcnSeqno, Boolean exclusionCode) {
        this.gcnSeqno = gcnSeqno;
        this.exclusionCode = exclusionCode;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="GCN_SEQNO", unique=true, nullable=false, precision=6, scale=0)

    public Integer getGcnSeqno() {
        return this.gcnSeqno;
    }
    
    public void setGcnSeqno(Integer gcnSeqno) {
        this.gcnSeqno = gcnSeqno;
    }
    
    @Column(name="EXCLUSION_CODE", nullable=false, precision=1, scale=0)

    public Boolean getExclusionCode() {
        return this.exclusionCode;
    }
    
    public void setExclusionCode(Boolean exclusionCode) {
        this.exclusionCode = exclusionCode;
    }
   








}