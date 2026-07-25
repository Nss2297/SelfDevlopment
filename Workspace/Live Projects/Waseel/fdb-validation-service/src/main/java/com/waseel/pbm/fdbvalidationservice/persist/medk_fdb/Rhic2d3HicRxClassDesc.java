package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rhic2d3HicRxClassDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RHIC2D3_HIC_RX_CLASS_DESC"
    ,schema="MEDK_FDB"
)

public class Rhic2d3HicRxClassDesc  implements java.io.Serializable {


    // Fields    

     private Integer hic2Seqn;
     private String hic2;
     private String hic2Desc;
     private Integer hic2Root;


    // Constructors

    /** default constructor */
    public Rhic2d3HicRxClassDesc() {
    }

	/** minimal constructor */
    public Rhic2d3HicRxClassDesc(Integer hic2Seqn, String hic2) {
        this.hic2Seqn = hic2Seqn;
        this.hic2 = hic2;
    }
    
    /** full constructor */
    public Rhic2d3HicRxClassDesc(Integer hic2Seqn, String hic2, String hic2Desc, Integer hic2Root) {
        this.hic2Seqn = hic2Seqn;
        this.hic2 = hic2;
        this.hic2Desc = hic2Desc;
        this.hic2Root = hic2Root;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="HIC2_SEQN", unique=true, nullable=false, precision=6, scale=0)

    public Integer getHic2Seqn() {
        return this.hic2Seqn;
    }
    
    public void setHic2Seqn(Integer hic2Seqn) {
        this.hic2Seqn = hic2Seqn;
    }
    
    @Column(name="HIC2", nullable=false, length=2)

    public String getHic2() {
        return this.hic2;
    }
    
    public void setHic2(String hic2) {
        this.hic2 = hic2;
    }
    
    @Column(name="HIC2_DESC", length=50)

    public String getHic2Desc() {
        return this.hic2Desc;
    }
    
    public void setHic2Desc(String hic2Desc) {
        this.hic2Desc = hic2Desc;
    }
    
    @Column(name="HIC2_ROOT", precision=6, scale=0)

    public Integer getHic2Root() {
        return this.hic2Root;
    }
    
    public void setHic2Root(Integer hic2Root) {
        this.hic2Root = hic2Root;
    }
   








}