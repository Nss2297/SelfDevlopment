package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Retctbl0EtcId entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RETCTBL0_ETC_ID"
    ,schema="MEDK_FDB"
)

public class Retctbl0EtcId  implements java.io.Serializable {


    // Fields    

     private Integer etcId;
     private String etcName;
     private String etcUltimateChildInd;
     private String etcDrugConceptLinkInd;
     private Integer etcParentEtcId;
     private String etcFormularyLevelInd;
     private Integer etcPresentationSeqno;
     private Integer etcUltimateParentEtcId;
     private Byte etcHierarchyLevel;
     private Integer etcSortNumber;
     private String etcRetiredInd;
     private Timestamp etcRetiredDate;


    // Constructors

    /** default constructor */
    public Retctbl0EtcId() {
    }

	/** minimal constructor */
    public Retctbl0EtcId(Integer etcId) {
        this.etcId = etcId;
    }
    
    /** full constructor */
    public Retctbl0EtcId(Integer etcId, String etcName, String etcUltimateChildInd, String etcDrugConceptLinkInd, Integer etcParentEtcId, String etcFormularyLevelInd, Integer etcPresentationSeqno, Integer etcUltimateParentEtcId, Byte etcHierarchyLevel, Integer etcSortNumber, String etcRetiredInd, Timestamp etcRetiredDate) {
        this.etcId = etcId;
        this.etcName = etcName;
        this.etcUltimateChildInd = etcUltimateChildInd;
        this.etcDrugConceptLinkInd = etcDrugConceptLinkInd;
        this.etcParentEtcId = etcParentEtcId;
        this.etcFormularyLevelInd = etcFormularyLevelInd;
        this.etcPresentationSeqno = etcPresentationSeqno;
        this.etcUltimateParentEtcId = etcUltimateParentEtcId;
        this.etcHierarchyLevel = etcHierarchyLevel;
        this.etcSortNumber = etcSortNumber;
        this.etcRetiredInd = etcRetiredInd;
        this.etcRetiredDate = etcRetiredDate;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="ETC_ID", unique=true, nullable=false, precision=8, scale=0)

    public Integer getEtcId() {
        return this.etcId;
    }
    
    public void setEtcId(Integer etcId) {
        this.etcId = etcId;
    }
    
    @Column(name="ETC_NAME", length=70)

    public String getEtcName() {
        return this.etcName;
    }
    
    public void setEtcName(String etcName) {
        this.etcName = etcName;
    }
    
    @Column(name="ETC_ULTIMATE_CHILD_IND", length=1)

    public String getEtcUltimateChildInd() {
        return this.etcUltimateChildInd;
    }
    
    public void setEtcUltimateChildInd(String etcUltimateChildInd) {
        this.etcUltimateChildInd = etcUltimateChildInd;
    }
    
    @Column(name="ETC_DRUG_CONCEPT_LINK_IND", length=1)

    public String getEtcDrugConceptLinkInd() {
        return this.etcDrugConceptLinkInd;
    }
    
    public void setEtcDrugConceptLinkInd(String etcDrugConceptLinkInd) {
        this.etcDrugConceptLinkInd = etcDrugConceptLinkInd;
    }
    
    @Column(name="ETC_PARENT_ETC_ID", precision=8, scale=0)

    public Integer getEtcParentEtcId() {
        return this.etcParentEtcId;
    }
    
    public void setEtcParentEtcId(Integer etcParentEtcId) {
        this.etcParentEtcId = etcParentEtcId;
    }
    
    @Column(name="ETC_FORMULARY_LEVEL_IND", length=1)

    public String getEtcFormularyLevelInd() {
        return this.etcFormularyLevelInd;
    }
    
    public void setEtcFormularyLevelInd(String etcFormularyLevelInd) {
        this.etcFormularyLevelInd = etcFormularyLevelInd;
    }
    
    @Column(name="ETC_PRESENTATION_SEQNO", precision=5, scale=0)

    public Integer getEtcPresentationSeqno() {
        return this.etcPresentationSeqno;
    }
    
    public void setEtcPresentationSeqno(Integer etcPresentationSeqno) {
        this.etcPresentationSeqno = etcPresentationSeqno;
    }
    
    @Column(name="ETC_ULTIMATE_PARENT_ETC_ID", precision=8, scale=0)

    public Integer getEtcUltimateParentEtcId() {
        return this.etcUltimateParentEtcId;
    }
    
    public void setEtcUltimateParentEtcId(Integer etcUltimateParentEtcId) {
        this.etcUltimateParentEtcId = etcUltimateParentEtcId;
    }
    
    @Column(name="ETC_HIERARCHY_LEVEL", precision=2, scale=0)

    public Byte getEtcHierarchyLevel() {
        return this.etcHierarchyLevel;
    }
    
    public void setEtcHierarchyLevel(Byte etcHierarchyLevel) {
        this.etcHierarchyLevel = etcHierarchyLevel;
    }
    
    @Column(name="ETC_SORT_NUMBER", precision=5, scale=0)

    public Integer getEtcSortNumber() {
        return this.etcSortNumber;
    }
    
    public void setEtcSortNumber(Integer etcSortNumber) {
        this.etcSortNumber = etcSortNumber;
    }
    
    @Column(name="ETC_RETIRED_IND", length=1)

    public String getEtcRetiredInd() {
        return this.etcRetiredInd;
    }
    
    public void setEtcRetiredInd(String etcRetiredInd) {
        this.etcRetiredInd = etcRetiredInd;
    }
    
    @Column(name="ETC_RETIRED_DATE", length=7)

    public Timestamp getEtcRetiredDate() {
        return this.etcRetiredDate;
    }
    
    public void setEtcRetiredDate(Timestamp etcRetiredDate) {
        this.etcRetiredDate = etcRetiredDate;
    }
   








}