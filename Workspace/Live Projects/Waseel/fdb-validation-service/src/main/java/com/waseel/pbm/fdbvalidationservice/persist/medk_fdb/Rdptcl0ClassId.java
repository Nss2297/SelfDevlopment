package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rdptcl0ClassId entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDPTCL0_CLASS_ID"
    ,schema="MEDK_FDB"
)

public class Rdptcl0ClassId  implements java.io.Serializable {


    // Fields    

     private Integer dptClassId;
     private String dptClassDesc;
     private Byte dptAllowance;


    // Constructors

    /** default constructor */
    public Rdptcl0ClassId() {
    }

	/** minimal constructor */
    public Rdptcl0ClassId(Integer dptClassId) {
        this.dptClassId = dptClassId;
    }
    
    /** full constructor */
    public Rdptcl0ClassId(Integer dptClassId, String dptClassDesc, Byte dptAllowance) {
        this.dptClassId = dptClassId;
        this.dptClassDesc = dptClassDesc;
        this.dptAllowance = dptAllowance;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="DPT_CLASS_ID", unique=true, nullable=false, precision=8, scale=0)

    public Integer getDptClassId() {
        return this.dptClassId;
    }
    
    public void setDptClassId(Integer dptClassId) {
        this.dptClassId = dptClassId;
    }
    
    @Column(name="DPT_CLASS_DESC", length=60)

    public String getDptClassDesc() {
        return this.dptClassDesc;
    }
    
    public void setDptClassDesc(String dptClassDesc) {
        this.dptClassDesc = dptClassDesc;
    }
    
    @Column(name="DPT_ALLOWANCE", precision=2, scale=0)

    public Byte getDptAllowance() {
        return this.dptAllowance;
    }
    
    public void setDptAllowance(Byte dptAllowance) {
        this.dptAllowance = dptAllowance;
    }
   








}