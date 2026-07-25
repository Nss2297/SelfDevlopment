package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Retchlh0EtcHiclseqnoHistId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Retchlh0EtcHiclseqnoHistId  implements java.io.Serializable {


    // Fields    

     private Integer hiclSeqno;
     private Integer etcId;
     private Integer etcRevisionSeqno;


    // Constructors

    /** default constructor */
    public Retchlh0EtcHiclseqnoHistId() {
    }

    
    /** full constructor */
    public Retchlh0EtcHiclseqnoHistId(Integer hiclSeqno, Integer etcId, Integer etcRevisionSeqno) {
        this.hiclSeqno = hiclSeqno;
        this.etcId = etcId;
        this.etcRevisionSeqno = etcRevisionSeqno;
    }

   
    // Property accessors

    @Column(name="HICL_SEQNO", nullable=false, precision=6, scale=0)

    public Integer getHiclSeqno() {
        return this.hiclSeqno;
    }
    
    public void setHiclSeqno(Integer hiclSeqno) {
        this.hiclSeqno = hiclSeqno;
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
		 if ( !(other instanceof Retchlh0EtcHiclseqnoHistId) ) return false;
		 Retchlh0EtcHiclseqnoHistId castOther = ( Retchlh0EtcHiclseqnoHistId ) other; 
         
		 return ( (this.getHiclSeqno()==castOther.getHiclSeqno()) || ( this.getHiclSeqno()!=null && castOther.getHiclSeqno()!=null && this.getHiclSeqno().equals(castOther.getHiclSeqno()) ) )
 && ( (this.getEtcId()==castOther.getEtcId()) || ( this.getEtcId()!=null && castOther.getEtcId()!=null && this.getEtcId().equals(castOther.getEtcId()) ) )
 && ( (this.getEtcRevisionSeqno()==castOther.getEtcRevisionSeqno()) || ( this.getEtcRevisionSeqno()!=null && castOther.getEtcRevisionSeqno()!=null && this.getEtcRevisionSeqno().equals(castOther.getEtcRevisionSeqno()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getHiclSeqno() == null ? 0 : this.getHiclSeqno().hashCode() );
         result = 37 * result + ( getEtcId() == null ? 0 : this.getEtcId().hashCode() );
         result = 37 * result + ( getEtcRevisionSeqno() == null ? 0 : this.getEtcRevisionSeqno().hashCode() );
         return result;
   }   





}