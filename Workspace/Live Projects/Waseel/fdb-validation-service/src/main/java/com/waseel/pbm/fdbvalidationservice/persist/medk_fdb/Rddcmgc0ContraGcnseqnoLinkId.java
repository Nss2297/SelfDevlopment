package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rddcmgc0ContraGcnseqnoLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rddcmgc0ContraGcnseqnoLinkId  implements java.io.Serializable {


    // Fields    

     private Integer gcnSeqno;
     private Integer ddxcn;


    // Constructors

    /** default constructor */
    public Rddcmgc0ContraGcnseqnoLinkId() {
    }

    
    /** full constructor */
    public Rddcmgc0ContraGcnseqnoLinkId(Integer gcnSeqno, Integer ddxcn) {
        this.gcnSeqno = gcnSeqno;
        this.ddxcn = ddxcn;
    }

   
    // Property accessors

    @Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0)

    public Integer getGcnSeqno() {
        return this.gcnSeqno;
    }
    
    public void setGcnSeqno(Integer gcnSeqno) {
        this.gcnSeqno = gcnSeqno;
    }

    @Column(name="DDXCN", nullable=false, precision=5, scale=0)

    public Integer getDdxcn() {
        return this.ddxcn;
    }
    
    public void setDdxcn(Integer ddxcn) {
        this.ddxcn = ddxcn;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rddcmgc0ContraGcnseqnoLinkId) ) return false;
		 Rddcmgc0ContraGcnseqnoLinkId castOther = ( Rddcmgc0ContraGcnseqnoLinkId ) other; 
         
		 return ( (this.getGcnSeqno()==castOther.getGcnSeqno()) || ( this.getGcnSeqno()!=null && castOther.getGcnSeqno()!=null && this.getGcnSeqno().equals(castOther.getGcnSeqno()) ) )
 && ( (this.getDdxcn()==castOther.getDdxcn()) || ( this.getDdxcn()!=null && castOther.getDdxcn()!=null && this.getDdxcn().equals(castOther.getDdxcn()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getGcnSeqno() == null ? 0 : this.getGcnSeqno().hashCode() );
         result = 37 * result + ( getDdxcn() == null ? 0 : this.getDdxcn().hashCode() );
         return result;
   }   





}