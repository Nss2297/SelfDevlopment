package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rddcmrg0RoutedGenLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rddcmrg0RoutedGenLinkId  implements java.io.Serializable {


    // Fields    

     private Integer routedGenId;
     private Integer ddxcn;


    // Constructors

    /** default constructor */
    public Rddcmrg0RoutedGenLinkId() {
    }

    
    /** full constructor */
    public Rddcmrg0RoutedGenLinkId(Integer routedGenId, Integer ddxcn) {
        this.routedGenId = routedGenId;
        this.ddxcn = ddxcn;
    }

   
    // Property accessors

    @Column(name="ROUTED_GEN_ID", nullable=false, precision=8, scale=0)

    public Integer getRoutedGenId() {
        return this.routedGenId;
    }
    
    public void setRoutedGenId(Integer routedGenId) {
        this.routedGenId = routedGenId;
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
		 if ( !(other instanceof Rddcmrg0RoutedGenLinkId) ) return false;
		 Rddcmrg0RoutedGenLinkId castOther = ( Rddcmrg0RoutedGenLinkId ) other; 
         
		 return ( (this.getRoutedGenId()==castOther.getRoutedGenId()) || ( this.getRoutedGenId()!=null && castOther.getRoutedGenId()!=null && this.getRoutedGenId().equals(castOther.getRoutedGenId()) ) )
 && ( (this.getDdxcn()==castOther.getDdxcn()) || ( this.getDdxcn()!=null && castOther.getDdxcn()!=null && this.getDdxcn().equals(castOther.getDdxcn()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRoutedGenId() == null ? 0 : this.getRoutedGenId().hashCode() );
         result = 37 * result + ( getDdxcn() == null ? 0 : this.getDdxcn().hashCode() );
         return result;
   }   





}