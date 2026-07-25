package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Retcgch0EtcGcnseqnoHistId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Retcgch0EtcGcnseqnoHistId  implements java.io.Serializable {


    // Fields    

     private Integer gcnSeqno;
     private Integer etcId;
     private Integer etcRevisionSeqno;


    // Constructors

    /** default constructor */
    public Retcgch0EtcGcnseqnoHistId() {
    }

    
    /** full constructor */
    public Retcgch0EtcGcnseqnoHistId(Integer gcnSeqno, Integer etcId, Integer etcRevisionSeqno) {
        this.gcnSeqno = gcnSeqno;
        this.etcId = etcId;
        this.etcRevisionSeqno = etcRevisionSeqno;
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

    @Column(name="ETC_REVISION_SEQNO", nullable=false, precision=5, scale=0)

    public Integer getEtcRevisionSeqno() {
        return this.etcRevisionSeqno;
    }
    
    public void setEtcRevisionSeqno(Integer etcRevisionSeqno) {
        this.etcRevisionSeqno = etcRevisionSeqno;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Retcgch0EtcGcnseqnoHistId) ) return false;
		 Retcgch0EtcGcnseqnoHistId castOther = ( Retcgch0EtcGcnseqnoHistId ) other; 
         
		 return ( (this.getGcnSeqno()==castOther.getGcnSeqno()) || ( this.getGcnSeqno()!=null && castOther.getGcnSeqno()!=null && this.getGcnSeqno().equals(castOther.getGcnSeqno()) ) )
 && ( (this.getEtcId()==castOther.getEtcId()) || ( this.getEtcId()!=null && castOther.getEtcId()!=null && this.getEtcId().equals(castOther.getEtcId()) ) )
 && ( (this.getEtcRevisionSeqno()==castOther.getEtcRevisionSeqno()) || ( this.getEtcRevisionSeqno()!=null && castOther.getEtcRevisionSeqno()!=null && this.getEtcRevisionSeqno().equals(castOther.getEtcRevisionSeqno()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getGcnSeqno() == null ? 0 : this.getGcnSeqno().hashCode() );
         result = 37 * result + ( getEtcId() == null ? 0 : this.getEtcId().hashCode() );
         result = 37 * result + ( getEtcRevisionSeqno() == null ? 0 : this.getEtcRevisionSeqno().hashCode() );
         return result;
   }   





}