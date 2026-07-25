package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rmedmhl0MedHiclseqnoLinkId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rmedmhl0MedHiclseqnoLinkId  implements java.io.Serializable {


    // Fields    

     private Integer medConceptId;
     private Boolean medConceptIdTyp;
     private Integer hiclSeqno;
     private Boolean medConceptHiclSrcCd;


    // Constructors

    /** default constructor */
    public Rmedmhl0MedHiclseqnoLinkId() {
    }

    
    /** full constructor */
    public Rmedmhl0MedHiclseqnoLinkId(Integer medConceptId, Boolean medConceptIdTyp, Integer hiclSeqno, Boolean medConceptHiclSrcCd) {
        this.medConceptId = medConceptId;
        this.medConceptIdTyp = medConceptIdTyp;
        this.hiclSeqno = hiclSeqno;
        this.medConceptHiclSrcCd = medConceptHiclSrcCd;
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

    @Column(name="HICL_SEQNO", nullable=false, precision=6, scale=0)

    public Integer getHiclSeqno() {
        return this.hiclSeqno;
    }
    
    public void setHiclSeqno(Integer hiclSeqno) {
        this.hiclSeqno = hiclSeqno;
    }

    @Column(name="MED_CONCEPT_HICL_SRC_CD", nullable=false, precision=1, scale=0)

    public Boolean getMedConceptHiclSrcCd() {
        return this.medConceptHiclSrcCd;
    }
    
    public void setMedConceptHiclSrcCd(Boolean medConceptHiclSrcCd) {
        this.medConceptHiclSrcCd = medConceptHiclSrcCd;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rmedmhl0MedHiclseqnoLinkId) ) return false;
		 Rmedmhl0MedHiclseqnoLinkId castOther = ( Rmedmhl0MedHiclseqnoLinkId ) other; 
         
		 return ( (this.getMedConceptId()==castOther.getMedConceptId()) || ( this.getMedConceptId()!=null && castOther.getMedConceptId()!=null && this.getMedConceptId().equals(castOther.getMedConceptId()) ) )
 && ( (this.getMedConceptIdTyp()==castOther.getMedConceptIdTyp()) || ( this.getMedConceptIdTyp()!=null && castOther.getMedConceptIdTyp()!=null && this.getMedConceptIdTyp().equals(castOther.getMedConceptIdTyp()) ) )
 && ( (this.getHiclSeqno()==castOther.getHiclSeqno()) || ( this.getHiclSeqno()!=null && castOther.getHiclSeqno()!=null && this.getHiclSeqno().equals(castOther.getHiclSeqno()) ) )
 && ( (this.getMedConceptHiclSrcCd()==castOther.getMedConceptHiclSrcCd()) || ( this.getMedConceptHiclSrcCd()!=null && castOther.getMedConceptHiclSrcCd()!=null && this.getMedConceptHiclSrcCd().equals(castOther.getMedConceptHiclSrcCd()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getMedConceptId() == null ? 0 : this.getMedConceptId().hashCode() );
         result = 37 * result + ( getMedConceptIdTyp() == null ? 0 : this.getMedConceptIdTyp().hashCode() );
         result = 37 * result + ( getHiclSeqno() == null ? 0 : this.getHiclSeqno().hashCode() );
         result = 37 * result + ( getMedConceptHiclSrcCd() == null ? 0 : this.getMedConceptHiclSrcCd().hashCode() );
         return result;
   }   





}