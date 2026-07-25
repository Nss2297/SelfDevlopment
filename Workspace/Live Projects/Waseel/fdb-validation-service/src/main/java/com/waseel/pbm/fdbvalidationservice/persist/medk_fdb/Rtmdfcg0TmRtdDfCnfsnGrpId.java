package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rtmdfcg0TmRtdDfCnfsnGrpId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rtmdfcg0TmRtdDfCnfsnGrpId  implements java.io.Serializable {


    // Fields    

     private Integer routedDosageFormMedId;
     private Integer tmGroupId;


    // Constructors

    /** default constructor */
    public Rtmdfcg0TmRtdDfCnfsnGrpId() {
    }

    
    /** full constructor */
    public Rtmdfcg0TmRtdDfCnfsnGrpId(Integer routedDosageFormMedId, Integer tmGroupId) {
        this.routedDosageFormMedId = routedDosageFormMedId;
        this.tmGroupId = tmGroupId;
    }

   
    // Property accessors

    @Column(name="ROUTED_DOSAGE_FORM_MED_ID", nullable=false, precision=8, scale=0)

    public Integer getRoutedDosageFormMedId() {
        return this.routedDosageFormMedId;
    }
    
    public void setRoutedDosageFormMedId(Integer routedDosageFormMedId) {
        this.routedDosageFormMedId = routedDosageFormMedId;
    }

    @Column(name="TM_GROUP_ID", nullable=false, precision=5, scale=0)

    public Integer getTmGroupId() {
        return this.tmGroupId;
    }
    
    public void setTmGroupId(Integer tmGroupId) {
        this.tmGroupId = tmGroupId;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rtmdfcg0TmRtdDfCnfsnGrpId) ) return false;
		 Rtmdfcg0TmRtdDfCnfsnGrpId castOther = ( Rtmdfcg0TmRtdDfCnfsnGrpId ) other; 
         
		 return ( (this.getRoutedDosageFormMedId()==castOther.getRoutedDosageFormMedId()) || ( this.getRoutedDosageFormMedId()!=null && castOther.getRoutedDosageFormMedId()!=null && this.getRoutedDosageFormMedId().equals(castOther.getRoutedDosageFormMedId()) ) )
 && ( (this.getTmGroupId()==castOther.getTmGroupId()) || ( this.getTmGroupId()!=null && castOther.getTmGroupId()!=null && this.getTmGroupId().equals(castOther.getTmGroupId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRoutedDosageFormMedId() == null ? 0 : this.getRoutedDosageFormMedId().hashCode() );
         result = 37 * result + ( getTmGroupId() == null ? 0 : this.getTmGroupId().hashCode() );
         return result;
   }   





}