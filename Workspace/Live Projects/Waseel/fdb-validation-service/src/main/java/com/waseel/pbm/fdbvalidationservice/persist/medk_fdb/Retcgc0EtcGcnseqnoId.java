package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Retcgc0EtcGcnseqnoId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Retcgc0EtcGcnseqnoId  implements java.io.Serializable {


    // Fields    

     private Integer gcnSeqno;
     private Integer etcId;


    // Constructors

    /** default constructor */
    public Retcgc0EtcGcnseqnoId() {
    }

    
    /** full constructor */
    public Retcgc0EtcGcnseqnoId(Integer gcnSeqno, Integer etcId) {
        this.gcnSeqno = gcnSeqno;
        this.etcId = etcId;
    }

   
    // Property accessors

    @Column(name="GCN_SEQNO", nullable=false, precision=6, scale=0)

    public Integer getGcnSeqno() {
        return this.gcnSeqno;
    }
    
    public void setGcnSeqno(Integer gcnSeqno) {
        this.gcnSeqno = gcnSeqno;
    }

    @Column(name="ETC_ID", nullable=false, precision=8, scale=0)

    public Integer getEtcId() {
        return this.etcId;
    }
    
    public void setEtcId(Integer etcId) {
        this.etcId = etcId;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Retcgc0EtcGcnseqnoId) ) return false;
		 Retcgc0EtcGcnseqnoId castOther = ( Retcgc0EtcGcnseqnoId ) other; 
         
		 return ( (this.getGcnSeqno()==castOther.getGcnSeqno()) || ( this.getGcnSeqno()!=null && castOther.getGcnSeqno()!=null && this.getGcnSeqno().equals(castOther.getGcnSeqno()) ) )
 && ( (this.getEtcId()==castOther.getEtcId()) || ( this.getEtcId()!=null && castOther.getEtcId()!=null && this.getEtcId().equals(castOther.getEtcId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getGcnSeqno() == null ? 0 : this.getGcnSeqno().hashCode() );
         result = 37 * result + ( getEtcId() == null ? 0 : this.getEtcId().hashCode() );
         return result;
   }   





}