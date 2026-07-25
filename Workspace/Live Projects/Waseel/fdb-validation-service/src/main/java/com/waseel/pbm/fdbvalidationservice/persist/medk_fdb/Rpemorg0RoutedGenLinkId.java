package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rpemorg0RoutedGenLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rpemorg0RoutedGenLinkId  implements java.io.Serializable {


    // Fields    

     private Integer routedGenId;
     private Short pemono;


    // Constructors

    /** default constructor */
    public Rpemorg0RoutedGenLinkId() {
    }

    
    /** full constructor */
    public Rpemorg0RoutedGenLinkId(Integer routedGenId, Short pemono) {
        this.routedGenId = routedGenId;
        this.pemono = pemono;
    }

   
    // Property accessors

    @Column(name="ROUTED_GEN_ID", nullable=false, precision=8, scale=0)

    public Integer getRoutedGenId() {
        return this.routedGenId;
    }
    
    public void setRoutedGenId(Integer routedGenId) {
        this.routedGenId = routedGenId;
    }

    @Column(name="PEMONO", nullable=false, precision=4, scale=0)

    public Short getPemono() {
        return this.pemono;
    }
    
    public void setPemono(Short pemono) {
        this.pemono = pemono;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rpemorg0RoutedGenLinkId) ) return false;
		 Rpemorg0RoutedGenLinkId castOther = ( Rpemorg0RoutedGenLinkId ) other; 
         
		 return ( (this.getRoutedGenId()==castOther.getRoutedGenId()) || ( this.getRoutedGenId()!=null && castOther.getRoutedGenId()!=null && this.getRoutedGenId().equals(castOther.getRoutedGenId()) ) )
 && ( (this.getPemono()==castOther.getPemono()) || ( this.getPemono()!=null && castOther.getPemono()!=null && this.getPemono().equals(castOther.getPemono()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRoutedGenId() == null ? 0 : this.getRoutedGenId().hashCode() );
         result = 37 * result + ( getPemono() == null ? 0 : this.getPemono().hashCode() );
         return result;
   }   





}