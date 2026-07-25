package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rdossrc0DosingAgeSource entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDOSSRC0_DOSING_AGE_SOURCE"
    ,schema="MEDK_FDB"
)

public class Rdossrc0DosingAgeSource  implements java.io.Serializable {


    // Fields    

     private Short dosingAgeSourceId;
     private String dosingAgeSourceDesc;


    // Constructors

    /** default constructor */
    public Rdossrc0DosingAgeSource() {
    }

    
    /** full constructor */
    public Rdossrc0DosingAgeSource(Short dosingAgeSourceId, String dosingAgeSourceDesc) {
        this.dosingAgeSourceId = dosingAgeSourceId;
        this.dosingAgeSourceDesc = dosingAgeSourceDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="DOSING_AGE_SOURCE_ID", unique=true, nullable=false, precision=4, scale=0)

    public Short getDosingAgeSourceId() {
        return this.dosingAgeSourceId;
    }
    
    public void setDosingAgeSourceId(Short dosingAgeSourceId) {
        this.dosingAgeSourceId = dosingAgeSourceId;
    }
    
    @Column(name="DOSING_AGE_SOURCE_DESC", nullable=false, length=50)

    public String getDosingAgeSourceDesc() {
        return this.dosingAgeSourceDesc;
    }
    
    public void setDosingAgeSourceDesc(String dosingAgeSourceDesc) {
        this.dosingAgeSourceDesc = dosingAgeSourceDesc;
    }
   








}