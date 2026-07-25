package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rindmrm0RoutedMedLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rindmrm0RoutedMedLinkId  implements java.io.Serializable {


    // Fields    

     private Integer routedMedId;
     private Integer indcts;


    // Constructors

    /** default constructor */
    public Rindmrm0RoutedMedLinkId() {
    }

    
    /** full constructor */
    public Rindmrm0RoutedMedLinkId(Integer routedMedId, Integer indcts) {
        this.routedMedId = routedMedId;
        this.indcts = indcts;
    }

   
    // Property accessors

    @Column(name="ROUTED_MED_ID", nullable=false, precision=8, scale=0)

    public Integer getRoutedMedId() {
        return this.routedMedId;
    }
    
    public void setRoutedMedId(Integer routedMedId) {
        this.routedMedId = routedMedId;
    }

    @Column(name="INDCTS", nullable=false, precision=5, scale=0)

    public Integer getIndcts() {
        return this.indcts;
    }
    
    public void setIndcts(Integer indcts) {
        this.indcts = indcts;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rindmrm0RoutedMedLinkId) ) return false;
		 Rindmrm0RoutedMedLinkId castOther = ( Rindmrm0RoutedMedLinkId ) other; 
         
		 return ( (this.getRoutedMedId()==castOther.getRoutedMedId()) || ( this.getRoutedMedId()!=null && castOther.getRoutedMedId()!=null && this.getRoutedMedId().equals(castOther.getRoutedMedId()) ) )
 && ( (this.getIndcts()==castOther.getIndcts()) || ( this.getIndcts()!=null && castOther.getIndcts()!=null && this.getIndcts().equals(castOther.getIndcts()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRoutedMedId() == null ? 0 : this.getRoutedMedId().hashCode() );
         result = 37 * result + ( getIndcts() == null ? 0 : this.getIndcts().hashCode() );
         return result;
   }   





}