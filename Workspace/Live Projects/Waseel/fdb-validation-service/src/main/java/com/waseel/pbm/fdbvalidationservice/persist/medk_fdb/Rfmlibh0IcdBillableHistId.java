package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rfmlibh0IcdBillableHistId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rfmlibh0IcdBillableHistId  implements java.io.Serializable {


    // Fields    

     private String icdCd;
     private String icdCdType;
     private Timestamp icdFirstBillableDt;


    // Constructors

    /** default constructor */
    public Rfmlibh0IcdBillableHistId() {
    }

    
    /** full constructor */
    public Rfmlibh0IcdBillableHistId(String icdCd, String icdCdType, Timestamp icdFirstBillableDt) {
        this.icdCd = icdCd;
        this.icdCdType = icdCdType;
        this.icdFirstBillableDt = icdFirstBillableDt;
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

    @Column(name="ICD_FIRST_BILLABLE_DT", nullable=false, length=7)

    public Timestamp getIcdFirstBillableDt() {
        return this.icdFirstBillableDt;
    }
    
    public void setIcdFirstBillableDt(Timestamp icdFirstBillableDt) {
        this.icdFirstBillableDt = icdFirstBillableDt;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rfmlibh0IcdBillableHistId) ) return false;
		 Rfmlibh0IcdBillableHistId castOther = ( Rfmlibh0IcdBillableHistId ) other; 
         
		 return ( (this.getIcdCd()==castOther.getIcdCd()) || ( this.getIcdCd()!=null && castOther.getIcdCd()!=null && this.getIcdCd().equals(castOther.getIcdCd()) ) )
 && ( (this.getIcdCdType()==castOther.getIcdCdType()) || ( this.getIcdCdType()!=null && castOther.getIcdCdType()!=null && this.getIcdCdType().equals(castOther.getIcdCdType()) ) )
 && ( (this.getIcdFirstBillableDt()==castOther.getIcdFirstBillableDt()) || ( this.getIcdFirstBillableDt()!=null && castOther.getIcdFirstBillableDt()!=null && this.getIcdFirstBillableDt().equals(castOther.getIcdFirstBillableDt()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getIcdCd() == null ? 0 : this.getIcdCd().hashCode() );
         result = 37 * result + ( getIcdCdType() == null ? 0 : this.getIcdCdType().hashCode() );
         result = 37 * result + ( getIcdFirstBillableDt() == null ? 0 : this.getIcdFirstBillableDt().hashCode() );
         return result;
   }   





}