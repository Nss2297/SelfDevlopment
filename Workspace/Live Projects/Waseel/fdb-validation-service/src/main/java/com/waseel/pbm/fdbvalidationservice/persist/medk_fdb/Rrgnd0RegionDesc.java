package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rrgnd0RegionDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RRGND0_REGION_DESC"
    ,schema="MEDK_FDB"
)

public class Rrgnd0RegionDesc  implements java.io.Serializable {


    // Fields    

     private Integer regionCode;
     private String regionDesc;


    // Constructors

    /** default constructor */
    public Rrgnd0RegionDesc() {
    }

    
    /** full constructor */
    public Rrgnd0RegionDesc(Integer regionCode, String regionDesc) {
        this.regionCode = regionCode;
        this.regionDesc = regionDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="REGION_CODE", unique=true, nullable=false, precision=8, scale=0)

    public Integer getRegionCode() {
        return this.regionCode;
    }
    
    public void setRegionCode(Integer regionCode) {
        this.regionCode = regionCode;
    }
    
    @Column(name="REGION_DESC", nullable=false, length=100)

    public String getRegionDesc() {
        return this.regionDesc;
    }
    
    public void setRegionDesc(String regionDesc) {
        this.regionDesc = regionDesc;
    }
   








}