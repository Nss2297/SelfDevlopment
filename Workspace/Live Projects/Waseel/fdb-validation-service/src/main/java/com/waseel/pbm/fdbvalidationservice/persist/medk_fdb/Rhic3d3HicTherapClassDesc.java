package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rhic3d3HicTherapClassDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RHIC3D3_HIC_THERAP_CLASS_DESC"
    ,schema="MEDK_FDB"
)

public class Rhic3d3HicTherapClassDesc  implements java.io.Serializable {


    // Fields    

     private Integer hic3Seqn;
     private String hic3;
     private String hic3Desc;
     private Integer hic3Grpn;
     private Integer hic3Root;


    // Constructors

    /** default constructor */
    public Rhic3d3HicTherapClassDesc() {
    }

	/** minimal constructor */
    public Rhic3d3HicTherapClassDesc(Integer hic3Seqn, String hic3) {
        this.hic3Seqn = hic3Seqn;
        this.hic3 = hic3;
    }
    
    /** full constructor */
    public Rhic3d3HicTherapClassDesc(Integer hic3Seqn, String hic3, String hic3Desc, Integer hic3Grpn, Integer hic3Root) {
        this.hic3Seqn = hic3Seqn;
        this.hic3 = hic3;
        this.hic3Desc = hic3Desc;
        this.hic3Grpn = hic3Grpn;
        this.hic3Root = hic3Root;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="HIC3_SEQN", unique=true, nullable=false, precision=6, scale=0)

    public Integer getHic3Seqn() {
        return this.hic3Seqn;
    }
    
    public void setHic3Seqn(Integer hic3Seqn) {
        this.hic3Seqn = hic3Seqn;
    }
    
    @Column(name="HIC3", nullable=false, length=3)

    public String getHic3() {
        return this.hic3;
    }
    
    public void setHic3(String hic3) {
        this.hic3 = hic3;
    }
    
    @Column(name="HIC3_DESC", length=50)

    public String getHic3Desc() {
        return this.hic3Desc;
    }
    
    public void setHic3Desc(String hic3Desc) {
        this.hic3Desc = hic3Desc;
    }
    
    @Column(name="HIC3_GRPN", precision=6, scale=0)

    public Integer getHic3Grpn() {
        return this.hic3Grpn;
    }
    
    public void setHic3Grpn(Integer hic3Grpn) {
        this.hic3Grpn = hic3Grpn;
    }
    
    @Column(name="HIC3_ROOT", precision=6, scale=0)

    public Integer getHic3Root() {
        return this.hic3Root;
    }
    
    public void setHic3Root(Integer hic3Root) {
        this.hic3Root = hic3Root;
    }
   








}