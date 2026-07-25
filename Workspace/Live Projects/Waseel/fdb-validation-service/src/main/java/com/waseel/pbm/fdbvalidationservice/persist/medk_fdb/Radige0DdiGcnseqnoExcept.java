package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import java.sql.Timestamp;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Radige0DdiGcnseqnoExcept entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RADIGE0_DDI_GCNSEQNO_EXCEPT"
    ,schema="MEDK_FDB"
)

public class Radige0DdiGcnseqnoExcept  implements java.io.Serializable {


    // Fields    

     private Radige0DdiGcnseqnoExceptId id;
     private String ddiSl;
     private Integer ddiDisplayActionId;
     private String coadminDosingText;
     private Timestamp ddiExceptAddDt;


    // Constructors

    /** default constructor */
    public Radige0DdiGcnseqnoExcept() {
    }

	/** minimal constructor */
    public Radige0DdiGcnseqnoExcept(Radige0DdiGcnseqnoExceptId id, String ddiSl, Integer ddiDisplayActionId, Timestamp ddiExceptAddDt) {
        this.id = id;
        this.ddiSl = ddiSl;
        this.ddiDisplayActionId = ddiDisplayActionId;
        this.ddiExceptAddDt = ddiExceptAddDt;
    }
    
    /** full constructor */
    public Radige0DdiGcnseqnoExcept(Radige0DdiGcnseqnoExceptId id, String ddiSl, Integer ddiDisplayActionId, String coadminDosingText, Timestamp ddiExceptAddDt) {
        this.id = id;
        this.ddiSl = ddiSl;
        this.ddiDisplayActionId = ddiDisplayActionId;
        this.coadminDosingText = coadminDosingText;
        this.ddiExceptAddDt = ddiExceptAddDt;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="ddiMonox", column=@Column(name="DDI_MONOX", nullable=false, precision=5, scale=0) ), 
        @AttributeOverride(name="sideAGcnSeqno", column=@Column(name="SIDE_A_GCN_SEQNO", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="sideBGcnSeqno", column=@Column(name="SIDE_B_GCN_SEQNO", nullable=false, precision=6, scale=0) ) } )

    public Radige0DdiGcnseqnoExceptId getId() {
        return this.id;
    }
    
    public void setId(Radige0DdiGcnseqnoExceptId id) {
        this.id = id;
    }
    
    @Column(name="DDI_SL", nullable=false, length=1)

    public String getDdiSl() {
        return this.ddiSl;
    }
    
    public void setDdiSl(String ddiSl) {
        this.ddiSl = ddiSl;
    }
    
    @Column(name="DDI_DISPLAY_ACTION_ID", nullable=false, precision=8, scale=0)

    public Integer getDdiDisplayActionId() {
        return this.ddiDisplayActionId;
    }
    
    public void setDdiDisplayActionId(Integer ddiDisplayActionId) {
        this.ddiDisplayActionId = ddiDisplayActionId;
    }
    
    @Column(name="COADMIN_DOSING_TEXT")

    public String getCoadminDosingText() {
        return this.coadminDosingText;
    }
    
    public void setCoadminDosingText(String coadminDosingText) {
        this.coadminDosingText = coadminDosingText;
    }
    
    @Column(name="DDI_EXCEPT_ADD_DT", nullable=false, length=7)

    public Timestamp getDdiExceptAddDt() {
        return this.ddiExceptAddDt;
    }
    
    public void setDdiExceptAddDt(Timestamp ddiExceptAddDt) {
        this.ddiExceptAddDt = ddiExceptAddDt;
    }
   








}