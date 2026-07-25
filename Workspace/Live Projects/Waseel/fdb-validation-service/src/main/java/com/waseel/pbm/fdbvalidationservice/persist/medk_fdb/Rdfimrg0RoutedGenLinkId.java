package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rdfimrg0RoutedGenLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rdfimrg0RoutedGenLinkId  implements java.io.Serializable {


    // Fields    

     private Integer routedGenId;
     private Short fdcde;


    // Constructors

    /** default constructor */
    public Rdfimrg0RoutedGenLinkId() {
    }

    
    /** full constructor */
    public Rdfimrg0RoutedGenLinkId(Integer routedGenId, Short fdcde) {
        this.routedGenId = routedGenId;
        this.fdcde = fdcde;
    }

   
    // Property accessors

    @Column(name="ROUTED_GEN_ID", nullable=false, precision=8, scale=0)

    public Integer getRoutedGenId() {
        return this.routedGenId;
    }
    
    public void setRoutedGenId(Integer routedGenId) {
        this.routedGenId = routedGenId;
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
		 if ( !(other instanceof Rdfimrg0RoutedGenLinkId) ) return false;
		 Rdfimrg0RoutedGenLinkId castOther = ( Rdfimrg0RoutedGenLinkId ) other; 
         
		 return ( (this.getRoutedGenId()==castOther.getRoutedGenId()) || ( this.getRoutedGenId()!=null && castOther.getRoutedGenId()!=null && this.getRoutedGenId().equals(castOther.getRoutedGenId()) ) )
 && ( (this.getFdcde()==castOther.getFdcde()) || ( this.getFdcde()!=null && castOther.getFdcde()!=null && this.getFdcde().equals(castOther.getFdcde()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRoutedGenId() == null ? 0 : this.getRoutedGenId().hashCode() );
         result = 37 * result + ( getFdcde() == null ? 0 : this.getFdcde().hashCode() );
         return result;
   }   





}