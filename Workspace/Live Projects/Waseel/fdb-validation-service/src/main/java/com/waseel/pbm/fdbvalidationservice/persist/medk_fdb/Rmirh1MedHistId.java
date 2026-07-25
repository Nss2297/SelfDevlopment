package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rmirh1MedHistId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rmirh1MedHistId  implements java.io.Serializable {


    // Fields    

     private Integer medReplMedid;
     private Integer medPrevMedid;


    // Constructors

    /** default constructor */
    public Rmirh1MedHistId() {
    }

    
    /** full constructor */
    public Rmirh1MedHistId(Integer medReplMedid, Integer medPrevMedid) {
        this.medReplMedid = medReplMedid;
        this.medPrevMedid = medPrevMedid;
    }

   
    // Property accessors

    @Column(name="MED_REPL_MEDID", nullable=false, precision=8, scale=0)

    public Integer getMedReplMedid() {
        return this.medReplMedid;
    }
    
    public void setMedReplMedid(Integer medReplMedid) {
        this.medReplMedid = medReplMedid;
    }

    @Column(name="MED_PREV_MEDID", nullable=false, precision=8, scale=0)

    public Integer getMedPrevMedid() {
        return this.medPrevMedid;
    }
    
    public void setMedPrevMedid(Integer medPrevMedid) {
        this.medPrevMedid = medPrevMedid;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rmirh1MedHistId) ) return false;
		 Rmirh1MedHistId castOther = ( Rmirh1MedHistId ) other; 
         
		 return ( (this.getMedReplMedid()==castOther.getMedReplMedid()) || ( this.getMedReplMedid()!=null && castOther.getMedReplMedid()!=null && this.getMedReplMedid().equals(castOther.getMedReplMedid()) ) )
 && ( (this.getMedPrevMedid()==castOther.getMedPrevMedid()) || ( this.getMedPrevMedid()!=null && castOther.getMedPrevMedid()!=null && this.getMedPrevMedid().equals(castOther.getMedPrevMedid()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getMedReplMedid() == null ? 0 : this.getMedReplMedid().hashCode() );
         result = 37 * result + ( getMedPrevMedid() == null ? 0 : this.getMedPrevMedid().hashCode() );
         return result;
   }   





}