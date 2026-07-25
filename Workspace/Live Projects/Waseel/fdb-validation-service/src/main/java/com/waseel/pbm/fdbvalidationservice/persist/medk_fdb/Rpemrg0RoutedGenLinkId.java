package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rpemrg0RoutedGenLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rpemrg0RoutedGenLinkId  implements java.io.Serializable {


    // Fields    

     private Integer routedGenId;
     private Integer pec;


    // Constructors

    /** default constructor */
    public Rpemrg0RoutedGenLinkId() {
    }

    
    /** full constructor */
    public Rpemrg0RoutedGenLinkId(Integer routedGenId, Integer pec) {
        this.routedGenId = routedGenId;
        this.pec = pec;
    }

   
    // Property accessors

    @Column(name="ROUTED_GEN_ID", nullable=false, precision=8, scale=0)

    public Integer getRoutedGenId() {
        return this.routedGenId;
    }
    
    public void setRoutedGenId(Integer routedGenId) {
        this.routedGenId = routedGenId;
    }

    @Column(name="PEC", nullable=false, precision=6, scale=0)

    public Integer getPec() {
        return this.pec;
    }
    
    public void setPec(Integer pec) {
        this.pec = pec;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rpemrg0RoutedGenLinkId) ) return false;
		 Rpemrg0RoutedGenLinkId castOther = ( Rpemrg0RoutedGenLinkId ) other; 
         
		 return ( (this.getRoutedGenId()==castOther.getRoutedGenId()) || ( this.getRoutedGenId()!=null && castOther.getRoutedGenId()!=null && this.getRoutedGenId().equals(castOther.getRoutedGenId()) ) )
 && ( (this.getPec()==castOther.getPec()) || ( this.getPec()!=null && castOther.getPec()!=null && this.getPec().equals(castOther.getPec()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRoutedGenId() == null ? 0 : this.getRoutedGenId().hashCode() );
         result = 37 * result + ( getPec() == null ? 0 : this.getPec().hashCode() );
         return result;
   }   





}