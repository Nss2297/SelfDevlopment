package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Rrtgipd0RtdGenProductLink entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RRTGIPD0_RTD_GEN_PRODUCT_LINK"
    ,schema="MEDK_FDB"
)

public class Rrtgipd0RtdGenProductLink  implements java.io.Serializable {


    // Fields    

     private Integer productId;
     private Integer routedGenId;


    // Constructors

    /** default constructor */
    public Rrtgipd0RtdGenProductLink() {
    }

    
    /** full constructor */
    public Rrtgipd0RtdGenProductLink(Integer productId, Integer routedGenId) {
        this.productId = productId;
        this.routedGenId = routedGenId;
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
    
    @Column(name="ROUTED_GEN_ID", nullable=false, precision=8, scale=0)

    public Integer getRoutedGenId() {
        return this.routedGenId;
    }
    
    public void setRoutedGenId(Integer routedGenId) {
        this.routedGenId = routedGenId;
    }
   








}