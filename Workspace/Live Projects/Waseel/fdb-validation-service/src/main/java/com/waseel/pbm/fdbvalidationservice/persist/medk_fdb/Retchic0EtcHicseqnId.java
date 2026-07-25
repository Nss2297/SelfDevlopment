package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Retchic0EtcHicseqnId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Retchic0EtcHicseqnId  implements java.io.Serializable {


    // Fields    

     private Integer hicSeqn;
     private Integer etcId;


    // Constructors

    /** default constructor */
    public Retchic0EtcHicseqnId() {
    }

    
    /** full constructor */
    public Retchic0EtcHicseqnId(Integer hicSeqn, Integer etcId) {
        this.hicSeqn = hicSeqn;
        this.etcId = etcId;
    }

   
    // Property accessors

    @Column(name="HIC_SEQN", nullable=false, precision=6, scale=0)

    public Integer getHicSeqn() {
        return this.hicSeqn;
    }
    
    public void setHicSeqn(Integer hicSeqn) {
        this.hicSeqn = hicSeqn;
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
		 if ( !(other instanceof Retchic0EtcHicseqnId) ) return false;
		 Retchic0EtcHicseqnId castOther = ( Retchic0EtcHicseqnId ) other; 
         
		 return ( (this.getHicSeqn()==castOther.getHicSeqn()) || ( this.getHicSeqn()!=null && castOther.getHicSeqn()!=null && this.getHicSeqn().equals(castOther.getHicSeqn()) ) )
 && ( (this.getEtcId()==castOther.getEtcId()) || ( this.getEtcId()!=null && castOther.getEtcId()!=null && this.getEtcId().equals(castOther.getEtcId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getHicSeqn() == null ? 0 : this.getHicSeqn().hashCode() );
         result = 37 * result + ( getEtcId() == null ? 0 : this.getEtcId().hashCode() );
         return result;
   }   





}