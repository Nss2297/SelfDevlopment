package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * Rfmlddd0DxDurationDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RFMLDDD0_DX_DURATION_DESC"
    ,schema="MEDK_FDB"
)

public class Rfmlddd0DxDurationDesc  implements java.io.Serializable {


    // Fields    

     private String dxidDiseaseDurationCd;
     private String dxidDiseaseDurationCdDesc;
     private Set<Rfmldx0Dxid> rfmldx0Dxids = new HashSet<Rfmldx0Dxid>(0);


    // Constructors

    /** default constructor */
    public Rfmlddd0DxDurationDesc() {
    }

	/** minimal constructor */
    public Rfmlddd0DxDurationDesc(String dxidDiseaseDurationCd) {
        this.dxidDiseaseDurationCd = dxidDiseaseDurationCd;
    }
    
    /** full constructor */
    public Rfmlddd0DxDurationDesc(String dxidDiseaseDurationCd, String dxidDiseaseDurationCdDesc, Set<Rfmldx0Dxid> rfmldx0Dxids) {
        this.dxidDiseaseDurationCd = dxidDiseaseDurationCd;
        this.dxidDiseaseDurationCdDesc = dxidDiseaseDurationCdDesc;
        this.rfmldx0Dxids = rfmldx0Dxids;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="DXID_DISEASE_DURATION_CD", unique=true, nullable=false, length=1)

    public String getDxidDiseaseDurationCd() {
        return this.dxidDiseaseDurationCd;
    }
    
    public void setDxidDiseaseDurationCd(String dxidDiseaseDurationCd) {
        this.dxidDiseaseDurationCd = dxidDiseaseDurationCd;
    }
    
    @Column(name="DXID_DISEASE_DURATION_CD_DESC", length=50)

    public String getDxidDiseaseDurationCdDesc() {
        return this.dxidDiseaseDurationCdDesc;
    }
    
    public void setDxidDiseaseDurationCdDesc(String dxidDiseaseDurationCdDesc) {
        this.dxidDiseaseDurationCdDesc = dxidDiseaseDurationCdDesc;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="rfmlddd0DxDurationDesc")

    public Set<Rfmldx0Dxid> getRfmldx0Dxids() {
        return this.rfmldx0Dxids;
    }
    
    public void setRfmldx0Dxids(Set<Rfmldx0Dxid> rfmldx0Dxids) {
        this.rfmldx0Dxids = rfmldx0Dxids;
    }
   








}