package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rfmldrh0DxidHist entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RFMLDRH0_DXID_HIST"
    ,schema="MEDK_FDB"
)

public class Rfmldrh0DxidHist  implements java.io.Serializable {


    // Fields    

     private Rfmldrh0DxidHistId id;
     private Timestamp fmldxrepdt;


    // Constructors

    /** default constructor */
    public Rfmldrh0DxidHist() {
    }

	/** minimal constructor */
    public Rfmldrh0DxidHist(Rfmldrh0DxidHistId id) {
        this.id = id;
    }
    
    /** full constructor */
    public Rfmldrh0DxidHist(Rfmldrh0DxidHistId id, Timestamp fmldxrepdt) {
        this.id = id;
        this.fmldxrepdt = fmldxrepdt;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="fmlprvdxid", column=@Column(name="FMLPRVDXID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="fmlrepdxid", column=@Column(name="FMLREPDXID", nullable=false, precision=8, scale=0) ) } )

    public Rfmldrh0DxidHistId getId() {
        return this.id;
    }
    
    public void setId(Rfmldrh0DxidHistId id) {
        this.id = id;
    }
    
    @Column(name="FMLDXREPDT", length=7)

    public Timestamp getFmldxrepdt() {
        return this.fmldxrepdt;
    }
    
    public void setFmldxrepdt(Timestamp fmldxrepdt) {
        this.fmldxrepdt = fmldxrepdt;
    }
   








}