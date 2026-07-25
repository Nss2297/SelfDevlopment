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
 * Rfmlssd0SynStatusDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RFMLSSD0_SYN_STATUS_DESC"
    ,schema="MEDK_FDB"
)

public class Rfmlssd0SynStatusDesc  implements java.io.Serializable {


    // Fields    

     private String dxidSynStatus;
     private String dxidSynStatusDesc;
     private Set<Rfmlsyn0DxidSyn> rfmlsyn0DxidSyns = new HashSet<Rfmlsyn0DxidSyn>(0);


    // Constructors

    /** default constructor */
    public Rfmlssd0SynStatusDesc() {
    }

	/** minimal constructor */
    public Rfmlssd0SynStatusDesc(String dxidSynStatus) {
        this.dxidSynStatus = dxidSynStatus;
    }
    
    /** full constructor */
    public Rfmlssd0SynStatusDesc(String dxidSynStatus, String dxidSynStatusDesc, Set<Rfmlsyn0DxidSyn> rfmlsyn0DxidSyns) {
        this.dxidSynStatus = dxidSynStatus;
        this.dxidSynStatusDesc = dxidSynStatusDesc;
        this.rfmlsyn0DxidSyns = rfmlsyn0DxidSyns;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="DXID_SYN_STATUS", unique=true, nullable=false, length=1)

    public String getDxidSynStatus() {
        return this.dxidSynStatus;
    }
    
    public void setDxidSynStatus(String dxidSynStatus) {
        this.dxidSynStatus = dxidSynStatus;
    }
    
    @Column(name="DXID_SYN_STATUS_DESC", length=50)

    public String getDxidSynStatusDesc() {
        return this.dxidSynStatusDesc;
    }
    
    public void setDxidSynStatusDesc(String dxidSynStatusDesc) {
        this.dxidSynStatusDesc = dxidSynStatusDesc;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="rfmlssd0SynStatusDesc")

    public Set<Rfmlsyn0DxidSyn> getRfmlsyn0DxidSyns() {
        return this.rfmlsyn0DxidSyns;
    }
    
    public void setRfmlsyn0DxidSyns(Set<Rfmlsyn0DxidSyn> rfmlsyn0DxidSyns) {
        this.rfmlsyn0DxidSyns = rfmlsyn0DxidSyns;
    }
   








}