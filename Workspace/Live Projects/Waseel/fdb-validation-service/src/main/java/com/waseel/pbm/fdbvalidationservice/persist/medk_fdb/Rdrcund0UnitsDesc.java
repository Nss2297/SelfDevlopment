package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rdrcund0UnitsDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDRCUND0_UNITS_DESC"
    ,schema="MEDK_FDB"
)

public class Rdrcund0UnitsDesc  implements java.io.Serializable {


    // Fields    

     private String dr2Units;
     private String unitsDesc;
     private String unitsCtyp;
     private String unitsDcc;
     private String unitsRui;


    // Constructors

    /** default constructor */
    public Rdrcund0UnitsDesc() {
    }

	/** minimal constructor */
    public Rdrcund0UnitsDesc(String dr2Units) {
        this.dr2Units = dr2Units;
    }
    
    /** full constructor */
    public Rdrcund0UnitsDesc(String dr2Units, String unitsDesc, String unitsCtyp, String unitsDcc, String unitsRui) {
        this.dr2Units = dr2Units;
        this.unitsDesc = unitsDesc;
        this.unitsCtyp = unitsCtyp;
        this.unitsDcc = unitsDcc;
        this.unitsRui = unitsRui;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="DR2_UNITS", unique=true, nullable=false, length=2)

    public String getDr2Units() {
        return this.dr2Units;
    }
    
    public void setDr2Units(String dr2Units) {
        this.dr2Units = dr2Units;
    }
    
    @Column(name="UNITS_DESC", length=12)

    public String getUnitsDesc() {
        return this.unitsDesc;
    }
    
    public void setUnitsDesc(String unitsDesc) {
        this.unitsDesc = unitsDesc;
    }
    
    @Column(name="UNITS_CTYP", length=1)

    public String getUnitsCtyp() {
        return this.unitsCtyp;
    }
    
    public void setUnitsCtyp(String unitsCtyp) {
        this.unitsCtyp = unitsCtyp;
    }
    
    @Column(name="UNITS_DCC", length=1)

    public String getUnitsDcc() {
        return this.unitsDcc;
    }
    
    public void setUnitsDcc(String unitsDcc) {
        this.unitsDcc = unitsDcc;
    }
    
    @Column(name="UNITS_RUI", length=2)

    public String getUnitsRui() {
        return this.unitsRui;
    }
    
    public void setUnitsRui(String unitsRui) {
        this.unitsRui = unitsRui;
    }
   








}