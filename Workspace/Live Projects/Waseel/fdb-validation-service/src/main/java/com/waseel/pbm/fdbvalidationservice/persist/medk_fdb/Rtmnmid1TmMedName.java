package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rtmnmid1TmMedName entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RTMNMID1_TM_MED_NAME"
    ,schema="MEDK_FDB"
)

public class Rtmnmid1TmMedName  implements java.io.Serializable {


    // Fields    

     private Integer medNameId;
     private Integer tmSourceId;
     private Boolean tmInd;
     private String tmAltMedNameDesc;


    // Constructors

    /** default constructor */
    public Rtmnmid1TmMedName() {
    }

	/** minimal constructor */
    public Rtmnmid1TmMedName(Integer medNameId) {
        this.medNameId = medNameId;
    }
    
    /** full constructor */
    public Rtmnmid1TmMedName(Integer medNameId, Integer tmSourceId, Boolean tmInd, String tmAltMedNameDesc) {
        this.medNameId = medNameId;
        this.tmSourceId = tmSourceId;
        this.tmInd = tmInd;
        this.tmAltMedNameDesc = tmAltMedNameDesc;
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
    
    @Column(name="TM_SOURCE_ID", precision=5, scale=0)

    public Integer getTmSourceId() {
        return this.tmSourceId;
    }
    
    public void setTmSourceId(Integer tmSourceId) {
        this.tmSourceId = tmSourceId;
    }
    
    @Column(name="TM_IND", precision=1, scale=0)

    public Boolean getTmInd() {
        return this.tmInd;
    }
    
    public void setTmInd(Boolean tmInd) {
        this.tmInd = tmInd;
    }
    
    @Column(name="TM_ALT_MED_NAME_DESC", length=70)

    public String getTmAltMedNameDesc() {
        return this.tmAltMedNameDesc;
    }
    
    public void setTmAltMedNameDesc(String tmAltMedNameDesc) {
        this.tmAltMedNameDesc = tmAltMedNameDesc;
    }
   








}