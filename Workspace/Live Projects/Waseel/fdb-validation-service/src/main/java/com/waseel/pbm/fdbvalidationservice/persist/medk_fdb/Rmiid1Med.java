package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rmiid1Med entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMIID1_MED"
    ,schema="MEDK_FDB"
)

public class Rmiid1Med  implements java.io.Serializable {


    // Fields    

     private Integer medid;
     private Integer routedDosageFormMedId;
     private String medStrength;
     private String medStrengthUom;
     private String medMedidDesc;
     private Integer gcnSeqno;
     private String medGcnseqnoAssignCd;
     private String medNameSourceCd;
     private String medRefFedLegendInd;
     private String medRefDeaCd;
     private String medRefMultiSourceCd;
     private String medRefGenDrugNameCd;
     private String medRefGenCompPriceCd;
     private String medRefGenSpreadCd;
     private String medRefInnovInd;
     private String medRefGenTheraEquCd;
     private String medRefDesiInd;
     private String medRefDesi2Ind;
     private String medStatusCd;
     private Integer genericMedid;


    // Constructors

    /** default constructor */
    public Rmiid1Med() {
    }

	/** minimal constructor */
    public Rmiid1Med(Integer medid, Integer routedDosageFormMedId, String medMedidDesc, Integer gcnSeqno, String medGcnseqnoAssignCd, String medNameSourceCd, String medRefFedLegendInd, String medRefDeaCd, String medRefMultiSourceCd, String medRefGenDrugNameCd, String medRefGenCompPriceCd, String medRefGenSpreadCd, String medRefInnovInd, String medRefGenTheraEquCd, String medRefDesiInd, String medRefDesi2Ind, String medStatusCd) {
        this.medid = medid;
        this.routedDosageFormMedId = routedDosageFormMedId;
        this.medMedidDesc = medMedidDesc;
        this.gcnSeqno = gcnSeqno;
        this.medGcnseqnoAssignCd = medGcnseqnoAssignCd;
        this.medNameSourceCd = medNameSourceCd;
        this.medRefFedLegendInd = medRefFedLegendInd;
        this.medRefDeaCd = medRefDeaCd;
        this.medRefMultiSourceCd = medRefMultiSourceCd;
        this.medRefGenDrugNameCd = medRefGenDrugNameCd;
        this.medRefGenCompPriceCd = medRefGenCompPriceCd;
        this.medRefGenSpreadCd = medRefGenSpreadCd;
        this.medRefInnovInd = medRefInnovInd;
        this.medRefGenTheraEquCd = medRefGenTheraEquCd;
        this.medRefDesiInd = medRefDesiInd;
        this.medRefDesi2Ind = medRefDesi2Ind;
        this.medStatusCd = medStatusCd;
    }
    
    /** full constructor */
    public Rmiid1Med(Integer medid, Integer routedDosageFormMedId, String medStrength, String medStrengthUom, String medMedidDesc, Integer gcnSeqno, String medGcnseqnoAssignCd, String medNameSourceCd, String medRefFedLegendInd, String medRefDeaCd, String medRefMultiSourceCd, String medRefGenDrugNameCd, String medRefGenCompPriceCd, String medRefGenSpreadCd, String medRefInnovInd, String medRefGenTheraEquCd, String medRefDesiInd, String medRefDesi2Ind, String medStatusCd, Integer genericMedid) {
        this.medid = medid;
        this.routedDosageFormMedId = routedDosageFormMedId;
        this.medStrength = medStrength;
        this.medStrengthUom = medStrengthUom;
        this.medMedidDesc = medMedidDesc;
        this.gcnSeqno = gcnSeqno;
        this.medGcnseqnoAssignCd = medGcnseqnoAssignCd;
        this.medNameSourceCd = medNameSourceCd;
        this.medRefFedLegendInd = medRefFedLegendInd;
        this.medRefDeaCd = medRefDeaCd;
        this.medRefMultiSourceCd = medRefMultiSourceCd;
        this.medRefGenDrugNameCd = medRefGenDrugNameCd;
        this.medRefGenCompPriceCd = medRefGenCompPriceCd;
        this.medRefGenSpreadCd = medRefGenSpreadCd;
        this.medRefInnovInd = medRefInnovInd;
        this.medRefGenTheraEquCd = medRefGenTheraEquCd;
        this.medRefDesiInd = medRefDesiInd;
        this.medRefDesi2Ind = medRefDesi2Ind;
        this.medStatusCd = medStatusCd;
        this.genericMedid = genericMedid;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="MEDID", unique=true, nullable=false, precision=8, scale=0)

    public Integer getMedid() {
        return this.medid;
    }
    
    public void setMedid(Integer medid) {
        this.medid = medid;
    }
    
    @Column(name="ROUTED_DOSAGE_FORM_MED_ID", nullable=false, precision=8, scale=0)

    public Integer getRoutedDosageFormMedId() {
        return this.routedDosageFormMedId;
    }
    
    public void setRoutedDosageFormMedId(Integer routedDosageFormMedId) {
        this.routedDosageFormMedId = routedDosageFormMedId;
    }
    
    @Column(name="MED_STRENGTH", length=15)

    public String getMedStrength() {
        return this.medStrength;
    }
    
    public void setMedStrength(String medStrength) {
        this.medStrength = medStrength;
    }
    
    @Column(name="MED_STRENGTH_UOM", length=15)

