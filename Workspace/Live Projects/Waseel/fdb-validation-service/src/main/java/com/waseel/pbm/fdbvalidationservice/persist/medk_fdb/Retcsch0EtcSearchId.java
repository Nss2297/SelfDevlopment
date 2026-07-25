package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Retcsch0EtcSearchId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Retcsch0EtcSearchId  implements java.io.Serializable {


    // Fields    

     private Integer etcSearchEtcId;
     private Integer etcProductRelatedEtcId;


    // Constructors

    /** default constructor */
    public Retcsch0EtcSearchId() {
    }

    
    /** full constructor */
    public Retcsch0EtcSearchId(Integer etcSearchEtcId, Integer etcProductRelatedEtcId) {
        this.etcSearchEtcId = etcSearchEtcId;
        this.etcProductRelatedEtcId = etcProductRelatedEtcId;
    }

   
    // Property accessors

    @Column(name="ETC_SEARCH_ETC_ID", nullable=false, precision=8, scale=0)

    public Integer getEtcSearchEtcId() {
        return this.etcSearchEtcId;
    }
    
    public void setEtcSearchEtcId(Integer etcSearchEtcId) {
        this.etcSearchEtcId = etcSearchEtcId;
    }

    @Column(name="ETC_PRODUCT_RELATED_ETC_ID", nullable=false, precision=8, scale=0)

    public Integer getEtcProductRelatedEtcId() {
        return this.etcProductRelatedEtcId;
    }
    
    public void setEtcProductRelatedEtcId(Integer etcProductRelatedEtcId) {
        this.etcProductRelatedEtcId = etcProductRelatedEtcId;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Retcsch0EtcSearchId) ) return false;
		 Retcsch0EtcSearchId castOther = ( Retcsch0EtcSearchId ) other; 
         
		 return ( (this.getEtcSearchEtcId()==castOther.getEtcSearchEtcId()) || ( this.getEtcSearchEtcId()!=null && castOther.getEtcSearchEtcId()!=null && this.getEtcSearchEtcId().equals(castOther.getEtcSearchEtcId()) ) )
 && ( (this.getEtcProductRelatedEtcId()==castOther.getEtcProductRelatedEtcId()) || ( this.getEtcProductRelatedEtcId()!=null && castOther.getEtcProductRelatedEtcId()!=null && this.getEtcProductRelatedEtcId().equals(castOther.getEtcProductRelatedEtcId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getEtcSearchEtcId() == null ? 0 : this.getEtcSearchEtcId().hashCode() );
         result = 37 * result + ( getEtcProductRelatedEtcId() == null ? 0 : this.getEtcProductRelatedEtcId().hashCode() );
         return result;
   }   





}