package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rmidesd1RefDesiIndDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMIDESD1_REF_DESI_IND_DESC"
    ,schema="MEDK_FDB"
)

public class Rmidesd1RefDesiIndDesc  implements java.io.Serializable {


    // Fields    

     private String medRefDesiInd;
     private String medRefDesiIndDesc;


    // Constructors

    /** default constructor */
    public Rmidesd1RefDesiIndDesc() {
    }

    
    /** full constructor */
    public Rmidesd1RefDesiIndDesc(String medRefDesiInd, String medRefDesiIndDesc) {
        this.medRefDesiInd = medRefDesiInd;
        this.medRefDesiIndDesc = medRefDesiIndDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="MED_REF_DESI_IND", unique=true, nullable=false, length=1)

    public String getMedRefDesiInd() {
        return this.medRefDesiInd;
    }
    
    public void setMedRefDesiInd(String medRefDesiInd) {
        this.medRefDesiInd = medRefDesiInd;
    }
    
    @Column(name="MED_REF_DESI_IND_DESC", nullable=false, length=60)

    public String getMedRefDesiIndDesc() {
        return this.medRefDesiIndDesc;
    }
    
    public void setMedRefDesiIndDesc(String medRefDesiIndDesc) {
        this.medRefDesiIndDesc = medRefDesiIndDesc;
    }
   








}