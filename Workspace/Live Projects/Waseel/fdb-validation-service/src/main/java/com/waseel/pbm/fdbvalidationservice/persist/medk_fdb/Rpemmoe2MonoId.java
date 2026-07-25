package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rpemmoe2MonoId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rpemmoe2MonoId  implements java.io.Serializable {


    // Fields    

     private Short pemono;
     private Short pemonoeSn;


    // Constructors

    /** default constructor */
    public Rpemmoe2MonoId() {
    }

    
    /** full constructor */
    public Rpemmoe2MonoId(Short pemono, Short pemonoeSn) {
        this.pemono = pemono;
        this.pemonoeSn = pemonoeSn;
    }

   
    // Property accessors

    @Column(name="PEMONO", nullable=false, precision=4, scale=0)

    public Short getPemono() {
        return this.pemono;
    }
    
    public void setPemono(Short pemono) {
        this.pemono = pemono;
    }

    @Column(name="PEMONOE_SN", nullable=false, precision=3, scale=0)

    public Short getPemonoeSn() {
        return this.pemonoeSn;
    }
    
    public void setPemonoeSn(Short pemonoeSn) {
        this.pemonoeSn = pemonoeSn;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rpemmoe2MonoId) ) return false;
		 Rpemmoe2MonoId castOther = ( Rpemmoe2MonoId ) other; 
         
		 return ( (this.getPemono()==castOther.getPemono()) || ( this.getPemono()!=null && castOther.getPemono()!=null && this.getPemono().equals(castOther.getPemono()) ) )
 && ( (this.getPemonoeSn()==castOther.getPemonoeSn()) || ( this.getPemonoeSn()!=null && castOther.getPemonoeSn()!=null && this.getPemonoeSn().equals(castOther.getPemonoeSn()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getPemono() == null ? 0 : this.getPemono().hashCode() );
         result = 37 * result + ( getPemonoeSn() == null ? 0 : this.getPemonoeSn().hashCode() );
         return result;
   }   





}