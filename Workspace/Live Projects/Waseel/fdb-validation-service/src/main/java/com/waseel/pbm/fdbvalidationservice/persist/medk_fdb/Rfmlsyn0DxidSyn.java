package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * Rfmlsyn0DxidSyn entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RFMLSYN0_DXID_SYN"
    ,schema="MEDK_FDB"
)

public class Rfmlsyn0DxidSyn  implements java.io.Serializable {


    // Fields    

     private Integer dxidSynid;
     private Rfmldx0Dxid rfmldx0Dxid;
     private Rfmlssd0SynStatusDesc rfmlssd0SynStatusDesc;
     private Rfmlsnd0SynNameTypeDesc rfmlsnd0SynNameTypeDesc;
     private String dxidSynDesc56;
     private String dxidSynDesc100;


    // Constructors

    /** default constructor */
    public Rfmlsyn0DxidSyn() {
    }

	/** minimal constructor */
    public Rfmlsyn0DxidSyn(Integer dxidSynid, Rfmldx0Dxid rfmldx0Dxid, Rfmlssd0SynStatusDesc rfmlssd0SynStatusDesc, Rfmlsnd0SynNameTypeDesc rfmlsnd0SynNameTypeDesc) {
        this.dxidSynid = dxidSynid;
        this.rfmldx0Dxid = rfmldx0Dxid;
        this.rfmlssd0SynStatusDesc = rfmlssd0SynStatusDesc;
        this.rfmlsnd0SynNameTypeDesc = rfmlsnd0SynNameTypeDesc;
    }
    
    /** full constructor */
    public Rfmlsyn0DxidSyn(Integer dxidSynid, Rfmldx0Dxid rfmldx0Dxid, Rfmlssd0SynStatusDesc rfmlssd0SynStatusDesc, Rfmlsnd0SynNameTypeDesc rfmlsnd0SynNameTypeDesc, String dxidSynDesc56, String dxidSynDesc100) {
        this.dxidSynid = dxidSynid;
        this.rfmldx0Dxid = rfmldx0Dxid;
        this.rfmlssd0SynStatusDesc = rfmlssd0SynStatusDesc;
        this.rfmlsnd0SynNameTypeDesc = rfmlsnd0SynNameTypeDesc;
        this.dxidSynDesc56 = dxidSynDesc56;
        this.dxidSynDesc100 = dxidSynDesc100;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="DXID_SYNID", unique=true, nullable=false, precision=8, scale=0)

    public Integer getDxidSynid() {
        return this.dxidSynid;
    }
    
    public void setDxidSynid(Integer dxidSynid) {
        this.dxidSynid = dxidSynid;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="DXID", nullable=false)

    public Rfmldx0Dxid getRfmldx0Dxid() {
        return this.rfmldx0Dxid;
    }
    
    public void setRfmldx0Dxid(Rfmldx0Dxid rfmldx0Dxid) {
        this.rfmldx0Dxid = rfmldx0Dxid;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="DXID_SYN_STATUS", nullable=false)

    public Rfmlssd0SynStatusDesc getRfmlssd0SynStatusDesc() {
        return this.rfmlssd0SynStatusDesc;
    }
    
    public void setRfmlssd0SynStatusDesc(Rfmlssd0SynStatusDesc rfmlssd0SynStatusDesc) {
        this.rfmlssd0SynStatusDesc = rfmlssd0SynStatusDesc;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="DXID_SYN_NMTYP", nullable=false)

    public Rfmlsnd0SynNameTypeDesc getRfmlsnd0SynNameTypeDesc() {
        return this.rfmlsnd0SynNameTypeDesc;
    }
    
    public void setRfmlsnd0SynNameTypeDesc(Rfmlsnd0SynNameTypeDesc rfmlsnd0SynNameTypeDesc) {
        this.rfmlsnd0SynNameTypeDesc = rfmlsnd0SynNameTypeDesc;
    }
    
    @Column(name="DXID_SYN_DESC56", length=56)

    public String getDxidSynDesc56() {
        return this.dxidSynDesc56;
    }
    
    public void setDxidSynDesc56(String dxidSynDesc56) {
        this.dxidSynDesc56 = dxidSynDesc56;
    }
    
    @Column(name="DXID_SYN_DESC100", length=100)

    public String getDxidSynDesc100() {
        return this.dxidSynDesc100;
    }
    
    public void setDxidSynDesc100(String dxidSynDesc100) {
        this.dxidSynDesc100 = dxidSynDesc100;
    }
   








}