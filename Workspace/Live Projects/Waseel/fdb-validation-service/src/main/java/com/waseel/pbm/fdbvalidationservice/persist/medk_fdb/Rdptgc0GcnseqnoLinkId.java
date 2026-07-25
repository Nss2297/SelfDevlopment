package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rdptgc0GcnseqnoLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rdptgc0GcnseqnoLinkId  implements java.io.Serializable {


    // Fields    

     private Integer gcnSeqno;
     private Integer dptClassId;


    // Constructors

    /** default constructor */
    public Rdptgc0GcnseqnoLinkId() {
    }

    
    /** full constructor */
    public Rdptgc0GcnseqnoLinkId(Integer gcnSeqno, Integer dptClassId) {
        this.gcnSeqno = gcnSeqno;
        this.dptClassId = dptClassId;
    }

   
    // Property accessors

    @Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0)

    public Integer getGcnSeqno() {
        return this.gcnSeqno;
    }
    
    public void setGcnSeqno(Integer gcnSeqno) {
        this.gcnSeqno = gcnSeqno;
    }

    @Column(name="DPT_CLASS_ID", nullable=false, precision=8, scale=0)

    public Integer getDptClassId() {
        return this.dptClassId;
    }
    
    public void setDptClassId(Integer dptClassId) {
        this.dptClassId = dptClassId;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rdptgc0GcnseqnoLinkId) ) return false;
		 Rdptgc0GcnseqnoLinkId castOther = ( Rdptgc0GcnseqnoLinkId ) other; 
         
		 return ( (this.getGcnSeqno()==castOther.getGcnSeqno()) || ( this.getGcnSeqno()!=null && castOther.getGcnSeqno()!=null && this.getGcnSeqno().equals(castOther.getGcnSeqno()) ) )
 && ( (this.getDptClassId()==castOther.getDptClassId()) || ( this.getDptClassId()!=null && castOther.getDptClassId()!=null && this.getDptClassId().equals(castOther.getDptClassId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getGcnSeqno() == null ? 0 : this.getGcnSeqno().hashCode() );
         result = 37 * result + ( getDptClassId() == null ? 0 : this.getDptClassId().hashCode() );
         return result;
   }   





}