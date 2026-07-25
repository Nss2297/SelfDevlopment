package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;


/**
 * Rmedpgr0ProductGenMedidRsnId entity. @author MyEclipse Persistence Tools
 */
@Embeddable

public class Rmedpgr0ProductGenMedidRsnId  implements java.io.Serializable {


    // Fields    

     private Integer productId;
     private Timestamp productionDate;
     private Short moveReasonCd;


    // Constructors

    /** default constructor */
    public Rmedpgr0ProductGenMedidRsnId() {
    }

    
    /** full constructor */
    public Rmedpgr0ProductGenMedidRsnId(Integer productId, Timestamp productionDate, Short moveReasonCd) {
        this.productId = productId;
        this.productionDate = productionDate;
        this.moveReasonCd = moveReasonCd;
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

    @Column(name="MOVE_REASON_CD", nullable=false, precision=4, scale=0)

    public Short getMoveReasonCd() {
        return this.moveReasonCd;
    }
    
    public void setMoveReasonCd(Short moveReasonCd) {
        this.moveReasonCd = moveReasonCd;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof Rmedpgr0ProductGenMedidRsnId) ) return false;
		 Rmedpgr0ProductGenMedidRsnId castOther = ( Rmedpgr0ProductGenMedidRsnId ) other; 
         
		 return ( (this.getProductId()==castOther.getProductId()) || ( this.getProductId()!=null && castOther.getProductId()!=null && this.getProductId().equals(castOther.getProductId()) ) )
 && ( (this.getProductionDate()==castOther.getProductionDate()) || ( this.getProductionDate()!=null && castOther.getProductionDate()!=null && this.getProductionDate().equals(castOther.getProductionDate()) ) )
 && ( (this.getMoveReasonCd()==castOther.getMoveReasonCd()) || ( this.getMoveReasonCd()!=null && castOther.getMoveReasonCd()!=null && this.getMoveReasonCd().equals(castOther.getMoveReasonCd()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getProductId() == null ? 0 : this.getProductId().hashCode() );
         result = 37 * result + ( getProductionDate() == null ? 0 : this.getProductionDate().hashCode() );
         result = 37 * result + ( getMoveReasonCd() == null ? 0 : this.getMoveReasonCd().hashCode() );
         return result;
   }   





}