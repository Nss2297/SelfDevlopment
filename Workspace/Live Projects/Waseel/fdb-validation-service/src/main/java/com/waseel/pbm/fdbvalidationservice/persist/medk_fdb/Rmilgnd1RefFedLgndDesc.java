package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rmilgnd1RefFedLgndDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMILGND1_REF_FED_LGND_DESC"
    ,schema="MEDK_FDB"
)

public class Rmilgnd1RefFedLgndDesc  implements java.io.Serializable {


    // Fields    

     private String medRefFedLegendInd;
     private String medRefFedLegendIndDesc;


    // Constructors

    /** default constructor */
    public Rmilgnd1RefFedLgndDesc() {
    }

    
    /** full constructor */
    public Rmilgnd1RefFedLgndDesc(String medRefFedLegendInd, String medRefFedLegendIndDesc) {
        this.medRefFedLegendInd = medRefFedLegendInd;
        this.medRefFedLegendIndDesc = medRefFedLegendIndDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="MED_REF_FED_LEGEND_IND", unique=true, nullable=false, length=1)

    public String getMedRefFedLegendInd() {
        return this.medRefFedLegendInd;
    }
    
    public void setMedRefFedLegendInd(String medRefFedLegendInd) {
        this.medRefFedLegendInd = medRefFedLegendInd;
    }
    
    @Column(name="MED_REF_FED_LEGEND_IND_DESC", nullable=false, length=60)

    public String getMedRefFedLegendIndDesc() {
        return this.medRefFedLegendIndDesc;
    }
    
    public void setMedRefFedLegendIndDesc(String medRefFedLegendIndDesc) {
        this.medRefFedLegendIndDesc = medRefFedLegendIndDesc;
    }
   








}