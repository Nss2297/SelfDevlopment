package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * Rfmldx0Dxid entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RFMLDX0_DXID"
    ,schema="MEDK_FDB"
)

public class Rfmldx0Dxid  implements java.io.Serializable {


    // Fields    

     private Integer dxid;
     private Rfmlddd0DxDurationDesc rfmlddd0DxDurationDesc;
     private Rfmlscd0DxidStatusDesc rfmlscd0DxidStatusDesc;
     private String dxidDesc56;
     private String dxidDesc100;
     private String fdbdx;
     private Set<Rfmlsyn0DxidSyn> rfmlsyn0DxidSyns = new HashSet<Rfmlsyn0DxidSyn>(0);
     private Set<Rfmlnav0DxidNavigation> rfmlnav0DxidNavigations = new HashSet<Rfmlnav0DxidNavigation>(0);


    // Constructors

    /** default constructor */
    public Rfmldx0Dxid() {
    }

	/** minimal constructor */
    public Rfmldx0Dxid(Integer dxid, Rfmlddd0DxDurationDesc rfmlddd0DxDurationDesc, Rfmlscd0DxidStatusDesc rfmlscd0DxidStatusDesc, String fdbdx) {
        this.dxid = dxid;
        this.rfmlddd0DxDurationDesc = rfmlddd0DxDurationDesc;
        this.rfmlscd0DxidStatusDesc = rfmlscd0DxidStatusDesc;
        this.fdbdx = fdbdx;
    }
    
    /** full constructor */
    public Rfmldx0Dxid(Integer dxid, Rfmlddd0DxDurationDesc rfmlddd0DxDurationDesc, Rfmlscd0DxidStatusDesc rfmlscd0DxidStatusDesc, String dxidDesc56, String dxidDesc100, String fdbdx, Set<Rfmlsyn0DxidSyn> rfmlsyn0DxidSyns, Set<Rfmlnav0DxidNavigation> rfmlnav0DxidNavigations) {
        this.dxid = dxid;
        this.rfmlddd0DxDurationDesc = rfmlddd0DxDurationDesc;
        this.rfmlscd0DxidStatusDesc = rfmlscd0DxidStatusDesc;
        this.dxidDesc56 = dxidDesc56;
        this.dxidDesc100 = dxidDesc100;
        this.fdbdx = fdbdx;
        this.rfmlsyn0DxidSyns = rfmlsyn0DxidSyns;
        this.rfmlnav0DxidNavigations = rfmlnav0DxidNavigations;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="DXID", unique=true, nullable=false, precision=8, scale=0)

    public Integer getDxid() {
        return this.dxid;
    }
    
    public void setDxid(Integer dxid) {
        this.dxid = dxid;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="DXID_DISEASE_DURATION_CD", nullable=false)

    public Rfmlddd0DxDurationDesc getRfmlddd0DxDurationDesc() {
        return this.rfmlddd0DxDurationDesc;
    }
    
    public void setRfmlddd0DxDurationDesc(Rfmlddd0DxDurationDesc rfmlddd0DxDurationDesc) {
        this.rfmlddd0DxDurationDesc = rfmlddd0DxDurationDesc;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="DXID_STATUS", nullable=false)

    public Rfmlscd0DxidStatusDesc getRfmlscd0DxidStatusDesc() {
        return this.rfmlscd0DxidStatusDesc;
    }
    
    public void setRfmlscd0DxidStatusDesc(Rfmlscd0DxidStatusDesc rfmlscd0DxidStatusDesc) {
        this.rfmlscd0DxidStatusDesc = rfmlscd0DxidStatusDesc;
    }
    
    @Column(name="DXID_DESC56", length=56)

    public String getDxidDesc56() {
        return this.dxidDesc56;
    }
    
    public void setDxidDesc56(String dxidDesc56) {
        this.dxidDesc56 = dxidDesc56;
    }
    
    @Column(name="DXID_DESC100", length=100)

    public String getDxidDesc100() {
        return this.dxidDesc100;
    }
    
    public void setDxidDesc100(String dxidDesc100) {
        this.dxidDesc100 = dxidDesc100;
    }
    
    @Column(name="FDBDX", nullable=false, length=9)

    public String getFdbdx() {
        return this.fdbdx;
    }
    
    public void setFdbdx(String fdbdx) {
        this.fdbdx = fdbdx;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="rfmldx0Dxid")

    public Set<Rfmlsyn0DxidSyn> getRfmlsyn0DxidSyns() {
        return this.rfmlsyn0DxidSyns;
    }
    
    public void setRfmlsyn0DxidSyns(Set<Rfmlsyn0DxidSyn> rfmlsyn0DxidSyns) {
        this.rfmlsyn0DxidSyns = rfmlsyn0DxidSyns;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="rfmldx0Dxid")

    public Set<Rfmlnav0DxidNavigation> getRfmlnav0DxidNavigations() {
        return this.rfmlnav0DxidNavigations;
    }
    
    public void setRfmlnav0DxidNavigations(Set<Rfmlnav0DxidNavigation> rfmlnav0DxidNavigations) {
        this.rfmlnav0DxidNavigations = rfmlnav0DxidNavigations;
    }
   








}