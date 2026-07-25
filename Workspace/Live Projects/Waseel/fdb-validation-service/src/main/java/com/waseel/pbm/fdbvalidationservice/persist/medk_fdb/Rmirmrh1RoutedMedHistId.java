package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rmirmrh1RoutedMedHistId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rmirmrh1RoutedMedHistId  implements java.io.Serializable {


    // Fields    

     private Integer medReplRoutedMedId;
     private Integer medPrevRoutedMedId;


    // Constructors

    /** default constructor */
    public Rmirmrh1RoutedMedHistId() {
    }

    
    /** full constructor */
    public Rmirmrh1RoutedMedHistId(Integer medReplRoutedMedId, Integer medPrevRoutedMedId) {
        this.medReplRoutedMedId = medReplRoutedMedId;
        this.medPrevRoutedMedId = medPrevRoutedMedId;
    }

   
    // Property accessors

    @Column(name="MED_REPL_ROUTED_MED_ID", nullable=false, precision=8, scale=0)

    public Integer getMedReplRoutedMedId() {
        return this.medReplRoutedMedId;
    }
    
    public void setMedReplRoutedMedId(Integer medReplRoutedMedId) {
        this.medReplRoutedMedId = medReplRoutedMedId;
    }

    @Column(name="MED_PREV_ROUTED_MED_ID", nullable=false, precision=8, scale=0)

    public Integer getMedPrevRoutedMedId() {
        return this.medPrevRoutedMedId;
    }
    
    public void setMedPrevRoutedMedId(Integer medPrevRoutedMedId) {
        this.medPrevRoutedMedId = medPrevRoutedMedId;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rmirmrh1RoutedMedHistId) ) return false;
		 Rmirmrh1RoutedMedHistId castOther = ( Rmirmrh1RoutedMedHistId ) other; 
         
		 return ( (this.getMedReplRoutedMedId()==castOther.getMedReplRoutedMedId()) || ( this.getMedReplRoutedMedId()!=null && castOther.getMedReplRoutedMedId()!=null && this.getMedReplRoutedMedId().equals(castOther.getMedReplRoutedMedId()) ) )
 && ( (this.getMedPrevRoutedMedId()==castOther.getMedPrevRoutedMedId()) || ( this.getMedPrevRoutedMedId()!=null && castOther.getMedPrevRoutedMedId()!=null && this.getMedPrevRoutedMedId().equals(castOther.getMedPrevRoutedMedId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getMedReplRoutedMedId() == null ? 0 : this.getMedReplRoutedMedId().hashCode() );
         result = 37 * result + ( getMedPrevRoutedMedId() == null ? 0 : this.getMedPrevRoutedMedId().hashCode() );
         return result;
   }   





}