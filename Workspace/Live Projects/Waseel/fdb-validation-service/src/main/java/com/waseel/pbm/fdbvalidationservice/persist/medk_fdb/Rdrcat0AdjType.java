package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rdrcat0AdjType entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDRCAT0_ADJ_TYPE"
    ,schema="MEDK_FDB"
)

public class Rdrcat0AdjType  implements java.io.Serializable {


    // Fields    

     private Short dosingAdjTypeCd;
     private String dosingAdjTypeDesc;


    // Constructors

    /** default constructor */
    public Rdrcat0AdjType() {
    }

    
    /** full constructor */
    public Rdrcat0AdjType(Short dosingAdjTypeCd, String dosingAdjTypeDesc) {
        this.dosingAdjTypeCd = dosingAdjTypeCd;
        this.dosingAdjTypeDesc = dosingAdjTypeDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="DOSING_ADJ_TYPE_CD", unique=true, nullable=false, precision=4, scale=0)

    public Short getDosingAdjTypeCd() {
        return this.dosingAdjTypeCd;
    }
    
    public void setDosingAdjTypeCd(Short dosingAdjTypeCd) {
        this.dosingAdjTypeCd = dosingAdjTypeCd;
    }
    
    @Column(name="DOSING_ADJ_TYPE_DESC", nullable=false, length=70)

    public String getDosingAdjTypeDesc() {
        return this.dosingAdjTypeDesc;
    }
    
    public void setDosingAdjTypeDesc(String dosingAdjTypeDesc) {
        this.dosingAdjTypeDesc = dosingAdjTypeDesc;
    }
   








}