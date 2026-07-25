package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rddcmdd0ContraDrugDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDDCMDD0_CONTRA_DRUG_DESC"
    ,schema="MEDK_FDB"
)

public class Rddcmdd0ContraDrugDesc  implements java.io.Serializable {


    // Fields    

     private Integer ddxcn;
     private String ddxcnDrugDesc;


    // Constructors

    /** default constructor */
    public Rddcmdd0ContraDrugDesc() {
    }

	/** minimal constructor */
    public Rddcmdd0ContraDrugDesc(Integer ddxcn) {
        this.ddxcn = ddxcn;
    }
    
    /** full constructor */
    public Rddcmdd0ContraDrugDesc(Integer ddxcn, String ddxcnDrugDesc) {
        this.ddxcn = ddxcn;
        this.ddxcnDrugDesc = ddxcnDrugDesc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="DDXCN", unique=true, nullable=false, precision=5, scale=0)

    public Integer getDdxcn() {
        return this.ddxcn;
    }
    
    public void setDdxcn(Integer ddxcn) {
        this.ddxcn = ddxcn;
    }
    
    @Column(name="DDXCN_DRUG_DESC", length=100)

    public String getDdxcnDrugDesc() {
        return this.ddxcnDrugDesc;
    }
    
    public void setDdxcnDrugDesc(String ddxcnDrugDesc) {
        this.ddxcnDrugDesc = ddxcnDrugDesc;
    }
   








}