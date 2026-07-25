package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Retcmdh0EtcMedidHistId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Retcmdh0EtcMedidHistId  implements java.io.Serializable {


    // Fields    

     private Integer medid;
     private Integer etcId;
     private Integer etcRevisionSeqno;


    // Constructors

    /** default constructor */
    public Retcmdh0EtcMedidHistId() {
    }

    
    /** full constructor */
    public Retcmdh0EtcMedidHistId(Integer medid, Integer etcId, Integer etcRevisionSeqno) {
        this.medid = medid;
        this.etcId = etcId;
        this.etcRevisionSeqno = etcRevisionSeqno;
    }

   
    // Property accessors

    @Column(name="MEDID", nullable=false, precision=8, scale=0)

    public Integer getMedid() {
        return this.medid;
    }
    
    public void setMedid(Integer medid) {
        this.medid = medid;
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
		 if ( !(other instanceof Retcmdh0EtcMedidHistId) ) return false;
		 Retcmdh0EtcMedidHistId castOther = ( Retcmdh0EtcMedidHistId ) other; 
         
		 return ( (this.getMedid()==castOther.getMedid()) || ( this.getMedid()!=null && castOther.getMedid()!=null && this.getMedid().equals(castOther.getMedid()) ) )
 && ( (this.getEtcId()==castOther.getEtcId()) || ( this.getEtcId()!=null && castOther.getEtcId()!=null && this.getEtcId().equals(castOther.getEtcId()) ) )
 && ( (this.getEtcRevisionSeqno()==castOther.getEtcRevisionSeqno()) || ( this.getEtcRevisionSeqno()!=null && castOther.getEtcRevisionSeqno()!=null && this.getEtcRevisionSeqno().equals(castOther.getEtcRevisionSeqno()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getMedid() == null ? 0 : this.getMedid().hashCode() );
         result = 37 * result + ( getEtcId() == null ? 0 : this.getEtcId().hashCode() );
         result = 37 * result + ( getEtcRevisionSeqno() == null ? 0 : this.getEtcRevisionSeqno().hashCode() );
         return result;
   }   





}