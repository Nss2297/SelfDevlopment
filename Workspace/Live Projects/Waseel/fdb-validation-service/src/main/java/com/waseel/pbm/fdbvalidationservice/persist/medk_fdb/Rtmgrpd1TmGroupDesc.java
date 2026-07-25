package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rtmgrpd1TmGroupDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RTMGRPD1_TM_GROUP_DESC"
    ,schema="MEDK_FDB"
)

public class Rtmgrpd1TmGroupDesc  implements java.io.Serializable {


    // Fields    

     private Integer tmGroupId;
     private String tmGroupDesc;


    // Constructors

    /** default constructor */
    public Rtmgrpd1TmGroupDesc() {
    }

	/** minimal constructor */
    public Rtmgrpd1TmGroupDesc(Integer tmGroupId) {
        this.tmGroupId = tmGroupId;
    }
    
    /** full constructor */
    public Rtmgrpd1TmGroupDesc(Integer tmGroupId, String tmGroupDesc) {
        this.tmGroupId = tmGroupId;
        this.tmGroupDesc = tmGroupDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="TM_GROUP_ID", unique=true, nullable=false, precision=5, scale=0)

    public Integer getTmGroupId() {
        return this.tmGroupId;
    }
    
    public void setTmGroupId(Integer tmGroupId) {
        this.tmGroupId = tmGroupId;
    }
    
    @Column(name="TM_GROUP_DESC", length=250)

    public String getTmGroupDesc() {
        return this.tmGroupDesc;
    }
    
    public void setTmGroupDesc(String tmGroupDesc) {
        this.tmGroupDesc = tmGroupDesc;
    }
   








}