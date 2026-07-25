package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rtmsrcd1TmSourceDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RTMSRCD1_TM_SOURCE_DESC"
    ,schema="MEDK_FDB"
)

public class Rtmsrcd1TmSourceDesc  implements java.io.Serializable {


    // Fields    

     private Integer tmSourceId;
     private String tmSourceDesc;


    // Constructors

    /** default constructor */
    public Rtmsrcd1TmSourceDesc() {
    }

	/** minimal constructor */
    public Rtmsrcd1TmSourceDesc(Integer tmSourceId) {
        this.tmSourceId = tmSourceId;
    }
    
    /** full constructor */
    public Rtmsrcd1TmSourceDesc(Integer tmSourceId, String tmSourceDesc) {
        this.tmSourceId = tmSourceId;
        this.tmSourceDesc = tmSourceDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="TM_SOURCE_ID", unique=true, nullable=false, precision=5, scale=0)

    public Integer getTmSourceId() {
        return this.tmSourceId;
    }
    
    public void setTmSourceId(Integer tmSourceId) {
        this.tmSourceId = tmSourceId;
    }
    
    @Column(name="TM_SOURCE_DESC", length=120)

    public String getTmSourceDesc() {
        return this.tmSourceDesc;
    }
    
    public void setTmSourceDesc(String tmSourceDesc) {
        this.tmSourceDesc = tmSourceDesc;
    }
   








}