package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rlacted0ExcrtPotentialDescId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rlacted0ExcrtPotentialDescId  implements java.io.Serializable {


    // Fields    

     private String lactExcrt;
     private Byte lactExcrtsn;


    // Constructors

    /** default constructor */
    public Rlacted0ExcrtPotentialDescId() {
    }

    
    /** full constructor */
    public Rlacted0ExcrtPotentialDescId(String lactExcrt, Byte lactExcrtsn) {
        this.lactExcrt = lactExcrt;
        this.lactExcrtsn = lactExcrtsn;
    }

   
    // Property accessors

    @Column(name="LACT_EXCRT", nullable=false, length=1)

    public String getLactExcrt() {
        return this.lactExcrt;
    }
    
    public void setLactExcrt(String lactExcrt) {
        this.lactExcrt = lactExcrt;
    }

    @Column(name="LACT_EXCRTSN", nullable=false, precision=2, scale=0)

    public Byte getLactExcrtsn() {
        return this.lactExcrtsn;
    }
    
    public void setLactExcrtsn(Byte lactExcrtsn) {
        this.lactExcrtsn = lactExcrtsn;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rlacted0ExcrtPotentialDescId) ) return false;
		 Rlacted0ExcrtPotentialDescId castOther = ( Rlacted0ExcrtPotentialDescId ) other; 
         
		 return ( (this.getLactExcrt()==castOther.getLactExcrt()) || ( this.getLactExcrt()!=null && castOther.getLactExcrt()!=null && this.getLactExcrt().equals(castOther.getLactExcrt()) ) )
 && ( (this.getLactExcrtsn()==castOther.getLactExcrtsn()) || ( this.getLactExcrtsn()!=null && castOther.getLactExcrtsn()!=null && this.getLactExcrtsn().equals(castOther.getLactExcrtsn()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getLactExcrt() == null ? 0 : this.getLactExcrt().hashCode() );
         result = 37 * result + ( getLactExcrtsn() == null ? 0 : this.getLactExcrtsn().hashCode() );
         return result;
   }   





}