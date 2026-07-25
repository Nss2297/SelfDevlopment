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
 * Rfmlscd0DxidStatusDesc entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RFMLSCD0_DXID_STATUS_DESC"
    ,schema="MEDK_FDB"
)

public class Rfmlscd0DxidStatusDesc  implements java.io.Serializable {


    // Fields    

     private String dxidStatus;
     private String dxidStatusDesc;
     private Set<Rfmldx0Dxid> rfmldx0Dxids = new HashSet<Rfmldx0Dxid>(0);


    // Constructors

    /** default constructor */
    public Rfmlscd0DxidStatusDesc() {
    }

	/** minimal constructor */
    public Rfmlscd0DxidStatusDesc(String dxidStatus) {
        this.dxidStatus = dxidStatus;
    }
    
    /** full constructor */
    public Rfmlscd0DxidStatusDesc(String dxidStatus, String dxidStatusDesc, Set<Rfmldx0Dxid> rfmldx0Dxids) {
        this.dxidStatus = dxidStatus;
        this.dxidStatusDesc = dxidStatusDesc;
        this.rfmldx0Dxids = rfmldx0Dxids;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="DXID_STATUS", unique=true, nullable=false, length=1)

    public String getDxidStatus() {
        return this.dxidStatus;
    }
    
    public void setDxidStatus(String dxidStatus) {
        this.dxidStatus = dxidStatus;
    }
    
    @Column(name="DXID_STATUS_DESC", length=50)

    public String getDxidStatusDesc() {
        return this.dxidStatusDesc;
    }
    
    public void setDxidStatusDesc(String dxidStatusDesc) {
        this.dxidStatusDesc = dxidStatusDesc;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="rfmlscd0DxidStatusDesc")

    public Set<Rfmldx0Dxid> getRfmldx0Dxids() {
        return this.rfmldx0Dxids;
    }
    
    public void setRfmldx0Dxids(Set<Rfmldx0Dxid> rfmldx0Dxids) {
        this.rfmldx0Dxids = rfmldx0Dxids;
    }
   








}