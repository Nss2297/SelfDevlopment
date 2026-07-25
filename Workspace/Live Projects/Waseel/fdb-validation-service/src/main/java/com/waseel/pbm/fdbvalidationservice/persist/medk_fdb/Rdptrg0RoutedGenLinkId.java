package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rdptrg0RoutedGenLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rdptrg0RoutedGenLinkId  implements java.io.Serializable {


    // Fields    

     private Integer routedGenId;
     private Integer dptClassId;


    // Constructors

    /** default constructor */
    public Rdptrg0RoutedGenLinkId() {
    }

    
    /** full constructor */
    public Rdptrg0RoutedGenLinkId(Integer routedGenId, Integer dptClassId) {
        this.routedGenId = routedGenId;
        this.dptClassId = dptClassId;
    }

   
    // Property accessors

    @Column(name="ROUTED_GEN_ID", nullable=false, precision=8, scale=0)

    public Integer getRoutedGenId() {
        return this.routedGenId;
    }
    
    public void setRoutedGenId(Integer routedGenId) {
        this.routedGenId = routedGenId;
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
		 if ( !(other instanceof Rdptrg0RoutedGenLinkId) ) return false;
		 Rdptrg0RoutedGenLinkId castOther = ( Rdptrg0RoutedGenLinkId ) other; 
         
		 return ( (this.getRoutedGenId()==castOther.getRoutedGenId()) || ( this.getRoutedGenId()!=null && castOther.getRoutedGenId()!=null && this.getRoutedGenId().equals(castOther.getRoutedGenId()) ) )
 && ( (this.getDptClassId()==castOther.getDptClassId()) || ( this.getDptClassId()!=null && castOther.getDptClassId()!=null && this.getDptClassId().equals(castOther.getDptClassId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRoutedGenId() == null ? 0 : this.getRoutedGenId().hashCode() );
         result = 37 * result + ( getDptClassId() == null ? 0 : this.getDptClassId().hashCode() );
         return result;
   }   





}