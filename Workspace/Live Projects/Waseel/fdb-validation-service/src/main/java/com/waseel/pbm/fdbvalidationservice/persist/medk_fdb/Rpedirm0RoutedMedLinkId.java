package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rpedirm0RoutedMedLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rpedirm0RoutedMedLinkId  implements java.io.Serializable {


    // Fields    

     private Integer routedMedId;
     private Integer pediCode;


    // Constructors

    /** default constructor */
    public Rpedirm0RoutedMedLinkId() {
    }

    
    /** full constructor */
    public Rpedirm0RoutedMedLinkId(Integer routedMedId, Integer pediCode) {
        this.routedMedId = routedMedId;
        this.pediCode = pediCode;
    }

   
    // Property accessors

    @Column(name="ROUTED_MED_ID", nullable=false, precision=8, scale=0)

    public Integer getRoutedMedId() {
        return this.routedMedId;
    }
    
    public void setRoutedMedId(Integer routedMedId) {
        this.routedMedId = routedMedId;
    }

    @Column(name="PEDI_CODE", nullable=false, precision=6, scale=0)

    public Integer getPediCode() {
        return this.pediCode;
    }
    
    public void setPediCode(Integer pediCode) {
        this.pediCode = pediCode;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rpedirm0RoutedMedLinkId) ) return false;
		 Rpedirm0RoutedMedLinkId castOther = ( Rpedirm0RoutedMedLinkId ) other; 
         
		 return ( (this.getRoutedMedId()==castOther.getRoutedMedId()) || ( this.getRoutedMedId()!=null && castOther.getRoutedMedId()!=null && this.getRoutedMedId().equals(castOther.getRoutedMedId()) ) )
 && ( (this.getPediCode()==castOther.getPediCode()) || ( this.getPediCode()!=null && castOther.getPediCode()!=null && this.getPediCode().equals(castOther.getPediCode()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRoutedMedId() == null ? 0 : this.getRoutedMedId().hashCode() );
         result = 37 * result + ( getPediCode() == null ? 0 : this.getPediCode().hashCode() );
         return result;
   }   





}