package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rmiinnd1RefInnovIndDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMIINND1_REF_INNOV_IND_DESC"
    ,schema="MEDK_FDB"
)

public class Rmiinnd1RefInnovIndDesc  implements java.io.Serializable {


    // Fields    

     private String medRefInnovInd;
     private String medRefInnovIndDesc;


    // Constructors

    /** default constructor */
    public Rmiinnd1RefInnovIndDesc() {
    }

    
    /** full constructor */
    public Rmiinnd1RefInnovIndDesc(String medRefInnovInd, String medRefInnovIndDesc) {
        this.medRefInnovInd = medRefInnovInd;
        this.medRefInnovIndDesc = medRefInnovIndDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="MED_REF_INNOV_IND", unique=true, nullable=false, length=1)

    public String getMedRefInnovInd() {
        return this.medRefInnovInd;
    }
    
    public void setMedRefInnovInd(String medRefInnovInd) {
        this.medRefInnovInd = medRefInnovInd;
    }
    
    @Column(name="MED_REF_INNOV_IND_DESC", nullable=false, length=90)

    public String getMedRefInnovIndDesc() {
        return this.medRefInnovIndDesc;
    }
    
    public void setMedRefInnovIndDesc(String medRefInnovIndDesc) {
        this.medRefInnovIndDesc = medRefInnovIndDesc;
    }
   








}