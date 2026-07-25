package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rfmlisx0IcdSearchExclusionId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rfmlisx0IcdSearchExclusionId  implements java.io.Serializable {


    // Fields    

     private String searchIcdCd;
     private String icdCdType;
     private Integer relatedDxid;
     private String fmlClinCode;
     private Integer clinDrugGroup;


    // Constructors

    /** default constructor */
    public Rfmlisx0IcdSearchExclusionId() {
    }

    
    /** full constructor */
    public Rfmlisx0IcdSearchExclusionId(String searchIcdCd, String icdCdType, Integer relatedDxid, String fmlClinCode, Integer clinDrugGroup) {
        this.searchIcdCd = searchIcdCd;
        this.icdCdType = icdCdType;
        this.relatedDxid = relatedDxid;
        this.fmlClinCode = fmlClinCode;
        this.clinDrugGroup = clinDrugGroup;
    }

   
    // Property accessors

    @Column(name="SEARCH_ICD_CD", nullable=false, length=10)

    public String getSearchIcdCd() {
        return this.searchIcdCd;
    }
    
    public void setSearchIcdCd(String searchIcdCd) {
        this.searchIcdCd = searchIcdCd;
    }

    @Column(name="ICD_CD_TYPE", nullable=false, length=2)

    public String getIcdCdType() {
        return this.icdCdType;
    }
    
    public void setIcdCdType(String icdCdType) {
        this.icdCdType = icdCdType;
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

    @Column(name="CLIN_DRUG_GROUP", nullable=false, precision=5, scale=0)

    public Integer getClinDrugGroup() {
        return this.clinDrugGroup;
    }
    
    public void setClinDrugGroup(Integer clinDrugGroup) {
        this.clinDrugGroup = clinDrugGroup;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rfmlisx0IcdSearchExclusionId) ) return false;
		 Rfmlisx0IcdSearchExclusionId castOther = ( Rfmlisx0IcdSearchExclusionId ) other; 
         
		 return ( (this.getSearchIcdCd()==castOther.getSearchIcdCd()) || ( this.getSearchIcdCd()!=null && castOther.getSearchIcdCd()!=null && this.getSearchIcdCd().equals(castOther.getSearchIcdCd()) ) )
 && ( (this.getIcdCdType()==castOther.getIcdCdType()) || ( this.getIcdCdType()!=null && castOther.getIcdCdType()!=null && this.getIcdCdType().equals(castOther.getIcdCdType()) ) )
 && ( (this.getRelatedDxid()==castOther.getRelatedDxid()) || ( this.getRelatedDxid()!=null && castOther.getRelatedDxid()!=null && this.getRelatedDxid().equals(castOther.getRelatedDxid()) ) )
 && ( (this.getFmlClinCode()==castOther.getFmlClinCode()) || ( this.getFmlClinCode()!=null && castOther.getFmlClinCode()!=null && this.getFmlClinCode().equals(castOther.getFmlClinCode()) ) )
 && ( (this.getClinDrugGroup()==castOther.getClinDrugGroup()) || ( this.getClinDrugGroup()!=null && castOther.getClinDrugGroup()!=null && this.getClinDrugGroup().equals(castOther.getClinDrugGroup()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getSearchIcdCd() == null ? 0 : this.getSearchIcdCd().hashCode() );
         result = 37 * result + ( getIcdCdType() == null ? 0 : this.getIcdCdType().hashCode() );
         result = 37 * result + ( getRelatedDxid() == null ? 0 : this.getRelatedDxid().hashCode() );
         result = 37 * result + ( getFmlClinCode() == null ? 0 : this.getFmlClinCode().hashCode() );
         result = 37 * result + ( getClinDrugGroup() == null ? 0 : this.getClinDrugGroup().hashCode() );
         return result;
   }   





}