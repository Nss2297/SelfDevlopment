package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rddimrm0RoutedMedLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rddimrm0RoutedMedLinkId  implements java.io.Serializable {


    // Fields    

     private Integer routedMedId;
     private Integer ddiCodex;


    // Constructors

    /** default constructor */
    public Rddimrm0RoutedMedLinkId() {
    }

    
    /** full constructor */
    public Rddimrm0RoutedMedLinkId(Integer routedMedId, Integer ddiCodex) {
        this.routedMedId = routedMedId;
        this.ddiCodex = ddiCodex;
    }

   
    // Property accessors

    @Column(name="ROUTED_MED_ID", nullable=false, precision=8, scale=0)

    public Integer getRoutedMedId() {
        return this.routedMedId;
    }
    
    public void setRoutedMedId(Integer routedMedId) {
        this.routedMedId = routedMedId;
    }

    @Column(name="DDI_CODEX", nullable=false, precision=5, scale=0)

    public Integer getDdiCodex() {
        return this.ddiCodex;
    }
    
    public void setDdiCodex(Integer ddiCodex) {
        this.ddiCodex = ddiCodex;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rddimrm0RoutedMedLinkId) ) return false;
		 Rddimrm0RoutedMedLinkId castOther = ( Rddimrm0RoutedMedLinkId ) other; 
         
		 return ( (this.getRoutedMedId()==castOther.getRoutedMedId()) || ( this.getRoutedMedId()!=null && castOther.getRoutedMedId()!=null && this.getRoutedMedId().equals(castOther.getRoutedMedId()) ) )
 && ( (this.getDdiCodex()==castOther.getDdiCodex()) || ( this.getDdiCodex()!=null && castOther.getDdiCodex()!=null && this.getDdiCodex().equals(castOther.getDdiCodex()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRoutedMedId() == null ? 0 : this.getRoutedMedId().hashCode() );
         result = 37 * result + ( getDdiCodex() == null ? 0 : this.getDdiCodex().hashCode() );
         return result;
   }   





}