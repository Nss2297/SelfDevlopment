package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rgerigc0GeriGcnseqnoLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rgerigc0GeriGcnseqnoLinkId  implements java.io.Serializable {


    // Fields    

     private Integer gcnSeqno;
     private Integer geriCode;


    // Constructors

    /** default constructor */
    public Rgerigc0GeriGcnseqnoLinkId() {
    }

    
    /** full constructor */
    public Rgerigc0GeriGcnseqnoLinkId(Integer gcnSeqno, Integer geriCode) {
        this.gcnSeqno = gcnSeqno;
        this.geriCode = geriCode;
    }

   
    // Property accessors

    @Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0)

    public Integer getGcnSeqno() {
        return this.gcnSeqno;
    }
    
    public void setGcnSeqno(Integer gcnSeqno) {
        this.gcnSeqno = gcnSeqno;
    }

    @Column(name="GERI_CODE", nullable=false, precision=6, scale=0)

    public Integer getGeriCode() {
        return this.geriCode;
    }
    
    public void setGeriCode(Integer geriCode) {
        this.geriCode = geriCode;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rgerigc0GeriGcnseqnoLinkId) ) return false;
		 Rgerigc0GeriGcnseqnoLinkId castOther = ( Rgerigc0GeriGcnseqnoLinkId ) other; 
         
		 return ( (this.getGcnSeqno()==castOther.getGcnSeqno()) || ( this.getGcnSeqno()!=null && castOther.getGcnSeqno()!=null && this.getGcnSeqno().equals(castOther.getGcnSeqno()) ) )
 && ( (this.getGeriCode()==castOther.getGeriCode()) || ( this.getGeriCode()!=null && castOther.getGeriCode()!=null && this.getGeriCode().equals(castOther.getGeriCode()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getGcnSeqno() == null ? 0 : this.getGcnSeqno().hashCode() );
         result = 37 * result + ( getGeriCode() == null ? 0 : this.getGeriCode().hashCode() );
         return result;
   }   





}