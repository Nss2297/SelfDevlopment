package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rddcmrm0RoutedMedLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rddcmrm0RoutedMedLinkId  implements java.io.Serializable {


    // Fields    

     private Integer routedMedId;
     private Integer ddxcn;


    // Constructors

    /** default constructor */
    public Rddcmrm0RoutedMedLinkId() {
    }

    
    /** full constructor */
    public Rddcmrm0RoutedMedLinkId(Integer routedMedId, Integer ddxcn) {
        this.routedMedId = routedMedId;
        this.ddxcn = ddxcn;
    }

   
    // Property accessors

    @Column(name="ROUTED_MED_ID", nullable=false, precision=8, scale=0)

    public Integer getRoutedMedId() {
        return this.routedMedId;
    }
    
    public void setRoutedMedId(Integer routedMedId) {
        this.routedMedId = routedMedId;
    }

    @Column(name="DDXCN", nullable=false, precision=5, scale=0)

    public Integer getDdxcn() {
        return this.ddxcn;
    }
    
    public void setDdxcn(Integer ddxcn) {
        this.ddxcn = ddxcn;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rddcmrm0RoutedMedLinkId) ) return false;
		 Rddcmrm0RoutedMedLinkId castOther = ( Rddcmrm0RoutedMedLinkId ) other; 
         
		 return ( (this.getRoutedMedId()==castOther.getRoutedMedId()) || ( this.getRoutedMedId()!=null && castOther.getRoutedMedId()!=null && this.getRoutedMedId().equals(castOther.getRoutedMedId()) ) )
 && ( (this.getDdxcn()==castOther.getDdxcn()) || ( this.getDdxcn()!=null && castOther.getDdxcn()!=null && this.getDdxcn().equals(castOther.getDdxcn()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRoutedMedId() == null ? 0 : this.getRoutedMedId().hashCode() );
         result = 37 * result + ( getDdxcn() == null ? 0 : this.getDdxcn().hashCode() );
         return result;
   }   





}