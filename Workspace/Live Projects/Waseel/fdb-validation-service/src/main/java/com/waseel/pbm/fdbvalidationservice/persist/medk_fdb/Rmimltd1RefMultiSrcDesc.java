package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rmimltd1RefMultiSrcDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMIMLTD1_REF_MULTI_SRC_DESC"
    ,schema="MEDK_FDB"
)

public class Rmimltd1RefMultiSrcDesc  implements java.io.Serializable {


    // Fields    

     private String medRefMultiSourceCd;
     private String medRefMultiSourceCdDesc;


    // Constructors

    /** default constructor */
    public Rmimltd1RefMultiSrcDesc() {
    }

    
    /** full constructor */
    public Rmimltd1RefMultiSrcDesc(String medRefMultiSourceCd, String medRefMultiSourceCdDesc) {
        this.medRefMultiSourceCd = medRefMultiSourceCd;
        this.medRefMultiSourceCdDesc = medRefMultiSourceCdDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="MED_REF_MULTI_SOURCE_CD", unique=true, nullable=false, length=1)

    public String getMedRefMultiSourceCd() {
        return this.medRefMultiSourceCd;
    }
    
    public void setMedRefMultiSourceCd(String medRefMultiSourceCd) {
        this.medRefMultiSourceCd = medRefMultiSourceCd;
    }
    
    @Column(name="MED_REF_MULTI_SOURCE_CD_DESC", nullable=false, length=90)

    public String getMedRefMultiSourceCdDesc() {
        return this.medRefMultiSourceCdDesc;
    }
    
    public void setMedRefMultiSourceCdDesc(String medRefMultiSourceCdDesc) {
        this.medRefMultiSourceCdDesc = medRefMultiSourceCdDesc;
    }
   








}