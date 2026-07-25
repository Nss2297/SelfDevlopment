package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rhicd5HicDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RHICD5_HIC_DESC"
    ,schema="MEDK_FDB"
)

public class Rhicd5HicDesc  implements java.io.Serializable {


    // Fields    

     private Integer hicSeqn;
     private String hic;
     private String hicDesc;
     private Integer hicRoot;
     private Boolean hicPotentiallyInactvInd;
     private Boolean ingStatusCd;


    // Constructors

    /** default constructor */
    public Rhicd5HicDesc() {
    }

	/** minimal constructor */
    public Rhicd5HicDesc(Integer hicSeqn) {
        this.hicSeqn = hicSeqn;
    }
    
    /** full constructor */
    public Rhicd5HicDesc(Integer hicSeqn, String hic, String hicDesc, Integer hicRoot, Boolean hicPotentiallyInactvInd, Boolean ingStatusCd) {
        this.hicSeqn = hicSeqn;
        this.hic = hic;
        this.hicDesc = hicDesc;
        this.hicRoot = hicRoot;
        this.hicPotentiallyInactvInd = hicPotentiallyInactvInd;
        this.ingStatusCd = ingStatusCd;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="HIC_SEQN", unique=true, nullable=false, precision=6, scale=0)

    public Integer getHicSeqn() {
        return this.hicSeqn;
    }
    
    public void setHicSeqn(Integer hicSeqn) {
        this.hicSeqn = hicSeqn;
    }
    
    @Column(name="HIC", length=6)

    public String getHic() {
        return this.hic;
    }
    
    public void setHic(String hic) {
        this.hic = hic;
    }
    
    @Column(name="HIC_DESC", length=50)

    public String getHicDesc() {
        return this.hicDesc;
    }
    
    public void setHicDesc(String hicDesc) {
        this.hicDesc = hicDesc;
    }
    
    @Column(name="HIC_ROOT", precision=6, scale=0)

    public Integer getHicRoot() {
        return this.hicRoot;
    }
    
    public void setHicRoot(Integer hicRoot) {
        this.hicRoot = hicRoot;
    }
    
    @Column(name="HIC_POTENTIALLY_INACTV_IND", precision=1, scale=0)

    public Boolean getHicPotentiallyInactvInd() {
        return this.hicPotentiallyInactvInd;
    }
    
    public void setHicPotentiallyInactvInd(Boolean hicPotentiallyInactvInd) {
        this.hicPotentiallyInactvInd = hicPotentiallyInactvInd;
    }
    
    @Column(name="ING_STATUS_CD", precision=1, scale=0)

    public Boolean getIngStatusCd() {
        return this.ingStatusCd;
    }
    
    public void setIngStatusCd(Boolean ingStatusCd) {
        this.ingStatusCd = ingStatusCd;
    }
   








}