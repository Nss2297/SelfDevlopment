package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rhic1d2HicOrganSysDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RHIC1D2_HIC_ORGAN_SYS_DESC"
    ,schema="MEDK_FDB"
)

public class Rhic1d2HicOrganSysDesc  implements java.io.Serializable {


    // Fields    

     private Integer hic1Seqn;
     private String hic1;
     private String hic1Desc;


    // Constructors

    /** default constructor */
    public Rhic1d2HicOrganSysDesc() {
    }

	/** minimal constructor */
    public Rhic1d2HicOrganSysDesc(Integer hic1Seqn, String hic1) {
        this.hic1Seqn = hic1Seqn;
        this.hic1 = hic1;
    }
    
    /** full constructor */
    public Rhic1d2HicOrganSysDesc(Integer hic1Seqn, String hic1, String hic1Desc) {
        this.hic1Seqn = hic1Seqn;
        this.hic1 = hic1;
        this.hic1Desc = hic1Desc;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="HIC1_SEQN", unique=true, nullable=false, precision=6, scale=0)

    public Integer getHic1Seqn() {
        return this.hic1Seqn;
    }
    
    public void setHic1Seqn(Integer hic1Seqn) {
        this.hic1Seqn = hic1Seqn;
    }
    
    @Column(name="HIC1", nullable=false, length=1)

    public String getHic1() {
        return this.hic1;
    }
    
    public void setHic1(String hic1) {
        this.hic1 = hic1;
    }
    
    @Column(name="HIC1_DESC", length=50)

    public String getHic1Desc() {
        return this.hic1Desc;
    }
    
    public void setHic1Desc(String hic1Desc) {
        this.hic1Desc = hic1Desc;
    }
   








}