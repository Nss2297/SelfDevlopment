package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rfmlinm1IcdDescId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rfmlinm1IcdDescId  implements java.io.Serializable {


    // Fields    

     private String icdCd;
     private String icdCdType;


    // Constructors

    /** default constructor */
    public Rfmlinm1IcdDescId() {
    }

    
    /** full constructor */
    public Rfmlinm1IcdDescId(String icdCd, String icdCdType) {
        this.icdCd = icdCd;
        this.icdCdType = icdCdType;
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
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rfmlinm1IcdDescId) ) return false;
		 Rfmlinm1IcdDescId castOther = ( Rfmlinm1IcdDescId ) other; 
         
		 return ( (this.getIcdCd()==castOther.getIcdCd()) || ( this.getIcdCd()!=null && castOther.getIcdCd()!=null && this.getIcdCd().equals(castOther.getIcdCd()) ) )
 && ( (this.getIcdCdType()==castOther.getIcdCdType()) || ( this.getIcdCdType()!=null && castOther.getIcdCdType()!=null && this.getIcdCdType().equals(castOther.getIcdCdType()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getIcdCd() == null ? 0 : this.getIcdCd().hashCode() );
         result = 37 * result + ( getIcdCdType() == null ? 0 : this.getIcdCdType().hashCode() );
         return result;
   }   





}