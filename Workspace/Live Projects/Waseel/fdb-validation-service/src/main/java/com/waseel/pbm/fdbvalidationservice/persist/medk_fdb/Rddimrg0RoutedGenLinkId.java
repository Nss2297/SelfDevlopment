package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rddimrg0RoutedGenLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rddimrg0RoutedGenLinkId  implements java.io.Serializable {


    // Fields    

     private Integer routedGenId;
     private Integer ddiCodex;


    // Constructors

    /** default constructor */
    public Rddimrg0RoutedGenLinkId() {
    }

    
    /** full constructor */
    public Rddimrg0RoutedGenLinkId(Integer routedGenId, Integer ddiCodex) {
        this.routedGenId = routedGenId;
        this.ddiCodex = ddiCodex;
    }

   
    // Property accessors

    @Column(name="ROUTED_GEN_ID", nullable=false, precision=8, scale=0)

    public Integer getRoutedGenId() {
        return this.routedGenId;
    }
    
    public void setRoutedGenId(Integer routedGenId) {
        this.routedGenId = routedGenId;
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
		 if ( !(other instanceof Rddimrg0RoutedGenLinkId) ) return false;
		 Rddimrg0RoutedGenLinkId castOther = ( Rddimrg0RoutedGenLinkId ) other; 
         
		 return ( (this.getRoutedGenId()==castOther.getRoutedGenId()) || ( this.getRoutedGenId()!=null && castOther.getRoutedGenId()!=null && this.getRoutedGenId().equals(castOther.getRoutedGenId()) ) )
 && ( (this.getDdiCodex()==castOther.getDdiCodex()) || ( this.getDdiCodex()!=null && castOther.getDdiCodex()!=null && this.getDdiCodex().equals(castOther.getDdiCodex()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRoutedGenId() == null ? 0 : this.getRoutedGenId().hashCode() );
         result = 37 * result + ( getDdiCodex() == null ? 0 : this.getDdiCodex().hashCode() );
         return result;
   }   





}