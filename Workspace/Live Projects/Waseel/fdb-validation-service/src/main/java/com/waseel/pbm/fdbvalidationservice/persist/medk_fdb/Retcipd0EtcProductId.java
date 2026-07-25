package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Retcipd0EtcProductId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Retcipd0EtcProductId  implements java.io.Serializable {


    // Fields    

     private Integer productId;
     private Integer etcId;


    // Constructors

    /** default constructor */
    public Retcipd0EtcProductId() {
    }

    
    /** full constructor */
    public Retcipd0EtcProductId(Integer productId, Integer etcId) {
        this.productId = productId;
        this.etcId = etcId;
    }

   
    // Property accessors

    @Column(name="PRODUCT_ID", nullable=false, precision=8, scale=0)

    public Integer getProductId() {
        return this.productId;
    }
    
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Column(name="ETC_ID", nullable=false, precision=8, scale=0)

    public Integer getEtcId() {
        return this.etcId;
    }
    
    public void setEtcId(Integer etcId) {
        this.etcId = etcId;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Retcipd0EtcProductId) ) return false;
		 Retcipd0EtcProductId castOther = ( Retcipd0EtcProductId ) other; 
         
		 return ( (this.getProductId()==castOther.getProductId()) || ( this.getProductId()!=null && castOther.getProductId()!=null && this.getProductId().equals(castOther.getProductId()) ) )
 && ( (this.getEtcId()==castOther.getEtcId()) || ( this.getEtcId()!=null && castOther.getEtcId()!=null && this.getEtcId().equals(castOther.getEtcId()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getProductId() == null ? 0 : this.getProductId().hashCode() );
         result = 37 * result + ( getEtcId() == null ? 0 : this.getEtcId().hashCode() );
         return result;
   }   





}