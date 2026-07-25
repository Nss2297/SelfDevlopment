package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rlactgc0LactGcnseqnoLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rlactgc0LactGcnseqnoLinkId  implements java.io.Serializable {


    // Fields    

     private Integer gcnSeqno;
     private Integer lactCode;


    // Constructors

    /** default constructor */
    public Rlactgc0LactGcnseqnoLinkId() {
    }

    
    /** full constructor */
    public Rlactgc0LactGcnseqnoLinkId(Integer gcnSeqno, Integer lactCode) {
        this.gcnSeqno = gcnSeqno;
        this.lactCode = lactCode;
    }

   
    // Property accessors

    @Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0)

    public Integer getGcnSeqno() {
        return this.gcnSeqno;
    }
    
    public void setGcnSeqno(Integer gcnSeqno) {
        this.gcnSeqno = gcnSeqno;
    }

    @Column(name="LACT_CODE", nullable=false, precision=6, scale=0)

    public Integer getLactCode() {
        return this.lactCode;
    }
    
    public void setLactCode(Integer lactCode) {
        this.lactCode = lactCode;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rlactgc0LactGcnseqnoLinkId) ) return false;
		 Rlactgc0LactGcnseqnoLinkId castOther = ( Rlactgc0LactGcnseqnoLinkId ) other; 
         
		 return ( (this.getGcnSeqno()==castOther.getGcnSeqno()) || ( this.getGcnSeqno()!=null && castOther.getGcnSeqno()!=null && this.getGcnSeqno().equals(castOther.getGcnSeqno()) ) )
 && ( (this.getLactCode()==castOther.getLactCode()) || ( this.getLactCode()!=null && castOther.getLactCode()!=null && this.getLactCode().equals(castOther.getLactCode()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getGcnSeqno() == null ? 0 : this.getGcnSeqno().hashCode() );
         result = 37 * result + ( getLactCode() == null ? 0 : this.getLactCode().hashCode() );
         return result;
   }   





}