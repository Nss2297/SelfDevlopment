package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Runitsd0UnitsDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RUNITSD0_UNITS_DESC"
    ,schema="MEDK_FDB"
)

public class Runitsd0UnitsDesc  implements java.io.Serializable {


    // Fields    

     private String dosingModuleUnitAbbrev;
     private String unitDescAbbrev;
     private String unitDescExpanded;


    // Constructors

    /** default constructor */
    public Runitsd0UnitsDesc() {
    }

	/** minimal constructor */
    public Runitsd0UnitsDesc(String dosingModuleUnitAbbrev) {
        this.dosingModuleUnitAbbrev = dosingModuleUnitAbbrev;
    }
    
    /** full constructor */
    public Runitsd0UnitsDesc(String dosingModuleUnitAbbrev, String unitDescAbbrev, String unitDescExpanded) {
        this.dosingModuleUnitAbbrev = dosingModuleUnitAbbrev;
        this.unitDescAbbrev = unitDescAbbrev;
        this.unitDescExpanded = unitDescExpanded;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="DOSING_MODULE_UNIT_ABBREV", unique=true, nullable=false, length=30)

    public String getDosingModuleUnitAbbrev() {
        return this.dosingModuleUnitAbbrev;
    }
    
    public void setDosingModuleUnitAbbrev(String dosingModuleUnitAbbrev) {
        this.dosingModuleUnitAbbrev = dosingModuleUnitAbbrev;
    }
    
    @Column(name="UNIT_DESC_ABBREV", length=30)

    public String getUnitDescAbbrev() {
        return this.unitDescAbbrev;
    }
    
    public void setUnitDescAbbrev(String unitDescAbbrev) {
        this.unitDescAbbrev = unitDescAbbrev;
    }
    
    @Column(name="UNIT_DESC_EXPANDED", length=60)

    public String getUnitDescExpanded() {
        return this.unitDescExpanded;
    }
    
    public void setUnitDescExpanded(String unitDescExpanded) {
        this.unitDescExpanded = unitDescExpanded;
    }
   








}