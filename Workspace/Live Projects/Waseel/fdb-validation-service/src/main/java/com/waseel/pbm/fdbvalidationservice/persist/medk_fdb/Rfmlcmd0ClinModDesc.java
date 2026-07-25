package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rfmlcmd0ClinModDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RFMLCMD0_CLIN_MOD_DESC"
    ,schema="MEDK_FDB"
)

public class Rfmlcmd0ClinModDesc  implements java.io.Serializable {


    // Fields    

     private String fmlClinCode;
     private String fmlClinCodeDesc;


    // Constructors

    /** default constructor */
    public Rfmlcmd0ClinModDesc() {
    }

	/** minimal constructor */
    public Rfmlcmd0ClinModDesc(String fmlClinCode) {
        this.fmlClinCode = fmlClinCode;
    }
    
    /** full constructor */
    public Rfmlcmd0ClinModDesc(String fmlClinCode, String fmlClinCodeDesc) {
        this.fmlClinCode = fmlClinCode;
        this.fmlClinCodeDesc = fmlClinCodeDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="FML_CLIN_CODE", unique=true, nullable=false, length=2)

    public String getFmlClinCode() {
        return this.fmlClinCode;
    }
    
    public void setFmlClinCode(String fmlClinCode) {
        this.fmlClinCode = fmlClinCode;
    }
    
    @Column(name="FML_CLIN_CODE_DESC", length=50)

    public String getFmlClinCodeDesc() {
        return this.fmlClinCodeDesc;
    }
    
    public void setFmlClinCodeDesc(String fmlClinCodeDesc) {
        this.fmlClinCodeDesc = fmlClinCodeDesc;
    }
   








}