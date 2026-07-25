package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rdrcrl0RenalMonoLineId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rdrcrl0RenalMonoLineId  implements java.io.Serializable {


    // Fields    

     private Integer renMonoId;
     private Integer renMonoLineNumber;


    // Constructors

    /** default constructor */
    public Rdrcrl0RenalMonoLineId() {
    }

    
    /** full constructor */
    public Rdrcrl0RenalMonoLineId(Integer renMonoId, Integer renMonoLineNumber) {
        this.renMonoId = renMonoId;
        this.renMonoLineNumber = renMonoLineNumber;
    }

   
    // Property accessors

    @Column(name="REN_MONO_ID", nullable=false, precision=8, scale=0)

    public Integer getRenMonoId() {
        return this.renMonoId;
    }
    
    public void setRenMonoId(Integer renMonoId) {
        this.renMonoId = renMonoId;
    }

    @Column(name="REN_MONO_LINE_NUMBER", nullable=false, precision=8, scale=0)

    public Integer getRenMonoLineNumber() {
        return this.renMonoLineNumber;
    }
    
    public void setRenMonoLineNumber(Integer renMonoLineNumber) {
        this.renMonoLineNumber = renMonoLineNumber;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rdrcrl0RenalMonoLineId) ) return false;
		 Rdrcrl0RenalMonoLineId castOther = ( Rdrcrl0RenalMonoLineId ) other; 
         
		 return ( (this.getRenMonoId()==castOther.getRenMonoId()) || ( this.getRenMonoId()!=null && castOther.getRenMonoId()!=null && this.getRenMonoId().equals(castOther.getRenMonoId()) ) )
 && ( (this.getRenMonoLineNumber()==castOther.getRenMonoLineNumber()) || ( this.getRenMonoLineNumber()!=null && castOther.getRenMonoLineNumber()!=null && this.getRenMonoLineNumber().equals(castOther.getRenMonoLineNumber()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getRenMonoId() == null ? 0 : this.getRenMonoId().hashCode() );
         result = 37 * result + ( getRenMonoLineNumber() == null ? 0 : this.getRenMonoLineNumber().hashCode() );
         return result;
   }   





}