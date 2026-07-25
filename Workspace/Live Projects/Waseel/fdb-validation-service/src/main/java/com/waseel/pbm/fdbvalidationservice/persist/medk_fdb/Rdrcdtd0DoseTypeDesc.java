package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rdrcdtd0DoseTypeDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDRCDTD0_DOSE_TYPE_DESC"
    ,schema="MEDK_FDB"
)

public class Rdrcdtd0DoseTypeDesc  implements java.io.Serializable {


    // Fields    

     private String dr2Dostpi;
     private String dostpiDes;


    // Constructors

    /** default constructor */
    public Rdrcdtd0DoseTypeDesc() {
    }

	/** minimal constructor */
    public Rdrcdtd0DoseTypeDesc(String dr2Dostpi) {
        this.dr2Dostpi = dr2Dostpi;
    }
    
    /** full constructor */
    public Rdrcdtd0DoseTypeDesc(String dr2Dostpi, String dostpiDes) {
        this.dr2Dostpi = dr2Dostpi;
        this.dostpiDes = dostpiDes;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="DR2_DOSTPI", unique=true, nullable=false, length=2)

    public String getDr2Dostpi() {
        return this.dr2Dostpi;
    }
    
    public void setDr2Dostpi(String dr2Dostpi) {
        this.dr2Dostpi = dr2Dostpi;
    }
    
    @Column(name="DOSTPI_DES", length=25)

    public String getDostpiDes() {
        return this.dostpiDes;
    }
    
    public void setDostpiDes(String dostpiDes) {
        this.dostpiDes = dostpiDes;
    }
   








}