    public String getMedStrengthUom() {
        return this.medStrengthUom;
    }
    
    public void setMedStrengthUom(String medStrengthUom) {
        this.medStrengthUom = medStrengthUom;
    }
    
    @Column(name="MED_MEDID_DESC", nullable=false, length=70)

    public String getMedMedidDesc() {
        return this.medMedidDesc;
    }
    
    public void setMedMedidDesc(String medMedidDesc) {
        this.medMedidDesc = medMedidDesc;
    }
    
    @Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0)

    public Integer getGcnSeqno() {
        return this.gcnSeqno;
    }
    
    public void setGcnSeqno(Integer gcnSeqno) {
        this.gcnSeqno = gcnSeqno;
    }
    
    @Column(name="MED_GCNSEQNO_ASSIGN_CD", nullable=false, length=1)

    public String getMedGcnseqnoAssignCd() {
        return this.medGcnseqnoAssignCd;
    }
    
    public void setMedGcnseqnoAssignCd(String medGcnseqnoAssignCd) {
        this.medGcnseqnoAssignCd = medGcnseqnoAssignCd;
    }
    
    @Column(name="MED_NAME_SOURCE_CD", nullable=false, length=1)

    public String getMedNameSourceCd() {
        return this.medNameSourceCd;
    }
    
    public void setMedNameSourceCd(String medNameSourceCd) {
        this.medNameSourceCd = medNameSourceCd;
    }
    
    @Column(name="MED_REF_FED_LEGEND_IND", nullable=false, length=1)

    public String getMedRefFedLegendInd() {
        return this.medRefFedLegendInd;
    }
    
    public void setMedRefFedLegendInd(String medRefFedLegendInd) {
        this.medRefFedLegendInd = medRefFedLegendInd;
    }
    
    @Column(name="MED_REF_DEA_CD", nullable=false, length=1)

    public String getMedRefDeaCd() {
        return this.medRefDeaCd;
    }
    
    public void setMedRefDeaCd(String medRefDeaCd) {
        this.medRefDeaCd = medRefDeaCd;
    }
    
    @Column(name="MED_REF_MULTI_SOURCE_CD", nullable=false, length=1)

    public String getMedRefMultiSourceCd() {
        return this.medRefMultiSourceCd;
    }
    
    public void setMedRefMultiSourceCd(String medRefMultiSourceCd) {
        this.medRefMultiSourceCd = medRefMultiSourceCd;
    }
    
    @Column(name="MED_REF_GEN_DRUG_NAME_CD", nullable=false, length=1)

    public String getMedRefGenDrugNameCd() {
        return this.medRefGenDrugNameCd;
    }
    
    public void setMedRefGenDrugNameCd(String medRefGenDrugNameCd) {
        this.medRefGenDrugNameCd = medRefGenDrugNameCd;
    }
    
    @Column(name="MED_REF_GEN_COMP_PRICE_CD", nullable=false, length=1)

    public String getMedRefGenCompPriceCd() {
        return this.medRefGenCompPriceCd;
    }
    
    public void setMedRefGenCompPriceCd(String medRefGenCompPriceCd) {
        this.medRefGenCompPriceCd = medRefGenCompPriceCd;
    }
    
    @Column(name="MED_REF_GEN_SPREAD_CD", nullable=false, length=1)

    public String getMedRefGenSpreadCd() {
        return this.medRefGenSpreadCd;
    }
    
    public void setMedRefGenSpreadCd(String medRefGenSpreadCd) {
        this.medRefGenSpreadCd = medRefGenSpreadCd;
    }
    
    @Column(name="MED_REF_INNOV_IND", nullable=false, length=1)

    public String getMedRefInnovInd() {
        return this.medRefInnovInd;
    }
    
    public void setMedRefInnovInd(String medRefInnovInd) {
        this.medRefInnovInd = medRefInnovInd;
    }
    
    @Column(name="MED_REF_GEN_THERA_EQU_CD", nullable=false, length=1)

    public String getMedRefGenTheraEquCd() {
        return this.medRefGenTheraEquCd;
    }
    
    public void setMedRefGenTheraEquCd(String medRefGenTheraEquCd) {
        this.medRefGenTheraEquCd = medRefGenTheraEquCd;
    }
    
    @Column(name="MED_REF_DESI_IND", nullable=false, length=1)

    public String getMedRefDesiInd() {
        return this.medRefDesiInd;
    }
    
    public void setMedRefDesiInd(String medRefDesiInd) {
        this.medRefDesiInd = medRefDesiInd;
    }
    
    @Column(name="MED_REF_DESI2_IND", nullable=false, length=1)

    public String getMedRefDesi2Ind() {
        return this.medRefDesi2Ind;
    }
    
    public void setMedRefDesi2Ind(String medRefDesi2Ind) {
        this.medRefDesi2Ind = medRefDesi2Ind;
    }
    
    @Column(name="MED_STATUS_CD", nullable=false, length=1)

    public String getMedStatusCd() {
        return this.medStatusCd;
    }
    
    public void setMedStatusCd(String medStatusCd) {
        this.medStatusCd = medStatusCd;
    }
    
    @Column(name="GENERIC_MEDID", precision=8, scale=0)

    public Integer getGenericMedid() {
        return this.genericMedid;
    }
    
    public void setGenericMedid(Integer genericMedid) {
        this.genericMedid = genericMedid;
    }
   








}