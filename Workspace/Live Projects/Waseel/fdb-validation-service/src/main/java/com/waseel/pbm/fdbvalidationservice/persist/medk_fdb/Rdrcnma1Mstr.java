package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rdrcnma1Mstr entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RDRCNMA1_MSTR"
    ,schema="MEDK_FDB"
)

public class Rdrcnma1Mstr  implements java.io.Serializable {


    // Fields    

     private Rdrcnma1MstrId id;
     private String neomGestBirthAgeReqInd;
     private String neomWeightReqInd;
     private Double dr2Lodosd;
     private String dr2Lodosu;
     private Double dr2Hidosd;
     private String dr2Hidosu;
     private Double dr2Mxdosd;
     private String dr2Mxdosu;
     private Double dr2Lofreq;
     private Double dr2Hifreq;
     private String dr2Renimp;
     private Short dr2Crclth;
     private String dr2Crclu;
     private Integer dr2Lodotx;
     private Integer dr2Hidotx;
     private Integer dr2Mxdotx;
     private String dr2Hepimp;
     private Double dr2Thaflo;
     private Double dr2Thafhi;
     private String dr2Thafu;
     private Double dr2Mx1dos;
     private String dr2Mx1dsu;
     private Double dr2Mxlifd;
     private String dr2Mxlifu;
     private Integer dxid;
     private Double nteSingleDose;
     private String nteSingleDoseUnitCode;
     private Short dosingAgeSourceId;


    // Constructors

    /** default constructor */
    public Rdrcnma1Mstr() {
    }

	/** minimal constructor */
    public Rdrcnma1Mstr(Rdrcnma1MstrId id, Double nteSingleDose, String nteSingleDoseUnitCode, Short dosingAgeSourceId) {
        this.id = id;
        this.nteSingleDose = nteSingleDose;
        this.nteSingleDoseUnitCode = nteSingleDoseUnitCode;
        this.dosingAgeSourceId = dosingAgeSourceId;
    }
    
