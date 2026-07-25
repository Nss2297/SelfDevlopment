package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * ProductInfo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="PRODUCT_INFO"
    ,schema="MEDK_FDB"
)

public class ProductInfo  implements java.io.Serializable {


    // Fields    

     private Timestamp productionDate;


    // Constructors

    /** default constructor */
    public ProductInfo() {
    }

    
    /** full constructor */
    public ProductInfo(Timestamp productionDate) {
        this.productionDate = productionDate;
    }

   
    // Property accessors
    @Id 
    
    @Column(name="PRODUCTION_DATE", nullable=false, length=7)

    public Timestamp getProductionDate() {
        return this.productionDate;
    }
    
    public void setProductionDate(Timestamp productionDate) {
        this.productionDate = productionDate;
    }
   








}