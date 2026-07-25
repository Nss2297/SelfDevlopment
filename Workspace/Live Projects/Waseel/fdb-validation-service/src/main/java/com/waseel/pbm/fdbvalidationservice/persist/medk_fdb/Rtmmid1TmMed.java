package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rtmmid1TmMed entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RTMMID1_TM_MED"
    ,schema="MEDK_FDB"
)

public class Rtmmid1TmMed  implements java.io.Serializable {


    // Fields    

     private Integer medid;
     private Integer tmSourceId;
     private Boolean tmInd;
     private String tmAltMedidDesc;


    // Constructors

    /** default constructor */
    public Rtmmid1TmMed() {
    }

	/** minimal constructor */
    public Rtmmid1TmMed(Integer medid) {
        this.medid = medid;
    }
    
    /** full constructor */
    public Rtmmid1TmMed(Integer medid, Integer tmSourceId, Boolean tmInd, String tmAltMedidDesc) {
        this.medid = medid;
        this.tmSourceId = tmSourceId;
        this.tmInd = tmInd;
        this.tmAltMedidDesc = tmAltMedidDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="MEDID", unique=true, nullable=false, precision=8, scale=0)

    public Integer getMedid() {
        return this.medid;
    }
    
    public void setMedid(Integer medid) {
        this.medid = medid;
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
    
    @Column(name="TM_ALT_MEDID_DESC", length=70)

    public String getTmAltMedidDesc() {
        return this.tmAltMedidDesc;
    }
    
    public void setTmAltMedidDesc(String tmAltMedidDesc) {
        this.tmAltMedidDesc = tmAltMedidDesc;
    }
   








}