package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rgerirm0RoutedMedLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rgerirm0RoutedMedLinkId  implements java.io.Serializable {


    // Fields    

     private Integer routedMedId;
     private Integer geriCode;


    // Constructors

    /** default constructor */
    public Rgerirm0RoutedMedLinkId() {
    }

    
    /** full constructor */
    public Rgerirm0RoutedMedLinkId(Integer routedMedId, Integer geriCode) {
        this.routedMedId = routedMedId;
        this.geriCode = geriCode;
    }

   
    // Property accessors

    @Column(name="ROUTED_MED_ID", nullable=false, precision=8, scale=0)

    public Integer getRoutedMedId() {
        return this.routedMedId;
    }
    
    public void setRoutedMedId(Integer routedMedId) {
        this.routedMedId = routedMedId;
    }

    @Column(name="GERI_CODE", nullable=false, precision=6, scale=0)

    public Integer getGeriCode() {
        return this.geriCode;
    }
    
    public void setGeriCode(Integer geriCode) {
        this.geriCode = geriCode;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rgerirm0RoutedMedLinkId) ) return false;
		 Rgerirm0RoutedMedLinkId castOther = ( Rgerirm0RoutedMedLinkId ) other; 
         
		 return ( (this.getRoutedMedId()==castOther.getRoutedMedId()) || ( this.getRoutedMedId()!=null && castOther.getRoutedMedId()!=null && this.getRoutedMedId().equals(castOther.getRoutedMedId()) ) )
 && ( (this.getGeriCode()==castOther.getGeriCode()) || ( this.getGeriCode()!=null && castOther.getGeriCode()!=null && this.getGeriCode().equals(castOther.getGeriCode()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRoutedMedId() == null ? 0 : this.getRoutedMedId().hashCode() );
         result = 37 * result + ( getGeriCode() == null ? 0 : this.getGeriCode().hashCode() );
         return result;
   }   





}