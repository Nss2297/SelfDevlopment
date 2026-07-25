package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rdrcdd0DoseCalcDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDRCDD0_DOSE_CALC_DESC"
    ,schema="MEDK_FDB"
)

public class Rdrcdd0DoseCalcDesc  implements java.io.Serializable {


    // Fields    

     private String unitsDcc;
     private String unitsDccDesc;


    // Constructors

    /** default constructor */
    public Rdrcdd0DoseCalcDesc() {
    }

	/** minimal constructor */
    public Rdrcdd0DoseCalcDesc(String unitsDcc) {
        this.unitsDcc = unitsDcc;
    }
    
    /** full constructor */
    public Rdrcdd0DoseCalcDesc(String unitsDcc, String unitsDccDesc) {
        this.unitsDcc = unitsDcc;
        this.unitsDccDesc = unitsDccDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="UNITS_DCC", unique=true, nullable=false, length=1)

    public String getUnitsDcc() {
        return this.unitsDcc;
    }
    
    public void setUnitsDcc(String unitsDcc) {
        this.unitsDcc = unitsDcc;
    }
    
    @Column(name="UNITS_DCC_DESC", length=50)

    public String getUnitsDccDesc() {
        return this.unitsDccDesc;
    }
    
    public void setUnitsDccDesc(String unitsDccDesc) {
        this.unitsDccDesc = unitsDccDesc;
    }
   








}