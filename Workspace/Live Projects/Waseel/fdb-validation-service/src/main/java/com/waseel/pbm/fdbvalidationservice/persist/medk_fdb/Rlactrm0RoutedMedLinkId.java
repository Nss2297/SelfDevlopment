package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rlactrm0RoutedMedLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rlactrm0RoutedMedLinkId  implements java.io.Serializable {


    // Fields    

     private Integer routedMedId;
     private Integer lactCode;


    // Constructors

    /** default constructor */
    public Rlactrm0RoutedMedLinkId() {
    }

    
    /** full constructor */
    public Rlactrm0RoutedMedLinkId(Integer routedMedId, Integer lactCode) {
        this.routedMedId = routedMedId;
        this.lactCode = lactCode;
    }

   
    // Property accessors

    @Column(name="ROUTED_MED_ID", nullable=false, precision=8, scale=0)

    public Integer getRoutedMedId() {
        return this.routedMedId;
    }
    
    public void setRoutedMedId(Integer routedMedId) {
        this.routedMedId = routedMedId;
    }

    @Column(name="LACT_CODE", nullable=false, precision=6, scale=0)

    public Integer getLactCode() {
        return this.lactCode;
    }
    
    public void setLactCode(Integer lactCode) {
        this.lactCode = lactCode;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rlactrm0RoutedMedLinkId) ) return false;
		 Rlactrm0RoutedMedLinkId castOther = ( Rlactrm0RoutedMedLinkId ) other; 
         
		 return ( (this.getRoutedMedId()==castOther.getRoutedMedId()) || ( this.getRoutedMedId()!=null && castOther.getRoutedMedId()!=null && this.getRoutedMedId().equals(castOther.getRoutedMedId()) ) )
 && ( (this.getLactCode()==castOther.getLactCode()) || ( this.getLactCode()!=null && castOther.getLactCode()!=null && this.getLactCode().equals(castOther.getLactCode()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRoutedMedId() == null ? 0 : this.getRoutedMedId().hashCode() );
         result = 37 * result + ( getLactCode() == null ? 0 : this.getLactCode().hashCode() );
         return result;
   }   





}