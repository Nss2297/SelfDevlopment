package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rrtgnsd0RtdGenStatusDsc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RRTGNSD0_RTD_GEN_STATUS_DSC"
    ,schema="MEDK_FDB"
)

public class Rrtgnsd0RtdGenStatusDsc  implements java.io.Serializable {


    // Fields    

     private String routedGenStatusCd;
     private String routedGenStatusCdDesc;


    // Constructors

    /** default constructor */
    public Rrtgnsd0RtdGenStatusDsc() {
    }

    
    /** full constructor */
    public Rrtgnsd0RtdGenStatusDsc(String routedGenStatusCd, String routedGenStatusCdDesc) {
        this.routedGenStatusCd = routedGenStatusCd;
        this.routedGenStatusCdDesc = routedGenStatusCdDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="ROUTED_GEN_STATUS_CD", unique=true, nullable=false, length=1)

    public String getRoutedGenStatusCd() {
        return this.routedGenStatusCd;
    }
    
    public void setRoutedGenStatusCd(String routedGenStatusCd) {
        this.routedGenStatusCd = routedGenStatusCd;
    }
    
    @Column(name="ROUTED_GEN_STATUS_CD_DESC", nullable=false, length=30)

    public String getRoutedGenStatusCdDesc() {
        return this.routedGenStatusCdDesc;
    }
    
    public void setRoutedGenStatusCdDesc(String routedGenStatusCdDesc) {
        this.routedGenStatusCdDesc = routedGenStatusCdDesc;
    }
   








}