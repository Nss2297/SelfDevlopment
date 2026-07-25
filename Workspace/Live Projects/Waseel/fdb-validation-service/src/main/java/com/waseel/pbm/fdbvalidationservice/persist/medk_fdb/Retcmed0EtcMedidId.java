package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Retcmed0EtcMedidId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Retcmed0EtcMedidId  implements java.io.Serializable {


    // Fields    

     private Integer medid;
     private Integer etcId;


    // Constructors

    /** default constructor */
    public Retcmed0EtcMedidId() {
    }

    
    /** full constructor */
    public Retcmed0EtcMedidId(Integer medid, Integer etcId) {
        this.medid = medid;
        this.etcId = etcId;
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
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Retcmed0EtcMedidId) ) return false;
		 Retcmed0EtcMedidId castOther = ( Retcmed0EtcMedidId ) other; 
         
		 return ( (this.getMedid()==castOther.getMedid()) || ( this.getMedid()!=null && castOther.getMedid()!=null && this.getMedid().equals(castOther.getMedid()) ) )
 && ( (this.getEtcId()==castOther.getEtcId()) || ( this.getEtcId()!=null && castOther.getEtcId()!=null && this.getEtcId().equals(castOther.getEtcId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getMedid() == null ? 0 : this.getMedid().hashCode() );
         result = 37 * result + ( getEtcId() == null ? 0 : this.getEtcId().hashCode() );
         return result;
   }   





}