package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Rmedpgr0ProductGenMedidRsn entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMEDPGR0_PRODUCT_GEN_MEDID_RSN"
    ,schema="MEDK_FDB"
)

public class Rmedpgr0ProductGenMedidRsn  implements java.io.Serializable {


    // Fields    

     private Rmedpgr0ProductGenMedidRsnId id;


    // Constructors

    /** default constructor */
    public Rmedpgr0ProductGenMedidRsn() {
    }

    
    /** full constructor */
    public Rmedpgr0ProductGenMedidRsn(Rmedpgr0ProductGenMedidRsnId id) {
        this.id = id;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="productId", column=@Column(name="PRODUCT_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="productionDate", column=@Column(name="PRODUCTION_DATE", nullable=false, length=7) ), 
        @AttributeOverride(name="moveReasonCd", column=@Column(name="MOVE_REASON_CD", nullable=false, precision=4, scale=0) ) } )

    public Rmedpgr0ProductGenMedidRsnId getId() {
        return this.id;
    }
    
    public void setId(Rmedpgr0ProductGenMedidRsnId id) {
        this.id = id;
    }
   








}