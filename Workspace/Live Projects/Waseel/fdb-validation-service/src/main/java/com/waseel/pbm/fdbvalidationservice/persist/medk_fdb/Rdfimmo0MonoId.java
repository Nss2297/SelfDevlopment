package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rdfimmo0MonoId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rdfimmo0MonoId  implements java.io.Serializable {


    // Fields    

     private Short fdcde;
     private Short fdcdeSn;


    // Constructors

    /** default constructor */
    public Rdfimmo0MonoId() {
    }

    
    /** full constructor */
    public Rdfimmo0MonoId(Short fdcde, Short fdcdeSn) {
        this.fdcde = fdcde;
        this.fdcdeSn = fdcdeSn;
    }

   
    // Property accessors

    @Column(name="FDCDE", nullable=false, precision=3, scale=0)

    public Short getFdcde() {
        return this.fdcde;
    }
    
    public void setFdcde(Short fdcde) {
        this.fdcde = fdcde;
    }

    @Column(name="FDCDE_SN", nullable=false, precision=3, scale=0)

    public Short getFdcdeSn() {
        return this.fdcdeSn;
    }
    
    public void setFdcdeSn(Short fdcdeSn) {
        this.fdcdeSn = fdcdeSn;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rdfimmo0MonoId) ) return false;
		 Rdfimmo0MonoId castOther = ( Rdfimmo0MonoId ) other; 
         
		 return ( (this.getFdcde()==castOther.getFdcde()) || ( this.getFdcde()!=null && castOther.getFdcde()!=null && this.getFdcde().equals(castOther.getFdcde()) ) )
 && ( (this.getFdcdeSn()==castOther.getFdcdeSn()) || ( this.getFdcdeSn()!=null && castOther.getFdcdeSn()!=null && this.getFdcdeSn().equals(castOther.getFdcdeSn()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getFdcde() == null ? 0 : this.getFdcde().hashCode() );
         result = 37 * result + ( getFdcdeSn() == null ? 0 : this.getFdcdeSn().hashCode() );
         return result;
   }   





}