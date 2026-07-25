package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rpregrm0RoutedMedLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rpregrm0RoutedMedLinkId  implements java.io.Serializable {


    // Fields    

     private Integer routedMedId;
     private Integer pregCode;


    // Constructors

    /** default constructor */
    public Rpregrm0RoutedMedLinkId() {
    }

    
    /** full constructor */
    public Rpregrm0RoutedMedLinkId(Integer routedMedId, Integer pregCode) {
        this.routedMedId = routedMedId;
        this.pregCode = pregCode;
    }

   
    // Property accessors

    @Column(name="ROUTED_MED_ID", nullable=false, precision=8, scale=0)

    public Integer getRoutedMedId() {
        return this.routedMedId;
    }
    
    public void setRoutedMedId(Integer routedMedId) {
        this.routedMedId = routedMedId;
    }

    @Column(name="PREG_CODE", nullable=false, precision=6, scale=0)

    public Integer getPregCode() {
        return this.pregCode;
    }
    
    public void setPregCode(Integer pregCode) {
        this.pregCode = pregCode;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rpregrm0RoutedMedLinkId) ) return false;
		 Rpregrm0RoutedMedLinkId castOther = ( Rpregrm0RoutedMedLinkId ) other; 
         
		 return ( (this.getRoutedMedId()==castOther.getRoutedMedId()) || ( this.getRoutedMedId()!=null && castOther.getRoutedMedId()!=null && this.getRoutedMedId().equals(castOther.getRoutedMedId()) ) )
 && ( (this.getPregCode()==castOther.getPregCode()) || ( this.getPregCode()!=null && castOther.getPregCode()!=null && this.getPregCode().equals(castOther.getPregCode()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRoutedMedId() == null ? 0 : this.getRoutedMedId().hashCode() );
         result = 37 * result + ( getPregCode() == null ? 0 : this.getPregCode().hashCode() );
         return result;
   }   





}