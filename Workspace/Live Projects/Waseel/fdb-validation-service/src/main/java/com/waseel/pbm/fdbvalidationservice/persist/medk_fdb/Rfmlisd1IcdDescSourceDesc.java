package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rfmlisd1IcdDescSourceDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RFMLISD1_ICD_DESC_SOURCE_DESC"
    ,schema="MEDK_FDB"
)

public class Rfmlisd1IcdDescSourceDesc  implements java.io.Serializable {


    // Fields    

     private String icdDescSourceCd;
     private String icdDescSourceDesc;


    // Constructors

    /** default constructor */
    public Rfmlisd1IcdDescSourceDesc() {
    }

    
    /** full constructor */
    public Rfmlisd1IcdDescSourceDesc(String icdDescSourceCd, String icdDescSourceDesc) {
        this.icdDescSourceCd = icdDescSourceCd;
        this.icdDescSourceDesc = icdDescSourceDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="ICD_DESC_SOURCE_CD", unique=true, nullable=false, length=2)

    public String getIcdDescSourceCd() {
        return this.icdDescSourceCd;
    }
    
    public void setIcdDescSourceCd(String icdDescSourceCd) {
        this.icdDescSourceCd = icdDescSourceCd;
    }
    
    @Column(name="ICD_DESC_SOURCE_DESC", nullable=false, length=50)

    public String getIcdDescSourceDesc() {
        return this.icdDescSourceDesc;
    }
    
    public void setIcdDescSourceDesc(String icdDescSourceDesc) {
        this.icdDescSourceDesc = icdDescSourceDesc;
    }
   








}