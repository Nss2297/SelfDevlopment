package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Radimef0ClinEffect entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RADIMEF0_CLIN_EFFECT"
    ,schema="MEDK_FDB"
)

public class Radimef0ClinEffect  implements java.io.Serializable {


    // Fields    

     private String adiEfftc;
     private String adiEfftxt;


    // Constructors

    /** default constructor */
    public Radimef0ClinEffect() {
    }

	/** minimal constructor */
    public Radimef0ClinEffect(String adiEfftc) {
        this.adiEfftc = adiEfftc;
    }
    
    /** full constructor */
    public Radimef0ClinEffect(String adiEfftc, String adiEfftxt) {
        this.adiEfftc = adiEfftc;
        this.adiEfftxt = adiEfftxt;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="ADI_EFFTC", unique=true, nullable=false, length=3)

    public String getAdiEfftc() {
        return this.adiEfftc;
    }
    
    public void setAdiEfftc(String adiEfftc) {
        this.adiEfftc = adiEfftc;
    }
    
    @Column(name="ADI_EFFTXT", length=50)

    public String getAdiEfftxt() {
        return this.adiEfftxt;
    }
    
    public void setAdiEfftxt(String adiEfftxt) {
        this.adiEfftxt = adiEfftxt;
    }
   








}