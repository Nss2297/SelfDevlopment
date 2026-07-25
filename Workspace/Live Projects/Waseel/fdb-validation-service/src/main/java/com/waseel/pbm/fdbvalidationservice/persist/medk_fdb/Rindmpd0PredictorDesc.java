package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rindmpd0PredictorDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RINDMPD0_PREDICTOR_DESC"
    ,schema="MEDK_FDB"
)

public class Rindmpd0PredictorDesc  implements java.io.Serializable {


    // Fields    

     private String predCode;
     private String preddesc;


    // Constructors

    /** default constructor */
    public Rindmpd0PredictorDesc() {
    }

	/** minimal constructor */
    public Rindmpd0PredictorDesc(String predCode) {
        this.predCode = predCode;
    }
    
    /** full constructor */
    public Rindmpd0PredictorDesc(String predCode, String preddesc) {
        this.predCode = predCode;
        this.preddesc = preddesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="PRED_CODE", unique=true, nullable=false, length=1)

    public String getPredCode() {
        return this.predCode;
    }
    
    public void setPredCode(String predCode) {
        this.predCode = predCode;
    }
    
    @Column(name="PREDDESC", length=90)

    public String getPreddesc() {
        return this.preddesc;
    }
    
    public void setPreddesc(String preddesc) {
        this.preddesc = preddesc;
    }
   








}