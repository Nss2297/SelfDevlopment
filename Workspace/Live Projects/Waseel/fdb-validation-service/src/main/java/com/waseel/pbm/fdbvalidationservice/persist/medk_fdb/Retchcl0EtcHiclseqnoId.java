package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Retchcl0EtcHiclseqnoId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Retchcl0EtcHiclseqnoId  implements java.io.Serializable {


    // Fields    

     private Integer hiclSeqno;
     private Integer etcId;


    // Constructors

    /** default constructor */
    public Retchcl0EtcHiclseqnoId() {
    }

    
    /** full constructor */
    public Retchcl0EtcHiclseqnoId(Integer hiclSeqno, Integer etcId) {
        this.hiclSeqno = hiclSeqno;
        this.etcId = etcId;
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
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Retchcl0EtcHiclseqnoId) ) return false;
		 Retchcl0EtcHiclseqnoId castOther = ( Retchcl0EtcHiclseqnoId ) other; 
         
		 return ( (this.getHiclSeqno()==castOther.getHiclSeqno()) || ( this.getHiclSeqno()!=null && castOther.getHiclSeqno()!=null && this.getHiclSeqno().equals(castOther.getHiclSeqno()) ) )
 && ( (this.getEtcId()==castOther.getEtcId()) || ( this.getEtcId()!=null && castOther.getEtcId()!=null && this.getEtcId().equals(castOther.getEtcId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getHiclSeqno() == null ? 0 : this.getHiclSeqno().hashCode() );
         result = 37 * result + ( getEtcId() == null ? 0 : this.getEtcId().hashCode() );
         return result;
   }   





}