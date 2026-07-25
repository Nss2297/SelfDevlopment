package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rmedpmr0ProductMedidReason entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMEDPMR0_PRODUCT_MEDID_REASON"
    ,schema="MEDK_FDB"
)

public class Rmedpmr0ProductMedidReason  implements java.io.Serializable {


    // Fields    

     private Rmedpmr0ProductMedidReasonId id;


    // Constructors

    /** default constructor */
    public Rmedpmr0ProductMedidReason() {
    }

    
    /** full constructor */
    public Rmedpmr0ProductMedidReason(Rmedpmr0ProductMedidReasonId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="productId", column=@Column(name="PRODUCT_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="productionDate", column=@Column(name="PRODUCTION_DATE", nullable=false, length=7) ), 
        @AttributeOverride(name="moveReasonCd", column=@Column(name="MOVE_REASON_CD", nullable=false, precision=4, scale=0) ) } )

    public Rmedpmr0ProductMedidReasonId getId() {
        return this.id;
    }
    
    public void setId(Rmedpmr0ProductMedidReasonId id) {
        this.id = id;
    }
   








}