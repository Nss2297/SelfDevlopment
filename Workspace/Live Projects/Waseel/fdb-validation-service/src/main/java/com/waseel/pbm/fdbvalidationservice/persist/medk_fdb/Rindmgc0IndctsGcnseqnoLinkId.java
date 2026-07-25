package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rindmgc0IndctsGcnseqnoLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rindmgc0IndctsGcnseqnoLinkId  implements java.io.Serializable {


    // Fields    

     private Integer gcnSeqno;
     private Integer indcts;


    // Constructors

    /** default constructor */
    public Rindmgc0IndctsGcnseqnoLinkId() {
    }

    
    /** full constructor */
    public Rindmgc0IndctsGcnseqnoLinkId(Integer gcnSeqno, Integer indcts) {
        this.gcnSeqno = gcnSeqno;
        this.indcts = indcts;
    }

   
    // Property accessors

    @Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0)

    public Integer getGcnSeqno() {
        return this.gcnSeqno;
    }
    
    public void setGcnSeqno(Integer gcnSeqno) {
        this.gcnSeqno = gcnSeqno;
    }

    @Column(name="INDCTS", nullable=false, precision=5, scale=0)

    public Integer getIndcts() {
        return this.indcts;
    }
    
    public void setIndcts(Integer indcts) {
        this.indcts = indcts;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rindmgc0IndctsGcnseqnoLinkId) ) return false;
		 Rindmgc0IndctsGcnseqnoLinkId castOther = ( Rindmgc0IndctsGcnseqnoLinkId ) other; 
         
		 return ( (this.getGcnSeqno()==castOther.getGcnSeqno()) || ( this.getGcnSeqno()!=null && castOther.getGcnSeqno()!=null && this.getGcnSeqno().equals(castOther.getGcnSeqno()) ) )
 && ( (this.getIndcts()==castOther.getIndcts()) || ( this.getIndcts()!=null && castOther.getIndcts()!=null && this.getIndcts().equals(castOther.getIndcts()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getGcnSeqno() == null ? 0 : this.getGcnSeqno().hashCode() );
         result = 37 * result + ( getIndcts() == null ? 0 : this.getIndcts().hashCode() );
         return result;
   }   





}