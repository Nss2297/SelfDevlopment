package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Radimmo5MonoId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Radimmo5MonoId  implements java.io.Serializable {


    // Fields    

     private Integer ddiMonox;
     private Short adiMonosn;


    // Constructors

    /** default constructor */
    public Radimmo5MonoId() {
    }

    
    /** full constructor */
    public Radimmo5MonoId(Integer ddiMonox, Short adiMonosn) {
        this.ddiMonox = ddiMonox;
        this.adiMonosn = adiMonosn;
    }

   
    // Property accessors

    @Column(name="DDI_MONOX", nullable=false, precision=5, scale=0)

    public Integer getDdiMonox() {
        return this.ddiMonox;
    }
    
    public void setDdiMonox(Integer ddiMonox) {
        this.ddiMonox = ddiMonox;
    }

    @Column(name="ADI_MONOSN", nullable=false, precision=3, scale=0)

    public Short getAdiMonosn() {
        return this.adiMonosn;
    }
    
    public void setAdiMonosn(Short adiMonosn) {
        this.adiMonosn = adiMonosn;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Radimmo5MonoId) ) return false;
		 Radimmo5MonoId castOther = ( Radimmo5MonoId ) other; 
         
		 return ( (this.getDdiMonox()==castOther.getDdiMonox()) || ( this.getDdiMonox()!=null && castOther.getDdiMonox()!=null && this.getDdiMonox().equals(castOther.getDdiMonox()) ) )
 && ( (this.getAdiMonosn()==castOther.getAdiMonosn()) || ( this.getAdiMonosn()!=null && castOther.getAdiMonosn()!=null && this.getAdiMonosn().equals(castOther.getAdiMonosn()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getDdiMonox() == null ? 0 : this.getDdiMonox().hashCode() );
         result = 37 * result + ( getAdiMonosn() == null ? 0 : this.getAdiMonosn().hashCode() );
         return result;
   }   





}