    /** full constructor */
    public Rdrcnma1Mstr(Rdrcnma1MstrId id, String neomGestBirthAgeReqInd, String neomWeightReqInd, Double dr2Lodosd, String dr2Lodosu, Double dr2Hidosd, String dr2Hidosu, Double dr2Mxdosd, String dr2Mxdosu, Double dr2Lofreq, Double dr2Hifreq, String dr2Renimp, Short dr2Crclth, String dr2Crclu, Integer dr2Lodotx, Integer dr2Hidotx, Integer dr2Mxdotx, String dr2Hepimp, Double dr2Thaflo, Double dr2Thafhi, String dr2Thafu, Double dr2Mx1dos, String dr2Mx1dsu, Double dr2Mxlifd, String dr2Mxlifu, Integer dxid, Double nteSingleDose, String nteSingleDoseUnitCode, Short dosingAgeSourceId) {
        this.id = id;
        this.neomGestBirthAgeReqInd = neomGestBirthAgeReqInd;
        this.neomWeightReqInd = neomWeightReqInd;
        this.dr2Lodosd = dr2Lodosd;
        this.dr2Lodosu = dr2Lodosu;
        this.dr2Hidosd = dr2Hidosd;
        this.dr2Hidosu = dr2Hidosu;
        this.dr2Mxdosd = dr2Mxdosd;
        this.dr2Mxdosu = dr2Mxdosu;
        this.dr2Lofreq = dr2Lofreq;
        this.dr2Hifreq = dr2Hifreq;
        this.dr2Renimp = dr2Renimp;
        this.dr2Crclth = dr2Crclth;
        this.dr2Crclu = dr2Crclu;
        this.dr2Lodotx = dr2Lodotx;
        this.dr2Hidotx = dr2Hidotx;
        this.dr2Mxdotx = dr2Mxdotx;
        this.dr2Hepimp = dr2Hepimp;
        this.dr2Thaflo = dr2Thaflo;
        this.dr2Thafhi = dr2Thafhi;
        this.dr2Thafu = dr2Thafu;
        this.dr2Mx1dos = dr2Mx1dos;
        this.dr2Mx1dsu = dr2Mx1dsu;
        this.dr2Mxlifd = dr2Mxlifd;
        this.dr2Mxlifu = dr2Mxlifu;
        this.dxid = dxid;
        this.nteSingleDose = nteSingleDose;
        this.nteSingleDoseUnitCode = nteSingleDoseUnitCode;
        this.dosingAgeSourceId = dosingAgeSourceId;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="gcnSeqno", column=@Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0) ), 
        @AttributeOverride(name="dr2Rt", column=@Column(name="DR2_RT", nullable=false, length=3) ), 
        @AttributeOverride(name="dr2Loaged", column=@Column(name="DR2_LOAGED", nullable=false, precision=5, scale=0) ), 
        @AttributeOverride(name="dr2Hiaged", column=@Column(name="DR2_HIAGED", nullable=false, precision=5, scale=0) ), 
        @AttributeOverride(name="fdbdx", column=@Column(name="FDBDX", nullable=false, length=9) ), 
        @AttributeOverride(name="dr2Dostpi", column=@Column(name="DR2_DOSTPI", nullable=false, length=2) ), 
        @AttributeOverride(name="neomLowGestBirthAgeWeeks", column=@Column(name="NEOM_LOW_GEST_BIRTH_AGE_WEEKS", nullable=false, precision=2, scale=0) ), 
        @AttributeOverride(name="neomHighGestBirthAgeWeeks", column=@Column(name="NEOM_HIGH_GEST_BIRTH_AGE_WEEKS", nullable=false, precision=2, scale=0) ), 
        @AttributeOverride(name="neomLowCurrentWeightGrams", column=@Column(name="NEOM_LOW_CURRENT_WEIGHT_GRAMS", nullable=false, precision=5, scale=0) ), 
        @AttributeOverride(name="neomHighCurrentWeightGrams", column=@Column(name="NEOM_HIGH_CURRENT_WEIGHT_GRAMS", nullable=false, precision=5, scale=0) ) } )

    public Rdrcnma1MstrId getId() {
        return this.id;
    }
    
    public void setId(Rdrcnma1MstrId id) {
        this.id = id;
    }
    
    @Column(name="NEOM_GEST_BIRTH_AGE_REQ_IND", length=1)

    public String getNeomGestBirthAgeReqInd() {
        return this.neomGestBirthAgeReqInd;
    }
    
    public void setNeomGestBirthAgeReqInd(String neomGestBirthAgeReqInd) {
        this.neomGestBirthAgeReqInd = neomGestBirthAgeReqInd;
    }
    
    @Column(name="NEOM_WEIGHT_REQ_IND", length=1)

    public String getNeomWeightReqInd() {
        return this.neomWeightReqInd;
    }
    
    public void setNeomWeightReqInd(String neomWeightReqInd) {
        this.neomWeightReqInd = neomWeightReqInd;
    }
    
    @Column(name="DR2_LODOSD", precision=8, scale=3)

    public Double getDr2Lodosd() {
        return this.dr2Lodosd;
    }
    
    public void setDr2Lodosd(Double dr2Lodosd) {
        this.dr2Lodosd = dr2Lodosd;
    }
    
    @Column(name="DR2_LODOSU", length=2)

    public String getDr2Lodosu() {
        return this.dr2Lodosu;
    }
    
    public void setDr2Lodosu(String dr2Lodosu) {
        this.dr2Lodosu = dr2Lodosu;
    }
    
    @Column(name="DR2_HIDOSD", precision=8, scale=3)

    public Double getDr2Hidosd() {
        return this.dr2Hidosd;
    }
    
    public void setDr2Hidosd(Double dr2Hidosd) {
        this.dr2Hidosd = dr2Hidosd;
    }
    
    @Column(name="DR2_HIDOSU", length=2)

    public String getDr2Hidosu() {
        return this.dr2Hidosu;
    }
    
    public void setDr2Hidosu(String dr2Hidosu) {
        this.dr2Hidosu = dr2Hidosu;
    }
    
    @Column(name="DR2_MXDOSD", precision=8, scale=3)

    public Double getDr2Mxdosd() {
        return this.dr2Mxdosd;
    }
    
    public void setDr2Mxdosd(Double dr2Mxdosd) {
        this.dr2Mxdosd = dr2Mxdosd;
    }
    
    @Column(name="DR2_MXDOSU", length=2)

    public String getDr2Mxdosu() {
        return this.dr2Mxdosu;
    }
    
    public void setDr2Mxdosu(String dr2Mxdosu) {
        this.dr2Mxdosu = dr2Mxdosu;
    }
    
    @Column(name="DR2_LOFREQ", precision=4)

    public Double getDr2Lofreq() {
        return this.dr2Lofreq;
    }
    
    public void setDr2Lofreq(Double dr2Lofreq) {
        this.dr2Lofreq = dr2Lofreq;
    }
    
    @Column(name="DR2_HIFREQ", precision=4)

    public Double getDr2Hifreq() {
        return this.dr2Hifreq;
    }
    
    public void setDr2Hifreq(Double dr2Hifreq) {
        this.dr2Hifreq = dr2Hifreq;
    }
    
    @Column(name="DR2_RENIMP", length=1)

    public String getDr2Renimp() {
        return this.dr2Renimp;
    }
    
    public void setDr2Renimp(String dr2Renimp) {
        this.dr2Renimp = dr2Renimp;
    }
    
    @Column(name="DR2_CRCLTH", precision=3, scale=0)

    public Short getDr2Crclth() {
        return this.dr2Crclth;
    }
    
    public void setDr2Crclth(Short dr2Crclth) {
        this.dr2Crclth = dr2Crclth;
    }
    
    @Column(name="DR2_CRCLU", length=2)

    public String getDr2Crclu() {
        return this.dr2Crclu;
    }
    
    public void setDr2Crclu(String dr2Crclu) {
        this.dr2Crclu = dr2Crclu;
    }
    
    @Column(name="DR2_LODOTX", precision=5, scale=0)

    public Integer getDr2Lodotx() {
        return this.dr2Lodotx;
    }
    
    public void setDr2Lodotx(Integer dr2Lodotx) {
        this.dr2Lodotx = dr2Lodotx;
    }
    
    @Column(name="DR2_HIDOTX", precision=5, scale=0)

    public Integer getDr2Hidotx() {
        return this.dr2Hidotx;
    }
    
    public void setDr2Hidotx(Integer dr2Hidotx) {
        this.dr2Hidotx = dr2Hidotx;
    }
    
    @Column(name="DR2_MXDOTX", precision=5, scale=0)

    public Integer getDr2Mxdotx() {
        return this.dr2Mxdotx;
    }
    
    public void setDr2Mxdotx(Integer dr2Mxdotx) {
        this.dr2Mxdotx = dr2Mxdotx;
    }
    
    @Column(name="DR2_HEPIMP", length=1)

    public String getDr2Hepimp() {
        return this.dr2Hepimp;
    }
    
    public void setDr2Hepimp(String dr2Hepimp) {
        this.dr2Hepimp = dr2Hepimp;
    }
    
    @Column(name="DR2_THAFLO", precision=5)

    public Double getDr2Thaflo() {
        return this.dr2Thaflo;
    }
    
    public void setDr2Thaflo(Double dr2Thaflo) {
        this.dr2Thaflo = dr2Thaflo;
    }
    
    @Column(name="DR2_THAFHI", precision=5)

    public Double getDr2Thafhi() {
        return this.dr2Thafhi;
    }
    
    public void setDr2Thafhi(Double dr2Thafhi) {
        this.dr2Thafhi = dr2Thafhi;
    }
    
    @Column(name="DR2_THAFU", length=2)

    public String getDr2Thafu() {
        return this.dr2Thafu;
    }
    
    public void setDr2Thafu(String dr2Thafu) {
        this.dr2Thafu = dr2Thafu;
    }
    
    @Column(name="DR2_MX1DOS", precision=8, scale=3)

    public Double getDr2Mx1dos() {
        return this.dr2Mx1dos;
    }
    
    public void setDr2Mx1dos(Double dr2Mx1dos) {
        this.dr2Mx1dos = dr2Mx1dos;
    }
    
    @Column(name="DR2_MX1DSU", length=2)

    public String getDr2Mx1dsu() {
        return this.dr2Mx1dsu;
    }
    
    public void setDr2Mx1dsu(String dr2Mx1dsu) {
        this.dr2Mx1dsu = dr2Mx1dsu;
    }
    
    @Column(name="DR2_MXLIFD", precision=8, scale=3)

    public Double getDr2Mxlifd() {
        return this.dr2Mxlifd;
    }
    
    public void setDr2Mxlifd(Double dr2Mxlifd) {
        this.dr2Mxlifd = dr2Mxlifd;
    }
    
    @Column(name="DR2_MXLIFU", length=2)

    public String getDr2Mxlifu() {
        return this.dr2Mxlifu;
    }
    
    public void setDr2Mxlifu(String dr2Mxlifu) {
        this.dr2Mxlifu = dr2Mxlifu;
    }
    
    @Column(name="DXID", precision=8, scale=0)

    public Integer getDxid() {
        return this.dxid;
    }
    
    public void setDxid(Integer dxid) {
        this.dxid = dxid;
    }
    
    @Column(name="NTE_SINGLE_DOSE", nullable=false, precision=8, scale=3)

    public Double getNteSingleDose() {
        return this.nteSingleDose;
    }
    
    public void setNteSingleDose(Double nteSingleDose) {
        this.nteSingleDose = nteSingleDose;
    }
    
    @Column(name="NTE_SINGLE_DOSE_UNIT_CODE", nullable=false, length=2)

    public String getNteSingleDoseUnitCode() {
        return this.nteSingleDoseUnitCode;
    }
    
    public void setNteSingleDoseUnitCode(String nteSingleDoseUnitCode) {
        this.nteSingleDoseUnitCode = nteSingleDoseUnitCode;
    }
    
    @Column(name="DOSING_AGE_SOURCE_ID", nullable=false, precision=4, scale=0)

    public Short getDosingAgeSourceId() {
        return this.dosingAgeSourceId;
    }
    
    public void setDosingAgeSourceId(Short dosingAgeSourceId) {
        this.dosingAgeSourceId = dosingAgeSourceId;
    }
   








}