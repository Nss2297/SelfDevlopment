package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rpregrl0PregReferenceLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rpregrl0PregReferenceLinkId  implements java.io.Serializable {


    // Fields    

     private Integer pregCode;
     private Integer pregReferenceId;


    // Constructors

    /** default constructor */
    public Rpregrl0PregReferenceLinkId() {
    }

    
    /** full constructor */
    public Rpregrl0PregReferenceLinkId(Integer pregCode, Integer pregReferenceId) {
        this.pregCode = pregCode;
        this.pregReferenceId = pregReferenceId;
    }

   
    // Property accessors

    @Column(name="PREG_CODE", nullable=false, precision=6, scale=0)

    public Integer getPregCode() {
        return this.pregCode;
    }
    
    public void setPregCode(Integer pregCode) {
        this.pregCode = pregCode;
    }

    @Column(name="PREG_REFERENCE_ID", nullable=false, precision=8, scale=0)

    public Integer getPregReferenceId() {
        return this.pregReferenceId;
    }
    
    public void setPregReferenceId(Integer pregReferenceId) {
        this.pregReferenceId = pregReferenceId;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rpregrl0PregReferenceLinkId) ) return false;
		 Rpregrl0PregReferenceLinkId castOther = ( Rpregrl0PregReferenceLinkId ) other; 
         
		 return ( (this.getPregCode()==castOther.getPregCode()) || ( this.getPregCode()!=null && castOther.getPregCode()!=null && this.getPregCode().equals(castOther.getPregCode()) ) )
 && ( (this.getPregReferenceId()==castOther.getPregReferenceId()) || ( this.getPregReferenceId()!=null && castOther.getPregReferenceId()!=null && this.getPregReferenceId().equals(castOther.getPregReferenceId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getPregCode() == null ? 0 : this.getPregCode().hashCode() );
         result = 37 * result + ( getPregReferenceId() == null ? 0 : this.getPregReferenceId().hashCode() );
         return result;
   }   





}