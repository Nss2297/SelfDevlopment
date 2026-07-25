package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Radimgc4GcnseqnoLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Radimgc4GcnseqnoLinkId  implements java.io.Serializable {


    // Fields    

     private Integer gcnSeqno;
     private Integer ddiCodex;


    // Constructors

    /** default constructor */
    public Radimgc4GcnseqnoLinkId() {
    }

    
    /** full constructor */
    public Radimgc4GcnseqnoLinkId(Integer gcnSeqno, Integer ddiCodex) {
        this.gcnSeqno = gcnSeqno;
        this.ddiCodex = ddiCodex;
    }

   
    // Property accessors

    @Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0)

    public Integer getGcnSeqno() {
        return this.gcnSeqno;
    }
    
    public void setGcnSeqno(Integer gcnSeqno) {
        this.gcnSeqno = gcnSeqno;
    }

    @Column(name="DDI_CODEX", nullable=false, precision=5, scale=0)

    public Integer getDdiCodex() {
        return this.ddiCodex;
    }
    
    public void setDdiCodex(Integer ddiCodex) {
        this.ddiCodex = ddiCodex;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Radimgc4GcnseqnoLinkId) ) return false;
		 Radimgc4GcnseqnoLinkId castOther = ( Radimgc4GcnseqnoLinkId ) other; 
         
		 return ( (this.getGcnSeqno()==castOther.getGcnSeqno()) || ( this.getGcnSeqno()!=null && castOther.getGcnSeqno()!=null && this.getGcnSeqno().equals(castOther.getGcnSeqno()) ) )
 && ( (this.getDdiCodex()==castOther.getDdiCodex()) || ( this.getDdiCodex()!=null && castOther.getDdiCodex()!=null && this.getDdiCodex().equals(castOther.getDdiCodex()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getGcnSeqno() == null ? 0 : this.getGcnSeqno().hashCode() );
         result = 37 * result + ( getDdiCodex() == null ? 0 : this.getDdiCodex().hashCode() );
         return result;
   }   





}