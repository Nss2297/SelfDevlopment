package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Radimm0MonoxMstr entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RADIMM0_MONOX_MSTR"
    ,schema="MEDK_FDB"
)

public class Radimm0MonoxMstr  implements java.io.Serializable {


    // Fields    

     private Integer ddiMonox;
     private String monoxTitle;
     private String ddiSl;
     private Integer ddiDisplayActionId;
     private Integer sideADdiCodex;
     private Integer sideBDdiCodex;
     private String ddiPharmacodynamicInd;
     private String ddiPharmacokineticInd;
     private Timestamp monoxEndDt;


    // Constructors

    /** default constructor */
    public Radimm0MonoxMstr() {
    }

	/** minimal constructor */
    public Radimm0MonoxMstr(Integer ddiMonox, String monoxTitle, String ddiSl, Integer ddiDisplayActionId, Integer sideADdiCodex, Integer sideBDdiCodex, String ddiPharmacodynamicInd, String ddiPharmacokineticInd) {
        this.ddiMonox = ddiMonox;
        this.monoxTitle = monoxTitle;
        this.ddiSl = ddiSl;
        this.ddiDisplayActionId = ddiDisplayActionId;
        this.sideADdiCodex = sideADdiCodex;
        this.sideBDdiCodex = sideBDdiCodex;
        this.ddiPharmacodynamicInd = ddiPharmacodynamicInd;
        this.ddiPharmacokineticInd = ddiPharmacokineticInd;
    }
    
    /** full constructor */
    public Radimm0MonoxMstr(Integer ddiMonox, String monoxTitle, String ddiSl, Integer ddiDisplayActionId, Integer sideADdiCodex, Integer sideBDdiCodex, String ddiPharmacodynamicInd, String ddiPharmacokineticInd, Timestamp monoxEndDt) {
        this.ddiMonox = ddiMonox;
        this.monoxTitle = monoxTitle;
        this.ddiSl = ddiSl;
        this.ddiDisplayActionId = ddiDisplayActionId;
        this.sideADdiCodex = sideADdiCodex;
        this.sideBDdiCodex = sideBDdiCodex;
        this.ddiPharmacodynamicInd = ddiPharmacodynamicInd;
        this.ddiPharmacokineticInd = ddiPharmacokineticInd;
        this.monoxEndDt = monoxEndDt;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="DDI_MONOX", unique=true, nullable=false, precision=5, scale=0)

    public Integer getDdiMonox() {
        return this.ddiMonox;
    }
    
    public void setDdiMonox(Integer ddiMonox) {
        this.ddiMonox = ddiMonox;
    }
    
    @Column(name="MONOX_TITLE", nullable=false)

    public String getMonoxTitle() {
        return this.monoxTitle;
    }
    
    public void setMonoxTitle(String monoxTitle) {
        this.monoxTitle = monoxTitle;
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
    
    @Column(name="SIDE_A_DDI_CODEX", nullable=false, precision=5, scale=0)

    public Integer getSideADdiCodex() {
        return this.sideADdiCodex;
    }
    
    public void setSideADdiCodex(Integer sideADdiCodex) {
        this.sideADdiCodex = sideADdiCodex;
    }
    
    @Column(name="SIDE_B_DDI_CODEX", nullable=false, precision=5, scale=0)

    public Integer getSideBDdiCodex() {
        return this.sideBDdiCodex;
    }
    
    public void setSideBDdiCodex(Integer sideBDdiCodex) {
        this.sideBDdiCodex = sideBDdiCodex;
    }
    
    @Column(name="DDI_PHARMACODYNAMIC_IND", nullable=false, length=1)

    public String getDdiPharmacodynamicInd() {
        return this.ddiPharmacodynamicInd;
    }
    
    public void setDdiPharmacodynamicInd(String ddiPharmacodynamicInd) {
        this.ddiPharmacodynamicInd = ddiPharmacodynamicInd;
    }
    
    @Column(name="DDI_PHARMACOKINETIC_IND", nullable=false, length=1)

    public String getDdiPharmacokineticInd() {
        return this.ddiPharmacokineticInd;
    }
    
    public void setDdiPharmacokineticInd(String ddiPharmacokineticInd) {
        this.ddiPharmacokineticInd = ddiPharmacokineticInd;
    }
    
    @Column(name="MONOX_END_DT", length=7)

    public Timestamp getMonoxEndDt() {
        return this.monoxEndDt;
    }
    
    public void setMonoxEndDt(Timestamp monoxEndDt) {
        this.monoxEndDt = monoxEndDt;
    }
   








}