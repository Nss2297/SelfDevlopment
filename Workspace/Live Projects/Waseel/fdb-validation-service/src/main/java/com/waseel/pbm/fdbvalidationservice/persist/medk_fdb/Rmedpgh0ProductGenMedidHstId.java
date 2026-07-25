package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rmedpgh0ProductGenMedidHstId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rmedpgh0ProductGenMedidHstId  implements java.io.Serializable {


    // Fields    

     private Integer productId;
     private Timestamp productionDate;


    // Constructors

    /** default constructor */
    public Rmedpgh0ProductGenMedidHstId() {
    }

    
    /** full constructor */
    public Rmedpgh0ProductGenMedidHstId(Integer productId, Timestamp productionDate) {
        this.productId = productId;
        this.productionDate = productionDate;
    }

   
    // Property accessors

    @Column(name="PRODUCT_ID", nullable=false, precision=8, scale=0)

    public Integer getProductId() {
        return this.productId;
    }
    
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Column(name="PRODUCTION_DATE", nullable=false, length=7)

    public Timestamp getProductionDate() {
        return this.productionDate;
    }
    
    public void setProductionDate(Timestamp productionDate) {
        this.productionDate = productionDate;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rmedpgh0ProductGenMedidHstId) ) return false;
		 Rmedpgh0ProductGenMedidHstId castOther = ( Rmedpgh0ProductGenMedidHstId ) other; 
         
		 return ( (this.getProductId()==castOther.getProductId()) || ( this.getProductId()!=null && castOther.getProductId()!=null && this.getProductId().equals(castOther.getProductId()) ) )
 && ( (this.getProductionDate()==castOther.getProductionDate()) || ( this.getProductionDate()!=null && castOther.getProductionDate()!=null && this.getProductionDate().equals(castOther.getProductionDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getProductId() == null ? 0 : this.getProductId().hashCode() );
         result = 37 * result + ( getProductionDate() == null ? 0 : this.getProductionDate().hashCode() );
         return result;
   }   





}