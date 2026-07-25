package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rdptrtm0RoutedMedLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rdptrtm0RoutedMedLinkId  implements java.io.Serializable {


    // Fields    

     private Integer routedMedId;
     private Integer dptClassId;


    // Constructors

    /** default constructor */
    public Rdptrtm0RoutedMedLinkId() {
    }

    
    /** full constructor */
    public Rdptrtm0RoutedMedLinkId(Integer routedMedId, Integer dptClassId) {
        this.routedMedId = routedMedId;
        this.dptClassId = dptClassId;
    }

   
    // Property accessors

    @Column(name="ROUTED_MED_ID", nullable=false, precision=8, scale=0)

    public Integer getRoutedMedId() {
        return this.routedMedId;
    }
    
    public void setRoutedMedId(Integer routedMedId) {
        this.routedMedId = routedMedId;
    }

    @Column(name="DPT_CLASS_ID", nullable=false, precision=8, scale=0)

    public Integer getDptClassId() {
        return this.dptClassId;
    }
    
    public void setDptClassId(Integer dptClassId) {
        this.dptClassId = dptClassId;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rdptrtm0RoutedMedLinkId) ) return false;
		 Rdptrtm0RoutedMedLinkId castOther = ( Rdptrtm0RoutedMedLinkId ) other; 
         
		 return ( (this.getRoutedMedId()==castOther.getRoutedMedId()) || ( this.getRoutedMedId()!=null && castOther.getRoutedMedId()!=null && this.getRoutedMedId().equals(castOther.getRoutedMedId()) ) )
 && ( (this.getDptClassId()==castOther.getDptClassId()) || ( this.getDptClassId()!=null && castOther.getDptClassId()!=null && this.getDptClassId().equals(castOther.getDptClassId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRoutedMedId() == null ? 0 : this.getRoutedMedId().hashCode() );
         result = 37 * result + ( getDptClassId() == null ? 0 : this.getDptClassId().hashCode() );
         return result;
   }   





}