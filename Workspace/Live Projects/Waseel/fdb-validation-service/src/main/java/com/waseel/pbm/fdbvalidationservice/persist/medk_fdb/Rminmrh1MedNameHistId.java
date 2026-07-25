package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rminmrh1MedNameHistId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rminmrh1MedNameHistId  implements java.io.Serializable {


    // Fields    

     private Integer medReplNameId;
     private Integer medPrevNameId;


    // Constructors

    /** default constructor */
    public Rminmrh1MedNameHistId() {
    }

    
    /** full constructor */
    public Rminmrh1MedNameHistId(Integer medReplNameId, Integer medPrevNameId) {
        this.medReplNameId = medReplNameId;
        this.medPrevNameId = medPrevNameId;
    }

   
    // Property accessors

    @Column(name="MED_REPL_NAME_ID", nullable=false, precision=8, scale=0)

    public Integer getMedReplNameId() {
        return this.medReplNameId;
    }
    
    public void setMedReplNameId(Integer medReplNameId) {
        this.medReplNameId = medReplNameId;
    }

    @Column(name="MED_PREV_NAME_ID", nullable=false, precision=8, scale=0)

    public Integer getMedPrevNameId() {
        return this.medPrevNameId;
    }
    
    public void setMedPrevNameId(Integer medPrevNameId) {
        this.medPrevNameId = medPrevNameId;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rminmrh1MedNameHistId) ) return false;
		 Rminmrh1MedNameHistId castOther = ( Rminmrh1MedNameHistId ) other; 
         
		 return ( (this.getMedReplNameId()==castOther.getMedReplNameId()) || ( this.getMedReplNameId()!=null && castOther.getMedReplNameId()!=null && this.getMedReplNameId().equals(castOther.getMedReplNameId()) ) )
 && ( (this.getMedPrevNameId()==castOther.getMedPrevNameId()) || ( this.getMedPrevNameId()!=null && castOther.getMedPrevNameId()!=null && this.getMedPrevNameId().equals(castOther.getMedPrevNameId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getMedReplNameId() == null ? 0 : this.getMedReplNameId().hashCode() );
         result = 37 * result + ( getMedPrevNameId() == null ? 0 : this.getMedPrevNameId().hashCode() );
         return result;
   }   





}