package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rdrcrm0RenalMstrId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rdrcrm0RenalMstrId  implements java.io.Serializable {


    // Fields    

     private Integer gcnSeqno;
     private String dr2Rt;
     private Integer renLoaged;
     private Integer renHiaged;
     private String fdbdx;
     private String dr2Dostpi;
     private Short renLocrcl;
     private Short renHicrcl;
     private Short renSortOrder;
     private Short dosingAdjTypeCd;
     private Double renLodosd;
     private String renLodosu;
     private Double renHidosd;
     private String renHidosu;
     private Double renMxdosd;
     private String renMxdosu;
     private Double renLofreq;
     private Double renHifreq;
     private Double renMx1dos;
     private String renMx1dsu;
     private Integer dxid;
     private Double renNteSingleDose;
     private String renNteSingleDoseUnitCode;
     private String renFootnote;
     private Integer renMonoId;


    // Constructors

    /** default constructor */
    public Rdrcrm0RenalMstrId() {
    }

	/** minimal constructor */
    public Rdrcrm0RenalMstrId(Integer gcnSeqno, String dr2Rt, Integer renLoaged, Integer renHiaged, String fdbdx, String dr2Dostpi, Short renLocrcl, Short renHicrcl, Short renSortOrder, Short dosingAdjTypeCd, Integer dxid) {
        this.gcnSeqno = gcnSeqno;
        this.dr2Rt = dr2Rt;
        this.renLoaged = renLoaged;
        this.renHiaged = renHiaged;
        this.fdbdx = fdbdx;
        this.dr2Dostpi = dr2Dostpi;
        this.renLocrcl = renLocrcl;
        this.renHicrcl = renHicrcl;
        this.renSortOrder = renSortOrder;
        this.dosingAdjTypeCd = dosingAdjTypeCd;
        this.dxid = dxid;
    }
    
    /** full constructor */
    public Rdrcrm0RenalMstrId(Integer gcnSeqno, String dr2Rt, Integer renLoaged, Integer renHiaged, String fdbdx, String dr2Dostpi, Short renLocrcl, Short renHicrcl, Short renSortOrder, Short dosingAdjTypeCd, Double renLodosd, String renLodosu, Double renHidosd, String renHidosu, Double renMxdosd, String renMxdosu, Double renLofreq, Double renHifreq, Double renMx1dos, String renMx1dsu, Integer dxid, Double renNteSingleDose, String renNteSingleDoseUnitCode, String renFootnote, Integer renMonoId) {
        this.gcnSeqno = gcnSeqno;
        this.dr2Rt = dr2Rt;
        this.renLoaged = renLoaged;
        this.renHiaged = renHiaged;
        this.fdbdx = fdbdx;
        this.dr2Dostpi = dr2Dostpi;
        this.renLocrcl = renLocrcl;
        this.renHicrcl = renHicrcl;
        this.renSortOrder = renSortOrder;
        this.dosingAdjTypeCd = dosingAdjTypeCd;
        this.renLodosd = renLodosd;
        this.renLodosu = renLodosu;
        this.renHidosd = renHidosd;
        this.renHidosu = renHidosu;
        this.renMxdosd = renMxdosd;
        this.renMxdosu = renMxdosu;
        this.renLofreq = renLofreq;
        this.renHifreq = renHifreq;
        this.renMx1dos = renMx1dos;
        this.renMx1dsu = renMx1dsu;
        this.dxid = dxid;
        this.renNteSingleDose = renNteSingleDose;
        this.renNteSingleDoseUnitCode = renNteSingleDoseUnitCode;
        this.renFootnote = renFootnote;
        this.renMonoId = renMonoId;
    }

   
    // Property accessors

    @Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0)

    public Integer getGcnSeqno() {
        return this.gcnSeqno;
    }
    
    public void setGcnSeqno(Integer gcnSeqno) {
        this.gcnSeqno = gcnSeqno;
    }

    @Column(name="DR2_RT", nullable=false, length=3)

    public String getDr2Rt() {
        return this.dr2Rt;
    }
    
    public void setDr2Rt(String dr2Rt) {
        this.dr2Rt = dr2Rt;
    }

    @Column(name="REN_LOAGED", nullable=false, precision=5, scale=0)

    public Integer getRenLoaged() {
        return this.renLoaged;
    }
    
    public void setRenLoaged(Integer renLoaged) {
        this.renLoaged = renLoaged;
    }

    @Column(name="REN_HIAGED", nullable=false, precision=5, scale=0)

    public Integer getRenHiaged() {
        return this.renHiaged;
    }
    
    public void setRenHiaged(Integer renHiaged) {
        this.renHiaged = renHiaged;
    }

    @Column(name="FDBDX", nullable=false, length=9)

    public String getFdbdx() {
        return this.fdbdx;
    }
    
    public void setFdbdx(String fdbdx) {
        this.fdbdx = fdbdx;
    }

    @Column(name="DR2_DOSTPI", nullable=false, length=2)

    public String getDr2Dostpi() {
        return this.dr2Dostpi;
    }
    
    public void setDr2Dostpi(String dr2Dostpi) {
        this.dr2Dostpi = dr2Dostpi;
    }

    @Column(name="REN_LOCRCL", nullable=false, precision=3, scale=0)

    public Short getRenLocrcl() {
        return this.renLocrcl;
    }
    
    public void setRenLocrcl(Short renLocrcl) {
        this.renLocrcl = renLocrcl;
    }

    @Column(name="REN_HICRCL", nullable=false, precision=3, scale=0)

    public Short getRenHicrcl() {
        return this.renHicrcl;
    }
    
    public void setRenHicrcl(Short renHicrcl) {
        this.renHicrcl = renHicrcl;
    }

    @Column(name="REN_SORT_ORDER", nullable=false, precision=3, scale=0)

    public Short getRenSortOrder() {
        return this.renSortOrder;
    }
    
    public void setRenSortOrder(Short renSortOrder) {
        this.renSortOrder = renSortOrder;
    }

    @Column(name="DOSING_ADJ_TYPE_CD", nullable=false, precision=4, scale=0)

    public Short getDosingAdjTypeCd() {
        return this.dosingAdjTypeCd;
    }
    
    public void setDosingAdjTypeCd(Short dosingAdjTypeCd) {
        this.dosingAdjTypeCd = dosingAdjTypeCd;
    }

    @Column(name="REN_LODOSD", precision=8, scale=3)

    public Double getRenLodosd() {
        return this.renLodosd;
    }
    
    public void setRenLodosd(Double renLodosd) {
        this.renLodosd = renLodosd;
    }

    @Column(name="REN_LODOSU", length=2)

    public String getRenLodosu() {
        return this.renLodosu;
    }
    
    public void setRenLodosu(String renLodosu) {
        this.renLodosu = renLodosu;
    }

    @Column(name="REN_HIDOSD", precision=8, scale=3)

    public Double getRenHidosd() {
        return this.renHidosd;
    }
    
    public void setRenHidosd(Double renHidosd) {
        this.renHidosd = renHidosd;
    }

    @Column(name="REN_HIDOSU", length=2)

    public String getRenHidosu() {
        return this.renHidosu;
    }
    
    public void setRenHidosu(String renHidosu) {
        this.renHidosu = renHidosu;
    }

    @Column(name="REN_MXDOSD", precision=8, scale=3)

    public Double getRenMxdosd() {
        return this.renMxdosd;
    }
    
    public void setRenMxdosd(Double renMxdosd) {
        this.renMxdosd = renMxdosd;
    }

    @Column(name="REN_MXDOSU", length=2)

    public String getRenMxdosu() {
        return this.renMxdosu;
    }
    
    public void setRenMxdosu(String renMxdosu) {
        this.renMxdosu = renMxdosu;
    }

    @Column(name="REN_LOFREQ", precision=4)

    public Double getRenLofreq() {
        return this.renLofreq;
    }
    
    public void setRenLofreq(Double renLofreq) {
        this.renLofreq = renLofreq;
    }

    @Column(name="REN_HIFREQ", precision=4)

    public Double getRenHifreq() {
        return this.renHifreq;
    }
    
    public void setRenHifreq(Double renHifreq) {
        this.renHifreq = renHifreq;
    }

    @Column(name="REN_MX1DOS", precision=8, scale=3)

    public Double getRenMx1dos() {
        return this.renMx1dos;
    }
    
    public void setRenMx1dos(Double renMx1dos) {
        this.renMx1dos = renMx1dos;
    }

    @Column(name="REN_MX1DSU", length=2)

    public String getRenMx1dsu() {
        return this.renMx1dsu;
    }
    
    public void setRenMx1dsu(String renMx1dsu) {
        this.renMx1dsu = renMx1dsu;
    }

    @Column(name="DXID", nullable=false, precision=8, scale=0)

    public Integer getDxid() {
        return this.dxid;
    }
    
    public void setDxid(Integer dxid) {
        this.dxid = dxid;
    }

    @Column(name="REN_NTE_SINGLE_DOSE", precision=8, scale=3)

    public Double getRenNteSingleDose() {
        return this.renNteSingleDose;
    }
    
    public void setRenNteSingleDose(Double renNteSingleDose) {
        this.renNteSingleDose = renNteSingleDose;
    }

    @Column(name="REN_NTE_SINGLE_DOSE_UNIT_CODE", length=2)

    public String getRenNteSingleDoseUnitCode() {
        return this.renNteSingleDoseUnitCode;
    }
    
    public void setRenNteSingleDoseUnitCode(String renNteSingleDoseUnitCode) {
        this.renNteSingleDoseUnitCode = renNteSingleDoseUnitCode;
    }

    @Column(name="REN_FOOTNOTE")

    public String getRenFootnote() {
        return this.renFootnote;
    }
    
    public void setRenFootnote(String renFootnote) {
        this.renFootnote = renFootnote;
    }

    @Column(name="REN_MONO_ID", precision=8, scale=0)

    public Integer getRenMonoId() {
        return this.renMonoId;
    }
    
    public void setRenMonoId(Integer renMonoId) {
        this.renMonoId = renMonoId;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rdrcrm0RenalMstrId) ) return false;
		 Rdrcrm0RenalMstrId castOther = ( Rdrcrm0RenalMstrId ) other; 
         
		 return ( (this.getGcnSeqno()==castOther.getGcnSeqno()) || ( this.getGcnSeqno()!=null && castOther.getGcnSeqno()!=null && this.getGcnSeqno().equals(castOther.getGcnSeqno()) ) )
 && ( (this.getDr2Rt()==castOther.getDr2Rt()) || ( this.getDr2Rt()!=null && castOther.getDr2Rt()!=null && this.getDr2Rt().equals(castOther.getDr2Rt()) ) )
 && ( (this.getRenLoaged()==castOther.getRenLoaged()) || ( this.getRenLoaged()!=null && castOther.getRenLoaged()!=null && this.getRenLoaged().equals(castOther.getRenLoaged()) ) )
 && ( (this.getRenHiaged()==castOther.getRenHiaged()) || ( this.getRenHiaged()!=null && castOther.getRenHiaged()!=null && this.getRenHiaged().equals(castOther.getRenHiaged()) ) )
 && ( (this.getFdbdx()==castOther.getFdbdx()) || ( this.getFdbdx()!=null && castOther.getFdbdx()!=null && this.getFdbdx().equals(castOther.getFdbdx()) ) )
 && ( (this.getDr2Dostpi()==castOther.getDr2Dostpi()) || ( this.getDr2Dostpi()!=null && castOther.getDr2Dostpi()!=null && this.getDr2Dostpi().equals(castOther.getDr2Dostpi()) ) )
 && ( (this.getRenLocrcl()==castOther.getRenLocrcl()) || ( this.getRenLocrcl()!=null && castOther.getRenLocrcl()!=null && this.getRenLocrcl().equals(castOther.getRenLocrcl()) ) )
 && ( (this.getRenHicrcl()==castOther.getRenHicrcl()) || ( this.getRenHicrcl()!=null && castOther.getRenHicrcl()!=null && this.getRenHicrcl().equals(castOther.getRenHicrcl()) ) )
 && ( (this.getRenSortOrder()==castOther.getRenSortOrder()) || ( this.getRenSortOrder()!=null && castOther.getRenSortOrder()!=null && this.getRenSortOrder().equals(castOther.getRenSortOrder()) ) )
 && ( (this.getDosingAdjTypeCd()==castOther.getDosingAdjTypeCd()) || ( this.getDosingAdjTypeCd()!=null && castOther.getDosingAdjTypeCd()!=null && this.getDosingAdjTypeCd().equals(castOther.getDosingAdjTypeCd()) ) )
 && ( (this.getRenLodosd()==castOther.getRenLodosd()) || ( this.getRenLodosd()!=null && castOther.getRenLodosd()!=null && this.getRenLodosd().equals(castOther.getRenLodosd()) ) )
 && ( (this.getRenLodosu()==castOther.getRenLodosu()) || ( this.getRenLodosu()!=null && castOther.getRenLodosu()!=null && this.getRenLodosu().equals(castOther.getRenLodosu()) ) )
 && ( (this.getRenHidosd()==castOther.getRenHidosd()) || ( this.getRenHidosd()!=null && castOther.getRenHidosd()!=null && this.getRenHidosd().equals(castOther.getRenHidosd()) ) )
 && ( (this.getRenHidosu()==castOther.getRenHidosu()) || ( this.getRenHidosu()!=null && castOther.getRenHidosu()!=null && this.getRenHidosu().equals(castOther.getRenHidosu()) ) )
 && ( (this.getRenMxdosd()==castOther.getRenMxdosd()) || ( this.getRenMxdosd()!=null && castOther.getRenMxdosd()!=null && this.getRenMxdosd().equals(castOther.getRenMxdosd()) ) )
 && ( (this.getRenMxdosu()==castOther.getRenMxdosu()) || ( this.getRenMxdosu()!=null && castOther.getRenMxdosu()!=null && this.getRenMxdosu().equals(castOther.getRenMxdosu()) ) )
 && ( (this.getRenLofreq()==castOther.getRenLofreq()) || ( this.getRenLofreq()!=null && castOther.getRenLofreq()!=null && this.getRenLofreq().equals(castOther.getRenLofreq()) ) )
 && ( (this.getRenHifreq()==castOther.getRenHifreq()) || ( this.getRenHifreq()!=null && castOther.getRenHifreq()!=null && this.getRenHifreq().equals(castOther.getRenHifreq()) ) )
 && ( (this.getRenMx1dos()==castOther.getRenMx1dos()) || ( this.getRenMx1dos()!=null && castOther.getRenMx1dos()!=null && this.getRenMx1dos().equals(castOther.getRenMx1dos()) ) )
 && ( (this.getRenMx1dsu()==castOther.getRenMx1dsu()) || ( this.getRenMx1dsu()!=null && castOther.getRenMx1dsu()!=null && this.getRenMx1dsu().equals(castOther.getRenMx1dsu()) ) )
 && ( (this.getDxid()==castOther.getDxid()) || ( this.getDxid()!=null && castOther.getDxid()!=null && this.getDxid().equals(castOther.getDxid()) ) )
 && ( (this.getRenNteSingleDose()==castOther.getRenNteSingleDose()) || ( this.getRenNteSingleDose()!=null && castOther.getRenNteSingleDose()!=null && this.getRenNteSingleDose().equals(castOther.getRenNteSingleDose()) ) )
 && ( (this.getRenNteSingleDoseUnitCode()==castOther.getRenNteSingleDoseUnitCode()) || ( this.getRenNteSingleDoseUnitCode()!=null && castOther.getRenNteSingleDoseUnitCode()!=null && this.getRenNteSingleDoseUnitCode().equals(castOther.getRenNteSingleDoseUnitCode()) ) )
 && ( (this.getRenFootnote()==castOther.getRenFootnote()) || ( this.getRenFootnote()!=null && castOther.getRenFootnote()!=null && this.getRenFootnote().equals(castOther.getRenFootnote()) ) )
 && ( (this.getRenMonoId()==castOther.getRenMonoId()) || ( this.getRenMonoId()!=null && castOther.getRenMonoId()!=null && this.getRenMonoId().equals(castOther.getRenMonoId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getGcnSeqno() == null ? 0 : this.getGcnSeqno().hashCode() );
         result = 37 * result + ( getDr2Rt() == null ? 0 : this.getDr2Rt().hashCode() );
         result = 37 * result + ( getRenLoaged() == null ? 0 : this.getRenLoaged().hashCode() );
         result = 37 * result + ( getRenHiaged() == null ? 0 : this.getRenHiaged().hashCode() );
         result = 37 * result + ( getFdbdx() == null ? 0 : this.getFdbdx().hashCode() );
         result = 37 * result + ( getDr2Dostpi() == null ? 0 : this.getDr2Dostpi().hashCode() );
         result = 37 * result + ( getRenLocrcl() == null ? 0 : this.getRenLocrcl().hashCode() );
         result = 37 * result + ( getRenHicrcl() == null ? 0 : this.getRenHicrcl().hashCode() );
         result = 37 * result + ( getRenSortOrder() == null ? 0 : this.getRenSortOrder().hashCode() );
         result = 37 * result + ( getDosingAdjTypeCd() == null ? 0 : this.getDosingAdjTypeCd().hashCode() );
         result = 37 * result + ( getRenLodosd() == null ? 0 : this.getRenLodosd().hashCode() );
         result = 37 * result + ( getRenLodosu() == null ? 0 : this.getRenLodosu().hashCode() );
         result = 37 * result + ( getRenHidosd() == null ? 0 : this.getRenHidosd().hashCode() );
         result = 37 * result + ( getRenHidosu() == null ? 0 : this.getRenHidosu().hashCode() );
         result = 37 * result + ( getRenMxdosd() == null ? 0 : this.getRenMxdosd().hashCode() );
         result = 37 * result + ( getRenMxdosu() == null ? 0 : this.getRenMxdosu().hashCode() );
         result = 37 * result + ( getRenLofreq() == null ? 0 : this.getRenLofreq().hashCode() );
         result = 37 * result + ( getRenHifreq() == null ? 0 : this.getRenHifreq().hashCode() );
         result = 37 * result + ( getRenMx1dos() == null ? 0 : this.getRenMx1dos().hashCode() );
         result = 37 * result + ( getRenMx1dsu() == null ? 0 : this.getRenMx1dsu().hashCode() );
         result = 37 * result + ( getDxid() == null ? 0 : this.getDxid().hashCode() );
         result = 37 * result + ( getRenNteSingleDose() == null ? 0 : this.getRenNteSingleDose().hashCode() );
         result = 37 * result + ( getRenNteSingleDoseUnitCode() == null ? 0 : this.getRenNteSingleDoseUnitCode().hashCode() );
         result = 37 * result + ( getRenFootnote() == null ? 0 : this.getRenFootnote().hashCode() );
         result = 37 * result + ( getRenMonoId() == null ? 0 : this.getRenMonoId().hashCode() );
         return result;
   }   





}