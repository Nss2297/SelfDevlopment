package com.waseel.pbm.fdbvalidationservice.persist.medk_fdb;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * Ripdat0ProductAttribute entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="RIPDAT0_PRODUCT_ATTRIBUTE"
    ,schema="MEDK_FDB"
)

public class Ripdat0ProductAttribute  implements java.io.Serializable {


    // Fields    

     private Ripdat0ProductAttributeId id;
     private String productAttributeValue;


    // Constructors

    /** default constructor */
    public Ripdat0ProductAttribute() {
    }

	/** minimal constructor */
    public Ripdat0ProductAttribute(Ripdat0ProductAttributeId id) {
        this.id = id;
    }
    
    /** full constructor */
    public Ripdat0ProductAttribute(Ripdat0ProductAttributeId id, String productAttributeValue) {
        this.id = id;
        this.productAttributeValue = productAttributeValue;
    }

   
    // Property accessors
    @EmbeddedId
    
    @AttributeOverrides( {
        @AttributeOverride(name="productId", column=@Column(name="PRODUCT_ID", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="productAttributeCode", column=@Column(name="PRODUCT_ATTRIBUTE_CODE", nullable=false, precision=8, scale=0) ), 
        @AttributeOverride(name="productAttributeSeq", column=@Column(name="PRODUCT_ATTRIBUTE_SEQ", nullable=false, precision=8, scale=0) ) } )

    public Ripdat0ProductAttributeId getId() {
        return this.id;
    }
    
    public void setId(Ripdat0ProductAttributeId id) {
        this.id = id;
    }
    
    @Column(name="PRODUCT_ATTRIBUTE_VALUE")

    public String getProductAttributeValue() {
        return this.productAttributeValue;
    }
    
    public void setProductAttributeValue(String productAttributeValue) {
        this.productAttributeValue = productAttributeValue;
    }
   








}