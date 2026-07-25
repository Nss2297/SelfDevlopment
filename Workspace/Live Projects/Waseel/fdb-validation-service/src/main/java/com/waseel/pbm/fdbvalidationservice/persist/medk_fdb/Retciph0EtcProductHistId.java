package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Retciph0EtcProductHistId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Retciph0EtcProductHistId  implements java.io.Serializable {


    // Fields    

     private Integer productId;
     private Integer etcId;
     private Integer etcRevisionSeqno;


    // Constructors

    /** default constructor */
    public Retciph0EtcProductHistId() {
    }

    
    /** full constructor */
    public Retciph0EtcProductHistId(Integer productId, Integer etcId, Integer etcRevisionSeqno) {
        this.productId = productId;
        this.etcId = etcId;
        this.etcRevisionSeqno = etcRevisionSeqno;
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

    @Column(name="ETC_REVISION_SEQNO", nullable=false, precision=5, scale=0)

    public Integer getEtcRevisionSeqno() {
        return this.etcRevisionSeqno;
    }
    
    public void setEtcRevisionSeqno(Integer etcRevisionSeqno) {
        this.etcRevisionSeqno = etcRevisionSeqno;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Retciph0EtcProductHistId) ) return false;
		 Retciph0EtcProductHistId castOther = ( Retciph0EtcProductHistId ) other; 
         
		 return ( (this.getProductId()==castOther.getProductId()) || ( this.getProductId()!=null && castOther.getProductId()!=null && this.getProductId().equals(castOther.getProductId()) ) )
 && ( (this.getEtcId()==castOther.getEtcId()) || ( this.getEtcId()!=null && castOther.getEtcId()!=null && this.getEtcId().equals(castOther.getEtcId()) ) )
 && ( (this.getEtcRevisionSeqno()==castOther.getEtcRevisionSeqno()) || ( this.getEtcRevisionSeqno()!=null && castOther.getEtcRevisionSeqno()!=null && this.getEtcRevisionSeqno().equals(castOther.getEtcRevisionSeqno()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getProductId() == null ? 0 : this.getProductId().hashCode() );
         result = 37 * result + ( getEtcId() == null ? 0 : this.getEtcId().hashCode() );
         result = 37 * result + ( getEtcRevisionSeqno() == null ? 0 : this.getEtcRevisionSeqno().hashCode() );
         return result;
   }   





}