package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rhic4d2HicBaseIngDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RHIC4D2_HIC_BASE_ING_DESC"
    ,schema="MEDK_FDB"
)

public class Rhic4d2HicBaseIngDesc  implements java.io.Serializable {


    // Fields    

     private Integer hic4Seqn;
     private String hic4;
     private String hic4Desc;
     private Integer hic4Root;
     private Boolean hic4PotentiallyInactvInd;
     private Boolean ingStatusCd;


    // Constructors

    /** default constructor */
    public Rhic4d2HicBaseIngDesc() {
    }

	/** minimal constructor */
    public Rhic4d2HicBaseIngDesc(Integer hic4Seqn) {
        this.hic4Seqn = hic4Seqn;
    }
    
    /** full constructor */
    public Rhic4d2HicBaseIngDesc(Integer hic4Seqn, String hic4, String hic4Desc, Integer hic4Root, Boolean hic4PotentiallyInactvInd, Boolean ingStatusCd) {
        this.hic4Seqn = hic4Seqn;
        this.hic4 = hic4;
        this.hic4Desc = hic4Desc;
        this.hic4Root = hic4Root;
        this.hic4PotentiallyInactvInd = hic4PotentiallyInactvInd;
        this.ingStatusCd = ingStatusCd;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="HIC4_SEQN", unique=true, nullable=false, precision=6, scale=0)

    public Integer getHic4Seqn() {
        return this.hic4Seqn;
    }
    
    public void setHic4Seqn(Integer hic4Seqn) {
        this.hic4Seqn = hic4Seqn;
    }
    
    @Column(name="HIC4", length=4)

    public String getHic4() {
        return this.hic4;
    }
    
    public void setHic4(String hic4) {
        this.hic4 = hic4;
    }
    
    @Column(name="HIC4_DESC", length=50)

    public String getHic4Desc() {
        return this.hic4Desc;
    }
    
    public void setHic4Desc(String hic4Desc) {
        this.hic4Desc = hic4Desc;
    }
    
    @Column(name="HIC4_ROOT", precision=6, scale=0)

    public Integer getHic4Root() {
        return this.hic4Root;
    }
    
    public void setHic4Root(Integer hic4Root) {
        this.hic4Root = hic4Root;
    }
    
    @Column(name="HIC4_POTENTIALLY_INACTV_IND", precision=1, scale=0)

    public Boolean getHic4PotentiallyInactvInd() {
        return this.hic4PotentiallyInactvInd;
    }
    
    public void setHic4PotentiallyInactvInd(Boolean hic4PotentiallyInactvInd) {
        this.hic4PotentiallyInactvInd = hic4PotentiallyInactvInd;
    }
    
    @Column(name="ING_STATUS_CD", precision=1, scale=0)

    public Boolean getIngStatusCd() {
        return this.ingStatusCd;
    }
    
    public void setIngStatusCd(Boolean ingStatusCd) {
        this.ingStatusCd = ingStatusCd;
    }
   








}