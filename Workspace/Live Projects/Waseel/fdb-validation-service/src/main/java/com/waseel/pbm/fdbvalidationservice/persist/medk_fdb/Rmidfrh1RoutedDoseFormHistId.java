package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rmidfrh1RoutedDoseFormHistId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rmidfrh1RoutedDoseFormHistId  implements java.io.Serializable {


    // Fields    

     private Integer medReplRoutedDfMedId;
     private Integer medPrevRoutedDfMedId;


    // Constructors

    /** default constructor */
    public Rmidfrh1RoutedDoseFormHistId() {
    }

    
    /** full constructor */
    public Rmidfrh1RoutedDoseFormHistId(Integer medReplRoutedDfMedId, Integer medPrevRoutedDfMedId) {
        this.medReplRoutedDfMedId = medReplRoutedDfMedId;
        this.medPrevRoutedDfMedId = medPrevRoutedDfMedId;
    }

   
    // Property accessors

    @Column(name="MED_REPL_ROUTED_DF_MED_ID", nullable=false, precision=8, scale=0)

    public Integer getMedReplRoutedDfMedId() {
        return this.medReplRoutedDfMedId;
    }
    
    public void setMedReplRoutedDfMedId(Integer medReplRoutedDfMedId) {
        this.medReplRoutedDfMedId = medReplRoutedDfMedId;
    }

    @Column(name="MED_PREV_ROUTED_DF_MED_ID", nullable=false, precision=8, scale=0)

    public Integer getMedPrevRoutedDfMedId() {
        return this.medPrevRoutedDfMedId;
    }
    
    public void setMedPrevRoutedDfMedId(Integer medPrevRoutedDfMedId) {
        this.medPrevRoutedDfMedId = medPrevRoutedDfMedId;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rmidfrh1RoutedDoseFormHistId) ) return false;
		 Rmidfrh1RoutedDoseFormHistId castOther = ( Rmidfrh1RoutedDoseFormHistId ) other; 
         
		 return ( (this.getMedReplRoutedDfMedId()==castOther.getMedReplRoutedDfMedId()) || ( this.getMedReplRoutedDfMedId()!=null && castOther.getMedReplRoutedDfMedId()!=null && this.getMedReplRoutedDfMedId().equals(castOther.getMedReplRoutedDfMedId()) ) )
 && ( (this.getMedPrevRoutedDfMedId()==castOther.getMedPrevRoutedDfMedId()) || ( this.getMedPrevRoutedDfMedId()!=null && castOther.getMedPrevRoutedDfMedId()!=null && this.getMedPrevRoutedDfMedId().equals(castOther.getMedPrevRoutedDfMedId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getMedReplRoutedDfMedId() == null ? 0 : this.getMedReplRoutedDfMedId().hashCode() );
         result = 37 * result + ( getMedPrevRoutedDfMedId() == null ? 0 : this.getMedPrevRoutedDfMedId().hashCode() );
         return result;
   }   





}