package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rtmrmcg0TmRtdMedCnfsnGrpId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rtmrmcg0TmRtdMedCnfsnGrpId  implements java.io.Serializable {


    // Fields    

     private Integer routedMedId;
     private Integer tmGroupId;


    // Constructors

    /** default constructor */
    public Rtmrmcg0TmRtdMedCnfsnGrpId() {
    }

    
    /** full constructor */
    public Rtmrmcg0TmRtdMedCnfsnGrpId(Integer routedMedId, Integer tmGroupId) {
        this.routedMedId = routedMedId;
        this.tmGroupId = tmGroupId;
    }

   
    // Property accessors

    @Column(name="ROUTED_MED_ID", nullable=false, precision=8, scale=0)

    public Integer getRoutedMedId() {
        return this.routedMedId;
    }
    
    public void setRoutedMedId(Integer routedMedId) {
        this.routedMedId = routedMedId;
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
		 if ( !(other instanceof Rtmrmcg0TmRtdMedCnfsnGrpId) ) return false;
		 Rtmrmcg0TmRtdMedCnfsnGrpId castOther = ( Rtmrmcg0TmRtdMedCnfsnGrpId ) other; 
         
		 return ( (this.getRoutedMedId()==castOther.getRoutedMedId()) || ( this.getRoutedMedId()!=null && castOther.getRoutedMedId()!=null && this.getRoutedMedId().equals(castOther.getRoutedMedId()) ) )
 && ( (this.getTmGroupId()==castOther.getTmGroupId()) || ( this.getTmGroupId()!=null && castOther.getTmGroupId()!=null && this.getTmGroupId().equals(castOther.getTmGroupId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRoutedMedId() == null ? 0 : this.getRoutedMedId().hashCode() );
         result = 37 * result + ( getTmGroupId() == null ? 0 : this.getTmGroupId().hashCode() );
         return result;
   }   





}