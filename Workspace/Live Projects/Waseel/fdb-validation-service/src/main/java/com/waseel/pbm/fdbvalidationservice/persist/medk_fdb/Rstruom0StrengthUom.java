package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rstruom0StrengthUom entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RSTRUOM0_STRENGTH_UOM"
    ,schema="MEDK_FDB"
)

public class Rstruom0StrengthUom  implements java.io.Serializable {


    // Fields    

     private Integer uomId;
     private String uomDesc;
     private String uomAbbr;
     private String uomPreferredDesc;


    // Constructors

    /** default constructor */
    public Rstruom0StrengthUom() {
    }

	/** minimal constructor */
    public Rstruom0StrengthUom(Integer uomId) {
        this.uomId = uomId;
    }
    
    /** full constructor */
    public Rstruom0StrengthUom(Integer uomId, String uomDesc, String uomAbbr, String uomPreferredDesc) {
        this.uomId = uomId;
        this.uomDesc = uomDesc;
        this.uomAbbr = uomAbbr;
        this.uomPreferredDesc = uomPreferredDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="UOM_ID", unique=true, nullable=false, precision=8, scale=0)

    public Integer getUomId() {
        return this.uomId;
    }
    
    public void setUomId(Integer uomId) {
        this.uomId = uomId;
    }
    
    @Column(name="UOM_DESC", length=50)

    public String getUomDesc() {
        return this.uomDesc;
    }
    
    public void setUomDesc(String uomDesc) {
        this.uomDesc = uomDesc;
    }
    
    @Column(name="UOM_ABBR", length=10)

    public String getUomAbbr() {
        return this.uomAbbr;
    }
    
    public void setUomAbbr(String uomAbbr) {
        this.uomAbbr = uomAbbr;
    }
    
    @Column(name="UOM_PREFERRED_DESC", length=50)

    public String getUomPreferredDesc() {
        return this.uomPreferredDesc;
    }
    
    public void setUomPreferredDesc(String uomPreferredDesc) {
        this.uomPreferredDesc = uomPreferredDesc;
    }
   








}