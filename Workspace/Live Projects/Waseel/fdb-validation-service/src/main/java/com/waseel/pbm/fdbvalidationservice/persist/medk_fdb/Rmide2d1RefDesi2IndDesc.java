package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rmide2d1RefDesi2IndDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMIDE2D1_REF_DESI2_IND_DESC"
    ,schema="MEDK_FDB"
)

public class Rmide2d1RefDesi2IndDesc  implements java.io.Serializable {


    // Fields    

     private String medRefDesi2Ind;
     private String medRefDesi2IndDesc;


    // Constructors

    /** default constructor */
    public Rmide2d1RefDesi2IndDesc() {
    }

    
    /** full constructor */
    public Rmide2d1RefDesi2IndDesc(String medRefDesi2Ind, String medRefDesi2IndDesc) {
        this.medRefDesi2Ind = medRefDesi2Ind;
        this.medRefDesi2IndDesc = medRefDesi2IndDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="MED_REF_DESI2_IND", unique=true, nullable=false, length=1)

    public String getMedRefDesi2Ind() {
        return this.medRefDesi2Ind;
    }
    
    public void setMedRefDesi2Ind(String medRefDesi2Ind) {
        this.medRefDesi2Ind = medRefDesi2Ind;
    }
    
    @Column(name="MED_REF_DESI2_IND_DESC", nullable=false, length=60)

    public String getMedRefDesi2IndDesc() {
        return this.medRefDesi2IndDesc;
    }
    
    public void setMedRefDesi2IndDesc(String medRefDesi2IndDesc) {
        this.medRefDesi2IndDesc = medRefDesi2IndDesc;
    }
   








}