package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rdfimgc0GcnseqnoLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rdfimgc0GcnseqnoLinkId  implements java.io.Serializable {


    // Fields    

     private Integer gcnSeqno;
     private Short fdcde;


    // Constructors

    /** default constructor */
    public Rdfimgc0GcnseqnoLinkId() {
    }

    
    /** full constructor */
    public Rdfimgc0GcnseqnoLinkId(Integer gcnSeqno, Short fdcde) {
        this.gcnSeqno = gcnSeqno;
        this.fdcde = fdcde;
    }

   
    // Property accessors

    @Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0)

    public Integer getGcnSeqno() {
        return this.gcnSeqno;
    }
    
    public void setGcnSeqno(Integer gcnSeqno) {
        this.gcnSeqno = gcnSeqno;
    }

    @Column(name="FDCDE", nullable=false, precision=3, scale=0)

    public Short getFdcde() {
        return this.fdcde;
    }
    
    public void setFdcde(Short fdcde) {
        this.fdcde = fdcde;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rdfimgc0GcnseqnoLinkId) ) return false;
		 Rdfimgc0GcnseqnoLinkId castOther = ( Rdfimgc0GcnseqnoLinkId ) other; 
         
		 return ( (this.getGcnSeqno()==castOther.getGcnSeqno()) || ( this.getGcnSeqno()!=null && castOther.getGcnSeqno()!=null && this.getGcnSeqno().equals(castOther.getGcnSeqno()) ) )
 && ( (this.getFdcde()==castOther.getFdcde()) || ( this.getFdcde()!=null && castOther.getFdcde()!=null && this.getFdcde().equals(castOther.getFdcde()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getGcnSeqno() == null ? 0 : this.getGcnSeqno().hashCode() );
         result = 37 * result + ( getFdcde() == null ? 0 : this.getFdcde().hashCode() );
         return result;
   }   





}