package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rlactid0EffectsInfantsDescId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rlactid0EffectsInfantsDescId  implements java.io.Serializable {


    // Fields    

     private String lactLctn;
     private Byte lactLctnsn;


    // Constructors

    /** default constructor */
    public Rlactid0EffectsInfantsDescId() {
    }

    
    /** full constructor */
    public Rlactid0EffectsInfantsDescId(String lactLctn, Byte lactLctnsn) {
        this.lactLctn = lactLctn;
        this.lactLctnsn = lactLctnsn;
    }

   
    // Property accessors

    @Column(name="LACT_LCTN", nullable=false, length=1)

    public String getLactLctn() {
        return this.lactLctn;
    }
    
    public void setLactLctn(String lactLctn) {
        this.lactLctn = lactLctn;
    }

    @Column(name="LACT_LCTNSN", nullable=false, precision=2, scale=0)

    public Byte getLactLctnsn() {
        return this.lactLctnsn;
    }
    
    public void setLactLctnsn(Byte lactLctnsn) {
        this.lactLctnsn = lactLctnsn;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rlactid0EffectsInfantsDescId) ) return false;
		 Rlactid0EffectsInfantsDescId castOther = ( Rlactid0EffectsInfantsDescId ) other; 
         
		 return ( (this.getLactLctn()==castOther.getLactLctn()) || ( this.getLactLctn()!=null && castOther.getLactLctn()!=null && this.getLactLctn().equals(castOther.getLactLctn()) ) )
 && ( (this.getLactLctnsn()==castOther.getLactLctnsn()) || ( this.getLactLctnsn()!=null && castOther.getLactLctnsn()!=null && this.getLactLctnsn().equals(castOther.getLactLctnsn()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getLactLctn() == null ? 0 : this.getLactLctn().hashCode() );
         result = 37 * result + ( getLactLctnsn() == null ? 0 : this.getLactLctnsn().hashCode() );
         return result;
   }   





}