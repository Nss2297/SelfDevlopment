package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rfmliad0IcdAllDescId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rfmliad0IcdAllDescId  implements java.io.Serializable {


    // Fields    

     private String icdCd;
     private String icdCdType;
     private String icdDescSourceCd;


    // Constructors

    /** default constructor */
    public Rfmliad0IcdAllDescId() {
    }

    
    /** full constructor */
    public Rfmliad0IcdAllDescId(String icdCd, String icdCdType, String icdDescSourceCd) {
        this.icdCd = icdCd;
        this.icdCdType = icdCdType;
        this.icdDescSourceCd = icdDescSourceCd;
    }

   
    // Property accessors

    @Column(name="ICD_CD", nullable=false, length=10)

    public String getIcdCd() {
        return this.icdCd;
    }
    
    public void setIcdCd(String icdCd) {
        this.icdCd = icdCd;
    }

    @Column(name="ICD_CD_TYPE", nullable=false, length=2)

    public String getIcdCdType() {
        return this.icdCdType;
    }
    
    public void setIcdCdType(String icdCdType) {
        this.icdCdType = icdCdType;
    }

    @Column(name="ICD_DESC_SOURCE_CD", nullable=false, length=2)

    public String getIcdDescSourceCd() {
        return this.icdDescSourceCd;
    }
    
    public void setIcdDescSourceCd(String icdDescSourceCd) {
        this.icdDescSourceCd = icdDescSourceCd;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rfmliad0IcdAllDescId) ) return false;
		 Rfmliad0IcdAllDescId castOther = ( Rfmliad0IcdAllDescId ) other; 
         
		 return ( (this.getIcdCd()==castOther.getIcdCd()) || ( this.getIcdCd()!=null && castOther.getIcdCd()!=null && this.getIcdCd().equals(castOther.getIcdCd()) ) )
 && ( (this.getIcdCdType()==castOther.getIcdCdType()) || ( this.getIcdCdType()!=null && castOther.getIcdCdType()!=null && this.getIcdCdType().equals(castOther.getIcdCdType()) ) )
 && ( (this.getIcdDescSourceCd()==castOther.getIcdDescSourceCd()) || ( this.getIcdDescSourceCd()!=null && castOther.getIcdDescSourceCd()!=null && this.getIcdDescSourceCd().equals(castOther.getIcdDescSourceCd()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getIcdCd() == null ? 0 : this.getIcdCd().hashCode() );
         result = 37 * result + ( getIcdCdType() == null ? 0 : this.getIcdCdType().hashCode() );
         result = 37 * result + ( getIcdDescSourceCd() == null ? 0 : this.getIcdDescSourceCd().hashCode() );
         return result;
   }   





}