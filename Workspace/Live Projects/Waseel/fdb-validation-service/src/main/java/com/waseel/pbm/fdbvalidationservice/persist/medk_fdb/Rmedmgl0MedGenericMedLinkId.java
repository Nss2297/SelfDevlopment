package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rmedmgl0MedGenericMedLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rmedmgl0MedGenericMedLinkId  implements java.io.Serializable {


    // Fields    

     private Integer medConceptId;
     private Boolean medConceptIdTyp;
     private Integer genericMedConceptId;


    // Constructors

    /** default constructor */
    public Rmedmgl0MedGenericMedLinkId() {
    }

    
    /** full constructor */
    public Rmedmgl0MedGenericMedLinkId(Integer medConceptId, Boolean medConceptIdTyp, Integer genericMedConceptId) {
        this.medConceptId = medConceptId;
        this.medConceptIdTyp = medConceptIdTyp;
        this.genericMedConceptId = genericMedConceptId;
    }

   
    // Property accessors

    @Column(name="MED_CONCEPT_ID", nullable=false, precision=8, scale=0)

    public Integer getMedConceptId() {
        return this.medConceptId;
    }
    
    public void setMedConceptId(Integer medConceptId) {
        this.medConceptId = medConceptId;
    }

    @Column(name="MED_CONCEPT_ID_TYP", nullable=false, precision=1, scale=0)

    public Boolean getMedConceptIdTyp() {
        return this.medConceptIdTyp;
    }
    
    public void setMedConceptIdTyp(Boolean medConceptIdTyp) {
        this.medConceptIdTyp = medConceptIdTyp;
    }

    @Column(name="GENERIC_MED_CONCEPT_ID", nullable=false, precision=8, scale=0)

    public Integer getGenericMedConceptId() {
        return this.genericMedConceptId;
    }
    
    public void setGenericMedConceptId(Integer genericMedConceptId) {
        this.genericMedConceptId = genericMedConceptId;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rmedmgl0MedGenericMedLinkId) ) return false;
		 Rmedmgl0MedGenericMedLinkId castOther = ( Rmedmgl0MedGenericMedLinkId ) other; 
         
		 return ( (this.getMedConceptId()==castOther.getMedConceptId()) || ( this.getMedConceptId()!=null && castOther.getMedConceptId()!=null && this.getMedConceptId().equals(castOther.getMedConceptId()) ) )
 && ( (this.getMedConceptIdTyp()==castOther.getMedConceptIdTyp()) || ( this.getMedConceptIdTyp()!=null && castOther.getMedConceptIdTyp()!=null && this.getMedConceptIdTyp().equals(castOther.getMedConceptIdTyp()) ) )
 && ( (this.getGenericMedConceptId()==castOther.getGenericMedConceptId()) || ( this.getGenericMedConceptId()!=null && castOther.getGenericMedConceptId()!=null && this.getGenericMedConceptId().equals(castOther.getGenericMedConceptId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getMedConceptId() == null ? 0 : this.getMedConceptId().hashCode() );
         result = 37 * result + ( getMedConceptIdTyp() == null ? 0 : this.getMedConceptIdTyp().hashCode() );
         result = 37 * result + ( getGenericMedConceptId() == null ? 0 : this.getGenericMedConceptId().hashCode() );
         return result;
   }   





}