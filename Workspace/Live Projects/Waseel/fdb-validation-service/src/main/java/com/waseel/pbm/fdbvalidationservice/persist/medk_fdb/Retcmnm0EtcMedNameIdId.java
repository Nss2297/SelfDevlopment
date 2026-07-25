package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Retcmnm0EtcMedNameIdId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Retcmnm0EtcMedNameIdId  implements java.io.Serializable {


    // Fields    

     private Integer medNameId;
     private Integer etcId;


    // Constructors

    /** default constructor */
    public Retcmnm0EtcMedNameIdId() {
    }

    
    /** full constructor */
    public Retcmnm0EtcMedNameIdId(Integer medNameId, Integer etcId) {
        this.medNameId = medNameId;
        this.etcId = etcId;
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
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Retcmnm0EtcMedNameIdId) ) return false;
		 Retcmnm0EtcMedNameIdId castOther = ( Retcmnm0EtcMedNameIdId ) other; 
         
		 return ( (this.getMedNameId()==castOther.getMedNameId()) || ( this.getMedNameId()!=null && castOther.getMedNameId()!=null && this.getMedNameId().equals(castOther.getMedNameId()) ) )
 && ( (this.getEtcId()==castOther.getEtcId()) || ( this.getEtcId()!=null && castOther.getEtcId()!=null && this.getEtcId().equals(castOther.getEtcId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getMedNameId() == null ? 0 : this.getMedNameId().hashCode() );
         result = 37 * result + ( getEtcId() == null ? 0 : this.getEtcId().hashCode() );
         return result;
   }   





}