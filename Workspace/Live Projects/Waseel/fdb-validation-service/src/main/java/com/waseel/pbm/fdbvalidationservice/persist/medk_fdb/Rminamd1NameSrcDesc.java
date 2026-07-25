package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rminamd1NameSrcDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMINAMD1_NAME_SRC_DESC"
    ,schema="MEDK_FDB"
)

public class Rminamd1NameSrcDesc  implements java.io.Serializable {


    // Fields    

     private String medNameSourceCd;
     private String medNameSourceCdDesc;


    // Constructors

    /** default constructor */
    public Rminamd1NameSrcDesc() {
    }

    
    /** full constructor */
    public Rminamd1NameSrcDesc(String medNameSourceCd, String medNameSourceCdDesc) {
        this.medNameSourceCd = medNameSourceCd;
        this.medNameSourceCdDesc = medNameSourceCdDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="MED_NAME_SOURCE_CD", unique=true, nullable=false, length=1)

    public String getMedNameSourceCd() {
        return this.medNameSourceCd;
    }
    
    public void setMedNameSourceCd(String medNameSourceCd) {
        this.medNameSourceCd = medNameSourceCd;
    }
    
    @Column(name="MED_NAME_SOURCE_CD_DESC", nullable=false, length=90)

    public String getMedNameSourceCdDesc() {
        return this.medNameSourceCdDesc;
    }
    
    public void setMedNameSourceCdDesc(String medNameSourceCdDesc) {
        this.medNameSourceCdDesc = medNameSourceCdDesc;
    }
   








}