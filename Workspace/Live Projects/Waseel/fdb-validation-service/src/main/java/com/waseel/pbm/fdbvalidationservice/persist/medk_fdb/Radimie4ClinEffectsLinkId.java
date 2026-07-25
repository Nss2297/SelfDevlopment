package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Radimie4ClinEffectsLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Radimie4ClinEffectsLinkId  implements java.io.Serializable {


    // Fields    

     private Integer ddiCodex;
     private String adiEfftc;


    // Constructors

    /** default constructor */
    public Radimie4ClinEffectsLinkId() {
    }

    
    /** full constructor */
    public Radimie4ClinEffectsLinkId(Integer ddiCodex, String adiEfftc) {
        this.ddiCodex = ddiCodex;
        this.adiEfftc = adiEfftc;
    }

   
    // Property accessors

    @Column(name="DDI_CODEX", nullable=false, precision=5, scale=0)

    public Integer getDdiCodex() {
        return this.ddiCodex;
    }
    
    public void setDdiCodex(Integer ddiCodex) {
        this.ddiCodex = ddiCodex;
    }

    @Column(name="ADI_EFFTC", nullable=false, length=3)

    public String getAdiEfftc() {
        return this.adiEfftc;
    }
    
    public void setAdiEfftc(String adiEfftc) {
        this.adiEfftc = adiEfftc;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Radimie4ClinEffectsLinkId) ) return false;
		 Radimie4ClinEffectsLinkId castOther = ( Radimie4ClinEffectsLinkId ) other; 
         
		 return ( (this.getDdiCodex()==castOther.getDdiCodex()) || ( this.getDdiCodex()!=null && castOther.getDdiCodex()!=null && this.getDdiCodex().equals(castOther.getDdiCodex()) ) )
 && ( (this.getAdiEfftc()==castOther.getAdiEfftc()) || ( this.getAdiEfftc()!=null && castOther.getAdiEfftc()!=null && this.getAdiEfftc().equals(castOther.getAdiEfftc()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getDdiCodex() == null ? 0 : this.getDdiCodex().hashCode() );
         result = 37 * result + ( getAdiEfftc() == null ? 0 : this.getAdiEfftc().hashCode() );
         return result;
   }   





}