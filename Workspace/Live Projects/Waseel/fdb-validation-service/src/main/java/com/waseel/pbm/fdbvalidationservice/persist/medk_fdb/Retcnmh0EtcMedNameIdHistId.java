package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Retcnmh0EtcMedNameIdHistId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Retcnmh0EtcMedNameIdHistId  implements java.io.Serializable {


    // Fields    

     private Integer medNameId;
     private Integer etcId;
     private Integer etcRevisionSeqno;


    // Constructors

    /** default constructor */
    public Retcnmh0EtcMedNameIdHistId() {
    }

    
    /** full constructor */
    public Retcnmh0EtcMedNameIdHistId(Integer medNameId, Integer etcId, Integer etcRevisionSeqno) {
        this.medNameId = medNameId;
        this.etcId = etcId;
        this.etcRevisionSeqno = etcRevisionSeqno;
    }

   
    // Property accessors

    @Column(name="MED_NAME_ID", nullable=false, precision=8, scale=0)

    public Integer getMedNameId() {
        return this.medNameId;
    }
    
    public void setMedNameId(Integer medNameId) {
        this.medNameId = medNameId;
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
		 if ( !(other instanceof Retcnmh0EtcMedNameIdHistId) ) return false;
		 Retcnmh0EtcMedNameIdHistId castOther = ( Retcnmh0EtcMedNameIdHistId ) other; 
         
		 return ( (this.getMedNameId()==castOther.getMedNameId()) || ( this.getMedNameId()!=null && castOther.getMedNameId()!=null && this.getMedNameId().equals(castOther.getMedNameId()) ) )
 && ( (this.getEtcId()==castOther.getEtcId()) || ( this.getEtcId()!=null && castOther.getEtcId()!=null && this.getEtcId().equals(castOther.getEtcId()) ) )
 && ( (this.getEtcRevisionSeqno()==castOther.getEtcRevisionSeqno()) || ( this.getEtcRevisionSeqno()!=null && castOther.getEtcRevisionSeqno()!=null && this.getEtcRevisionSeqno().equals(castOther.getEtcRevisionSeqno()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getMedNameId() == null ? 0 : this.getMedNameId().hashCode() );
         result = 37 * result + ( getEtcId() == null ? 0 : this.getEtcId().hashCode() );
         result = 37 * result + ( getEtcRevisionSeqno() == null ? 0 : this.getEtcRevisionSeqno().hashCode() );
         return result;
   }   





}