package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Radidc0DdiDcGcnseqnoScreen entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RADIDC0_DDI_DC_GCNSEQNO_SCREEN"
    ,schema="MEDK_FDB"
)

public class Radidc0DdiDcGcnseqnoScreen  implements java.io.Serializable {


    // Fields    

     private Radidc0DdiDcGcnseqnoScreenId id;
     private Integer ddiDcDaysScreenAmount;


    // Constructors

    /** default constructor */
    public Radidc0DdiDcGcnseqnoScreen() {
    }

    
    /** full constructor */
    public Radidc0DdiDcGcnseqnoScreen(Radidc0DdiDcGcnseqnoScreenId id, Integer ddiDcDaysScreenAmount) {
        this.id = id;
        this.ddiDcDaysScreenAmount = ddiDcDaysScreenAmount;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="ddiMonox", column=@Column(name="DDI_MONOX", nullable=false, precision=5, scale=0) ), 
        @AttributeOverride(name="gcnSeqno", column=@Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0) ) } )

    public Radidc0DdiDcGcnseqnoScreenId getId() {
        return this.id;
    }
    
    public void setId(Radidc0DdiDcGcnseqnoScreenId id) {
        this.id = id;
    }
    
    @Column(name="DDI_DC_DAYS_SCREEN_AMOUNT", nullable=false, precision=5, scale=0)

    public Integer getDdiDcDaysScreenAmount() {
        return this.ddiDcDaysScreenAmount;
    }
    
    public void setDdiDcDaysScreenAmount(Integer ddiDcDaysScreenAmount) {
        this.ddiDcDaysScreenAmount = ddiDcDaysScreenAmount;
    }
   








}