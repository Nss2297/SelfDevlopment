package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Retcxrf0EtcHic3EtcId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Retcxrf0EtcHic3EtcId  implements java.io.Serializable {


    // Fields    

     private Integer etcId;
     private Integer hic3Seqn;


    // Constructors

    /** default constructor */
    public Retcxrf0EtcHic3EtcId() {
    }

    
    /** full constructor */
    public Retcxrf0EtcHic3EtcId(Integer etcId, Integer hic3Seqn) {
        this.etcId = etcId;
        this.hic3Seqn = hic3Seqn;
    }

   
    // Property accessors

    @Column(name="ETC_ID", nullable=false, precision=8, scale=0)

    public Integer getEtcId() {
        return this.etcId;
    }
    
    public void setEtcId(Integer etcId) {
        this.etcId = etcId;
    }

    @Column(name="HIC3_SEQN", nullable=false, precision=6, scale=0)

    public Integer getHic3Seqn() {
        return this.hic3Seqn;
    }
    
    public void setHic3Seqn(Integer hic3Seqn) {
        this.hic3Seqn = hic3Seqn;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Retcxrf0EtcHic3EtcId) ) return false;
		 Retcxrf0EtcHic3EtcId castOther = ( Retcxrf0EtcHic3EtcId ) other; 
         
		 return ( (this.getEtcId()==castOther.getEtcId()) || ( this.getEtcId()!=null && castOther.getEtcId()!=null && this.getEtcId().equals(castOther.getEtcId()) ) )
 && ( (this.getHic3Seqn()==castOther.getHic3Seqn()) || ( this.getHic3Seqn()!=null && castOther.getHic3Seqn()!=null && this.getHic3Seqn().equals(castOther.getHic3Seqn()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getEtcId() == null ? 0 : this.getEtcId().hashCode() );
         result = 37 * result + ( getHic3Seqn() == null ? 0 : this.getHic3Seqn().hashCode() );
         return result;
   }   





}