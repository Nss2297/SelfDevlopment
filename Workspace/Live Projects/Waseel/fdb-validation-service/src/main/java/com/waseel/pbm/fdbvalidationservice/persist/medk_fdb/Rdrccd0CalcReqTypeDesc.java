package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rdrccd0CalcReqTypeDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDRCCD0_CALC_REQ_TYPE_DESC"
    ,schema="MEDK_FDB"
)

public class Rdrccd0CalcReqTypeDesc  implements java.io.Serializable {


    // Fields    

     private Boolean unitsCtyp;
     private String unitsCtypDesc;


    // Constructors

    /** default constructor */
    public Rdrccd0CalcReqTypeDesc() {
    }

	/** minimal constructor */
    public Rdrccd0CalcReqTypeDesc(Boolean unitsCtyp) {
        this.unitsCtyp = unitsCtyp;
    }
    
    /** full constructor */
    public Rdrccd0CalcReqTypeDesc(Boolean unitsCtyp, String unitsCtypDesc) {
        this.unitsCtyp = unitsCtyp;
        this.unitsCtypDesc = unitsCtypDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="UNITS_CTYP", unique=true, nullable=false, precision=1, scale=0)

    public Boolean getUnitsCtyp() {
        return this.unitsCtyp;
    }
    
    public void setUnitsCtyp(Boolean unitsCtyp) {
        this.unitsCtyp = unitsCtyp;
    }
    
    @Column(name="UNITS_CTYP_DESC", length=50)

    public String getUnitsCtypDesc() {
        return this.unitsCtypDesc;
    }
    
    public void setUnitsCtypDesc(String unitsCtypDesc) {
        this.unitsCtypDesc = unitsCtypDesc;
    }
   








}