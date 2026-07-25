package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rgcn0GcnGcnseqnoLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rgcn0GcnGcnseqnoLinkId  implements java.io.Serializable {


    // Fields    

     private Integer gcnSeqno;
     private Integer gcn;


    // Constructors

    /** default constructor */
    public Rgcn0GcnGcnseqnoLinkId() {
    }

    
    /** full constructor */
    public Rgcn0GcnGcnseqnoLinkId(Integer gcnSeqno, Integer gcn) {
        this.gcnSeqno = gcnSeqno;
        this.gcn = gcn;
    }

   
    // Property accessors

    @Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0)

    public Integer getGcnSeqno() {
        return this.gcnSeqno;
    }
    
    public void setGcnSeqno(Integer gcnSeqno) {
        this.gcnSeqno = gcnSeqno;
    }

    @Column(name="GCN", nullable=false, precision=5, scale=0)

    public Integer getGcn() {
        return this.gcn;
    }
    
    public void setGcn(Integer gcn) {
        this.gcn = gcn;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rgcn0GcnGcnseqnoLinkId) ) return false;
		 Rgcn0GcnGcnseqnoLinkId castOther = ( Rgcn0GcnGcnseqnoLinkId ) other; 
         
		 return ( (this.getGcnSeqno()==castOther.getGcnSeqno()) || ( this.getGcnSeqno()!=null && castOther.getGcnSeqno()!=null && this.getGcnSeqno().equals(castOther.getGcnSeqno()) ) )
 && ( (this.getGcn()==castOther.getGcn()) || ( this.getGcn()!=null && castOther.getGcn()!=null && this.getGcn().equals(castOther.getGcn()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getGcnSeqno() == null ? 0 : this.getGcnSeqno().hashCode() );
         result = 37 * result + ( getGcn() == null ? 0 : this.getGcn().hashCode() );
         return result;
   }   





}