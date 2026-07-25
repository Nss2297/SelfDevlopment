package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rgerima1GeriMstr entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RGERIMA1_GERI_MSTR"
    ,schema="MEDK_FDB"
)

public class Rgerima1GeriMstr  implements java.io.Serializable {


    // Fields    

     private Integer geriCode;
     private String geriDesc;
     private String geriSl;
     private String geriRnl;
     private String geriHep;
     private String geriCard;
     private String geriPulm;
     private String geriNeur;
     private String geriEnd;
     private String geriBeersInd;
     private String geriHedisInd;
     private String geriStoppInd;
     private String geriNarrative;


    // Constructors

    /** default constructor */
    public Rgerima1GeriMstr() {
    }

    
    /** full constructor */
    public Rgerima1GeriMstr(Integer geriCode, String geriDesc, String geriSl, String geriRnl, String geriHep, String geriCard, String geriPulm, String geriNeur, String geriEnd, String geriBeersInd, String geriHedisInd, String geriStoppInd, String geriNarrative) {
        this.geriCode = geriCode;
        this.geriDesc = geriDesc;
        this.geriSl = geriSl;
        this.geriRnl = geriRnl;
        this.geriHep = geriHep;
        this.geriCard = geriCard;
        this.geriPulm = geriPulm;
        this.geriNeur = geriNeur;
        this.geriEnd = geriEnd;
        this.geriBeersInd = geriBeersInd;
        this.geriHedisInd = geriHedisInd;
        this.geriStoppInd = geriStoppInd;
        this.geriNarrative = geriNarrative;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="GERI_CODE", unique=true, nullable=false, precision=6, scale=0)

    public Integer getGeriCode() {
        return this.geriCode;
    }
    
    public void setGeriCode(Integer geriCode) {
        this.geriCode = geriCode;
    }
    
    @Column(name="GERI_DESC", nullable=false, length=41)

    public String getGeriDesc() {
        return this.geriDesc;
    }
    
    public void setGeriDesc(String geriDesc) {
        this.geriDesc = geriDesc;
    }
    
    @Column(name="GERI_SL", nullable=false, length=1)

    public String getGeriSl() {
        return this.geriSl;
    }
    
    public void setGeriSl(String geriSl) {
        this.geriSl = geriSl;
    }
    
    @Column(name="GERI_RNL", nullable=false, length=1)

    public String getGeriRnl() {
        return this.geriRnl;
    }
    
    public void setGeriRnl(String geriRnl) {
        this.geriRnl = geriRnl;
    }
    
    @Column(name="GERI_HEP", nullable=false, length=1)

    public String getGeriHep() {
        return this.geriHep;
    }
    
    public void setGeriHep(String geriHep) {
        this.geriHep = geriHep;
    }
    
    @Column(name="GERI_CARD", nullable=false, length=1)

    public String getGeriCard() {
        return this.geriCard;
    }
    
    public void setGeriCard(String geriCard) {
        this.geriCard = geriCard;
    }
    
    @Column(name="GERI_PULM", nullable=false, length=1)

    public String getGeriPulm() {
        return this.geriPulm;
    }
    
    public void setGeriPulm(String geriPulm) {
        this.geriPulm = geriPulm;
    }
    
    @Column(name="GERI_NEUR", nullable=false, length=1)

    public String getGeriNeur() {
        return this.geriNeur;
    }
    
    public void setGeriNeur(String geriNeur) {
        this.geriNeur = geriNeur;
    }
    
    @Column(name="GERI_END", nullable=false, length=1)

    public String getGeriEnd() {
        return this.geriEnd;
    }
    
    public void setGeriEnd(String geriEnd) {
        this.geriEnd = geriEnd;
    }
    
    @Column(name="GERI_BEERS_IND", nullable=false, length=1)

    public String getGeriBeersInd() {
        return this.geriBeersInd;
    }
    
    public void setGeriBeersInd(String geriBeersInd) {
        this.geriBeersInd = geriBeersInd;
    }
    
    @Column(name="GERI_HEDIS_IND", nullable=false, length=1)

    public String getGeriHedisInd() {
        return this.geriHedisInd;
    }
    
    public void setGeriHedisInd(String geriHedisInd) {
        this.geriHedisInd = geriHedisInd;
    }
    
    @Column(name="GERI_STOPP_IND", nullable=false, length=1)

    public String getGeriStoppInd() {
        return this.geriStoppInd;
    }
    
    public void setGeriStoppInd(String geriStoppInd) {
        this.geriStoppInd = geriStoppInd;
    }
    
    @Column(name="GERI_NARRATIVE", nullable=false, length=500)

    public String getGeriNarrative() {
        return this.geriNarrative;
    }
    
    public void setGeriNarrative(String geriNarrative) {
        this.geriNarrative = geriNarrative;
    }
   








}