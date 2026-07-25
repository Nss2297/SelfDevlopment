package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rminmid1MedName entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMINMID1_MED_NAME"
    ,schema="MEDK_FDB"
)

public class Rminmid1MedName  implements java.io.Serializable {


    // Fields    

     private Integer medNameId;
     private String medName;
     private String medNameTypeCd;
     private String medStatusCd;


    // Constructors

    /** default constructor */
    public Rminmid1MedName() {
    }

    
    /** full constructor */
    public Rminmid1MedName(Integer medNameId, String medName, String medNameTypeCd, String medStatusCd) {
        this.medNameId = medNameId;
        this.medName = medName;
        this.medNameTypeCd = medNameTypeCd;
        this.medStatusCd = medStatusCd;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="MED_NAME_ID", unique=true, nullable=false, precision=8, scale=0)

    public Integer getMedNameId() {
        return this.medNameId;
    }
    
    public void setMedNameId(Integer medNameId) {
        this.medNameId = medNameId;
    }
    
    @Column(name="MED_NAME", nullable=false, length=30)

    public String getMedName() {
        return this.medName;
    }
    
    public void setMedName(String medName) {
        this.medName = medName;
    }
    
    @Column(name="MED_NAME_TYPE_CD", nullable=false, length=1)

    public String getMedNameTypeCd() {
        return this.medNameTypeCd;
    }
    
    public void setMedNameTypeCd(String medNameTypeCd) {
        this.medNameTypeCd = medNameTypeCd;
    }
    
    @Column(name="MED_STATUS_CD", nullable=false, length=1)

    public String getMedStatusCd() {
        return this.medStatusCd;
    }
    
    public void setMedStatusCd(String medStatusCd) {
        this.medStatusCd = medStatusCd;
    }
   








}