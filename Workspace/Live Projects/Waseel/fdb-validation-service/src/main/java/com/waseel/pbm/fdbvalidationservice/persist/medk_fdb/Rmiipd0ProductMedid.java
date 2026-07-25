package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rmiipd0ProductMedid entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RMIIPD0_PRODUCT_MEDID"
    ,schema="MEDK_FDB"
)

public class Rmiipd0ProductMedid  implements java.io.Serializable {


    // Fields    

     private Integer productId;
     private Integer medid;


    // Constructors

    /** default constructor */
    public Rmiipd0ProductMedid() {
    }

    
    /** full constructor */
    public Rmiipd0ProductMedid(Integer productId, Integer medid) {
        this.productId = productId;
        this.medid = medid;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="PRODUCT_ID", unique=true, nullable=false, precision=8, scale=0)

    public Integer getProductId() {
        return this.productId;
    }
    
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    
    @Column(name="MEDID", nullable=false, precision=8, scale=0)

    public Integer getMedid() {
        return this.medid;
    }
    
    public void setMedid(Integer medid) {
        this.medid = medid;
    }
   








}