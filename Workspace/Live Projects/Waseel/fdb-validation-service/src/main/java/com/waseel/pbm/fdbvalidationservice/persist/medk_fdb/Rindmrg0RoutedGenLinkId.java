package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rindmrg0RoutedGenLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rindmrg0RoutedGenLinkId  implements java.io.Serializable {


    // Fields    

     private Integer routedGenId;
     private Integer indcts;


    // Constructors

    /** default constructor */
    public Rindmrg0RoutedGenLinkId() {
    }

    
    /** full constructor */
    public Rindmrg0RoutedGenLinkId(Integer routedGenId, Integer indcts) {
        this.routedGenId = routedGenId;
        this.indcts = indcts;
    }

   
    // Property accessors

    @Column(name="ROUTED_GEN_ID", nullable=false, precision=8, scale=0)

    public Integer getRoutedGenId() {
        return this.routedGenId;
    }
    
    public void setRoutedGenId(Integer routedGenId) {
        this.routedGenId = routedGenId;
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
		 if ( !(other instanceof Rindmrg0RoutedGenLinkId) ) return false;
		 Rindmrg0RoutedGenLinkId castOther = ( Rindmrg0RoutedGenLinkId ) other; 
         
		 return ( (this.getRoutedGenId()==castOther.getRoutedGenId()) || ( this.getRoutedGenId()!=null && castOther.getRoutedGenId()!=null && this.getRoutedGenId().equals(castOther.getRoutedGenId()) ) )
 && ( (this.getIndcts()==castOther.getIndcts()) || ( this.getIndcts()!=null && castOther.getIndcts()!=null && this.getIndcts().equals(castOther.getIndcts()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRoutedGenId() == null ? 0 : this.getRoutedGenId().hashCode() );
         result = 37 * result + ( getIndcts() == null ? 0 : this.getIndcts().hashCode() );
         return result;
   }   





}