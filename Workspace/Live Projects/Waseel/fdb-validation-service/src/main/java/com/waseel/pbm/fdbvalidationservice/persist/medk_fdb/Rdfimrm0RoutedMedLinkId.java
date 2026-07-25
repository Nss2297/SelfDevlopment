package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rdfimrm0RoutedMedLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rdfimrm0RoutedMedLinkId  implements java.io.Serializable {


    // Fields    

     private Integer routedMedId;
     private Short fdcde;


    // Constructors

    /** default constructor */
    public Rdfimrm0RoutedMedLinkId() {
    }

    
    /** full constructor */
    public Rdfimrm0RoutedMedLinkId(Integer routedMedId, Short fdcde) {
        this.routedMedId = routedMedId;
        this.fdcde = fdcde;
    }

   
    // Property accessors

    @Column(name="ROUTED_MED_ID", nullable=false, precision=8, scale=0)

    public Integer getRoutedMedId() {
        return this.routedMedId;
    }
    
    public void setRoutedMedId(Integer routedMedId) {
        this.routedMedId = routedMedId;
    }

    @Column(name="FDCDE", nullable=false, precision=3, scale=0)

    public Short getFdcde() {
        return this.fdcde;
    }
    
    public void setFdcde(Short fdcde) {
        this.fdcde = fdcde;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rdfimrm0RoutedMedLinkId) ) return false;
		 Rdfimrm0RoutedMedLinkId castOther = ( Rdfimrm0RoutedMedLinkId ) other; 
         
		 return ( (this.getRoutedMedId()==castOther.getRoutedMedId()) || ( this.getRoutedMedId()!=null && castOther.getRoutedMedId()!=null && this.getRoutedMedId().equals(castOther.getRoutedMedId()) ) )
 && ( (this.getFdcde()==castOther.getFdcde()) || ( this.getFdcde()!=null && castOther.getFdcde()!=null && this.getFdcde().equals(castOther.getFdcde()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRoutedMedId() == null ? 0 : this.getRoutedMedId().hashCode() );
         result = 37 * result + ( getFdcde() == null ? 0 : this.getFdcde().hashCode() );
         return result;
   }   





}