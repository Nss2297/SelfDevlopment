package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * Rfmlnav0DxidNavigation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RFMLNAV0_DXID_NAVIGATION"
    ,schema="MEDK_FDB"
)

public class Rfmlnav0DxidNavigation  implements java.io.Serializable {


    // Fields    

     private Rfmlnav0DxidNavigationId id;
     private Rfmldx0Dxid rfmldx0Dxid;


    // Constructors

    /** default constructor */
    public Rfmlnav0DxidNavigation() {
    }

    
    /** full constructor */
    public Rfmlnav0DxidNavigation(Rfmlnav0DxidNavigationId id, Rfmldx0Dxid rfmldx0Dxid) {
        this.id = id;
        this.rfmldx0Dxid = rfmldx0Dxid;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="dxid", column=@Column(name="DXID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="broaderDxid", column=@Column(name="BROADER_DXID", nullable=false, precision=8, scale=0) ) } )

    public Rfmlnav0DxidNavigationId getId() {
        return this.id;
    }
    
    public void setId(Rfmlnav0DxidNavigationId id) {
        this.id = id;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="DXID", nullable=false, insertable=false, updatable=false)

    public Rfmldx0Dxid getRfmldx0Dxid() {
        return this.rfmldx0Dxid;
    }
    
    public void setRfmldx0Dxid(Rfmldx0Dxid rfmldx0Dxid) {
        this.rfmldx0Dxid = rfmldx0Dxid;
    }
   








}