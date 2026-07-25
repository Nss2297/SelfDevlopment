package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rdosed2DoseDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDOSED2_DOSE_DESC"
    ,schema="MEDK_FDB"
)

public class Rdosed2DoseDesc  implements java.io.Serializable {


    // Fields    

     private String gcdf;
     private String dose;
     private String gcdfDesc;


    // Constructors

    /** default constructor */
    public Rdosed2DoseDesc() {
    }

	/** minimal constructor */
    public Rdosed2DoseDesc(String gcdf) {
        this.gcdf = gcdf;
    }
    
    /** full constructor */
    public Rdosed2DoseDesc(String gcdf, String dose, String gcdfDesc) {
        this.gcdf = gcdf;
        this.dose = dose;
        this.gcdfDesc = gcdfDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="GCDF", unique=true, nullable=false, length=2)

    public String getGcdf() {
        return this.gcdf;
    }
    
    public void setGcdf(String gcdf) {
        this.gcdf = gcdf;
    }
    
    @Column(name="DOSE", length=10)

    public String getDose() {
        return this.dose;
    }
    
    public void setDose(String dose) {
        this.dose = dose;
    }
    
    @Column(name="GCDF_DESC", length=40)

    public String getGcdfDesc() {
        return this.gcdfDesc;
    }
    
    public void setGcdfDesc(String gcdfDesc) {
        this.gcdfDesc = gcdfDesc;
    }
   








}