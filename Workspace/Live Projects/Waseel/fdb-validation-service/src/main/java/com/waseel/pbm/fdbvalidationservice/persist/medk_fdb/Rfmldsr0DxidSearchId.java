package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rfmldsr0DxidSearchId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rfmldsr0DxidSearchId  implements java.io.Serializable {


    // Fields    

     private Integer searchDxid;
     private Integer relatedDxid;
     private String fmlClinCode;


    // Constructors

    /** default constructor */
    public Rfmldsr0DxidSearchId() {
    }

    
    /** full constructor */
    public Rfmldsr0DxidSearchId(Integer searchDxid, Integer relatedDxid, String fmlClinCode) {
        this.searchDxid = searchDxid;
        this.relatedDxid = relatedDxid;
        this.fmlClinCode = fmlClinCode;
    }

   
    // Property accessors

    @Column(name="SEARCH_DXID", nullable=false, precision=8, scale=0)

    public Integer getSearchDxid() {
        return this.searchDxid;
    }
    
    public void setSearchDxid(Integer searchDxid) {
        this.searchDxid = searchDxid;
    }

    @Column(name="RELATED_DXID", nullable=false, precision=8, scale=0)

    public Integer getRelatedDxid() {
        return this.relatedDxid;
    }
    
    public void setRelatedDxid(Integer relatedDxid) {
        this.relatedDxid = relatedDxid;
    }

    @Column(name="FML_CLIN_CODE", nullable=false, length=2)

    public String getFmlClinCode() {
        return this.fmlClinCode;
    }
    
    public void setFmlClinCode(String fmlClinCode) {
        this.fmlClinCode = fmlClinCode;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rfmldsr0DxidSearchId) ) return false;
		 Rfmldsr0DxidSearchId castOther = ( Rfmldsr0DxidSearchId ) other; 
         
		 return ( (this.getSearchDxid()==castOther.getSearchDxid()) || ( this.getSearchDxid()!=null && castOther.getSearchDxid()!=null && this.getSearchDxid().equals(castOther.getSearchDxid()) ) )
 && ( (this.getRelatedDxid()==castOther.getRelatedDxid()) || ( this.getRelatedDxid()!=null && castOther.getRelatedDxid()!=null && this.getRelatedDxid().equals(castOther.getRelatedDxid()) ) )
 && ( (this.getFmlClinCode()==castOther.getFmlClinCode()) || ( this.getFmlClinCode()!=null && castOther.getFmlClinCode()!=null && this.getFmlClinCode().equals(castOther.getFmlClinCode()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getSearchDxid() == null ? 0 : this.getSearchDxid().hashCode() );
         result = 37 * result + ( getRelatedDxid() == null ? 0 : this.getRelatedDxid().hashCode() );
         result = 37 * result + ( getFmlClinCode() == null ? 0 : this.getFmlClinCode().hashCode() );
         return result;
   }   





}